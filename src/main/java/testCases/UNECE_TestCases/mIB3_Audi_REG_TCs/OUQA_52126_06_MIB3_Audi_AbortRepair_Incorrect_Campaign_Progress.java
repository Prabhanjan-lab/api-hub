package testCases.UNECE_TestCases.mIB3_Audi_REG_TCs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.DeapproveBatch;
import testCases.reusable_TestSteps.ForceAbort;
import testCases.testStep_Advice.mIB3_Audi.Advice_MIB3Audi;
import testCases.testStep_Installbase.mIB3_Audi.UploadInstallbase_MIB3_Audi;
import testCases.testStep_Package.mIB3_Audi.packages_MIB3Audi;
import testCases.testStep_PutVC.mIB3_Audi.PutVC_RepairNecessary_MIB3Audi;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-52126")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")
public class OUQA_52126_06_MIB3_Audi_AbortRepair_Incorrect_Campaign_Progress extends AbstractStandardBehaviour {
	String logPath="";
	String testname="MIB3Audi_IncorrectCampaignProcess";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String batchName = ReadTestParameters.getAttributeValue(testname, "batchName");
	@Override
    @BeforeEach
    protected void setUp() throws Throwable {

        try {
            super.setUp();
        } catch (Throwable e) {
            this.setThrowable(e);
            throw e;
        } finally {
            this.initTestContainer(8); // test has eight test step
        }
    }
	@Override
    protected void testHook() throws Throwable { 
		
		LogReports.logs(testname);
	
		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "DeapproveBatch");
		this.setLogPath(logPath);
		DeapproveBatch.DeapproveDefaultBatch(testname, campaign, criterion, batchName);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "38");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "OAuthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_MIB3_Audi.assertInstallbase_MIB3Audi();
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname,"PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_MIB3Audi.assertPutVC_MIB3Audi(testname, vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Advice");
		this.setLogPath(logPath);
		Advice_MIB3Audi.assertAdviceIncorrectCampaign_MIB3Audi(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "Packages");
		this.setLogPath(logPath);
		packages_MIB3Audi.assertPackagesNoApproval_MIB3Audi(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "ForceAbort");
		this.setLogPath(logPath);
		ForceAbort.assertForceAbort(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		System.out.println("Test Execution Completed");
		
		
	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub
		
	}

	

}
