package testCases.reusable_TestSteps;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class GetOverallFlashProcessingStatus extends JWT{
	
	public static ValidatableResponse getFalshOverallProcessingStatus(String testname, String campaign, String criterion) throws IOException {
		JWT.GenerateJWTToken();
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String amCode = ReadTestParameters.getAttributeValue(testname, "amCode");
		String amId = ReadTestParameters.getAttributeValue(testname, "amId");
		String TNR = ReadTestParameters.getAttributeValue(testname, "TNR");
		//String Revision = ReadTestParameters.getAttributeValue(testname, "criterion");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		ValidatableResponse response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/oru/processingstatus/overall/"+campaign+"/"+criterion+"/"+amCode+"/"+amId+"/"+TNR+"/"+criterion).then().log().all();
		return response;
	}
	
	public static void assertFlashProcessingStatus(String testname, String campaign, String criterion) throws Throwable {
	    long maxWaitMilliSec = 6 *60*1000; //6 min wait
	    long endTime = System.currentTimeMillis()+maxWaitMilliSec;
	    String actualResponse = "" ;
	    boolean completed = false;
		while (System.currentTimeMillis() <= endTime) {
	        ValidatableResponse response = GetOverallFlashProcessingStatus.getFalshOverallProcessingStatus(testname, campaign, criterion);
	        response.assertThat().statusCode(200);

	        actualResponse = response.extract().path("overallProgress.percentage").toString();
	        System.out.println("Current progress: " + actualResponse);

	        if ("100.0".equals(actualResponse)) {
	            System.out.println("Processing completed: 100.0");
	            completed = true;
	            break;
	        }

	        Thread.sleep(60000); // wait for 1 minutes
	    }
		if(!completed) {
		System.out.println("The max Wait Time is reached. Flash File have an issue to upload Completely."+ actualResponse);
		}
	}

}
