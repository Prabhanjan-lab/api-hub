package testCases.Q4_2024;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.reusable_TestSteps.CreateManualRecall;
import testCases.reusable_TestSteps.ExceptionList;
import testCases.reusable_TestSteps.GetVehicleAttributes;
import testCases.reusable_TestSteps.ImportRecallWithFaultyFields;
import testCases.reusable_TestSteps.ImporterRecall_Logs;
import testCases.reusable_TestSteps.InboxRecall;
import testCases.utils.CurrentDateTime;
import testCases.utils.LogReports;
import testCases.utils.TestVariables;
import testFramework.AbstractStandardBehaviour;
import testFramework.utilities.recall.SshConnection;

@Tag("OUQA-63083")
@Tag("Demo_ECE")
@Tag("Sequentiell")
@Tag("Mock")

public class OUQA_63083_RECALL2_Demo_UNECE_Importing_ReCall_Actions_Type_05_Ignored_Partnernumber extends AbstractStandardBehaviour{

	String logPath = "";
	String testname = "RECALL2_Importing_ReCall_Actions_Type_05_Ignored_Partnernumber";
	private String RECALL_ID;
	String campaign = "campaign";
	String brand = ReadTestParameters.getAttributeValue(testname, "brand");
	String vin1 = ReadTestParameters.getAttributeValue(testname, "vin");
	String vin2 = ReadTestParameters.getAttributeValue(testname, "vin2");
	String invalidPartnerValue=ReadTestParameters.getAttributeValue(testname, "invalidPartnerValue");   //Including special characters
	String logCheck1=ReadTestParameters.getAttributeValue(testname, "logCheck1");
	String logCheck2=ReadTestParameters.getAttributeValue(testname, "logCheck2");
	String vin1ExpectedPartnerValue=ReadTestParameters.getAttributeValue(testname, "vin1ExpectedPartnerValue");
	String vin2ExpectedPartnerValue=ReadTestParameters.getAttributeValue(testname, "vin2ExpectedPartnerValue");
	String vin2ExpectedCountry=ReadTestParameters.getAttributeValue(testname, "vin2ExpectedCountry");
	String vin2ExpectedWholesaler=ReadTestParameters.getAttributeValue(testname, "vin2ExpectedWholesaler");
	String recordsType = ReadTestParameters.getAttributeValue(testname, "recordsType"); //Adapted,Added
	String webAppLogRemoteFilePath =ReadTestParameters.getAttributeValue(testname, "webAppLogRemoteFilePath");
	String webAppLogRemoteFileName =ReadTestParameters.getAttributeValue(testname, "webAppLogRemoteFileName");
	String webAppLogLocalFileName =ReadTestParameters.getAttributeValue(testname, "webAppLogLocalFileName");
	String expectedWARNmessage ="WARN  com.audi.onup.mbbb.service.campaign.vehicle.VehicleData - The PartnerKey='A#B!K' does not match the required pattern ([a-zA-Z0-9]{5})| so information will be discard for WholesalerDTO={number='898', countryCode='DEU', partnerKey='', manufacturer='null'} and vin=vin='BAUSA74N816110928'";
	LocalDateTime currentTimestamp = CurrentDateTime.getCurrentTimeStamp();


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

		logPath = LogReports.logGeneration(testname, "Create_ImportedRecall_WithSpecialCharacters_asCurrentNumberWithinPKN");
		this.setLogPath(logPath);
		System.out.println("Vins Exception-list entry checking....");
		ExceptionList.assertVinNotInExceptionList(testname, vin1);
		ExceptionList.assertVinNotInExceptionList(testname, vin2);
		RECALL_ID =  CreateManualRecall.assertNewRecallID(testname, TestVariables.CreateDeleteRecall_filePath);
		ImportRecallWithFaultyFields.createImportedRecall2WithFaultyfields(RECALL_ID, "recall-ouqa-63083-minimal-data-record.json", "05",vin2,invalidPartnerValue,184,5);
		UpdateTestParameters.updateAttributeValueInJson(campaign, RECALL_ID, testname);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		this.setLogPath(logPath);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "Assert_Logs");
		this.setLogPath(logPath);
		ImporterRecall_Logs.assertLogDetails(testname, recordsType, vin1, logCheck1, currentTimestamp);
		ImporterRecall_Logs.assertLogDetails(testname, recordsType, vin2, logCheck2, currentTimestamp);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "AssertRecall_Inbox");
		this.setLogPath(logPath);
		InboxRecall.assertRecallInbox(testname, RECALL_ID);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "assertVin1PartnerValue");
		this.setLogPath(logPath);
		GetVehicleAttributes.assertAttendingPartnerValue(testname, vin1, vin1ExpectedPartnerValue);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "assertVin2AttributeValues");
		this.setLogPath(logPath);
		GetVehicleAttributes.assertAttendingCountry(testname, vin2,vin2ExpectedCountry);
		GetVehicleAttributes.assertAttendingWholesaler(testname, vin2,vin2ExpectedWholesaler);
		GetVehicleAttributes.assertAttendingPartnerValueNull(testname, vin2);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "assertWebAppLog");
		this.setLogPath(logPath);
		SshConnection.downloadFileAlongWithContentViaSsh(webAppLogRemoteFilePath, webAppLogRemoteFileName);
		String webAppLogContent=SshConnection.readFileLines(webAppLogLocalFileName).toString();
		Assert.assertTrue(webAppLogContent.contains(expectedWARNmessage));
		this.logStepPassed(logPath);


	}

	@Override
	protected void tearDownHook() throws Throwable {

	}

}
