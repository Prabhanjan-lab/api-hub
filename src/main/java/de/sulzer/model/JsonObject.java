/**
 *
 */
package de.sulzer.model;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * @author Sulzer GmbH
 *
 */
public class JsonObject implements IJson {

    private String testExecutionKey;

    private Info info;

    private List<ItemCustomField> itemField;

    private List<ItemTest> itemTest;

    private static final String FIELD_TESTS = "tests";
    private static final String FIELD_FIELDS = "fields";
    private static final String FIELD_INFO = "info";
    private static final String FIELD_TEST_EXECUTION_KEY = "testExecutionKey";

    /**
     *
     */
    public JsonObject() {
        super();
        this.info = new Info();
        this.itemField = new ArrayList<>();
        this.itemTest = new ArrayList<>();
    }

    /**
     * @param info
     * @param tests
     */
    public JsonObject(Info info, List<ItemTest> itemTest, List<ItemCustomField> itemField) {

        super();

        if (info != null) {
            this.info = info;
        } else {
            this.info = new Info();
        }

        if (itemTest != null) {
            this.itemTest = itemTest;
        } else {
            this.itemTest = new ArrayList<>();
        }

        if (itemField != null) {
            this.itemField = itemField;
        } else {
            this.itemField = new ArrayList<>();
        }

    }

    /**
     * @return the testExecutionKey
     */
    public String getTestExecutionKey() {
        return testExecutionKey;
    }

    /**
     * @param testExecutionKey the testExecutionKey to set
     */
    public void setTestExecutionKey(String testExecutionKey) {
        this.testExecutionKey = testExecutionKey;
    }

    /**
     * @return the info
     */
    public Info getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(Info info) {
        this.info = info;
    }

    /**
     * @return the itemField
     */
    public List<ItemCustomField> getItemField() {
        return itemField;
    }

    /**
     * @param itemField the itemField to set
     */
    public void setItemField(List<ItemCustomField> itemField) {
        this.itemField = itemField;
    }

    public void addFields(List<ItemCustomField> list) {

        if (this.itemField == null) {
            this.itemField = new ArrayList<>();
        }

        this.itemField.addAll(list);
    }

    /**
     * @return the tests
     */
    public List<ItemTest> getTests() {

        if (this.itemTest == null) {
            this.itemTest = new ArrayList<ItemTest>();
        }

        return this.itemTest;
    }

    /**
     * ATTENTION:
     * According REST API of Jira it is assumed, that you have to handle more
     * than one test in Json document. However, in this model/environment, it is
     * defined, that ONLY ONE test is available/for inserting into Jira as Json!
     *
     * @return ItemTest
     */
    public ItemTest getTest() {

        if (this.itemTest == null || this.itemTest.size() == 0) {
            return null;
        } else {
            return (ItemTest) this.itemTest.get(0);
        }

    }

    /**
     * @param tests the tests to set
     */
    public void setTests(List<ItemTest> itemTest) {
        this.itemTest = itemTest;
    }

    public void addTests(List<ItemTest> itemTest) {

        if (this.itemTest == null) {
            this.itemTest = new ArrayList<>();
        }

        this.itemTest.addAll(itemTest);
    }

    public void addTestItemsEvidence(int i, ItemEvidence itemEvidence) {

        this.checkTest();

        IJson itemTest = this.itemTest.get(0);

        ((ItemTest)itemTest).getItemsEvidences().add(itemEvidence);

    }

    public void addTestItemsExample(ItemExample itemExample) {

        this.checkTest();

        IJson itemTest = this.itemTest.get(0);

        ((ItemTest)itemTest).getItemsExamples().add(itemExample);

    }

    public void addTestItemsResult(int i, ItemResult itemResult) {

        this.checkTest();

        ItemTest itemTest = this.itemTest.get(0);

        itemTest.getItemsResults().add(itemResult);

    }

    public void addTestCustomField(ItemCustomField itemCustomField) {

        if (this.itemField == null) {
            this.itemField = new ArrayList<>();
        }

        this.itemField.add(itemCustomField);

    }

    public void addTestItemStep(int indexTestItem, ItemStep itemStep) {

        this.checkTest();

        IJson itemTest = this.itemTest.get(0);

        ((ItemTest)itemTest).getItemsSteps().add(itemStep);

    }

    private void checkTest() {

        if (this.itemTest.size() == 0){
            this.itemTest.add(new ItemTest());
        }

    }

    @Override
    public String toJSON() {

        String temp = "";

        StringBuilder sb = new StringBuilder();

        //
        sb.append("{");

        sb.append(JSONFormatting.fieldToJSON(FIELD_TEST_EXECUTION_KEY, this.testExecutionKey));

        temp = this.info.toJSON();

        if (temp.length() > 10) {
            sb.append(temp);
        }

        if (this.itemField.size() > 0) {

            String fields = "";
            fields = JSONFormatting.listToJSON(FIELD_FIELDS, this.itemField);

            // escaping of '[' is needed for replacement,
            // see https://stackoverflow.com/a/5058220
            // reason: with fields is special bracketing needed
            fields = fields.replaceFirst(": \\[", ": {");
            fields = fields.substring(0, fields.length() - 1) + "}";

            sb.append(",");
            sb.append(fields);
        }

        if (this.itemTest.size() > 0) {

            String tests = "";
            tests = JSONFormatting.listToJSON(FIELD_TESTS, this.itemTest);

            sb.append(",");
            sb.append(tests);
        }

        //
        sb.append("}");

        return sb.toString();
    }

    @Override
    public JsonValue getAsJson() {

        JsonObjectBuilder root = Json.createObjectBuilder();

        if (!(this.testExecutionKey == null || this.testExecutionKey.length() == 0)) {

            System.out.println("testExecutionKey: " + this.testExecutionKey);

            root.add(FIELD_TEST_EXECUTION_KEY, this.testExecutionKey);

        }

        root.add(FIELD_INFO, this.info.getAsJson());

        if (this.itemField.size() > 0) {

            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            for (IJson list : this.itemField) {

                jsonArray.add(list.getAsJson());

            }

            root.add(FIELD_FIELDS, jsonArray.build());

        }

        if (this.itemTest.size() > 0) {

            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            for (IJson list : this.itemTest) {

                jsonArray.add(list.getAsJson());

            }

            root.add(FIELD_TESTS, jsonArray.build());

        }

        return root.build();

    }

}
