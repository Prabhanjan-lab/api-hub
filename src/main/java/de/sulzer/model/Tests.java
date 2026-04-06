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

import com.google.gson.annotations.SerializedName;

import de.sulzer.model.util.Constants;

/**
 * @author Bege
 *
 */
public class Tests implements IJson {

    @SerializedName("tests")
    private List<ItemTest> itemTest;

    /**
     *
     */
    public Tests() {
        super();
        this.itemTest = new ArrayList<>();
    }

    /**
     * @param itemTest
     */
    public Tests(List<ItemTest> itemTest) {
        super();
        if (itemTest != null) {
            this.itemTest = new ArrayList<>();
        } else {
            this.itemTest = itemTest;
        }
    }

    /**
     * @return the itemTest
     */
    public List<ItemTest> getItemTest() {
        return itemTest;
    }

    /**
     * @param itemTest the itemTest to set
     */
    public void setItemTest(List<ItemTest> itemTest) {
        this.itemTest = itemTest;
    }

    public void addTestItemsStep(int indexTestItem, ItemStep itemStep) {

        if (this.itemTest.size() == 0){
            this.itemTest.add(new ItemTest());
        }

        ItemTest it = this.itemTest.get(0);

        it.getItemsSteps().add(itemStep);

    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((itemTest == null) ? 0 : itemTest.hashCode());
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
        Tests other = (Tests) obj;
        if (itemTest == null) {
            if (other.itemTest != null)
                return false;
        } else if (!itemTest.equals(other.itemTest))
            return false;
        return true;
    }

    @Override
    public String toJSON() {

        StringBuilder sb = new StringBuilder();

        if (this.itemTest.size() <= 0) {
            return "";
        }

        sb.append("{");
        sb.append(Constants.APO);
        sb.append("tests");
        sb.append(Constants.APO);
        sb.append(": [");
        for (IJson itemTest : this.itemTest) {
            sb.append(itemTest.toJSON());
        }
        sb.append(" ]");
        sb.append("}");

        return sb.toString();
    }

    @Override
    public JsonValue getAsJson() {

        JsonObjectBuilder root = Json.createObjectBuilder();

        if (this.itemTest.size() > 0) {

            JsonArrayBuilder jsonArray = Json.createArrayBuilder();

            for (IJson list : this.itemTest) {

                jsonArray.add(list.getAsJson());

            }

            root.add("tests", jsonArray.build());

        }

        return root.build();

    }

}
