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
public class ItemStep implements IJson {

    private String status;
    private String comment;

    private List<ItemEvidence> itemsEvidences;

    private static final String FIELD_EVIDENCES = "evidences";
    private static final String FIELD_COMMENT = "comment";
    private static final String FIELD_STATUS = "status";

    /**
     *
     */
    public ItemStep() {
        super();
        this.itemsEvidences = new ArrayList<>();
    }

    /**
     * @param status
     * @param comment
     */
    public ItemStep(String status, String comment) {
        super();
        this.status = status;
        this.comment = comment;
        this.itemsEvidences = new ArrayList<>();
    }

    /**
     * @param status
     * @param comment
     * @param itemsEvidences
     */
    public ItemStep(String status, String comment,
                    List<ItemEvidence> itemsEvidences) {
        super();
        this.status = status;
        this.comment = comment;

        if (this.itemsEvidences != null) {
            this.itemsEvidences = new ArrayList<>();
        } else {
            this.itemsEvidences = itemsEvidences;
        }

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
     * @return the itemsEvidences
     */
    public List<ItemEvidence> getItemsEvidences() {
        return itemsEvidences;
    }

    /**
     * @param list the itemsEvidences to set
     */
    public void setItemsEvidences(List<ItemEvidence> list) {
        this.itemsEvidences = list;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
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
        ItemStep other = (ItemStep) obj;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
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
        sb.append(JSONFormatting.fieldToJSON(FIELD_STATUS, this.status));
        sb.append(JSONFormatting.fieldToJSON(FIELD_COMMENT, this.comment));

        if (this.itemsEvidences != null && this.itemsEvidences.size() > 0) {

            sb.append(JSONFormatting.listToJSON(FIELD_EVIDENCES, this.itemsEvidences));

        }

        sb.append("}");

        String temp = sb.toString();
        temp = temp.replace(Constants.LISTEND_v3, "}");

        return temp;
    }

    @Override
    public JsonValue getAsJson() {

        JsonObjectBuilder itemStep = Json.createObjectBuilder();

        JSONFormatting.addKeyValuePair(itemStep, FIELD_STATUS, this.status);
        JSONFormatting.addKeyValuePair(itemStep, FIELD_COMMENT, this.comment);

        if (this.itemsEvidences != null && this.itemsEvidences.size() > 0) {

            JsonArrayBuilder itemStepArray = Json.createArrayBuilder();

            for (IJson itemEvidence : this.itemsEvidences) {

                itemStepArray.add(itemEvidence.getAsJson());

            }

            itemStep.add(FIELD_EVIDENCES, itemStepArray);

        }

        return itemStep.build();

    }

}
