package testCases.UNECE_TestCases.tM1_VW_REG_TCs;

import java.io.IOException;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.reusable_TestSteps.BlockedVinStatus;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.VehicleUnblock;
import testCases.testStep_Advice.tM1_VW.Advice_TM1_VW;
import testCases.testStep_Installbase.tM1_VW.UploadInstallbase_TM1_VW;
import testCases.testStep_PutVC.TM1_VW.PutVC_ImplausibleVC_TM1VW;
import testCases.testStep_PutVC.TM1_VW.PutVC_RepairNecessary_TM1VW;
import testCases.testStep_ResultCode.tM1_VW.ResultCode4_13_TM1_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;

public class TM1_VW_ImplausibleVC_OUQA_52159 extends TestVariables {
	 @Test
	 //TODO: test format
	public void TM1_VW_ImplausibleVC() throws IOException, InterruptedException {
		String testname = "TM1VW_ImplausibleVC";
		LogReports.logs(testname);

/*		LogReports.logGeneration(testname, "Rewind");
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname);
		
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_TM1_VW.assertInstallbase_TM1VW();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_TM1VW.assertPutVC_TM1VW(testname);

		LogReports.logGeneration(testname, "ImplausiblePutVC");
		PutVC_ImplausibleVC_TM1VW.assertImplausibleVC_TM1VW(testname);
		
		LogReports.logGeneration(testname, "Advice");
		Advice_TM1_VW.assertAdviceAbortRepairImplausible_TM1_VW(testname, PutVC2Hash);

		LogReports.logGeneration(testname, "VCSOStatus38");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, "38");

		LogReports.logGeneration(testname, "ResultCode4-13");
		ResultCode4_13_TM1_VW.assertResultCode_TM1VW(testname, PutVC2Hash);

		LogReports.logGeneration(testname, "VCSOStatus47");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, "47");

		LogReports.logGeneration(testname, "BlockedVin");
		BlockedVinStatus.assertBlockedVinStatus(testname);

		LogReports.logGeneration(testname, "vehicleUnblock");
		VehicleUnblock.assertunblock(testname);

		LogReports.logGeneration(testname, "VCSOStatus5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, "5");
*/
		System.out.println("Test Execution completed");

	}

}
