package testCases.testStep_VetoAdvice.mIB3_VW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class VetoAdvice_MIB3VW_MQTT extends RegisterAndGetOAUTH {

	public static ValidatableResponse vetoAdvice(String testname, String vin, String campaign, String criterion) throws IOException {

		String filePath = "resources/Payload_Xmls/MIB3_VW_MQTT/MIB3VW_MQTT_Vetoadvice.xml"; // Read the XML content from the file
		String vetoadvice = new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();

		RequestSpecification request = RestAssured.given().log().all();
		request.header("Accept-Charset", "UTF-8").header("Authorization", "Bearer " + OAuthToken).header("Accept",
				"application/vnd.vwg.mbb.onlineUpdateRepairAdviceResponse_v2_1_0+xml,application/vnd.vwg.mbb.onlineUpdateError_v1_1_0+xml,application/vnd.vwg.mbb.genericError_v1_1_1+xml,")
				.header("X-Brand", "VW").header("X-Vin", vin).header("Content-Type", "application/xml");

		ValidatableResponse response = request.when().queryParam("lang", "de_DE").queryParam("vc", PutVCHash)
				.queryParam("timestamp", "2016-04-28T09:00:00Z")
				.config(RestAssured.config().encoderConfig(
						EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
				.body(vetoadvice).post("/mbb/uota/v1/advice/campaign/" + campaign + "/version/" + criterion + "/advice")
				.then().log().all(); 
		return response;
	}

	public static void assertVetoAdvice_MIB3VW_MQTT(String testname, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse vetoadvice = VetoAdvice_MIB3VW_MQTT.vetoAdvice(testname, vin, campaign, criterion);
		vetoadvice.assertThat().statusCode(200);
		vetoadvice.and().body("repairAdvice.adviceCode", Matchers.equalTo("REPAIR_NECESSARY"));
	}
}
