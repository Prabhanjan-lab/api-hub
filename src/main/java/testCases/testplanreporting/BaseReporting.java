/**
 *
 */
package testCases.testplanreporting;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import de.sulzer.REST.ConstantsURLs;
import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;
import de.sulzer.logging.ConstantsLogger;
import de.sulzer.logging.LogFormatter;
import testFramework.AbstractStandardBehaviour;

/**
 * @author Sulzer GmbH
 *
 */
public abstract class BaseReporting extends AbstractStandardBehaviour {

    protected static final String USER_DIR = "user.dir";

    protected static final String OUQA_ID = "OUQA";

    protected static final String REPORTING_PASS = "REPORTING PASS: ";
    protected static final String REPORTING_FAIL = "REPORTING FAIL: ";

    // logging
    protected Logger logReporting = Logger.getLogger("reporting");

    protected static final String HYPHEN = "-";
    protected String SPACE = " ";

    /*
     * JSON keys
     */
    protected static final String JSON_LATEST_STATUS = "latestStatus";
    protected static final String JSON_SUMMARY = "summary";
    protected static final String JSON_COMMENT = "comment";
    protected static final String JSON_STEPS = "steps";
    protected static final String JSON_BUG = "Bug";
    protected static final String JSON_BUG_PRIORITY = "priority";
    protected static final String JSON_CLOSED = "Closed";
    protected static final String JSON_ISSUETYPE = "issuetype";
    protected static final String JSON_NAME = "name";
    protected static final String JSON_STATUS = "status";
    protected static final String JSON_OUTWARD_ISSUE = "outwardIssue";
    protected static final String JSON_INWARD_ISSUE = "inwardIssue";
    protected static final String JSON_ISSUELINKS = "issuelinks";
    protected static final String JSON_FIELDS = "fields";
    protected static final String JSON_FIELD_CUSTOMFIELD_10228 = "customfield_10228";
    protected static final String JSON_KEY = "key";
    protected static final String JSON_ACTION = "Action";
    protected static final String JSON_RESULT_EXPECTED = "Expected Result";
    protected static final String JSON_EVIDENCES = "evidences";
    protected static final String JASON_DATA = "data";
    protected static final String JSON_CONTENT_TYPE = "contentType";
    protected static final String JASON_FILENAME = "filename";

    public static final String STATUS_PASS = "PASS";
    public static final String STATUS_FAIL = "FAIL";

    // Jira testplan key (coming from environment)
    protected static final String TEST_PLAN_KEY = "testPlanKey";
    protected static final String TESTPLANKEY = System.getProperty(TEST_PLAN_KEY);

    protected String testplanReportName;

    /*
     * JSON contents with evidences
     */
    protected static final String APPLICATION_ZIP = "application/zip";
    protected static final String IMAGE_PNG = "image/png";
    protected static final String IMAGE_JPG = "image/jpg";

    /*
     * URLs for Jira access
     */
    protected static final String URL_PART_TEST = "/test";
    protected static final String URL_PART_TESTEXECUTION = "/testexecution";

    protected static final String HOST = ConstantsURLs.HOST_JIRA;

    protected static final String URL_TESTCASES_BY_TESTPLAN =
            HOST +
            "/jira/rest/raven/1.0/api/testplan/" +
            TESTPLANKEY +
            URL_PART_TEST;

    protected static final String URL_TEST_EXECUTIONS_BY_TESTPLAN =
            HOST +
            "/jira/rest/raven/1.0/api/testplan/" +
            TESTPLANKEY +
            URL_PART_TESTEXECUTION;

    protected static final String URL_TEST_EXECUTION_BY_KEY =
            HOST +
            "/jira/rest/raven/1.0/testruns?testExecKey=";

    protected static final String URL_TEST_CASE_BY_KEY =
            HOST +
            "/jira/rest/api/2/issue/";

    /*
     * Pattern matching
     * source:
     * http://tutorials.jenkov.com/java-regex/matcher.html#find-start-end-methods
     */
    protected static final String PATTERN_VALUE = "[+|-][0-9]{2}:[0-9]{2}";
    protected static final Pattern PATTERN = Pattern.compile(PATTERN_VALUE);

