package testCases.UNECE_VW_OCU3_StoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueAbortCampaign;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_Package.oCU3_VW.packages_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_13_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54583")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")

public class OUQA_54583_VW_OCU3_Trigger_CancelUpdateJobs_AEU13_and_VCSO_19_new extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VW_OCU3_Trigger_CancelUpdateJobs_AEU13_and_VCSO_19_new";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
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
			this.initTestContainer(13); // test has thirteen test step
		}
	}

	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "Advice");
		Advice_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);

		logPath = LogReports.logGeneration(testname, "Package");
		this.setLogPath(logPath);
		packages_OCU3_VW.assertPackagesRepairDone_OCU3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus19");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "19");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Abort");
		this.setLogPath(logPath);
		ActiveQueueAbortCampaign.abortCampaign(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus39");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "39");
		Thread.sleep(1800000);
		ActiveQueueVCSOStatus.assertActiveQueueNotificationCancelUpdateJobSent(testname, vin, campaign);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode4-13");
		this.setLogPath(logPath);
		ResultCode4_13_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus37");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		System.out.println("TestExecution Complete");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
