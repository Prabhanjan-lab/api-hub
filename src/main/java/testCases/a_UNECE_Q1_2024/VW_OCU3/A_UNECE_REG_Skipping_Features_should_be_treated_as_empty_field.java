package testCases.a_UNECE_Q1_2024.VW_OCU3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.PendingQueue;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-57277123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
public class A_UNECE_REG_Skipping_Features_should_be_treated_as_empty_field extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "VWMIB3_UNECE_REG_Skipping_Features_should_be_treated_as_empty_field";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String expectedCampaign = ReadTestParameters.getAttributeValue(testname, "expectedCampaign");

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