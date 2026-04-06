package testCases.Q3_2024;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
 
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.ApproveBatch;
import testCases.reusable_TestSteps.DeapproveBatch;
import testCases.reusable_TestSteps.GetChargeStatus;
import testCases.reusable_TestSteps.MoveVinToDefaultBatch;
import testCases.utils.LogReports;
import testFramework.AbstractStandardBehaviour;
 
@Tag("OUQA-62277")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("24Q3")
 
public class OUQA_62277_UNECE_REG_Retest_Bug_OU_2250_Wrong_number_of_Released_unreleased_VINs
		extends AbstractStandardBehaviour {
 
	String logPath = "";
	String testname = "VWOCU3_UNECE_REG_Retest_Bug_OU_2250_Wrong_number_of_Released_unreleased_VINs";
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
			this.initTestContainer(8);
		}
	}
 
	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);
 
		logPath = LogReports.logGeneration(testname, "ApproveDefaultBatch");
		this.setLogPath(logPath);
		MoveVinToDefaultBatch.assertMoveVinToBatch(testname, campaign, criterion);
		ApproveBatch.assertApproveBatch(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);
 
		logPath = LogReports.logGeneration(testname, "AssertNumberOfReleasedVins");
		this.setLogPath(logPath);
		GetChargeStatus.assertNumberOfReleasedVins(testname, campaign, criterion,"1", batchName);
		this.logStepPassed(logPath);
 
		logPath = LogReports.logGeneration(testname, "AssertChargeStatusReleased");
		this.setLogPath(logPath);
		GetChargeStatus.assertChargeStatusReleased(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);
 
		logPath = LogReports.logGeneration(testname, "AssertActiveStatus");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueueWaitTime(testname, vin, campaign, "5");
		this.logStepPassed(logPath);
 
		logPath=LogReports.logGeneration(testname, "De-approveDefaultBatch");
		this.setLogPath(logPath);
		DeapproveBatch.assertDeapproveBatch(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);
 
		logPath = LogReports.logGeneration(testname, "AssertReleasedVins");
		this.setLogPath(logPath);
		GetChargeStatus.assertNumberOfReleasedVins(testname, campaign, criterion,"1", batchName);
		this.logStepPassed(logPath);
 
		logPath = LogReports.logGeneration(testname, "AssertChargeStatusCancelled");
		this.setLogPath(logPath);
		GetChargeStatus.assertChargeStatusCancelled(testname, campaign, criterion, batchName);
		this.logStepPassed(logPath);
 
		logPath = LogReports.logGeneration(testname, "AssertActiveStatus");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "38");
		this.logStepPassed(logPath);
 
	}
 
	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub
 
	}
 
}