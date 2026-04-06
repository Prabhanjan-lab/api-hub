package testCases.Q4_2024;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.Get_VinsForInspection;
import testCases.reusable_TestSteps.ImporterRecall_Logs;
import testCases.utils.CurrentDateTime;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-65734")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_65734_RECALL2_UNECE_REG_VWOCU3_Importing_ReCall_Actions_Type_04_Part_of_ORU_Campaign_ORU_Relevance_Yes_No extends AbstractStandardBehaviour {
	
	String logPath = "";
	String testname = "RECALL2_UNECE_REG_VWOCU3_Importing_ReCall_Actions_Type_04_Part_of_ORU_Campaign_ORU_Relevance_Yes_No";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String type = "Adapted";
	String logCheck = "Changed: ORU-Relevant: (N)";//need to update the logcheck
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
			this.initTestContainer(6); // test has six steps
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		logPath = LogReports.logGeneration(testname, "CheckApprovedStatus");
		this.setLogPath(logPath);
		Get_VinsForInspection.assertVinApprovedStatusForInspection(testname, vin, campaign, criterion, batchName);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "RevokeDST04ForVin");
		this.setLogPath(logPath);
		CreateImportedRecall.createImportedRecall2(campaign, "recall-ouqa-65734-b-minimal-data-record.json");
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "LoggingOverview");
		this.setLogPath(logPath);
		ImporterRecall_Logs.assertLogDetails(testname, type, campaign, logCheck, currentTimestamp);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "VinInBatch");
		this.setLogPath(logPath);
		Get_VinsForInspection.assertVinInBatch(testname, vin, campaign, criterion, batchName);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "CheckNotApprovedStatus");
		this.setLogPath(logPath);
		Get_VinsForInspection.assertVinNotApprovedStatusForInsepection(testname, vin, campaign, criterion, batchName);
		this.logStepPassed(logPath);
		
		System.out.println("Test Execution Completed.");
		
	}
	
	@Override
	protected void tearDownHook() throws Throwable{
		
	}

}
