package testCases.a_Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-63129123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class A_UNECE_REG_TestVehicle_ShouldBeReleased_AfterRolloutdateIsReached  extends AbstractStandardBehaviour{

	public static String testname="UNECE_REG_TestVehicle_ShouldBeReleased_AfterRolloutdateIsReached";
	public static String vin1 = ReadTestParameters.getAttributeValue(testname, "vin");


	String logPath = "";

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

		// Oauth
		logPath = LogReports.logGeneration(testname, "Oauth");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin1);
		System.out.println(vin1);
		this.logStepPassed(logPath);
		
		// IB
		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		this.logStepPassed(logPath);
		
		// PutVC
		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin1);
		this.logStepPassed(logPath);

		System.out.println("A condition execution completed.");
	}

	@Override
	protected void tearDownHook() throws Throwable {

	}
}
