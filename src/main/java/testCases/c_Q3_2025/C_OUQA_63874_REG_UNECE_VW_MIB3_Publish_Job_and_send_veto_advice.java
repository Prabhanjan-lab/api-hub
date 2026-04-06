package testCases.c_Q3_2025;

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

@Tag("OUQA-63874456")
@Tag("Approval_ECE")
public class C_OUQA_63874_REG_UNECE_VW_MIB3_Publish_Job_and_send_veto_advice extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "UNECE_VW_MIB3_Publish_Job_and_send_veto_advice";
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
		LogReports.logGeneration(testname, "ForceAbort");

		// Empty Active Queue and Schedule Queue Campaigns For first VIN
		String activeCampaign = GetActiveCampaign.getActiveQueueCampaign(testname, vin);
		String activeCampaignCriterionId = GetActiveCampaign.getActiveQueueCampaigncriterionId(testname, vin);
		if (ScheduledQueue.assertCampaignInScheduledQueue(testname, vin)) {
			LogReports.logGeneration(testname, "EmptyScheduleQueue");
			List<String> scheduledCampaignList = ScheduledQueue.getScheduledCampaigns(testname, vin);
			List<String> scheduledCampaignsCriterionList = ScheduledQueue.getScheduledCampaignsCriterion(testname, vin);
			CancelScheduleQueueCampaign.assertEmptyScheduledQueue(testname, vin, scheduledCampaignList,
					scheduledCampaignsCriterionList);
		} else {
			System.out.println("ScheduleQueue is empty for vin: " + vin);
		}
		if (ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin)) {
			LogReports.logGeneration(testname, "ForceAbortCampaign");
			ForceAbort.assertForceAbort(testname, vin, activeCampaign, activeCampaignCriterionId);
		} else {
			System.out.println("Active Queue is empty for vin: " + vin);
		}
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
