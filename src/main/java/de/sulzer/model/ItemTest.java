package de.sulzer.model;

import de.sulzer.model.util.ConstantsTestResult;

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
public class ItemTest implements IJson {

    private String comment;
    private String finish;
    private String start;
    private String status; //= ConstantsTestResult.TODO;
    private String testKey;
    private String executedBy;

    private List<ItemEvidence> itemsEvidences;

    private List<ItemResult> itemsResults;

    private List<ItemStep> itemsSteps;

    private List<ItemExample> itemsExamples;

    private static final String FIELD_EXAMPLES = "examples";
    private static final String FIELD_STEPS = "steps";
    private static final String FIELD_RESULTS = "results";
    private static final String FIELD_EVIDENCES = "evidences";
    private static final String FIELD_EXECUTED_BY = "executedBy";
    private static final String FIELD_TEST_KEY = "testKey";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_START = "start";
    private static final String FIELD_FINISH = "finish";
    private static final String FIELD_COMMENT = "comment";

    /**
     *
     */
    public ItemTest() {
        super();
        this.itemsEvidences = new ArrayList();
        this.itemsResults = new ArrayList();
        this.itemsSteps = new ArrayList();
        this.itemsExamples = new ArrayList();
    }

    /**
     * @param comment
     * @param finish
     * @param start
     * @param status
     * @param testKey
     * @param executedBy
     * @param itemsEvidences
     * @param itemsResults
     * @param itemsSteps
     * @param itemsExamples
     */
    public ItemTest(String comment, String finish, String start, String status, String testKey, String executedBy,
            List<ItemEvidence> itemsEvidences, List<ItemResult> itemsResults, List<ItemStep> itemsSteps, List<ItemExample> itemsExamples) {
        super();
        this.comment = comment;
        this.finish = finish;
        this.start = start;
        this.status = status;
        this.testKey = testKey;
        this.executedBy = executedBy;

        if (this.itemsEvidences != null) {
            this.itemsEvidences = new ArrayList<>();
        } else {
            this.itemsEvidences = itemsEvidences;
        }

        if (this.itemsResults != null) {
            this.itemsResults = new ArrayList<>();
        } else {
            this.itemsResults = itemsResults;
        }

        if (this.itemsSteps != null) {
            this.itemsSteps = new ArrayList<>();
        } else {
            this.itemsSteps = itemsSteps;
        }

        if (this.itemsExamples != null) {
            this.itemsExamples = new ArrayList<>();
        } else {
            this.itemsExamples = itemsExamples;
        }

    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the finish
     */
    public String getFinish() {
        return finish;
    }

    /**
     * @param finish the finish to set
     */
    public void setFinish(String finish) {
        this.finish = finish;
    }

    /**
     * @return the start
     */
    public String getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the testKey
     */
    public String getTestKey() {
        return testKey;
    }

    /**
     * @param testKey the testKey to set
     */
    public void setTestKey(String testKey) {
        this.testKey = testKey;
    }

    /**
     * @return the executedBy
     */
    public String getExecutedBy() {
        return executedBy;
    }

    /**
     * @param executedBy the executedBy to set
     */
    public void setExecutedBy(String executedBy) {
        this.executedBy = executedBy;
    }

    /**
     * @return the itemsEvidences
     */
    public List<ItemEvidence> getItemsEvidences() {

        if (this.itemsEvidences == null) {
            this.itemsEvidences = new ArrayList<ItemEvidence>();
        }

        return itemsEvidences;
    }

    /**
     * @param itemsEvidences the itemsEvidences to set
     */
    public void setItemsEvidences(List<ItemEvidence> itemsEvidences) {
        this.itemsEvidences = itemsEvidences;
    }

    /**
     * @return the itemsResults
     */
    public List<ItemResult> getItemsResults() {

        if (this.itemsResults == null) {
            this.itemsResults = new ArrayList<ItemResult>();
        }

        return itemsResults;
    }

    /**
     * @param itemsResults the itemsResults to set
     */
    public void setItemsResults(List<ItemResult> itemsResults) {
        this.itemsResults = itemsResults;
    }

    /**
     * @return the itemsSteps
     */
    public List<ItemStep> getItemsSteps() {

        if (this.itemsSteps == null) {
            this.itemsSteps = new ArrayList<ItemStep>();
        }

        return itemsSteps;
    }

    /**
     * @param itemsSteps the itemsSteps to set
     */
    public void setItemsSteps(List<ItemStep> itemsSteps) {
        this.itemsSteps = itemsSteps;
    }

    /**
     * @return the itemsExamples
     */
    public List<ItemExample> getItemsExamples() {

        if (this.itemsExamples == null) {
            this.itemsExamples = new ArrayList<ItemExample>();
        }

        return itemsExamples;
    }

    /**
     * @param itemsExamples the itemsExamples to set
     */
    public void setItemsExamples(List<ItemExample> itemsExamples) {
        this.itemsExamples = itemsExamples;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((executedBy == null) ? 0 : executedBy.hashCode());
        result = prime * result + ((finish == null) ? 0 : finish.hashCode());
        result = prime * result + ((itemsEvidences == null) ? 0 : itemsEvidences.hashCode());
        result = prime * result + ((itemsExamples == null) ? 0 : itemsExamples.hashCode());
        result = prime * result + ((itemsResults == null) ? 0 : itemsResults.hashCode());
        result = prime * result + ((itemsSteps == null) ? 0 : itemsSteps.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((testKey == null) ? 0 : testKey.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemTest other = (ItemTest) obj;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        if (executedBy == null) {
            if (other.executedBy != null)
                return false;
        } else if (!executedBy.equals(other.executedBy))
            return false;
        if (finish == null) {
            if (other.finish != null)
                return false;
        } else if (!finish.equals(other.finish))
            return false;
        if (itemsEvidences == null) {
            if (other.itemsEvidences != null)
                return false;
        } else if (!itemsEvidences.equals(other.itemsEvidences))
            return false;
        if (itemsExamples == null) {
            if (other.itemsExamples != null)
                return false;
        } else if (!itemsExamples.equals(other.itemsExamples))
            return false;
        if (itemsResults == null) {
            if (other.itemsResults != null)
                return false;
        } else if (!itemsResults.equals(other.itemsResults))
            return false;
        if (itemsSteps == null) {
            if (other.itemsSteps != null)
                return false;
        } else if (!itemsSteps.equals(other.itemsSteps))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (testKey == null) {
            if (other.testKey != null)
                return false;
        } else if (!testKey.equals(other.testKey))
            return false;
        return true;
    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONFormatting.fieldToJSON(FIELD_COMMENT, this.comment));
        sb.append(JSONFormatting.fieldToJSON(FIELD_FINISH, this.finish));
        sb.append(JSONFormatting.fieldToJSON(FIELD_START, this.start));
        sb.append(JSONFormatting.fieldToJSON(FIELD_STATUS, this.status));
        sb.append(JSONFormatting.fieldToJSON(FIELD_TEST_KEY, this.testKey));
        sb.append(JSONFormatting.fieldToJSON(FIELD_EXECUTED_BY, this.executedBy));

        String evidences = "";
        String results = "";
        String steps = "";
        String examples = "";

        evidences = JSONFormatting.listToJSON(FIELD_EVIDENCES, this.itemsEvidences);
        results = JSONFormatting.listToJSON(FIELD_RESULTS, this.itemsResults);
        steps = JSONFormatting.listToJSON(FIELD_STEPS, this.itemsSteps);
        examples = JSONFormatting.listToJSON(FIELD_EXAMPLES, this.itemsExamples);

        if (this.itemsEvidences.size() > 0) {
            sb.append(evidences);
        }

        if (this.itemsEvidences.size() > 0 && this.itemsResults.size() > 0) {
            sb.append(",");
            sb.append(results);
        } else if (results.length() > 0) {
            sb.append(results);
        }

        if (this.itemsEvidences.size() > 0 || this.itemsResults.size() > 0) {
            sb.append(",");
            sb.append(steps);
        } else {
            sb.append(steps);
        }

        if ((this.itemsEvidences.size() > 0 || this.itemsResults.size() > 0 ||
                this.itemsSteps.size() > 0) && this.itemsExamples.size() > 0) {
            sb.append(",");
            sb.append(examples);
        } else {
            sb.append(examples);
        }

        sb.append("}");

        return sb.toString();
    }

    @Override
    public JsonValue getAsJson() {

        JsonObjectBuilder itemTest = Json.createObjectBuilder();

        JSONFormatting.addKeyValuePair(itemTest, FIELD_COMMENT, this.comment);
        JSONFormatting.addKeyValuePair(itemTest, FIELD_FINISH, this.finish);
        JSONFormatting.addKeyValuePair(itemTest, FIELD_START, this.start);
        JSONFormatting.addKeyValuePair(itemTest, FIELD_STATUS, this.status);
        JSONFormatting.addKeyValuePair(itemTest, FIELD_TEST_KEY, this.testKey);
        JSONFormatting.addKeyValuePair(itemTest, FIELD_EXECUTED_BY, this.executedBy);

        if (this.itemsEvidences.size() > 0) {

            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            for (IJson list : this.itemsEvidences) {

                jsonArray.add(list.getAsJson());

            }

            itemTest.add(FIELD_EVIDENCES, jsonArray.build());

        }

        if (this.itemsResults.size() > 0) {

            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            for (IJson list : this.itemsResults) {

                jsonArray.add(list.getAsJson());

            }

            itemTest.add(FIELD_RESULTS, jsonArray.build());

        }

        if (this.itemsSteps.size() > 0) {

            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            for (IJson list : this.itemsSteps) {

                jsonArray.add(list.getAsJson());

            }

            itemTest.add(FIELD_STEPS, jsonArray.build());

        }

        if (this.itemsExamples.size() > 0) {

            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            for (IJson list : this.itemsExamples) {

                jsonArray.add(list.getAsJson());

            }

            itemTest.add(FIELD_EXAMPLES, jsonArray.build());

        }

        return itemTest.build();

    }

}
