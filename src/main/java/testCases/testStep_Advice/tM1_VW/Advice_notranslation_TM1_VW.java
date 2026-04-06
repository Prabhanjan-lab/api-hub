package testCases.testStep_Advice.tM1_VW;

import java.io.IOException;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class Advice_notranslation_TM1_VW extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse postRequestForAdvice(String testname, String vin, String campaign, String criterion) throws IOException {
		Environments.setEnvironmentURL();
		RestAssured.baseURI= BaseURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + OAuthToken)
		.header("Accept","application/vnd.vwg.mbb.onlineUpdateRepairAdviceResponse_v2_1_0+xml","application/vnd.vwg.mbb.onlineUpdateError_v1_1_0+xml","application/vnd.vwg.mbb.genericError_v1_1_1+xml")
		.header("Content-Type","application/xml")
		.header("X-Brand","VW")
		.header("X-Vin",vin)
		.queryParam("lang", "fr_FR")
		.queryParam("vc",PutVCHash)
		.queryParam("timestamp","2016-04-28T09:00:00Z");
		ValidatableResponse response = request.when().post("/mbb/uota/v1/advice/campaign/"+campaign+"/version/"+criterion+"/advice").then().log().all();
		return response;
	}
	
	public static void assertAdviceAbortRepair_TM1_VW(String testname, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse advice= Advice_notranslation_TM1_VW.postRequestForAdvice(testname, vin, campaign, criterion);
		advice.assertThat().statusCode(200);
		advice.and().body("repairAdvice.adviceCode", Matchers.equalTo("ABORT_REPAIR"));
		advice.and().assertThat().header("X-Warning", "No translated campaign description found for HMI language fr_FR");
	}
	
}
