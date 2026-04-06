package testCases.SoapUI.reusable_TestSteps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class NotifyVehicleCampaign extends Environments{

	public static ValidatableResponse getNotifyVehicleCampaign(String testname, String vin, String campaign, String criterion) throws IOException {
		
		String filePath = "resources/Payload_Xmls/NotifyVehicleCampaign.xml"; // Read the XML content from the file
		String payload= new String(Files.readAllBytes(Paths.get(filePath)));
		payload = payload.replace("vin", vin).replace("campaign", campaign).replace("criterion", criterion);
		
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Content-Type","text/xml; charset=UTF-8");
		ValidatableResponse response = request.body(payload).when().post("/ia/OTASoftwareInformationService/V1").then().log().all();;
		return response;
	}
	
	public static void assertNotifyVehicleStatusOK(String testname,String vin,String campaign, String criterion, String expectedNotifyStatus) throws InterruptedException, IOException {
		ValidatableResponse notifyVehicleStatus= NotifyVehicleCampaign.getNotifyVehicleCampaign(testname, vin, campaign, criterion);
		notifyVehicleStatus.assertThat().statusCode(200);
		notifyVehicleStatus.and().body("Envelope.Body.NotifyVehicleCampaignResponse.Status", Matchers.equalTo(expectedNotifyStatus));
		//notifyVehicleStatus.and().body("Envelope.Body.NotifyVehicleCampaignResponse.Message", Matchers.equalTo(expectednotifyMessage));
		
	}
	
	public static void assertNotifyVehicleStatusNOK(String testname,String vin,String campaign, String criterion, String expectedNotifyStatus, String expectedNotifyMessage) throws InterruptedException, IOException {
		ValidatableResponse notifyVehicleStatus= NotifyVehicleCampaign.getNotifyVehicleCampaign(testname, vin, campaign, criterion);
		notifyVehicleStatus.assertThat().statusCode(200);
		notifyVehicleStatus.and().body("Envelope.Body.NotifyVehicleCampaignResponse.Status", Matchers.equalTo(expectedNotifyStatus));
		notifyVehicleStatus.and().body("Envelope.Body.NotifyVehicleCampaignResponse.Message", Matchers.equalTo(expectedNotifyMessage));
		
	}
	
}