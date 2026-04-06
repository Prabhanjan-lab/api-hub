package testCases.a_Q1_2025;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.DeapproveBatch;
import testCases.reusable_TestSteps.PendingQueue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-11072123")
@Tag("Approval_ECE")
@Tag("25Q1")
public class A_REG_No_longer_in_active_queue_after_unblocking_blocked_vehicle extends AbstractStandardBehaviour{


	String logPath = "";
	String testname = "REG_No_longer_in_active_queue_after_unblocking_blocked_vehicle";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");

	

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(1); // test has six test step
		}
	}

	@Override
	protected void testHook() throws Throwable, IOException {
		LogReports.logs(testname);

		logPath = LogReports.logGeneration(testname, "assertPendingQueueCampaign");
		this.setLogPath(logPath);
		PendingQueue.assertPendingCampaign(testname, vin, campaign);
		DeapproveBatch.assertDeapproveBatch(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}


}
