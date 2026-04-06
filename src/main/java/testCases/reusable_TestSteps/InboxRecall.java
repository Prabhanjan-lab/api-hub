package testCases.reusable_TestSteps;
 
import java.io.IOException;
 
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;
 
public class InboxRecall extends JWT{
	public static ValidatableResponse getRecall(String testname, String recallId) throws IOException {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/recalls/inbox/"+recallId).then().log().all();
		return response;
		}
	public static void assertRecallInbox(String testname, String recallId) throws Exception {
		ValidatableResponse Recall= InboxRecall.getRecall(testname, recallId);
		Recall.assertThat().statusCode(200);
	}
 
}