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

public class getOruActionCsv {

	public static ValidatableResponse getOruActionCsvFile(String testname,String campaign)
			throws IOException {
		
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		ValidatableResponse response = request.when()
				.get("/onup/api/bs/oru/v1/" + brand + "/oru/csvencrypted/" + campaign).then().log().all();
		return response;
	}

	public static void assertStatusResponseWithInvalidCampaignNumber(String testname,String campaign) throws InterruptedException, IOException {
		ValidatableResponse statusResponse = getOruActionCsv
				.getOruActionCsvFile(testname,campaign);
		statusResponse.assertThat().statusCode(400);
		statusResponse.and().assertThat().body("error.description",
				Matchers.equalToIgnoringCase("Oru Campaign number is invalid."));

	}

}
