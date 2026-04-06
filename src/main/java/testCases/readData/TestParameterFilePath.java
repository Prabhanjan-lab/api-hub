package testCases.readData;

import java.io.IOException;

import testCases.utils.Environments;

public class TestParameterFilePath extends Environments{

	private static String filepath ;
	public static String setTestParamerterFilePath() throws IOException {

		String env= Environments.getEnvironment();
		String os = System.getProperty("os.name");
		os = os.toLowerCase();

		if (os.contains("linux")) {
			if(env.equalsIgnoreCase("DEMO_ECE")){
				filepath="TestParameters/DemoTestdata.json";
			}
			else if(env.equalsIgnoreCase("APPROVAL_ECE")){
				filepath="TestParameters/ApprovalTestdata.json";
			}
			else {
				System.out.println("Enter Correct Environment");
			}
		} else if (os.contains("windows")) {
			if(env.equalsIgnoreCase("DEMO_ECE")){
				filepath="TestParameters\\DemoTestdata.json";
			}
			else if(env.equalsIgnoreCase("APPROVAL_ECE")){
				filepath="TestParameters\\ApprovalTestdata.json";
			}
			else {
				System.out.println("Enter Correct Environment");
			}
		}

		return filepath;
	}
}