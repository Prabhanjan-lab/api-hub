package testCases.UNECE_TestCases.mIB3_VW_REG_TCs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.testStep_Advice.mIB3_VW.Advice_MIB3VW;
import testCases.testStep_Installbase.mIB3_VW.UploadInstalbase_MIB3_VW;
import testCases.testStep_PutVC.mIB3_VW.PutVC_RepairNecessary_MIB3VW;
import testCases.testStep_VetoAdvice.mIB3_VW.vetoAdvice_MIB3VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-60153")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")
public class OUQA_60153_10_VW_MIB3_publish_job_veto_advice_ivdProgHash_vcso12 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "MIB3VW_PublishJobVetoAdviceVCSO12";
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
			this.initTestContainer(17);
		}
	}

	@Override
	protected void testHook() throws Throwable {

		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstalbase_MIB3_VW.assertInstallbase_MIB3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_MIB3VW.assertPutVC_MIB3VW_NoProgHash(testname, vin);
		this.logStepPassed(logPath);

		// advice step mit leerem Payload, kannst du dir von anderen tests kopieren
		// danach 3h abwarten, dann veto advice
		Thread.sleep(1800000);// 30min

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_MIB3VW.assertAdviceRepairNecessary_MIB3VW_NoProgHash(testname, TestVariables.PutVCHash, vin, campaign,
				criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus12");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);

		Thread.sleep(10800000);// (3h wartezeit)

		// wichtig, dass es immernoch aus 12 und nicht auf 6 ist
		logPath = LogReports.logGeneration(testname, "VCSOStatus12");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VetoAdvice");
		this.setLogPath(logPath);
		vetoAdvice_MIB3VW.assertVetoAdvice_MIB3VW(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus12");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "12");
		this.logStepPassed(logPath);

		System.out.println("Test Execution completed");
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
