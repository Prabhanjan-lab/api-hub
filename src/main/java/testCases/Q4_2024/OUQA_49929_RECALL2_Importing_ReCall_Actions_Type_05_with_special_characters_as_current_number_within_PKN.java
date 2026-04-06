package testCases.Q4_2024;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.ExceptionList;
import testCases.reusable_TestSteps.GetVehicleAttributes;
import testCases.reusable_TestSteps.ImportRecallWithFaultyFields;
import testCases.reusable_TestSteps.ImporterRecall_Logs;
import testCases.reusable_TestSteps.InboxRecall;
import testCases.utils.CurrentDateTime;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-49929")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_49929_RECALL2_Importing_ReCall_Actions_Type_05_with_special_characters_as_current_number_within_PKN extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "RECALL2_Importing_ReCall_Actions_Type_05_with_special_characters_as_current_number_within_PKN";
	private String RECALL_ID;
	String campaign = "campaign";
	String brand = ReadTestParameters.getAttributeValue(testname, "brand");
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String faultyValue="+AB?";   //Including special characters
	String expectedPKNvalue="047662009";
	String logCheck="PKN: (047+AB?662009)";
	String recordsType = "Adapted"; //Adapted,Added
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

		logPath = LogReports.logGeneration(testname, "Create_ImportedRecall_WithSpecialCharacters_asCurrentNumberWithinPKN");
		this.setLogPath(logPath);
		ExceptionList.assertVinNotInExceptionList(testname, vin);
		RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		ImportRecallWithFaultyFields.createImportedRecall2WithFaultyfields(RECALL_ID, "recall-ouqa-49929-minimal-data-record.json", "05",vin,faultyValue,168,4);
		UpdateTestParameters.updateAttributeValueInJson(campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Assert_Logs");
		this.setLogPath(logPath);
		ImporterRecall_Logs.assertLogDetails(testname, recordsType, vin, logCheck, currentTimestamp);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertRecall_Inbox");
		this.setLogPath(logPath);
		InboxRecall.assertRecallInbox(testname, RECALL_ID);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "assertPKNvalue");
		this.setLogPath(logPath);
		GetVehicleAttributes.assertPKNValue(testname, vin, expectedPKNvalue);
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
