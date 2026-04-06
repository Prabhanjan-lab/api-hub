package testCases.a_Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-28242123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
public class A_Retest_Bug_OU_4395_Current_Blocked_Vehicles_Error_Code_Filtering extends AbstractStandardBehaviour{


	String logPath = "";
	String testname = "Current_Blocked_Vehicles_Error_Code_Filtering";
	String vin1 = ReadTestParameters.getAttributeValue(testname, "vin1");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String vin3 = ReadTestParameters.getAttributeValue(testname, "vin3");
	String vin4 = ReadTestParameters.getAttributeValue(testname, "vin4");
	String vin5= ReadTestParameters.getAttributeValue(testname, "vin5");
	String vin6 = ReadTestParameters.getAttributeValue(testname, "vin6");
	String vin7 = ReadTestParameters.getAttributeValue(testname, "vin7");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(8); // test has one test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin1);
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin1, campaign, criterion);
		this.logStepPassed(logPath);
		
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin2);
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin2, campaign, criterion);
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin3);
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin3, campaign, criterion);
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin4);
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin4, campaign, criterion);
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin5);
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin5, campaign, criterion);
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin6);
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin6, campaign, criterion);
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin7);
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin7, campaign, criterion);
		this.logStepPassed(logPath);
		
		this.setLogPath("");
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}



}
