package testCases.reusable_TestSteps;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class CarportLifeCycleSucessfull {
		public static String testname = "NON_UNECE_Vehicle_Notification_not_sent_to_Campaign_at_VCSO_30";
		public static String vin = ReadTestParameters.getAttributeValue(testname, "vin");
		public static String campaign = ReadTestParameters.getAttributeValue(testname, "campaign");
		public static String criterion = ReadTestParameters.getAttributeValue(testname, "criterion");
		public static String id ;
		public static Long createdTimeStamp;
		
	public static ValidatableResponse getCarportLifeCycleSucessful(String testname) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("search","");
		ValidatableResponse body = request.when().get("/onup/api/bs/oru/v1/"+brand+"/s42/lifecycledocumentation/queue/false").then().log().all();
		
		
		return body;
	}
	
	public static void assertGetCarportLifeCycleSucessful(String testname, String expectedVin, String expectedCampaign) throws IOException  {
		ValidatableResponse response = CarportLifeCycleSucessfull.getCarportLifeCycleSucessful(testname);
		response.assertThat().statusCode(200);
		
		Response response1 = response.extract().response();
		JsonPath jsonPath = response1.jsonPath();
		
		List<Map<String, Object>> dataList =
		        jsonPath.getList("data");

		
		boolean matchFound = false;
		
		
		for(Map<String, Object> item : dataList) {
			String vin = (String) item.get("vin");
            String campaign = (String) item.get("campaignNumber");
			
			if(expectedVin.equals(vin) && expectedCampaign.equals(campaign)) {
				id = item.get("id").toString();
				 createdTimeStamp = 0L;

                if (item.get("createTimestamp") instanceof Number) {
                	createdTimeStamp = ((Number) item.get("createTimestamp")).longValue();
                }
                
                matchFound = true;
			}
		}
		assertTrue(matchFound, "No matching VIN and Campaign found in response.");
		System.out.println("id : "+id);
		System.out.println("createdTimeStamp : "+createdTimeStamp);
	}
	
	public static ValidatableResponse postCarportLifeCycleSucessfull(String testname, String vin, String campaign, String criterion) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String board = ReadTestParameters.getAttributeValue(testname, "board");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{\"data\": [ {\"vin\": \""+vin+"\",\"id\": \""+id+"\",\"messageId\": null,\"createTimestamp\": "+createdTimeStamp+",\"campaignNumber\": \""+campaign+"\",\"criterionId\": \""+criterion+"\",\"error\": null,\"errorConcatenated\": null,\"errorTimestamp\": null,\"doNotRetryFlag\": false,\"manufacturer\": \""+brand+"\",\"components\":[\""+board+"\"],\"details\": null}]}");
		ValidatableResponse body = request.when().post("/onup/api/bs/oru/v1/"+brand+"/s42/lifecycledocumentation/successful").then().log().all();
		
		return body;
	}	
	
	public static void assertCarportLifeCycleSuccessful(String testname, String vin, String campaign, String criterion) throws IOException {
		CarportLifeCycleSucessfull.assertGetCarportLifeCycleSucessful(testname, vin, campaign);
		ValidatableResponse response = CarportLifeCycleSucessfull.postCarportLifeCycleSucessfull(testname, vin, campaign, criterion);
		response.assertThat().statusCode(200);
	}
	
	
	public static void main(String [] args) throws IOException {
		//CarportLifeCycleSucessfull.assertCarportLifeCycleSuccessful(testname, vin, campaign, criterion);
		CarportLifeCycleSucessfull.assertGetCarportLifeCycleSucessful(testname, vin, campaign);
	}
}
