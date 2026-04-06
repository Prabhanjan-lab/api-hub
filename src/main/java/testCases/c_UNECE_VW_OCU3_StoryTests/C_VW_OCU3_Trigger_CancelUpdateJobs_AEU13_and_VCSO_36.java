package testCases.c_UNECE_VW_OCU3_StoryTests;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CancelScheduleQueueCampaign;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.GetActiveCampaign;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.reusable_TestSteps.SkipMassnotificationTrue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54587456")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class C_VW_OCU3_Trigger_CancelUpdateJobs_AEU13_and_VCSO_36 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VW_OCU3_CancelUpdateJobs_AEU13_and_VCSO_36";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(1);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		LogReports.logGeneration(testname, "assertSkipMassnotification");
		SkipMassnotificationTrue.assertSkipMassnotificationTrue(testname, vin);
		SkipMassnotificationTrue.assertSkipMassnotificationTrue(testname, vin2);

		// Empty Active Queue and Schedule Queue Campaigns For first VIN
		String activeCampaign = GetActiveCampaign.getActiveQueueCampaign(testname, vin);
		String activeCampaignCriterionId = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin);
		if (ScheduledQueue.assertCampaignInScheduledQueue(testname, vin)) {
			LogReports.logGeneration(testname, "EmptyFirstVinScheduleQueue");
			List<String> scheduledCampaignList = ScheduledQueue.getScheduledCampaigns(testname, vin);
			List<String> scheduledCampaignsCriterionList = ScheduledQueue.getScheduledCampaignsCriterion(testname, vin);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin, scheduledCampaignList,
					scheduledCampaignsCriterionList);
		} else {
			System.out.println("ScheduleQueue is empty for vin: " + vin);
		}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInFirstVin");
			ForceAbort.assertForceAbort(testname, vin, activeCampaign, activeCampaignCriterionId);
		} else {
			System.out.println("Active Queue is empty for vin: " + vin);
		}

		// Empty Active Queue and Schedule Queue Campaigns For second VIN
		String activeCampaign2 = GetActiveCampaign.getActiveQueueCampaign(testname, vin2);
		String activeCampaignCriterionId2 = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin2);
		if (ScheduledQueue.assertCampaignInScheduledQueue(testname, vin2)) {
			LogReports.logGeneration(testname, "EmptySecondVinScheduleQueue");
			List<String> scheduledCampaignList2 = ScheduledQueue.getScheduledCampaigns(testname, vin2);
			List<String> scheduledCampaignsCriterionList2 = ScheduledQueue.getScheduledCampaignsCriterion(testname,
					vin2);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin2, scheduledCampaignList2,
					scheduledCampaignsCriterionList2);
		} else {
			System.out.println("ScheduleQueue is empty for vin: " + vin2);
		}

		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin2)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInsecondVin");
			ForceAbort.assertForceAbort(testname, vin2, activeCampaign2, activeCampaignCriterionId2);
		} else {
			System.out.println("Active Queue is empty for vin: " + vin2);
		}
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
