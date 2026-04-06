package testCases.c_Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import java.util.List;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CancelScheduleQueueCampaign;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.GetActiveCampaign;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-2485456")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class C_01_IMPORTED_RECALL_Automatically_releasing_batch_with_status_created_that_got_a_ReCall_approval_only_conditions extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "IMPORTED_RECALL_Automatically_releasing_batch_with_status_created_that_got_a_ReCall_approval_only_conditions";
	String vin1 = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String vin3 = ReadTestParameters.getAttributeValue(testname, "vin3");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(3);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		LogReports.logGeneration(testname, "ForceAbort");
		// Empty Active Queue and Schedule Queue Campaigns For first VIN
		String activeCampaign = GetActiveCampaign.getActiveQueueCampaign(testname, vin1);
		String activeCampaignCriterionId = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin1);
		if(ScheduledQueue.assertCampaignInScheduledQueue(testname, vin1)) {
			LogReports.logGeneration(testname, "EmptyFirstVinScheduleQueue");
			List<String> scheduledCampaignList= ScheduledQueue.getScheduledCampaigns(testname,vin1);
			List<String> scheduledCampaignsCriterionList=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin1);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin1,scheduledCampaignList,scheduledCampaignsCriterionList);
		}else {
			System.out.println("ScheduleQueue is empty for vin: "+vin1);
		}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin1)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInFirstVin");
			ForceAbort.assertForceAbort(testname, vin1, activeCampaign, activeCampaignCriterionId);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin1);
		}


		// Empty Active Queue and Schedule Queue Campaigns For second VIN
		String activeCampaign2 = GetActiveCampaign.getActiveQueueCampaign(testname, vin2);
		String activeCampaignCriterionId2 = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin2);
		if(ScheduledQueue.assertCampaignInScheduledQueue(testname, vin2)) {
			LogReports.logGeneration(testname, "EmptySecondVinScheduleQueue");
			List<String> scheduledCampaignList2= ScheduledQueue.getScheduledCampaigns(testname,vin2);
			List<String> scheduledCampaignsCriterionList2=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin2);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin2,scheduledCampaignList2,scheduledCampaignsCriterionList2);
		}else {
			System.out.println("ScheduleQueue is empty for vin: "+vin2);
		}

		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin2)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInsecondVin");
			ForceAbort.assertForceAbort(testname, vin2, activeCampaign2, activeCampaignCriterionId2);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin2);
		}

		// Empty Active Queue and Schedule Queue Campaigns For third VIN
		String activeCampaign3 = GetActiveCampaign.getActiveQueueCampaign(testname, vin3);
		String activeCampaignCriterionId3 = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin3);
		if(ScheduledQueue.assertCampaignInScheduledQueue(testname, vin3)) {
			LogReports.logGeneration(testname, "EmptySecondVinScheduleQueue");
			List<String> scheduledCampaignList2= ScheduledQueue.getScheduledCampaigns(testname,vin3);
			List<String> scheduledCampaignsCriterionList2=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin3);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin3,scheduledCampaignList2,scheduledCampaignsCriterionList2);
		}else {
			System.out.println("ScheduleQueue is empty for vin: "+vin3);
		}

		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin3)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInsecondVin");
			ForceAbort.assertForceAbort(testname, vin3, activeCampaign3, activeCampaignCriterionId3);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin3);
		}
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
