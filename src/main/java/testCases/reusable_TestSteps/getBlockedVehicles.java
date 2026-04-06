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

public class getBlockedVehicles {
	public static ValidatableResponse getBlockedVehiclesInCampaign(String testname,String campaign,String criterion,String firstChargeName) throws IOException {
		
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String queryParams = "?page=0$count=1$sort=(Vehicle,asc)$filter=BAU(1)";
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json")
				.urlEncodingEnabled(false);
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/" + brand + "/campaign/" + campaign
				+ "/criterion/" + criterion + "/charge/" + firstChargeName + "/blockedVehicles" + queryParams).then().log()
				.all();
		return response;
	}

	public static void assertBlockedVehiclesWithInvalidCharge(String testname,String campaign,String criterion,String firstChargeName) throws InterruptedException, IOException {
		ValidatableResponse statusResponse = getBlockedVehicles.getBlockedVehiclesInCampaign(testname,campaign,criterion,firstChargeName);
		statusResponse.assertThat().statusCode(400);
		statusResponse.and().assertThat().body("error.description",
				Matchers.equalToIgnoringCase("Charge name is invalid."));
	}

}
