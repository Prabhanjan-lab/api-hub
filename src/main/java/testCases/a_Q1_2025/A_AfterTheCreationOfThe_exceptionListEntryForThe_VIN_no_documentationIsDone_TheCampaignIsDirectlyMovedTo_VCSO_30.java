package testCases.a_Q1_2025;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-60475123")
@Tag("Approval_ECE")

public class A_AfterTheCreationOfThe_exceptionListEntryForThe_VIN_no_documentationIsDone_TheCampaignIsDirectlyMovedTo_VCSO_30
		extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "AfterTheCreationOfThe_exceptionListEntryForThe_VIN_no_documentationIsDone_TheCampaignIsDirectlyMovedTo_VCSO_30";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
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
	protected void testHook() throws Throwable, IOException {
		LogReports.logs(testname);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();

		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin,
				campaign, criterion);
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}