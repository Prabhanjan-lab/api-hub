package testCases.testplanreporting;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import de.sulzer.REST.UtilityProperties;
import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.ComponentHandler;
import de.sulzer.REST.jira.SendTestRunResult;
import de.sulzer.REST.jira.TransitionHandler;
import de.sulzer.REST.jira.UpdateTestRun;
import testCases.util.ConstantsDownloadFiles;

/**
 * This class loads finished text executions up to Jira. Test executions to upload
 * are read from JSON files.
 *
 * @author Sulzer GmbH
 *
 */
@Tag("Configuration")
@Tag("OUDTA-19")
public class OUDTA_19_UploadTestExecutionsFromJson {

	private SendTestRunResult sendTestRunResult;
	private ComponentHandler componentHandler;
	private UpdateTestRun updateTestRun;
	private TransitionHandler transitionHandler;

	@org.junit.jupiter.api.BeforeEach
	protected void setUp() {

    	this.sendTestRunResult = new SendTestRunResult();
    	this.componentHandler = new ComponentHandler();
    	this.updateTestRun = new UpdateTestRun();
    	this.transitionHandler = new TransitionHandler();

	}

	@Test
	public void testTestplanEvaluation() throws Throwable {

		File directory = new File(ConstantsDownloadFiles.DOWNLOAD_DIRECTORY + "/testexecutions/");

		for (File file : directory.listFiles()) {


	        byte[] bytes = Files.readAllBytes(Paths.get(file.toURI()));
	        String content = new String(bytes, StandardCharsets.UTF_8);

//	        this.logToJira(content);
	        System.out.println("Test output: " + content.substring(0, 75));

		}

	}

	@org.junit.jupiter.api.AfterEach
    public void tearDownHook() {

    }

	/**
     * @param testContainer
	 * @param testClass
     *
     */
    private void logToJira(String json) {

        try {

        	String devstackApiToken = System.getProperty("devstackApiToken");

            XrayProperties properties = UtilityProperties.getProperties();

            // creating full token for Base64
            devstackApiToken = properties.getJiraUser() + ":" + devstackApiToken;

            String response = sendTestRunResult.postJsonWithSSL("",
                    Paths.get(properties.getKeyStorePath()),
                    properties.getKeyStorePassword(),
                    json,
                    devstackApiToken);

            if (response != null && response.contains("key")) {
                String testExecKey = "";

                /*
				 * get JSonObject from response
                 */
                JsonObject jsonObject = this.getJsonObject(response);

                // getting expected values from json above
                String transitionId = jsonObject.getJsonObject("testExecIssue").getString("id");
                testExecKey = jsonObject.getJsonObject("testExecIssue").getString("key");

                // set fix version for testExeceIssue
                setFixVersion(properties, testExecKey, devstackApiToken);

                /*
				 *  setting components in test execution run, according/given from test case
                 */
                // requesting test case
                response = this.componentHandler.requestTestCaseForComponents(
                		this.getTestCaseKey(json),
                        Paths.get(properties.getKeyStorePath()),
                        properties.getKeyStorePassword(),
                        devstackApiToken);
                //
                if (response != null) {

//                	System.out.println("RESPONSE: " + response);

                    //
                    jsonObject = this.getJsonObject(response);

                    this.writeUpdateComponents(properties, testExecKey, jsonObject, devstackApiToken);

                    this.writeUpdateLabels(properties, testExecKey, jsonObject, devstackApiToken);

                }

                // close test exec issue
                this.closeTestExecIssue(testExecKey, transitionId, properties, devstackApiToken);

            } else {

                System.out.println("Please log in manually to Jira - Token might be required");
    			System.out.print("Response from writing test result to Jira was: ");

    			if (response.length() > 150) {
    				System.out.println(response.substring(0, 150));
    			} else {
    				System.out.println(response);
    			}

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }

    }

	private String getTestCaseKey(String jsonContent) {

        JsonReader reader = Json.createReader(new StringReader(jsonContent));

        JsonObject json = reader.readObject();

        JsonArray jsonArray = json.getJsonArray("tests");

        JsonObject jsonObjectTest = jsonArray.getJsonObject(0);

        return jsonObjectTest.getString("testKey");

	}

	/**
	 * @param response
	 * @return JsonObject created from given response (String)
	 */
	private JsonObject getJsonObject(String response) {

	    // create JsonReader object
	    JsonReader jsonReader = Json.createReader(new StringReader(response));

	    //get JsonObject from JsonReader
	    return jsonReader.readObject();
	}

	private void setFixVersion(
				XrayProperties properties,
				String testExecKey,
				String devstackApiToken) throws KeyManagementException,
		            UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
	        /*
			 * updating/setting fixVersions with system property
	         */
	        String fixVersion = System.getProperty("fixVersion");
	        fixVersion = fixVersion.replaceAll("\"", "\\\\\"");
	        String jsonUpdateDocument = "{\"fields\": { \"fixVersions\": [{ \"name\": \"" + fixVersion + "\" }] }}";

	        this.updateTestRun.putUpdateJsonWithSSL(testExecKey,
	                Paths.get(properties.getKeyStorePath()),
	                properties.getKeyStorePassword(),
	                jsonUpdateDocument,
	                devstackApiToken);
	    }

