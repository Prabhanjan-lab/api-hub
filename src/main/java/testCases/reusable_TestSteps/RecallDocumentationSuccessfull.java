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

public class RecallDocumentationSuccessfull {
	
	public static String id ;
	public static Long createdTimeStamp;
		
	public static ValidatableResponse getRecallLifeCycleSuccessful(String testname) throws IOException {
		
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("search","");
		ValidatableResponse body = request.when().get("/onup/api/bs/oru/v1/"+brand+"/recall/lifecycledocumentation/queue/false").then().log().all();
		
		
		return body;
	}
	
	public static void assertRecallLifeCycleSuccessful(String testname, String expectedVin, String expectedCampaign,String expectedReason) throws IOException  {
		ValidatableResponse response = RecallDocumentationSuccessfull.getRecallLifeCycleSuccessful(testname);
		response.assertThat().statusCode(200);
		
		Response response1 = response.extract().response();
		JsonPath jsonPath = response1.jsonPath();
		
		List<Map<String, Object>> dataList =  jsonPath.getList("data");
		
		boolean matchFound = false;
		
		
		for(Map<String, Object> item : dataList) {
			String vin = (String) item.get("vin");
            String campaign = (String) item.get("campaignNumber");
            String reason =(String) item.get("reason");
			
			if(expectedVin.equals(vin) && expectedCampaign.equals(campaign)&&expectedReason.equals(reason)) {
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
	
	public static ValidatableResponse postRecallLifeCycleSuccessfull(String testname, String vin, String campaign, String criterion) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{\"data\": [ {\"id\": \""+id+"\",\"reason\": \"2_DONE_BY_ORU\"}]}");
		ValidatableResponse body = request.when().post("/onup/api/bs/oru/v1/"+brand+"/recall/lifecycledocumentation/successful").then().log().all();
		
		return body;
	}	
	
	public static void assertRecallLifeCycleDocumentationSuccessful(String testname, String vin, String campaign, String criterion,String reason) throws IOException {
		RecallDocumentationSuccessfull.assertRecallLifeCycleSuccessful(testname, vin, campaign,reason);
		ValidatableResponse response = RecallDocumentationSuccessfull.postRecallLifeCycleSuccessfull(testname, vin, campaign, criterion);
		response.assertThat().statusCode(200);
	}
	
}
