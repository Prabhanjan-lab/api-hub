package testCases.a_Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62295123")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class A_RECALL2_REG_UNECE_VW_OCU3_Progress_00_with_an_ReCall_import_Active_Queue
		extends AbstractStandardBehaviour {

	String testname="RECALL2_REG_UNECE_VW_OCU3_Progress_00_with_an_ReCall_import_Active_Queue";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String keystorePath = TestVariables.getKeyStorePath("BASE_"+vin);
	char[] password = vin.toCharArray();
    private String RECALL_ID1;
    private String campaign1="campaign1";
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
			this.initTestContainer(2);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		//Create a ReCall Configuration with one VIN.
		logPath = LogReports.logGeneration(testname, "CreateImportedRecall");
		this.setLogPath(logPath);
		RECALL_ID1 = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
    	CreateImportedRecall.createImportedRecall2(RECALL_ID1,"recall-ouqa-62295-a-minimal-data-record.json");
        UpdateTestParameters.updateAttributeValueInJson(campaign1,RECALL_ID1,testname);     
        CreateORUCampaign.assertCreateORU(testname,RECALL_ID1,criterion);
    	GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, RECALL_ID1, criterion);
    	
    	//Generate Oauth Token for Vin
    	TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname,keystorePath,password);
    	
    	//Upload IB for vin
    	UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();
    	
    	//PutVC for vin
    	PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport.assertPutVC_OCU3VW_NONUNECE_Carport(testname,vin);
    	
		//Move vin into default batch
    	MoveVinToDefaultBatch.assertMoveVinToBatch(testname, RECALL_ID1, criterion);
    	
    	//Approve default Batch
    	ApproveBatch.assertApproveBatch(testname, RECALL_ID1, criterion, batchName);
    	//ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, RECALL_ID1, "5");
    	this.logStepPassed(logPath); // 1
    	 	

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}