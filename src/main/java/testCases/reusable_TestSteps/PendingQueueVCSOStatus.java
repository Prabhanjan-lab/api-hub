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

public class PendingQueueVCSOStatus extends JWT {

	public static ValidatableResponse pendingCampaign(String testname,String vin) throws IOException {
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json")
				.queryParam("type", "pending");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/" + brand
				+ "/vehicle/" + vin + "/campaigns").then().log().all();
		return response;
	}

	public static void assertPendingQueue(String testname,String vin,String expectedCampaign, String VCSO) throws InterruptedException, IOException {
		ValidatableResponse pendingCampaign = PendingQueueVCSOStatus.pendingCampaign(testname,vin);
		pendingCampaign.assertThat().statusCode(200);
		pendingCampaign.and().body("data.campaignId", Matchers.contains(expectedCampaign));
		pendingCampaign.and().body("data.status.code", Matchers.contains(VCSO));
	}
	
	public static boolean assertCampaignInPendingQueue(String testname,String vin,String expectedCampaign) throws IOException {
		ValidatableResponse pendingCampaignNotification= PendingQueueVCSOStatus.pendingCampaign(testname,vin);
		pendingCampaignNotification.assertThat().statusCode(200);
		pendingCampaignNotification.getClass();
		String status=pendingCampaignNotification.extract().asString();
		if(status.contains(expectedCampaign)){
		return true;
		}else {
			return false;
		}
	}
}
