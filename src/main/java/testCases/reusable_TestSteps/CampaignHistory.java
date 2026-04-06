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

public class CampaignHistory extends JWT{
	public static Response CampaignStatus(String testname,String vin) throws IOException {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");	
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("type","concluded");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaigns").then().log().all().extract().response();
		return response;	
	}

	public static void assertCampaignHistory(String testname,String vin,String expectedCampaign,String VCSO) throws Exception  {
		Response status =CampaignHistory.CampaignStatus(testname, vin);
		JsonPath j = new JsonPath(status.asString());
		Boolean campaignfound=null;
		int index = 0;
		
			for(int i=0; j.getString("data["+i+"].campaignId")!=null; i++) {
				if(j.getString("data["+i+"].campaignId").equalsIgnoreCase(expectedCampaign)) {
					campaignfound=true;
					index=i;
					break;
					}	
				else if(!j.getString("data["+i+"].campaignId").equalsIgnoreCase(expectedCampaign)) {
				campaignfound=false;
				}
			}
			if(!campaignfound) {
				System.out.println("Campaign not found with the expected vcso");
				throw new Exception();
				
			}
			else if(campaignfound){
				Assert.assertEquals(expectedCampaign, j.getString("data["+index+"].campaignId"));
				Assert.assertEquals(VCSO,j.getString("data["+index+"].status.code" ));
				System.out.println("Campaign  found with the expected vcso");	
			}
	}
}