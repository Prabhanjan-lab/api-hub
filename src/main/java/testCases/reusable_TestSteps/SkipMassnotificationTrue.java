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

public class SkipMassnotificationTrue extends JWT {
	public static ValidatableResponse setSkipMassnotificationToTrue(String testname,String vin) throws IOException {
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		String brand =ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.body("{ \"settings\": { \"testVehicle\": true, \"prototypeVehicle\": true, \"mibSwupSupport\": false, \"skipMassNotification\": true } }").when().post("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/settings/change").then().log().all();
		return response;
	}
	
	public static ValidatableResponse getSkipMassnotification(String testname,String vin) throws Exception {
		Environments.setEnvironmentURL();
		String brand =ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/settings").then().log().all();
		return response;
	}
	
	public static void assertSkipMassnotificationTrue(String testname,String vin) throws Exception {
		ValidatableResponse skipmassno= SkipMassnotificationTrue.setSkipMassnotificationToTrue(testname,vin);
		skipmassno.assertThat().statusCode(200);
		ValidatableResponse skipmassnotrue= SkipMassnotificationTrue.getSkipMassnotification(testname,vin);
		skipmassnotrue.assertThat().statusCode(200);
		skipmassnotrue.and().body("settings.skipMassNotification", Matchers.equalTo(true));
	}

}
