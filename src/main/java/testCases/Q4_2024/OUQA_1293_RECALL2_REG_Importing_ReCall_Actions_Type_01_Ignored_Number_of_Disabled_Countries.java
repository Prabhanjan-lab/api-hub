package testCases.Q4_2024;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.ImportRecallWithFaultyFields;
import testCases.reusable_TestSteps.ImporterRecall_Logs;
import testCases.reusable_TestSteps.InboxRecall;
import testCases.utils.CurrentDateTime;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;
@Tag("OUQA-1293")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")
public class OUQA_1293_RECALL2_REG_Importing_ReCall_Actions_Type_01_Ignored_Number_of_Disabled_Countries extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "RECALL2_ImportingReCallActionsType_01_IgnoredNumberOfDisabledCountries";
	private String RECALL_ID;
	String logCheck="Ignored: Number of DC: (AAA)";
	String recordsType = "Added"; //Adapted,Added
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
			this.initTestContainer(5);
		}
	}
	

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		logPath = LogReports.logGeneration(testname, "Create_ImportedRecall_WithFaulty_NoOf_disabledCountries");
		this.setLogPath(logPath);
		RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		ImportRecallWithFaultyFields.createImportedRecall2WithFaultyfields(RECALL_ID, "recall-ouqa-1293-minimal-data-record.json", "01", "de-DE","AAA",1299,3);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertRecall_Inbox");
		this.setLogPath(logPath);
		InboxRecall.assertRecallInbox(testname, RECALL_ID);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Assert_Logs");
		this.setLogPath(logPath);
		ImporterRecall_Logs.assertLogDetails(testname,recordsType, RECALL_ID, logCheck, currentTimestamp); //1: T1: TB3V: Ignored: Number of DC: (AAA);
		this.logStepPassed(logPath); 
		
	}
	
	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub
		
	}

}
