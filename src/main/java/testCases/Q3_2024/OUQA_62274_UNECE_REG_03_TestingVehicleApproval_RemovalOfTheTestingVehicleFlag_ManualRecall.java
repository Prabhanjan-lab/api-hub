package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CreateAutoApprovedBatch;
import testCases.reusable_TestSteps.Get_VinsForInspection;
import testCases.reusable_TestSteps.TestVehicleFalse;
import testCases.reusable_TestSteps.TestVehicleTrue;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62274")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62274_UNECE_REG_03_TestingVehicleApproval_RemovalOfTheTestingVehicleFlag_ManualRecall
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "UNECE_REG_03_TestingVehicleApproval_RemovalOfTheTestingVehicleFlag_ManualRecall";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String campaign= ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String firstBatchName=ReadTestParameters.getAttributeValue(testname, "firstBatchName");
	String firstAutoApprovedbatchNameForVinInspection=ReadTestParameters.getAttributeValue(testname,"firstAutoApprovedbatchNameForVinInspection");
	String firstBatchPredecessorValue = null;
	String firstAutoApprovedBatchTargetValue = ReadTestParameters.getAttributeValue(testname, "firstBatchTargetValue");
	String firstBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "firstBatchImporterValue");
	String firstBatchMbtValue = null;
	String firstBatchPknValue = null;
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

		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		LogReports.logGeneration(testname, "OAuthTokenForFirstVin");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIBForFirstVin");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();

		LogReports.logGeneration(testname, "OAuthTokenforsecondVin");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin2);

		LogReports.logGeneration(testname, "UploadIBForSecondVin");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		
		logPath = LogReports.logGeneration(testname, "TestVehicleTrue");
		this.setLogPath(logPath);
		TestVehicleTrue.assertTestVehicleTrue(testname,vin);
		TestVehicleTrue.assertTestVehicleTrue(testname,vin2);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CreateAutoApprovedBatch1");
		this.setLogPath(logPath);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign, criterion, firstBatchName, firstBatchPredecessorValue, firstAutoApprovedBatchTargetValue, firstBatchImporterValue, firstBatchMbtValue, firstBatchPknValue);
		ApproveBatch.approveBatch(testname,campaign,criterion, firstBatchName);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin2,campaign, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "InspectrecallApprovalStatus");
		this.setLogPath(testname);
		Get_VinsForInspection.assertApprovedStatementTestVehicle(testname,vin,campaign,criterion, firstAutoApprovedbatchNameForVinInspection);
		Get_VinsForInspection.assertApprovedStatementTestVehicle(testname,vin2,campaign,criterion, firstAutoApprovedbatchNameForVinInspection);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "TestVehicleFalse");
		this.setLogPath(logPath);
		TestVehicleFalse.assertTestVehicleFalse(testname,vin);
		TestVehicleFalse.assertTestVehicleFalse(testname,vin2);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "recallApprovalStatusForBothVins");
		this.setLogPath(testname);
		Get_VinsForInspection.assertVinApprovedStatusForInspection(testname, vin, campaign, criterion, firstAutoApprovedbatchNameForVinInspection);
		Get_VinsForInspection.assertVinNotApprovedStatusForInsepection(testname, vin2, campaign, criterion, firstAutoApprovedbatchNameForVinInspection);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApprovedVinStatus");
		this.setLogPath(testname);
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin,campaign, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AbortingVinCampaignStatus");
		this.setLogPath(testname);
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin2,campaign, "38");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
