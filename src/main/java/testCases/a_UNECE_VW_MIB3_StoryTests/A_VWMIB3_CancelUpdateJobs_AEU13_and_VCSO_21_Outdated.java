package testCases.a_UNECE_VW_MIB3_StoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54585123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Disabled("This test is outdated")

public class A_VWMIB3_CancelUpdateJobs_AEU13_and_VCSO_21_Outdated extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VWMIB3_CancelUpdateJobs_AEU13_and_VCSO_21";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	// TODO : setUp notwendig?
	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
		} finally {
			initTestContainer(1);// test has two steps
		}
	}

	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstalbase_MIB3_VW.assertInstallbase_MIB3VW();

		LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname, vin, campaign, criterion);//rewind a campaign which ORU have imported recall
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {

	}

}
