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

public class GETRequestForLoggingShort {
	
	public static ValidatableResponse LoggingShort(String testname,String Page,String Count) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("search","page="+Page).queryParam("count", Count).urlEncodingEnabled(false);
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/logging/short").then().log().all();
		return response;
	}
	
	public static void assertLoggingShortWithInvalidParameters(String testname,String InvalidPage,String InvalidCount) throws InterruptedException, IOException {
		ValidatableResponse LoggingShort = GETRequestForLoggingShort.LoggingShort(testname,InvalidPage,InvalidCount);
		LoggingShort.assertThat().statusCode(400);	
		LoggingShort.and().assertThat().body("error.code", Matchers.equalToIgnoringCase("common_invalid-search-param"));
		LoggingShort.and().assertThat().body("error.description", Matchers.equalToIgnoringCase("Invalid search parameter."));
	
	}

}
