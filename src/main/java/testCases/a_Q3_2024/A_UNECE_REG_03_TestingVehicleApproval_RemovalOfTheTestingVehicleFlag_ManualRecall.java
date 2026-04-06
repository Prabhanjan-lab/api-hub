package testCases.a_Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62274123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class A_UNECE_REG_03_TestingVehicleApproval_RemovalOfTheTestingVehicleFlag_ManualRecall
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "UNECE_REG_03_TestingVehicleApproval_RemovalOfTheTestingVehicleFlag_ManualRecall";
	String campaign = "campaign";
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
			this.initTestContainer(2);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		logPath = LogReports.logGeneration(testname, "CreateManualRecall");
		this.setLogPath(logPath);
		String recallID= CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateManualRecall.assertCreateManualRecall(testname, TestVariables.OUQA_62274_filePath,recallID);
		UpdateTestParameters.updateAttributeValueInJson(campaign, recallID, testname);
		this.logStepPassed(logPath);
		
		String campaign= ReadTestParameters.getAttributeValue(testname, "campaign");
		logPath = LogReports.logGeneration(testname, "CreateORUCampaign");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname,campaign,criterion);
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, campaign, criterion);
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}