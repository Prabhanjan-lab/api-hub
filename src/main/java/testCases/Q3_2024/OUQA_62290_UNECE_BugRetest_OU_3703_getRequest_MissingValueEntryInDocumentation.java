package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.getRecallWithCampaignNumber;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62290")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62290_UNECE_BugRetest_OU_3703_getRequest_MissingValueEntryInDocumentation
		extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "BugRetest_OU_3703_getRequest_MissingValueEntryInDocumentation";
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

		logPath = LogReports.logGeneration(testname, "GetRecallResponseContainsValidAttribute");
		this.setLogPath(logPath);
		getRecallWithCampaignNumber.assertResponseContainsValidAttribute(testname,campaign);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
