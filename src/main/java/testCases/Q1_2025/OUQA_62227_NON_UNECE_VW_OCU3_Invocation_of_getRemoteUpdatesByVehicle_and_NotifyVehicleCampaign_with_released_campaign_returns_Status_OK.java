package testCases.Q1_2025;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

import de.sulzer.pages.splunk.SplunkHomepage;
import testCases.SoapUI.reusable_TestSteps.NotifyVehicleCampaign;
import testCases.SoapUI.reusable_TestSteps.currentSPSStatusofCampaign;
import testCases.readData.ReadTestParameters;
import testCases.reusable_TestSteps.ActiveQueueVCSOStatus;
import testCases.reusable_TestSteps.GetDetailsofVCA;
import testCases.utils.CurrentDateTime;
import testCases.utils.Environments;
import testCases.utils.LogReports;
import testCases.utils.logMessageVerifiedInLogFile;
import testFramework.AbstractStandardBehaviour;
import testFramework.utilities.recall.SshConnection;

@Tag("OUQA-62227")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("25Q1")

public class OUQA_62227_NON_UNECE_VW_OCU3_Invocation_of_getRemoteUpdatesByVehicle_and_NotifyVehicleCampaign_with_released_campaign_returns_Status_OK
		extends AbstractStandardBehaviour {

	String logPath = "";

	String testname = "NON_UNECE_VW_OCU3_Invocation_of_getRemoteUpdatesByVehicle_and_NotifyVehicleCampaign_with_released_campaign_returns_Status_OK";
	String vin = ReadTestParameters.getAttributeValue(testname, "vin");
	String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
	String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
	String operationCode = ReadTestParameters.getAttributeValue(testname, "operationCode");

	String actionDetails = ReadTestParameters.getAttributeValue(testname, "actionDetails");
	String notifyVehicleStatus = ReadTestParameters.getAttributeValue(testname, "notifyVehicleStatus");
	String webAppLogRemoteFilePath = ReadTestParameters.getAttributeValue(testname, "webAppLogRemoteFilePath");
	String webAppLogRemoteFileName = ReadTestParameters.getAttributeValue(testname, "webAppLogRemoteFileName");
	String webAppLogLocalFileName = ReadTestParameters.getAttributeValue(testname, "webAppLogLocalFileName");
	String expectedInfomessageforRequest = "com.audi.cip.aou.commons.utils.soap.SoapRequestConsumerLoggingHandler - Request to api='16'";
	String expectedInfomessageforResponse = "INFO  com.audi.cip.aou.commons.utils.soap.SoapRequestConsumerLoggingHandler - Response to api='16'";
	LocalDateTime currentTimestamp = CurrentDateTime.getCurrentTimeStamp();
	private SplunkHomepage spage;

	@Override
	@BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(8); // test has Eight test step
		}
	}

	@Override
	protected void testHook() throws Throwable {
		LogReports.logs(testname);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		this.setLogPath("");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCSOStatus5");
		this.setLogPath(logPath);
		ActiveQueueVCSOStatus.assertActiveQueue(testname, vin, campaign, "5");
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "IsNotifyPossibleTrue");
		this.setLogPath(logPath);
		currentSPSStatusofCampaign.assertIsNotifyPossibleTrue(testname, vin, campaign, criterion);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "NotifyVehicleCampaign");
		this.setLogPath(logPath);
		NotifyVehicleCampaign.assertNotifyVehicleStatusOK(testname, vin, campaign, criterion, notifyVehicleStatus);
		this.logStepPassed(logPath);

		logPath = LogReports.logGeneration(testname, "VCADetails");
		this.setLogPath(logPath);
		GetDetailsofVCA.assertVCADetails(testname, campaign, vin, criterion, actionDetails, operationCode);
		this.logStepPassed(logPath);

		if (Environments.getEnvironment().equalsIgnoreCase("Demo_ECE")) {
			logPath = LogReports.logGeneration(testname, "assertWebAppLog");
			this.setLogPath(logPath);
			SshConnection.downloadFileAlongWithContentViaSsh(webAppLogRemoteFilePath, webAppLogRemoteFileName);
			List<String> wepAppLogContent = SshConnection.readFileLines(webAppLogLocalFileName);

			Assert.assertTrue("Request log not found in last " + 5 + " minutes", logMessageVerifiedInLogFile
					.isLogMessagePresentInRecentTime(wepAppLogContent, expectedInfomessageforRequest, 5));

			Assert.assertTrue("Response log not found in last " + 5 + " minutes", logMessageVerifiedInLogFile
					.isLogMessagePresentInRecentTime(wepAppLogContent, expectedInfomessageforResponse, 5));
			this.logStepPassed(logPath);
		} else {
			logPath = LogReports.logGeneration(testname, "assertWebAppLog");
			this.setLogPath(logPath);
			String endPoint = "/search?q=search%20" + vin
					+ " AND Request to api%3D'16'&display.page.search.mode=smart&dispatch.sample_ratio=1&earliest=-24h%40h&latest=now";
			initDriverforSplunk(true, endPoint);
			this.intializepages();
			Thread.sleep(120000);
			spage.getDetailedLog(expectedInfomessageforRequest);
			this.logStepPassed(logPath);

		}

		System.out.println("Test Execution Completed.");

	}

	@Override
	protected void tearDownHook() throws Throwable {
		// TODO Auto-generated method stub

	}

	private void intializepages() {
		this.spage = new SplunkHomepage(this.getWebDriver());
	}
}
