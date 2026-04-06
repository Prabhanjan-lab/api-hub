package testCases.testStep_PutVC.oCU3_VW;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.oAuthTokenGeneration.TokenGenerationForCarportVins;
import testCases.utils.Environments;
import testCases.utils.TestVariables;
import testCases.utils.proxySettings;

public class PutVC_RepairNecessary_OCU3VW_UNECE_Carport extends RegisterAndGetOAUTH{

public static ValidatableResponse putResquestForPutVc(String testname, String vin) throws IOException{
	String filePath = "resources/Payload_Xmls/OCU3_VW_Carport/OCU3_VW_UNECE_Carport_PUTVC_RepairNecessary.xml"; // Read the XML content from the file
	String PutVC_OCU3RepairNecessary= new String(Files.readAllBytes(Paths.get(filePath)));
	Environments.setEnvironmentURL();
	RestAssured.baseURI = Environments.BaseURLCarport;
	proxySettings.proxy();
	TestVariables.setPutVCHash();
	TestVariables.setPutVC2Hash();
	RequestSpecification request = RestAssured.given().log().all();
	request.header("Authorization","Bearer " + TokenGenerationForCarportVins.accessToken)
		.header("Accept", "application/xml","application/vnd.vwg.mbb.genericError_v2_0_1+xml")
		.header("Content-Type","application/vnd.vwg.mbb.onlineUpdateVehicleConfiguration_v2_0_0+xml")
		.header("X-Vin",vin);
	ValidatableResponse response = request.body(PutVC_OCU3RepairNecessary).when().put("/mbb/vehicleConfiguration/v1/vehicleConf/"+PutVCHash).then().log().all();
	return response;
}

public static void assertPutVC_OCU3VW_UNECE_Carport(String testname, String vin) throws IOException {
	ValidatableResponse putvc= PutVC_RepairNecessary_OCU3VW_UNECE_Carport.putResquestForPutVc(testname, vin);
	putvc.assertThat().statusCode(200);
}
}
