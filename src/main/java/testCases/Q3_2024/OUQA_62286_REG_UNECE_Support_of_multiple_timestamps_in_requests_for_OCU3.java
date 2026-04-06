package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.testStep_Advice.oCU3_VW.Advice_Multiple_TimeStamps_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_Package.oCU3_VW.packages_Multiple_TimeStamp_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode_Multiple_TimeStamps_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62286")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62286_REG_UNECE_Support_of_multiple_timestamps_in_requests_for_OCU3 extends AbstractStandardBehaviour {
	String logPath = "";
	String testname = "REG_UNECE_Support_of_multiple_timestamps_in_requests_for_OCU3";
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
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname,vin);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_Multiple_TimeStamps_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash,"2018-09-24T18:40:54+0000",vin,campaign,criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode_Multiple_TimeStamps_OCU3_VW.assertStatusCode_OCU3VW(testname,"StatusCode1","2018-04-19T15:02:45.61Z",vin,campaign,criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Package");
		this.setLogPath(logPath);
		packages_Multiple_TimeStamp_OCU3_VW.assertPackagesRepairDone_OCU3VW(testname,TestVariables.PutVCHash,"2018-07-04T10:10:22+0000",vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ForceAbortandRewind");
		this.setLogPath(logPath);
		ForceAbort.assertForceAbort(testname,vin,campaign,criterion);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname,vin,campaign,criterion);
		Thread.sleep(366000);
		this.logStepPassed(logPath);
		
		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname,vin);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_Multiple_TimeStamps_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash,"2018-09-24T18:40:54.5+0000",vin,campaign,criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode_Multiple_TimeStamps_OCU3_VW.assertStatusCode_OCU3VW(testname,"StatusCode1","2018-09-24T18:40:54.5Z",vin,campaign,criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "StatusCode3");
		this.setLogPath(logPath);
		StatusCode_Multiple_TimeStamps_OCU3_VW.assertStatusCode_OCU3VW(testname,"StatusCode3","2018-09-24T18:40:54.55Z",vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode4");
		this.setLogPath(logPath);
		StatusCode_Multiple_TimeStamps_OCU3_VW.assertStatusCode_OCU3VW(testname,"StatusCode4","2018-09-24T18:40:54.555Z",vin,campaign,criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "ForceAbortandRewind");
		this.setLogPath(logPath);
		ForceAbort.assertForceAbort(testname,vin,campaign,criterion);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname,vin,campaign,criterion);
		Thread.sleep(366000);
		this.logStepPassed(logPath);
		
		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname,vin);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_Multiple_TimeStamps_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash,"2018-09-24T18:40:54.55+0000",vin,campaign,criterion);
		this.logStepPassed(logPath);		
		
		logPath = LogReports.logGeneration(testname, "ForceAbortandRewind");
		this.setLogPath(logPath);
		ForceAbort.assertForceAbort(testname,vin,campaign,criterion);
		RewindCampaignWithSkipMassnotification.assertRewindCampaign(testname,vin,campaign,criterion);
		Thread.sleep(366000);
		this.logStepPassed(logPath);

		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname,vin);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_Multiple_TimeStamps_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash,"2018-09-24T18:40:54.555+0000",vin,campaign,criterion);
		this.logStepPassed(logPath);
		
		System.out.println("TestExecution Complete");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
