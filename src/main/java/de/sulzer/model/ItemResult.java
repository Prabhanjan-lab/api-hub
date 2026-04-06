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

import de.sulzer.model.util.Constants;

/**
 * @author Sulzer GmbH
 *
 */
public class ItemResult implements IJson {
    private String name;
    private String duration;
    private String log;
    private String status;

    private List<ItemExample> itemsExamples;

    private static final String FIELD_EXAMPLES = "examples";
    private static final String FIELD_STATUS = "status";
    private static final String FIELD_LOG = "log";
    private static final String FIELD_DURATION = "duration";
    private static final String FIELD_NAME = "name";

    /**
     *
     */
    public ItemResult() {
        super();
        this.itemsExamples = new ArrayList<>();
    }

    /**
     * @param name
     * @param duration
     * @param log
     * @param status
     * @param itemsExamples
     */
    public ItemResult(String name, String duration, String log, String status,
                      List<ItemExample> itemsExamples) {
        super();
        this.name = name;
        this.duration = duration;
        this.log = log;
        this.status = status;

        if (this.itemsExamples != null) {
            this.itemsExamples = new ArrayList<>();
        } else {
            this.itemsExamples = itemsExamples;
        }

    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the duration
     */
    public String getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * @return the log
     */
    public String getLog() {
        return log;
    }

    /**
     * @param log the log to set
     */
    public void setLog(String log) {
        this.log = log;
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
     * @return the itemsExamples
     */
    public List<ItemExample> getItemsExamples() {
        return itemsExamples;
    }

    /**
     * @param list the itemsExamples to set
     */
    public void setItemsExamples(List<ItemExample> list) {
        this.itemsExamples = list;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((duration == null) ? 0 : duration.hashCode());
        result = prime * result + ((itemsExamples == null) ? 0 : itemsExamples.hashCode());
        result = prime * result + ((log == null) ? 0 : log.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(java.lang.Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ItemResult other = (ItemResult) obj;
        if (duration == null) {
            if (other.duration != null)
                return false;
        } else if (!duration.equals(other.duration))
            return false;
        if (itemsExamples == null) {
            if (other.itemsExamples != null)
                return false;
        } else if (!itemsExamples.equals(other.itemsExamples))
            return false;
        if (log == null) {
            if (other.log != null)
                return false;
        } else if (!log.equals(other.log))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        return true;
    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONFormatting.fieldToJSON(FIELD_NAME, this.name));
        sb.append(JSONFormatting.fieldToJSON(FIELD_DURATION, this.duration));
        sb.append(JSONFormatting.fieldToJSON(FIELD_LOG, this.log));
        sb.append(JSONFormatting.fieldToJSON(FIELD_STATUS, this.status));

        if (this.itemsExamples.size() > 0) {

            sb.append(JSONFormatting.listToJSON(FIELD_EXAMPLES, this.itemsExamples));

        }

        sb.append("}");

        String temp = sb.toString();
        temp = temp.replace(Constants.LISTEND_v3, "}");

        return temp;
    }

    @Override
    public JsonValue getAsJson() {

        JsonObjectBuilder itemResult = Json.createObjectBuilder();

        JSONFormatting.addKeyValuePair(itemResult, FIELD_NAME, this.name);
        JSONFormatting.addKeyValuePair(itemResult, FIELD_DURATION, this.duration);
        JSONFormatting.addKeyValuePair(itemResult, FIELD_LOG, this.log);
        JSONFormatting.addKeyValuePair(itemResult, FIELD_STATUS, this.status);

        if (this.itemsExamples.size() > 0) {

            JsonArrayBuilder itemResultArray = Json.createArrayBuilder();

            for (IJson ijson : this.itemsExamples) {

                itemResultArray.add(ijson.getAsJson());

            }

            itemResult.add(FIELD_EXAMPLES, itemResultArray);
        }

        return itemResult.build();

    }

}
