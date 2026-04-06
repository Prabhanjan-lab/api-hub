package testCases.UNECE_TestCases.oCU3_Audi_REG_TCs;

import java.io.IOException;

import org.junit.Test;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Installbase.oCU3_Audi.UploadInstallbase_OCU3_Audi;
import testCases.testStep_PutVC.oCU3_Audi.PutVC_RepairNecessary_OCU3Audi;
import testCases.testStep_VetoAdvice.oCU3_Audi.vetoAdvice_OCU3Audi;
import testCases.utils.LogReports;

public class OUQA_48967_08_AUDI_OCU3_veto_advice_ivdProgHash {
	String testname = "OCU3Audi_VetoAdvice";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	@Test
	public void OCU3AudiVetoAdvice() throws IOException, InterruptedException {

		// TODO: Test format?
		LogReports.logs(testname);

		LogReports.logGeneration(testname, "Rewind");
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_Audi.assertInstallbase_OCU3Audi();

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3Audi.assertPutVC_OCU3Audi(testname, vin);

		LogReports.logGeneration(testname, "VetoAdvice");
		vetoAdvice_OCU3Audi.assertVetoAdvice_OCU3Audi(testname, vin, campaign, criterion);

		LogReports.logGeneration(testname, "VCSOStatus12");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");

		LogReports.logGeneration(testname, "ForceAbort");
		ForceAbort.assertForceAbort(testname, vin, campaign, criterion);

		System.out.println("Test Execution completed");
	}
}
