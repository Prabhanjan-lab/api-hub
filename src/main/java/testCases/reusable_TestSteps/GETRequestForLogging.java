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

public class GETRequestForLogging {
	
	public static ValidatableResponse logging(String testname,String Page,String Count) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();

		request.header("Authorization","Bearer " + JWT.JWTToken)
		.queryParam("search","page="+Page).queryParam("count", Count).urlEncodingEnabled(false);
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/logging").then().log().all();
		return response;
	}
	
	public static void assertLoggingWithInvalidParameters(String testname,String InvalidPage,String InvalidCount) throws InterruptedException, IOException {
		ValidatableResponse Logging = GETRequestForLogging.logging(testname,InvalidPage,InvalidCount);
		Logging.assertThat().statusCode(400);	
		Logging.and().assertThat().body("error.code", Matchers.equalToIgnoringCase("common_invalid-search-param"));
		Logging.and().assertThat().body("error.description", Matchers.equalToIgnoringCase("Invalid search parameter."));
	
	}

}
