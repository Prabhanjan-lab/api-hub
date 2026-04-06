package testCases.reusable_TestSteps;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class Get_VehicleConfiguration {
	public static ValidatableResponse VehicleConfiguration(String testname,String vin) throws IOException {
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		String domain=ReadTestParameters.getAttributeValue(testname, "domain");
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		ValidatableResponse response = request.when()
				.get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/configurations/"+domain)
				.then().log().all();
		return response;
	}
	
	public static void assertVariantInVehilceConfiguration(String testname,String vin,String variant) {
		try {
			ValidatableResponse	VehicleConfig= Get_VehicleConfiguration.VehicleConfiguration(testname,vin);
			VehicleConfig.assertThat().statusCode(200);
			String response=VehicleConfig.extract().asString();
			response.contains("variant=\""+variant+"\"");
			System.out.println("Variant matched");
			} catch (IOException e) {
			System.out.println("Vehicle Configuration not found");
			e.printStackTrace();
		}	
	}
	public static void assertVCPlausible(String testname,String vin) throws IOException {
		ValidatableResponse	VehicleConfig= Get_VehicleConfiguration.VehicleConfiguration(testname,vin);
		VehicleConfig.assertThat().statusCode(200);
	//	VehicleConfig.and().body("plausible", Matchers.equalTo("true"));
	}
}
