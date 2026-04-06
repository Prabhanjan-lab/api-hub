package testCases.UNECE_TestCases.oCU3_Audi_REG_TCs;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.testStep_Advice.oCU3_Audi.Advice_OCU3_Audi;
import testCases.testStep_Installbase.oCU3_Audi.UploadInstallbase_OCU3_Audi;
import testCases.testStep_Package.oCU3_Audi.packages_OCU3_Audi;
import testCases.testStep_PutVC.oCU3_Audi.PutVC_RepairComplete_OCU3Audi;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;

public class OUQA_52138_07_OCU3_Audi_AbortRepair_Campaign_not_active extends TestVariables {
	String testname="OCU3Audi_CampaignNotActive";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	
	@Test
	public void OCU3AudiCampaignNotActive() throws Exception {

		//TODO: test format?
		LogReports.logs(testname);

		LogReports.logGeneration(testname, "VCSOStatus37");
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
		
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_Audi.assertInstallbase_OCU3Audi();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairComplete_OCU3Audi.assertPutVC_OCU3Audi(testname, vin);

		LogReports.logGeneration(testname, "Advice");
		Advice_OCU3_Audi.assertAdviceAbortRepair_OCU3_Audi(testname,PutVCHash, vin, campaign, criterion);

		LogReports.logGeneration(testname, "AfterAdviceVCSOStatus37");
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");
 
		LogReports.logGeneration(testname, "Package");
		packages_OCU3_Audi.assertPackagesCampaignNotActive_OCU3Audi(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "AfterPackageVCSOStatus37");
		CampaignHistory.assertCampaignHistory(testname, vin, campaign, "37");

		System.out.println("Test Execution completed");

	}

}
