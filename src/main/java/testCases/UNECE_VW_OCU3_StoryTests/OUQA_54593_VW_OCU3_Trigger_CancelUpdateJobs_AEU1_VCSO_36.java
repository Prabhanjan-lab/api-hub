package testCases.UNECE_VW_OCU3_StoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.DeapproveBatch;
import testCases.reusable_TestSteps.DefaultBatchStatus;
import testCases.testStep_Advice.oCU3_VW.Advice_notranslation_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54593")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")

public class OUQA_54593_VW_OCU3_Trigger_CancelUpdateJobs_AEU1_VCSO_36 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VW_OCU3_CancelUpdateJobs_AEU1_VCSO_36";
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
			this.initTestContainer(10);
		}
	}

	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
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
		Advice_notranslation_OCU3_VW.assertAdviceAbortRepair_OCU3_VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus36");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "36");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "DeapproveBatch");
		this.setLogPath(logPath);
		DeapproveBatch.assertDeapproveBatch(testname, campaign, criterion, batchName);
		DefaultBatchStatus.assertDefaultBatchStatusCancelled(testname, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CampaignInActiveQueue");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus36");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "36");
		ActiveQueueVCSOStatus.assertActiveQueueNotificationNoCancelUpdateJobSent(testname, vin, campaign);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() {

	}
}
