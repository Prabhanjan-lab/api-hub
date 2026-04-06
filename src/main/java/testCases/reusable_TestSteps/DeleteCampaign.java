package testCases.reusable_TestSteps;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class DeleteCampaign {
	public static ValidatableResponse ORUdelete(String testname, String campaign ) throws IOException {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().delete("/onup/api/bs/oru/v1/"+brand+"/oru/"+campaign).then().log().all();
		return response;
	}
	public static void assertORUdelete(String testname, String campaign) throws Exception {
		ValidatableResponse oru= DeleteCampaign.ORUdelete(testname, campaign);
		oru.assertThat().statusCode(200);
	}
}