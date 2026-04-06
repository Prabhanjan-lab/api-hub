package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.DeleteDataImportLogsByFilter;
import testCases.reusable_TestSteps.FindImportLogs;
import testCases.reusable_TestSteps.GETRequestForLogging;
import testCases.reusable_TestSteps.GETRequestForLoggingShort;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62268")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62268_UNECE_GET_logging_importlogid_Getting_status_200_with_invalid_searching_term extends AbstractStandardBehaviour{
	String logPath = "";
	String testname = "GET_logging_importlogid_Getting_status_200_with_invalid_searching_term";
	String invalidImportID=ReadTestParameters.getAttributeValue(testname,"invalidImportID");
	String invalidPage=ReadTestParameters.getAttributeValue(testname,"invalidPage");
	String invalidCount=ReadTestParameters.getAttributeValue(testname,"invalidCount");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(3);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		logPath = LogReports.logGeneration(testname, "GetRequestInvalidLoggingID");
		this.setLogPath(logPath);
		FindImportLogs.assertInvalidLoggingId(testname,invalidImportID);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "DeleteRequestWithInvalidLoggingID");
		this.setLogPath(logPath);
		DeleteDataImportLogsByFilter.assertDeleteInvalidLoggingId(testname,invalidImportID);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "GetRequestLoggingWithInvalidParameters");
		this.setLogPath(logPath);
		GETRequestForLogging.assertLoggingWithInvalidParameters(testname,invalidPage,invalidCount);
		this.logStepPassed(logPath);
		
		System.out.println("Test Execution completed");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
