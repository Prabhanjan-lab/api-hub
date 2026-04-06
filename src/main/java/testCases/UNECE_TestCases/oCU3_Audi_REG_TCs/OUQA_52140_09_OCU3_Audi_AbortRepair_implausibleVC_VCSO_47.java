package testCases.UNECE_TestCases.oCU3_Audi_REG_TCs;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.BlockedVinStatus;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.reusable_TestSteps.VehicleUnblock;
import testCases.testStep_Advice.oCU3_Audi.Advice_OCU3_Audi;
import testCases.testStep_Installbase.oCU3_Audi.UploadInstallbase_OCU3_Audi;
import testCases.testStep_PutVC.oCU3_Audi.PutVC_ImplausibleVC_OCU3Audi;
import testCases.testStep_PutVC.oCU3_Audi.PutVC_RepairNecessary_OCU3Audi;
import testCases.testStep_ResultCode.oCU3_Audi.ResultCode4_13_OCU3_Audi;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;

public class OUQA_52140_09_OCU3_Audi_AbortRepair_implausibleVC_VCSO_47 extends TestVariables {
	String testname = "OCU3Audi_ImplausibleVC";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	@Test
	public void OCU3AudiImplausibleVC() throws Exception {
		// TODO: test format?

		LogReports.logs(testname);

		LogReports.logGeneration(testname, "Rewind_Campaign");
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_Audi.assertInstallbase_OCU3Audi();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3Audi.assertPutVC_OCU3Audi(testname, vin);

		LogReports.logGeneration(testname, "ImplausiblePutVC");
		PutVC_ImplausibleVC_OCU3Audi.assertPutVCImplausible_OCU3Audi(testname, vin);

		LogReports.logGeneration(testname, "Advice");
		Advice_OCU3_Audi.assertAdviceAbortRepairImplausible_OCU3_Audi(testname, PutVC2Hash, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSOStatus38");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "38");

		LogReports.logGeneration(testname, "ResultCode4-13");
		ResultCode4_13_OCU3_Audi.assertResultCode_OCU3Audi(testname, PutVC2Hash, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSOStatus47");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "47");

		LogReports.logGeneration(testname, "BlockedVin");
		BlockedVinStatus.assertBlockedVinStatus(testname, campaign, criterion);

		LogReports.logGeneration(testname, "vehicleUnblock");
		VehicleUnblock.assertunblock(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSOStatus5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "5");

		System.out.println("Test Execution completed");

	}

}
