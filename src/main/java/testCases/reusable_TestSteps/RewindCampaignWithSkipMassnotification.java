package testCases.reusable_TestSteps;

import java.io.IOException;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class RewindCampaignWithSkipMassnotification extends JWT{

	public static ValidatableResponse rewind(String testname,String vin,String campaign,String criterion) throws IOException {
		JWT.GenerateJWTToken();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken)
		.header("Content-Type","application/json")
		.body("{ \"activity\" : { \"operationCode\" : \"\", \"description\" : \"\", \"referenceId\" :\"\" }, \"campaignInfo\" : { \"campaignId\" :  \""+campaign+"\", \"criterionId\" : \""+criterion+"\" }, \"error\" : { \"code\" : \"\", \"description\" : \"\" } }");
		ValidatableResponse response = request.when().post("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaign/"+campaign+"/criterion/"+criterion+"/rewind").then().log().all();
		return response;
	}
	
	public static ValidatableResponse activeCampaign(String testname,String vin) throws IOException {
		Environments.setEnvironmentURL();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("type","active");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaigns").then().log().all();
		return response;
	}

	public static void assertRewindCampaign(String testname,String vin,String campaign,String criterion) throws InterruptedException, IOException {
		ValidatableResponse rewindResponse= RewindCampaignWithSkipMassnotification.rewind(testname,vin,campaign,criterion);
		rewindResponse.assertThat().statusCode(200);
//		Thread.sleep(360000);
//		ValidatableResponse activeCampaign= RewindCampaignWithSkipMassnotification.activeCampaign(testname,vin);
//		activeCampaign.assertThat().statusCode(200);
//		activeCampaign.and().body("data.vin", Matchers.contains(vin));
//		activeCampaign.and().body("data.campaignId", Matchers.contains(campaign));
//		activeCampaign.and().body("data.status.code", Matchers.contains("5"));
	}
	
	
	public static ValidatableResponse scheduleCampaign(String testname,String vin) throws IOException {
		Environments.setEnvironmentURL();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("type","schedule");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaigns").then().log().all();
		return response;
	}
	
	public static void assertRewindCampaignToSchedule(String testname,String vin,String campaign,String criterion) throws Exception {
		SkipMassnotificationTrue.assertSkipMassnotificationTrue(testname,vin);
		ValidatableResponse rewindResponse= RewindCampaignWithSkipMassnotification.rewind(testname,vin,campaign,criterion);
		rewindResponse.assertThat().statusCode(200);
		/*ValidatableResponse scheduledCampaign= RewindCampaignWithSkipMassnotification.scheduleCampaign(testname);
		scheduledCampaign.assertThat().statusCode(200);
		activeCampaign.and().body("data.vin", Matchers.contains(ReadTestParameters.getVin(testname)));
		activeCampaign.and().body("data.campaignId", Matchers.contains(ReadTestParameters.getCampaign(testname)));
		activeCampaign.and().body("data.status.code", Matchers.contains("2"));*/
	}
}