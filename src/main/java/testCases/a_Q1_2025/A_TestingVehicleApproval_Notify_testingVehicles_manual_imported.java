package testCases.a_Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-1587123")
@Tag("Demo_ECE")

public class A_TestingVehicleApproval_Notify_testingVehicles_manual_imported extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "TestingVehicleApproval_Notify_testingVehicles_manual_imported";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");

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

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		
		LogReports.logGeneration(testname, "PutVCForFirstVin");
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin);

		logPath = LogReports.logGeneration(testname, "RecallCreation");
		this.setLogPath(logPath);
		String recallID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateManualRecall.assertCreateManualRecall(testname, TestVariables.OUQA_1587_filePath, recallID);
		UpdateTestParameters.updateAttributeValueInJson("campaign", recallID, testname);
		this.logStepPassed(logPath);

		String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		logPath = LogReports.logGeneration(testname, "ORUCreation");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname, campaign, criterion);
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}