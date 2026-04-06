package testCases.Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.CampaignHistory;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_Package.oCU3_VW.packages_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode1_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode1_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode3_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode4_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode7_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode8_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62275")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")

public class OUQA_62275_UNECE_VW_OCU3_REG_GLCS_RepairDone_moving_campaigns_into_the_active_queue extends AbstractStandardBehaviour{
	String logPath = "";
	String testname = "UNECE_VW_OCU3_REG_GLCS_RepairDone_moving_campaigns_into_the_active_queue";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign= ReadTestParameters.getAttributeValue(testname, "campaign");
	String campaign2= ReadTestParameters.getAttributeValue(testname, "campaign2");
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
			this.initTestContainer(19);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		logPath = LogReports.logGeneration(testname, "AssertActiveQueue");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "5");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertScheduleQueue");
		this.setLogPath(logPath);
		ScheduledQueue.assertScheduledCampaign(testname, vin, campaign2);
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
		Advice_OCU3_VW.assertAdviceRepairNecessary_OCU3VW(testname, TestVariables.PutVCHash,vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus12");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin,campaign, "12");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode1");
		this.setLogPath(logPath);
		StatusCode1_OCU3_VW.assertStatusCode_OCU3VW(testname,vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus16");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin,campaign, "16");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Package");
		this.setLogPath(logPath);
		packages_OCU3_VW.assertPackagesRepairDone_OCU3VW(testname,vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus19");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin,campaign, "19");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode3");
		this.setLogPath(logPath);
		StatusCode3_OCU3_VW.assertStatusCode_OCU3VW(testname,vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus20");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname,vin,campaign, "20");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode4");
		this.setLogPath(logPath);
		StatusCode4_OCU3_VW.assertStatusCode_OCU3VW(testname,vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode7");
		this.setLogPath(logPath);
		StatusCode7_OCU3_VW.assertStatusCode_OCU3VW(testname,vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "StatusCode8");
		this.setLogPath(logPath);
		StatusCode8_OCU3_VW.assertStatusCode_OCU3VW(testname,vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ResultCode1");
		this.setLogPath(logPath);
		ResultCode1_OCU3_VW.assertResultCode_OCU3VW(testname, TestVariables.PutVCHash,vin,campaign,criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus30");
		this.setLogPath(logPath);
		CampaignHistory.assertCampaignHistory(testname,vin,campaign, "30");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "SecondVinVCSOStatus5");
		this.setLogPath(logPath);
		Thread.sleep(180000);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname,vin,campaign2, "5");
		this.logStepPassed(logPath);
		
		System.out.println("Test Execution completed");
	}
	
	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
