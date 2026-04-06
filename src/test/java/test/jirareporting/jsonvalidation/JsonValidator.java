/**
 * 
 */
package test.jirareporting.jsonvalidation;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * @author bege
 *
 */
public final class JsonValidator {

	/**
	 * 
	 */
	private JsonValidator() {

	}

	/**
	 * This method checks if parameter is valid JSON document.
	 * 
	 * taken from: https://stackoverflow.com/a/30725993
	 * 
	 * @param json
	 * @return true = valid JSON, false = invalid JSON
	 */
	public static boolean isValidJson(String json) {
	
		try {
	
			JsonParser parser = new JsonParser();
			parser.parse(json);
	
		} catch (JsonSyntaxException ex) {
			return false;
		}
	
		return true;
	}

}
