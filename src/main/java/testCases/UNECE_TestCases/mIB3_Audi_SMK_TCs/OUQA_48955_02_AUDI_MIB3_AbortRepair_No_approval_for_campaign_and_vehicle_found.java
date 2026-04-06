package testCases.UNECE_TestCases.mIB3_Audi_SMK_TCs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.testStep_Advice.mIB3_Audi.Advice_MIB3Audi;
import testCases.testStep_Installbase.mIB3_Audi.UploadInstallbase_MIB3_Audi;
import testCases.testStep_Package.mIB3_Audi.packages_MIB3Audi;
import testCases.testStep_PutVC.mIB3_Audi.PutVC_RepairNecessary_MIB3Audi;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;


@Tag("OUQA-48955")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("23Q3")
public class OUQA_48955_02_AUDI_MIB3_AbortRepair_No_approval_for_campaign_and_vehicle_found extends AbstractStandardBehaviour{
	String logPath="";
	String testname="MIB3Audi_NoApproval";
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
            this.initTestContainer(7); // test has seven test step
        }
    }
	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		this.setLogPath("");
		this.logStepPassed(logPath);
		this.setLogPath("");
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname,"GenerateOauthToken");
		this.setLogPath(logPath);
		RegisterAndGetOAUTH.registerAndGetOAUTH(testname, vin);
		this.logStepPassed(logPath);
		
		
		logPath = LogReports.logGeneration(testname,"UploadIB");
		this.setLogPath(logPath);
		UploadInstallbase_MIB3_Audi.assertInstallbase_MIB3Audi();
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname,"PutVC");
		this.setLogPath(logPath);
		PutVC_RepairNecessary_MIB3Audi.assertPutVC_MIB3Audi(testname, vin);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname,"Advice");
		this.setLogPath(logPath);
		Advice_MIB3Audi.assertAdviceAbortRepair_MIB3Audi(testname, TestVariables.PutVCHash, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname,"Package");
		this.setLogPath(logPath);
		packages_MIB3Audi.assertPackagesNoApproval_MIB3Audi(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);
		
		System.out.println("Test Execution completed");
		
	}

		@Override
		protected void tearDownHook() throws Throwable {
			// TODO Auto-generated method stub
			
		}

}