package testCases.c_UNECE_VW_OCU3_StoryTests;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CancelPendingQueueCampaign;
import testCases.reusable_TestSteps.CancelScheduleQueueCampaign;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.GetActiveCampaign;
import testCases.reusable_TestSteps.PendingQueueVCSOStatus;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.reusable_TestSteps.SkipMassnotificationTrue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54611456")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class C_VW_OCU3_Trigger_CancelUpdateJobs_AEU1_and_VCSO_5_new2_noJob extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VW_OCU3_CancelUpdateJobs_AEU1_and_VCSO_5_new2_noJob";
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
			this.initTestContainer(1);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		LogReports.logGeneration(testname, "ApproveBatch");
		SkipMassnotificationTrue.assertSkipMassnotificationTrue(testname, vin);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, batchName);
		
		String activeCampaign = GetActiveCampaign.getActiveQueueCampaign(testname, vin);
		String activeCampaignCriterionId = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin);
		if (PendingQueueVCSOStatus.assertCampaignInPendingQueue(testname, vin, campaign)) {
			LogReports.logGeneration(testname, "ForceAbort");
			CancelPendingQueueCampaign.assertCancelPendingCampaign(testname, vin, campaign, criterion);
		} else if (ScheduledQueue.assertCampaignInScheduledQueue(testname, vin)) {
			LogReports.logGeneration(testname, "EmptyScheduleQueue");
			List<String> scheduledCampaignList = ScheduledQueue.getScheduledCampaigns(testname, vin);
			List<String> scheduledCampaignsCriterionList = ScheduledQueue.getScheduledCampaignsCriterion(testname, vin);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin, scheduledCampaignList,
					scheduledCampaignsCriterionList);
		} else if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin)) {
			LogReports.logGeneration(testname, "ForceAbortCampaign");
			ForceAbort.assertForceAbort(testname, vin, activeCampaign, activeCampaignCriterionId);
		} else {
			System.out.print("Active Queue, Schedule Queue And Pending Queue is not having a matching Campaign.");
		}

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
