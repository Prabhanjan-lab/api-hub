package testCases.reusable_TestSteps;

import java.io.IOException;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class GetActiveCampaign extends JWT{

	public static Response activeCampaign(String testname, String vin) throws IOException {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("type","active");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaigns").then().log().all().extract().response();
		return response;
	}


	public static String getActiveQueueCampaign(String testname, String vin) throws IOException {
		Response status= GetActiveCampaign.activeCampaign(testname, vin);
		String	campaign = null; 
		JsonPath j = new JsonPath(status.asString());
		for(int i = 0; j.getString("data["+i+"].vin")!=null; i++) {
			if(j.getString("data["+i+"].vin").equalsIgnoreCase(vin)) {
			campaign = j.getString("data["+i+"].campaignId").toString();
			}
		}
		return campaign;
	}
	
	public static String getActiveQueueCampaigncriterionId(String testname, String vin) throws IOException {
		Response status= GetActiveCampaign.activeCampaign(testname, vin);
		String	criterionId = null; 
		JsonPath j = new JsonPath(status.asString());
		for(int i = 0; j.getString("data["+i+"].vin")!=null; i++) {
			if(j.getString("data["+i+"].vin").equalsIgnoreCase(vin)) {
				criterionId = j.getString("data["+i+"].criterionId").toString();
			}
		}
		return criterionId;
	}
}
