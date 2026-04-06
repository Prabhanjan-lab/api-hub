package testCases.c_Q3_2024;

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

@Tag("OUQA-62292456")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class C_UNECE_REG_An_automaticallyBatch_hasToReleaseAutomatically_if_PreviousBatchReaches_ThePOC
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "VWOCU3_UNECE_REG_An_automaticallyBatch_hasToReleaseAutomatically_if_PreviousBatchReaches_ThePOC";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String vin3 = ReadTestParameters.getAttributeValue(testname, "vin3");
	String vin4 = ReadTestParameters.getAttributeValue(testname, "vin4");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(2);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		LogReports.logGeneration(testname, "ForceAbort");

		// Empty Active Queue and Schedule Queue Campaigns For first VIN
		String activeCampaign = GetActiveCampaign.getActiveQueueCampaign(testname, vin);
		String activeCampaignCriterionId = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin);
        if(ScheduledQueue.assertCampaignInScheduledQueue(testname, vin)) {
			LogReports.logGeneration(testname, "EmptyFirstVinScheduleQueue");
			List<String> scheduledCampaignList= ScheduledQueue.getScheduledCampaigns(testname,vin);
			List<String> scheduledCampaignsCriterionList=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin,scheduledCampaignList,scheduledCampaignsCriterionList);
		}else {
				System.out.println("ScheduleQueue is empty for vin: "+vin);
			}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInFirstVin");
			ForceAbort.assertForceAbort(testname, vin, activeCampaign, activeCampaignCriterionId);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin);
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
			LogReports.logGeneration(testname, "EmptyThirdVinScheduleQueue");
			List<String> scheduledCampaignList3= ScheduledQueue.getScheduledCampaigns(testname,vin3);
			List<String> scheduledCampaignsCriterionList3=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin3);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin3,scheduledCampaignList3,scheduledCampaignsCriterionList3);
		}else {
				System.out.println("ScheduleQueue is empty for vin: "+vin3);
			}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin3)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInThirdVin");
			ForceAbort.assertForceAbort(testname, vin3, activeCampaign3, activeCampaignCriterionId3);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin3);
		}
		
		// Empty Active Queue and Schedule Queue Campaigns For fourth VIN
		String activeCampaign4 = GetActiveCampaign.getActiveQueueCampaign(testname, vin4);
		String activeCampaignCriterionId4 = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin4);
		if(ScheduledQueue.assertCampaignInScheduledQueue(testname, vin4)) {
			LogReports.logGeneration(testname, "EmptyFourthVinScheduleQueue");
			List<String> scheduledCampaignList4= ScheduledQueue.getScheduledCampaigns(testname,vin4);
			List<String> scheduledCampaignsCriterionList4=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin4);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin4,scheduledCampaignList4,scheduledCampaignsCriterionList4);
		}else {
				System.out.println("ScheduleQueue is empty for vin:"+vin4);
			}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin4)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInFourthVin");
			ForceAbort.assertForceAbort(testname, vin4, activeCampaign4, activeCampaignCriterionId4);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin4);
		}
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
