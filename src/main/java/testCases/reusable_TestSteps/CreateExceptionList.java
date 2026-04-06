package testCases.reusable_TestSteps;
 
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
 
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;
 
public class CreateExceptionList {
	public static Response createExceptionList(String testname) throws Exception {
		JWT.GenerateJWTToken();
		String vin = ReadTestParameters.getAttributeValue(testname, "vin");
		String filePath = "resources/Payload_Jsons/createExceptionList.json";
		String payload = new String(Files.readAllBytes(Paths.get(filePath)));
		payload= payload.replaceAll("VIN",vin);
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		Response response = request.when().body(payload).post("/onup/api/bs/oru/v1/"+brand+"/exceptionlist").then().log().all().extract().response();
		return response;
	}
	public static String assertVinCreationInExceptionList(String testname) throws Exception {
		Response list= CreateExceptionList.createExceptionList(testname);
		JsonPath allList=list.jsonPath();
		String id= allList.getString("id");
		return id;
		}	
}