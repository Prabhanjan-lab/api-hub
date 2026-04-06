package testCases.Q2_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.testStep_Advice.oCU3_VW.Advice_notranslation_OCU3_VW_NONUNECE;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_NONUNECE;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-59777")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q2")

public class OUQA_59777_ODP_NON_UNECE_VW_OCU3_05_AbortRepair_No_translated_campaign_description_found_for_HMI_language
		extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "NON_UNECE_VWOCU3_NoTranslation";
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
		UploadInstallbase_OCU3_VW_NONUNECE.assertInstallbase_OCU3VW_NONUNECE();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW_NONUNECE.assertPutVC_OCU3VW_NONUNECE(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_notranslation_OCU3_VW_NONUNECE.assertAdviceAbortRepair_OCU3_VW_NONUNECE(testname, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus36");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "36");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ForceAbort");
		this.setLogPath(logPath);
		ForceAbort.assertForceAbort(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() {

	}

}
