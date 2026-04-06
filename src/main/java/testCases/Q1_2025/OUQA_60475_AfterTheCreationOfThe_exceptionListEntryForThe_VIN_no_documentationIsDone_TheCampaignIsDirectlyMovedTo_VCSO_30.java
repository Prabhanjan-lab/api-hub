package testCases.Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.GetVehicleFromCarPort;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3VW_NONUNECE_Carport;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.testStep_Package.oCU3_VW.packages_OCU3VW_NONUNECE_Carport;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode1_OCU3VW_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode1_OCU3VW_NONUNECE_Carport;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-60475")
@Tag("Approval_ECE")
@Tag("25Q1")

public class OUQA_60475_AfterTheCreationOfThe_exceptionListEntryForThe_VIN_no_documentationIsDone_TheCampaignIsDirectlyMovedTo_VCSO_30
		extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "AfterTheCreationOfThe_exceptionListEntryForThe_VIN_no_documentationIsDone_TheCampaignIsDirectlyMovedTo_VCSO_30";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String keystorePath = TestVariables.getKeyStorePath("BASE_Approval_" + vin);
	char[] password = vin.toCharArray();

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(12);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertVCSO5");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport.assertPutVC_OCU3VW_NONUNECE_Carport(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_OCU3VW_NONUNECE_Carport.assertAdviceRepairNecessary_OCU3VW_NONUNECE_Carport(testname,
				TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode1_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Package");
		this.setLogPath(logPath);
		packages_OCU3VW_NONUNECE_Carport.assertPackagesRepairDone_OCU3VW_NONUNECE_Carport(testname, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode1");
		this.setLogPath(logPath);
		ResultCode1_OCU3VW_Carport.assertResultCode_OCU3VW_Carport(testname, TestVariables.PutVCHash, vin,
				campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus30");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "30");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "NoDocumentationIsDone");
		this.setLogPath(logPath);
		GetVehicleFromCarPort.assertVINEmpty(testname, vin);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() throws Throwable {
	}
}
