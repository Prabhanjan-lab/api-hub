package testCases.Q4_2024;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.ImportRecallWithFaultyFields;
import testCases.reusable_TestSteps.ImporterRecall_Logs;
import testCases.reusable_TestSteps.InboxRecall;
import testCases.reusable_TestSteps.getRecallWithCampaignNumber;
import testCases.utils.CurrentDateTime;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62297")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_62297_RECALL2_UNECE_REG_VW_OCU3_Importing_ReCall_Actions_Type_02_Ignored_Language_of_Criterions_Title extends AbstractStandardBehaviour{
	
	String logPath = "";
	private String RECALL_ID;
	private String campaign = "campaign";
	String testname = "RECALL2_UNECE_REG_VW_OCU3_Importing_ReCall_Actions_Type_02_Ignored_Language_of_Criterions_Title";
	//String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String type = "Added";
	String logCheck = " Ignored: Title language: (de-de);";
	String criterionTitle = null;
	LocalDateTime currentTimestamp = CurrentDateTime.getCurrentTimeStamp();

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(7);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "CreateImportedRecall");
		this.setLogPath(logPath);
		RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		ImportRecallWithFaultyFields.createImportedRecall2WithFaultyfields(RECALL_ID,
				"recall-ouqa-62297-a-minimal-data-record.json", "02", RECALL_ID, "de", 26, 2);
		UpdateTestParameters.updateAttributeValueInJson(campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);
		
		
		this.setLogPath(logPath);
		logPath = LogReports.logGeneration(testname, "LoggingOverview");
		campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		ImporterRecall_Logs.assertLogDetails(testname, type, campaign, logCheck, currentTimestamp);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		logPath = LogReports.logGeneration(testname, "RecallinInbox");
		InboxRecall.assertRecallInbox(testname, campaign);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		logPath = LogReports.logGeneration(testname, "CriterionExists");
		getRecallWithCampaignNumber.assertCriterion(testname, campaign, criterion);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		logPath = LogReports.logGeneration(testname, "CriterionTitleEmpty");
		getRecallWithCampaignNumber.assertCriterionTitleEmpty(testname, campaign, criterionTitle);
		this.logStepPassed(logPath);
		
		
	}
	
	@Override
	protected void tearDownHook() throws Throwable {
	}
}

