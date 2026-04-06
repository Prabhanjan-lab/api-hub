package testCases.testStep_ResultCode.oCU3_Audi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class ResultCode1_OCU3_Audi extends RegisterAndGetOAUTH{

	public static ValidatableResponse postRequestForResultCode(String testname, String VC, String vin, String campaign, String criterion ) throws IOException {
		String filePath = "resources/Payload_Xmls/ResultCode1.xml"; // Read the XML content from the file
		String resultcode = new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + OAuthToken)
				.config(RestAssured.config().encoderConfig(
						EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
				.header("Accept", "application/xml", "application/vnd.vwg.mbb.onlineUpdateError_v1_1_0+xml",
						"application/vnd.vwg.mbb.genericError_v1_1_1+xml")
				.header("X-Brand", "VW").header("X-Vin", vin)
				.header("Content-Type", "application/vnd.vwg.mbb.onlineUpdateRepairResultRequest_v1_1_0+xml")
				.queryParam("vc", VC);
		ValidatableResponse response = request.body(resultcode).when().post("/mbb/uota/v1/campaign/"+campaign+"/version/"+criterion+ "/result").then()
				.log().all();
		return response;
	}

	public static void assertResultCode_OCU3Audi(String testname, String VC, String vin, String campaign, String criterion ) throws IOException {
		ValidatableResponse result = ResultCode1_OCU3_Audi.postRequestForResultCode(testname, VC, vin, campaign, criterion);
		result.assertThat().statusCode(201);
	}
}
