package testCases.reusable_TestSteps;
import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class SetReferenceVC{

	public static ValidatableResponse SetReferenceVehicle(String testname,String vin,String VcHash) throws IOException {
		JWT.GenerateJWTToken();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		String domain = ReadTestParameters.getAttributeValue(testname, "domain");
		Environments.setEnvironmentURL();
		
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().post("/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/configurations/"+domain+"/"+VcHash).then().log().all();
		return response;
		}
	
	public static void assertSetReferenceVCNotfound(String testname,String vin,String VcHash) throws IOException {
		ValidatableResponse response= SetReferenceVC.SetReferenceVehicle(testname,vin,VcHash);
		response.assertThat().statusCode(404);			
	}
	
}
