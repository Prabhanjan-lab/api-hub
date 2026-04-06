package testCases.readData;

import java.io.IOException;

import testCases.utils.Environments;

public class RecallIDFilePath extends Environments{

	private static String filepath ;
	public static String setRecallIDFilePath() throws IOException {
		String env= Environments.getEnvironment();
		if(env.equalsIgnoreCase("DEMO_ECE")){
			filepath="DemoLastUsedRecallID.txt";
		}
		else if(env.equalsIgnoreCase("APPROVAL_ECE")){
			filepath="ApprovalLastUsedRecallID.txt";
		}
		else {
			System.out.println("Enter Correct Environment");
		}
	return filepath;
	}
	
}
