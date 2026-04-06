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

public class DefaultBatchStatus extends JWT{
	public static ValidatableResponse GetDefaultBatchStatus(String testname,String campaign,String criterion) throws IOException {
		JWT.GenerateJWTToken();
//		ArrayList<String> id= MoveVinToDefaultBatch.DefaultBatch(testname);
//		String charge = String.join("-", id);
		
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body("[\"Default\"]");
		ValidatableResponse response = request.when().post("/onup/api/bs/oru/v1/"+brand+"/charge/"+campaign+"/"+criterion+"/chargeStatusUpdate").then().log().all();
		
		return response;
		}
	
	public static void assertDefaultBatchStatusReleased(String testname,String campaign,String criterion) throws IOException {
		ValidatableResponse status= DefaultBatchStatus.GetDefaultBatchStatus(testname,campaign,criterion);
		status.assertThat().statusCode(200);
		status.and().body("chargeStatus",Matchers.contains("RELEASED"));
		
	}
	public static void assertDefaultBatchStatusCancelled(String testname,String campaign,String criterion) throws IOException, Throwable {
		Thread.sleep(10000);
		ValidatableResponse status= DefaultBatchStatus.GetDefaultBatchStatus(testname,campaign,criterion);
		status.assertThat().statusCode(200);
		status.and().body("chargeStatus",Matchers.contains("CANCELED"));
		
	}
	public static void assertDefaultBatchStatusCreated(String testname,String campaign,String criterion) throws IOException {
		ValidatableResponse status= DefaultBatchStatus.GetDefaultBatchStatus(testname,campaign,criterion);
		status.assertThat().statusCode(200);
		status.and().body("chargeStatus",Matchers.contains("CREATED"));
		
	}
		
}
