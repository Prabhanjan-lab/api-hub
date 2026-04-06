package testCases.testStep_StatusCode.mIB3_VW;

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

public class StatusCode6_MIB3VW extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse postRequestForStatusCode(String testname, String vin, String campaign, String criterion) throws IOException {
		String filePath = "resources/Payload_Xmls/StatusCode6.xml"; // Read the XML content from the file
		String statuscode= new String(Files.readAllBytes(Paths.get(filePath)));		
		Environments.setEnvironmentURL();
		RestAssured.baseURI= BaseURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + OAuthToken)
		.config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
		.header("Accept","application/vnd.vwg.mbb.onlineUpdateError_v1_1_0+xml","application/vnd.vwg.mbb.genericError_v1_1_1+xml")
		.header("X-Brand","VW")
		.header("X-Vin",vin)
		.header("Content-Type","application/vnd.vwg.mbb.onlineUpdateStatusUpdatesRequest_v2_1_0+xml")
		.queryParam("vc",PutVCHash);
		ValidatableResponse response = request.body(statuscode).when().post("/mbb/uota/v1/campaign/"+campaign+"/version/"+criterion+"/status").then().log().all();
		return response;
		
	}
	
	public static void assertStatusCode_MIB3VW(String testname, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse status=StatusCode6_MIB3VW.postRequestForStatusCode(testname, vin, campaign, criterion);
		status.assertThat().statusCode(201);
	}

}
