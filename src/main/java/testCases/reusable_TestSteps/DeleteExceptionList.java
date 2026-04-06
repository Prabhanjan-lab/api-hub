package testCases.reusable_TestSteps;
 
import org.junit.Assert;
 
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;
 
public class DeleteExceptionList {
	public static Response deleteExceptionList(String testname,String id) throws Exception {
		JWT.GenerateJWTToken();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		Response response = request.when().delete("/onup/api/bs/oru/v1/"+brand+"/exceptionlist/"+id).then().log().all().extract().response();
		return response;
	}
	public static void assertVinDeletionInExceptionList(String testname,String id) throws Exception {
		Response list= DeleteExceptionList.deleteExceptionList(testname,id);
		int statusCode = list.getStatusCode();
		Assert.assertEquals("Status code is not 204", 204, statusCode);
		}
	}