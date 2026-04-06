package testCases.testStep_Installbase.oCU3_VW;

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

public class UploadInstallbase_OCU3_VW extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse putRequestForUploadIB() throws IOException {
		
		String filePath = "resources/Payload_Xmls/OCU3_VW/OCU3_VW_Installbase.xml"; // Read the XML content from the file
		String Installbase_OCU3VW= new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		TestVariables.setChecksum();
		String InstallBase_OCU3VWWithChecksum = Installbase_OCU3VW.replaceAll("sm:checksum" + "=\"[^\"]+\"", "sm:checksum" + "=\"" + TestVariables.checksum + "\"");
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + OAuthToken)
			.header("Content-Type", "application/xml", "application/vnd.vwg.mbb.installedBase_v3_0_3+xml")
			.header("Accept", "application/xml", "application/vnd.vwg.mbb.serviceList_v5_0_1+xml");
		ValidatableResponse response =  request.body(InstallBase_OCU3VWWithChecksum).when().put("/mbb/services/v1/uploadIB/"+checksum).then().log().all();
		return response;	
	}
	
	public static void assertInstallbase_OCU3VW() throws IOException {	
		ValidatableResponse ib= UploadInstallbase_OCU3_VW.putRequestForUploadIB();
		ib.assertThat().statusCode(200);
	}
}
