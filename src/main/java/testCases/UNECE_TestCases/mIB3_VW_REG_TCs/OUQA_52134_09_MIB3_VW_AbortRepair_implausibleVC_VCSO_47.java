package testCases.UNECE_TestCases.mIB3_VW_REG_TCs;

import java.io.FileNotFoundException;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.BlockedVinStatus;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.reusable_TestSteps.Unblock_Vehicle;
import testCases.testStep_Advice.mIB3_VW.Advice_MIB3VW;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW;
import testCases.testStep_PutVC.mIB3_VW.PutVC_ImplausibleVC_MIB3VW;
import testCases.testStep_PutVC.mIB3_VW.PutVC_RepairNecessary_MIB3VW;
import testCases.testStep_ResultCode.mIB3_VW.ResultCode4_13_MIB3VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;

public class OUQA_52134_09_MIB3_VW_AbortRepair_implausibleVC_VCSO_47 extends TestVariables {
	String testname = "MIB3VW_ImplausibleVC";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	public void MIB3VWImplausibleVC() throws Exception, FileNotFoundException {

		LogReports.logs(testname);

		LogReports.logGeneration(testname, "Rewind");
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstalbase_MIB3_VW.assertInstallbase_MIB3VW();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_MIB3VW.assertPutVC_MIB3VW(testname, vin);

		LogReports.logGeneration(testname, "Putvc");
		PutVC_ImplausibleVC_MIB3VW.assertPutVC_MIB3VW(testname, vin);

		LogReports.logGeneration(testname, "Advice");
		Advice_MIB3VW.assertAdviceImplausibleVC_MIB3VW(testname, PutVC2Hash, vin, campaign, criterion);

		LogReports.logGeneration(testname, "ResultCode4-13");
		ResultCode4_13_MIB3VW.assertResultCode_MIB3VW(testname, PutVC2Hash, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSO47");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "47");

		LogReports.logGeneration(testname, "BlockedVinStatus");
		BlockedVinStatus.assertBlockedVinStatus(testname, campaign, criterion);

		LogReports.logGeneration(testname, "UnblockVin");
		Unblock_Vehicle.assertUnBlockVin(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "BlockedVinStatus");
		BlockedVinStatus.assertNoBlockedVinStatus(testname, campaign, criterion);

		LogReports.logGeneration(testname, "VCSO5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "5");

		System.out.println("Test Execution Completed");

	}

}
