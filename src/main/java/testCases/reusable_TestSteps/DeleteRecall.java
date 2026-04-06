package testCases.reusable_TestSteps;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class DeleteRecall {
	public static ValidatableResponse RecallDel(String testname, String campaign) throws IOException {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().delete("/onup/api/bs/oru/v1/"+brand+"/recalls/"+campaign).then().log().all();
		return response;
	}
	public static void assertRecallDelete(String testname, String campaign) throws Exception {
		ValidatableResponse recall= DeleteRecall.RecallDel(testname, campaign);
		recall.assertThat().statusCode(200);
	}

}
