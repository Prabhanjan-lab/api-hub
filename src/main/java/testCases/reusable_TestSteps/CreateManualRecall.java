package testCases.reusable_TestSteps;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;

import de.sulzer.oudcampaign.CampaignIdOUDFileBased;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.readData.UpdateTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class CreateManualRecall extends JWT {
	public static Response postRequestForManualRecall(String testname, String filePath,String recallID) throws Exception {
		
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String vin = ReadTestParameters.getAttributeValue(testname, "vin");
		JWT.GenerateJWTToken();
		String manualRecallPayload = new String(Files.readAllBytes(Paths.get(filePath)));
		manualRecallPayload = manualRecallPayload.replace("campaignnumber", recallID).replace("campaigntitle", recallID).replace("Vin", vin);
		Environments.setEnvironmentURL();
		RestAssured.baseURI = Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization", "Bearer " + JWT.JWTToken).header("Content-Type", "application/json");
		Response response = request.body(manualRecallPayload).when().post("/onup/api/bs/oru/v1/" + brand + "/recalls")
				.then().log().all().extract().response();
		return response;
	}
	public static String assertNewRecallID(String testname, String FilePath) throws Exception {
		String RECALL_ID = CampaignIdOUDFileBased.getInstance().ascertainNewId();
		Response recall = CreateManualRecall.postRequestForManualRecall(testname, FilePath,RECALL_ID);
		int statuscode = recall.getStatusCode();
		while (statuscode == 400) {
			RECALL_ID = CampaignIdOUDFileBased.getInstance().ascertainNewId();
			recall = CreateManualRecall.postRequestForManualRecall(testname, FilePath,RECALL_ID);
			statuscode = recall.getStatusCode();
			
			
		}
		Assert.assertEquals(201, statuscode);
		DeleteRecall.assertRecallDelete(testname, RECALL_ID);
		return RECALL_ID;
	}
	public static void assertCreateManualRecall(String testname, String FilePath,String recallID) throws Exception {
		Response recall = CreateManualRecall.postRequestForManualRecall(testname, FilePath,recallID);
		int statuscode = recall.getStatusCode();
		while (statuscode == 400) {
			recall = CreateManualRecall.postRequestForManualRecall(testname, FilePath,recallID);
			statuscode = recall.getStatusCode();
		}
		Assert.assertEquals(201, statuscode);
		UpdateTestParameters.updateAttributeValueInJson("campaign",recallID,testname); 
	}
}