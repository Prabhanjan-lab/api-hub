package testCases.a_UNECE_VW_MIB3_StoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-54590123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class A_VWMIB3_CancelUpdateJobs_AEU1_VCSO_2 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VWMIB3_CancelUpdateJobs_AEU1_VCSO_2";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
		} finally {
			initTestContainer(1);// test has one steps
		}
	}

	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);

		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);

		LogReports.logGeneration(testname, "UploadIB");
		UploadInstalbase_MIB3_VW.assertInstallbase_MIB3VW();

	}

	@Override
	protected void tearDownHook() throws Throwable {

	}

}
