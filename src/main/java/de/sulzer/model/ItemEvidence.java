/**
 *
 */
package de.sulzer.model;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import de.sulzer.model.util.Constants;

/**
 * @author Sulzer GmbH
 *
 */
public class ItemEvidence implements IJson {

    private String data;
    private String filename;
    private String contentType;

    private static final String FIELD_CONTENT_TYPE = "contentType";
    private static final String FIELD_FILENAME = "filename";
    private static final String FIELD_DATA = "data";

    /**
     *
     */
    public ItemEvidence() {
        super();
    }

    /**
     * @param data
     * @param filename
     * @param contentType
     */
    public ItemEvidence(String data, String filename, String contentType) {
        super();
        this.data = data;
        this.filename = filename;
        this.contentType = contentType;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contentType == null) ? 0 : contentType.hashCode());
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((filename == null) ? 0 : filename.hashCode());
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
        ItemEvidence other = (ItemEvidence) obj;
        if (contentType == null) {
            if (other.contentType != null)
                return false;
        } else if (!contentType.equals(other.contentType))
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (filename == null) {
            if (other.filename != null)
                return false;
        } else if (!filename.equals(other.filename))
            return false;
        return true;
    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONFormatting.fieldToJSON(FIELD_DATA, this.data));
        sb.append(JSONFormatting.fieldToJSON(FIELD_FILENAME, this.filename));
        sb.append(JSONFormatting.fieldToJSON(FIELD_CONTENT_TYPE, this.contentType));
        sb.append("}");

        String temp = sb.toString();
        temp = temp.replace(Constants.LISTEND_v3, "}");

        return temp;
    }

    @Override
    public JsonValue getAsJson() {

        JsonObjectBuilder itemEvidence = Json.createObjectBuilder();

        JSONFormatting.addKeyValuePair(itemEvidence, FIELD_DATA, this.data);
        JSONFormatting.addKeyValuePair(itemEvidence, FIELD_FILENAME, this.filename);
        JSONFormatting.addKeyValuePair(itemEvidence, FIELD_CONTENT_TYPE, this.contentType);

        return itemEvidence.build();

    }

}
