package testCases.Q4_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_Package.oCU3_VW.packageWithUnknownVC_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode3_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-79727")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("25Q4")

public class OUQA_79727_UNECE_REG_package_with_unknown_VC_Response_has_to_be_HTTP_404
		extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VWOCU3_package_with_unknown_VC_Response_has_to_be_HTTP_404";
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
			this.initTestContainer(14); // test has seven test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "IncorrectVCforAdvice");
		this.setLogPath(logPath);
		Advice_OCU3_VW.assertAdviceWithUnknownVC_OCU3_VW(testname, TestVariables.PutVC2Hash, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "ForceAbrotAndRewindCampaign");
		this.setLogPath(logPath);
		ForceAbort.assertForceAbort(testname, vin, campaign, criterion);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "OauthAndUploadIB");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "PutVCforIncorrectPackages");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AdviceforIncorrectPackages");
		this.setLogPath(logPath);
		Advice_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "IncorrectVCforPackage");
		this.setLogPath(logPath);
		packageWithUnknownVC_OCU3_VW.assertPackageWithUnknownVC_OCU3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "ForceAbrotAndRewindCampaign");
		this.setLogPath(logPath);
		ForceAbort.assertForceAbort(testname, vin, campaign, criterion);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "OauthAndUploadIBForResultCode");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "PutVCforIncorrectResultCode");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "IncorrectVCforResultCode3");
		this.setLogPath(logPath);
		ResultCode3_OCU3_VW.assertResultCode3_OCU3VW(testname, TestVariables.PutVC2Hash, vin, campaign, criterion);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
