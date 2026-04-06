package testCases.Q4_2024;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.GetVehicleAttributes;
import testCases.reusable_TestSteps.ImportRecallWithFaultyFields;
import testCases.reusable_TestSteps.ImporterRecall_Logs;
import testCases.reusable_TestSteps.InboxRecall;
import testCases.utils.CurrentDateTime;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-49928")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_49928_RECALL2_ImportingReCallActions_Type05_with_empty_CurrentNumber_within_PKN
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "RECALL2_ImportingReCallActions_Type05_with_empty_CurrentNumber_within_PKN";
	String campaign = "campaign";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	private String RECALL_ID;
	String faultyValue = ReadTestParameters.getAttributeValue(testname, "faultyValue");
	String logCheck;
	String recordsType = ReadTestParameters.getAttributeValue(testname, "recordsType"); // Adapted,Added
	String expectedPKNValue = ReadTestParameters.getAttributeValue(testname, "expectedPKNValue");
	LocalDateTime currentTimestamp;

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

		currentTimestamp = CurrentDateTime.getCurrentTimeStamp();

		RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);;
		logPath = LogReports.logGeneration(testname, "ImportRecallWith_Empty_CurrentNumber_within_PKN");
		ImportRecallWithFaultyFields.createImportedRecall2WithFaultyfields(RECALL_ID,
				"recall-ouqa-49928-a-minimal-data-record.json", "05", vin, faultyValue, 168, 4);
		UpdateTestParameters.updateAttributeValueInJson(campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Assert_Logs");
		this.setLogPath(logPath);
		logCheck=RECALL_ID+"-"+criterion+"-"+vin;
		ImporterRecall_Logs.assertLogDetails(testname, recordsType, vin, logCheck, currentTimestamp);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertRecall_Inbox");
		this.setLogPath(logPath);
		InboxRecall.assertRecallInbox(testname, RECALL_ID);
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "GetVehicleAttributesDetails");
		this.setLogPath(logPath);
		GetVehicleAttributes.assertPKNValue(testname, vin, expectedPKNValue);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
