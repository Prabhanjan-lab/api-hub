package testCases.testStep_VetoAdvice.oCU3_VW;

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

public class vetoAdvice_NON_UNECE_VW_OCU3 extends RegisterAndGetOAUTH {

	public static ValidatableResponse vetoAdvice(String testname, String vin, String campaign, String criterion) throws IOException {
		
		String filePath = "resources/Payload_Xmls/OCU3_VW_NONUNECE/NON_UNECE_VW_OCU3_Vetoadvice.xml"; // Read the XML content from the file
		String vetoadvice = new String(Files.readAllBytes(Paths.get(filePath)));

		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();

		RequestSpecification request = RestAssured.given().log().all();
		request.header("Accept-Charset","UTF-8")
		.header("Authorization", "Bearer " + OAuthToken)
		.header("Accept","application/vnd.vwg.mbb.onlineUpdateVetoRepairAdviceResponse_v1_1_0+xml,application/vnd.vwg.mbb.onlineUpdateError_v1_1_0+xml,application/vnd.vwg.mbb.genericError_v1_1_1+xml")
		.header("X-Brand", "VW").header("X-Vin", vin)
		.config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
		.header("Content-Type", "application/xml","application/vnd.vwg.mbb.onlineUpdateVetoRepairAdviceRequest_v1_1_0+xml");

		ValidatableResponse response = request.when().queryParam("vc", PutVCHash)
				.queryParam("timestamp", "2016-04-28T09:00:00Z").queryParam("lang", "de_DE").body(vetoadvice)
				.post("/mbb/uota/v1/campaign/" + campaign + "/version/" + criterion + "/vetoAdvice").then().log().all();
		return response;
	}

	public static void assertVetoAdvice_NON_UNECE_VWOCU3(String testname, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse vetoAdvice = vetoAdvice_NON_UNECE_VW_OCU3.vetoAdvice(testname, vin, campaign, criterion);
		vetoAdvice.assertThat().statusCode(200);
		vetoAdvice.and().assertThat().body("repairAdvice.adviceCode", Matchers.equalTo("ABORT_REPAIR"));
		vetoAdvice.and().assertThat().header("X-Warning","Vehicle tried to use the veto protocol on a campaign with a domain that does not support the protocol at all");
	}
}
