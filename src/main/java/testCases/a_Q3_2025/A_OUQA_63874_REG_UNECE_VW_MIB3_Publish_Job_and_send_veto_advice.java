package testCases.a_Q3_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.reusable_TestSteps.SkipMassnotificationFalse;
import testCases.testStep_Installbase.mIB3_VW.UploadInstallbase_MIB3_VW_MQTT;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-63874123")
@Tag("Approval_ECE")

public class A_OUQA_63874_REG_UNECE_VW_MIB3_Publish_Job_and_send_veto_advice extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "UNECE_VW_MIB3_Publish_Job_and_send_veto_advice";
	private String campaign = "campaign";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String batch = ReadTestParameters.getAttributeValue(testname, "batchName");



	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(4);
		}
	}


	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);
		
		//Ouath, IB and Skipmass false
		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin);
		UploadInstallbase_MIB3_VW_MQTT.assertInstallbase_MIB3VW_MQTT();
		SkipMassnotificationFalse.assertSkipMassnotificationFalse(testname, vin);
		this.logStepPassed(logPath);//1
		
		//Recall creation
		logPath = LogReports.logGeneration(testname, "Recall_Creation");
		this.setLogPath(logPath);
		String recallID=CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateManualRecall.assertCreateManualRecall(testname, TestVariables.OUQA_63874_filePath,recallID);
		System.out.println("Recall ID : "+recallID + " created successfully");
		this.logStepPassed(logPath);//2

		//ORU creation
		logPath = LogReports.logGeneration(testname, "ORU_Creation");
		this.setLogPath(logPath);
		System.out.println("Campaign ID : "+recallID + " creating...");
		CreateORUCampaign.assertCreateORU(testname, recallID, criterion);
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, recallID, criterion);
		UpdateTestParameters.updateAttributeValueInJson(this.campaign, recallID, testname);
		System.out.println("Campaign ID : "+recallID + " created successfully");
		this.logStepPassed(logPath);//3

		//Approve Batch
		logPath = LogReports.logGeneration(testname, "ApproveCampaign");
		this.setLogPath(logPath);
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, recallID, criterion);
		ApproveBatch.assertApproveBatch(testname, recallID, criterion, batch);
		this.logStepPassed(logPath);//4
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
