package testCases.testplanreporting;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.junit.jupiter.api.Tag;

import de.sulzer.REST.UtilityProperties;
import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;
import testCases.testplanreporting.output.PrintBugReportDescription;
import testCases.testplanreporting.output.PrintPdf;
import testCases.testplanreporting.output.PrintTableCsv;
import testCases.testplanreporting.output.PrintTableJiraIssueTable;

/**
 * This unit test creates a report as CSV about all failed tests in a given
 * testplan.
 *
 * Some boring stuff (= documentation)
 *
 * - In order that bug numbers are found, tests relate to bugs by 'creates' or
 *   'related to'. Therefore a bug refers to a test by 'is related to'.
 *   OTHERWISE no bug is found!
 *
 * @author Sulzer GmbH
 *
 */
@Tag("Configuration")
@Tag("OUDTA-17")
public class OUDTA_17_TestplanReportingFails extends BaseReporting {

    @Override
    @org.junit.jupiter.api.BeforeEach
    protected void setUp() throws Throwable {

        try {
            super.setUp();
        } catch (Throwable e) {
            this.setThrowable(e);
            throw e;
        } finally {
            this.initTestContainer(0); // test has 0 test steps
            this.initLogger(this.logReporting);
        }
    }

    @Override
    protected void testHook() throws Throwable {

        if (null == BaseReporting.TESTPLANKEY) {
            System.out.println("Program was aborted because test plan key wasn't provided!");
        } else {
            this.testplanReportName = BaseReporting.TESTPLANKEY + "-" + LocalDateTime.now().toString();
        }

        /*
         * preparing REST access to Jira
         */

        try {

            String devstackApiToken = System.getProperty("devstackApiToken");

            XrayProperties properties = UtilityProperties.getProperties();

            // creating full token for Base64
            devstackApiToken = properties.getJiraUser() + ":" + devstackApiToken;

            HandlerRestVariable hrv = new HandlerRestVariable();

            String response = "";

            Map<String, TestExecutionResult> mapTestExecutions = new TreeMap<>();

            for (int i = 1; i <= 3; i++) {

                response = this.loadAllTestCasesFromGivenTestplan(properties, hrv, devstackApiToken, i, 200);

                // get all test case IDs marked as failed and store the IDs
                Map<String, TestExecutionResult> tempMapTestExecutions = this.getFailures(response);

                mapTestExecutions.putAll(tempMapTestExecutions);

            }

//
//            String response = this.loadAllTestCasesFromGivenTestplan(properties, hrv, devstackApiToken);
//
//            // get all test case IDs marked as failed and store the IDs
//            Map<String, TestExecutionResult> mapTestExecutions = this.getFailures(response);

            response = loadAllTestExecutionsFromGivenTestplan(properties, hrv, devstackApiToken);

            // reading summary per test execution and comparing IDs with previously
            // collected IDs (yes a lot of resource wasting; but with given Jira REST API ....)
            this.readTestExecutionDetails(mapTestExecutions, response);

            // getting error from test step in test execution (maybe first 100 to 150 characters)
            // and creating/setting test execution/test steps link
            this.processErrorMessagesFromTestExecution(properties, hrv, mapTestExecutions, devstackApiToken);

            /*
             *  load all information of test case (getting bug issues, if available
             *  and write bug report, if it seems necessary)
             */
            this.processDetailsDataOfTestCase(properties, hrv, mapTestExecutions, devstackApiToken);

            File outdirectory = new File(System.getProperty(USER_DIR));

            this.writeResultsToFile(mapTestExecutions, outdirectory, hrv, properties, devstackApiToken);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param mapTestExecutions
     * @param outdirectory
     * @param hrv
     * @param properties
     * @throws IOException
     */
    private void writeResultsToFile(
            Map<String, TestExecutionResult> mapTestExecutions,
            File outdirectory,
            HandlerRestVariable hrv,
            XrayProperties properties,
            String devstackApiToken)
            throws IOException {

        // output of collected information about failed test case and test execution (CSV table)
        PrintTestExecutionResult ptc = new PrintTableCsv(this.testplanReportName, hrv, properties, devstackApiToken);
        ptc.print(outdirectory, mapTestExecutions);

        // output PDF of CSV data, but with screenshot
        PrintPdf pp = new PrintPdf(hrv, properties, this.testplanReportName, devstackApiToken);
        pp.print(outdirectory, mapTestExecutions);

        // output of collected information about failed test case and test execution (Jira table)
        PrintTestExecutionResult ptjit = new PrintTableJiraIssueTable(this.testplanReportName);
        ptjit.print(outdirectory, mapTestExecutions);

        // output of prophylactic bug reports based on collected test steps
        PrintTestExecutionResult pbgd = new PrintBugReportDescription();
        pbgd.print(outdirectory, mapTestExecutions);

    }

    /**
     * @param properties
     * @param hrv
     * @param mapTestExecutions
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    private void processDetailsDataOfTestCase(
            XrayProperties properties,
            HandlerRestVariable hrv,
            Map<String, TestExecutionResult> mapTestExecutions,
            String devstackApiToken) throws KeyManagementException,
            UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        String response;

        for (String key : mapTestExecutions.keySet()) {

            String url = URL_TEST_CASE_BY_KEY + key;

            logReporting.info("TEST CASE DETAILS URL: " + url);

            response = hrv.getResultByUrl(url, properties, devstackApiToken);

            if (response.trim().equalsIgnoreCase("</html>")) {
                logReporting.info("HTML ERROR TEST CASE KEY: " + key + ", RESPONSE: " + response);
                continue;
            }

            // getting title of test case
            this.processingTestCaseTitle(mapTestExecutions, response);

            // getting bug reports, if available
            this.processingTestCaseBugs(mapTestExecutions, response);

            // writing bug template of test case, if FAIL but no bugs available
            if (mapTestExecutions.get(key).getBugs().size() == 0) {
                this.processingTestCaseTestSteps(mapTestExecutions, response);
            }

        }

    }

    /**
     * @param properties
     * @param hrv
     * @param mapTestExecutions
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    private void processErrorMessagesFromTestExecution(
            XrayProperties properties,
            HandlerRestVariable hrv,
            Map<String, TestExecutionResult> mapTestExecutions,
            String devstackApiToken) throws KeyManagementException,
            UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

        String response;

        for (Entry<String, TestExecutionResult> entry : mapTestExecutions.entrySet()) {

            String testExecutionId = entry.getValue().getTestExecutionId();

            String url = URL_TEST_EXECUTION_BY_KEY + testExecutionId;

            this.logReporting.fine("Reading test execution for " + testExecutionId + ", on URL " + url);

            response = hrv.getResultByUrl(url, properties, devstackApiToken);

            this.extractErrorDetailsFromTestExecution(response, entry.getValue());

            entry.getValue().createTestExecutionTestStepLink();

        }

    }

    private void extractErrorDetailsFromTestExecution(
            String response,
            TestExecutionResult testExecutionResult) {

        String commentErrorMessage = "";
        String commentEvidenceLinkScreenshot = "";
        String commentEvidenceLinkZipFileErrorText = "";

        /*
         * Read JSON for error message of test executions; test step.
         */
        JsonReader jr = Json.createReader(new StringReader(response));

        JsonArray jsonArray = null;

        try {
            jsonArray = jr.readArray();
        } catch (Exception e) {
            testExecutionResult.setErrorMessageShortened("No test steps available.");
            return ;
        }

        JsonObject jo = jsonArray.getJsonObject(0);

        JsonArray ja = jo.getJsonArray(JSON_STEPS);

        for (JsonValue jv : ja) {

            String status = jv.asJsonObject().getString(JSON_STATUS);

            if (STATUS_FAIL.equals(status.trim())) {

                // getting error message
                try {
                    commentErrorMessage = jv.asJsonObject().getString(JSON_COMMENT);
                } catch (Exception e) {
                    commentErrorMessage = "No comment (error message) available for test step.";
                }

                // getting link of first evidence, if available (screenshot as PNG)
                try {

                    JsonArray jaEvidences = jv.asJsonObject().getJsonArray(JSON_EVIDENCES);

                    logReporting.fine("LOGGING EVIDENCE Test case has " + jaEvidences.size() + " evidences.");

                    if (jaEvidences.size() > 0) {

                        for (JsonValue evidence : jaEvidences) {

                            JsonObject jsonObject = evidence.asJsonObject();

                            if (jsonObject.containsKey(JASON_DATA)) {

                                logReporting.fine("LOGGING EVIDENCE Test case has evidence");

                                String contentType = jsonObject.getString(JSON_CONTENT_TYPE);

                                if (contentType.equals(APPLICATION_ZIP)) {

                                    logReporting.fine("LOGGING EVIDENCE Evidence zip file: " + evidence.toString());

                                    commentEvidenceLinkZipFileErrorText = this.getFileLink(evidence);

                                } else if (contentType.equals(IMAGE_PNG) ||
                                        contentType.equals(IMAGE_JPG)) {

                                    logReporting.fine("LOGGING EVIDENCE Evidence screenshot file: " + evidence.toString());

                                    commentEvidenceLinkScreenshot = this.getFileLink(evidence);

                                }

                            }

                        }

                    }

                } catch (Exception e) {
                    commentEvidenceLinkScreenshot = "";
                }

                logReporting.fine("Found evidence screenshot link: " + commentEvidenceLinkScreenshot);
                logReporting.fine("Found evidence error zip file link: " + commentEvidenceLinkZipFileErrorText);

            }

        }

        jr.close();

        if (commentErrorMessage.length() >= 150) {
            testExecutionResult.setErrorMessageShortened(commentErrorMessage.substring(0, 150));
        } else {
            testExecutionResult.setErrorMessageShortened(commentErrorMessage);
        }

        testExecutionResult.setLinkEvidenceScreenshot(commentEvidenceLinkScreenshot);
        testExecutionResult.setLinkEvidenceZipFileErrorText(commentEvidenceLinkZipFileErrorText);

    }

    /**
     * @param evidence
     * @return
     */
    private String getFileLink(JsonValue evidence) {
        String commentEvicendeLinkScreenshot;
        commentEvicendeLinkScreenshot = evidence.asJsonObject().getString(JASON_DATA);
        commentEvicendeLinkScreenshot = commentEvicendeLinkScreenshot.replace("\"", "");
        commentEvicendeLinkScreenshot = commentEvicendeLinkScreenshot.replace(" ", "%20");
        return commentEvicendeLinkScreenshot;
    }

    private Map<String, TestExecutionResult> getFailures(
            String response) {

        Map<String, TestExecutionResult> map = new TreeMap<>();

        if (response.length() > 150) {
            System.out.println("Failures (JSON start): " + response.substring(0, 150));
        } else {
            System.out.println("Failures (JSON start): " + response);
        }

        /*
         * Read JSON for FAILed test executions.
         */
        JsonReader jr = Json.createReader(new StringReader(response));
        JsonArray jsonArray = jr.readArray();

        for (JsonValue jv : jsonArray) {

            String testCaseId = jv.asJsonObject().getString(JSON_KEY);
            String status = jv.asJsonObject().getString(JSON_LATEST_STATUS);

            // store, in case of 'FAIL'
            if (status.contains(STATUS_FAIL)) {
                map.put(testCaseId, new TestExecutionResult(STATUS_FAIL));
                map.get(testCaseId).setTestCaseId(testCaseId);
            }

        }

        jr.close();

        this.logReporting.info(REPORTING_FAIL + "found " + map.size() + " FAILS.");

        return map;
    }

    @Override
    public void tearDownHook() {

    }

}