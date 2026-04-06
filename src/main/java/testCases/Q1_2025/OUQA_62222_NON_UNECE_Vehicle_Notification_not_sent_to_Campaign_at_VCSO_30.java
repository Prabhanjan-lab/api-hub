package testCases.Q1_2025;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Tag;


import testCases.SoapUI.reusable_TestSteps.NotifyVehicleCampaign;
import testCases.SoapUI.reusable_TestSteps.currentSPSStatusofCampaign;
import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.CarportLifeCycleSucessfull;
import testCases.reusable_TestSteps.GetDetailsofVCA;
import testCases.reusable_TestSteps.GetVehicleFromCarPort;
import testCases.reusable_TestSteps.RecallDocumentationSuccessfull;
import testCases.reusable_TestSteps.getRecallDocumentation;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3VW_NONUNECE_Carport;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.testStep_Package.oCU3_VW.packages_OCU3VW_NONUNECE_Carport;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode1_OCU3VW_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode1_OCU3VW_NONUNECE_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode3_OCU3VW_NONUNECE_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode4_OCU3VW_NONUNECE_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode7_OCU3VW_NONUNECE_Carport;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode8_OCU3VW_NONUNECE_Carport;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62222")
@Tag("Approval_ECE")
@Tag("25Q1")

public class OUQA_62222_NON_UNECE_Vehicle_Notification_not_sent_to_Campaign_at_VCSO_30 extends AbstractStandardBehaviour{
	
	String logPath = "";

	String testname = "NON_UNECE_Vehicle_Notification_not_sent_to_Campaign_at_VCSO_30";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String notifyVehicleStatus = ReadTestParameters.getAttributeValue(testname, "notifyVehicleStatus");
	String notifyVehicleMessage = ReadTestParameters.getAttributeValue(testname, "notifyVehicleMessage");
	String expectedReason = "1_CUSTOMER_NOTIFIED";
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
			this.initTestContainer(26); 
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

		logPath = LogReports.logGeneration(testname, "VCSOStatus5");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "5");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "PUTVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport.assertPutVC_OCU3VW_NONUNECE_Carport(testname, vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_OCU3VW_NONUNECE_Carport.assertAdviceRepairNecessary_OCU3VW_NONUNECE_Carport(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode1_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "16");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Packages");
		this.setLogPath(logPath);
		packages_OCU3VW_NONUNECE_Carport.assertPackagesRepairDone_OCU3VW_NONUNECE_Carport(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "19");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "CustomerNotified");
		this.setLogPath(logPath);
		getRecallDocumentation.assertgetRecallDocumentation(testname, vin, campaign, expectedReason);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "StatusCode3");
		this.setLogPath(logPath);
		StatusCode3_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "20");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "StatusCode4");
		this.setLogPath(logPath);
		StatusCode4_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "41");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "StatusCode7");
		this.setLogPath(logPath);
		StatusCode7_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "22");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "StatusCode8");
		this.setLogPath(logPath);
		StatusCode8_OCU3VW_NONUNECE_Carport.assertStatusCode_OCU3VW_NONUNECE_Carport(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "24");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "ResultCode1");
		this.setLogPath(logPath);
		ResultCode1_OCU3VW_Carport.assertResultCode_OCU3VW_Carport(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "26");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "VSCO42");
		this.setLogPath(logPath);
		Thread.sleep(60000);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "42");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "GetVehicleFromCarport");
		this.setLogPath(logPath);
		GetVehicleFromCarPort.assertGetVehicleFromCarPortDetails(testname, vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "MarkCarportSucessful");
		this.setLogPath(logPath);
		CarportLifeCycleSucessfull.assertCarportLifeCycleSuccessful(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "CheckVCSO28&43");
		this.setLogPath(logPath);
		GetDetailsofVCA.assertVCSOfromVCADetails(testname, campaign, vin, criterion, "28", "A1000");
		Thread.sleep(240000);
		GetDetailsofVCA.assertVCSOfromVCADetails(testname, campaign, vin, criterion, "43", "A3000");
		RecallDocumentationSuccessfull.assertRecallLifeCycleDocumentationSuccessful(testname, vin, campaign, criterion,"2_DONE_BY_ORU");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "CheckVCSO30");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "30");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "GetRemoteUpdateByVehicle");
		this.setLogPath(logPath);
		currentSPSStatusofCampaign.assertIsNotifyPossibleFalse(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "NotifyVehicleCampaign");
		this.setLogPath(logPath);
		NotifyVehicleCampaign.assertNotifyVehicleStatusNOK(testname, vin, campaign, criterion, notifyVehicleStatus, notifyVehicleMessage);
		this.logStepPassed(logPath);
		
		//For Approval We don't implemented Log Check.
		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);
		System.out.println("Test Execution Completed.");
		
	}
	
	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
