package testCases.Q4_2024;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.InboxRecall;
import testCases.reusable_TestSteps.getRecallWithCampaignNumber;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;

@Tag("OUQA-10737")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_10737_RECALL2_REG_Using_ReCall_Actions_while_Another_Action_is_Still_Being_processed extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "RECALL2_Using_ReCall_Actions_while_Another_Action_is_Still_Being_processed";
	String campaign1=ReadTestParameters.getAttributeValue(testname, "campaign1");
	String campaign2=ReadTestParameters.getAttributeValue(testname, "campaign2");
    String campaign3=ReadTestParameters.getAttributeValue(testname, "campaign3");
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
			this.initTestContainer(5);
		}
	}
	
	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
		
		this.setLogPath("");     
		this.logStepPassed(logPath);
        
		logPath = LogReports.logGeneration(testname, "AssertRecall_1_Inbox");
		this.setLogPath(logPath);
		InboxRecall.assertRecallInbox(testname, campaign1);
		getRecallWithCampaignNumber.assertNumberOfVehicles(testname, campaign1,10);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "CreateORUCampaign");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname,campaign1,criterion);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertRecall_2_3_Inbox");
		this.setLogPath(logPath);
		InboxRecall.assertRecallInbox(testname, campaign2);
		InboxRecall.assertRecallInbox(testname, campaign3);
		this.logStepPassed(logPath);
		
		logPath = LogReports.logGeneration(testname, "AssertNumberOfVehicles");
		this.setLogPath(logPath);
		getRecallWithCampaignNumber.assertNumberOfVehicles(testname, campaign2,10);
		getRecallWithCampaignNumber.assertNumberOfVehicles(testname, campaign3,10);
		this.logStepPassed(logPath);
        
	}

	@Override
	protected void tearDownHook() throws Throwable {


	}

}
