package testCases.reusable_TestSteps;

import java.io.IOException;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class ApproveBatch extends JWT {
	public static ValidatableResponse approveBatch(String testname, String campaign, String criterion, String batchName)
			throws IOException {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String amId = ReadTestParameters.getAttributeValue(testname, "amId");
		String amCode = ReadTestParameters.getAttributeValue(testname, "amCode");
		String TNR = ReadTestParameters.getAttributeValue(testname, "TNR");
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json")
				.body("{ \"data\": [ { \"name\": \"" + batchName + "\", \"approved\": true } ] }");
		ValidatableResponse response = request.when().put("/onup/api/bs/oru/v1/" + brand + "/oru/" + campaign
				+ "/criterion/" + criterion + "/flashmedium/" + amCode + "/" + amId + "/" + TNR + "/release").then()
				.log().all();
		return response;
	}

	public static Response getChargeStatusUpdate(String testname, String campaign, String criterion)
			throws IOException {
		JWT.GenerateJWTToken();

		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String TNR = ReadTestParameters.getAttributeValue(testname, "TNR");
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		Response response = request.when()
				.get("/onup/api/bs/oru/v1/" + brand + "/charge/" + campaign + "/" + criterion + "/" + TNR).then().log()
				.all().extract().response();
		return response;
	}

	public static void assertApproveBatch(String testname, String campaign, String criterion, String batchName)
			throws IOException {
		Response chargeStatusUpdate = getChargeStatusUpdate(testname, campaign, criterion);
		String chargeStatus = chargeStatusUpdate.getBody().jsonPath().getString("data.approved");
		if (chargeStatus.contains("false")) {
			ValidatableResponse batch = ApproveBatch.approveBatch(testname, campaign, criterion, batchName);
			batch.assertThat().statusCode(204);
		} else {
			System.out.println("Charge is already approved");
		}
	}

	public static void assertApproveBatchDisabled(String testname, String campaign, String criterion, String batchName)
			throws IOException {
		Response chargeStatusUpdate = getChargeStatusUpdate(testname, campaign, criterion);
		String chargeStatus = chargeStatusUpdate.getBody().jsonPath().getString("data.approved");
		if ("false".equalsIgnoreCase(chargeStatus)) {
			ValidatableResponse batch = ApproveBatch.approveBatch(testname, campaign, criterion, batchName);
			batch.assertThat().statusCode(400);
			batch.and().assertThat().body("error.code", Matchers.equalToIgnoringCase("criterion_not-oru-relevant"));
			batch.and().assertThat().body("error.description",
					Matchers.equalToIgnoringCase("Criterion isn't oru relevant."));
		}
	}
}
