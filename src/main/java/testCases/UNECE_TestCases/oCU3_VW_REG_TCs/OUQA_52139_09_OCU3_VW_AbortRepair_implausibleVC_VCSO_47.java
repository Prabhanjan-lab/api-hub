package testCases.UNECE_TestCases.oCU3_VW_REG_TCs;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.reusable_TestSteps.BlockedVinStatus;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.VehicleUnblock;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_ImplausibleVC_OCU3VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_13_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;

public class OUQA_52139_09_OCU3_VW_AbortRepair_implausibleVC_VCSO_47 extends TestVariables {
	@Test
	//TODO: Test format?
	public void OCU3VWImplausibleVC() throws Exception {
		String testname="OCU3VW_ImplausibleVC";
		LogReports.logs(testname);
/*
		LogReports.logGeneration(testname, "Rewind");
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname);
		
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname);

		LogReports.logGeneration(testname, "ImplausiblePutVC");
		PutVC_ImplausibleVC_OCU3VW.assertPutVC_OCU3VW(testname);

		LogReports.logGeneration(testname, "Advice");
		Advice_OCU3_VW.assertAdviceAbortRepairImplausible_OCU3_VW(testname,PutVC2Hash);

		LogReports.logGeneration(testname, "VCSOStatus38");
		ActiveQueueVCSOStatus.assertActiveQueue(testname,"38");

		LogReports.logGeneration(testname, "ResultCode4-13");
		ResultCode4_13_OCU3_VW.assertResultCode_OCU3VW(testname,PutVC2Hash);
		
		LogReports.logGeneration(testname, "VCSOStatus47");
		ActiveQueueVCSOStatus.assertActiveQueue(testname,"47");
		
		LogReports.logGeneration(testname, "BlockedVin");
		BlockedVinStatus.assertBlockedVinStatus(testname);
		
		LogReports.logGeneration(testname, "vehicleUnblock");
		VehicleUnblock.assertunblock(testname);
		
		LogReports.logGeneration(testname, "VCSOStatus5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname,"5");
*/		
		System.out.println("Test Execution completed");

	}

}
