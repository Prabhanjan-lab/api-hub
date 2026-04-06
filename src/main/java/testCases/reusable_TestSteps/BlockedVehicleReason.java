package testCases.reusable_TestSteps;

import java.io.IOException;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class BlockedVehicleReason extends JWT {

	public static ValidatableResponse blockedReason(String testname,String campaign,String criterion) throws IOException {
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		ValidatableResponse response = request.when()
				.get("/onup/api/bs/oru/v1/V/campaign/" + campaign + "/criterion/"
						+ criterion + "/charge/Default/blockedVehicles")
				.then().log().all();
		return response;
	}

	public static void assertblockedVehicleReason(String testname,String vin,String campaign,String criterion) throws IOException {

		ValidatableResponse blockingReason = BlockedVehicleReason.blockedReason(testname,campaign,criterion);
		blockingReason.assertThat().statusCode(200);
		blockingReason.assertThat().body("data.vin", Matchers.contains(vin));
		blockingReason.assertThat().body("data.reasonDetailParameters.flatten()", Matchers.contains("fr_FR"));
	}
}
