package testCases.Q2_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.testStep_Advice.mIB3_VW.Advice_MIB3VW_ODP;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW_ODP;
import testCases.testStep_Package.mIB3_VW.packages_MIB3VW_ODP;
import testCases.testStep_PutVC.mIB3_VW.PutVC_RepairNecessary_MIB3VW_ODP;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-17808")
@Tag("Demo-ECE")
@Tag("Approval_ECE")
@Tag("24Q2")
public class OUQA_17808_LS_07_Passat_MIB3_AbortRepair_CampaignNotActive extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "Passat_MIB3_AbortRepair_CampaignNotActive";
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
			this.initTestContainer(10); // test has ten test step
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
		PutVC_RepairNecessary_MIB3VW_ODP.assertPutVC_MIB3VW_ODP(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CampaignStatusAfterPackages");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_MIB3VW_ODP.assertAdviceAbortRepair_MIB3VW_ODP(testname, TestVariables.PutVCHash, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CampaignStatusAfterAdvice");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Packages");
		this.setLogPath(logPath);
		packages_MIB3VW_ODP.assertPackagesCampaignNotActive_MIB3VW_ODP(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CampaignStatusAfterPackages");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		this.logStepPassed(logPath);

		System.out.println("Test Execution Completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
