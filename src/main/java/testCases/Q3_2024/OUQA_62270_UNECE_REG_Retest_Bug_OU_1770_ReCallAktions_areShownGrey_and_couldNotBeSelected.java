package testCases.Q3_2024;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.CreateExceptionList;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.CreateORUCampaign;
import testCases.reusable_TestSteps.DeleteCampaign;
import testCases.reusable_TestSteps.DeleteExceptionList;
import testCases.reusable_TestSteps.DeleteRecall;
import testCases.reusable_TestSteps.InboxRecall;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;
 
@Tag("OUQA-62270")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")
 
public class OUQA_62270_UNECE_REG_Retest_Bug_OU_1770_ReCallAktions_areShownGrey_and_couldNotBeSelected
		extends AbstractStandardBehaviour {
 
	String logPath = "";
	String testname = "VWOCU3_UNECE_REG_Retest_Bug_OU_1770_ReCallAktions_areShownGrey_and_couldNotBeSelected";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = "";
	String RECALL_ID = "";
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
			this.initTestContainer(4);
		}
	}
 
	@Override
	protected void testHook() throws Throwable {
 
		LogReports.logs(testname);
 
		LogReports.logGeneration(testname, "AssertVinNotInExceptionList");
		this.setLogPath(logPath);
		String id=CreateExceptionList.assertVinCreationInExceptionList(testname);
		DeleteExceptionList.assertVinDeletionInExceptionList(testname,id);
		this.logStepPassed(logPath);
 
		LogReports.logGeneration(testname, "RecallCreation");
		this.setLogPath(logPath);
		RECALL_ID=CreateManualRecall.assertNewRecallID(testname, TestVariables.OUQA_62270_filePath);
		CreateManualRecall.assertCreateManualRecall(testname, TestVariables.OUQA_62270_filePath, RECALL_ID);
		this.logStepPassed(logPath);
 
		LogReports.logGeneration(testname, "AssertRecallInbox");
		this.setLogPath(logPath);
		campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		InboxRecall.assertRecallInbox(testname, campaign);
		this.logStepPassed(logPath);
 
		LogReports.logGeneration(testname, "ORUCreation");
		this.setLogPath(logPath);
		CreateORUCampaign.assertCreateORU(testname, campaign, criterion);
		this.logStepPassed(logPath);
		
		LogReports.logGeneration(testname, "ORU_Recall_Deletion");
		Thread.sleep(120000);
		DeleteCampaign.assertORUdelete(testname, campaign);
		DeleteRecall.assertRecallDelete(testname, campaign);

 
	}
 
	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub
 
	}
 
}