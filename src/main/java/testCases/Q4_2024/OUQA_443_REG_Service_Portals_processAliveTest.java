package testCases.Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.reusable_TestSteps.ProcessAliveTest_Step;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-443")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q4")

public class OUQA_443_REG_Service_Portals_processAliveTest extends AbstractStandardBehaviour{
	String logPath = "";
	String testname = "REG_Service_Portals_processAliveTest";// not required any TestData.
	
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

		logPath = LogReports.logGeneration(testname, "ProcessAliveTest");
		this.setLogPath(logPath);
		ProcessAliveTest_Step.assertProcessAliveTest();
		this.logStepPassed(logPath);
		
		System.out.println("TestExecution Completed.");
        
	}
	
	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
