package testCases.reusable_TestSteps;

import java.nio.file.Files;
import java.nio.file.Paths;

import testCases.util.ConstantsReCallImports;
import testFramework.constants.ConstantsCommunicationRestServer;
import testFramework.utilities.recall.SshConnection;

public class ImportRecallWithFaultyFields {
	public static void createImportedRecallWithFaultyfields(String recallID,String path,String targetLine,String subText,String faultyCode,int faultyCodeIndex,int faultyCodeLength) throws Exception {
		
		String PATH = ConstantsCommunicationRestServer.getPath(path);
        String content = new String(Files.readAllBytes(Paths.get(PATH)));
        content = content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, recallID);        

//      Sending JSON document to Mock-/REST-Server
         
        SshConnection.openSessionViaSSHAndCreateImportedRecallWithFaultyFields(content,targetLine,subText,faultyCode,faultyCodeIndex,faultyCodeLength);
        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);

	}
	
public static void createImportedRecall2WithFaultyfields(String recallID,String path,String targetLine,String subText,String faultyCode,int faultyCodeIndex,int faultyCodeLength) throws Exception {
		
		String PATH = ConstantsCommunicationRestServer.getPath(path);
        String content = new String(Files.readAllBytes(Paths.get(PATH)));
        content = content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, recallID);        

//      Sending JSON document to Mock-/REST-Server
         
        SshConnection.openSessionViaSSHAndCreateImportedRecall2WithFaultyFields(content,targetLine,subText,faultyCode,faultyCodeIndex,faultyCodeLength);
        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);

	}


}
