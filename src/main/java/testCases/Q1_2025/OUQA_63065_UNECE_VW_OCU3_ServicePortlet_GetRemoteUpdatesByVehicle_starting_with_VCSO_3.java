package testCases.Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.SoapUI.reusable_TestSteps.currentSPSStatusofCampaign;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueMassNotification;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.RewindCampaignWithoutSkipMassnotification;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_Package.oCU3_VW.packages_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode1_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode1_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode3_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode4_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode7_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode8_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-63065")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("25Q1")

public class OUQA_63065_UNECE_VW_OCU3_ServicePortlet_GetRemoteUpdatesByVehicle_starting_with_VCSO_3 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "UNECE_VW_OCU3_ServicePortlet_GetRemoteUpdatesByVehicle_starting_with_VCSO_3";
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
			this.initTestContainer(16); // test has sixteen test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		this.setLogPath("");     
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithoutSkipMassnotification.assertRewindCampaignWithoutSkipMassnotification(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus3andspsStatus1");
		this.setLogPath(logPath);
		Thread.sleep(360000);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "3");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "1");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CampaignMassNotification");
		this.setLogPath(logPath);
		Thread.sleep(900000);
		ActiveQueueMassNotification.assertActiveQueueMassNotification(testname, vin, campaign,criterion);
		this.logStepPassed(logPath);
		
		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
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

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "1");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode1_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "16");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "1");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Package");
		this.setLogPath(logPath);
		packages_OCU3_VW.assertPackagesRepairDone_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "19");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "3");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode3");
		this.setLogPath(logPath);
		StatusCode3_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "20");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "4");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode4");
		this.setLogPath(logPath);
		StatusCode4_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "41");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode7");
		this.setLogPath(logPath);
		StatusCode7_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "22");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "8");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode8");
		this.setLogPath(logPath);
		StatusCode8_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "24");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "9");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode1");
		this.setLogPath(logPath);
		ResultCode1_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "30");
		currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, campaign, criterion, "10");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
