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

public class UploadInstallbase_OCU3_VW_NONUNECE extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse putRequestForUploadIB() throws IOException {
		
		String filePath = "resources/Payload_Xmls/OCU3_VW_NONUNECE/OCU3_VW_NONUNECE_Installbase.xml"; // Read the XML content from the file
		String Installbase_OCU3VW= new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		TestVariables.setChecksum();
		String InstallBase_OCU3VWWithChecksum = Installbase_OCU3VW.replaceAll("sm:checksum" + "=\"[^\"]+\"", "sm:checksum" + "=\"" + TestVariables.checksum + "\"");
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + OAuthToken)
			   .header("Accept", "application/xml", "application/vnd.vwg.mbb.serviceList_v2_1_1+xml")
			   .header("Content-Type", "application/xml", "application/vnd.vwg.installedBase_v1_0_0+xml");
		ValidatableResponse response =  request.body(InstallBase_OCU3VWWithChecksum).when().put("/mbb/services/v1/uploadIB/"+checksum).then().log().all();
		return response;	
	}
	
	public static void assertInstallbase_OCU3VW_NONUNECE() throws IOException {	
		ValidatableResponse ib= UploadInstallbase_OCU3_VW_NONUNECE.putRequestForUploadIB();
		ib.assertThat().statusCode(200);
	}
}

