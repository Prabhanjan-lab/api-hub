package testCases.Q4_2024;

import java.time.LocalDateTime;

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

/**
 * @author Sulzer GmbH
 */
@Tag("OUQA-62296")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")


public class OUQA_62296_RECALL2_UNECE_REG_Importing_ReCall_Actions_Type_05_Ignored_PKN_DAY_faulty extends AbstractStandardBehaviour {

    // ReCall entries
	String testname="RECALL2_UNECE_REG_Importing_ReCall_Actions_Type_05_Ignored_PKN_DAY_faulty";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = "campaign";
	String RECALL_ID;
	String logCheck="Faulty Field 8, PKN";
	String recordsType = "Adapted";
	LocalDateTime currentTimestamp = CurrentDateTime.getCurrentTimeStamp();
    String logPath = "";

    @Override
    @org.junit.jupiter.api.BeforeEach
    protected void setUp() throws Throwable {

        try {
            super.setUp();
        } catch (Throwable e) {
            this.setThrowable(e);
            throw e;
        } finally {
            this.initTestContainer(5); // test has 5 test steps
        }
    }

    @Override
    protected void testHook() throws Throwable {
    	LogReports.logs(testname);
    	
    	logPath = LogReports.logGeneration(testname, "Create_ImportedRecall_With_FaultyPKNDAy");
		this.setLogPath(logPath);
		RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		ImportRecallWithFaultyFields.createImportedRecall2WithFaultyfields(RECALL_ID, "recall-ouqa-62296-a-minimal-data-record.json", "05", vin,"A",167,1);
		ImporterRecall_Logs.assertLogDetails(testname,recordsType, vin+":", logCheck, currentTimestamp); 
		UpdateTestParameters.updateAttributeValueInJson(campaign,RECALL_ID,testname); 
		this.logStepPassed(logPath); 
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertRecall_Inbox");
		this.setLogPath(logPath);
		InboxRecall.assertRecallInbox(testname, RECALL_ID);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Assert_VehicleAttributePKN");
		this.setLogPath(logPath);
		GetVehicleAttributes.assertPKNEmpty(testname,vin);
		this.logStepPassed(logPath); 
    }

	@Override
    public void tearDownHook() {

    }

}