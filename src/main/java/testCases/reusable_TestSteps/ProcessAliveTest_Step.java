package testCases.reusable_TestSteps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class ProcessAliveTest_Step extends Environments{
	
public static ValidatableResponse postRequestForProcessAlive() throws IOException {
		
		String filePath = "resources/Payload_Xmls/ProcessAliveTest.xml"; // Read the XML content from the file
		String payload= new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		ValidatableResponse response =  request.body(payload).when().post("/ia/OTASoftwareInformationService/V1").then().log().all();
		return response;	
	}
		
			
public static void assertProcessAliveTest() throws IOException {	
	ValidatableResponse Response= ProcessAliveTest_Step.postRequestForProcessAlive();
	Response.assertThat().statusCode(200);
}

}
