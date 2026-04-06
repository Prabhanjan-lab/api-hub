package testCases.reusable_TestSteps;
import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class Vin_In_Vehiclepool extends JWT{

public static ValidatableResponse RecallPool(String testname, String campaign, String criterion) throws IOException {
	JWT.GenerateJWTToken();
	String brand = ReadTestParameters.getAttributeValue(testname, "brand");
	Environments.setEnvironmentURL();
	RestAssured.baseURI= Environments.OUDMockURL;
	proxySettings.proxy();
	RequestSpecification request = RestAssured.given().log().all();
	request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
	ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/oru/"+campaign+"/criterion/"+criterion).then().log().all();
	return response;
}
public static void assertVinInRecallPool(String testname, String campaign, String criterion) throws IOException {
	ValidatableResponse vin= Vin_In_Vehiclepool.RecallPool(testname, campaign, criterion);
	vin.assertThat().statusCode(200);
	}
}
