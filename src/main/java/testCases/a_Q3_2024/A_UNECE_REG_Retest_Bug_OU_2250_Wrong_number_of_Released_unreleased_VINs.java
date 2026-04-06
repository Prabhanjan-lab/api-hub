package testCases.a_Q3_2024;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
 
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;
 
 
@Tag("OUQA-62277123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
public class A_UNECE_REG_Retest_Bug_OU_2250_Wrong_number_of_Released_unreleased_VINs extends AbstractStandardBehaviour {
 
	
	String logPath = "";
	String testname = "VWOCU3_UNECE_REG_Retest_Bug_OU_2250_Wrong_number_of_Released_unreleased_VINs";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
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
			this.initTestContainer(2);
		}
	}
 
	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		LogReports.logGeneration(testname, "OAuthToken");
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		
		LogReports.logGeneration(testname, "UploadIB");
		UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
		
		logPath = LogReports.logGeneration(testname, "RecallCreation");
		this.setLogPath(logPath); 
		String recallID=CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		CreateManualRecall.assertCreateManualRecall(testname,TestVariables.OUQA_62277_filePath,recallID);
		this.logStepPassed(logPath);
		
		String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		logPath = LogReports.logGeneration(testname, "ORUCreation");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname, campaign, criterion);
		this.logStepPassed(logPath);
	}
 
	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub
 
	} 
}