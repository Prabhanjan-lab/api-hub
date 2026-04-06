package testCases.testStep_MQTT;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class GetRolloutPlan_MIB3_VW_MQTT extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse getRolloutPlanID(String testname,String campaign, String criterion) throws IOException {
		JWT.GenerateJWTToken();
		String brand= ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/rolloutplan/"+campaign+"/criterion/"+criterion).then().log().all();
		return response;
	}

	public static String assertGetRolloutPlanId(String testname, String campaign, String criterion) throws IOException{
		ValidatableResponse response = GetRolloutPlan_MIB3_VW_MQTT.getRolloutPlanID(testname, campaign, criterion);
		response.assertThat().statusCode(200);
		String jsonResponse = response.extract().asString();
		
		JsonPath jsonPath = new JsonPath(jsonResponse);
		
		String rolloutPlanId = jsonPath.getString("data[0].partId");
		
		return rolloutPlanId ;
	}
}
