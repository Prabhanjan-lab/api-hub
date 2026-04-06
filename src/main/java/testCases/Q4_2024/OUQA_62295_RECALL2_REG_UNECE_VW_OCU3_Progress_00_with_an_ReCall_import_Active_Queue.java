package testCases.Q4_2024;

import org.junit.jupiter.api.Tag;


import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_13_OCU3_VW_Carport;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

/**
 * @author Sulzer GmbH
 */
@Tag("OUQA-62295")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")


public class OUQA_62295_RECALL2_REG_UNECE_VW_OCU3_Progress_00_with_an_ReCall_import_Active_Queue extends AbstractStandardBehaviour {

    // ReCall entries
	String testname="RECALL2_REG_UNECE_VW_OCU3_Progress_00_with_an_ReCall_import_Active_Queue";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign1 = ReadTestParameters.getAttributeValue(testname, "campaign1");
    String RECALL_ID2;
    String campaign2="campaign2";
    String keystorePath = TestVariables.getKeyStorePath("BASE_" + vin);
	char[] password = vin.toCharArray();
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
            this.initTestContainer(11); // test has 11 test steps
        }
    }

    @Override
    protected void testHook() throws Throwable {
    	LogReports.logs(testname);
    	
    	logPath = LogReports.logGeneration(testname, "VCSOStatus5ForFirstCampaign");
		this.setLogPath(logPath);
    	ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign1, "5");
    	this.logStepPassed(logPath);
    	
   
//		Create another ReCall Configuration with same VIN.
		logPath = LogReports.logGeneration(testname, "CreateImportedRecall2");
		this.setLogPath(logPath);
		RECALL_ID2 = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
    	CreateImportedRecall.createImportedRecall2(RECALL_ID2,"recall-ouqa-62295-b-minimal-data-record.json");
        UpdateTestParameters.updateAttributeValueInJson(campaign2,RECALL_ID2,testname); 
        campaign2=ReadTestParameters.getAttributeValue(testname, "campaign2");
    	Thread.sleep(60000);
        CreateORUCampaign.assertCreateORU(testname,campaign2,criterion);
    	GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, campaign2, criterion);
    	MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign2, criterion);
    	ApproveBatch.assertApproveBatch(testname, campaign2, criterion, batchName);
    	Thread.sleep(10000);
    	ScheduledQueue.assertScheduledCampaign(testname, vin, campaign2);
        this.logStepPassed(logPath);
        
        logPath = LogReports.logGeneration(testname, "ReImportFirstRecallWithProgress00");
		this.setLogPath(logPath);
		CreateImportedRecall.createImportedRecall2(campaign1,"recall-ouqa-62295-c-minimal-data-record.json");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "VCSOStatus38ForFirstCampaign");
		this.setLogPath(logPath);
    	Thread.sleep(60000);
    	ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign1, "38");
    	this.logStepPassed(logPath);
    	
    	this.setLogPath(logPath);
    	this.logStepPassed(logPath);
    	
    	logPath = LogReports.logGeneration(testname, "NoCancelUpdateJobSent");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueueNotificationNoCancelUpdateJobSent(testname, vin, campaign1);
    	this.logStepPassed(logPath);
    	
    	logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
    	TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);
    	this.logStepPassed(logPath);
    	
    	logPath = LogReports.logGeneration(testname, "UploadInstallBase");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();
    	this.logStepPassed(logPath);
    	
    	logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport.assertPutVC_OCU3VW_NONUNECE_Carport(testname,vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "ResultCode4-13");
		this.setLogPath(logPath);
		ResultCode4_13_OCU3_VW_Carport.assertResultCode_OCU3VW_Carport(testname, TestVariables.PutVCHash,vin,campaign1,criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "FirstCampaignVCSOStatus");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign1, "55");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "SecondCampaignVCSOStatus");
		this.setLogPath(logPath);
		Thread.sleep(250000);
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin,campaign2, "5");
		this.logStepPassed(logPath);
		 	
    }

	@Override
    public void tearDownHook() {

    }

}