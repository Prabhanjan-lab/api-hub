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
 
public class GetChargeStatus extends JWT{
	public static Response getCharge(String testname, String campaign, String criterion, String batchName) throws Exception {
		JWT.GenerateJWTToken();
		String TNR=ReadTestParameters.getAttributeValue(testname, "TNR");
		String brand=ReadTestParameters.getAttributeValue(testname, "brand");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/charge/"+campaign+"/"+criterion+"/"+TNR).then().log().all().extract().response();
		return response;
	}
	public static void assertChargeCurrentValue(String testname, String campaign, String criterion,String currentvalue, String batchName) throws Exception {
		Response charge= GetChargeStatus.getCharge(testname, campaign, criterion, batchName);
		JsonPath currentchargestatus=charge.jsonPath();
		for(int i = 0; currentchargestatus.getString("data["+i+"].name")!=null; i++) {
			if(currentchargestatus.getString("data["+i+"].name").equalsIgnoreCase(batchName)) {
			String current =	currentchargestatus.getString("data["+i+"].current");	
			Assert.assertEquals(current,currentvalue);
			}
		}
	}
	public static void assertNumberOfReleasedVins(String testname, String campaign, String criterion, String count,String batchName) throws Exception {
		Response charge= GetChargeStatus.getCharge(testname, campaign, criterion, batchName);
		JsonPath currentchargestatus=charge.jsonPath();
		for(int i = 0; currentchargestatus.getString("data["+i+"].name")!=null; i++) {
			if(currentchargestatus.getString("data["+i+"].name").equalsIgnoreCase(batchName)) {
				String vins = currentchargestatus.getString("data["+i+"].numberOfVehicles");
				Assert.assertEquals(vins,count);
			}
		}
	}
	public static void assertChargeStatusReleased(String testname, String campaign, String criterion, String batchName) throws Exception {
		Response charge= GetChargeStatus.getCharge(testname, campaign, criterion, batchName);
		JsonPath currentchargestatus=charge.jsonPath();
		for(int i = 0; currentchargestatus.getString("data["+i+"].name")!=null; i++) {
			if(currentchargestatus.getString("data["+i+"].name").equalsIgnoreCase(batchName)) {
			String status =	currentchargestatus.getString("data["+i+"].status");
			Assert.assertTrue("RELEASED".equals(status) || "RELEASING".equals(status));
			}
		}
	}
	public static void assertChargeStatusNotReleased(String testname, String campaign, String criterion, String batchName) throws Exception {
		Response charge= GetChargeStatus.getCharge(testname, campaign, criterion, batchName);
		JsonPath currentchargestatus=charge.jsonPath();
		for(int i = 0; currentchargestatus.getString("data["+i+"].name")!=null; i++) {
			if(currentchargestatus.getString("data["+i+"].name").equalsIgnoreCase(batchName)) {
			String status =	currentchargestatus.getString("data["+i+"].status");
			Assert.assertFalse("RELEASED".equals(status) || "RELEASING".equals(status));
			}
		}
	}
	public static void assertChargeTargetValue(String testname, String campaign, String criterion, String batchName, String targetValue) throws Exception {
		Response charge= GetChargeStatus.getCharge(testname, campaign, criterion, batchName);
		JsonPath currentchargestatus=charge.jsonPath();
		for(int i = 0; currentchargestatus.getString("data["+i+"].name")!=null; i++) {
			if(currentchargestatus.getString("data["+i+"].name").equalsIgnoreCase(batchName)) {
			String target = currentchargestatus.get("data["+i+"].target");
			Assert.assertEquals(target, targetValue);
			}
		}
	}
	public static void assertChargeStatusCancelled(String testname, String campaign, String criterion, String batchName) throws Exception {
		Response charge= GetChargeStatus.getCharge(testname, campaign, criterion, batchName);
		JsonPath currentchargestatus=charge.jsonPath();
		for(int i = 0; currentchargestatus.getString("data["+i+"].name")!=null; i++) {
			if(currentchargestatus.getString("data["+i+"].name").equalsIgnoreCase(batchName)) {
				String status = currentchargestatus.getString("data["+i+"].status");
				Assert.assertTrue("CANCELED".equals(status) || "CANCELING".equals(status));
			}
		}
	}
}