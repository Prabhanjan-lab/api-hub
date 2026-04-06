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

@Tag("OUQA-62214456")
@Tag("Demo_ECE")


public class C_RECALL2_REG_UNECE_VW_OCU3_Vehicle_Overview_After_Recall_import_Dataset_Type_02_for_a_ReCall_Criterion_from_No_to_Yes_VCSO_remains_not_equal_to_55 extends AbstractStandardBehaviour{
	
	String logPath = "";
	String testname = "RECALL2_VW_OCU3_Vehicle_Overview_After_Recall_import_Dataset_Type_02_for_a_ReCall_Criterion_from_No_to_Yes_VCSO_remains_not_equal_to_55";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");

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
		String activeCampaign = GetActiveCampaign.getActiveQueueCampaign(testname, vin);
		String activeCampaignCriterionId = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin);
		if (ScheduledQueue.assertCampaignInScheduledQueue(testname, vin)) {
			LogReports.logGeneration(testname, "EmptyVinScheduleQueue");
			List<String> scheduledCampaignList = ScheduledQueue.getScheduledCampaigns(testname, vin);
			List<String> scheduledCampaignsCriterionList = ScheduledQueue.getScheduledCampaignsCriterion(testname, vin);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin, scheduledCampaignList,
					scheduledCampaignsCriterionList);
		} else {
			System.out.println("ScheduleQueue is empty for vin: " + vin);
		}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin)) {
			LogReports.logGeneration(testname, "ForceAbortCampaignInVin");
			ForceAbort.assertForceAbort(testname, vin, activeCampaign, activeCampaignCriterionId);
		} else {
			System.out.println("Active Queue is empty for vin: " + vin);
		}
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method  stub

	}

}
