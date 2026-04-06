package testCases.reusable_TestSteps;

import java.nio.file.Files;
import java.nio.file.Paths;

import testCases.util.ConstantsReCallImports;
import testFramework.constants.ConstantsCommunicationRestServer;
import testFramework.utilities.recall.SshConnection;

public class CreateImportedRecall {
	public static void createImportedRecall(String recallID,String path) throws Exception {
		
		String PATH = ConstantsCommunicationRestServer.getPath(path);
        String content = new String(Files.readAllBytes(Paths.get(PATH)));
        content = content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, recallID);        

//      Sending JSON document to Mock-/REST-Server
         
        SshConnection.openSessionViaSSHAndCreateImportedRecall(content);
        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);

	}
	
	public static void createImportedRecall2(String recallID,String path) throws Exception {
		
		String PATH = ConstantsCommunicationRestServer.getPath(path);
        String content = new String(Files.readAllBytes(Paths.get(PATH)));
        content = content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, recallID);        

//      Sending JSON document to Mock-/REST-Server
         
        SshConnection.openSessionViaSSHAndCreateImportedRecall2(content);
        System.out.println("Waiting for recall import to admin portlet.");
        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);

	}
}
