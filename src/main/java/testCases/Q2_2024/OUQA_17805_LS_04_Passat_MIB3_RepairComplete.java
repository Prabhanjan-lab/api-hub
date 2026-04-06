package testCases.Q2_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.testStep_Advice.mIB3_VW.Advice_MIB3VW_ODP;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW_ODP;
import testCases.testStep_PutVC.mIB3_VW.PutVC_RepairComplete_MIB3VW_ODP;
import testCases.testStep_ResultCode.mIB3_VW.ResultCode2_MIB3VW;
import testCases.testStep_StatusCode.mIB3_VW.StatusCode1_MIB3VW_ODP;
import testCases.testStep_StatusCode.mIB3_VW.StatusCode3_MIB3VW_ODP;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-17805")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q2")
public class OUQA_17805_LS_04_Passat_MIB3_RepairComplete extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "Passat_MIB3_RepairComplete";
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

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstalbase_MIB3_VW_ODP.assertInstallbase_MIB3VW_ODP();
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairComplete_MIB3VW_ODP.assertPutVC_MIB3VW_ODP(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_MIB3VW_ODP.assertAdviceRepairComplete_MIB3VW_ODP(testname, TestVariables.PutVCHash, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus12");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode1_MIB3VW_ODP.assertStatusCode_MIB3VW_ODP(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus16");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "16");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode3");
		this.setLogPath(logPath);
		StatusCode3_MIB3VW_ODP.assertStatusCode_MIB3VW_ODP(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus20");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "20");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode2");
		this.setLogPath(logPath);
		ResultCode2_MIB3VW.assertResultCode_MIB3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus30");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "30");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
