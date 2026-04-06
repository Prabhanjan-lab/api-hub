package testCases.reusable_TestSteps;
 
import org.junit.Assert;
 
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;
 
public class ExceptionList {
	public static Response getAllExceptionList(String testname) throws Exception {
		JWT.GenerateJWTToken();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/exceptionlist").then().log().all().extract().response();
		return response;
	}
	public static void assertVinNotInExceptionList(String testname, String vin) throws Exception {
		Response list= ExceptionList.getAllExceptionList(testname);
		JsonPath allList=list.jsonPath();
		for(int i = 0; allList.getString("data["+i+"].id")!=null; i++) {
			String exceptionListVIN = allList.get("data["+i+"].vin");
			Assert.assertNotEquals(exceptionListVIN,vin);
			}
		}
	}