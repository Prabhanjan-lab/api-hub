package testCases.reusable_TestSteps;

import java.io.IOException;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class GetS42Detail {

	public static ValidatableResponse GetService42Details(String testname,String amCode) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/service42/amCode/"+amCode+"/detail").then().log().all();
		return response;
	}
	
	public static void assertService42WithInvalidamCode(String testname,String amCode) throws InterruptedException, IOException {
		ValidatableResponse invalidS42amCode = GetS42Detail.GetService42Details(testname,amCode);
		invalidS42amCode.assertThat().statusCode(404);
		invalidS42amCode.and().assertThat().body("error.code", Matchers.equalToIgnoringCase("service42_not-found-am"));
		invalidS42amCode.and().assertThat().body("error.description", Matchers.equalToIgnoringCase("Service42 campaign not found for AM code and id."));
	}
}
