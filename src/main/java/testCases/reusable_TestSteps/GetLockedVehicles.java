package testCases.reusable_TestSteps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class GetLockedVehicles extends JWT{

	public static Response BlockedVins(String testname, String vin, String campaign, String criterion,String batch) throws IOException {

		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/campaign/"+campaign+"/criterion/"+criterion+"/charge/"+batch+"/blockedVehicles").then().log().all().extract().response();
		return response;
	}
	
	public static void assertVinBlocked(String testname, String vin, String campaign, String criterion,String batch) throws IOException {
		Response blockedVins =GetLockedVehicles.BlockedVins(testname, vin, campaign, criterion, batch);
		int scode=	blockedVins.statusCode();
		assertEquals(scode, 200);
		JSONObject jsonObject = new JSONObject(blockedVins.asString());
		JSONArray dataArray = jsonObject.getJSONArray("data");
		// Iterate over the JSON array
		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject obj = dataArray.getJSONObject(i);          	
			String lockedVIN=	obj.getString("vin");
	Assert.assertTrue(lockedVIN.equals(vin));
		}
	}

	public static void assertBlockedVinWithFilter_Code36(String testname, String vin, String campaign, String criterion,String batch) throws IOException {
		Response blockedVins =GetLockedVehicles.BlockedVins(testname, vin, campaign, criterion, batch);
		int scode=	blockedVins.statusCode();
		assertEquals(scode, 200);
		JSONObject jsonObject = new JSONObject(blockedVins.asString());
		JSONArray dataArray = jsonObject.getJSONArray("data");
		// Iterate over the JSON array
		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject obj = dataArray.getJSONObject(i);          	
			String lockedVIN=	obj.getString("vin");
			if(	lockedVIN.equals(vin)) {
				String status = obj.getString("campaignProcessingStatus");
				int vcso = obj.getInt("vcso");
				Assert.assertEquals(status, "CAMPAIGN_BLOCKED");
				Assert.assertEquals(vcso, 36);
				System.out.println(vin+" blocked reason "+vcso+" - "+status);
			}
		}
	}
	
	public static void assertBlockedVinWithFilter_Code46(String testname, String vin, String campaign, String criterion,String batch) throws IOException {
		Response blockedVins =GetLockedVehicles.BlockedVins(testname, vin, campaign, criterion, batch);
		int scode=	blockedVins.statusCode();
		assertEquals(scode, 200);
		JSONObject jsonObject = new JSONObject(blockedVins.asString());
		JSONArray dataArray = jsonObject.getJSONArray("data");
		// Iterate over the JSON array
		for (int i = 0; i < dataArray.length(); i++) {
			JSONObject obj = dataArray.getJSONObject(i);          	
			String lockedVIN=	obj.getString("vin");
			if(	lockedVIN.equals(vin)) {
				String status = obj.getString("campaignProcessingStatus");
				int vcso = obj.getInt("vcso");
				Assert.assertEquals(status, "VEHICLE_ABORTED_ITS_OWN_CAMPAIGN");
				Assert.assertEquals(vcso, 46);
				System.out.println(vin+" blocked reason "+vcso+" - "+status);
			}
		}
	}


	public static void assertBlockedVinsCount(String testname, String vin, String campaign, String criterion,String batch,int expVinCount) throws IOException {
		Response bVins =GetLockedVehicles.BlockedVins(testname, vin, campaign, criterion, batch);
		int scode=	bVins.statusCode();
		assertEquals(scode, 200);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(bVins.asString());
		// Extract recordsTotal
		int lockedVinCount = rootNode.get("recordsTotal").asInt();
		Assert.assertEquals(lockedVinCount,expVinCount);
		System.out.println("Locked Vins Count : "+lockedVinCount);
	}

	public static void FilteringBlockedVINs(String testname, String vin, String campaign, String criterion,String batch,int filterCode,int expVinCount) throws IOException {
		Response res =GetLockedVehicles.BlockedVins(testname, vin, campaign, criterion, batch);
		int scode=	res.statusCode();
		assertEquals(scode, 200);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(res.asString());
		JsonNode dataArray = rootNode.get("data");
		int count = 0;
		for (JsonNode item : dataArray) {
			if (item.get("vcso").asInt() == filterCode) {
				count++;
			}
		}
		System.out.println("Total vins of VCSO_46 filter are : " + count);
		Assert.assertEquals(count, expVinCount);
	}

}
