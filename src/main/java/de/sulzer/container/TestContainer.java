/**
 *
 */
package de.sulzer.container;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;

import de.sulzer.model.IJson;
import de.sulzer.model.Info;
import de.sulzer.model.ItemCustomField;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.ItemExample;
import de.sulzer.model.ItemKeyValuePair;
import de.sulzer.model.ItemResult;
import de.sulzer.model.ItemStep;
import de.sulzer.model.ItemTest;
import de.sulzer.model.JsonObject;
import de.sulzer.model.util.ConstantsTestResult;

import javax.json.JsonValue;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;


/**
 * @author Sulzer GmbH
 *
 */
public class TestContainer {

    private int expectedTestSteps;
    private int executedTestSteps;
    private JsonObject jso;
    private String responseJson;

    public TestContainer(int expectedTestSteps) {
        this.init(expectedTestSteps);
    }

    public void reset(int expectedTestSteps) {
        this.init(expectedTestSteps);
    }

    private void init(int expectedTestSteps) {

        this.jso = new JsonObject();

        this.expectedTestSteps = expectedTestSteps;
        this.executedTestSteps = 0;

    }

    private void increaseTestStepCount() {
        this.executedTestSteps++;
    }

    public int getExpectedStepsPassed() {
        return this.expectedTestSteps;
    }

    public int getStepsPassed() {
        return this.executedTestSteps;
    }

    public boolean allTestStepsPassed() {
        return this.expectedTestSteps == this.executedTestSteps;
    }

    /**
     * @param jso the jso to set
     */
    public void setJso(JsonObject jso) {
        this.jso = jso;
    }

    /**
     * @return the jso
     */
    public JsonObject getJso() {
        return jso;
    }

    public String getJSON() {
        return this.jso.toJSON();
    }

    public JsonValue getAsJSON() {
        return this.jso.getAsJson();
    }

    /**
     * @return the responseJson
     */
    public String getResponseJson() {
        return responseJson;
    }

    /**
     * @param responseJson the responseJson to set
     */
    public void setResponseJson(String responseJson) {
        this.responseJson = responseJson;
    }

    public Info getInfo() {
        return this.getJso().getInfo();
    }

    public ItemTest getTest() {
        return this.getJso().getTest();
    }

    public void addTestEvidence(String pass, String comment, String contentType) {
        this.jso.addTestItemsEvidence(0, new ItemEvidence(pass, comment, contentType));
    }

    public void addTestResult(String name, String duration, String log, String status, List<ItemExample> itemExamples) {
        this.jso.addTestItemsResult(0, new ItemResult(name, duration, log, status, itemExamples));
    }

    public void addTestStep(String status, String comment, List<ItemEvidence> itemEvidences) {

        this.jso.addTestItemStep(0, new ItemStep(status, comment, itemEvidences));

        if (ConstantsTestResult.PASS.equals(status)) {
            this.increaseTestStepCount();
        }

    }

    public void addTestStep(String pass, String comment) {
        this.addTestStep(pass, comment, new ArrayList<ItemEvidence>());
    }

    public void addTestStepPassed(String logPath) {

        String content = "passed";
        if (!Objects.equals(logPath, "")){
            try {
                content = new String(Files.readAllBytes(Paths.get(logPath)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        this.addTestStep(ConstantsTestResult.PASS, content);
    }

    public void addTestExample(String type) {
        this.jso.addTestItemsExample(new ItemExample(type));
    }


    public void setAssignee(String assignee) {
        this.getInfo().setAssginee(assignee);
    }

    public void setReporter(String reporter) {
        this.getInfo().setReporter(reporter);
    }

    public void setEnvironment(String environment) {
        // TODO environment
    }

    public void addCustomField(String fieldId, Map<String, String> keyValuePairs) {

        // list for value pairs
        List<IJson> customFields = new ArrayList<>();
        // custom field
        ItemCustomField icf = null;

        // creating key value pair object from given list
        for (String key : keyValuePairs.keySet()) {

            ItemKeyValuePair ikvp = new ItemKeyValuePair(key, keyValuePairs.get(key));

            // adding key value pair to list
            customFields.add(ikvp);
        }

        // creating custom field with key value pairs
        icf = new ItemCustomField(fieldId, customFields);

        // adding custom field to field list
        this.getJso().getItemField().add(icf);

    }

    public void processFailure(String logPath, ItemEvidence ...itemEvidence) {

        //System.out.println("***processFailure in TestContainer.java!!");
        //
        List<ItemEvidence> itemEvidences = new ArrayList<>();

        for (ItemEvidence item : itemEvidence) {
            itemEvidences.add(item);
        }

        //
        String content = "Find full StackTrace in zip file.";
        if (!Objects.equals(logPath, "")){
            try {
                content = new String(Files.readAllBytes(Paths.get(logPath)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        ItemStep itemStep = new ItemStep(ConstantsTestResult.FAIL,
                content,
                itemEvidences);

        // getting exceptions stack trace
        this.jso.addTestItemStep(0, itemStep);

        for (int i = this.executedTestSteps + 1; i < this.expectedTestSteps; i++) {

            itemStep = new ItemStep(ConstantsTestResult.TODO,
                    "one of previous test steps failed");

            this.jso.addTestItemStep(0, itemStep);
        }

        // setting test result
        ((ItemTest)this.jso.getTests().get(0)).setStatus(ConstantsTestResult.FAIL);

    }

    /**
     *
     * @param e
     * @return
     */
    private String getExceptionAsString(Throwable e) {

        StringBuilder sb = new StringBuilder();

        // write exception etc. message as first line of error message
        sb.append(e.getMessage() + "\\n"); // escaping (linux) line change for JSON/Jira

        // see:
        // https://stackoverflow.com/a/22271986
        // or
        // https://docs.oracle.com/javase/tutorial/essential/exceptions/chained.html
        //
        StackTraceElement elements[] = e.getStackTrace();

        for (int i = 0, n = elements.length; i < n; i++) {
            sb.append("" + elements[i].getFileName()
                    + " : line " + elements[i].getLineNumber()
                    + " >> "
                    + elements[i].getMethodName() + "()" + "\\n"); // escaping (linux) line change for JSON/Jira
        }

        return StringEscapeUtils.escapeJson(sb.toString());
    }

    public String checkTestResult() {

        int passCounter = 0;

        String returnStatus = ConstantsTestResult.TODO;

        ItemTest itemTest = this.jso.getTests().get(0);

        for (ItemStep itemStep : itemTest.getItemsSteps()) {

            if (itemStep.getStatus().equals(ConstantsTestResult.PASS)){
                passCounter++;
            }

        }

        if (passCounter == this.expectedTestSteps) {
            returnStatus = ConstantsTestResult.PASS;

        } else {
            returnStatus = ConstantsTestResult.FAIL;

        }

        return returnStatus;
    }

    public void setInfo(Info info) {
        if (jso == null) {
            this.jso = new JsonObject();
        }

        this.jso.setInfo(info);
    }

    public void setTest(ItemTest test) {
        if (jso == null) {
            this.jso = new JsonObject();
        }

        List<ItemTest> listTests = new ArrayList<ItemTest>();
        listTests.add(test);

        this.jso.setTests(listTests);
    }
}
