package testCases.reusable_TestSteps;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.SoapUI.reusable_TestSteps.currentSPSStatusofCampaign;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class ActiveQueueMassNotification extends JWT {

	public static Response activeCampaign(String testname,String vin) throws IOException {
		JWT.GenerateJWTToken();
		String brand= ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("type","active");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaigns").then().log().all().extract().response();
		return response;
	}

	public static void assertActiveQueueMassNotification(String testname,String vin,String expectedCampaign,String criterion) throws InterruptedException, IOException {
		Boolean vcsoStatus=false;
		while(vcsoStatus==false) {
			Response activeCampaign= ActiveQueueMassNotification.activeCampaign(testname,vin);
			JsonPath j = new JsonPath(activeCampaign.asString());
			String campaign=j.getString("data[0].campaignId").toString();
			Assert.assertEquals(campaign,expectedCampaign);
			String vcso=j.getString("data[0].status.code").toString();
			System.out.println("actual vcso:"+ vcso);
			
			if(vcso.equalsIgnoreCase("3")) {
				System.out.println("vcso reached value: 3");
				Thread.sleep(900000);
			}
			else if(vcso.equalsIgnoreCase("4")){
				System.out.println("vcso reached value: 4");
				currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, expectedCampaign, criterion, "1");	
				Thread.sleep(1801000);
				vcsoStatus=false;
			}
			else if(vcso.equalsIgnoreCase("5")) {
				System.out.println("vcso reached expected value: 5");
				currentSPSStatusofCampaign.assertCurrentspsStatus(testname, vin, expectedCampaign, criterion, "1");
				vcsoStatus=true;
			}
			else if(vcso.equalsIgnoreCase("33")) {
				System.out.println("Campaign moved to vcso 33");
				break;
			}
		}
	}
}
