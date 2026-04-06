package testCases.Q2_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.CreateAutoApprovedBatch;
import testCases.reusable_TestSteps.GetChargeStatus;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode1_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-59776")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q2")
public class OUQA_59776_UNECE_REG_Using_several_Batches_Automatic_approval_of_batches_has_to_work
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "VWOCU3_UNECE_REG_Using_several_Batches_Automatic_approval_of_batches_has_to_work";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String firstBatchName = ReadTestParameters.getAttributeValue(testname, "firstBatchName");
	String firstBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "firstBatchImporterValue");
	String firstAutoApprovedBatchTargetValue = ReadTestParameters.getAttributeValue(testname, "firstBatchTargetValue");
	String firstBatchPredecessorValue = null;
	String secondBatchPredecessorValue = ReadTestParameters.getAttributeValue(testname, "secondBatchPredecessorValue");
	String secondBatchName = ReadTestParameters.getAttributeValue(testname, "secondBatchName");
	String secondBatchImporterValue = ReadTestParameters.getAttributeValue(testname, "secondBatchImporterValue");
	String secondAutoApprovedBatchTargetValue = ReadTestParameters.getAttributeValue(testname,
			"secondBatchTargetValue");
	String firstBatchMbtValue = null;
	String secondBatchMbtValue = null;
	String firstBatchPknValue = null;
	String secondBatchPknValue = null;
	

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(7);
		}
	}

	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CreateAutoApprovedBatch1");
		this.setLogPath(logPath);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign, criterion, firstBatchName,
				firstBatchPredecessorValue, firstAutoApprovedBatchTargetValue, firstBatchImporterValue,
				firstBatchMbtValue, firstBatchPknValue);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "CreateAutoApprovedBatch2");
		this.setLogPath(logPath);
		CreateAutoApprovedBatch.assertAutoApprovedBatchCreation(testname, campaign, criterion, secondBatchName,
				secondBatchPredecessorValue, secondAutoApprovedBatchTargetValue, secondBatchImporterValue,
				secondBatchMbtValue, secondBatchPknValue);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApproveAutoApprovedBatch");
		this.setLogPath(logPath);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, firstBatchName);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode1");
		this.setLogPath(logPath);
		ResultCode1_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "30");
		// GetChargeStatus.assertChargeCurrentValue(testname, campaign, criterion, "100", firstBatchName); //Current value functionality is removed
		GetChargeStatus.assertChargeTargetValue(testname, campaign, criterion, firstBatchName,
				firstAutoApprovedBatchTargetValue);
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, secondBatchName);
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
