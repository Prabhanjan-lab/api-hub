package testCases.a_Q3_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.RewindCampaignWithSkipMassnotification;
import testCases.reusable_TestSteps.ScheduledQueue;
import testCases.testStep_Installbase.oCU3_VW.UploadInstallbase_OCU3_VW;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-62275123")
@Tag("Demo_ECE")
@Tag("Approval_ECE")

public class A_UNECE_VW_OCU3_REG_GLCS_RepairDone_moving_campaigns_into_the_active_queue extends AbstractStandardBehaviour{
	
	String logPath="";
	 String testname = "UNECE_VW_OCU3_REG_GLCS_RepairDone_moving_campaigns_into_the_active_queue";
	 String vin = ReadTestParameters.getAttributeValue(testname, "vin");
		String campaign= ReadTestParameters.getAttributeValue(testname, "campaign");
		String campaign2=ReadTestParameters.getAttributeValue(testname, "campaign2");
		String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");  
	 //TODO: setUp notwendig?
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
			RegisterAndGetOAUTH.registerAndGetOAUTH(testname,vin);

			LogReports.logGeneration(testname, "UploadIB");
			UploadInstallbase_OCU3_VW.assertInstallbase_OCU3VW();
			
	    	logPath=LogReports.logGeneration(testname,"Rewind");
	    	this.setLogPath(logPath);
			RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname,vin,campaign,criterion);
			this.logStepPassed(logPath);
			
	    	logPath=LogReports.logGeneration(testname,"rewindSecondCampaign");
	    	this.setLogPath(logPath);
	    	RewindCampaignWithSkipMassnotification.assertRewindCampaignToSchedule(testname,vin,campaign2,criterion);
	    	ScheduledQueue.assertScheduledCampaign(testname, vin, campaign2);
			this.logStepPassed(logPath);
			
	    }
	    
	    @Override
		protected void tearDownHook() throws Throwable {
			// TODO Auto-generated method stub
			
		}
}