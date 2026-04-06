package testCases.Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CreateManualBatch1;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.reusable_TestSteps.Get_VinsForInspection;
import testCases.reusable_TestSteps.TestVehicleFalse;
import testCases.reusable_TestSteps.TestVehicleTrue;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62271")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_62271_RECALL2_UNECE_REG_03_TestingVehicleApproval_RemovalOfTheTestingVehicleFlag_ImportRecall
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "RECALL2_UNECE_REG_03_TestingVehicleApproval_RemovalOfTheTestingVehicleFlag_ImportRecall";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String firstChargeName = ReadTestParameters.getAttributeValue(testname, "firstChargeName");
	String firstManualbatchNameForVinInspection = ReadTestParameters.getAttributeValue(testname,
			"firstManualbatchNameForVinInspection");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(8);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		logPath = LogReports.logGeneration(testname, "CreateORUCampaign");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname, campaign, criterion);
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "TestVehicleTrue");
		this.setLogPath(logPath);
		TestVehicleTrue.assertTestVehicleTrue(testname, vin);
		TestVehicleTrue.assertTestVehicleTrue(testname, vin2);
		this.logStepPassed(logPath);
		
		LogReports.logGeneration(testname, "GenerateOauthTokenForFirstVin");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		
		LogReports.logGeneration(testname, "UploadIBForFirstVin");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		
		LogReports.logGeneration(testname, "PutVCForFirstVin");
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname,vin);
		
		LogReports.logGeneration(testname, "GenerateOauthTokenForSecondVin");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin2);
		
		LogReports.logGeneration(testname, "UploadIBForSecondVin");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		
		LogReports.logGeneration(testname, "PutVCForSecondVin");
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname,vin2);
		
		logPath = LogReports.logGeneration(testname, "CreateManualBatch1");
		this.setLogPath(logPath);
		
		CreateManualBatch1.assertManualBatchCreation(testname, campaign, criterion);
		ApproveBatch.approveBatch(testname, campaign, criterion, firstChargeName);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "recallApprovalStatus");
		this.setLogPath(testname);
		Get_VinsForInspection.assertApprovedStatementTestVehicle(testname, vin, campaign, criterion,
				firstManualbatchNameForVinInspection);
		Get_VinsForInspection.assertApprovedStatementTestVehicle(testname, vin2, campaign, criterion,
				firstManualbatchNameForVinInspection);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "TestVehicleFalse");
		this.setLogPath(logPath);
		TestVehicleFalse.assertTestVehicleFalse(testname, vin);
		TestVehicleFalse.assertTestVehicleFalse(testname, vin2);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "recallApprovalStatus");
		this.setLogPath(testname);
		Get_VinsForInspection.assertVinApprovedStatusForInspection(testname, vin, campaign, criterion,
				firstManualbatchNameForVinInspection);
		Get_VinsForInspection.assertVinNotApprovedStatusForInsepection(testname, vin2, campaign, criterion,
				firstManualbatchNameForVinInspection);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApprovedVinStatus");
		this.setLogPath(testname);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AbortingVinCampaignStatus");
		this.setLogPath(testname);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "38");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
