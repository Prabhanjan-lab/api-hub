package testCases.c_Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.DeleteCampaign;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;
@Tag("OUQA-65727456")
@Tag("Demo_ECE")

public class C_RECALL2_UNECE_REG_VWOCU3_Importing_ReCall_Actions_Type_04_Part_of_ORU_Campaign_ORU_Relevance_No_Yes extends AbstractStandardBehaviour{
	
	
	String logPath = "";
	String testname ="RECALL2_UNECE_REG_VWOCU3_Importing_ReCall_Actions_Type_04_Part_of_ORU_Campaign_ORU_Relevance_Yes_No";
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

		System.out.println("ORU campaign" + campaign + "Deleted sucessfully.");
	}

	@Override
	protected void tearDownHook() throws Throwable {
	}
	
	
}
