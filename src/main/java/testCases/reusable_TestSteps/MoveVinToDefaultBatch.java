package testCases.reusable_TestSteps;

import java.io.IOException;
import java.util.ArrayList;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class MoveVinToDefaultBatch extends JWT{
public static ArrayList<String> DefaultBatch(String testname,String campaign,String criterion) throws IOException {
	JWT.GenerateJWTToken();
	String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
	String brand=ReadTestParameters.getAttributeValue(testname, "brand");
	Environments.setEnvironmentURL();
	RestAssured.baseURI= Environments.OUDMockURL;
	proxySettings.proxy();
	RequestSpecification request = RestAssured.given().log().all();
	request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
	Response body = request.when().get("/onup/api/bs/oru/v1/"+brand+"/charge/"+campaign+"/"+criterion+"/"+TNR).then().log().all().extract().response();
//	String response2=body.getBody().asString();		
	ArrayList<String> jsonvalue=body.then().extract().path("data.id");
	return jsonvalue;
	}
public static String Vehicles(String testname,String campaign,String criterion) throws IOException {
	ArrayList<String> id= MoveVinToDefaultBatch.DefaultBatch(testname,campaign,criterion);
	String charge = String.join("-", id);
	Environments.setEnvironmentURL();
	String brand=ReadTestParameters.getAttributeValue(testname, "brand");
	RestAssured.baseURI= Environments.OUDMockURL;
	proxySettings.proxy();
	RequestSpecification request = RestAssured.given().log().all();
	request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
	.body("{ \""+criterion+"\": \"01\", \"chargeId\": \""+charge+"\" }");
	Response response = request.when().post("/onup/api/bs/oru/v1/"+brand+"/charge/filteredVehicles").then().log().all().extract().response();
//	String body = response.getBody().asString();
	String transid = response.then().extract().path("transactionalId");
	return transid;
	}
public static String Draft(String testname,String campaign,String criterion) throws IOException {
	String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
	String brand=ReadTestParameters.getAttributeValue(testname, "brand");
	Environments.setEnvironmentURL();
	RestAssured.baseURI= Environments.OUDMockURL;
	proxySettings.proxy();
	RequestSpecification request = RestAssured.given().log().all();
	request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
	.body("{ \"criterionId\": \""+criterion+"\", \"campaignNumber\": \""+campaign+"\", \"name\": \"name\", \"target\": \"1\", \"flashMediumIdentifier\": \""+TNR+"\" }");
	Response body = request.when().put("/onup/api/bs/oru/v1/"+brand+"/charge/draft").then().log().all().extract().response();
//	String res1=body.getBody().asString();
	String res= body.then().extract().path("id");
	return res;
	}
public static ValidatableResponse Charge(String testname,String campaign,String criterion) throws IOException {
	String brand = ReadTestParameters.getAttributeValue(testname, "brand");
	String amId= ReadTestParameters.getAttributeValue(testname, "amId");
	String amCode= ReadTestParameters.getAttributeValue(testname, "amCode");
	String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
	String flashMedium= ReadTestParameters.getAttributeValue(testname, "flashMedium");
	String transactionalId= MoveVinToDefaultBatch.Vehicles(testname,campaign,criterion);
	String draftId= MoveVinToDefaultBatch.Draft(testname,campaign,criterion);
	Environments.setEnvironmentURL();
	RestAssured.baseURI= Environments.OUDMockURL;
	proxySettings.proxy();
	RequestSpecification request = RestAssured.given().log().all();
	request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
	.body("{ \"pKNRangeFrom\": \"{}\", \"pKNRangeTill\": \"{}\", \"selectedVehicles\": [ { \"batchName\": \"Vehicle Pool\", \"batchAction\": \"NEW\", \"batchGroupId\": null, \"flashIdentifier\": null } ], \"flashmedium\": { \"amCode\": \""+amCode+"\", \"amId\": \""+amId+"\", \"flashIdentifier\": \""+TNR+"\", \"tnr\": \""+TNR+"\", \"status\": null, \"revision\": \"1\", \"name\": \""+flashMedium+"\" }, \"campaignId\": \""+campaign+"\", \"campaignTitle\": \""+campaign+"\", \"criterionId\": \""+criterion+"\", \"criterionTitle\": \""+criterion+"\", \"name\": \"Default\", \"manual\": true, \"chargeId\": \""+draftId+"\", \"newName\": \"Default\", \"default\": true, \"oldName\": \"Default\", \"target\": \"\", \"chargeStatus\": \"created\", \"predecessor\": \"First automatically approved batch\", \"draftChargeId\": \""+draftId+"\", \"pknNumberFilter\": null, \"transactionalId\": \""+transactionalId+"\" }");
	ValidatableResponse response = request.when().put("/onup/api/bs/oru/v1/"+brand+"/charge").then().log().all();
	return response;
	}
public static void assertMoveVinToBatch(String testname,String campaign,String criterion) throws IOException {
	ValidatableResponse response= MoveVinToDefaultBatch.Charge(testname,campaign,criterion);
	response.assertThat().statusCode(204);
	System.out.println("Vin moved to Default Batch");
	}

}
