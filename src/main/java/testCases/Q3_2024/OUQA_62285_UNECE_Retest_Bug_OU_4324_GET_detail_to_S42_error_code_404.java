package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.GetS42Detail;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62285")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62285_UNECE_Retest_Bug_OU_4324_GET_detail_to_S42_error_code_404 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "GET_detail_to_S42_error_code_404";
	String amCode = ReadTestParameters.getAttributeValue(testname, "amCode");
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

		logPath = LogReports.logGeneration(testname, "GETDetailToService42");
		this.setLogPath(logPath);
		GetS42Detail.assertService42WithInvalidamCode(testname,amCode);
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
