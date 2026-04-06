package testCases.Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualBatch;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_13_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62299")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_62299_RECALL2_REG_UNECE_VW_OCU3_StatusChangeCheck_VCSO_38_VCSO_1 extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "RECALL2_REG_UNECE_VW_OCU3_StatusChangeCheck_VCSO_38_VCSO_1";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String vin3 = ReadTestParameters.getAttributeValue(testname, "vin3");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String criterion02 = ReadTestParameters.getAttributeValue(testname, "criterion02");
	String firstManualBatchName = ReadTestParameters.getAttributeValue(testname, "firstManualBatchName");
	String firstBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "firstBatchImporterValue");
	String secoundManualBatchName = ReadTestParameters.getAttributeValue(testname, "secoundManualBatchName");
	String secoundBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "secoundBatchImporterValue");
	String thirdManualBatchName = ReadTestParameters.getAttributeValue(testname, "thirdManualBatchName");
	String thirdBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "thirdBatchImporterValue");
	String firstBatchMbtValue = null;
	String secondBatchMbtValue = null;
	String thirdBatchMbtValue = null;

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

		this.setLogPath("");
		this.logStepPassed(logPath);//1

		this.setLogPath("");
		this.logStepPassed(logPath);//2

		this.setLogPath("");
		this.logStepPassed(logPath);//3

		logPath = LogReports.logGeneration(testname, "CreateManualBatchesForBothCriterions");
		this.setLogPath(logPath);
		CreateManualBatch.assertManualBatchCreation(testname, campaign, criterion, firstManualBatchName,
				firstBatchImporterValue, firstBatchMbtValue);
		CreateManualBatch.assertManualBatchCreation(testname, campaign, criterion, secoundManualBatchName,
				secoundBatchImporterValue, secondBatchMbtValue);
		CreateManualBatch.assertManualBatchCreation(testname, campaign, criterion, thirdManualBatchName,
				thirdBatchImporterValue, thirdBatchMbtValue);
		CreateManualBatch.assertManualBatchCreation(testname, campaign, criterion02, firstManualBatchName,
				firstBatchImporterValue, firstBatchMbtValue);
		CreateManualBatch.assertManualBatchCreation(testname, campaign, criterion02, secoundManualBatchName,
				secoundBatchImporterValue, secondBatchMbtValue);
		CreateManualBatch.assertManualBatchCreation(testname, campaign, criterion02, thirdManualBatchName,
				thirdBatchImporterValue, thirdBatchMbtValue);
		this.logStepPassed(logPath);//4

		logPath = LogReports.logGeneration(testname, "ApproveThreeManualBatches");
		this.setLogPath(logPath);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, firstManualBatchName);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, secoundManualBatchName);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, thirdManualBatchName);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin3, campaign, "5");
		this.logStepPassed(logPath);//5

		logPath = LogReports.logGeneration(testname, "ReImportFirstRecallWithDT02False");
		this.setLogPath(logPath);
		CreateImportedRecall.createImportedRecall2(campaign, "recall-ouqa-62299-b-minimal-data-record.json");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "38");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "38");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin3, campaign, "38");
		this.logStepPassed(logPath);//6

		logPath = LogReports.logGeneration(testname, "VCSOStatus38After5Mins");
		this.setLogPath(logPath);
		Thread.sleep(500000);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "38");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "38");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin3, campaign, "38");
		this.logStepPassed(logPath);//7

		logPath = LogReports.logGeneration(testname, "OAuthToken,UploadInstallBase,PutVCAndResultCode4-13ForThreeVins");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		ResultCode4_13_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);

		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin2);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin2);
		ResultCode4_13_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin2, campaign, criterion);

		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin3);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin3);
		ResultCode4_13_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin3, campaign, criterion);
		this.logStepPassed(logPath);//8

		this.setLogPath("");
		this.logStepPassed(logPath);//9

		this.setLogPath("");
		this.logStepPassed(logPath);//10

		this.setLogPath("");
		this.logStepPassed(logPath);//11

		logPath = LogReports.logGeneration(testname, "FirstCampaignVCSOStatus");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "55");
		CampaignHistory.assertCampaignHistory(testname, vin2, campaign, "55");
		CampaignHistory.assertCampaignHistory(testname, vin3, campaign, "55");
		this.logStepPassed(logPath);//12

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
