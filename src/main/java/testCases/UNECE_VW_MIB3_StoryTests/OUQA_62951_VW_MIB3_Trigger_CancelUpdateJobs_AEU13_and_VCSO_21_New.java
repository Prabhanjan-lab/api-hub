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
import testCases.testStep_Package.mIB3_VW.packages_MIB3VW;
import testCases.testStep_PutVC.mIB3_VW.PutVC_RepairNecessary_MIB3VW;
import testCases.testStep_ResultCode.mIB3_VW.ResultCode4_13_MIB3VW;
import testCases.testStep_StatusCode.mIB3_VW.StatusCode1_MIB3VW;
import testCases.testStep_StatusCode.mIB3_VW.StatusCode6_MIB3VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62951")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")

public class OUQA_62951_VW_MIB3_Trigger_CancelUpdateJobs_AEU13_and_VCSO_21_New extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VWMIB3_CancelUpdateJobs_AEU13_and_VCSO_21_New";
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
		} finally {
			initTestContainer(12); // test has twelve steps
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
		UploadInstalbase_MIB3_VW.assertInstallbase_MIB3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_MIB3VW.assertPutVC_MIB3VW(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_MIB3VW.assertAdviceRepairNecessary_MIB3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode1_MIB3VW.assertStatusCode_MIB3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "16");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Packages");
		this.setLogPath(logPath);
		packages_MIB3VW.assertPackagesRepairDone_MIB3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "19");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode6");
		this.setLogPath(logPath);
		StatusCode6_MIB3VW.assertStatusCode_MIB3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "21");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Abort");
		this.setLogPath(logPath);
		ActiveQueueAbortCampaign.assertAbortCampaign(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "39");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode1");
		this.setLogPath(logPath);
		ResultCode4_13_MIB3VW.assertResultCode_MIB3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		// ResultCode1_MIB3VW.assertResultCode_MIB3VW(testname,
		// TestVariables.PutVCHash);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus37");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed.");

	}

	@Override
	protected void tearDownHook() throws Throwable {

	}
}
