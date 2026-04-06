package testCases.testplanreporting;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import de.sulzer.REST.ConstantsURLs;
import de.sulzer.REST.UtilityProperties;
import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;
import de.sulzer.logging.ConstantsLogger;
import de.sulzer.logging.LogFormatter;
import testFramework.constants.ConstantsCommunicationRestServer;

/**
 * This unit test creates a report as CSV about all failed tests in a given
 * testplan.
 *
 * Some boring stuff (= documentation)
 *
 * - CARIT Jira: In order that bug numbers are found, tests relate to bugs by 'creates' or
 *   'related to'. Therefore a bug refers to a test by 'is related to'.
 *   OTHERWISE no bug is found!
 *
 * - devstack Jira: In order that bug numbers are found, tests relate to bugs by 'creates' or
 *   'relates to'. Therefore a bug refers to a test by 'relates to'.
 *   OTHERWISE no bug is found!
 *
 * @author Sulzer GmbH
 *
 */
@Tag("Configuration")
@Tag("OUDTA-17-bugger")
public class OUDTA_17_TestplanReportingBug {

	private static final String TEST_CASE_KEY = "testCaseKey";
	private static final String TESTCASEKEY = System.getProperty(TEST_CASE_KEY);

	private static final String USER_DIR = "user.dir";

	// logging
	private Logger logReporting = Logger.getLogger("reporting");

	private static final String JSON_STEPS = "steps";
	private static final String JSON_STEP = "step";
	private static final String JSON_FIELDS = "fields";
	private static final String JSON_FIELD_CUSTOMFIELD_10228 = "customfield_10228";
//	private static final String JSON_FIELD_CUSTOMFIELD_13874 = "customfield_13874";
	private static final String JSON_KEY = "key";
	private static final String JSON_ACTION = "Action";
	private static final String JSON_RESULT_EXPECTED = "Expected Result";

	private static final String OUT_BUGREPORT = "-bug-report.txt";

	private static final String HOST = ConstantsURLs.HOST_JIRA;

	private static final String URL_TEST_CASE_BY_KEY =
			HOST +
			"/jira/rest/api/2/issue/";

	//    @Override
    @org.junit.jupiter.api.BeforeEach
	protected void setUp() {

			this.initLogger(this.logReporting);
    }

    @Test
    public void testTestplanEvaluation() throws Throwable {

    	/*
    	 * preparing REST access to Jira
    	 */

        try {

        	String devstackApiToken = System.getProperty("devstackApiToken");

            XrayProperties properties = UtilityProperties.getProperties();

            // creating full token for Base64
            devstackApiToken = properties.getJiraUser() + ":" + devstackApiToken;

            HandlerRestVariable hrv = new HandlerRestVariable();

	    	/*
	    	 *  load all information of test case (getting bug issues, if available
	    	 *  and write bug report, if it seems necessary)
	    	 */
            this.processDetailsDataOfTestCase(properties, hrv, devstackApiToken);

        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
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
	private void processDetailsDataOfTestCase(
			XrayProperties properties,
			HandlerRestVariable hrv,
			String devstackApiToken) throws KeyManagementException,
			UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

		String response;

		String url = URL_TEST_CASE_BY_KEY + TESTCASEKEY;

		response = hrv.getResultByUrl(url, properties, devstackApiToken);

		this.processingTestCaseTestSteps(response, TESTCASEKEY);

	}

    /**
     * Extracting test step description of given test case.
     *
     * @param response
     * @param testCase
     */
    private void processingTestCaseTestSteps(String response, String testCase) {

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

		List<String[]> listTestSteps = new ArrayList<>();

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

			String[] teststep_pairs = {step, result};

			listTestSteps.add(teststep_pairs);

			// write Bug
	        File outdirectory = new File(System.getProperty(USER_DIR));

	        // output of prophylactic bug reports based on collected test steps
	        try {
				this.print(outdirectory, testCase, listTestSteps);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public void print(File directory, String testCase, List<String[]> listTestSteps) throws IOException {

		if (! directory.exists()) {
			directory.mkdir();
		}

		// crating file name
		String filename = testCase + OUT_BUGREPORT;

		StringBuffer sb = new StringBuffer();

		// Management summary
		sb.append("{panel:title=Management Summary|borderStyle=dashed|borderColor=#ccc|titleBGColor=#FAFAFA|bgColor=#FFFFCE}");
		sb.append(System.lineSeparator());
		sb.append("_<text>_");
		sb.append(System.lineSeparator());
		sb.append("{panel}");
		sb.append(System.lineSeparator());

		// Context and starting situation
		sb.append("{panel:title=Context and Starting Situation|borderStyle=dashed|borderColor=#ccc|titleBGColor=#FAFAFA|bgColor=#FFFFFF}");
		sb.append(System.lineSeparator());
		sb.append("* Test performed: DD.MM.YYYY HH24:MM ");
		sb.append(System.lineSeparator());
		sb.append("* VIN: <if applicable> ");
		sb.append(System.lineSeparator());
		sb.append("* Campaign: <AB20-01, if applicable>");
		sb.append(System.lineSeparator());
		sb.append("* Source: Carport / Excepetion List / ReCall / Deleted");
		sb.append(System.lineSeparator());
		sb.append("* Vehicle Options:");
		sb.append(System.lineSeparator());
		sb.append("||Testvehicle||Prototype Vehicle||Software Version Comparison disabled||Skip mass notification||");
		sb.append(System.lineSeparator());
		sb.append("|(/)|(x)|(/)|(/)| ");
		sb.append(System.lineSeparator());
		sb.append("{panel}");
		sb.append(System.lineSeparator());

		// Input sequence and expected result
		sb.append("{panel:title=Input Sequence and Expected Result|borderStyle=dashed|borderColor=#58af07|titleBGColor=#d1f9ac|bgColor=#FFFFFF}");
		sb.append(System.lineSeparator());

		for (String[] step : listTestSteps) {
			sb.append("# _" + step[0] + "_");
			sb.append(System.lineSeparator());
			sb.append("#* _" + step[1] + "_");
			sb.append(System.lineSeparator());
		}

		sb.append("{panel}");
		sb.append(System.lineSeparator());

		// Actual behaviour
		sb.append("{panel:title=Actual Behavior|borderStyle=dashed|borderColor=#af3f07|titleBGColor=#ffcbb7|bgColor=#FFFFFF}");
		sb.append(System.lineSeparator());
		sb.append("Step #: _<Explain what didn't go according to your expectation>_");
		sb.append(System.lineSeparator());
		sb.append("{panel}");
		sb.append(System.lineSeparator());

		// Preanalysis
		sb.append("{panel:title=Preanalysis|borderStyle=dashed|borderColor=#1874cd|titleBGColor=#6194b9|bgColor=#ffffff}");
		sb.append(System.lineSeparator());
		sb.append("{panel}");
		sb.append(System.lineSeparator());

		// First estimation
		sb.append("{panel:title=First estimation}");
		sb.append(System.lineSeparator());
		sb.append("{panel}");
		sb.append(System.lineSeparator());

		// write test steps into bug report template
		// making sure, content is written as UTF-8, from https://stackoverflow.com/a/1001568
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(directory, filename)), ConstantsCommunicationRestServer.UTF_8));
		bw.write(sb.toString());
		bw.close();

	}

	protected void tearDown() throws Throwable {

    }

	/**
	 *
	 */
	private void initLogger(Logger logger) {

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

}