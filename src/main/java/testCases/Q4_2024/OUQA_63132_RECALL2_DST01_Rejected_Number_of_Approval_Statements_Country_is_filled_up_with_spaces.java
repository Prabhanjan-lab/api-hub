package testCases.Q4_2024;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.ImportRecallWithFaultyFields;
import testCases.reusable_TestSteps.ImporterRecall_Logs;
import testCases.utils.CurrentDateTime;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;
import testFramework.utilities.recall.SshConnection;
@Tag("OUQA-63132")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")
public class OUQA_63132_RECALL2_DST01_Rejected_Number_of_Approval_Statements_Country_is_filled_up_with_spaces extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "RECALL2_DST01_Rejected_Number_of_Approval_Statements_Country_is_filled_up_with_spaces";
	private String RECALL_ID;
	String campaign = "campaign";
	String noOfManipulatedApprovalCountry="   ";
	String logCheck="Ignored: AS";
	String recordsType = "Errors"; //Adapted,Added,Errors
	String ignoredFilePath ="/nfs/mbbb_onup/as/demo/recall_import/processed/ignored";
	LocalDateTime currentTimestamp = CurrentDateTime.getCurrentTimeStamp();
	String ignoredFile;

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

		logPath = LogReports.logGeneration(testname, "Create_ImportedRecall_NumberOfApprovalCountryIsFilledUpWithSpaces");
		this.setLogPath(logPath);
		RECALL_ID = CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		ImportRecallWithFaultyFields.createImportedRecall2WithFaultyfields(RECALL_ID, "recall-ouqa-63132-minimal-data-record.json", "01", "de-DE",noOfManipulatedApprovalCountry,96,3);
		UpdateTestParameters.updateAttributeValueInJson(campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "assertFileLocatedInIgnoredFolder");
		this.setLogPath(logPath);
		ignoredFile = SshConnection.importedRecallFileName+"_ignored.zip";
		System.out.println("Ignored File name is "+ignoredFile);
		boolean fileExists = SshConnection.checkRemoteFileExists(ignoredFilePath, ignoredFile);
		Assert.assertTrue(fileExists);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Assert_Logs");
		this.setLogPath(logPath);
		ImporterRecall_Logs.assertLogDetails(testname,recordsType, RECALL_ID, logCheck, currentTimestamp); //1: 01: TB44: Ignored: AS: (   );
		this.logStepPassed(logPath);


	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

}
