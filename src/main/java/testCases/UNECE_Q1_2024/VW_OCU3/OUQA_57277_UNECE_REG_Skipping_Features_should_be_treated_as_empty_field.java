package testCases.UNECE_Q1_2024.VW_OCU3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.Get_VehicleConfiguration;
import testCases.testStep_PutVC.mIB3_VW.PutVC_WrongVariant_MIB3VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-57277")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q1")
public class OUQA_57277_UNECE_REG_Skipping_Features_should_be_treated_as_empty_field extends AbstractStandardBehaviour {

	String logPath = "";
	String testname = "VWMIB3_UNECE_REG_Skipping_Features_should_be_treated_as_empty_field";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(9);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVCWithfirstvariant");
		this.setLogPath(logPath);
		PutVC_WrongVariant_MIB3VW.assertPutVC_MIB3VW(testname, "M2P-H-CON-13--EU-AU-MLB-AL", vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertFirstVariant");
		this.setLogPath(logPath);
		Get_VehicleConfiguration.assertVariantInVehilceConfiguration(testname, vin, "M2P-H-CON-13--EU-AU-MLB-AL");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVCWithSecondVariant");
		this.setLogPath(logPath);
		PutVC_WrongVariant_MIB3VW.assertPutVC_MIB3VW(testname, "M2P-H--EU-AU-MLB-AL", vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertSecondVariant");
		this.setLogPath(logPath);
		Get_VehicleConfiguration.assertVariantInVehilceConfiguration(testname, vin, "M2P-H--EU-AU-MLB-AL");
		this.logStepPassed(logPath);

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