	private void closeTestExecIssue(
				String testExecKey,
				String transitionId,
				XrayProperties properties,
				String devstackApiToken) throws KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {

	        String response = "";

	        // requesting transitions
	        response = this.transitionHandler.requestTransition(transitionId,
	                Paths.get(properties.getKeyStorePath()),
	                properties.getKeyStorePassword(),
	                devstackApiToken);

	//		System.out.println("closing-text-execution response:" + response);

	        /*
			 *  Reading response with transitions.
			 *
			 *  Necessary, because ID's can change, and are NOT predictable
			 *  due to possible changes by Jira/-Workflows.
	         */
	        JsonReader jsonReader = Json.createReader(new StringReader(response));

	        JsonArray transitions = jsonReader.readObject().getJsonArray("transitions");

	        String id = "";

	        for (JsonValue transition : transitions) {

	            JsonObject object = transition.asJsonObject();

	            /*
				 * TODO If solution found with encoding problem of new Jenkins, reverse
				 *      to normal state of if ('equals').
	             */
	            if (object.getString("name").contains("Vorgang schlie") ||
	            		object.getString("name").contains("Close Issue")) {
	                id = object.getString("id");
	                break;
	            }

	        }

	        /*
			 * Closing created testKey-Issue.
			 *
			 * In case id is '' test execution will not be closed (has to be done manually in Jira).
	         */
	        String payload = "{ \"fields\": { \"resolution\": { \"name\": \"Done\" } }, \"transition\": { \"id\": \"" + id + "\" } }";

	//		System.out.println("payload of requesting transitions ID: " + payload);
	        //
	        response = this.transitionHandler.doTransition(transitionId,
	                Paths.get(properties.getKeyStorePath()),
	                properties.getKeyStorePassword(),
	                payload,
	                devstackApiToken);

	//		System.out.println("closing-text-execution response:" + response);
	    }

	/**
	     * @param properties
	     * @param testExecKey
	     * @param jsonObject
	     * @throws KeyManagementException
	     * @throws UnrecoverableKeyException
	     * @throws KeyStoreException
	     * @throws NoSuchAlgorithmException
	     * @throws CertificateException
	     * @throws IOException
	     */
	    private void writeUpdateComponents(XrayProperties properties,
	            String testExecKey,
	            JsonObject jsonObject,
				String devstackApiToken) throws
	            KeyManagementException,
	            UnrecoverableKeyException,
	            KeyStoreException,
	            NoSuchAlgorithmException,
	            CertificateException,
	            IOException {

	        //
	        JsonArray testComponents = jsonObject.getJsonObject("fields").getJsonArray("components");

	        for (JsonValue component : testComponents) {
	            String value = component.asJsonObject().getString("name");

	            String jsonUpdateDocument
	                    = "{ \"update\": { \"components\": [ {\"add\": {\"name\": \"" + value + "\"}} ] } }";

	            // updating test exec issue with components from test case
	            this.writeIssueUpdate(properties, testExecKey, testComponents, jsonUpdateDocument, devstackApiToken);

	//			System.out.println(jsonUpdateDocument);
	        }

	    }

	/**
	     * @param properties
	     * @param testExecKey
	     * @param jsonObject
	     * @throws KeyManagementException
	     * @throws UnrecoverableKeyException
	     * @throws KeyStoreException
	     * @throws NoSuchAlgorithmException
	     * @throws CertificateException
	     * @throws IOException
	     */
	    private void writeUpdateLabels(XrayProperties properties,
	            String testExecKey,
	            JsonObject jsonObject,
				String devstackApiToken) throws
	            KeyManagementException,
	            UnrecoverableKeyException,
	            KeyStoreException,
	            NoSuchAlgorithmException,
	            CertificateException,
	            IOException {

	        //
	        JsonArray testLabels = jsonObject.getJsonObject("fields").getJsonArray("labels");

	        if (testLabels.size() == 0) {
	            System.out.println("testLabels is empty!");
	            return;
	        }

	//		System.out.println(testExecKey + " : array = " + testLabels.toString());
	        for (JsonValue label : testLabels) {

	            String value = label.toString();
	//			System.out.println(testExecKey + " : label = " + value);

	            String jsonUpdateDocument
	                    = "{ \"update\": { \"labels\": [ {\"add\": " + value + " } ] } }";

	//			System.out.println("json: " + jsonUpdateDocument);
	            // updating test exec issue with components from test case
	            this.writeIssueUpdate(properties, testExecKey, testLabels, jsonUpdateDocument, devstackApiToken);

	//			System.out.println(jsonUpdateDocument);
	        }

	    }

	/**
	     * Update of component values according Jira documentation:
	     * https://developer.atlassian.com/server/jira/platform/updating-an-issue-via-the-jira-rest-apis-6848604/
	     *
	     * further reading here (10/2018), down below:
	     * https://developer.atlassian.com/server/jira/platform/jira-rest-api-example-edit-issues-6291632/
	     *
	     * @param properties
	     * @param testExecKey
	     * @param testComponents
	     * @param jsonUpdateDocument
	     * @throws KeyManagementException
	     * @throws UnrecoverableKeyException
	     * @throws KeyStoreException
	     * @throws NoSuchAlgorithmException
	     * @throws CertificateException
	     * @throws IOException
	     */
	    private void writeIssueUpdate(XrayProperties properties,
	            String testExecKey,
	            JsonArray testComponents,
	            String jsonUpdateDocument,
				String devstackApiToken) throws
	            KeyManagementException,
	            UnrecoverableKeyException,
	            KeyStoreException,
	            NoSuchAlgorithmException,
	            CertificateException,
	            IOException {

	//		System.out.println("write issue update json: " + jsonUpdateDocument);
	    	this.updateTestRun.putUpdateJsonWithSSL(testExecKey,
	                Paths.get(properties.getKeyStorePath()),
	                properties.getKeyStorePassword(),
	                jsonUpdateDocument,
	                devstackApiToken);

	    }

}