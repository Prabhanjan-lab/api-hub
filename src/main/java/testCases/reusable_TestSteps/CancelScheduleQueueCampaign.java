package testCases.reusable_TestSteps;

import java.io.IOException;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class CancelScheduleQueueCampaign extends JWT {

	public static ValidatableResponse EmptyScheduledQueue(String testname,String vin,String scheduledCampaign,String scheduledCampaignsCriterion) throws IOException {
		
			JWT.GenerateJWTToken();
			Environments.setEnvironmentURL();
			String brand = ReadTestParameters.getAttributeValue(testname, "brand");
			RestAssured.baseURI = Environments.OUDMockURL;
			proxySettings.proxy();
			RequestSpecification request = RestAssured.given().log().all();
			request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json").body(
					"{ \"activity\" : { \"operationCode\" : \"\", \"description\" : \"\", \"referenceId\" :\"\" }, \"campaignInfo\" : { \"campaignId\" : \""
							+ scheduledCampaign + "\", \"criterionId\" : \"" + scheduledCampaignsCriterion
							+ "\" }, \"error\" : { \"code\" : \"\", \"description\" : \"\" } }");

			ValidatableResponse res = request.when().post("/onup/api/bs/oru/v1/" + brand + "/vehicle/" + vin
					+ "/campaign/" + scheduledCampaign + "/criterion/" + scheduledCampaignsCriterion + "/abort").then().log().all();
			return res;
	}
	public static void assertEmptyScheduledQueue(String testname,String vin,List<String> scheduledCampaignList,List<String> scheduledCampaignsCriterion) throws IOException {
		for (int i=0; i<scheduledCampaignList.size();i++) {	
			ValidatableResponse response=EmptyScheduledQueue(testname,vin,scheduledCampaignList.get(i),scheduledCampaignsCriterion.get(i));
			response.assertThat().statusCode(200);
		}
	}
}
