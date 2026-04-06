package testCases.Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CreateAutoApprovedBatch;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetChargeStatus;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.reusable_TestSteps.PendingQueueVCSOStatus;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.reusable_TestSteps.TestVehicleFalse;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode1_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-2485")
@Tag("Demo_ECE")
@Tag("25Q1")

public class OUQA_2485_01_IMPORTED_RECALL_Automatically_releasing_batch_with_status_created_that_got_a_ReCall_approval_only_conditions
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "IMPORTED_RECALL_Automatically_releasing_batch_with_status_created_that_got_a_ReCall_approval_only_conditions";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String vin1 = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String vin3 = ReadTestParameters.getAttributeValue(testname, "vin3");
	String aaFirstBatchName = ReadTestParameters.getAttributeValue(testname, "aaFirstBatchName");
	String aaSecondBatchName = ReadTestParameters.getAttributeValue(testname, "aaSecondBatchName");
	String firstBatchPredecessorValue = null;
	String secondBatchPredecessorValue = ReadTestParameters.getAttributeValue(testname, "secondBatchPredecessorValue");
	String targetValue = ReadTestParameters.getAttributeValue(testname, "targetValue");
	String firstBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "firstBatchImporterValue");
	String firstBatchMBTvalue = null;
	String firstBatchPknValue = null;
	String secondBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "secondBatchImporterValue");
	String secondBatchMBTValue = null;
	String secondBatchPknValue = null;
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String dummyCampaign = ReadTestParameters.getAttributeValue(testname, "dummyCampaign");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(15);
		}
	}

	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);

		logPath = LogReports.logGeneration(testname, "TestVehicelFalse");
		this.setLogPath(logPath);
		TestVehicleFalse.assertTestVehicleFalse(testname, vin1);
		TestVehicleFalse.assertTestVehicleFalse(testname, vin2);
		TestVehicleFalse.assertTestVehicleFalse(testname, vin3);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CreateORU");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AABatch1withImporter");
		this.setLogPath(logPath);
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, campaign, criterion);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign, criterion, aaFirstBatchName, firstBatchPredecessorValue, targetValue, firstBatchImporterValue, firstBatchMBTvalue, firstBatchPknValue);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CheckVCSO1");
		this.setLogPath(logPath);
		PendingQueueVCSOStatus.assertCampaignInPendingQueue(testname, vin1, campaign);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AABatch2withImporter");
		this.setLogPath(logPath);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign, criterion, aaSecondBatchName, secondBatchPredecessorValue, targetValue, secondBatchImporterValue, secondBatchMBTValue,secondBatchPknValue);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CheckVCSO1");
		this.setLogPath(logPath);
		PendingQueueVCSOStatus.assertCampaignInPendingQueue(testname, vin2, campaign);
		PendingQueueVCSOStatus.assertCampaignInPendingQueue(testname, vin3, campaign);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "RewinddummyCampaign");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin2, dummyCampaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApproveFirstAABatch");
		this.setLogPath(logPath);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, aaFirstBatchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CheckStatusofAA1");
		this.setLogPath(logPath);
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, aaFirstBatchName);
		Thread.sleep(360000);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CheckVCSO5");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin1, campaign, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode1");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin1);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin1);
		ResultCode1_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin1, campaign, criterion);
		this.logStepPassed(logPath);

		Thread.sleep(5000);
		logPath = LogReports.logGeneration(testname, "CheckStatusofAA2");
		this.setLogPath(logPath);
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, aaSecondBatchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CheckVCSO5_2");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, dummyCampaign, "5");
		ScheduledQueue.assertScheduledCampaign(testname, vin2, campaign);
		Thread.sleep(360000);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin3, campaign, "5");
		this.logStepPassed(logPath);

		System.out.println("Test Execution Commpleted.");

	}

	@Override
	protected void tearDownHook() throws Throwable {

	}

}
