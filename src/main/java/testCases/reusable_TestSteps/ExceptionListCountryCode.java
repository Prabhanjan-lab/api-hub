package testCases.reusable_TestSteps;

import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class ExceptionListCountryCode extends JWT {
	public static ValidatableResponse upadateExceptionListCountryCode(String testname) throws Exception {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		String env = Environments.getEnvironment();
		String payload = "";
		if (env.equalsIgnoreCase("DEMO_ECE")) {
			String filePath = "resources/Payload_Jsons/Demo_ExceptionListCountryCode.json";
			payload = new String(Files.readAllBytes(Paths.get(filePath)));

		} else if (env.equalsIgnoreCase("APPROVAL_ECE")) {
			String filePath = "resources/Payload_Jsons/Approval_ExceptionListCountryCode.json";
			payload = new String(Files.readAllBytes(Paths.get(filePath)));
		} else {
			System.out.println("Pass the correct JSON payload");
		}
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		ValidatableResponse response = request.when().body(payload)
				.put("/onup/api/bs/oru/v1/" + brand + "/exceptionlist").then().log().all();
		return response;
	}

	public static void assertUpdateExceptionListCountryCode(String testname) throws Exception {
		ValidatableResponse response = ExceptionListCountryCode.upadateExceptionListCountryCode(testname);
		response.assertThat().statusCode(200);
	}

}
