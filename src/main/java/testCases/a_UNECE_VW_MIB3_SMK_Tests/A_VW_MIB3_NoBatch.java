package testCases.a_UNECE_VW_MIB3_SMK_Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.PendingQueue;
import testFramework.AbstractStandardBehaviour;
import testCases.utils.LogReports;

@Tag("OUQA-48963123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
public class A_VW_MIB3_NoBatch extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "MIB3VW_NoBatch";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String expectedCampaign = ReadTestParameters.getAttributeValue(testname, "campaign");

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
			this.initTestContainer(1); // test has one test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		logPath = LogReports.logGeneration(testname, "VCSOStatus1");
		this.setLogPath(logPath);
		PendingQueue.assertPendingCampaign(testname, vin, expectedCampaign);
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
