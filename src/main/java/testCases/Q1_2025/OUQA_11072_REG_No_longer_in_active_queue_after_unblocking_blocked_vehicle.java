package testCases.Q1_2025;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.DeapproveBatch;
import testCases.reusable_TestSteps.GetLockedVehicles;
import testCases.reusable_TestSteps.GetVehicleAttributes;
import testCases.reusable_TestSteps.PendingQueue;
import testCases.reusable_TestSteps.Unblock_Vehicle;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.testStep_Package.oCU3_VW.packages_OCU3VW_NONUNECE_Carport;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport;
import testCases.testStep_ResultCode.oCU3_VW.ResultCode4_22_OCU3VW_Carport;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-11072")
@Tag("Approval_ECE")
@Tag("25Q1")
public class OUQA_11072_REG_No_longer_in_active_queue_after_unblocking_blocked_vehicle extends AbstractStandardBehaviour{


	String logPath = "";
	String testname = "REG_No_longer_in_active_queue_after_unblocking_blocked_vehicle";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	String keystorePath = TestVariables.getKeyStorePath("BASE_Approval_" + vin);
	char[] password = vin.toCharArray();
	public static final String Carport = "CARPORT";

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(8); // test has Eight test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Assert_CarportVIN");
		this.setLogPath(logPath);
		Assert.assertEquals(GetVehicleAttributes.GetVehicleAttributesVehicleDataSource(testname, vin),Carport);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "ApproveDefaultBatch");
		this.setLogPath(logPath);
		ApproveBatch.approveBatch(testname, campaign, criterion, batchName);
		Thread.sleep(360000);
		Assert.assertTrue(ActiveQueueVCSOStatus.assertCampaignInActiveQueue(testname, vin));
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "De-approveDefaultBatch");
		this.setLogPath(logPath);
		DeapproveBatch.assertDeapproveBatch(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "packages");
		this.setLogPath(logPath);
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);	
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();
		PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport.assertPutVC_OCU3VW_NONUNECE_Carport(testname, vin);
		packages_OCU3VW_NONUNECE_Carport.assertPackagesNoApproval_OCU3VW_NONUNECE_Carport(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "result4-22");
		this.setLogPath(logPath);
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);	
		PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport.assertPutVC_OCU3VW_NONUNECE_Carport(testname, vin);
		ResultCode4_22_OCU3VW_Carport.assertResultCode_OCU3VW_Carport(testname, batchName, vin, campaign, criterion);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "46"); 
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertblockedVehicle");
		this.setLogPath(logPath);
		GetLockedVehicles.assertVinBlocked(testname, vin, campaign, criterion, batchName);	
		this.logStepPassed(logPath);


		logPath = LogReports.logGeneration(testname, "assertUn-blockedVehicleMovedTopendingQueue");
		this.setLogPath(logPath);
		Unblock_Vehicle.assertUnBlockVin(testname, vin, campaign, criterion);
		PendingQueue.assertPendingCampaign(testname, vin, campaign);
		this.logStepPassed(logPath);



	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}




}
