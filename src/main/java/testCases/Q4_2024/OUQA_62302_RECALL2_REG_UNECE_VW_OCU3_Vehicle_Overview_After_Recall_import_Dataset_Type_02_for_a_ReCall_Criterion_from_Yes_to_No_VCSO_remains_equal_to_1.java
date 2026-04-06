package testCases.Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.PendingQueue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62302")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_62302_RECALL2_REG_UNECE_VW_OCU3_Vehicle_Overview_After_Recall_import_Dataset_Type_02_for_a_ReCall_Criterion_from_Yes_to_No_VCSO_remains_equal_to_1 
	extends AbstractStandardBehaviour {

		String logPath = "";
		String testname = "RECALL2_REG_UNECE_VWOCU3_Vehicle_Overview_After_Recall_import_Dataset_Type_02_for_a_ReCall_Criterion_from_Yes_to_No_VCSO_remains_equal_to_1";
		String vin = ReadTestParameters.getAttributeValue(testname, "vin");
		String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

		@Override
		@BeforeEach
		protected void setUp() throws Throwable {

			try {
				super.setUp();
			} catch (Throwable e) {
				this.setThrowable(e);
				throw e;
			} finally {
				this.initTestContainer(5);
			}
		}

		@Override
		protected void testHook() throws Throwable {
			LogReports.logs(testname);

			this.setLogPath(logPath);	
			this.logStepPassed(logPath);
			
			logPath = LogReports.logGeneration(testname, "RevokeDST02");
			this.setLogPath(logPath);
			PendingQueue.assertPendingCampaign(testname, vin, campaign);
			CreateImportedRecall.createImportedRecall2(campaign,"recall-ouqa-62302-b-minimal-data-record.json");
			this.logStepPassed(logPath);
			
			logPath = LogReports.logGeneration(testname, "CheckVCSO55");
			this.setLogPath(logPath);
			Thread.sleep(3000);	
			CampaignHistory.assertCampaignHistory(testname, vin, campaign, "55");
			this.logStepPassed(logPath);
			
			logPath = LogReports.logGeneration(testname, "SetTrueDST02");
			this.setLogPath(logPath);
			CreateImportedRecall.createImportedRecall2(campaign,"recall-ouqa-62302-a-minimal-data-record.json");
			this.logStepPassed(logPath);
			
			logPath = LogReports.logGeneration(testname, "CheckVCSO1");
			this.setLogPath(logPath);
			PendingQueue.assertPendingCampaign(testname, vin, campaign);
			this.logStepPassed(logPath);
			
			System.out.println("Test Execution Completed.");
			
		}
			@Override
			protected void tearDownHook() throws Throwable {
				// TODO Auto-generated method  stub

			}	
}
