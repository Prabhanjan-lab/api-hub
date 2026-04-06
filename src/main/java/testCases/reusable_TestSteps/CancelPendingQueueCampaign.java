package testCases.reusable_TestSteps;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class CancelPendingQueueCampaign extends JWT {

	public static ValidatableResponse CancelPendingCampaign(String testname,String vin,String campaign,String criterion) throws IOException {
		JWT.GenerateJWTToken();
		
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json").body(
				"{ \"activity\" : { \"operationCode\" : \"\", \"description\" : \"\", \"referenceId\" :\"\" }, \"campaignInfo\" : { \"campaignId\" : \""
						+ campaign + "\", \"criterionId\" : \"" + criterion
						+ "\" }, \"error\" : { \"code\" : \"\", \"description\" : \"\" } }");
		ValidatableResponse response = request.when().post("/onup/api/bs/oru/v1/" + brand + "/vehicle/" + vin
				+ "/campaign/" + campaign + "/criterion/" + criterion + "/cancel").then().log().all();
		return response;
	}

	public static Response CampaignStatus(String testname,String vin) throws IOException {
		Environments.setEnvironmentURL();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json")
				.queryParam("type", "concluded");
		Response response = request.when().get("/onup/api/bs/oru/v1/" + brand
				+ "/vehicle/" + vin + "/campaigns").then().log().all().extract()
				.response();
		return response;
	}

	public static void assertCancelPendingCampaign(String testname,String vin,String expectedCampaign,String criterion) throws IOException {
		ValidatableResponse abort = CancelPendingQueueCampaign.CancelPendingCampaign(testname,vin,expectedCampaign,criterion);
		abort.assertThat().statusCode(200);
		Response status = CancelPendingQueueCampaign.CampaignStatus(testname,vin);
		JsonPath j = new JsonPath(status.asString());
		for (int i = 0; j.getString("data[" + i + "].campaignId") != null; i++) {
			if (j.getString("data[" + i + "].campaignId").equalsIgnoreCase(expectedCampaign)) {
				j.getString("data[" + i + "].status.code").contains("37");
			}
		}

	}

}
