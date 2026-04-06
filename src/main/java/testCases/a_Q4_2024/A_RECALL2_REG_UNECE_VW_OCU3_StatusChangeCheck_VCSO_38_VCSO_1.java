package testCases.a_Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaignWithMultipleCriterion;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62299123")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class A_RECALL2_REG_UNECE_VW_OCU3_StatusChangeCheck_VCSO_38_VCSO_1 extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "RECALL2_REG_UNECE_VW_OCU3_StatusChangeCheck_VCSO_38_VCSO_1";
	private String campaign = "campaign";
	private String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

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

		String RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		logPath = LogReports.logGeneration(testname, "ImportFirstRecallWithTwoCriterion");
		CreateImportedRecall.createImportedRecall2(RECALL_ID, "recall-ouqa-62299-a-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(this.campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CreateORUCampaign");
		this.setLogPath(logPath);
		CreateORUCampaignWithMultipleCriterion.assertCreateORUCampaign(testname,
				TestVariables.ORUCreationWithTwoCriterions_filePath);
		campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, campaign, criterion);
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
