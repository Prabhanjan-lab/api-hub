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

public class PutVC_RepairNecessary_MIB3VW extends RegisterAndGetOAUTH{

	static boolean progHashTrue=true;
	public static ValidatableResponse putResquestForPutVc(String testname, String vin) throws IOException{
		
		String filePath = "resources/Payload_Xmls/MIB3_VW/MIB3VW_PUTVC_RepairNecessary.xml"; // Read the XML content from the file

		if(PutVC_RepairNecessary_MIB3VW.progHashTrue) {
			filePath = "resources/Payload_Xmls/MIB3_VW/MIB3VW_PUTVC_RepairNecessary.xml";
		} else {
			filePath = "resources/Payload_Xmls/MIB3_VW/MIB3VW_PUTVC_RepairNecessary_NoProgHash.xml";
		}
		String PUTVC_MIB3RepairNecessary= new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		TestVariables.setPutVCHash();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + OAuthToken)
			.header("Accept", "application/xml","application/vnd.vwg.mbb.genericError_v1_0_2+xml")
			.header("Content-Type","application/xml","application/vnd.vwg.mbb.onlineUpdateVehicleConfiguration_v2_1_0+xml")
			.header("X-Vin",vin);
		ValidatableResponse response = request.body(PUTVC_MIB3RepairNecessary).when().put("/mbb/vehicleConfiguration/v1/vehicleConf/"+PutVCHash).then().log().all();
		return response;
	}
	
	public static void assertPutVC_MIB3VW(String testname, String vin) throws IOException {
		PutVC_RepairNecessary_MIB3VW.progHashTrue = true;
		ValidatableResponse putvc= PutVC_RepairNecessary_MIB3VW.putResquestForPutVc(testname, vin);
		putvc.assertThat().statusCode(200);
	}

	public static void assertPutVC_MIB3VW_NoProgHash(String testname, String vin) throws IOException {
		//For non UNECE Flash File
		PutVC_RepairNecessary_MIB3VW.progHashTrue = false;
		ValidatableResponse putvc= PutVC_RepairNecessary_MIB3VW.putResquestForPutVc(testname, vin);
		putvc.assertThat().statusCode(200);
	}
}
