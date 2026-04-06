package testCases.Q2_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW_NONUNECE;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_NONUNECE;
import testCases.testStep_Package.oCU3_VW.packages_OCU3_VW_NONUNECE;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-59795")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q2")

public class OUQA_59795_ODP_NON_UNECE_VW_OCU3_07_AbortRepair_Campaign_not_active extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "NON_UNECE_VWOCU3_CampaignNotActive";
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
			this.initTestContainer(9); // test has eight test step
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
		UploadInstallbase_OCU3_VW_NONUNECE.assertInstallbase_OCU3VW_NONUNECE();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW_NONUNECE.assertPutVC_OCU3VW_NONUNECE(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AfterAdviceVCSOStatus37");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_OCU3_VW_NONUNECE.assertAdviceAbortRepair_OCU3_VW_NONUNECE(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AfterAdviceVCSOStatus37");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Package");
		this.setLogPath(logPath);
		packages_OCU3_VW_NONUNECE.assertPackagesCampaignNotActive_OCU3VW_NONUNECE(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AfterPackageVCSOStatus37");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
