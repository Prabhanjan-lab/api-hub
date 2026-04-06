package testCases.UNECE_TestCases.oCU3_Audi_REG_TCs;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.DeapproveBatch;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Advice.oCU3_Audi.Advice_OCU3_Audi;
import testCases.testStep_Installbase.oCU3_Audi.UploadInstallbase_OCU3_Audi;
import testCases.testStep_Package.oCU3_Audi.packages_OCU3_Audi;
import testCases.testStep_PutVC.oCU3_Audi.PutVC_RepairNecessary_OCU3Audi;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;

public class OUQA_52136_06_OCU3_Audi_AbortRepair_Incorrect_Campaign_Progress extends TestVariables {
	String testname="OCU3Audi_IncorrectCampaignProgress";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	@Test
	public void OCU3AudiIncorrectCampaignProgress() throws Exception {
		//TODO: Test format?

		LogReports.logs(testname);

		LogReports.logGeneration(testname, "Rewind_Campaign");
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "DeapproveBatch");
		DeapproveBatch.assertDeapproveBatch(testname, campaign, criterion, batchName);
		
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_Audi.assertInstallbase_OCU3Audi();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3Audi.assertPutVC_OCU3Audi(testname, vin);

		LogReports.logGeneration(testname, "Advice");
		Advice_OCU3_Audi.assertAdviceIncorrectCampaign_OCU3_Audi(testname,PutVCHash, vin, campaign, criterion);

		LogReports.logGeneration(testname, "Package");
		packages_OCU3_Audi.assertPackagesNoApproval_OCU3Audi(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "ForceAbort");
		ForceAbort.assertForceAbort(testname, vin, campaign, criterion);

		System.out.println("Test Execution completed");

	}

}
