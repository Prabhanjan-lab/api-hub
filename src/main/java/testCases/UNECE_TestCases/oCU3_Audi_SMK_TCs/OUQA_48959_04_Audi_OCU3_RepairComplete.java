package testCases.UNECE_TestCases.oCU3_Audi_SMK_TCs;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Advice.oCU3_Audi.Advice_OCU3_Audi;
import testCases.testStep_Installbase.oCU3_Audi.UploadInstallbase_OCU3_Audi;
import testCases.testStep_PutVC.oCU3_Audi.PutVC_RepairComplete_OCU3Audi;
import testCases.testStep_ResultCode.oCU3_Audi.ResultCode2_OCU3_Audi;
import testCases.testStep_StatusCode.oCU3_Audi.StatusCode1_OCU3_Audi;
import testCases.testStep_StatusCode.oCU3_Audi.StatusCode8_OCU3_Audi;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;

public class OUQA_48959_04_Audi_OCU3_RepairComplete extends TestVariables {
	String testname = "OCU3Audi_RepairComplete";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	@Test
	// TODO: test format?
	public void OCU3AudiRepairComplete() throws Exception {

		LogReports.logs(testname);

		LogReports.logGeneration(testname, "Rewind");
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_Audi.assertInstallbase_OCU3Audi();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairComplete_OCU3Audi.assertPutVC_OCU3Audi(testname, vin);

		LogReports.logGeneration(testname, "Advice");
		Advice_OCU3_Audi.assertAdviceRepairComplete_OCU3Audi(testname, PutVCHash, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSOStatus12");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");

		LogReports.logGeneration(testname, "StatusCode1");
		StatusCode1_OCU3_Audi.assertStatusCode_OCU3Audi(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSOStatus16");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "16");

		LogReports.logGeneration(testname, "StatusCode3");
		StatusCode8_OCU3_Audi.assertStatusCode_OCU3Audi(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "ResultCode2");
		ResultCode2_OCU3_Audi.assertResultCode_OCU3Audi(testname, PutVCHash, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSOStatus30");
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "30");

		System.out.println("Test Execution completed");
	}
}
