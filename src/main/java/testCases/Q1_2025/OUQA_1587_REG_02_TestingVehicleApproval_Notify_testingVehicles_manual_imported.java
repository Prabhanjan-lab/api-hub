package testCases.Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.GetChargeStatus;
import testCases.reusable_TestSteps.Get_VinsForInspection;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.reusable_TestSteps.TestVehicleTrue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-1587")
@Tag("Demo_ECE")
@Tag("25Q1")

public class OUQA_1587_REG_02_TestingVehicleApproval_Notify_testingVehicles_manual_imported
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "TestingVehicleApproval_Notify_testingVehicles_manual_imported";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String defaultBatchForVinInspection = ReadTestParameters.getAttributeValue(testname,
			"defaultBatchForVinInspection");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(4);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		logPath = LogReports.logGeneration(testname, "TestVehicleTrue");
		this.setLogPath(logPath);
		TestVehicleTrue.assertTestVehicleTrue(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "recallApprovalStatus");
		this.setLogPath(testname);
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign, criterion);
		Get_VinsForInspection.assertApprovedStatementTestVehicle(testname, vin, campaign, criterion,
				defaultBatchForVinInspection);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApproveDefaultBatch");
		this.setLogPath(logPath);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, batchName);
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApprovedVinStatus");
		this.setLogPath(testname);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
