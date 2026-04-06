package testCases.a_Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testFramework.AbstractStandardBehaviour;
import testCases.utils.LogReports;

@Tag("OUQA-62286123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class A_REG_UNECE_Support_of_multiple_timestamps_in_requests_for_OCU3 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "REG_UNECE_Support_of_multiple_timestamps_in_requests_for_OCU3";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign= ReadTestParameters.getAttributeValue(testname, "campaign");
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
			this.initTestContainer(1); // test has six test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin);

	    LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname,vin,campaign,criterion);
		this.logStepPassed(logPath);
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
