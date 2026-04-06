package testCases.a_Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62221123")
@Tag("Approval_ECE")

public class A_NON_UNECE_Vehicle_Notification_not_sent_to_Campaign_at_VCSO_42 extends AbstractStandardBehaviour{
	
	String logPath = "";
	String testname = "NON_UNECE_Vehicle_Notification_not_sent_to_Campaign_at_VCSO_42";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	
	String keystorePath = TestVariables.getKeyStorePath("BASE_Approval_" + vin);
	char[] password = vin.toCharArray();

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(1); 
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		LogReports.logGeneration(testname, "OAuthToken");
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();

		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		System.out.println("A-Conditon Execution Completed.");
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
