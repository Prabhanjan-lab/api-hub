package testCases.reusable_TestSteps;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

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

public class GetVehicleAttributes {
	public static Response getVehicleAttributes(String testname, String vin) throws Exception  {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.queryParam("type","active");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/attributes").then().log().all().extract().response();
		return response;
	}
	public static void assertGetVehicleAttributes(String testname,String vin) throws Exception {
		Response response=GetVehicleAttributes.getVehicleAttributes(testname, vin);
		int statuscode=response.getStatusCode();
		assertEquals(200, statuscode, "Vehicle attributes retrieved");	
	}
	public static String GetVehicleAttributesPKN(String testname,String vin) throws Exception {
		Response response=GetVehicleAttributes.getVehicleAttributes(testname, vin);
		String pkn =response.then().extract().path("pkn");
		return pkn;
	}
	
	public static String GetVehicleAttributesVehicleDataSource(String testname,String vin) throws Exception {
		Response response=GetVehicleAttributes.getVehicleAttributes(testname, vin);
		String vds =response.then().extract().path("vehicleDataSource");
		return vds;
	}
	public static void assertPKNEmpty(String testname,String vin) throws Exception {
		String assertPKN=GetVehicleAttributes.GetVehicleAttributesPKN(testname,vin);
		assertEquals(null,assertPKN,"PKN is empty");
	}
	public static void assertPKNValue(String testname,String vin,String PKN) throws Exception {
		String assertPKN=GetVehicleAttributes.GetVehicleAttributesPKN(testname,vin);
		assertEquals(assertPKN,PKN,"PKN matches with the given value");
	}
	
	public static String GetVehicleAttributesAttendingPartnerValue(String testname,String vin) throws Exception {
		Response response=GetVehicleAttributes.getVehicleAttributes(testname, vin);
		String partner=	response.body().asString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(partner);
		String partnerKey = rootNode.path("attendingWholesaler").path("partnerKey").asText();
		return partnerKey;
	}
	
	public static void assertAttendingPartnerValue(String testname,String vin,String partner) throws Exception {
		String assertPartner=GetVehicleAttributes.GetVehicleAttributesAttendingPartnerValue(testname,vin);
		assertEquals(assertPartner,partner,"AttendingWholesaler partnerKey is not matches with the given value");
	}
	
	public static void assertAttendingPartnerValueNull(String testname,String vin) throws Exception {
		Response response=GetVehicleAttributes.getVehicleAttributes(testname, vin);
		String partner=	response.body().asString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(partner);
		JsonNode partnerKey = rootNode.path("attendingWholesaler").path("partnerKey");
		Assert.assertTrue("PartnerKey should be null", partnerKey.isNull());
 
	}
	
	public static String GetVehicleAttributesAttendingCountry(String testname,String vin) throws Exception {
		Response response=GetVehicleAttributes.getVehicleAttributes(testname, vin);
		String partner=	response.body().asString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(partner);
		String countryCode = rootNode.path("attendingWholesaler").path("countryCode").asText();
		return countryCode;
	}
	
	public static void assertAttendingCountry(String testname,String vin,String country) throws Exception {
		String assertcountry=GetVehicleAttributes.GetVehicleAttributesAttendingCountry(testname,vin);
		assertEquals(assertcountry,country,"AttendingCountry is not matches with the given value");
	}
	
	public static String GetVehicleAttributesAttendingWholesaler(String testname,String vin) throws Exception {
		Response response=GetVehicleAttributes.getVehicleAttributes(testname, vin);
		String partner=	response.body().asString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(partner);
		String wholeSaler = rootNode.path("attendingWholesaler").path("number").asText();
		return wholeSaler;
	}
	
	public static void assertAttendingWholesaler(String testname,String vin,String wholesaler) throws Exception {
		String assertWholesaler=GetVehicleAttributes.GetVehicleAttributesAttendingWholesaler(testname,vin);
		assertEquals(assertWholesaler,wholesaler,"AttendingWholesaler is not matches with the given value");
	}
}
