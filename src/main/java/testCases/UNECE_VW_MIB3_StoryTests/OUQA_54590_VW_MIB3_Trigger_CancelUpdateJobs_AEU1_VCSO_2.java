package testCases.UNECE_VW_MIB3_StoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.DeapproveBatch;
import testCases.reusable_TestSteps.PendingQueue;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54590")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")

public class OUQA_54590_VW_MIB3_Trigger_CancelUpdateJobs_AEU1_VCSO_2 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VWMIB3_CancelUpdateJobs_AEU1_VCSO_2";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");

	// TODO : setUp notwendig?
	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
		} finally {
			initTestContainer(5);// test has five steps
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "RewindToSchedule");
		RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname, vin, campaign, criterion);

		logPath = LogReports.logGeneration(testname, "VCSOStatus2");
		this.setLogPath(logPath);
		ScheduledQueue.assertScheduledCampaign(testname, vin, campaign);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "DeapproveBatch");
		this.setLogPath(logPath);
		DeapproveBatch.assertDeapproveBatch(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PendingQueue");
		this.setLogPath(logPath);
		PendingQueue.assertPendingCampaign(testname, vin, campaign);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed.");
	}

	@Override
	protected void tearDownHook() throws Throwable {

	}

}
