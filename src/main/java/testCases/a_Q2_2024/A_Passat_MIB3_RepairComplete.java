package testCases.a_Q2_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW_ODP;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-17805123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
public class A_Passat_MIB3_RepairComplete extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "Passat_MIB3_RepairComplete";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	// TODO: setUp notwendig?
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
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstalbase_MIB3_VW_ODP.assertInstallbase_MIB3VW_ODP();

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
