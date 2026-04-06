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

public class CreateManualBatch1 extends JWT{
	public static String draftId;
	public static String targetValue="60";
	public static String ManaulBatchDraft(String testname,String campaign,String criterion) throws IOException {
		JWT.GenerateJWTToken();
		String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
		String firstCharge= ReadTestParameters.getAttributeValue(testname, "firstChargeName");
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"criterionId\": \""+criterion+"\", \"campaignNumber\": \""+campaign+"\", \"name\": \""+firstCharge+"\", \"target\": \""+targetValue+"\", \"flashMediumIdentifier\": \""+TNR+"\" }");
		Response body = request.when().put("/onup/api/bs/oru/v1/"+brand+"/charge/draft").then().log().all().extract().response();
//		String res1=body.getBody().asString();
		draftId= body.then().extract().path("id");
		return draftId;
		}
	
	public static ValidatableResponse DraftFilter(String testname) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String firstChargeImporterValue = ReadTestParameters.getAttributeValue(testname, "firstChargeImporterValue");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"pknNumberFilter\": null, \"partNumberFilter\": null, \"importerFilter\": \""+firstChargeImporterValue+"\", \"mbtFilter\": null, \"target\": \""+targetValue+"\" }");
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
		.body("{ \"criterionId\": \"01\", \"chargeId\": \""+draftId+"\" }");
		Response filteredVehicle = request.when().post("/onup/api/bs/oru/v1/"+brand+"/charge/filteredVehicles").then().log().all().extract().response();
//		String body = filteredVehicle.getBody().asString();
		String transid = filteredVehicle.then().extract().path("transactionalId");
		return transid;
	}
	
	public static ValidatableResponse Charge(String testname,String campaign,String criterion) throws IOException {
		draftId= CreateManualBatch1.ManaulBatchDraft(testname,campaign,criterion);
		ValidatableResponse Draftfilter=CreateManualBatch1.DraftFilter(testname);
		Draftfilter.assertThat().statusCode(201);
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String amId= ReadTestParameters.getAttributeValue(testname, "amId");
		String amCode= ReadTestParameters.getAttributeValue(testname, "amCode");
		String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
		String flashMedium= ReadTestParameters.getAttributeValue(testname, "flashMedium");
		String transactionalId = CreateManualBatch1.FilteredVehicles(testname);
		String firstCharge= ReadTestParameters.getAttributeValue(testname, "firstChargeName");
		String firstChargeImporterValue= ReadTestParameters.getAttributeValue(testname, "firstChargeImporterValue");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("{ \"partNumberFilter\": \"\",\"importerFilter\": \""+firstChargeImporterValue+"\", \"pKNRangeFrom\": \"{}\", \"pKNRangeTill\": \"{}\", \"selectedVehicles\": [ { \"batchName\": \"Vehicle Pool\", \"batchAction\": \"NEW\", \"batchGroupId\": null, \"flashIdentifier\": \""+TNR+"\" } ], \"flashmedium\": { \"amCode\": \""+amCode+"\", \"amId\": \""+amId+"\", \"flashIdentifier\": \""+TNR+"\", \"tnr\": \""+TNR+"\", \"status\": \"CREATED\", \"revision\": \"1\", \"name\": \""+flashMedium+"\" }, \"campaignId\": \""+campaign+"\", \"campaignTitle\": \""+campaign+"\", \"criterionId\": \"01\", \"criterionTitle\": \"01\", \"manual\": true, \"predecessor\": null, \"target\": \""+targetValue+"\", \"newName\": \""+firstCharge+"\", \"draftChargeId\": \""+draftId+"\", \"approved\": false, \"pknNumberFilter\": null, \"transactionalId\": \""+transactionalId+"\" }");
		ValidatableResponse charge = request.when().post("/onup/api/bs/oru/v1/"+brand+"/charge").then().log().all();
		return charge;
		}
	
	public static void assertManualBatchCreation(String testname,String campaign,String criterion) throws IOException {
		ValidatableResponse response= CreateManualBatch1.Charge(testname,campaign,criterion);
		response.assertThat().statusCode(201);
		System.out.println("Vin moved to Manual Batch1");
		}
}

