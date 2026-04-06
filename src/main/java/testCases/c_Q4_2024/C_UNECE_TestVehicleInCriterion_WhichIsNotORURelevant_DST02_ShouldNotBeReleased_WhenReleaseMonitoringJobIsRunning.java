package testCases.c_Q4_2024;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CancelScheduleQueueCampaign;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.GetActiveCampaign;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-63128456")
@Tag("Demo_ECE")

public class C_UNECE_TestVehicleInCriterion_WhichIsNotORURelevant_DST02_ShouldNotBeReleased_WhenReleaseMonitoringJobIsRunning
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "UNECE_TestVehicleInCriterion_WhichIsNotORURelevant_DST02_ShouldNotBeReleased_WhenReleaseMonitoringJobIsRunning";
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(1); // test has one test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		LogReports.logGeneration(testname, "EmptyScheduleandActiveQueue");
		// Empty Active Queue and Schedule Queue Campaigns For VIN
		String activeCampaign = GetActiveCampaign.getActiveQueueCampaign(testname, vin2);
		String activeCampaignCriterionId = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin2);
		if (ScheduledQueue.assertCampaignInScheduledQueue(testname, vin2)) {
			LogReports.logGeneration(testname, "EmptyVinScheduleQueue");
			List<String> scheduledCampaignList = ScheduledQueue.getScheduledCampaigns(testname, vin2);
			List<String> scheduledCampaignsCriterionList = ScheduledQueue.getScheduledCampaignsCriterion(testname,
					vin2);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin2, scheduledCampaignList,
					scheduledCampaignsCriterionList);
		} else {
			System.out.println("ScheduleQueue is empty for vin: " + vin2);
		}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin2)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInVin");
			ForceAbort.assertForceAbort(testname, vin2, activeCampaign, activeCampaignCriterionId);
		} else {
			System.out.println("Active Queue is empty for vin: " + vin2);
		}
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
