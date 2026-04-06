package testCases.reusable_TestSteps;
import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class VehicleUnblock extends JWT{

public static ValidatableResponse unblockVin(String testname, String vin, String campaign, String criterion) throws IOException {
	JWT.GenerateJWTToken();	
	Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().put("/onup/api/bs/oru/v1/V/campaign/"+campaign+"/criterion/"+criterion+"/blockedVehicles/"+vin+"/unblock").then().log().all();
		return response;
	}
public static void assertunblock(String testname, String vin, String campaign, String criterion) throws IOException {
	ValidatableResponse unblock= VehicleUnblock.unblockVin(testname,vin, campaign, criterion);
	unblock.assertThat().statusCode(200);
	}
}
