package testCases.a_UNECE_TM1_REG_Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Installbase.tM1_VW.UploadInstallbase_TM1_VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-52156123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
public class A_TM1_AbortRepair_Incorrect_Campaign_Progress extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "TM1VW_IncorrectCampaignProgress";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(1); // test has one test step
		}
	}

	@Override
	protected void tearDownHook() throws Throwable {
		LogReports.logs(testname);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_TM1_VW.assertInstallbase_TM1VW();

		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

	}

	@Override
	protected void testHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
