package testCases.a_Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62271123")
@Tag("Demo_ECE")


public class A_RECALL2_UNECE_REG_03_TestingVehicleApproval_RemovalOfTheTestingVehicleFlag_ImportedRecall
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "RECALL2_UNECE_REG_03_TestingVehicleApproval_RemovalOfTheTestingVehicleFlag_ImportRecall";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	private String campaign = "campaign";

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

		String RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateImportedRecall.createImportedRecall2(RECALL_ID, "recall-ouqa-62271-a-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(this.campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
