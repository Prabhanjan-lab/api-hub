package testCases.testStep_Package.mIB3_VW;

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

public class packages_MIB3VW_ODP extends RegisterAndGetOAUTH {
	public static ValidatableResponse postRequestForPackages(String testname, String vin, String campaign, String criterion) throws IOException {
		String filePath = "resources/Payload_Xmls/MIB3_VW_ODP/MIB3VW_ODP_Packages.xml"; // Read the XML content from the file
		String package_MIB3Audi = new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + OAuthToken)
				.config(RestAssured.config().encoderConfig(
						EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
				.header("Accept", "application/vnd.vwg.mbb.onlineUpdateDownloadResourcesResponse_v1_0_0+xml",
						"application/vnd.vwg.mbb.onlineUpdateError_v1_0_0+xml",
						"application/vnd.vwg.mbb.genericError_v1_0_2+xml")
				.header("X-Brand", "VW").header("X-Vin", vin).header("X-Country", "DE")
				.header("Content-Type", "application/vnd.vwg.mbb.onlineUpdateDownloadResourcesRequest_v1_0_0+xml")
				.queryParam("vc", PutVCHash).queryParam("timestamp", "2016-04-28T09:00:00Z");
		ValidatableResponse response = request.body(package_MIB3Audi).when()
				.post("/mbb/uota_url/v1/campaign/" + campaign + "/version/" + criterion + "/packages").then().log()
				.all();
		return response;
	}

	public static void assertPackagesRepairDone_MIB3VW_ODP(String testname, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse repairdonepackage = packages_MIB3VW_ODP.postRequestForPackages(testname, vin, campaign, criterion);
		repairdonepackage.assertThat().statusCode(200);
	}

	public static void assertPackagesNoApproval_MIB3VW_ODP(String testname, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse repairdonepackage = packages_MIB3VW_ODP.postRequestForPackages(testname, vin, campaign, criterion);
		repairdonepackage.assertThat().statusCode(400);
		repairdonepackage.and().assertThat().body("err:error",
				Matchers.hasItem("mbbc.com.vw.mbbb.onup.insufficientApproval"));
		repairdonepackage.and().assertThat().body("err:error",
				Matchers.hasItem("Vehicle has no approval for using this campaign."));
	}

	public static void assertPackagesCampaignNotActive_MIB3VW_ODP(String testname, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse repairdonepackage = packages_MIB3VW_ODP.postRequestForPackages(testname, vin, campaign, criterion);
		repairdonepackage.assertThat().statusCode(400);
		repairdonepackage.and().assertThat().body("err.error-code",
				Matchers.equalToIgnoringCase("mbbc.com.vw.mbbb.onup.parameterMissingOrWrong"));
		repairdonepackage.and().assertThat().body("err.description",
				Matchers.equalToIgnoringCase("Campaign not active"));

	}
}
