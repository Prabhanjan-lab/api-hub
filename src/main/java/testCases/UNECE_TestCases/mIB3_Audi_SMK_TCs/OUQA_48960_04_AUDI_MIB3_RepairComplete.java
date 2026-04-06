package testCases.UNECE_TestCases.mIB3_Audi_SMK_TCs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.testStep_Advice.mIB3_Audi.Advice_MIB3Audi;
import testCases.testStep_Installbase.mIB3_Audi.UploadInstallbase_MIB3_Audi;
import testCases.testStep_PutVC.mIB3_Audi.PutVC_RepairComplete_MIB3Audi;
import testCases.testStep_ResultCode.mIB3_Audi.ResultCode2_MIB3Audi;
import testCases.testStep_StatusCode.mIB3_Audi.StatusCode1_MIB3Audi;
import testCases.testStep_StatusCode.mIB3_Audi.StatusCode8_MIB3Audi;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-48960")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")
public class OUQA_48960_04_AUDI_MIB3_RepairComplete extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "MIB3Audi_RepairComplete";
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
			this.initTestContainer(12); // test has twelve test step
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
		UploadInstallbase_MIB3_Audi.assertInstallbase_MIB3Audi();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairComplete_MIB3Audi.assertPutVC_MIB3Audi(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_MIB3Audi.assertAdviceRepairComplete_MIB3Audi(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus12");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode1_MIB3Audi.assertStatusCode_MIB3Audi(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus16");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "16");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode8");
		this.setLogPath(logPath);
		StatusCode8_MIB3Audi.assertStatusCode_MIB3Audi(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode2");
		this.setLogPath(logPath);
		ResultCode2_MIB3Audi.assertResultCode_MIB3Audi(testname, TestVariables.PutVCHash, vin, campaign, criterion);
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
