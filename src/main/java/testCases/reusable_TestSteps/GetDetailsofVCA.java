package testCases.reusable_TestSteps;

import static org.junit.Assert.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class GetDetailsofVCA extends JWT{

	public static ValidatableResponse getVCADetails(String testname, String campaign, String vin, String criterion) throws Exception {
		JWT.GenerateJWTToken();
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/vehicle/"+vin+"/campaign/"+campaign+"/criterion/"+criterion+"/details").then().log().all();
		return response;
	}
	
	public static void assertVCADetails(String testname, String campaign, String vin, String criterion, String expectedDetail, String expectedOperationCode) throws Exception {
		ValidatableResponse VCADetails = GetDetailsofVCA.getVCADetails(testname, campaign, vin, criterion);
		VCADetails.assertThat().statusCode(200);
		Response response = VCADetails.extract().response();
		String jsonString = response.asString();

		// Convert to JSONObject
		JSONObject jsonObj = new JSONObject(jsonString);
		JSONArray dataArray = jsonObj.getJSONArray("data");

		// Loop through and assert operationCode
		boolean found = false;

		for (int i = 0; i < dataArray.length(); i++) {
		    JSONObject item = dataArray.getJSONObject(i);
		    String operationCode = item.getString("operationCode");

		    if (expectedOperationCode.equals(operationCode)) {
		        String actionDetails = item.getString("actionDetails");
		        if (expectedDetail.equals(actionDetails)) {
		            found = true;
		            break;
		        }
		    }
		}

		assertTrue("Expected operationCode M3000 with specific actionDetails not found", found);
	}
	
	public static void assertVCSOfromVCADetails(String testname, String campaign, String vin, String criterion, String expectedVCSOStatus, String expectedOperationCode) throws Exception {
		ValidatableResponse VCADetails = GetDetailsofVCA.getVCADetails(testname, campaign, vin, criterion);
		VCADetails.assertThat().statusCode(200);
		Response response = VCADetails.extract().response();
		String jsonString = response.asString();

		// Convert to JSONObject
		JSONObject jsonObj = new JSONObject(jsonString);
		JSONArray dataArray = jsonObj.getJSONArray("data");

		// Loop through and assert operationCode
		boolean found = false;
		for (int i = 0; i < dataArray.length(); i++) {
		    JSONObject item = dataArray.getJSONObject(i);
		    String operationCode = item.getString("operationCode");

		    if (expectedOperationCode.equals(operationCode)) {
		        String VCSOStatus = item.getString("code");
		        if (expectedVCSOStatus.equals(VCSOStatus)) {
		            found = true;
		            break;
		        }
		    }
		}

		assertTrue("Expected "+expectedOperationCode+ "with specific VCSO not found", found);
		
	}
}
