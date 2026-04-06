package testFramework.utilities.recall;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import testFramework.constants.ConstantsCommunicationRestServer;
import testFramework.constants.ValuesGlobal;
import testFramework.utilities.ReusableMethods;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SshConnection {

	public static String importedRecallFileName;

	// Required values to connect to SSH server
	// ivwb4363.audi-connect-mgm.web.audi.vwg
	private static final String HOST_4363 = ValuesGlobal.HOST_4363;
	private static final String USERNAME = ValuesGlobal.USERNAME;
	private static final String SERVER_PSW = ValuesGlobal.SERVER_PSW;
	private static final int PORT_4363 = ValuesGlobal.PORT_4363;
	private static final String MOCK_SERVER_KEY_PATH = ValuesGlobal.MOCK_SERVER_KEY_PATH;
	private static final String RECEIVE_FOLDER_PATH = ValuesGlobal.RECEIVE_FOLDER_PATH;
	private static final String LOCAL_TEMP_FOLDER = ValuesGlobal.LOCAL_TEMP_FOLDER;

	public static synchronized void openSessionViaSSHAndCreateImportedRecall(String content) throws Exception {
        importedRecallFileName = CreatingImportedRecallFile.generateFileName(); // Assign to class variable
		String localFilePath = LOCAL_TEMP_FOLDER + importedRecallFileName;
		String remoteFilePath = RECEIVE_FOLDER_PATH + importedRecallFileName;

		// Create recall file
		 CreatingImportedRecallFile.getContent(importedRecallFileName, content);
		  // Validate local file
		 File file = new File(localFilePath);
	     if (!file.exists()) {
		 throw new IllegalStateException("Local file " + localFilePath + " does not exist");
		 }
		  long fileSize = file.length();
          FileInputStream fileInputStream = null;
		  OutputStream out = null;
		  JSch javaSecureChannel = new JSch();
		  Session session = null;
		  ChannelExec channel = null;
		  try {
			    // SSH session setup
			    System.out.println("Starting SSH session setup");
			    session = javaSecureChannel.getSession(USERNAME, HOST_4363, PORT_4363);
			    session.setPassword(SERVER_PSW);
			    session.setConfig("StrictHostKeyChecking", "yes");
			 
			    String publicKeyContent = new String(Files.readAllBytes(Paths.get(MOCK_SERVER_KEY_PATH)));
			    String[] keyParts = publicKeyContent.split(" ");
			    int keyType = HostKey.SSHRSA;
			    byte[] keyBytes = Base64.getDecoder().decode(keyParts[1]);
			    HostKey hostKey = new HostKey(HOST_4363, keyType, keyBytes);
			    javaSecureChannel.getHostKeyRepository().add(hostKey, null);
			 
			    session.connect(30000);
			    System.out.println("Session connected");
			 
			    // SCP transfer
			    System.out.println("Starting SCP transfer");
			    String command = "sudo -u r2mbbb scp -t \"" + remoteFilePath + "\"";
			    channel = (ChannelExec) session.openChannel("exec");
			    channel.setCommand(command);
			    out = channel.getOutputStream();
			    channel.connect(10000);
			    System.out.println("Channel connected");
			 
			    // Send file metadata
			    System.out.println("Sending file metadata");
			    command = "C0644 " + fileSize + " " + importedRecallFileName + "\n";
			    out.write(command.getBytes());
			    out.flush();
			    System.out.println("File metadata sent");
			 
			    // Send file content
			    System.out.println("Starting file content transfer");
			    fileInputStream = new FileInputStream(localFilePath);
			    byte[] buffer = new byte[1024];
			    while (true) {
			        int len = fileInputStream.read(buffer, 0, buffer.length);
			        if (len <= 0) break;
			        out.write(buffer, 0, len);
			        out.flush();
			    }
			    System.out.println("File content transfer complete");
			 
			    // Signal end of file
			    buffer[0] = 0;
			    out.write(buffer, 0, 1);
			    out.flush();
			    out.close();
			    System.out.println("Output stream closed");
			 
			    // Wait for SCP command to complete
			    long startTime = System.currentTimeMillis();
			    while (!channel.isClosed() && System.currentTimeMillis() - startTime < 10000) {
			        Thread.sleep(100);
			    }
			    if (!channel.isClosed()) {
			        throw new IOException("SCP channel did not close within timeout");
			    }
			    int exitStatus = channel.getExitStatus();
			    if (exitStatus != 0) {
			        throw new IOException("SCP command failed with exit status: " + exitStatus);
			    }
			 
			    // Add delay for NFS synchronization
			    Thread.sleep(2000);
			 
			    // Assert remote file is not 0KB
			    System.out.println("Running stat command");
			    ChannelExec checkChannel = (ChannelExec) session.openChannel("exec");
			    checkChannel.setCommand("sudo -u r2mbbb sync && sudo -u r2mbbb stat --format=%s " + remoteFilePath);
			    InputStream in = checkChannel.getInputStream();
			    checkChannel.connect(10000);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			    StringBuilder output = new StringBuilder();
			    startTime = System.currentTimeMillis();
			    int timeoutMs = 10000;
			    String line;
			    while ((line = reader.readLine()) != null) {
			        output.append(line);
			    }
			    if (System.currentTimeMillis() - startTime > timeoutMs) {
			        throw new IOException("Timeout waiting for stat command output");
			    }
			    String remoteSize = output.toString().trim();
			    System.out.println("stat output: '" + remoteSize + "'");
			    checkChannel.disconnect();
			 
			    if (remoteSize == null || remoteSize.isEmpty()) {
			        throw new IOException("Failed to retrieve remote file size for " + remoteFilePath);
			    }
			    if (Long.parseLong(remoteSize) == 0) {
			        throw new IOException("Remote file " + remoteFilePath + " is 0KB");
			    }
			    if (Long.parseLong(remoteSize) != fileSize) {
			        throw new IOException("Remote file size mismatch: expected " + fileSize + ", got " + remoteSize);
			    }
			 
			    // Cleanup
			    channel.disconnect();
			    System.out.println("channel closed");
			    session.disconnect();
			    System.out.println("session closed");
			    System.out.println("File " + importedRecallFileName + " uploaded successfully to Server ivwb4363");
			    CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath);
			    String currentTimeStamp = ReusableMethods.getCurrentTimestampWithSecond();
			    System.out.println("Time to create imported recall via SSH: " + currentTimeStamp);
			 
			} catch (Exception e) {
			    System.err.println("Transfer failed: " + e.getMessage());
			    e.printStackTrace();
			    throw e;
			} finally {
		 if (fileInputStream != null) {
		 try {
	     fileInputStream.close();
		 } catch (IOException e) {
		  System.err.println("Error closing FileInputStream: " + e.getMessage());
		 }
		 }
		 if (out != null) {
		 try {
		 out.close();
		 } catch (IOException e) {
		  System.err.println("Error closing OutputStream: " + e.getMessage());
		  }
		  }
		 if (channel != null && !channel.isClosed()) {
		  channel.disconnect();
		 }
		 if (session != null && session.isConnected()) {
		 session.disconnect();
		 }
		 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath);
		 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath + ".json");
		 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath + ".zip");
	}
 }
	
	public static synchronized void openSessionViaSSHAndCreateImportedRecall2(String content) throws Exception {
        importedRecallFileName = CreatingImportedRecallFile.generateRecall2FileName(); // Assign to class variable
		String localFilePath = LOCAL_TEMP_FOLDER + importedRecallFileName;
		String remoteFilePath = RECEIVE_FOLDER_PATH + importedRecallFileName;

		// Create recall file
		 CreatingImportedRecallFile.getContent(importedRecallFileName, content);
		  // Validate local file
		 File file = new File(localFilePath);
	     if (!file.exists()) {
		 throw new IllegalStateException("Local file " + localFilePath + " does not exist");
		 }
		  long fileSize = file.length();
          FileInputStream fileInputStream = null;
		  OutputStream out = null;
		  JSch javaSecureChannel = new JSch();
		  Session session = null;
		  ChannelExec channel = null;
		  try {
			    // SSH session setup
			    System.out.println("Starting SSH session setup");
			    session = javaSecureChannel.getSession(USERNAME, HOST_4363, PORT_4363);
			    session.setPassword(SERVER_PSW);
			    session.setConfig("StrictHostKeyChecking", "yes");
			 
			    String publicKeyContent = new String(Files.readAllBytes(Paths.get(MOCK_SERVER_KEY_PATH)));
			    String[] keyParts = publicKeyContent.split(" ");
			    int keyType = HostKey.SSHRSA;
			    byte[] keyBytes = Base64.getDecoder().decode(keyParts[1]);
			    HostKey hostKey = new HostKey(HOST_4363, keyType, keyBytes);
			    javaSecureChannel.getHostKeyRepository().add(hostKey, null);
			 
			    session.connect(30000);
			    System.out.println("Session connected");
			 
			    // SCP transfer
			    System.out.println("Starting SCP transfer");
			    String command = "sudo -u r2mbbb scp -t \"" + remoteFilePath + "\"";
			    channel = (ChannelExec) session.openChannel("exec");
			    channel.setCommand(command);
			    out = channel.getOutputStream();
			    channel.connect(10000);
			    System.out.println("Channel connected");
			 
			    // Send file metadata
			    System.out.println("Sending file metadata");
			    command = "C0644 " + fileSize + " " + importedRecallFileName + "\n";
			    out.write(command.getBytes());
			    out.flush();
			    System.out.println("File metadata sent");
			 
			    // Send file content
			    System.out.println("Starting file content transfer");
			    fileInputStream = new FileInputStream(localFilePath);
			    byte[] buffer = new byte[1024];
			    while (true) {
			        int len = fileInputStream.read(buffer, 0, buffer.length);
			        if (len <= 0) break;
			        out.write(buffer, 0, len);
			        out.flush();
			    }
			    System.out.println("File content transfer complete");
			 
			    // Signal end of file
			    buffer[0] = 0;
			    out.write(buffer, 0, 1);
			    out.flush();
			    out.close();
			    System.out.println("Output stream closed");
			 
			    // Wait for SCP command to complete
			    long startTime = System.currentTimeMillis();
			    while (!channel.isClosed() && System.currentTimeMillis() - startTime < 10000) {
			        Thread.sleep(100);
			    }
			    if (!channel.isClosed()) {
			        throw new IOException("SCP channel did not close within timeout");
			    }
			    int exitStatus = channel.getExitStatus();
			    if (exitStatus != 0) {
			        throw new IOException("SCP command failed with exit status: " + exitStatus);
			    }
			 
			    // Add delay for NFS synchronization
			    Thread.sleep(2000);
			 
			    // Assert remote file is not 0KB
			    System.out.println("Running stat command");
			    ChannelExec checkChannel = (ChannelExec) session.openChannel("exec");
			    checkChannel.setCommand("sudo -u r2mbbb sync && sudo -u r2mbbb stat --format=%s " + remoteFilePath);
			    InputStream in = checkChannel.getInputStream();
			    checkChannel.connect(10000);
			    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			    StringBuilder output = new StringBuilder();
			    startTime = System.currentTimeMillis();
			    int timeoutMs = 10000;
			    String line;
			    while ((line = reader.readLine()) != null) {
			        output.append(line);
			    }
			    if (System.currentTimeMillis() - startTime > timeoutMs) {
			        throw new IOException("Timeout waiting for stat command output");
			    }
			    String remoteSize = output.toString().trim();
			    System.out.println("stat output: '" + remoteSize + "'");
			    checkChannel.disconnect();
			 
			    if (remoteSize == null || remoteSize.isEmpty()) {
			        throw new IOException("Failed to retrieve remote file size for " + remoteFilePath);
			    }
			    if (Long.parseLong(remoteSize) == 0) {
			        throw new IOException("Remote file " + remoteFilePath + " is 0KB");
			    }
			    if (Long.parseLong(remoteSize) != fileSize) {
			        throw new IOException("Remote file size mismatch: expected " + fileSize + ", got " + remoteSize);
			    }
			 
			    // Cleanup
			    channel.disconnect();
			    System.out.println("channel closed");
			    session.disconnect();
			    System.out.println("session closed");
			    System.out.println("File " + importedRecallFileName + " uploaded successfully to Server ivwb4363");
			    CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath);
			    String currentTimeStamp = ReusableMethods.getCurrentTimestampWithSecond();
			    System.out.println("Time to create imported recall via SSH: " + currentTimeStamp);
			 
			} catch (Exception e) {
			    System.err.println("Transfer failed: " + e.getMessage());
			    e.printStackTrace();
			    throw e;
			} finally {
		 if (fileInputStream != null) {
		 try {
	     fileInputStream.close();
		 } catch (IOException e) {
		  System.err.println("Error closing FileInputStream: " + e.getMessage());
		 }
		 }
		 if (out != null) {
		 try {
		 out.close();
		 } catch (IOException e) {
		  System.err.println("Error closing OutputStream: " + e.getMessage());
		  }
		  }
		 if (channel != null && !channel.isClosed()) {
		  channel.disconnect();
		 }
		 if (session != null && session.isConnected()) {
		 session.disconnect();
		 }
		 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath);
		 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath + ".json");
		 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath + ".zip");
	}
 }


	public static synchronized void openSessionViaSSHAndCreateImportedRecallWithFaultyFields(String content,
			String targetLine, String subText, String faultyCode, int faultyCodeIndex, int faultyCodeLength)
			throws Exception {
		importedRecallFileName = CreatingImportedRecallFile.generateFileName(); // Assign to class variable

		// Create recall file to be imported
		CreatingImportedRecallFile.getContentWithFaultyValues(importedRecallFileName, content, targetLine, subText,
				faultyCode, faultyCodeIndex, faultyCodeLength);
		
		String localFilePath = LOCAL_TEMP_FOLDER + importedRecallFileName;
		String remoteFilePath = RECEIVE_FOLDER_PATH + importedRecallFileName;
		// Validate local file
		
				 File file = new File(localFilePath);
			     if (!file.exists()) {
				 throw new IllegalStateException("Local file " + localFilePath + " does not exist");
				 }
				  long fileSize = file.length();
		          FileInputStream fileInputStream = null;
				  OutputStream out = null;
				  JSch javaSecureChannel = new JSch();
				  Session session = null;
				  ChannelExec channel = null;
				  try {
					    // SSH session setup
					    System.out.println("Starting SSH session setup");
					    session = javaSecureChannel.getSession(USERNAME, HOST_4363, PORT_4363);
					    session.setPassword(SERVER_PSW);
					    session.setConfig("StrictHostKeyChecking", "yes");
					 
					    String publicKeyContent = new String(Files.readAllBytes(Paths.get(MOCK_SERVER_KEY_PATH)));
					    String[] keyParts = publicKeyContent.split(" ");
					    int keyType = HostKey.SSHRSA;
					    byte[] keyBytes = Base64.getDecoder().decode(keyParts[1]);
					    HostKey hostKey = new HostKey(HOST_4363, keyType, keyBytes);
					    javaSecureChannel.getHostKeyRepository().add(hostKey, null);
					 
					    session.connect(30000);
					    System.out.println("Session connected");
					 
					    // SCP transfer
					    System.out.println("Starting SCP transfer");
					    String command = "sudo -u r2mbbb scp -t \"" + remoteFilePath + "\"";
					    channel = (ChannelExec) session.openChannel("exec");
					    channel.setCommand(command);
					    out = channel.getOutputStream();
					    channel.connect(10000);
					    System.out.println("Channel connected");
					 
					    // Send file metadata
					    System.out.println("Sending file metadata");
					    command = "C0644 " + fileSize + " " + importedRecallFileName + "\n";
					    out.write(command.getBytes());
					    out.flush();
					    System.out.println("File metadata sent");
					 
					    // Send file content
					    System.out.println("Starting file content transfer");
					    fileInputStream = new FileInputStream(localFilePath);
					    byte[] buffer = new byte[1024];
					    while (true) {
					        int len = fileInputStream.read(buffer, 0, buffer.length);
					        if (len <= 0) break;
					        out.write(buffer, 0, len);
					        out.flush();
					    }
					    System.out.println("File content transfer complete");
					 
					    // Signal end of file
					    buffer[0] = 0;
					    out.write(buffer, 0, 1);
					    out.flush();
					    out.close();
					    System.out.println("Output stream closed");
					 
					    // Wait for SCP command to complete
					    long startTime = System.currentTimeMillis();
					    while (!channel.isClosed() && System.currentTimeMillis() - startTime < 10000) {
					        Thread.sleep(100);
					    }
					    if (!channel.isClosed()) {
					        throw new IOException("SCP channel did not close within timeout");
					    }
					    int exitStatus = channel.getExitStatus();
					    if (exitStatus != 0) {
					        throw new IOException("SCP command failed with exit status: " + exitStatus);
					    }
					 
					    // Add delay for NFS synchronization
					    Thread.sleep(2000);
					 
					    // Assert remote file is not 0KB
					    System.out.println("Running stat command");
					    ChannelExec checkChannel = (ChannelExec) session.openChannel("exec");
					    checkChannel.setCommand("sudo -u r2mbbb sync && sudo -u r2mbbb stat --format=%s " + remoteFilePath);
					    InputStream in = checkChannel.getInputStream();
					    checkChannel.connect(10000);
					    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					    StringBuilder output = new StringBuilder();
					    startTime = System.currentTimeMillis();
					    int timeoutMs = 10000;
					    String line;
					    while ((line = reader.readLine()) != null) {
					        output.append(line);
					    }
					    if (System.currentTimeMillis() - startTime > timeoutMs) {
					        throw new IOException("Timeout waiting for stat command output");
					    }
					    String remoteSize = output.toString().trim();
					    System.out.println("stat output: '" + remoteSize + "'");
					    checkChannel.disconnect();
					 
					    if (remoteSize == null || remoteSize.isEmpty()) {
					        throw new IOException("Failed to retrieve remote file size for " + remoteFilePath);
					    }
					    if (Long.parseLong(remoteSize) == 0) {
					        throw new IOException("Remote file " + remoteFilePath + " is 0KB");
					    }
					    if (Long.parseLong(remoteSize) != fileSize) {
					        throw new IOException("Remote file size mismatch: expected " + fileSize + ", got " + remoteSize);
					    }
					 
					    // Cleanup
					    channel.disconnect();
					    System.out.println("channel closed");
					    session.disconnect();
					    System.out.println("session closed");
					    System.out.println("File " + importedRecallFileName + " uploaded successfully to Server ivwb4363");
					    CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath);
					    String currentTimeStamp = ReusableMethods.getCurrentTimestampWithSecond();
					    System.out.println("Time to create imported recall via SSH: " + currentTimeStamp);
					 
					} catch (Exception e) {
					    System.err.println("Transfer failed: " + e.getMessage());
					    e.printStackTrace();
					    throw e;
					} finally {
				 if (fileInputStream != null) {
				 try {
			     fileInputStream.close();
				 } catch (IOException e) {
				  System.err.println("Error closing FileInputStream: " + e.getMessage());
				 }
				 }
				 if (out != null) {
				 try {
				 out.close();
				 } catch (IOException e) {
				  System.err.println("Error closing OutputStream: " + e.getMessage());
				  }
				  }
				 if (channel != null && !channel.isClosed()) {
				  channel.disconnect();
				 }
				 if (session != null && session.isConnected()) {
				 session.disconnect();
				 }
				 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath);
				 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath + ".json");
				 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath + ".zip");
			}
		 }

	public static synchronized void openSessionViaSSHAndCreateImportedRecallWithoutText(String content)
			throws Exception {

		String importedRecallFileName = CreatingImportedRecallFile.generateFileName();

		// Create recall file to be imported
		CreatingImportedRecallFile.getContent_Out_Txt_Without_Json_File(importedRecallFileName, content);

		// To create an Imported Recall, send the created file to the server
		// ivwb4363.audi-connect-mgm.web.audi.vwg via SSH.
		String localFilePath = LOCAL_TEMP_FOLDER + importedRecallFileName;
		String remoteFilePath = RECEIVE_FOLDER_PATH + importedRecallFileName;

		FileInputStream fileInputStream = null;
		JSch javaSecureChannel = new JSch();

		try {
			// Create new session
			Session session = javaSecureChannel.getSession(USERNAME, HOST_4363, PORT_4363);
			session.setPassword(SERVER_PSW);

			// Adjust security settings
			session.setConfig("StrictHostKeyChecking", "yes");

			// Read public key file
			String publicKeyContent = new String(Files.readAllBytes(Paths.get(MOCK_SERVER_KEY_PATH)));
			String[] keyParts = publicKeyContent.split(" ");
			int keyType = HostKey.SSHRSA;
			byte[] keyBytes = Base64.getDecoder().decode(keyParts[1]);

			// Add the server's key to the HostKeyRepository before logging in
			HostKey hostKey = new HostKey(HOST_4363, keyType, keyBytes);
			javaSecureChannel.getHostKeyRepository().add(hostKey, null);

			// Open session
			session.connect();
			System.out.println("session opened");

			// Create the command to run the SCP command with 'sudo' and 'su'
			String command = "sudo -u r2mbbb scp -t \"" + remoteFilePath + "\"";

			// Open a channel for SCP
			ChannelExec channel = (ChannelExec) session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			// Get the output stream of the channel
			OutputStream out = channel.getOutputStream();
			channel.connect();
			System.out.println("channel opened");
			File file = new File(localFilePath);
			long fileSize = file.length();

			// Send command for file transfer
			command = "C0644 " + fileSize + " ";
			if (localFilePath.lastIndexOf('/') > 0) {
				command += localFilePath.substring(localFilePath.lastIndexOf('/') + 1);
			} else {
				command += localFilePath;
			}
			command += "\n";
			out.write(command.getBytes());
			out.flush();

			// Send file content
			fileInputStream = new FileInputStream(localFilePath);
			byte[] buffer = new byte[1024];
			while (true) {
				int len = fileInputStream.read(buffer, 0, buffer.length);
				if (len <= 0)
					break;
				out.write(buffer, 0, len);
			}

			fileInputStream.close();

			// End of file
			buffer[0] = 0;
			out.write(buffer, 0, 1);
			out.flush();

			// Disconnect channel and session
			channel.disconnect();
			System.out.println("channel closed");
			session.disconnect();
			System.out.println("session closed");

			System.out.println(
					"File " + importedRecallFileName + " has been uploaded successfully around SSH to Server ivwb4363");
			CreatingImportedRecallFile.deleteImportedRecallTemplate(LOCAL_TEMP_FOLDER + importedRecallFileName);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (fileInputStream != null)
					fileInputStream.close();
			} catch (Exception ee) {
				System.out.println("Handle error during stream close");
				e.printStackTrace();
			}

			// Delete created files
			CreatingImportedRecallFile.deleteImportedRecallTemplate(LOCAL_TEMP_FOLDER + importedRecallFileName);
			CreatingImportedRecallFile
					.deleteImportedRecallTemplate(LOCAL_TEMP_FOLDER + importedRecallFileName + ".json");
			CreatingImportedRecallFile
					.deleteImportedRecallTemplate(LOCAL_TEMP_FOLDER + importedRecallFileName + ".zip");
		}
	}
	
	public static synchronized void openSessionViaSSHAndCreateImportedRecall2WithFaultyFields(String content,
			String targetLine, String subText, String faultyCode, int faultyCodeIndex, int faultyCodeLength)
			throws Exception {
		importedRecallFileName = CreatingImportedRecallFile.generateRecall2FileName(); // Assign to class variable

		// Create recall file to be imported
		CreatingImportedRecallFile.getContentWithFaultyValues(importedRecallFileName, content, targetLine, subText,
				faultyCode, faultyCodeIndex, faultyCodeLength);
		
		String localFilePath = LOCAL_TEMP_FOLDER + importedRecallFileName;
		String remoteFilePath = RECEIVE_FOLDER_PATH + importedRecallFileName;
		// Validate local file
		
				 File file = new File(localFilePath);
			     if (!file.exists()) {
				 throw new IllegalStateException("Local file " + localFilePath + " does not exist");
				 }
				  long fileSize = file.length();
		          FileInputStream fileInputStream = null;
				  OutputStream out = null;
				  JSch javaSecureChannel = new JSch();
				  Session session = null;
				  ChannelExec channel = null;
				  try {
					    // SSH session setup
					    System.out.println("Starting SSH session setup");
					    session = javaSecureChannel.getSession(USERNAME, HOST_4363, PORT_4363);
					    session.setPassword(SERVER_PSW);
					    session.setConfig("StrictHostKeyChecking", "yes");
					 
					    String publicKeyContent = new String(Files.readAllBytes(Paths.get(MOCK_SERVER_KEY_PATH)));
					    String[] keyParts = publicKeyContent.split(" ");
					    int keyType = HostKey.SSHRSA;
					    byte[] keyBytes = Base64.getDecoder().decode(keyParts[1]);
					    HostKey hostKey = new HostKey(HOST_4363, keyType, keyBytes);
					    javaSecureChannel.getHostKeyRepository().add(hostKey, null);
					 
					    session.connect(30000);
					    System.out.println("Session connected");
					 
					    // SCP transfer
					    System.out.println("Starting SCP transfer");
					    String command = "sudo -u r2mbbb scp -t \"" + remoteFilePath + "\"";
					    channel = (ChannelExec) session.openChannel("exec");
					    channel.setCommand(command);
					    out = channel.getOutputStream();
					    channel.connect(10000);
					    System.out.println("Channel connected");
					 
					    // Send file metadata
					    System.out.println("Sending file metadata");
					    command = "C0644 " + fileSize + " " + importedRecallFileName + "\n";
					    out.write(command.getBytes());
					    out.flush();
					    System.out.println("File metadata sent");
					 
					    // Send file content
					    System.out.println("Starting file content transfer");
					    fileInputStream = new FileInputStream(localFilePath);
					    byte[] buffer = new byte[1024];
					    while (true) {
					        int len = fileInputStream.read(buffer, 0, buffer.length);
					        if (len <= 0) break;
					        out.write(buffer, 0, len);
					        out.flush();
					    }
					    System.out.println("File content transfer complete");
					 
					    // Signal end of file
					    buffer[0] = 0;
					    out.write(buffer, 0, 1);
					    out.flush();
					    out.close();
					    System.out.println("Output stream closed");
					 
					    // Wait for SCP command to complete
					    long startTime = System.currentTimeMillis();
					    while (!channel.isClosed() && System.currentTimeMillis() - startTime < 10000) {
					        Thread.sleep(100);
					    }
					    if (!channel.isClosed()) {
					        throw new IOException("SCP channel did not close within timeout");
					    }
					    int exitStatus = channel.getExitStatus();
					    if (exitStatus != 0) {
					        throw new IOException("SCP command failed with exit status: " + exitStatus);
					    }
					 
					    // Add delay for NFS synchronization
					    Thread.sleep(2000);
					 
					    // Assert remote file is not 0KB
					    System.out.println("Running stat command");
					    ChannelExec checkChannel = (ChannelExec) session.openChannel("exec");
					    checkChannel.setCommand("sudo -u r2mbbb sync && sudo -u r2mbbb stat --format=%s " + remoteFilePath);
					    InputStream in = checkChannel.getInputStream();
					    checkChannel.connect(10000);
					    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					    StringBuilder output = new StringBuilder();
					    startTime = System.currentTimeMillis();
					    int timeoutMs = 10000;
					    String line;
					    while ((line = reader.readLine()) != null) {
					        output.append(line);
					    }
					    if (System.currentTimeMillis() - startTime > timeoutMs) {
					        throw new IOException("Timeout waiting for stat command output");
					    }
					    String remoteSize = output.toString().trim();
					    System.out.println("stat output: '" + remoteSize + "'");
					    checkChannel.disconnect();
					 
					    if (remoteSize == null || remoteSize.isEmpty()) {
					        throw new IOException("Failed to retrieve remote file size for " + remoteFilePath);
					    }
					    if (Long.parseLong(remoteSize) == 0) {
					        throw new IOException("Remote file " + remoteFilePath + " is 0KB");
					    }
					    if (Long.parseLong(remoteSize) != fileSize) {
					        throw new IOException("Remote file size mismatch: expected " + fileSize + ", got " + remoteSize);
					    }
					 
					    // Cleanup
					    channel.disconnect();
					    System.out.println("channel closed");
					    session.disconnect();
					    System.out.println("session closed");
					    System.out.println("File " + importedRecallFileName + " uploaded successfully to Server ivwb4363");
					    CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath);
					    String currentTimeStamp = ReusableMethods.getCurrentTimestampWithSecond();
					    System.out.println("Time to create imported recall via SSH: " + currentTimeStamp);
					 
					} catch (Exception e) {
					    System.err.println("Transfer failed: " + e.getMessage());
					    e.printStackTrace();
					    throw e;
					} finally {
				 if (fileInputStream != null) {
				 try {
			     fileInputStream.close();
				 } catch (IOException e) {
				  System.err.println("Error closing FileInputStream: " + e.getMessage());
				 }
				 }
				 if (out != null) {
				 try {
				 out.close();
				 } catch (IOException e) {
				  System.err.println("Error closing OutputStream: " + e.getMessage());
				  }
				  }
				 if (channel != null && !channel.isClosed()) {
				  channel.disconnect();
				 }
				 if (session != null && session.isConnected()) {
				 session.disconnect();
				 }
				 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath);
				 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath + ".json");
				 CreatingImportedRecallFile.deleteImportedRecallTemplate(localFilePath + ".zip");
			}
		 }

	public static boolean checkRemoteFileExists(String remoteFilePath, String fileName) throws Exception {
		JSch javaSecureChannel = new JSch();
		Session session = null;
		ChannelExec channel = null;

		try {
			// Create new session
			session = javaSecureChannel.getSession(USERNAME, HOST_4363, PORT_4363);
			session.setPassword(SERVER_PSW);

			// Adjust security settings
			session.setConfig("StrictHostKeyChecking", "yes");

			// Read public key file
			String publicKeyContent = new String(Files.readAllBytes(Paths.get(MOCK_SERVER_KEY_PATH)));
			String[] keyParts = publicKeyContent.split(" ");
			int keyType = HostKey.SSHRSA;
			byte[] keyBytes = Base64.getDecoder().decode(keyParts[1]);

			// Add the server's key to the HostKeyRepository before logging in
			HostKey hostKey = new HostKey(HOST_4363, keyType, keyBytes);
			javaSecureChannel.getHostKeyRepository().add(hostKey, null);

			// Open session
			session.connect();
			System.out.println("session opened");

			// Prepare the command to check for file existence with sudo
			String command = "sudo ls " + remoteFilePath + "/" + fileName;

			// Open a channel for command execution
			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(command);

			// Get the input stream of the channel to read the output of the command
			InputStream in = channel.getInputStream();
			channel.connect();
			System.out.println("Channel opened");

			// Read the output of the command
			byte[] tmp = new byte[1024];
			StringBuilder outputBuffer = new StringBuilder();
			while (true) {
				int len = in.read(tmp, 0, 1024);
				if (len < 0)
					break;
				outputBuffer.append(new String(tmp, 0, len));
			}
			String output = outputBuffer.toString().trim();
			System.out.println("Command output: " + output);

			// Check if the file exists in the output
			boolean fileExists = output.contains(fileName);

			// Disconnect channel and session
			channel.disconnect();
			System.out.println("Channel closed");
			session.disconnect();
			System.out.println("Session closed");

			return fileExists;
		} catch (Exception e) {
			e.printStackTrace();
			if (channel != null && channel.isConnected()) {
				channel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
			throw e;
		}
	}

	public static void downloadFileViaSsh(String remoteFilePath, String fileName) throws Exception {
		JSch javaSecureChannel = new JSch();
		Session session = null;
		ChannelExec channel = null;

		try {
			// Create new session
			session = javaSecureChannel.getSession(USERNAME, HOST_4363, PORT_4363);
			session.setPassword(SERVER_PSW);

			// Adjust security settings
			session.setConfig("StrictHostKeyChecking", "yes");

			// Read public key file
			String publicKeyContent = new String(Files.readAllBytes(Paths.get(MOCK_SERVER_KEY_PATH)));
			String[] keyParts = publicKeyContent.split(" ");
			int keyType = HostKey.SSHRSA;
			byte[] keyBytes = Base64.getDecoder().decode(keyParts[1]);

			// Add the server's key to the HostKeyRepository before logging in
			HostKey hostKey = new HostKey(HOST_4363, keyType, keyBytes);
			javaSecureChannel.getHostKeyRepository().add(hostKey, null);

			// Open session
			session.connect();
			System.out.println("Session opened");

			// Prepare the command to check for file existence with sudo and su
			String checkCommand = "sudo su r2mbbb -c 'ls " + remoteFilePath + "/" + fileName + "'";

			// Open a channel for command execution
			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(checkCommand);

			// Get the input stream of the channel to read the output of the command
			InputStream in = channel.getInputStream();
			channel.connect();
			System.out.println("Channel opened for existence check");

			// Read the output of the command
			byte[] tmp = new byte[1024];
			StringBuilder outputBuffer = new StringBuilder();
			while (true) {
				int len = in.read(tmp, 0, 1024);
				if (len < 0)
					break;
				outputBuffer.append(new String(tmp, 0, len));
			}
			String output = outputBuffer.toString().trim();
			System.out.println("Existence check output: " + output);

			// Check if the file exists in the output
			boolean fileExists = output.contains(fileName);
			System.out.println("File exists check: " + fileExists);

			// Disconnect channel after existence check
			channel.disconnect();
			System.out.println("Channel closed after existence check");

			if (!fileExists) {
				throw new FileNotFoundException("File " + fileName + " does not exist at " + remoteFilePath);
			}

			// Prepare the command to download the file using scp and su
			String downloadCommand = "sudo su r2mbbb -c 'ls " + remoteFilePath + "/" + fileName + "'";

			// Execute the SCP command to download the file
			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(downloadCommand);
			channel.connect();
			System.out.println("Channel opened for download");

			// Read the output of the download command
			try (InputStream downloadIn = channel.getInputStream();
					FileOutputStream fos = new FileOutputStream(ValuesGlobal.LOCAL_TEMP_FOLDER + fileName)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = downloadIn.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
			}

			// Disconnect channel and session
			channel.disconnect();
			System.out.println("Channel closed after download");
			session.disconnect();
			System.out.println("Session closed");

			String message = "File " + fileName + " has been downloaded successfully to "
					+ ValuesGlobal.LOCAL_TEMP_FOLDER;
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
			if (channel != null && channel.isConnected()) {
				channel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
			throw e;
		}
	}

	public static List<String> readFileLines(String fileName) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line); // Add each line to the list
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines; // Return the list of lines
	}

	public static List<String> readFileLines(File downloadFile) {
		List<String> lines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(downloadFile))) {
			String line;
			while ((line = br.readLine()) != null) {
				lines.add(line); // Add each line to the list
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lines; // Return the list of lines
	}

	public static boolean hasLineStartingWith05(File file) {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("05")) {
					return true; // A line starting with "05" was found
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false; // No line starting with "05" was found
	}

	public static void sendJsonToMockServer(String PATH, String PLACEHOLDER_UNIQUE_ID, String UNIQUE_ID,
			String PLACEHOLDER_2, String Replace_2) throws Exception {

		/*
		 * Sending JSON document to Mock-/REST-Server
		 */
		String content_template = new String(Files.readAllBytes(Paths.get(PATH)));
		content_template = content_template.replace(PLACEHOLDER_UNIQUE_ID, UNIQUE_ID);
		String content = content_template.replaceAll(PLACEHOLDER_2, Replace_2);

		/*
		 * Sending JSON document to Mock-/REST-Server
		 */
		openSessionViaSSHAndCreateImportedRecall(content);

		// waiting one minutes for processing of delivered JSON by Mainframe
		System.out.println("1 minute wait...");
		Thread.sleep(ConstantsCommunicationRestServer.ONE_MINUTES);
	}

	public static void sendJsonToMockServerWithoutWait(String PATH, String PLACEHOLDER_UNIQUE_ID, String UNIQUE_ID,
			String PLACEHOLDER_2, String Replace_2) throws Exception {

		/*
		 * Sending JSON document to Mock-/REST-Server
		 */
		String content_template = new String(Files.readAllBytes(Paths.get(PATH)));
		content_template = content_template.replace(PLACEHOLDER_UNIQUE_ID, UNIQUE_ID);
		String content = content_template.replaceAll(PLACEHOLDER_2, Replace_2);

		/*
		 * Sending JSON document to Mock-/REST-Server
		 */
		openSessionViaSSHAndCreateImportedRecall(content);
	}

	public static void downloadFileAlongWithContentViaSsh(String remoteFilePath, String fileName) throws Exception {
		JSch javaSecureChannel = new JSch();
		Session session = null;
		ChannelExec channel = null;

		try {
			// Create new session
			session = javaSecureChannel.getSession(USERNAME, HOST_4363, PORT_4363);
			session.setPassword(SERVER_PSW);

			// Adjust security settings
			session.setConfig("StrictHostKeyChecking", "yes");

			// Read public key file
			String publicKeyContent = new String(Files.readAllBytes(Paths.get(MOCK_SERVER_KEY_PATH)));
			String[] keyParts = publicKeyContent.split(" ");
			int keyType = HostKey.SSHRSA;
			byte[] keyBytes = Base64.getDecoder().decode(keyParts[1]);

			// Add the server's key to the HostKeyRepository before logging in
			HostKey hostKey = new HostKey(HOST_4363, keyType, keyBytes);
			javaSecureChannel.getHostKeyRepository().add(hostKey, null);

			// Open session
			session.connect();
			System.out.println("Session opened");

			// Prepare the command to check for file existence
			String checkCommand = "sudo su r2mbbb -c 'ls " + remoteFilePath + "/" + fileName + "'";

			// Open a channel for command execution
			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(checkCommand);

			// Get the input stream of the channel to read the output of the command
			InputStream in = channel.getInputStream();
			channel.connect();
			System.out.println("Channel opened for existence check");

			// Read the output of the command
			byte[] tmp = new byte[1024];
			StringBuilder outputBuffer = new StringBuilder();
			while (true) {
				int len = in.read(tmp, 0, 1024);
				if (len < 0)
					break;
				outputBuffer.append(new String(tmp, 0, len));
			}
			String output = outputBuffer.toString().trim();
			System.out.println("Existence check output: " + output);

			// Check if the file exists in the output
			boolean fileExists = output.contains(fileName);
			System.out.println("File exists check: " + fileExists);

			// Disconnect channel after existence check
			channel.disconnect();
			System.out.println("Channel closed after existence check");

			if (!fileExists) {
				throw new FileNotFoundException("File " + fileName + " does not exist at " + remoteFilePath);
			}

			// Prepare the command to read file content using cat
			String readFileCommand = "sudo su r2mbbb -c 'cat " + remoteFilePath + "/" + fileName + "'";

			// Open a new channel to read the file content
			channel = (ChannelExec) session.openChannel("exec");
			channel.setCommand(readFileCommand);

			// Get the input stream of the channel to read the file content
			InputStream fileContentStream = channel.getInputStream();
			channel.connect();
			System.out.println("Channel opened for file content retrieval");
			// Write the file content to a local file
			try (FileOutputStream fos = new FileOutputStream(ValuesGlobal.LOCAL_TEMP_FOLDER + fileName)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = fileContentStream.read(buffer)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
			}

			// Disconnect channel and session
			channel.disconnect();
			System.out.println("Channel closed after file content retrieval");
			session.disconnect();
			System.out.println("Session closed");

			String message = "File " + fileName + " has been downloaded successfully to "
					+ ValuesGlobal.LOCAL_TEMP_FOLDER;
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
			if (channel != null && channel.isConnected()) {
				channel.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
			throw e;
		}
	}
}
