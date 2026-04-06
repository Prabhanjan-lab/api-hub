package testCases.Q4_2024;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaignWithMultipleCriterion;
import testCases.reusable_TestSteps.Get_VinsForInspection;
import testCases.reusable_TestSteps.ImporterRecall_Logs;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.utils.CurrentDateTime;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;
import testFramework.utilities.recall.SshConnection;

@Tag("OUQA-63128")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_63128_RECALL2_UNECE_TestVehicleInCriterion_WhichIsNotORURelevant_DST02_ShouldNotBeReleased_WhenReleaseMonitoringJobIsRunning
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "RECALL2_UNECE_TestVehicleInCriterion_WhichIsNotORURelevant_DST02_ShouldNotBeReleased_WhenReleaseMonitoringJobIsRunning";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = "campaign";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String criterion02 = ReadTestParameters.getAttributeValue(testname, "criterion02");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String defaultBatchForVinInspection = ReadTestParameters.getAttributeValue(testname,
			"defaultBatchForVinInspection");
	String webAppLogRemoteFilePath = ReadTestParameters.getAttributeValue(testname, "webAppLogRemoteFilePath");
	String webAppLogRemoteFileName = ReadTestParameters.getAttributeValue(testname, "webAppLogRemoteFileName");
	String webAppLogLocalFileName = ReadTestParameters.getAttributeValue(testname, "webAppLogLocalFileName");
	String type = "Adapted";
	String logCheck = "01: Changed: ORU-Relevant: (N)";//need to update the logcheck
	LocalDateTime currentTimestamp = CurrentDateTime.getCurrentTimeStamp();
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

		String RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		logPath = LogReports.logGeneration(testname, "ImportFirstRecallWithTwoCriterion");
		CreateImportedRecall.createImportedRecall2(RECALL_ID, "recall-ouqa-63128-a-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(this.campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		logPath = LogReports.logGeneration(testname, "CreateORUCampaign");
		this.setLogPath(logPath);
		CreateORUCampaignWithMultipleCriterion.assertCreateORUCampaign(testname,
				TestVariables.ORUCreationWithTwoCriterions_filePath);
		Thread.sleep(660000);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "MoveVinsIntoDefaultBatch");
		this.setLogPath(logPath);
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign, criterion);
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign, criterion02);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ReImportFirstRecallWithDT02False");
		this.setLogPath(logPath);
		CreateImportedRecall.createImportedRecall2(campaign, "recall-ouqa-63128-b-minimal-data-record.json");
		ImporterRecall_Logs.assertLogDetails(testname, type, campaign, logCheck, currentTimestamp);
		UpdateTestParameters.updateAttributeValueInJson(campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "recallApprovalStatus");
		this.setLogPath(testname);
		Get_VinsForInspection.assertVinNotApprovedStatusForInsepection(testname, vin, campaign, criterion,
				defaultBatchForVinInspection);
		Get_VinsForInspection.assertVinApprovedStatusForInspection(testname, vin, campaign, criterion02,
				defaultBatchForVinInspection);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApproveBatchForFirstCriterion");
		this.setLogPath(testname);
		ApproveBatch.assertApproveBatchDisabled(testname, campaign, criterion, batchName);
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "55");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApproveBatchForSecoundCriterion");
		this.setLogPath(testname);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion02, batchName);
		Thread.sleep(3000);
		String campaignWithCriterion = campaign+"-"+criterion02;
		ActiveQueueVCSOStatus.assertActiveQueueWithWTCriterion(testname, vin, campaignWithCriterion, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "assertWebAppLog");
		this.setLogPath(logPath);
		SshConnection.downloadFileAlongWithContentViaSsh(webAppLogRemoteFilePath, webAppLogRemoteFileName);
		String webAppLogContent = SshConnection.readFileLines(webAppLogLocalFileName).toString();
		Assert.assertTrue(webAppLogContent.contains("Vehicle moved to queue:VehicleCampaign={vehicleCampaignType='ACTIVE', domain='OCU3', vin='BVWDSCSLZ24012961', OruCampaignReference={campaignNo='" +campaign+ "', criterionNo='02', manufacturer='VW'}"));
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
