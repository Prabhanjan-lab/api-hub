package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.getOruActionCsv;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62282")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62282_UNECE_RetestBug_OU_4321_GET_oru_csvencrypted_campaignNumber_WrongStatusResponse
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "Encrypted_campaignNumber_WrongStatusResponse";
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
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

		logPath = LogReports.logGeneration(testname, "GET_StatusResponse");
		this.setLogPath(logPath);
	    getOruActionCsv.assertStatusResponseWithInvalidCampaignNumber(testname,campaign);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
