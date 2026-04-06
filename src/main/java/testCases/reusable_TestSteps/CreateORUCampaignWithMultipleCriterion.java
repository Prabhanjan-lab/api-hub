package testCases.reusable_TestSteps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class CreateORUCampaignWithMultipleCriterion extends JWT {
	public static Response postRequestForORUCampaign(String testname, String filePath) throws IOException {

		// Read test parameters
		String campaignNumber = ReadTestParameters.getAttributeValue(testname, "campaign");
		String campaignTitle = campaignNumber; // Campaign title and campaign number are same here
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String brandcontext = ReadTestParameters.getAttributeValue(testname, "brandContext");
		String amId = ReadTestParameters.getAttributeValue(testname, "amId");
		String amCode = ReadTestParameters.getAttributeValue(testname, "amCode");
		String TNR = ReadTestParameters.getAttributeValue(testname, "TNR");
		String domain = ReadTestParameters.getAttributeValue(testname, "domain");

		// Generate JWT token
		JWT.GenerateJWTToken();

		// Load the JSON template and replace placeholders
		String oRUCampaignPayload = new String(Files.readAllBytes(Paths.get(filePath)));

		// Replace placeholders in the JSON template
		oRUCampaignPayload = oRUCampaignPayload.replace("\"campaignnumber\"", "\"" + campaignNumber + "\"")
				.replace("\"campaigntitle\"", "\"" + campaignTitle + "\"")
				.replace("\"brandcontext\"", "\"" + brandcontext + "\"").replace("\"amcode\"", "\"" + amCode + "\"")
				.replace("\"amid\"", "\"" + amId + "\"").replace("\"tNR\"", "\"" + TNR + "\"")
				.replace("\"domain\"", "\"" + domain + "\"");

		// Set environment URL and configure request
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();

		// Set up request with headers and payload
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken)
				.config(RestAssured.config().encoderConfig(
						EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
				.header("Content-Type", "application/json").body(oRUCampaignPayload);

		// Send POST request
		Response response = request.when().post("onup/api/bs/oru/v1/" + brand + "/oru").then().log().all().extract()
				.response();

		return response;
	}

	public static void assertCreateORUCampaign(String testname, String filePath)
			throws IOException, InterruptedException {
		// Send POST request and validate response
		Response response = CreateORUCampaignWithMultipleCriterion.postRequestForORUCampaign(testname, filePath);
		int statusCode = response.getStatusCode();
		Assert.assertEquals("Status code is not 201", 201, statusCode);
		Thread.sleep(240000);
	}
}
