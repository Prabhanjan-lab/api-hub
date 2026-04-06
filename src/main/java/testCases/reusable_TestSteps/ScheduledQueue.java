package testCases.reusable_TestSteps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class ScheduledQueue extends JWT {
	public static Response scheduledQueueCampaignStatus(String testname, String vin) throws IOException {
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json")
		.queryParam("type", "scheduled");
		Response response = request.when().get("/onup/api/bs/oru/v1/" + brand + "/vehicle/" + vin + "/campaigns").then()
				.log().all().extract().response();
		return response;
	}

	public static void assertScheduledCampaign(String testname, String vin, String expectedCampaign)
			throws IOException {
		Response status = ScheduledQueue.scheduledQueueCampaignStatus(testname, vin);
		JsonPath j = new JsonPath(status.asString());

		if (j.getString("data[" + 0 + "].campaignId").equalsIgnoreCase(expectedCampaign)) {
			Assert.assertEquals(j.getString("data[" + 0 + "].status.code"), "2");

		}
	}

	public static void assertNotGetReScheduledCampaign(String testname, String vin, String expectedCampaign)
			throws IOException {
		Response status = ScheduledQueue.scheduledQueueCampaignStatus(testname, vin);
		JsonPath j = new JsonPath(status.asString());
		int i = 0;
		if (j.getString("data[" + i + "].campaignId") != (expectedCampaign)) {
			System.out.println("Campaign does not get rescheduled" + expectedCampaign);
		}
	}
	public static List<String> getScheduledCampaigns(String testname,String vin) throws IOException {
		Response status= ScheduledQueue.scheduledQueueCampaignStatus(testname,vin);
		JSONObject j = new JSONObject(status.asString());
		List<String> scheduledCampaignList =new ArrayList<>();
		for(int i = 0; i<j.getJSONArray("data").length(); i++) {
			String Campaign = j.getJSONArray("data").getJSONObject(i).getString("campaignId").toString();
			scheduledCampaignList.add(Campaign);
		}
		return scheduledCampaignList;
	}
	
	public static List<String> getScheduledCampaignsCriterion(String testname,String vin) throws IOException {
		Response statusRes= ScheduledQueue.scheduledQueueCampaignStatus(testname,vin);
		JSONObject j = new JSONObject(statusRes.asString());
		List<String> scheduledCampaignsCriterionList =new ArrayList<>();
		for(int i = 0; i<j.getJSONArray("data").length(); i++) {
			String criterion = j.getJSONArray("data").getJSONObject(i).getString("criterionId").toString();
			scheduledCampaignsCriterionList.add(criterion);
		}
		return scheduledCampaignsCriterionList;
	}
	
	public static boolean assertCampaignInScheduledQueue(String testname,String vin) throws IOException {
		Response SceduledCampaignNotification= ScheduledQueue.scheduledQueueCampaignStatus(testname, vin);
		SceduledCampaignNotification.then().assertThat().statusCode(200);
		 JsonPath jsonPath = SceduledCampaignNotification.jsonPath();
	     int recordsTotal = jsonPath.getInt("recordsTotal");
	     if (recordsTotal != 0) {
		return true;
		}else {
			return false;
		}
	}


}
