package testCases.a_Q1_2025;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-2485123")
@Tag("Demo_ECE")

public class A_01_IMPORTED_RECALL_Automatically_releasing_batch_with_status_created_that_got_a_ReCall_approval_only_conditions  extends AbstractStandardBehaviour{
	public static String testname="IMPORTED_RECALL_Automatically_releasing_batch_with_status_created_that_got_a_ReCall_approval_only_conditions";
    
	private String RECALL_ID;
	private String campaign = "campaign";
	public static String vin1 = ReadTestParameters.getAttributeValue(testname, "vin");
	public static String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	public static String vin3 = ReadTestParameters.getAttributeValue(testname, "vin3");

	
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
			this.initTestContainer(1);
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		// Create a ReCall Configuration with one VIN.
		logPath = LogReports.logGeneration(testname, "CreateImportedRecall");
		this.setLogPath(logPath);
		RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateImportedRecall.createImportedRecall(RECALL_ID, "recall-ouqa-2485-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);
		
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin1);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin1);
		
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin2);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin2);
		
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin3);
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		PutVC_RepairNecessary_OCU3VW.assertPutVC_OCU3VW(testname, vin3);
		
	    System.out.println("A condition execution completed.");
	}
	
	@Override
	protected void tearDownHook() throws Throwable {
		
	}
}
