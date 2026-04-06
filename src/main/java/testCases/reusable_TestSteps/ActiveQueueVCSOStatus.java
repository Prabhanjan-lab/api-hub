package testCases.reusable_TestSteps;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class ActiveQueueVCSOStatus extends JWT {

	public static ValidatableResponse activeCampaign(String testname,String vin) throws IOException {
		JWT.GenerateJWTToken();
		String brand= ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("type","active");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaigns").then().log().all();
		return response;
	}
	
	public static void assertActiveQueue(String testname,String vin,String expectedCampaign,String VCSO) throws InterruptedException, IOException {
		ValidatableResponse activeCampaign= ActiveQueueVCSOStatus.activeCampaign(testname,vin);
		activeCampaign.assertThat().statusCode(200);
		activeCampaign.and().body("data.campaignId", Matchers.contains(expectedCampaign));
		activeCampaign.and().body("data.status.code", Matchers.contains(VCSO));
	}
	
	public static void assertActiveQueueWaitTime(String testname,String vin,String expectedCampaign,String VCSO) throws InterruptedException, IOException {
		Thread.sleep(360000);
		ValidatableResponse activeCampaign= ActiveQueueVCSOStatus.activeCampaign(testname,vin);
		activeCampaign.assertThat().statusCode(200);
		activeCampaign.and().body("data.campaignId", Matchers.contains(expectedCampaign));
		activeCampaign.and().body("data.status.code", Matchers.contains(VCSO));
	}
	
	public static void assertActiveQueueNotificationNoCancelUpdateJobSent(String testname,String vin,String expectedCampaign) throws InterruptedException, IOException {
		ValidatableResponse activeCampaignNotification= ActiveQueueVCSOStatus.activeCampaign(testname,vin);
		activeCampaignNotification.assertThat().statusCode(200);
		activeCampaignNotification.and().body("data.campaignId", Matchers.contains(expectedCampaign));
		activeCampaignNotification.and().body("data.notification.type", Matchers.not("Jobs: Cancel Update Job"));
	}
	
	public static void assertActiveQueueNotificationCancelUpdateJobSent(String testname,String vin,String expectedCampaign) throws InterruptedException, IOException {
		ValidatableResponse activeCampaignNotification= ActiveQueueVCSOStatus.activeCampaign(testname,vin);
		activeCampaignNotification.assertThat().statusCode(200);
		activeCampaignNotification.and().body("data.campaignId", Matchers.contains(expectedCampaign));
		activeCampaignNotification.and().body("data.notification.type", Matchers.contains("Jobs: Cancel Update Job"));
	}
	
	public static boolean assertCampaignInActiveQueue(String testname,String vin) throws IOException {
		ValidatableResponse activeCampaignNotification= ActiveQueueVCSOStatus.activeCampaign(testname,vin);
		activeCampaignNotification.assertThat().statusCode(200);
		String status=activeCampaignNotification.extract().asString();
		if(status.contains("\"recordsTotal\":0")){
		return false;
		}else{
			return true;
		}
	}
	
	public static boolean assertActiveQueueWithWTCriterion(String testname, String vin, String expectedCampaign, String VCSO) throws IOException, InterruptedException {
		Thread.sleep(360000);
		ValidatableResponse activeCampaign= ActiveQueueVCSOStatus.activeCampaign(testname,vin);
		String status = activeCampaign.assertThat().statusCode(200).extract().asString();
		if(status == "200") {
			activeCampaign.and().body("data.campaignId", Matchers.contains(expectedCampaign));
			return true;
		}
		return false;
	}
	
	public static void assertCampaignwithExpectedVCSO(String testname, String vin, String campaign, String expectedVCSO) 
	        throws InterruptedException, IOException {
	    long maxWaitMillis = 6 * 60 * 60 * 1000; // 6 hours
	    long retryIntervalMillis = 30 * 60 * 1000; // 30 minutes
	    long startTime = System.currentTimeMillis();

	    while (System.currentTimeMillis() - startTime < maxWaitMillis) {
	            ValidatableResponse response = ActiveQueueVCSOStatus.activeCampaign(testname, vin);

	            // Assert status code is 200
	            response.assertThat().statusCode(200);

	            // Extract the VCSO from the response
	            List<String> actualVCSO = response.extract().path("data.status.code");

	            if (actualVCSO != null && actualVCSO.equals(expectedVCSO)) {
	                System.out.println("✅ Found expected VCSO: " + expectedVCSO);
	                return; // success
	            } else {
	                System.out.println("⚠️ 200 received but expected VCSO not found (found: " + actualVCSO + "). Will retry in 30 minutes...");
	            }


	        // Wait 30 minutes before retry
	        Thread.sleep(retryIntervalMillis);
	    }

	    // Fail if timeout reached
	    Assert.fail("❌ Expected VCSO [" + expectedVCSO + "] not found within 6 hours.");
	}
	 
	
}
