package testCases.testStep_PutVC.mIB3_VW;

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

public class PutVC_RepairComplete_MIB3VW_ODP extends RegisterAndGetOAUTH{
	public static ValidatableResponse putResquestForPutVc(String testname, String vin) throws IOException{
		
		String filePath = "resources/Payload_Xmls/MIB3_VW_ODP/MIB3VW_ODP_PUTVC_RepairComplete.xml"; // Read the XML content from the file
		String PUTVC_MIB3RepairComplete= new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		TestVariables.setPutVCHash();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + OAuthToken)
			.header("Accept", "application/xml","application/vnd.vwg.mbb.genericError_v1_0_2+xml")
			.header("Content-Type","application/xml","application/vnd.vwg.mbb.onlineUpdateVehicleConfiguration_v2_1_0+xml")
			.header("X-Vin",vin);
		ValidatableResponse response = request.body(PUTVC_MIB3RepairComplete).when().put("/mbb/vehicleConfiguration/v1/vehicleConf/"+PutVCHash).then().log().all();
		return response;
	}
	
	public static void assertPutVC_MIB3VW_ODP(String testname, String vin) throws IOException {
		ValidatableResponse putvc= PutVC_RepairComplete_MIB3VW_ODP.putResquestForPutVc(testname, vin);
		putvc.assertThat().statusCode(200);
	}
}
