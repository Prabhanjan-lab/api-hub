package testCases.UNECE_VW_OCU3_StoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.DeapproveBatch;
import testCases.reusable_TestSteps.DefaultBatchStatus;
import testCases.reusable_TestSteps.PendingQueue;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_13_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54611")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")

public class OUQA_54611_VW_OCU3_Trigger_CancelUpdateJobs_AEU1_and_VCSO_5_new2_noJob extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VW_OCU3_CancelUpdateJobs_AEU1_and_VCSO_5_new2_noJob";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(14); // test has fourteen test step
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

		logPath = LogReports.logGeneration(testname, "DeapproveBatch");
		this.setLogPath(logPath);
		DeapproveBatch.assertDeapproveBatch(testname, campaign, criterion, batchName);
		DefaultBatchStatus.assertDefaultBatchStatusCancelled(testname, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus38");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "38");
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
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_OCU3_VW.assertAdviceIncorrectCampaign_OCU3_VW(testname, TestVariables.PutVCHash, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AfterNoCancelUpdateJobSent");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueueNotificationNoCancelUpdateJobSent(testname, vin, campaign);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode4-13");
		this.setLogPath(logPath);
		ResultCode4_13_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PendingQueue");
		this.setLogPath(logPath);
		PendingQueue.assertPendingCampaign(testname, vin, campaign);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed.");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
