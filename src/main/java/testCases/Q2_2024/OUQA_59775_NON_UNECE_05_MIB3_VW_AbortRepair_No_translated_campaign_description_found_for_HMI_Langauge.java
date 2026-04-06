package testCases.Q2_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.testStep_Advice.mIB3_VW.Advice_notranslation_MIB3VW_ODP;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW_ODP;
import testCases.testStep_PutVC.mIB3_VW.PutVC_RepairNecessary_MIB3VW_ODP;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-59775")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q2")

public class OUQA_59775_NON_UNECE_05_MIB3_VW_AbortRepair_No_translated_campaign_description_found_for_HMI_Langauge extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "NON_UNECE_MIB3VW_NoTranslation";
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
			this.initTestContainer(7); // test has seven test step
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

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_MIB3VW_ODP.assertPutVC_MIB3VW_ODP(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_notranslation_MIB3VW_ODP.assertAdviceRepairNecessary_MIB3VW_ODP(testname, vin, campaign, criterion);
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
