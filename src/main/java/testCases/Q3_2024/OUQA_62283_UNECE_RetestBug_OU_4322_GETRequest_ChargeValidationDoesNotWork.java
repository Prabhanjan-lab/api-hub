package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.getBlockedVehicles;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62283")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62283_UNECE_RetestBug_OU_4322_GETRequest_ChargeValidationDoesNotWork
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "RetestBug_OU_4322_GETRequest_ChargeValidationDoesNotWork";
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String firstChargeName = ReadTestParameters.getAttributeValue(testname, "firstChargeName");

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

		logPath = LogReports.logGeneration(testname, "getBlockedVehiclesWithInvalidCharge");
		this.setLogPath(logPath);
		getBlockedVehicles.assertBlockedVehiclesWithInvalidCharge(testname,campaign,criterion,firstChargeName);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
