package testCases.UNECE_Q1_2024.VW_OCU3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.testStep_Advice.mIB3_VW.Advice_MIB3VW;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW;
import testCases.testStep_PutVC.mIB3_VW.PutVC_WrongVariant_MIB3VW;
import testCases.testStep_ResultCode.mIB3_VW.ResultCode3_MIB3VW;
import testCases.testStep_ResultCode.mIB3_VW.ResultCode4_13_MIB3VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-60166")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q1")
public class OUQA_60166_UNECE_REG_VCSO_38_Variant_mismatch_result_4_13_VCSO_46 extends AbstractStandardBehaviour {
	String testname = "VWMIB3_UNECE_REG_VCSO_38_Variant_mismatch_result_4_13_VCSO_46";
	String logPath = "";
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
			this.initTestContainer(8);
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
		UploadInstalbase_MIB3_VW.assertInstallbase_MIB3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "putVCWithWrongVariant");
		this.setLogPath(logPath);
		PutVC_WrongVariant_MIB3VW.assertPutVC_MIB3VW(testname, "M2P-H-CON-13--EU-AU-MLB-AL", vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_MIB3VW.assertAdviceWrongVariant_MIB3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode4-13");
		this.setLogPath(logPath);
		ResultCode4_13_MIB3VW.assertResultCode_MIB3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSO46");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "46");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode3-12");
		this.setLogPath(logPath);
		ResultCode3_MIB3VW.assertResultCode_MIB3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "46");
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() {

	}

}
