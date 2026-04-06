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

public class getRecallWithCampaignNumber {
	public static ValidatableResponse getRecall(String testname,String campaign) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/" + brand + "/recalls/" + campaign)
				.then().log().all();
		return response;
	}

	public static void assertResponseContainsValidAttribute(String testname,String campaign) throws InterruptedException, IOException {
		ValidatableResponse statusResponse = getRecallWithCampaignNumber
				.getRecall(testname,campaign);
		statusResponse.assertThat().statusCode(200); 
		// Here if any boolean value has to be validate you should pass boolean value(true, false) only instead of string.
		statusResponse.and().assertThat().body("valid", Matchers.equalTo(true));
	}
	
	public static void assertNumberOfVehicles(String testname,String campaign,int numberOfVehicles) throws InterruptedException, IOException {
		ValidatableResponse statusResponse = getRecallWithCampaignNumber.getRecall(testname,campaign);
		statusResponse.assertThat().statusCode(200);
		int i=0;
		statusResponse.and().assertThat().body("criterions["+i+"].numberOfVehicles", Matchers.equalTo(numberOfVehicles));
	}
	
	public static void assertCriterion(String testname, String campaign, String criterion) throws InterruptedException, IOException {
		ValidatableResponse criterionResponse = getRecallWithCampaignNumber.getRecall(testname, campaign);
		criterionResponse.assertThat().statusCode(200);
		int i = 0;
		criterionResponse.and().assertThat().body("criterions["+i+"].criterionId", Matchers.equalTo(criterion));
	}
	
	public static void assertCriterionTitleEmpty(String testname, String campaign, String criterionTitle) throws InterruptedException, IOException {
		ValidatableResponse criterionTitleResponse = getRecallWithCampaignNumber.getRecall(testname, campaign);
		criterionTitleResponse.assertThat().statusCode(200);
		int i = 0;
		criterionTitleResponse.and().assertThat().body("criterions["+i+"].criterionTitle", Matchers.equalTo(criterionTitle));
	}

}
