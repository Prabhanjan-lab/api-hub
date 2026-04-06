/**
 *
 */
package testFramework.utilities.testservices;

import java.io.*;
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

import de.sulzer.REST.UtilityProperties;
import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.ComponentHandler;
import de.sulzer.REST.jira.SendTestRunResult;
import de.sulzer.REST.jira.TransitionHandler;
import de.sulzer.REST.jira.UpdateTestRun;
import de.sulzer.container.TestContainer;
import testFramework.utilities.properties.PropertyReader;
import java.util.Properties;

/**
 * @author Sulzer GmbH
 *
 */
public class ReporterJira {

    private SendTestRunResult sendTestRunResult;
    private ComponentHandler componentHandler;
    private UpdateTestRun updateTestRun;
    private TransitionHandler transitionHandler;

    private final JsonValue AUTOMATED = Json.createValue("automated");
    private final JsonValue GUI = Json.createValue("GUI");

    private String keystorePath;
    private String keystorePW;

    /**
     *
     */
    public ReporterJira() {

        this.sendTestRunResult = new SendTestRunResult();
        this.componentHandler = new ComponentHandler();
        this.updateTestRun = new UpdateTestRun();
        this.transitionHandler = new TransitionHandler();

    }

    /**
     * @param testContainer
     * @param testClass
     *
     */
    public void logToJira(TestContainer testContainer, String[] classNameParts) {

        try {
            Properties propertiesXray = new Properties();
            XrayProperties properties = UtilityProperties.getProperties();
            String os = System.getProperty("os.name").toLowerCase();
            String devstackApiToken = "";
            String response = "";
            PropertyReader pr = new PropertyReader();


            if (os.contains("linux")) {
                propertiesXray.load(new FileInputStream(new File(System.getProperty("user.dir") + "/target/classes/certificate.properties")));

            }
            if (os.contains("windows")) {
                propertiesXray.load(new FileInputStream(new File(System.getProperty("user.dir") + "/target/classes/certificate.properties")));

            }


            //System.out.println("***Jira 1 jiraUser: " +System.getProperty("jirauser"));
            //System.out.println("***Jira 2: " +properties.getKeyStorePath());
            // creating full token for Base64
            if (os.contains("linux")) {
                devstackApiToken = System.getProperty("devstackApiToken");
                // System.out.println("***xxx :" +devstackApiToken.substring(7,14));
                // creating full token for Base64
                devstackApiToken = System.getProperty("jirauser") + ":" + devstackApiToken;
                keystorePath = System.getProperty("CTAT_KeyStore");
                keystorePW = System.getProperty("CTAT_KeyStore_Password");

            }
            if (os.contains("windows")) {
                devstackApiToken = pr.readProperty("local-config.properties", "devstackApiToken");
                // creating full token for Base64
                devstackApiToken = properties.getJiraUser() + ":" + devstackApiToken;
                //System.out.println("**devstackHadiOL: " +devstackApiToken);
                keystorePath = propertiesXray.getProperty("keyStorePath");
                keystorePW = propertiesXray.getProperty("KeyStorePassword");

            }


            response = sendTestRunResult.postJsonWithSSL(classNameParts[0],
                    Paths.get(keystorePath),
                    keystorePW,
                    testContainer.getAsJSON().toString(),
                    devstackApiToken);


            if (response.startsWith("{\"error\":")) {
                System.out.println("WRITING TEST RESULT CAUSED AN ERROR. RESPONSE: " + response);
            }

            if (response != null && response.contains("key")) {
                String testExecKey = "";

                //System.out.println("***Reporting geht: " +response);

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
                response = componentHandler.requestTestCaseForComponents(testContainer.getTest().getTestKey(),
                        Paths.get(keystorePath),
                        keystorePW,
                        devstackApiToken);
                //
                if (response != null) {

//                	System.out.println("RESPONSE: " + response);

                    jsonObject = this.getJsonObject(response);
                    this.writeUpdateComponents(properties, testExecKey, jsonObject, devstackApiToken);
                    this.writeUpdateLabels(properties, testExecKey, jsonObject, devstackApiToken);

                }

                // close test exec issue
                closeTestExecIssue(testExecKey, transitionId, properties, devstackApiToken);

            } else {

                System.out.println("Please log in manually to Jira - Token might be required");
                System.out.print("Response from writing test result to Jira was: ");

                if (response.length() > 350) {
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
        String os = System.getProperty("os.name").toLowerCase();
        PropertyReader pr = new PropertyReader();
        String fixVersion = "";


        if (os.contains("linux")) {
            fixVersion = System.getProperty("fixVersion");
        }
        if (os.contains("windows")) {
            fixVersion = pr.readProperty("local-config.properties", "fixVersion");
        }

        fixVersion = fixVersion.replaceAll("\"", "\\\\\"");
        String jsonUpdateDocument = "{\"fields\": { \"fixVersions\": [{ \"name\": \"" + fixVersion + "\" }] }}";
        this.updateTestRun.putUpdateJsonWithSSL(testExecKey,
                Paths.get(keystorePath),
                keystorePW,
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
                Paths.get(keystorePath),
                keystorePW,
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

        //
        response = this.transitionHandler.doTransition(transitionId,
                Paths.get(keystorePath),
                keystorePW,
                payload,
                devstackApiToken);

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

        if (!testLabels.contains(AUTOMATED)) {
            writeIssueUpdateLabel(AUTOMATED.toString(), properties, testExecKey, testLabels, devstackApiToken);
        } else if (!testLabels.contains(GUI)) {
            writeIssueUpdateLabel(GUI.toString(), properties, testExecKey, testLabels, devstackApiToken);
        }
        for (JsonValue label : testLabels) {

            String value = label.toString();

            writeIssueUpdateLabel(value, properties, testExecKey, testLabels, devstackApiToken);

        }

    }

    private void writeIssueUpdateLabel(
            String value,
            XrayProperties properties,
            String testExecKey,
            JsonArray testLabels,
            String devstackApiToken) throws
            KeyManagementException,
            UnrecoverableKeyException,
            KeyStoreException,
            NoSuchAlgorithmException,
            CertificateException,
            IOException {

        String jsonUpdateDocument
                = "{ \"update\": { \"labels\": [ {\"add\": " + value + " } ] } }";

//		System.out.println("json: " + jsonUpdateDocument);
        // updating test exec issue with components from test case
        this.writeIssueUpdate(properties, testExecKey, testLabels, jsonUpdateDocument, devstackApiToken);

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

        this.updateTestRun.putUpdateJsonWithSSL(testExecKey,
                Paths.get(keystorePath),
                keystorePW,
                jsonUpdateDocument,
                devstackApiToken);

    }

}