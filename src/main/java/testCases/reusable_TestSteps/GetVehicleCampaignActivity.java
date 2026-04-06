package testCases.reusable_TestSteps;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class GetVehicleCampaignActivity {

	public static Response GetVCA(String testname) throws IOException, InterruptedException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		String vin = ReadTestParameters.getAttributeValue(testname, "vin");
		String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
		String searchValue = ReadTestParameters.getAttributeValue(testname, "searchValue");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		request.queryParam("search", searchValue);
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaign/"+campaign+"/criterion/"+criterion+"/details").then().log().all().extract().response();
		return response;
	}

	public static void assertVCA(String testname) throws Exception {
		Response vca= GetVehicleCampaignActivity.GetVCA(testname);
		JSONObject jsonObject = new JSONObject(vca.asString());
		JSONArray dataArray = jsonObject.getJSONArray("data");
		boolean isValueFound = false;
		// Iterate over the JSON array
		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject obj = dataArray.getJSONObject(i);          	
			String action=	obj.getString("actionDetails");
			if(	action.contains("Customer was notified about the update")) {
				 isValueFound = true;
			        System.out.println("Found VCA : "+action);
				 break;
			}
		}
        assertTrue(isValueFound, "Expected 'actionDetails' value not found in the response!");

	}
	
}
