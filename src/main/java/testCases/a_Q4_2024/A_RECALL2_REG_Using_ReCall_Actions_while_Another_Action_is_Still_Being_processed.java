package testCases.a_Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.InboxRecall;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-10737123")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class A_RECALL2_REG_Using_ReCall_Actions_while_Another_Action_is_Still_Being_processed extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "RECALL2_Using_ReCall_Actions_while_Another_Action_is_Still_Being_processed";
	private String RECALL_ID1;
	private String RECALL_ID2;
	private String RECALL_ID3;
	private String campaign1="campaign1";
	private String campaign2="campaign2";
	private String campaign3="campaign3";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

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
		
		//Create 3 ReCall Configurations with 10 VINs each.
		logPath = LogReports.logGeneration(testname, "Create_3_ImportedRecalls");
		this.setLogPath(logPath);
		RECALL_ID1 = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateImportedRecall.createImportedRecall2(RECALL_ID1,"recall-ouqa-10737-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(campaign1,RECALL_ID1,testname);     
		RECALL_ID2 = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateImportedRecall.createImportedRecall2(RECALL_ID2,"recall-ouqa-10737-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(campaign2,RECALL_ID2,testname);     
		RECALL_ID3 = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateImportedRecall.createImportedRecall2(RECALL_ID3,"recall-ouqa-10737-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(campaign3,RECALL_ID3,testname);
		InboxRecall.assertRecallInbox(testname, RECALL_ID1);
		InboxRecall.assertRecallInbox(testname, RECALL_ID2);
		InboxRecall.assertRecallInbox(testname, RECALL_ID3);
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
