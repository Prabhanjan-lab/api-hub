package testCases.SoapUI.reusable_TestSteps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class currentSPSStatusofCampaign extends Environments{

	public static ValidatableResponse getCurrentspsStatus(String testname, String vin) throws IOException {
		
		String filePath = "resources/Payload_Xmls/GetRemoteUpdateByVehicle.xml"; // Read the XML content from the file
		String payload= new String(Files.readAllBytes(Paths.get(filePath)));
		payload = payload.replace("vin", vin);
		
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		ValidatableResponse response = request.body(payload).when().post("/ia/OTASoftwareInformationService/V1").then().log().all();;
		return response;
	}
	
	public static void assertCurrentspsStatus(String testname,String vin,String campaign, String criterion, String expectedSPSStatus) throws InterruptedException, IOException {
		ValidatableResponse currentspsStatus= currentSPSStatusofCampaign.getCurrentspsStatus(testname, vin);
		currentspsStatus.assertThat().statusCode(200);
		currentspsStatus.and().body("Envelope.Body.GetRemoteUpdatesByVehicleResponse.SoftwareUpdates.SoftwareUpdate.find { it.SoftwareUpdateID == '" + campaign +"-"+criterion+"' }.Status", Matchers.equalTo(expectedSPSStatus));
		
	}
	
	public static void assertIsNotifyPossibleTrue(String testname, String vin, String expectedCampaign, String criterion) throws InterruptedException, IOException{
		ValidatableResponse currentspsStatus= currentSPSStatusofCampaign.getCurrentspsStatus(testname, vin);
		currentspsStatus.assertThat().statusCode(200);
		
		XmlPath xmlPath = currentspsStatus.extract().xmlPath();
		 
	    List<String> campaigns = xmlPath.getList(
	        "Envelope.Body.GetRemoteUpdatesByVehicleResponse.SoftwareUpdates.SoftwareUpdate.SoftwareUpdateTitle"
	    );
	 
	    List<Boolean> notifyFlags = xmlPath.getList(
	        "Envelope.Body.GetRemoteUpdatesByVehicleResponse.SoftwareUpdates.SoftwareUpdate.IsNotifyPossible",
	        Boolean.class
	    );
	 
	    boolean campaignFound = false;
	 
	    for (int i = 0; i < campaigns.size(); i++) {
	        if (expectedCampaign.equals(campaigns.get(i))) {
	            campaignFound = true;
	 
	            boolean isNotifyPossible = notifyFlags.get(i);
	            System.out.println("Campaign=" + campaigns.get(i)
	                    + " IsNotifyPossible=" + isNotifyPossible);
	 
	            assertTrue(
	                "IsNotifyPossible should be true for campaign " + expectedCampaign,
	                isNotifyPossible
	            );
	        }
	    }
	   
	}
	
	public static void assertIsNotifyPossibleFalse(String testname, String vin, String expectedCampaign, String criterion) throws InterruptedException, IOException{
		ValidatableResponse currentspsStatus= currentSPSStatusofCampaign.getCurrentspsStatus(testname, vin);
		currentspsStatus.assertThat().statusCode(200);
		
		XmlPath xmlPath = currentspsStatus.extract().xmlPath();
		
		List<String> campaigns = xmlPath.getList(
		        "Envelope.Body.GetRemoteUpdatesByVehicleResponse.SoftwareUpdates.SoftwareUpdate.SoftwareUpdateTitle"
		    );
		 
		    List<Boolean> notifyFlags = xmlPath.getList(
		        "Envelope.Body.GetRemoteUpdatesByVehicleResponse.SoftwareUpdates.SoftwareUpdate.IsNotifyPossible",
		        Boolean.class
		    );
		 
		    boolean campaignFound = false;
		 
		    for (int i = 0; i < campaigns.size(); i++) {
		        if (expectedCampaign.equals(campaigns.get(i))) {
		            campaignFound = true;
		 
		            boolean isNotifyPossible = notifyFlags.get(i);
		            System.out.println("Campaign=" + campaigns.get(i)
		                    + " IsNotifyPossible=" + isNotifyPossible);
		 
		            assertFalse(
		                "IsNotifyPossible should be true for campaign " + expectedCampaign,
		                isNotifyPossible
		            );
		        }
		    }
		}
}