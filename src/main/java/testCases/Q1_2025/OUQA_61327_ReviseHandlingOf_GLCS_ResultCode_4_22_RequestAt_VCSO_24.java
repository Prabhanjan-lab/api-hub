package testCases.Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3VW_NONUNECE_Carport;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.testStep_Package.oCU3_VW.packages_OCU3VW_NONUNECE_Carport;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_22_OCU3VW_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode1_OCU3VW_NONUNECE_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode3_OCU3VW_NONUNECE_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode4_OCU3VW_NONUNECE_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode7_OCU3VW_NONUNECE_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode8_OCU3VW_NONUNECE_Carport;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-61327")
@Tag("Approval_ECE")
@Tag("25Q1")

public class OUQA_61327_ReviseHandlingOf_GLCS_ResultCode_4_22_RequestAt_VCSO_24 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "ReviseHandlingOf_GLCS_ResultCode_4_22_RequestAt_VCSO_24";
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
			this.initTestContainer(16);
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
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);
		this.logStepPassed(logPath);

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

		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode3");
		this.setLogPath(logPath);
		StatusCode3_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode4");
		this.setLogPath(logPath);
		StatusCode4_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode7");
		this.setLogPath(logPath);
		StatusCode7_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode8");
		this.setLogPath(logPath);
		StatusCode8_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus24");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "24");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport.assertPutVC_OCU3VW_NONUNECE_Carport(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode4-22");
		this.setLogPath(logPath);
		ResultCode4_22_OCU3VW_Carport.assertResultCode_OCU3VW_Carport(testname, TestVariables.PutVCHash, vin, campaign,
				criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "46");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() throws Throwable {
	}
}
