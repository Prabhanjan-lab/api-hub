package testCases.UNECE_TestCases.oCU3_Audi_REG_TCs;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Advice.oCU3_Audi.Advice_noTranslation_OCU3_Audi;
import testCases.testStep_Installbase.oCU3_Audi.UploadInstallbase_OCU3_Audi;
import testCases.testStep_PutVC.oCU3_Audi.PutVC_RepairComplete_OCU3Audi;
import testCases.utils.LogReports;

public class OUQA_52135_05_OCU3_Audi_AbortRepair_No_translated_campaign_description_found_for_HMI_language {
	String testname = "OCU3Audi_NoTranslation";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	@Test
	public void OCU3AudiNoTranslation() throws Exception {
		// TODO: Test format?

		LogReports.logs(testname);

		LogReports.logGeneration(testname, "Rewind_Campaign");
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_Audi.assertInstallbase_OCU3Audi();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairComplete_OCU3Audi.assertPutVC_OCU3Audi(testname, vin);

		LogReports.logGeneration(testname, "Advice");
		Advice_noTranslation_OCU3_Audi.assertAdviceAbortRepair_OCU3_Audi(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSOStatus36");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "36");

		LogReports.logGeneration(testname, "ForceAbort");
		ForceAbort.assertForceAbort(testname, vin, campaign, criterion);

		System.out.println("Test Execution completed");

	}

}
