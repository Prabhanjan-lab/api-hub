package testCases.UNECE_TestCases.oCU3_Audi_SMK_TCs;

import java.io.IOException;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.testStep_Advice.oCU3_Audi.Advice_OCU3_Audi;
import testCases.testStep_Installbase.oCU3_Audi.UploadInstallbase_OCU3_Audi;
import testCases.testStep_Package.oCU3_Audi.packages_OCU3_Audi;
import testCases.testStep_PutVC.oCU3_Audi.PutVC_RepairNecessary_OCU3Audi;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;

public class OUQA_48962_02_Audi_OCU3_AbortRepair_No_approval_for_campaign_and_vehicle_found extends TestVariables {
	String testname = "OCU3Audi_NoApproval";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	@Test
	//TODO: test format?
	public void OCU3AudiNoApproval() throws IOException {
		
		LogReports.logs(testname);

		LogReports.logGeneration(testname,"MoveVinToBatch");
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign, criterion);
		
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_Audi.assertInstallbase_OCU3Audi();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3Audi.assertPutVC_OCU3Audi(testname, vin);

		LogReports.logGeneration(testname, "Advice");
		Advice_OCU3_Audi.assertAdviceAbortRepair_OCU3_Audi(testname,PutVCHash, vin, campaign, criterion );

		LogReports.logGeneration(testname, "Package");
		packages_OCU3_Audi.assertPackagesNoApproval_OCU3Audi(testname, vin, campaign, criterion);

		System.out.println("Test Execution completed");
	}

}
