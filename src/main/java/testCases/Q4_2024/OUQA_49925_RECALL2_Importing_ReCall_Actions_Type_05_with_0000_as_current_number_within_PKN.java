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

@Tag("OUQA-49925")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_49925_RECALL2_Importing_ReCall_Actions_Type_05_with_0000_as_current_number_within_PKN extends AbstractStandardBehaviour{
	
	String logPath = "";
	String testname = "RECALL2_Importing_ReCall_Actions_Type_05_with_0000_as_current_number_within_PKN";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	private String RECALL_ID;
	String campaign = "campaign";
	String faultyValue = ReadTestParameters.getAttributeValue(testname, "faultyValue");
	String logCheck = ReadTestParameters.getAttributeValue(testname, "logCheck");
	String recordsType = ReadTestParameters.getAttributeValue(testname, "recordsType"); // Adapted,Added
	String expectedPKNValue = ReadTestParameters.getAttributeValue(testname, "expectedPKNValue");
	
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
		
		// Create a ReCall Configuration with one VIN.
		logPath = LogReports.logGeneration(testname, "CreateImportedRecall");
		this.setLogPath(logPath);
		RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		ImportRecallWithFaultyFields.createImportedRecall2WithFaultyfields(RECALL_ID,
								"recall-ouqa-49925-minimal-data-record.json", "05", vin, faultyValue, 168, 4);
		UpdateTestParameters.updateAttributeValueInJson(campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Logging Overview");
		this.setLogPath(logPath);
		ImporterRecall_Logs.assertLogDetails(testname, recordsType, vin, logCheck, currentTimestamp);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "CheckINBOX");
		this.setLogPath(logPath);
		InboxRecall.assertRecallInbox(testname, RECALL_ID);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "GetVinDetails");
		this.setLogPath(logPath);
	    GetVehicleAttributes.assertGetVehicleAttributes(testname, vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "VerifyPKNValue");
		this.setLogPath(logPath);
		GetVehicleAttributes.assertPKNValue(testname, vin, expectedPKNValue); 
		this.logStepPassed(logPath);
		
		System.out.println("Test Execution Completed Sucessfully.");
	}
	
	@Override
	protected void tearDownHook() throws Throwable {
	}

}
