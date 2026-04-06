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
public class ItemExample implements IJson {

    private String type;

    private static final String FIELD_TYPE = "type";

    /**
     *
     */
    public ItemExample() {
        super();
    }

    /**
     * @param type
     */
    public ItemExample(String type) {
        super();
        this.type = type;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        ItemExample other = (ItemExample) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append(JSONFormatting.fieldToJSON(FIELD_TYPE, this.type));
        sb.append("}");

        String temp = sb.toString();
        temp = temp.replace(Constants.LISTEND_v3, "}");

        return temp;
    }

    @Override
    public JsonValue getAsJson() {

        JsonObjectBuilder itemExample = Json.createObjectBuilder();

        JSONFormatting.addKeyValuePair(itemExample, FIELD_TYPE, this.type);

        return itemExample.build();

    }

}
