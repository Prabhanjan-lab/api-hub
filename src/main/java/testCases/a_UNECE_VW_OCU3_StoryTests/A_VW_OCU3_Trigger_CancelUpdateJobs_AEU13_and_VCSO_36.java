package testCases.a_UNECE_VW_OCU3_StoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.RewindCampaignWithoutSkipMassnotification;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54587123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class A_VW_OCU3_Trigger_CancelUpdateJobs_AEU13_and_VCSO_36 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VW_OCU3_CancelUpdateJobs_AEU13_and_VCSO_36";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(2); // test has two test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin2);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();

		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithoutSkipMassnotification.assertRewindCampaignWithoutSkipMassnotification(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
		RewindCampaignWithoutSkipMassnotification.assertRewindCampaignWithoutSkipMassnotification(testname, vin2, campaign, criterion);
		Thread.sleep(360000);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "3");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "3");
		Thread.sleep(3600000);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "5");
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
