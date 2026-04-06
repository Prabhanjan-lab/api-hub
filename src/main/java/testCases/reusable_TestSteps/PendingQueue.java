package testCases.reusable_TestSteps;
import java.io.IOException;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class PendingQueue extends JWT{

	public static Response CampaignStatus(String testname,String vin) throws IOException {
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("type","pending");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaigns").then().log().all().extract().response();
		return response;	
	}
	
	public static void assertPendingCampaign(String testname,String vin,String expectedCampaign) throws IOException {
		Response status= PendingQueue.CampaignStatus(testname,vin);
		JsonPath j = new JsonPath(status.asString());
		for(int i = 0; j.getString("data["+i+"].campaignId")!=null; i++) {
			if(j.getString("data["+i+"].campaignId").equalsIgnoreCase(expectedCampaign)) {
				Assert.assertEquals(j.getString("data["+i+"].status.code"), "1");	
				
			}
		}
	}
}
