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
import java.util.Map;
import java.util.TreeMap;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.junit.jupiter.api.Tag;

import de.sulzer.REST.UtilityProperties;
import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;
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
@Tag("OUDTA-31")
public class OUDTA_31_TestplanReportingPasses extends BaseReporting {

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

        if (null == TESTPLANKEY) {
            System.out.println("Program was aborted because test plan key wasn't provided!");
        } else {
            this.testplanReportName = TESTPLANKEY + "-" + LocalDateTime.now().toString();
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
                Map<String, TestExecutionResult> tempMapTestExecutions = this.getPasses(response);

                mapTestExecutions.putAll(tempMapTestExecutions);

            }

            response = loadAllTestExecutionsFromGivenTestplan(properties, hrv, devstackApiToken);

            // reading summary per test execution and comparing IDs with previously
            // collected IDs (yes a lot of resource wasting; but with given Jira REST API ....)
            this.readTestExecutionDetails(mapTestExecutions, response);

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

            response = hrv.getResultByUrl(url, properties, devstackApiToken);

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

    private Map<String, TestExecutionResult> getPasses(
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

            // store, in case of 'PASS'
            if (status.contains(STATUS_PASS)) {
                map.put(testCaseId, new TestExecutionResult(STATUS_PASS));
                map.get(testCaseId).setTestCaseId(testCaseId);
            }

        }

        jr.close();

        this.logReporting.info(REPORTING_PASS + "found " + map.size() + " PASSes.");

        return map;
    }

    @Override
    public void tearDownHook() {

    }

}