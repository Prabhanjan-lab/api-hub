package testCases.testStep_Package.oCU3_VW;

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

public class invalidPackages_OCU3_VW extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse postRequestForInvalidPackages(String testname, String vin, String campaign, String criterion) throws IOException {
		String filePath = "resources/Payload_Xmls/OCU3_VW/OCU3_VW_Invalid_Packages.xml"; // Read the XML content from the file
		String Packages_OCU3VW= new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Accept-Charset","UTF-8").header("Authorization","Bearer " + OAuthToken)
			.header("Accept","application/vnd.vwg.mbb.onlineUpdateDownloadResourcesResponse_v1_0_0+xml,application/vnd.vwg.mbb.onlineUpdateError_v1_0_0+xml,application/vnd.vwg.mbb.genericError_v1_0_2+xml")
			.header("X-Brand","VW")
			.header("X-Vin",vin)
			.header("X-Country","DE")
			.config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
			.header("Content-Type","application/vnd.vwg.mbb.onlineUpdateDownloadResourcesRequest_v1_0_0+xml")
			.queryParam("vc",PutVCHash)
			.queryParam("timestamp","2016-04-28T09:00:00Z");
		ValidatableResponse response = request.body(Packages_OCU3VW).when().post("/mbb/uota_url/v1/campaign/"+campaign+"/version/"+criterion+"/packages").then().log().all();
		return response;
	}
	
	public static void assertInvalidPackages_OCU3VW(String testname, String vin, String campaign, String criterion) throws IOException{
		ValidatableResponse repairdonepackage= invalidPackages_OCU3_VW.postRequestForInvalidPackages(testname, vin, campaign, criterion);
		repairdonepackage.assertThat().statusCode(400);
		repairdonepackage.and().assertThat().body("err.error-code", Matchers.equalToIgnoringCase("mbbc.com.vw.mbbb.onup.parameterMissingOrWrong"));
		repairdonepackage.and().assertThat().body("err.description", Matchers.equalToIgnoringCase("Request XML did not validate"));
	}

}
