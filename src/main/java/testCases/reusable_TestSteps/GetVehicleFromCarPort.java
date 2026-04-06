package testCases.reusable_TestSteps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class GetVehicleFromCarPort {
	public static Response getVehicleFromCarPort(String testname, String vin) throws Exception {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json")
				.queryParam("type", "active");
		Response response = request.when().get("/onup/api/bs/oru/v1/" + brand + "/carport/vehicleFromCarPort/" + vin)
				.then().log().all().extract().response();
		return response;
	}

	public static void assertGetVehicleFromCarPortDetails(String testname, String vin) throws Exception {
		Response response = GetVehicleFromCarPort.getVehicleFromCarPort(testname, vin);
		int statuscode = response.getStatusCode();
		assertEquals(200, statuscode, "Vehicle details received");
	}

	public static String GetGetVehicleFromCarPort(String testname, String vin) throws Exception {
		Response response = GetVehicleFromCarPort.getVehicleFromCarPort(testname, vin);
		return response.then().extract().path("vin");
	}

	public static void assertVINEmpty(String testname, String vin) throws Exception {
		String assertVIN = GetVehicleFromCarPort.GetGetVehicleFromCarPort(testname, vin);
		assertEquals(vin, assertVIN, "VIN is empty");
	}
}
