package testCases.reusable_TestSteps;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class ActiveQueueAbortCampaign extends JWT{
	public static ValidatableResponse abortCampaign(String testname, String vin, String campaign, String criterion) throws Exception {
		JWT.GenerateJWTToken();
		
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		
		String filePath = "resources/Payload_Jsons/ActiveQueueAbortCampaign.json"; // Read the Json content from the file
		String payload = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonObject = new JSONObject(payload);
	    jsonObject.put("campaignInfo", new JSONArray("[{\"campaignId\": \""+campaign+"\",\"criterionId\": \""+criterion+"\"}]"));
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body(jsonObject.toString());
		ValidatableResponse response = request.when().post("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaign/"+campaign+"/criterion/"+criterion+"/abort").then().log().all();
		return response;
		}

	public static void assertAbortCampaign(String testname, String vin, String campaign, String criterion) throws Exception {
		ValidatableResponse abort= ActiveQueueAbortCampaign.abortCampaign(testname,vin,campaign,criterion);
		abort.assertThat().statusCode(200);
	}
}
