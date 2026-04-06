package testCases.Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.GetLockedVehicles;
import testCases.testStep_Advice.oCU3_VW.Advice_OCU3_VW;
import testCases.testStep_Advice.oCU3_VW.Advice_notranslation_OCU3_VW;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_22_OCU3_VW;
import testCases.testStep_StatusCode.oCU3_VW.StatusCode1_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-28242")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("25Q1")
public class OUQA_28242_Retest_Bug_OU_4395_Current_Blocked_Vehicles_Error_Code_Filtering extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "Current_Blocked_Vehicles_Error_Code_Filtering";
	String vin1 = ReadTestParameters.getAttributeValue(testname, "vin1");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String vin3 = ReadTestParameters.getAttributeValue(testname, "vin3");
	String vin4 = ReadTestParameters.getAttributeValue(testname, "vin4");
	String vin5 = ReadTestParameters.getAttributeValue(testname, "vin5");
	String vin6 = ReadTestParameters.getAttributeValue(testname, "vin6");
	String vin7 = ReadTestParameters.getAttributeValue(testname, "vin7");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	int expLockedVinCount=3;
	int filtercode=46;
	int expFilter_46_LockedVinCount=2;


	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(10); // test has twelve test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath("");
		logPath = LogReports.logGeneration(testname, "Rewind");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin1, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin2, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin3, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin4, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin5, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin6, campaign, "5");
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin7, campaign, "5");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "PutVC");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin1);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin1);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_notranslation_OCU3_VW.assertAdviceAbortRepair_OCU3_VW(testname, vin1, campaign, criterion);	
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin1, campaign, "36"); 
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Result_4-22");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin2);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin2);
		ResultCode4_22_OCU3_VW.assertResultCode_OCU3VW(testname, batchName, vin2, campaign, criterion);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin3);
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin3);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		ResultCode4_22_OCU3_VW.assertResultCode_OCU3VW(testname, batchName, vin3, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertblockedVinCount");
		this.setLogPath(logPath);
		GetLockedVehicles.assertBlockedVinsCount(testname, vin1, campaign, criterion, batchName, expLockedVinCount);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertblockedVehicleWithReason");
		this.setLogPath(logPath);
		GetLockedVehicles.assertBlockedVinWithFilter_Code36(testname, vin1, campaign, criterion, batchName);
		GetLockedVehicles.assertBlockedVinWithFilter_Code46(testname, vin2, campaign, criterion, batchName);
		GetLockedVehicles.assertBlockedVinWithFilter_Code46(testname, vin3, campaign, criterion, batchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertFilter_46_BlockedVins");
		this.setLogPath(logPath);
		GetLockedVehicles.FilteringBlockedVINs(testname, vin1, campaign, criterion, batchName, filtercode, expFilter_46_LockedVinCount);
		this.logStepPassed(logPath);


	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
