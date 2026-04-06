package testCases.testStep_Advice.oCU3_Audi;

import java.io.IOException;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class Advice_OCU3_Audi extends RegisterAndGetOAUTH{

	public static ValidatableResponse postRequestForAdvice(String testname, String putVC, String vin, String campaign, String criterion) throws IOException {
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + OAuthToken)
				.header("Accept", "application/vnd.vwg.mbb.onlineUpdateRepairAdviceResponse_v2_1_0+xml",
						"application/vnd.vwg.mbb.onlineUpdateError_v1_1_0+xml",
						"application/vnd.vwg.mbb.genericError_v1_1_1+xml")
				.header("Content-Type", "application/xml").header("X-Brand", "Audi").header("X-Vin", vin)
				.queryParam("lang", "de_DE").queryParam("vc", putVC).queryParam("timestamp", "2016-04-28T09:00:00Z");
		ValidatableResponse response = request.when().post("/mbb/uota/v1/advice/campaign/"+campaign+"/version/"+criterion+"/advice").then().log().all();
		return response;
	}

	public static void assertAdviceAbortRepair_OCU3_Audi(String testname, String putVC, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse advice = Advice_OCU3_Audi.postRequestForAdvice(testname, putVC, vin, campaign, criterion);
		advice.assertThat().statusCode(200);
		advice.and().body("repairAdvice.adviceCode", Matchers.equalTo("ABORT_REPAIR"));
		advice.and().assertThat().header("X-Warning", "Campaign not active");
	}

	public static void assertAdviceRepairNecessary_OCU3Audi(String testname, String putVC, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse advice = Advice_OCU3_Audi.postRequestForAdvice(testname, putVC, vin, campaign, criterion);
		advice.assertThat().statusCode(200);
		advice.and().body("repairAdvice.adviceCode", Matchers.equalTo("REPAIR_NECESSARY"));
		advice.and().assertThat().body("repairAdvice.ivdProgHashSHA256List.ivdProgHashSHA256.@id",
				Matchers.equalTo("0075"));
	}

	public static void assertAdviceRepairComplete_OCU3Audi(String testname, String putVC, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse advice = Advice_OCU3_Audi.postRequestForAdvice(testname, putVC, vin, campaign, criterion);
		advice.assertThat().statusCode(200);
		advice.and().body("repairAdvice.adviceCode", Matchers.equalTo("REPAIR_COMPLETE"));
		advice.and().assertThat().body("repairAdvice.ivdProgHashSHA256List.ivdProgHashSHA256.@id",
				Matchers.equalTo("0075"));
	}

	public static void assertAdviceIncorrectCampaign_OCU3_Audi(String testname, String putVC, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse advice = Advice_OCU3_Audi.postRequestForAdvice(testname, putVC, vin, campaign, criterion);
		advice.assertThat().statusCode(200);
		advice.and().body("repairAdvice.adviceCode", Matchers.equalTo("ABORT_REPAIR"));
		advice.and().assertThat().header("X-Warning", "Incorrect Campaign Progress");
	}
	
	public static void assertAdviceAbortRepairImplausible_OCU3_Audi(String testname, String putVC, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse advice= Advice_OCU3_Audi.postRequestForAdvice(testname, putVC, vin, campaign, criterion);
		advice.assertThat().statusCode(200);
		advice.and().body("repairAdvice.adviceCode", Matchers.equalTo("ABORT_REPAIR"));
		advice.and().assertThat().header("X-Warning", "Implausible vehicle configuration found");
	}
}
