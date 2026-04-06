package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.CreateAutoApprovedBatch;
import testCases.reusable_TestSteps.GetChargeStatus;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Advice.oCU3_VW.Advice_notranslation_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_Package.oCU3_VW.packages_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode1_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode1_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode3_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode4_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode7_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode8_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;
@Tag("OUQA-62292")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")
public class OUQA_62292_UNECE_REG_An_automaticallyBatch_hasToReleaseAutomatically_if_PreviousBatchReaches_ThePOC
extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "VWOCU3_UNECE_REG_An_automaticallyBatch_hasToReleaseAutomatically_if_PreviousBatchReaches_ThePOC";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String aaFirstBatchName = ReadTestParameters.getAttributeValue(testname, "aaFirstBatchName");
	String aaSecondBatchName = ReadTestParameters.getAttributeValue(testname, "aaSecondBatchName");
	String firstBatchPredecessorValue = null;
	String secondBatchPredecessorValue = ReadTestParameters.getAttributeValue(testname, "secondBatchPredecessorValue");
	String firstBatchTargetValue = ReadTestParameters.getAttributeValue(testname, "firstBatchTargetValue");
	String secondBatchTargetValue = ReadTestParameters.getAttributeValue(testname, "secondBatchTargetValue");
	String firstBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "firstBatchImporterValue");
	String secondBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "secondBatchImporterValue");
	String firstBatchMbtValue = null;
	String secondBatchMbtValue = null;
	String firstBatchPknvalue = null;
	String secondBatchPknvalue = null;

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(11);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath("");
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname,campaign,criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		logPath = LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();

		logPath = LogReports.logGeneration(testname, "OAuthTokenForSecondVin");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin2);
		logPath = LogReports.logGeneration(testname, "UploadIBForSecondVin");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();

		logPath = LogReports.logGeneration(testname, "CreateAutoApprovedBatch1");
		this.setLogPath(logPath);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign,
				criterion, aaFirstBatchName, firstBatchPredecessorValue,
				firstBatchTargetValue, firstBatchImporterValue, firstBatchMbtValue, firstBatchPknvalue);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CreateAutoApprovedBatch2");
		this.setLogPath(logPath);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign,
				criterion, aaSecondBatchName, secondBatchPredecessorValue,
				secondBatchTargetValue, secondBatchImporterValue, secondBatchMbtValue, secondBatchPknvalue);
		this.logStepPassed(logPath);


		logPath = LogReports.logGeneration(testname, "AssertStatusOfBatches");
		this.setLogPath(logPath);
		ApproveBatch.approveBatch(testname, campaign, criterion, aaFirstBatchName);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "5");
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, aaFirstBatchName);
		GetChargeStatus.assertChargeStatusNotReleased(testname, campaign, criterion, aaSecondBatchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AdviceForSecondVin");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin2);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin2);
		Advice_notranslation_OCU3_VW.assertAdviceAbortRepair_OCU3_VW(testname, vin2, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "36");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertChargeStatus");
		this.setLogPath(logPath);
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, aaFirstBatchName);
		GetChargeStatus.assertChargeStatusNotReleased(testname, campaign, criterion, aaSecondBatchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "RepairDone");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		StatusCode1_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "16");
		packages_OCU3_VW.assertPackagesRepairDone_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "19");
		StatusCode3_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "20");
		StatusCode4_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		StatusCode7_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		StatusCode8_OCU3_VW.assertStatusCode_OCU3VW(testname, vin, campaign, criterion);
		ResultCode1_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "30");
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, aaFirstBatchName);
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, aaSecondBatchName);
		this.logStepPassed(logPath);

		System.out.println("Test Execution Completed.");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}