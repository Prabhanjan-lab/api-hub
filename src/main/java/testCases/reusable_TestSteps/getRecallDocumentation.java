package testCases.reusable_TestSteps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class getRecallDocumentation {
	
	public static Response GetRecallDocumentation(String testname) throws IOException {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/recall/lifecycledocumentation/queue/true").then().log().all().extract().response();
		return response;
	}
	
	public static void assertgetRecallDocumentation(String testname, String expectedVin, String expectedCampaign, String reason) throws IOException {
		Response response = getRecallDocumentation.GetRecallDocumentation(testname);
		int scode=	response.statusCode();
		assertEquals(scode, 200);
		JSONObject jsonObject = new JSONObject(response.asString());
		JSONArray dataArray = jsonObject.getJSONArray("data");
		// Iterate over the JSON array
		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject obj = dataArray.getJSONObject(i);          	
			String vin=	obj.getString("vin");
			String campaign = obj.getString("campaignNumber");
			String expectedReason = obj.getString("reason");
			if(vin.equals(expectedVin) && campaign.equals(expectedCampaign)) {
				System.out.println(expectedReason);
				Assert.assertTrue(reason.equals(expectedReason));
			}
		} 
	}
	
}
