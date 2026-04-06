package testCases.testStep_VetoAdvice.TM1_VW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class vetoAdvice_TM1VW extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse vetoAdvice(String testname, String vin, String campaign, String criterion) throws IOException {
		
		String filePath = "resources/Payload_Xmls/vetoadvice.xml"; // Read the XML content from the file
		String vetoadvice= new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI= BaseURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Accept-Charset","UTF-8")
			.header("Authorization","Bearer " +OAuthToken)
			.header("Content-Type","application/xml",
				"application/vnd.vwg.mbb.onlineUpdateVetoRepairAdviceRequest_v1_1_0+xml")
			.header("X-Vin",vin)
			.header("X-Brand","VW")
			.header("Accept","application/xml", 
				"application/vnd.vwg.mbb.onlineUpdateRepairAdviceResponse_v2_1_0+xml",
				"application/vnd.vwg.mbb.onlineUpdateError_v1_1_0+xml",
				"application/vnd.vwg.mbb.genericError_v1_1_1+xml");
		ValidatableResponse response = request.when().queryParam("lang","de_DE").queryParam("vc",PutVCHash).queryParam("timestamp","2016-04-28T09:00:00Z").
				body(vetoadvice).post("/mbb/uota/v1/advice/campaign/"+campaign+"/version/"+criterion+"/advice").then().log().all();
		return response;
	}
	
	public static void assertVetoAdvice_TM1VW(String testname, String vin, String campaign, String criterion) throws IOException {
		ValidatableResponse vetoadvice=vetoAdvice_TM1VW.vetoAdvice(testname, vin, campaign, criterion);
		vetoadvice.assertThat().statusCode(200);
		vetoadvice.and().assertThat().body("repairAdvice.adviceCode", Matchers.equalTo("REPAIR_NECESSARY"));
	}
}


