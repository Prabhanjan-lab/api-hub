package testCases.a_Q4_2024;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-34934123")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class A_RECALL2_REG_Creating_ORU_Campaign_with_imported_ReCall_with_2x5_VINs_for_finally_three_batches extends AbstractStandardBehaviour{
	
	String testname = "RECALL2_REG_Creating_ORU_Campaign_with_imported_ReCall_with_2x5_VINs_for_finally_three_batches";
	private String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String logPath = "";

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
	protected void testHook() throws Throwable, IOException {
		LogReports.logs(testname);
		
		logPath = LogReports.logGeneration(testname, "ImportRecallwith10Vins");
		this.setLogPath(logPath);
		CreateImportedRecall.createImportedRecall2(campaign, "recall-ouqa-34934-a-minimal-data-record.json");
		this.logStepPassed(logPath);
		
		System.out.println("A condition execution completed.");

		}

		@Override
		protected void tearDownHook() throws Throwable {
			// TODO Auto-generated method stub

		}

}
