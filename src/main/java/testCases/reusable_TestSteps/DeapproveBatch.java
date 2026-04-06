package testCases.reusable_TestSteps;
import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class DeapproveBatch extends JWT{

	public static ValidatableResponse DeapproveDefaultBatch(String testname,String campaign,String criterion,String batchName) throws IOException {
		JWT.GenerateJWTToken();
		
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String amId= ReadTestParameters.getAttributeValue(testname, "amId");
		String amCode= ReadTestParameters.getAttributeValue(testname, "amCode");
		String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"data\": [ { \"name\": \""+batchName+"\", \"approved\": false } ] }");
		ValidatableResponse response = request.when().put("/onup/api/bs/oru/v1/"+brand+"/oru/"+campaign+"/criterion/"+criterion+"/flashmedium/"+amCode+"/"+amId+"/"+TNR+"/release").then().log().all();
		return response;
		}
	
	public static void assertDeapproveBatch(String testname,String campaign,String criterion,String batchName) throws Exception {
		Response chargeStatusUpdate = ApproveBatch.getChargeStatusUpdate(testname,campaign, criterion);
		String chargeStatus= chargeStatusUpdate.getBody().jsonPath().getString("data.approved");
		
		if(chargeStatus.contains("true")) {
			ValidatableResponse batch = DeapproveBatch.DeapproveDefaultBatch(testname, campaign, criterion, batchName);
			batch.assertThat().statusCode(204);
		}
		else {
			System.out.println("Charge is already deapproved");
		}	
	}
}
