package testCases.Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.Get_VinsForInspection;
import testCases.reusable_TestSteps.JsonRolloutDateUpdater;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.reusable_TestSteps.PendingQueue;
import testCases.reusable_TestSteps.TestExecutionScheduler;
import testCases.reusable_TestSteps.TestVehicleFalse;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-63129")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")
@Tag("25Q1")

public class OUQA_63129_UNECE_REG_TestVehicle_ShouldBeReleased_AfterRolloutdateIsReached
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "UNECE_REG_TestVehicle_ShouldBeReleased_AfterRolloutdateIsReached";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = "campaign";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String Default = ReadTestParameters.getAttributeValue(testname, "Default");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String defaultBatchForVinInspection = ReadTestParameters.getAttributeValue(testname,
			"defaultBatchForVinInspection");
	String filePath = "data/importReCall/recall-ouqa-63129-a-minimal-data-record.json";

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(11);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		String RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		logPath = LogReports.logGeneration(testname, "ImportFirstRecallWithFutureRolloutDate");
		JsonRolloutDateUpdater.updateToTomorrowDate(filePath);
		CreateImportedRecall.createImportedRecall(RECALL_ID, "recall-ouqa-63129-a-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(this.campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		logPath = LogReports.logGeneration(testname, "CreateORUCampaign");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname, campaign, criterion);
		Thread.sleep(360000);
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "recallApprovalStatus");
		this.setLogPath(testname);
		TestVehicleFalse.assertTestVehicleFalse(testname, vin);
		Get_VinsForInspection.assertVinNotApprovedStatusForInsepection(testname, vin, campaign, criterion,
				defaultBatchForVinInspection);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "recallApprovalStatus");
		this.setLogPath(testname);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus1");
		this.setLogPath(logPath);
		PendingQueue.assertPendingCampaign(testname, vin, campaign);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "WaitUntilRolloutDateIsReached");
		this.setLogPath(logPath);
		TestExecutionScheduler.waitUntilNextDayAndRunTest();
		Thread.sleep(120000);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "recallApprovalStatus_Approved");
		this.setLogPath(testname);
		Get_VinsForInspection.assertVinApprovedStatusForInspection(testname, vin, campaign, criterion,
				defaultBatchForVinInspection);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus5");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}