    /*
     * Time stamp regex
     */
    protected static final String PATTERN_ISO8601 = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}";
    protected static final String PATTERN_ISO8601_TIMEZONE_SPACE = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}[ ]{1}[0-9]{2}:[0-9]{2}";
    protected static final String PATTERN_ISO8601_TIMEZONE_NO_SPACE = "[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}[+-]{1}[0-9]{2}:[0-9]{2}";
    protected static final String SUFFIX_OUQA_SELENIUM_SPACE = " OUQA[-]{1}[0-9]{1,}[ ]{1}";
    protected static final String SUFFIX_OUQA_SELENIUM_HYPHEN = " OUQA[-]{1}[0-9]{1,}[-]{1}";
    protected static final String PATTERN_SELENIUM_SAPCE = PATTERN_ISO8601 + SUFFIX_OUQA_SELENIUM_SPACE;
    protected static final String PATTERN_SELENIUM_HYPHEN = PATTERN_ISO8601 + SUFFIX_OUQA_SELENIUM_HYPHEN;
    protected static final String PATTERN_SELENIUM_TIMEZONE_WITH_SPACE_SUFFIX_SPACE = PATTERN_ISO8601_TIMEZONE_SPACE + SUFFIX_OUQA_SELENIUM_SPACE;
    protected static final String PATTERN_SELENIUM_TIMEZONE_WITHOUT_SPACE_SUFFIX_SPACE = PATTERN_ISO8601_TIMEZONE_NO_SPACE + SUFFIX_OUQA_SELENIUM_SPACE;
    protected static final String PATTERN_SELENIUM_TIMEZONE_WITH_SPACE_SUFFIX_HYPHEN = PATTERN_ISO8601_TIMEZONE_SPACE + SUFFIX_OUQA_SELENIUM_HYPHEN;
    protected static final String PATTERN_SELENIUM_TIMEZONE_WITHOUT_SPACE_SUFFIX_HYPHEN = PATTERN_ISO8601_TIMEZONE_NO_SPACE + SUFFIX_OUQA_SELENIUM_HYPHEN;

    protected static final String PATTERN_TIMESTAMP_CTAT = "[0-9]{4}.[0-9]{2}.[0-9]{2}_[0-9]{2}.[0-9]{2}.[0-9]{2}";
    protected static final String SUFFIX_OUQA_CTAT = "[_]OUQA[_][0-9]{1,}[_]";
    protected static final String PATTERN_CTAT = PATTERN_TIMESTAMP_CTAT + SUFFIX_OUQA_CTAT;

    protected static final String SUFFIX_OUQA_ACHALA = "[_]OUQA[-][0-9]{1,}[-]";
    protected static final String PATTERN_ACHALA = PATTERN_TIMESTAMP_CTAT + SUFFIX_OUQA_ACHALA;

    protected static final String SUFFIX_OUQA_WILDSAMPLE_01 = "[_][0-9]{1,}[_]";
    protected static final String PATTERN_TIMESTAMP_WILDSAMPLE_V1 = PATTERN_TIMESTAMP_CTAT + SUFFIX_OUQA_WILDSAMPLE_01;

    /**
     *
     */
    public BaseReporting() {

    }

    /**
     * @return the testplanReportName
     */
    public String getTestplanReportName() {
        return testplanReportName;
    }

    /**
     * @param testplanReportName the testplanReportName to set
     */
    public void setTestplanReportName(String testplanReportName) {
        this.testplanReportName = testplanReportName;
    }

    protected void initLogger(Logger logger) {

        logger.setUseParentHandlers(false);

        try {

            Handler handler = new FileHandler(
                    logger.getName() + ConstantsLogger.LOG_FILE_EXTENSION,
                    ConstantsLogger.LOG_FILE_SIZE,
                    ConstantsLogger.LOG_FILE_COUNT,
                    true); // appending new entries after program was restarted

            handler.setFormatter(new LogFormatter());

            logger.addHandler(handler);

            logger.setLevel(Level.FINE);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @param summary
     * @return the needed test case ID (e. g.: 'OUQA-1234')
     * @throws Exception
     */
    protected String extractTestCaseIdSummary(SummaryTuple st) throws Exception {

        logReporting.fine("SUMMARY FOR ID: " + st.alteredSummary);

        if (! st.alteredSummary.contains(OUQA_ID)) {
            throw new Exception("No OUQA-ID in test execution summary found.");
        }

        int pos = st.alteredSummary.indexOf(OUQA_ID);

        String str = st.alteredSummary.substring(pos);

        String sentence = str.replace(HYPHEN, SPACE);

        String[] parts = sentence.split(SPACE);

        if (parts.length >= 2) {
            st.testKeyId = (parts[0] + HYPHEN + parts[1]).trim();
            return st.testKeyId;
        } else {
            String message = "Test case ID couldn't be extracted from summary: '" + st.alteredSummary + "'.";
            logReporting.fine(message);
            throw new Exception(message);
        }

    }

    protected SummaryTuple prepareSummary(String summary) {

        SummaryTuple st = new SummaryTuple();
        st.ldt = null;

        Pattern pattern;
        Matcher matcher;

        // Selenium without time zone, and space after OUQA-number
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_SELENIUM_SAPCE);
            matcher = pattern.matcher(summary);

            if (matcher.find()) {
                st.ldt = LocalDateTime.parse(summary.substring(0, summary.indexOf(" "))); // no time zone extension
                st.alteredSummary = summary;
            }
        }

        // Selenium without time zone, and hyphen after OUQA-number
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_SELENIUM_HYPHEN);
            matcher = pattern.matcher(summary);

            if (matcher.find()) {
                st.ldt = LocalDateTime.parse(summary.substring(0, summary.indexOf(" "))); // no time zone extension
                st.alteredSummary = summary;
            }
        }

        // Selenium with space before time zone and hyphen as suffix
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_SELENIUM_TIMEZONE_WITH_SPACE_SUFFIX_HYPHEN);
            matcher = pattern.matcher(summary);

            String str = summary.replaceFirst(" ", "-"); // <-- setting dummy for following operations

            if (matcher.find()) {
                st.ldt = LocalDateTime.parse(str.substring(0, str.indexOf(" ") - 6)); // time zone extension
                st.alteredSummary = str;
            }
        }

        // Selenium with space before time zone and space as suffix
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_SELENIUM_TIMEZONE_WITH_SPACE_SUFFIX_SPACE);
            matcher = pattern.matcher(summary);

            String str = summary.replaceFirst(" ", "-"); // <-- setting dummy for following operations

            if (matcher.find()) {
                st.ldt = LocalDateTime.parse(str.substring(0, str.indexOf(" ") - 6)); // time zone extension
                st.alteredSummary = str;
            }
        }

        // Selenium with [+-] and time zone and hyphen as suffix
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_SELENIUM_TIMEZONE_WITHOUT_SPACE_SUFFIX_HYPHEN);
            matcher = pattern.matcher(summary);

            if (matcher.find()) {
                st.ldt = LocalDateTime.parse(summary.substring(0, summary.indexOf(" ") - 6)); // time zone extension
                st.alteredSummary = summary;
            }
        }

        // Selenium with [+-] and time zone and space as suffix
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_SELENIUM_TIMEZONE_WITHOUT_SPACE_SUFFIX_SPACE);
            matcher = pattern.matcher(summary);

            if (matcher.find()) {
                st.ldt = LocalDateTime.parse(summary.substring(0, summary.indexOf(" ") - 6)); // time zone extension
                st.alteredSummary = summary;
            }
        }

        // CTAT
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_CTAT);
            matcher = pattern.matcher(summary);

            System.out.println("SUMMARY: " + summary);

            if (matcher.find()) {
                // replacement orgy following..
                String date = summary.replaceFirst("\\.", "-");
                date = date.replaceFirst("\\.", "-");
                date = date.replaceFirst("_", "T");
                date = date.replaceFirst("\\.", ":");
                date = date.replaceFirst("\\.", ":");
                date = date.replaceFirst("_", " ");
                //
                date = date.replaceAll("_", "-"); // for all left '_'

                st.ldt = LocalDateTime.parse(date.substring(0, date.indexOf(" ")));
                st.alteredSummary = date.replaceAll("_", " ");

                System.out.println("ALTERED SUMMARY: " + st.alteredSummary);
            }
        }

        // Achala
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_ACHALA);
            matcher = pattern.matcher(summary);

            if (matcher.find()) {
                // replacement orgy following..
                String date = summary.replaceFirst("\\.", "-");
                date = date.replaceFirst("\\.", "-");
                date = date.replaceFirst("_", "T");
                date = date.replaceFirst("\\.", ":");
                date = date.replaceFirst("\\.", ":");
                date = date.replaceFirst("_", " ");
                //
                date = date.replaceAll("_", "-"); // for all left '_'

                st.ldt = LocalDateTime.parse(date.substring(0, date.indexOf(" ")));
                st.alteredSummary = date.replaceAll("_", " ");
            }
        }

        // wild sample v1
        if (null == st.ldt) {
            pattern = Pattern.compile(PATTERN_TIMESTAMP_WILDSAMPLE_V1);
            matcher = pattern.matcher(summary);

            if (matcher.find()) {
                // replacement orgy following..
                String date = summary.replaceFirst("\\.", "-");
                date = date.replaceFirst("\\.", "-");
                date = date.replaceFirst("_", "T");
                date = date.replaceFirst("\\.", ":");
                date = date.replaceFirst("\\.", ":");
                date = date.replaceFirst("_", " OUQA-"); // <-- more complex replacement for further processing for test case ID
                //
                date = date.replaceAll("_", "-"); // for all left '_'

                st.ldt = LocalDateTime.parse(date.substring(0, date.indexOf(" ")));
                st.alteredSummary = date.replaceAll("_", " ");
            }
        }

        return st;
    }

    protected void readTestExecutionDetails(
            Map<String, TestExecutionResult> mapTestExecutions,
            String response) {

        /*
         * Read JSON for extracting latest FAILed test executions.
         */
        JsonReader jr = Json.createReader(new StringReader(response));
        JsonArray jsonArray = jr.readArray();

        for (JsonValue jv : jsonArray) {

            String testExecutionKey = jv.asJsonObject().getString(JSON_KEY);
            String summary = jv.asJsonObject().getString(JSON_SUMMARY);

            this.logReporting.fine(
                    REPORTING_FAIL + "Reading test execution '" + testExecutionKey + "' summary '" + summary + "'.");

            // timestamp
            LocalDateTime ldt = null;

            SummaryTuple st = this.prepareSummary(summary);

            ldt = st.ldt;

            if (null == ldt) {
                logReporting.info("Couldn't extract time stamp from summary '" + summary + "'. Continued with next entry.");
                continue;
            }

            // test case ID
            String testCaseId;

            try {
                testCaseId = this.extractTestCaseIdSummary(st).trim();
            } catch (Exception e) {
                logReporting.info("Couldn't extract OUQA-number of summary '" + summary + "'. Continued with next entry.");
                continue;
            }

            if (mapTestExecutions.containsKey(testCaseId)) {

                TestExecutionResult ter = mapTestExecutions.get(testCaseId);

                if (null == ter.getTimestamp()) {

                    ter.setTimestamp(ldt);
                    ter.setTestExecutionId(testExecutionKey);

                } else if (null != ter.getTimestamp()) {

                    if (ldt.isAfter(ter.getTimestamp())) {
                        ter.setTimestamp(ldt);
                        ter.setTestExecutionId(testExecutionKey);
                    }

                }

            } else {
                logReporting.fine("Extracting details test executoin: Test case ID '" + testCaseId + "' not in HashMap.");
            }

        }

        jr.close();

    }

    /**
     * @param properties
     * @param hrv
     * @return
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    protected String loadAllTestExecutionsFromGivenTestplan(
            XrayProperties properties,
            HandlerRestVariable hrv,
            String devstackApiToken)
            throws KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
            CertificateException, IOException {

        return hrv.getResultByUrl(
                URL_TEST_EXECUTIONS_BY_TESTPLAN,
                properties,
                devstackApiToken);

    }

    /**
     * Extracting test step description of given test case.
     *
     * @param mapTestExecutions
     * @param response
     */
    protected void processingTestCaseTestSteps(Map<String, TestExecutionResult> mapTestExecutions, String response) {

        /*
         * Read JSON for bug ID of linked test case.
         */
        JsonReader jr = Json.createReader(new StringReader(response));
        JsonObject jsonObject = jr.readObject();

        String testCaseKey = jsonObject.getString(JSON_KEY);

        logReporting.fine("LOGGING TEST STEPS Got test case ID '" + testCaseKey + "'.");

        // splitting up JSON test case and getting supposed bug data
        JsonObject fields = jsonObject.getJsonObject(JSON_FIELDS);

        // splitting up JSON test case and getting test step entries
        JsonObject customfield_10228 = fields.getJsonObject(JSON_FIELD_CUSTOMFIELD_10228);

        JsonArray jaSteps = customfield_10228.getJsonArray(JSON_STEPS);

        for (JsonValue jv : jaSteps) {

            JsonObject jo = jv.asJsonObject();

            JsonObject joTestStep = jo.getJsonObject(JSON_FIELDS);

            String step = "";
            String result = "";

            if (null != joTestStep) {

                try {
                    step = joTestStep.getString(JSON_ACTION);
                } catch (Exception e) {
                    step = "";
                }

                // unfortunately a result description can be not existent
                try {
                    result = joTestStep.getString(JSON_RESULT_EXPECTED);
                } catch (Exception e) {
                    result = "";
                }

            }

            if (null == step) {
                step = "";
            }

            if (null == result) {
                result = "";
            }

            String[] s = {step, result};

            mapTestExecutions.get(testCaseKey).addTestStep(s);

        }

    }

    /**
     * Extracting test case title.
     *
     * @param mapTestExecutions
     * @param response
     */
    protected void processingTestCaseTitle(Map<String, TestExecutionResult> mapTestExecutions, String response) {

        String rsp = "";

        if (response.length() >= 150) {
            rsp = response.substring(0, 150);
        } else {
            rsp = response;
        }

        logReporting.info("PROCESSING RESPONSE '" + rsp + "'.");

        /*
         * Read JSON for bug ID of linked test case.
         */
        JsonReader jr = Json.createReader(new StringReader(response));
        JsonObject jsonObject = jr.readObject();

        String testCaseKey = jsonObject.getString(JSON_KEY);

        logReporting.fine("LOGGING TEST STEPS Got test case ID '" + testCaseKey + "'.");

        // splitting up JSON test case and getting supposed bug data
        JsonObject fields = jsonObject.getJsonObject(JSON_FIELDS);

        // splitting up JSON test case and getting test step entries
        String summary = fields.getString(JSON_SUMMARY);

        mapTestExecutions.get(testCaseKey).setTestCaseTitle(summary);

    }

    /**
     * For details regarding finding bugs per test case, see head of this source
     * file.
     *
     * @param mapTestExecutions
     * @param response
     */
    protected void processingTestCaseBugs(Map<String, TestExecutionResult> mapTestExecutions, String response) {

        String bugKey = "";

        /*
         * Read JSON for bug ID of linked test case.
         */
        JsonReader jr = Json.createReader(new StringReader(response));
        JsonObject jsonObject = jr.readObject();

        String testCaseKey = jsonObject.getString(JSON_KEY);

        logReporting.fine("LOGGING BUGS Got test case ID '" + testCaseKey + "'.");

        // splitting up JSON test case and getting supposed bug data
        JsonObject fields = jsonObject.getJsonObject(JSON_FIELDS);

        JsonArray jaIssueLinks = fields.getJsonArray(JSON_ISSUELINKS);

        logReporting.fine(
                "LOGGING BUGS Got test case ID '" + testCaseKey +
                "', Issue links found: " + jaIssueLinks.size());

        for (JsonValue jv : jaIssueLinks) {

            JsonObject jo = jv.asJsonObject();

            JsonObject outwardissue = jo.getJsonObject(JSON_OUTWARD_ISSUE);
            JsonObject inwardissue = jo.getJsonObject(JSON_INWARD_ISSUE);

            JsonObject issue = null;

            if (null == outwardissue && null == inwardissue) {

                logReporting.fine(
                        "LOGGING BUGS Test case ID '" + testCaseKey +
                        "', no Bug ID found.");

                continue;

            } else {

            	if (null != outwardissue) {
            		bugKey = outwardissue.getString(JSON_KEY);
            		issue = outwardissue;
            	}

            	if (null != inwardissue) {
            		bugKey = inwardissue.getString(JSON_KEY);
            		issue = inwardissue;
            	}

            }

            if (null == issue) {
            	continue;
            }

            JsonObject issueFields = issue.getJsonObject(JSON_FIELDS);

            JsonObject status = issueFields.getJsonObject(JSON_STATUS);
            String currentState = status.getString(JSON_NAME);

            JsonObject issutype = issueFields.getJsonObject(JSON_ISSUETYPE);

            String issueTypeName = issutype.getString(JSON_NAME);

            JsonObject priority = issueFields.getJsonObject(JSON_BUG_PRIORITY);

            String priorityName = priority.getString(JSON_NAME);

            //

            logReporting.fine(
                    "LOGGING BUGS Test case ID '" + testCaseKey +
                    "', Bug Key: '" + bugKey + "' (status: " + currentState + ", priority: " + priorityName + ").");

            //

            if (!currentState.equals(JSON_CLOSED) &&
                    issueTypeName.equals(JSON_BUG)) {

                if (mapTestExecutions.containsKey(testCaseKey) &&
                        ! mapTestExecutions.get(testCaseKey).getBugs().contains(bugKey)) {

                    mapTestExecutions.get(testCaseKey).getBugs().add(bugKey);
                    mapTestExecutions.get(testCaseKey).getBugPriorities().add(priorityName);

                }

            }

        }

        jr.close();

    }

    /**
     * @param properties
     * @param hrv
     * @return
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws IOException
     */
    protected String loadAllTestCasesFromGivenTestplan(
            XrayProperties properties,
            HandlerRestVariable hrv,
            String devstackApiToken,
            int page,
            int limit)
                    throws KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException,
                    CertificateException, IOException {

        String response = hrv.getResultByUrl(
                URL_TESTCASES_BY_TESTPLAN + "?limit=" + limit + "&page=" + page,
                properties,
                devstackApiToken);

        return response;
    }

}

