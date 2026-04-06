package testCases.UNECE_Q1_2024.VW_OCU3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.BlockedVinStatus;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_Package.oCU3_VW.packages_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode3_12_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode1_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode3_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode4_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode7_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode8_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-57278")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q1")
public class OUQA_57278_UNECE_After_unsuccessful_installation_campaign_in_blocked_state
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "VWOCU3_UNECE_After_unsuccessful_installation_campaign_in_blocked_state";
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
			this.initTestContainer(18); // test has seventeen test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

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
		Advice_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus12");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode1_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus16");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "16");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Package");
		this.setLogPath(logPath);
		packages_OCU3_VW.assertPackagesRepairDone_OCU3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus19");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "19");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode3");
		this.setLogPath(logPath);
		StatusCode3_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus20");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "20");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode4");
		this.setLogPath(logPath);
		StatusCode4_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode7");
		this.setLogPath(logPath);
		StatusCode7_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode8");
		this.setLogPath(logPath);
		StatusCode8_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode3-12");
		this.setLogPath(logPath);
		ResultCode3_12_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus34");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "34");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VinBlockedStatus");
		this.setLogPath(logPath);
		BlockedVinStatus.assertBlockedVinStatus(testname, campaign, criterion);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
