package testCases.testStep_PutVC.oCU3_VW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.utils.Environments;
import testCases.utils.TestVariables;
import testCases.utils.proxySettings;

public class PutVC_ImplausibleVC_OCU3VW extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse putResquestForPutVc(String testname, String vin) throws IOException{
	
		String filePath = "resources/Payload_Xmls/OCU3_VW/OCU3_VW_PUTVC_ImplausibleVC.xml"; // Read the XML content from the file
		String PutVC_OCU3RepairNecessary= new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		TestVariables.setPutVC2Hash();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + OAuthToken)
			.header("Accept", "application/xml","application/vnd.vwg.mbb.genericError_v1_0_2+xml")
			.header("Content-Type","application/vnd.vwg.mbb.onlineUpdateVehicleConfiguration_v2_0_0+xml")
			.header("X-Vin",vin);
		ValidatableResponse response = request.body(PutVC_OCU3RepairNecessary).when().put("/mbb/vehicleConfiguration/v1/vehicleConf/"+PutVC2Hash).then().log().all();
		return response;
	}
	
	public static void assertPutVC_OCU3VW(String testname, String vin) throws IOException {
		ValidatableResponse putvc= PutVC_ImplausibleVC_OCU3VW.putResquestForPutVc(testname, vin);
		putvc.assertThat().statusCode(200);
	}

}
