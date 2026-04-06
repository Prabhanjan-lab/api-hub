package testCases.reusable_TestSteps;

import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class DeleteTranslationToCampaign extends JWT {
	public static ValidatableResponse deleteTranslation_fr_FR(String testname,String campaign) throws Exception {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String filePath = "resources/Payload_Jsons/DeleteTranslationToCampaign.json";
		String payload = new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		ValidatableResponse response = request.when().body(payload)
				.post("/onup/api/bs/oru/v1/" + brand + "/translation/campaignDescription/" + campaign).then().log()
				.all();
		return response;
	}

	public static void assertDeleteTranslationToCampaign(String testname,String campaign) throws Exception {
		ValidatableResponse abort = DeleteTranslationToCampaign.deleteTranslation_fr_FR(testname,campaign);
		abort.assertThat().statusCode(200);
	}
}
