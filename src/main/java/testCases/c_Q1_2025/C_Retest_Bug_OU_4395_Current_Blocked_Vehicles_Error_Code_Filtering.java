package testCases.c_Q1_2025;

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

@Tag("OUQA-28242456")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
public class C_Retest_Bug_OU_4395_Current_Blocked_Vehicles_Error_Code_Filtering extends AbstractStandardBehaviour{


	String logPath = "";
	String testname = "Current_Blocked_Vehicles_Error_Code_Filtering";
	String vin1 = ReadTestParameters.getAttributeValue(testname, "vin1");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String vin3 = ReadTestParameters.getAttributeValue(testname, "vin3");
	String vin4 = ReadTestParameters.getAttributeValue(testname, "vin4");
	String vin5= ReadTestParameters.getAttributeValue(testname, "vin5");
	String vin6 = ReadTestParameters.getAttributeValue(testname, "vin6");
	String vin7 = ReadTestParameters.getAttributeValue(testname, "vin7");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(7); // test has one test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		LogReports.logGeneration(testname, "forceabort");
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

		// Empty Active Queue and Schedule Queue Campaigns For fifth VIN
		String activeCampaign5 = GetActiveCampaign.getActiveQueueCampaign(testname, vin5);
		String activeCampaignCriterionId5 = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin5);
		if(ScheduledQueue.assertCampaignInScheduledQueue(testname, vin5)) {
			LogReports.logGeneration(testname, "EmptyFourthVinScheduleQueue");
			List<String> scheduledCampaignList5= ScheduledQueue.getScheduledCampaigns(testname,vin5);
			List<String> scheduledCampaignsCriterionList5=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin5);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin5,scheduledCampaignList5,scheduledCampaignsCriterionList5);
		}else {
			System.out.println("ScheduleQueue is empty for vin:"+vin5);
		}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin5)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInFourthVin");
			ForceAbort.assertForceAbort(testname, vin5, activeCampaign5, activeCampaignCriterionId5);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin5);
		}

		// Empty Active Queue and Schedule Queue Campaigns For sixth VIN
		String activeCampaign6 = GetActiveCampaign.getActiveQueueCampaign(testname, vin6);
		String activeCampaignCriterionId6 = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin6);
		if(ScheduledQueue.assertCampaignInScheduledQueue(testname, vin6)) {
			LogReports.logGeneration(testname, "EmptyFourthVinScheduleQueue");
			List<String> scheduledCampaignList6= ScheduledQueue.getScheduledCampaigns(testname,vin6);
			List<String> scheduledCampaignsCriterionList6=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin6);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin6,scheduledCampaignList6,scheduledCampaignsCriterionList6);
		}else {
			System.out.println("ScheduleQueue is empty for vin:"+vin6);
		}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin6)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInFourthVin");
			ForceAbort.assertForceAbort(testname, vin6, activeCampaign6, activeCampaignCriterionId6);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin6);
		}

		// Empty Active Queue and Schedule Queue Campaigns For seventh VIN
		String activeCampaign7 = GetActiveCampaign.getActiveQueueCampaign(testname, vin7);
		String activeCampaignCriterionId7 = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin7);
		if(ScheduledQueue.assertCampaignInScheduledQueue(testname, vin7)) {
			LogReports.logGeneration(testname, "EmptyFourthVinScheduleQueue");
			List<String> scheduledCampaignList7= ScheduledQueue.getScheduledCampaigns(testname,vin7);
			List<String> scheduledCampaignsCriterionList7=ScheduledQueue.getScheduledCampaignsCriterion(testname,vin7);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin7,scheduledCampaignList7,scheduledCampaignsCriterionList7);
		}else {
			System.out.println("ScheduleQueue is empty for vin:"+vin7);
		}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin7)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInFourthVin");
			ForceAbort.assertForceAbort(testname, vin7, activeCampaign7, activeCampaignCriterionId7);
		} else {
			System.out.println("Active Queue is empty for vin: "+vin7);
		}
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}



}
