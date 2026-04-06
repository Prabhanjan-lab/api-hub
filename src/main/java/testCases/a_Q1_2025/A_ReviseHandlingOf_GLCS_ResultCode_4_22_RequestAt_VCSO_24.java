package testCases.a_Q1_2025;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.reusable_TestSteps.RewindCampaignWithoutSkipMassnotification;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-61327123")
@Tag("Approval_ECE")
@Tag("25Q1")
public class A_ReviseHandlingOf_GLCS_ResultCode_4_22_RequestAt_VCSO_24 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "ReviseHandlingOf_GLCS_ResultCode_4_22_RequestAt_VCSO_24";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String keystorePath = TestVariables.getKeyStorePath("BASE_" + vin);
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
			this.initTestContainer(1); // test has six test step
		}
	}

	@Override
	protected void testHook() throws Throwable, IOException {
		LogReports.logs(testname);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();

		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
