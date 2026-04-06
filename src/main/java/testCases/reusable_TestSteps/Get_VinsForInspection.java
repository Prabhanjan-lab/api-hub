package testCases.reusable_TestSteps;

import java.io.IOException;

import org.junit.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class Get_VinsForInspection extends JWT {

	public static Response VinsForInspection(String testname,String campaign,String criterion, String batchName) throws IOException {
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String TNR = ReadTestParameters.getAttributeValue(testname, "TNR");
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		request.queryParam("flashIdentifier", TNR);
		request.queryParam("search", "page%3D0$count%3D10");
		// name values for Default batch - RGVmYXVsdA== , first Manual batch - TU1N and auto
		// approved batch - QUFB, Second approved batch - QkJC, Third approved batch- Q0ND
		// first manual approved batch - Q0ND
		request.queryParam("name", batchName);
		Response response = request.when()
				.get("/onup/api/bs/oru/v1/" + brand + "/oru/campaign/" + campaign + "/criterion/" + criterion + "/vins")
				.then().log().all().extract().response();
		return response;
	}

	public static void assertVinApprovedStatusForInspection(String testname,String vin,String campaign,String criterion, String batchName) throws Exception {
		Response recallApproval = Get_VinsForInspection.VinsForInspection(testname, campaign, criterion, batchName);
		JsonPath recallApprovalStatus = recallApproval.jsonPath();
		for (int i = 0; recallApprovalStatus.getString("data[" + i + "].code") != null; i++) {
			if(recallApprovalStatus.getString("data[" + i + "].code").equals(vin)) {
			Assert.assertEquals( "APPROVED",recallApprovalStatus.getString("data[" + i + "].recallApproval"));
		}
			else  {
				continue;
				}
		}
	}
	public static void assertVinNotApprovedStatusForInsepection(String testname,String vin,String campaign,String criterion, String batchName) throws Exception {
		Response recallApproval = Get_VinsForInspection.VinsForInspection(testname, campaign, criterion, batchName);
		JsonPath recallApprovalStatus = recallApproval.jsonPath();
		for (int i = 0; recallApprovalStatus.getString("data[" + i + "].code") != null; i++) {
			if(recallApprovalStatus.getString("data[" + i + "].code").equals(vin)) {
			Assert.assertEquals("NOT_APPROVED",recallApprovalStatus.getString("data[" + i + "].recallApproval") );
		}
			else {
				continue;
			}
		}
	}
	public static void assertApprovedStatementTestVehicle(String testname,String vin,String campaign,String criterion, String batchName) throws Exception {
		Response recallApproval = Get_VinsForInspection.VinsForInspection(testname,campaign,criterion, batchName);
		JsonPath recallApprovalStatus = recallApproval.jsonPath();
		for (int i = 0; recallApprovalStatus.getString("data[" + i + "].code") != null; i++) {
			if(recallApprovalStatus.getString("data[" + i + "].code").equals(vin)) {
				Assert.assertEquals("TEST_VEHICLE" ,recallApprovalStatus.getString("data[" + i + "].recallApproval"));
			}
				else {
					continue;
					}
			}
	}
	
	public static void assertVinInBatch(String testname, String vin, String campaign, String criterion, String batchName) throws Exception{
		Response recallApproval = Get_VinsForInspection.VinsForInspection(testname,campaign,criterion, batchName);
		JsonPath recallApprovalStatus = recallApproval.jsonPath();
		for (int i = 0; recallApprovalStatus.getString("data[" + i + "].code") != null; i++) {
				Assert.assertEquals(vin,recallApprovalStatus.getString("data[" + i + "].code"));
			}
		
	}
}