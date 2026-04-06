/**
 *
 */
package de.sulzer.model;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

/**
 * @author Sulzer GmbH
 *
 */
public class ItemCustomField implements IJson {

    private String fieldname;

    private List<IJson> itemCustomFields;

    /**
     *
     */
    public ItemCustomField(String fieldname) {

        super();

        this.fieldname = fieldname;

        this.itemCustomFields = new ArrayList<>();
    }

    /**
     * @param self
     * @param id
     * @param value
     */
    public ItemCustomField(String fieldname,
                           List<IJson> customFields) {

        super();

        this.fieldname = fieldname;


        if (customFields != null) {
            this.itemCustomFields = customFields;
        } else {
            this.itemCustomFields = new ArrayList<>();
        }

    }

    /**
     * @return the fieldname
     */
    public String getFieldname() {
        return fieldname;
    }

    /**
     * @return the itemCustomFields
     */
    public List<IJson> getItemCustomFields() {
        return itemCustomFields;
    }

    /**
     * @param itemCustomFields the itemCustomFields to set
     */
    public void setItemCustomFields(List<IJson> itemCustomFields) {
        this.itemCustomFields = itemCustomFields;
    }

    /* (non-Javadoc)
     * @see de.sulzer.model.IJson#toJSON()
     */
    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();
        String fields = "";

        if (this.itemCustomFields.size() > 0) {

            fields = "";

            fields = JSONFormatting.listToJSON(this.fieldname, this.itemCustomFields);

            // replacing first ocurrence for json conformity
            // backslashes see: https://stackoverflow.com/a/20556123
            fields = fields.replaceFirst("\\[", "[{");
            // removing last ']', and adding '}]' for json conformity
            fields = fields.substring(0, fields.length() - 1);
            fields = fields + "}]";

            sb.append(fields);

        // in case no key value pairs available for custom field, producing output
        // according given example
        } else {
            if (this.fieldname.length() > 0) {
                fields = "\"" + this.fieldname + "\": null";
            }
            sb.append(fields);
        }

        String infoString = sb.toString();

        return infoString;
    }

    public JsonObject getAsJson() {

        JsonObjectBuilder itemCustomField = Json.createObjectBuilder();

        if (this.itemCustomFields.size() > 0) {
            itemCustomField.add(this.fieldname, this.getArrayCustomFields());
        }

        return itemCustomField.build();

    }

    private JsonValue getArrayCustomFields() {

        JsonArrayBuilder itemCustomFieldArray = Json.createArrayBuilder();

        for (IJson ijson : this.itemCustomFields) {

            itemCustomFieldArray.add(ijson.getAsJson());

        }

        return itemCustomFieldArray.build();
    }

}
