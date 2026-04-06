package testCases.UNECE_VW_MIB3_StoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueAbortCampaign;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.testStep_Advice.mIB3_VW.Advice_MIB3VW;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW;
import testCases.testStep_PutVC.mIB3_VW.PutVC_RepairNecessary_MIB3VW;
import testCases.testStep_ResultCode.mIB3_VW.ResultCode4_13_MIB3VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54592")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")

public class OUQA_54592_VW_MIB3_Trigger_CancelUpdateJobs_AEU13_VCSO_5_new extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VWMIB3_Trigger_CancelUpdateJobs_AEU13_VCSO_5_new";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	// TODO : setUp notwendig?
	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
		} finally {
			initTestContainer(11); // test has eleven steps
		}
	}

	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Abort");
		this.setLogPath(logPath);
		ActiveQueueAbortCampaign.assertAbortCampaign(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "39");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "NoCancelUpdateJobSent");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueueNotificationNoCancelUpdateJobSent(testname, vin, campaign);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstalbase_MIB3_VW.assertInstallbase_MIB3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_MIB3VW.assertPutVC_MIB3VW(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_MIB3VW.assertAdviceAbortRepairVCSO38_MIB3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "39");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AfterNoCancelUpdateJobSent");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueueNotificationNoCancelUpdateJobSent(testname, vin, campaign);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode4-13");
		this.setLogPath(logPath);
		ResultCode4_13_MIB3VW.assertResultCode_MIB3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CampaignHistoryVCSO37");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed.");
	}

	@Override
	protected void tearDownHook() throws Throwable {

	}
}
