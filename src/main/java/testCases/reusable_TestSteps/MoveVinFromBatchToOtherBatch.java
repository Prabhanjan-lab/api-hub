package testCases.reusable_TestSteps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

public class MoveVinFromBatchToOtherBatch extends JWT{
	public static String draftId;
	public static ValidatableResponse Charge(String testname, String filePath,String campaign, String criterion, String batchName, String predecessorValue, String targetValue, String importerValue, String mbtValue, String pknValue) throws IOException {

		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		String amId= ReadTestParameters.getAttributeValue(testname, "amId");
		String amCode= ReadTestParameters.getAttributeValue(testname, "amCode");
		String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
		String flashMedium= ReadTestParameters.getAttributeValue(testname, "flashMedium");
		String aaFirstBatchName = ReadTestParameters.getAttributeValue(testname, "aaFirstBatchName");
		String aaSecondBatchName = ReadTestParameters.getAttributeValue(testname, "aaSecondBatchName");
		
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		
		String chargePayload = new String(Files.readAllBytes(Paths.get(filePath)));
		
		draftId= CreateAutoApprovedBatch.AutoApprovedBatchDraft(testname, campaign, criterion, batchName, predecessorValue, targetValue, importerValue, mbtValue, pknValue);
		ValidatableResponse Draftfilter=CreateAutoApprovedBatch.DraftFilter(testname, targetValue, importerValue, mbtValue, pknValue);
		Draftfilter.assertThat().statusCode(201);
		String transactionalId = CreateAutoApprovedBatch.FilteredVehicles(testname);
		
		chargePayload = chargePayload.replace("campaignID", campaign)
									 .replace("campaigntitle", campaign)
									 .replace("transactionalid", transactionalId)
									 .replace("draftId", draftId);

		System.out.println(chargePayload);
		
		
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json")
		.body(chargePayload);
		ValidatableResponse charge = request.when().post("/onup/api/bs/oru/v1/"+brand+"/charge").then().log().all();
		return charge;
		}
	public static void assertMoveVinsFromBacthsToOtherBatch(String testname, String filePath, String campaign, String criterion, String batchName, String predecessorValue, String targetValue, String importerValue, String mbtValue, String pknValue) throws IOException {
		ValidatableResponse response= MoveVinFromBatchToOtherBatch.Charge(testname, filePath, campaign, criterion, batchName, predecessorValue, targetValue, importerValue, mbtValue, pknValue);
		response.assertThat().statusCode(201);
		System.out.println("Auto Approved Batch Created and Vin moved from other batches to the anotherBatch.");	
	}
}

