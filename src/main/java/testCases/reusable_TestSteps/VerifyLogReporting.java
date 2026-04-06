package testCases.reusable_TestSteps;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.SetDateTime;
import testCases.utils.proxySettings;

public class VerifyLogReporting extends JWT{
	public static ValidatableResponse logReportForPackges(String testname, String campaign) throws Exception {
		JWT.GenerateJWTToken();
		SetDateTime.setDateAndTime();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.urlEncodingEnabled=false;
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWTToken)
		.header("Accept","application/json")
		.header("Accept-Encoding","gzip, deflate")
		.header("Content-Type","application/json");
		ValidatableResponse response = request.when()
				.queryParam("page",0)
				.queryParam("rows", 10)
				.queryParam("paginate", false)
				.queryParam("resourceName", "packages")
				.queryParam("returnCode", 400)
				.queryParam("campaignNumber",campaign)
				.queryParam("status", "Error")
				.queryParam("from", SetDateTime.logTime1)
				.queryParam("until",SetDateTime.logTime2)
				.get("/onup/api/bs/oru/v1/"+brand+"/requestlog/overview").then().log().all();
		return response;
		}

	public static void assertlogReport(String testname, String campaign) throws Exception {
		ValidatableResponse abort = VerifyLogReporting.logReportForPackges(testname, campaign);
		abort.assertThat().statusCode(200);
	}	
}
