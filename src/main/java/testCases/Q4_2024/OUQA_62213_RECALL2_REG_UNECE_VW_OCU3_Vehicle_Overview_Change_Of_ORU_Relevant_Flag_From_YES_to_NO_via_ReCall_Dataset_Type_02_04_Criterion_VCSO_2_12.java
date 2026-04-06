package testCases.Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3VW_NONUNECE_Carport;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_UNECE_Carport;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_13_OCU3_VW_Carport;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62213")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_62213_RECALL2_REG_UNECE_VW_OCU3_Vehicle_Overview_Change_Of_ORU_Relevant_Flag_From_YES_to_NO_via_ReCall_Dataset_Type_02_04_Criterion_VCSO_2_12 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "RECALL2_VW_OCU3_Vehicle_Overview_Change_Of_ORU_Relevant_Flag_From_YES_to_NO_via_ReCall_Dataset_Type_02_04_Criterion_VCSO_2_12";
	String campaign1 = ReadTestParameters.getAttributeValue(testname, "campaign1");
	String campaign2 = ReadTestParameters.getAttributeValue(testname, "campaign2");
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String keystorePath = TestVariables.getKeyStorePath("BASE_" + vin);
	char[] password = vin.toCharArray();
	
	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(12);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		logPath = LogReports.logGeneration(testname, "AssertCampaignsVCSO_5_2");
		this.setLogPath(logPath);
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign1, criterion);
		ApproveBatch.assertApproveBatch(testname, campaign1, criterion, batchName);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign1, "5");
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign2, criterion);
		ApproveBatch.assertApproveBatch(testname, campaign2, criterion, batchName);
		Thread.sleep(10000);
		ScheduledQueue.assertScheduledCampaign(testname, vin, campaign2);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname,keystorePath, password);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW_UNECE_Carport.assertPutVC_OCU3VW_UNECE_Carport(testname,vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_OCU3VW_NONUNECE_Carport.assertAdviceRepairNecessary_OCU3VW_NONUNECE_Carport(testname, TestVariables.PutVCHash,vin,campaign1,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus12");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin,campaign1, "12");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertCampaignsVCSO_12_2");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign1, "12");
		ScheduledQueue.assertScheduledCampaign(testname, vin, campaign2);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Revoke_DST02_DST04_For_BothRecalls");
		this.setLogPath(logPath);
		CreateImportedRecall.createImportedRecall2(campaign1,"recall-ouqa-62213-b-minimal-data-record.json");
		CreateImportedRecall.createImportedRecall2(campaign2,"recall-ouqa-62213-b-minimal-data-record.json");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertCampaignsVCSO_38_55");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign1, "38");
		CampaignHistory.assertCampaignHistory(testname, vin, campaign2, "55");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();
		PutVC_RepairNecessary_OCU3VW_UNECE_Carport.assertPutVC_OCU3VW_UNECE_Carport(testname,vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "ResultCode4-13");
		this.setLogPath(logPath);
		ResultCode4_13_OCU3_VW_Carport.assertResultCode_OCU3VW_Carport(testname, TestVariables.PutVCHash, vin, campaign1, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertCampaignsVCSO_55_55");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign1, "55");
		CampaignHistory.assertCampaignHistory(testname, vin, campaign2, "55");
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
