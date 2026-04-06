package testCases.a_Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-65734123")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class A_RECALL2_UNECE_REG_VWOCU3_Importing_ReCall_Actions_Type_04_Part_of_ORU_Campaign_ORU_Relevance_Yes_No extends AbstractStandardBehaviour{
	String testname="RECALL2_UNECE_REG_VWOCU3_Importing_ReCall_Actions_Type_04_Part_of_ORU_Campaign_ORU_Relevance_Yes_No";
    
    String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
    String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
    String logPath = "";
    
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
		
		
		//Create a ReCall Configuration with one VIN.
		logPath = LogReports.logGeneration(testname, "CreateImportedRecall");
		this.setLogPath(logPath);
		CreateImportedRecall.createImportedRecall2(campaign, "recall-ouqa-65734-a-minimal-data-record.json");

	    CreateORUCampaign.assertCreateORU(testname, campaign, criterion);
	    GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, campaign, criterion);
	    MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign, criterion);
	    this.logStepPassed(logPath);
	    
	        
	    System.out.println("A condition execution completed.");
	}
	
	@Override
	protected void tearDownHook() throws Throwable {
		
	}
}
