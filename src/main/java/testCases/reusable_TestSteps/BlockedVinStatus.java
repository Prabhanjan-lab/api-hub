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

public class BlockedVinStatus extends JWT {

	public static ValidatableResponse VerifyBlockedVinStatus(String testname,String campaign,String criterion) throws IOException {
		JWT.GenerateJWTToken();
	
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/" + brand + "/campaign/" + campaign
				+ "/criterion/" + criterion + "/charge/Default/blockedVehicles").then().log().all();
		return response;
	}

	public static void assertBlockedVinStatus(String testname,String campaign,String criterion) throws IOException {

		ValidatableResponse blockedVin = BlockedVinStatus.VerifyBlockedVinStatus(testname,campaign,criterion);
		blockedVin.assertThat().statusCode(200);
		blockedVin.and().body("recordsTotal", Matchers.equalTo(1));

	}

	public static void assertNoBlockedVinStatus(String testname,String campaign,String criterion) throws IOException {
	ValidatableResponse vin= BlockedVinStatus.VerifyBlockedVinStatus(testname,campaign,criterion);
	vin.assertThat().statusCode(200);
	vin.and().assertThat().body("recordsTotal",  Matchers.equalTo(0));
	}


}
