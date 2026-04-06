package testCases.Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CreateAutoApprovedBatch;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.reusable_TestSteps.MoveVinFromBatchToOtherBatch;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-34934")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_34934_RECALL2_REG_Creating_ORU_Campaign_with_imported_ReCall_with_2x5_VINs_for_finally_three_batches extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "RECALL2_REG_Creating_ORU_Campaign_with_imported_ReCall_with_2x5_VINs_for_finally_three_batches";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String aaFirstBatchName = ReadTestParameters.getAttributeValue(testname, "aaFirstBatchName");
	String aaSecondBatchName = ReadTestParameters.getAttributeValue(testname, "aaSecondBatchName");
	String aaThirdBatchName = ReadTestParameters.getAttributeValue(testname, "aaThirdBatchName");
	String aaThirdBatchNameEdit = ReadTestParameters.getAttributeValue(testname, "aaThirdBatchNameNew");
	String firstBatchPredecessorValue = null;
	String secondBatchPredecessorValue = ReadTestParameters.getAttributeValue(testname, "secondBatchPredecessorValue");
	String thirdBatchPredecessorValue = ReadTestParameters.getAttributeValue(testname,"aaSecondBatchName");
	String targetValue = ReadTestParameters.getAttributeValue(testname, "targetValue");
	String firstBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "firstBatchImporterValue");
	String firstBatchMBTvalue = null;
	String firstBatchPknValue = null;
	String secondBatchImporterValue = null;
	String secondBatchMBTValue = ReadTestParameters.getAttributeValue(testname, "secondBatchMbtValue");
	String secondBatchPknValue = null;
	String thirdBatchImporterValue = "null";
	String thirdBatchMBTValue = null;
	String thirdBatchPknValue = ReadTestParameters.getAttributeValue(testname, "thirdBatchPknValue");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	/*String RECALL_ID = "";
	String campaign ="";*/
	
	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(7); // test has seven steps
		}
	}

	@Override
	protected void testHook() throws Throwable {
		
		LogReports.logs(testname);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		this.setLogPath(logPath);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "CreateORU");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AABatch1withImporter");
		this.setLogPath(logPath);
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, campaign, criterion);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign, criterion, aaFirstBatchName, firstBatchPredecessorValue, targetValue, firstBatchImporterValue, firstBatchMBTvalue, firstBatchPknValue);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AABatch2withMBT");
		this.setLogPath(logPath);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign, criterion, aaSecondBatchName, secondBatchPredecessorValue, targetValue, secondBatchImporterValue, secondBatchMBTValue, secondBatchPknValue);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AABatch3withPKN");
		this.setLogPath(logPath);
		MoveVinFromBatchToOtherBatch.assertMoveVinsFromBacthsToOtherBatch(testname, TestVariables.OUQA_34934_filePath, campaign, criterion, aaThirdBatchName, thirdBatchPredecessorValue, targetValue, thirdBatchImporterValue, thirdBatchMBTValue, thirdBatchPknValue);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "EditThirdBatch");
		this.setLogPath(logPath);
		CreateAutoApprovedBatch.assertAutoApprovedBatchEdit(testname, campaign, criterion, aaThirdBatchName, aaThirdBatchNameEdit, thirdBatchPredecessorValue, targetValue, thirdBatchImporterValue, thirdBatchMBTValue, thirdBatchPknValue);
		this.logStepPassed(logPath);
		
		System.out.println("Test Execution completed.");
	
	}
	
	@Override
	protected void tearDownHook() throws Throwable{
	}
}


