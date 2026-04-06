package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ConfirmS42ImportUpdate;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62284")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62284_UNECE_Retest_Bug_OU_4323_PUT_oru_campaignNumber_service42 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "PUT_oru_campaignNumber_service42";
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

		logPath = LogReports.logGeneration(testname, "PUTS42ImportUpdateRequestWithInvalidCampaignNumber");
		this.setLogPath(logPath);
		ConfirmS42ImportUpdate.assertS42ImportUpdateWithInvalidCampaignNumber(testname,campaign);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
