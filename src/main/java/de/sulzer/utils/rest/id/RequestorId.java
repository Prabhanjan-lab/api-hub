/**
 * 
 */
package de.sulzer.utils.rest.id;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import testFramework.AbstractStandardBehaviour;
import de.sulzer.utils.rest.id.util.ConstantsServices;

/**
 * @author GCH9F5D
 *
 */
public class RequestorId {

	// base URL for REST service without SSH
	private static final String BASEURL_IDGENERATOR_v1 = 
			"http://172.29.209.145:21000" +
					ConstantsServices.CONNECT +
					ConstantsServices.OUD +
					ConstantsServices.IDGENERATOR +
					ConstantsServices.V1;
	
	private static final String NEWID = ConstantsServices.REQUESTNEWID;

	private static final String LASTID = ConstantsServices.REQUESTLASTID;

	//
	private static final String TESTCASEID = "testcaseId";
	private static final String ID = "id";
	
	public static synchronized String requestNewId(Object object) throws Exception {
		
		String className = extractTestCaseNumber(object);
		// assembling JSON
		String json = createJSONRequestNewId(className);
		//
		String response = requestNewId(json);
		//
		return extractValue(ID, response);
	}

	public static synchronized String requestLastId(Object object) throws Exception {
		
		String className = extractTestCaseNumber(object);
		// assembling JSON
		String json = createJSONRequestLastId(className);
		//
		String response = requestLastId(json);
		//
		return extractValue(ID, response);
	}

	/**
	 * @param cls
	 * @return
	 */
	private static String extractTestCaseNumber(Object object) {

		// processing calling class (name)
		String className = object.getClass().getSimpleName();
		String[] parts = className.split("_");
		className = parts[0] + "-" + parts[1];

		return className;
	}

	private static String extractValue(String field, String response) {
		
		//
		JsonReader reader = Json.createReader(new StringReader(response));
        //
        JsonObject json = reader.readObject();
        //
        String value = json.getString(field); 
        // closing after reading data from JSON
        reader.close();
        
        return value;
	}

	private static synchronized String createJSONRequestNewId(String className) {
		return createJSONRequestId(className);
	}

	private static synchronized String createJSONRequestLastId(String className) {
		return createJSONRequestId(className);
	}

	/**
	 * 
	 * @param className
	 * @return JSON for sending to REST service, requesting an ID for given test case numer
	 */
	private static synchronized String createJSONRequestId(String className) {

		JsonObject json = Json.createObjectBuilder().
				add(TESTCASEID, className).
				build();
		
		return json.toString();
		
	}
	
	private static String requestNewId(String json) throws Exception {
		return sendRestCall(json, NEWID);
	}
	
	private static synchronized String requestLastId(String json) throws Exception {
		return sendRestCall(json, LASTID);
	}

	/**
	 * @param json
	 * @param restService 
	 * @return
	 * @throws Exception
	 */
	private static synchronized String sendRestCall(String json, String restService) throws Exception {

		try {

			// Testclient:
			Client client = ClientBuilder.newClient();

            WebTarget webTarget = client.target(BASEURL_IDGENERATOR_v1 + restService);
			Response response = webTarget.request().post(Entity.entity(json, MediaType.APPLICATION_JSON));

			if (200 == response.getStatus()) {
				return response.readEntity(String.class);
			} else {
				throw new Exception("Request (service type '" + restService + "') response but got HTTP status " + response.getStatus() + " (instead of 200).");
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			;
		}
	}
	
}
