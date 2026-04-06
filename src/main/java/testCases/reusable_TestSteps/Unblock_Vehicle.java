package testCases.reusable_TestSteps;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class Unblock_Vehicle extends JWT{
	
	public static ValidatableResponse Unblock_BlockedVin(String testname, String vin, String campaign, String criterion) throws IOException {
		
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
        RestAssured.baseURI= Environments.OUDMockURL;
        proxySettings.proxy();
        RequestSpecification request = RestAssured.given().log().all();
        request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
        ValidatableResponse response = request.when().put("/onup/api/bs/oru/v1/"+brand+"/campaign/"+campaign+"/criterion/"+criterion+"/blockedVehicles/"+vin+"/unblock").then().log().all();
        return response;
        }

    public static void assertUnBlockVin(String testname, String vin, String campaign, String criterion) throws IOException {

        ValidatableResponse UnblockedVin = Unblock_Vehicle.Unblock_BlockedVin(testname, vin, campaign, criterion);
        UnblockedVin.assertThat().statusCode(200);
    }

}
