package testCases.a_Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateImportedRecall;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.GetOverallFlashProcessingStatus;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW_CarportVin;
import testCases.testStep_PutVC.oCU3_VW.PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62213123")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")
public class A_RECALL2_REG_UNECE_VW_OCU3_Vehicle_Overview_Change_Of_ORU_Relevant_Flag_From_YES_to_NO_via_ReCall_Dataset_Type_02_04_Criterion_VCSO_2_12 extends AbstractStandardBehaviour{

	String testname="RECALL2_VW_OCU3_Vehicle_Overview_Change_Of_ORU_Relevant_Flag_From_YES_to_NO_via_ReCall_Dataset_Type_02_04_Criterion_VCSO_2_12";
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	private String RECALL_ID1;
	private String RECALL_ID2;
	private String campaign1="campaign1";
	private String campaign2="campaign2";
	String logPath = "";
	String keystorePath = TestVariables.getKeyStorePath("BASE_" + vin);
	char[] password = vin.toCharArray();
	
	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(2); // This test has two steps
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		//Create a ReCall1 Configuration with one VIN.
		logPath = LogReports.logGeneration(testname, "CreateImportedRecall_1");
		this.setLogPath(logPath);
		RECALL_ID1 = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateImportedRecall.createImportedRecall2(RECALL_ID1,"recall-ouqa-62213-a-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(campaign1,RECALL_ID1,testname);
		Thread.sleep(20000);
		CreateORUCampaign.assertCreateORU(testname,RECALL_ID1,criterion);
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, RECALL_ID1, criterion);
		this.logStepPassed(logPath);

		//Create a ReCall2 Configuration with one VIN.
		logPath = LogReports.logGeneration(testname, "CreateImportedRecall_2");
		this.setLogPath(logPath);
		RECALL_ID2 = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateImportedRecall.createImportedRecall2(RECALL_ID2,"recall-ouqa-62213-a-minimal-data-record.json");
		UpdateTestParameters.updateAttributeValueInJson(campaign2,RECALL_ID2,testname);     
		Thread.sleep(20000);
		CreateORUCampaign.assertCreateORU(testname,RECALL_ID2,criterion);
		GetOverallFlashProcessingStatus.assertFlashProcessingStatus(testname, RECALL_ID2, criterion);
		this.logStepPassed(logPath);

		//Generate Oauth Token for Vin
		LogReports.logGeneration(testname, "OAuthToken");
		TokenGenerationForCarportVins.GenerateOauthTokenForCarportVins(testname, keystorePath, password);

		//Upload IB for vin
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW_CarportVin.assertInstallbase_OCU3VW_Carport();

		//PutVC for vin
		LogReports.logGeneration(testname, "PutVC");
		PutVC_RepairNecessary_OCU3VW_NONUNECE_Carport.assertPutVC_OCU3VW_NONUNECE_Carport(testname,vin);
		
		System.out.println("A -condtion executed sucessfully.");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}
}
