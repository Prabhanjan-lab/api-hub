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

public class CreateAutoApprovedBatch extends JWT{
	public static String draftId;
	public static String AutoApprovedBatchDraft(String testname, String campaign, String criterion, String batchName, String predecessorValue, String targetValue, String importerValue, String mbtValue, String pknValue) throws IOException {
		JWT.GenerateJWTToken();
		String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"criterionId\": \""+criterion+"\", \"campaignNumber\": \""+campaign+"\", \"name\": \""+batchName+"\", \"target\": \""+targetValue+"\", \"flashMediumIdentifier\": \""+TNR+"\" }");
		Response body = request.when().put("/onup/api/bs/oru/v1/"+brand+"/charge/draft").then().log().all().extract().response();
		String res1=body.getBody().asString();
		draftId= body.then().extract().path("id");
		return draftId;
		}
	
	public static ValidatableResponse DraftFilter(String testname, String targetValue, String importerValue, String mbtValue, String pknValue) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"pknNumberFilter\": "+pknValue+", \"partNumberFilter\": null, \"importerFilter\": "+importerValue+", \"mbtFilter\": "+mbtValue+", \"target\": \""+targetValue+"\" }");
		ValidatableResponse body = request.when().post("/onup/api/bs/oru/v1/"+brand+"/charge/draft/"+draftId).then().log().all();
		return body;
		
	}
	
	public static String FilteredVehicles(String testname) throws IOException {
		Environments.setEnvironmentURL();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"originalChargeId\":null, \"criterionId\": \"01\", \"chargeId\": \""+draftId+"\" }");
		Response filteredVehicle = request.when().post("/onup/api/bs/oru/v1/"+brand+"/charge/filteredVehicles").then().log().all().extract().response();
//		String body = filteredVehicle.getBody().asString();
		String transid = filteredVehicle.then().extract().path("transactionalId");
		return transid;
	}
	
	public static ValidatableResponse Charge(String testname,String campaign, String criterion, String batchName, String predecessorValue, String targetValue, String importerValue, String mbtValue, String pknValue) throws IOException {
		draftId= CreateAutoApprovedBatch.AutoApprovedBatchDraft(testname, campaign, criterion, batchName, predecessorValue, targetValue, importerValue, mbtValue, pknValue);
		ValidatableResponse Draftfilter=CreateAutoApprovedBatch.DraftFilter(testname, targetValue, importerValue, mbtValue, pknValue);
		Draftfilter.assertThat().statusCode(201);
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String amId= ReadTestParameters.getAttributeValue(testname, "amId");
		String amCode= ReadTestParameters.getAttributeValue(testname, "amCode");
		String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
		String flashMedium= ReadTestParameters.getAttributeValue(testname, "flashMedium");
		String transactionalId = CreateAutoApprovedBatch.FilteredVehicles(testname);
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"partNumberFilter\": \"\", \"importerFilter\": "+importerValue+", \"pKNRangeFrom\": \"{}\", \"pKNRangeTill\": \"{}\", \"selectedVehicles\": [ { \"batchName\": \"Vehicle Pool\", \"batchAction\": \"NEW\", \"batchGroupId\": null, \"flashIdentifier\": \""+TNR+"\" } ], \"flashmedium\": { \"amCode\": \""+amCode+"\", \"amId\": \""+amId+"\", \"flashIdentifier\": \""+TNR+"\", \"tnr\": \""+TNR+"\", \"status\": \"CREATED\", \"revision\": \"1\", \"name\": \""+flashMedium+"\" }, \"campaignId\": \""+campaign+"\", \"campaignTitle\": \""+campaign+"\", \"criterionId\": \"01\", \"criterionTitle\": \"01\", \"manual\": false, \"predecessor\": "+predecessorValue+", \"target\": \""+targetValue+"\", \"newName\": \""+batchName+"\", \"draftChargeId\": \""+draftId+"\", \"approved\": true, \"pknNumberFilter\": "+pknValue+", \"transactionalId\": \""+transactionalId+"\" }");
		ValidatableResponse charge = request.when().post("/onup/api/bs/oru/v1/"+brand+"/charge").then().log().all();
		return charge;
		}
		
	
	public static void assertAutoApprovedBatchCreation(String testname,String campaign,String criterion, String batchName, String predecessorValue, String targetValue, String importerValue, String mbtValue, String pknValue) throws IOException {
		ValidatableResponse response= CreateAutoApprovedBatch.Charge(testname, campaign, criterion, batchName, predecessorValue, targetValue, importerValue, mbtValue, pknValue);
		response.assertThat().statusCode(201);
		System.out.println("Auto Approved Batch Created and Vin moved to the Batch.");
		}
	
	public static ValidatableResponse ChargeEdit(String testname,String campaign, String criterion, String previousBatchName, String batchName, String predecessorValue, String targetValue, String importerValue, String mbtValue, String pknValue) throws IOException {
		draftId= CreateAutoApprovedBatch.AutoApprovedBatchDraft(testname, campaign, criterion, batchName, predecessorValue, targetValue, importerValue, mbtValue, pknValue);
		ValidatableResponse Draftfilter=CreateAutoApprovedBatch.DraftFilter(testname, targetValue, importerValue, mbtValue, pknValue);
		Draftfilter.assertThat().statusCode(201);
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String amId= ReadTestParameters.getAttributeValue(testname, "amId");
		String amCode= ReadTestParameters.getAttributeValue(testname, "amCode");
		String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
		String flashMedium= ReadTestParameters.getAttributeValue(testname, "flashMedium");
		String transactionalId = CreateAutoApprovedBatch.FilteredVehicles(testname);
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"partNumberFilter\": \"\",\"importerFilter\": "+importerValue+", \"pKNRangeFrom\": \"{}\", \"pKNRangeTill\": \"{}\", \"selectedVehicles\": [ { \"batchName\": \""+previousBatchName+"\", \"batchAction\": \"NEW\", \"batchGroupId\": null, \"flashIdentifier\": \""+TNR+"\" } ], \"flashmedium\": { \"amCode\": \""+amCode+"\", \"amId\": \""+amId+"\", \"flashIdentifier\": \""+TNR+"\", \"tnr\": \""+TNR+"\", \"status\": \"CREATED\", \"revision\": \"1\", \"name\": \""+flashMedium+"\" }, \"campaignId\": \""+campaign+"\", \"campaignTitle\": \""+campaign+"\", \"criterionId\": \"01\", \"criterionTitle\": \"01\", \"manual\": false, \"predecessor\": \""+predecessorValue+"\", \"target\": \""+targetValue+"\", \"name\":\""+previousBatchName+"\", \"newName\": \""+batchName+"\", \"draftChargeId\": \""+draftId+"\", \"approved\": true, \"pknNumberFilter\": "+pknValue+", \"transactionalId\": \""+transactionalId+"\" }");
		ValidatableResponse charge = request.when().put("/onup/api/bs/oru/v1/"+brand+"/charge").then().log().all();
		return charge;
		}
		
	
	public static void assertAutoApprovedBatchEdit(String testname,String campaign,String criterion, String previousBatchName, String batchName, String predecessorValue, String targetValue, String importerValue, String mbtValue, String pknValue) throws IOException {
		ValidatableResponse response= CreateAutoApprovedBatch.ChargeEdit(testname, campaign, criterion,previousBatchName, batchName, predecessorValue, targetValue, importerValue, mbtValue, pknValue);
		response.assertThat().statusCode(204);
		System.out.println("Batch Edited successfully.");
		}
	
}
