package testCases.testStep_Advice.mIB3_VW;

import java.io.IOException;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class Advice_MIB3VW_MQTT extends RegisterAndGetOAUTH {

	public static ValidatableResponse postRequestForAdvice(String testname, String putVC, String vin, String campaign, String criterion) throws IOException {
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + OAuthToken)
				.header("Accept", "application/vnd.vwg.mbb.onlineUpdateRepairAdviceResponse_v1_1_0+xml",
						"application/vnd.vwg.mbb.onlineUpdateError_v1_1_0+xml",
						"application/vnd.vwg.mbb.genericError_v1_1_1+xml")
				.header("Content-Type", "application/xml").header("X-Brand", "VW")
				.header("X-Vin", vin)
				.queryParam("lang", "de_DE").queryParam("vc", putVC).queryParam("timestamp", "2016-04-28T09:00:00Z");
		ValidatableResponse response = request.when()
				.post("/mbb/uota/v1/advice/campaign/" + campaign + "/version/" + criterion + "/advice").then().log()
				.all();
		return response;
	}

	public static void assertAdviceRepairNecessary_MIB3VW_MQTT(String testname, String putVC, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse advice = Advice_MIB3VW_MQTT.postRequestForAdvice(testname, putVC, vin, campaign, criterion);
		advice.assertThat().statusCode(200);
		advice.and().body("repairAdvice.adviceCode", Matchers.equalTo("REPAIR_NECESSARY"));
	}
}
