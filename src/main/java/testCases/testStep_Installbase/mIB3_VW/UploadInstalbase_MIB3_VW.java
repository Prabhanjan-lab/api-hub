package testCases.testStep_Installbase.mIB3_VW;

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

public class UploadInstalbase_MIB3_VW extends RegisterAndGetOAUTH{
	
	public static ValidatableResponse putRequestForUploadIB() throws IOException {
		String filePath = "resources/Payload_Xmls/MIB3_VW/MIB3VW_Installbase.xml"; // Read the XML content from the file
		String Installbase_MIB3VW = new String(Files.readAllBytes(Paths.get(filePath)));
		Environments.setEnvironmentURL();
		RestAssured.baseURI = BaseURL;
		proxySettings.proxy();
		TestVariables.setChecksum();
		String Installbase_MIB3VWWithChecksum = Installbase_MIB3VW.replaceAll("sm:checksum" + "=\"[^\"]+\"", "sm:checksum" + "=\"" + TestVariables.checksum + "\"");
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + OAuthToken)
			.header("Content-Type", "application/xml", "application/vnd.vwg.mbb.installedBase_v3_0_3+xml")
			.header("Accept", "application/xml", "application/vnd.vwg.mbb.serviceList_v5_0_1+xml");
		ValidatableResponse response =  request.body(Installbase_MIB3VWWithChecksum).when().put("/mbb/services/v1/uploadIB/"+checksum).then().log().all();
		return response;
		
	}
	public static void assertInstallbase_MIB3VW() throws IOException {	
		ValidatableResponse ib= UploadInstalbase_MIB3_VW.putRequestForUploadIB();
		ib.assertThat().statusCode(200);
	}
	
}
