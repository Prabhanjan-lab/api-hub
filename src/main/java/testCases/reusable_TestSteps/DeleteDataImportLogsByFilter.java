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

public class DeleteDataImportLogsByFilter {
	
	public static ValidatableResponse deleteImportLogsByFilter(String testname,String ImportId) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().delete("/onup/api/bs/oru/v1/"+brand+"/logging/importlogid/"+ImportId+"/records").then().log().all();
		return response;
	}
	
	public static void assertDeleteInvalidLoggingId(String testname,String invalidImportId) throws InterruptedException, IOException {
		ValidatableResponse invalidLoggingId = DeleteDataImportLogsByFilter.deleteImportLogsByFilter(testname,invalidImportId);
		invalidLoggingId.assertThat().statusCode(400);	
		invalidLoggingId.and().assertThat().body("error.code", Matchers.equalToIgnoringCase("common_missing-request-param"));
		invalidLoggingId.and().assertThat().body("error.description", Matchers.equalToIgnoringCase("Missing request parameter."));
	
	}

}
