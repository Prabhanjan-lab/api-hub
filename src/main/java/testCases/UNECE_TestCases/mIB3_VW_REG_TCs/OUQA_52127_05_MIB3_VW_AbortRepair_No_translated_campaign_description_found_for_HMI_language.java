package testCases.UNECE_TestCases.mIB3_VW_REG_TCs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.testStep_Advice.mIB3_VW.Advice_notranslation_MIB3VW;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW;
import testCases.testStep_PutVC.mIB3_VW.PutVC_RepairNecessary_MIB3VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-52127")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")
public class OUQA_52127_05_MIB3_VW_AbortRepair_No_translated_campaign_description_found_for_HMI_language
		extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "MIB3VW_NoTranslation";
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
			this.initTestContainer(7); // test has six test step
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

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_MIB3VW.assertPutVC_MIB3VW(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_notranslation_MIB3VW.assertAdviceRepairNecessary_MIB3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus36");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "36");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ForceAbort");
		this.setLogPath(logPath);
		ForceAbort.assertForceAbort(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		System.out.println("Test Execution Completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
