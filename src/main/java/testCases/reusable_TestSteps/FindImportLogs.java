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

public class FindImportLogs {
	
	public static ValidatableResponse findDataImportLogs(String testname,String ImportLogId) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/logging/importlogid/"+ImportLogId+"/records").then().log().all();
		return response;
	}
	
	public static void assertInvalidLoggingId(String testname,String InvalidImportLogId) throws InterruptedException, IOException {
		ValidatableResponse invalidLoggingId = findDataImportLogs(testname,InvalidImportLogId);
		invalidLoggingId.assertThat().statusCode(400);
		invalidLoggingId.and().assertThat().body("error.code", Matchers.equalToIgnoringCase("common_missing-request-param"));
		invalidLoggingId.and().assertThat().body("error.description", Matchers.equalToIgnoringCase("Missing request parameter."));
	}

}
