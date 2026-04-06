/**
 *
 */
package de.sulzer.model;

import java.util.List;

import javax.json.JsonObjectBuilder;

import de.sulzer.model.util.Constants;

/**
 * @author Sulzer GmbH
 *
 */
public class JSONFormatting {

    public static String fieldToJSON(String fieldName, String value) {

        if (value == null || value.length() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        sb.append(Constants.APO);
        sb.append(fieldName);
        sb.append(Constants.APO);
        sb.append(" : ");
        sb.append(Constants.APO);
        sb.append(value);
        sb.append(Constants.APO);
        sb.append(",");

        return sb.toString();
    }

    public static String listToJSON(String listName, List<? extends IJson> itemList) {

        if (itemList == null || itemList.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder sbList = new StringBuilder();

        sb.append(Constants.APO);
        sb.append(listName);
        sb.append(Constants.APO);
        sb.append(": [");

        if (itemList.size() == 1) {
            sbList.append(itemList.get(0).toJSON());
        } else if (itemList.size() > 0) {
            sbList.append(itemList.get(0).toJSON());
            for (int i = 1; i < itemList.size(); i++) {
                sbList.append(",");
                sbList.append(itemList.get(i).toJSON());
            }
        }

        sb.append(sbList);

        sb.append(" ]");

        return sb.toString();
    }

    public static synchronized void addKeyValuePair(JsonObjectBuilder jsonObject, String key, String value) {

        if (!(value == null || value.length() == 0)) {
            jsonObject.add(key, value);
        }

    }

}
