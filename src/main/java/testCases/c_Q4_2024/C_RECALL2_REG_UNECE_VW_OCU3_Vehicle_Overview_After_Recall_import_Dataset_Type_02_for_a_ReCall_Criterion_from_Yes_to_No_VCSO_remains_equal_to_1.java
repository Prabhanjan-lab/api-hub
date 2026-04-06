package testCases.c_Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.DeleteCampaign;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62302456")
@Tag("Demo_ECE")



public class C_RECALL2_REG_UNECE_VW_OCU3_Vehicle_Overview_After_Recall_import_Dataset_Type_02_for_a_ReCall_Criterion_from_Yes_to_No_VCSO_remains_equal_to_1 extends AbstractStandardBehaviour{
	String logPath = "";
	String testname = "RECALL2_REG_UNECE_VWOCU3_Vehicle_Overview_After_Recall_import_Dataset_Type_02_for_a_ReCall_Criterion_from_Yes_to_No_VCSO_remains_equal_to_1";
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");

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
		
		logPath = LogReports.logGeneration(testname, "DeleteORU");
		DeleteCampaign.assertORUdelete(testname, campaign);
		
		System.out.println("ORU campaign" +campaign+ "Deleted sucessfully.");
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method  stub

	}

}
