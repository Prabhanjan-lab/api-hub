package testCases.reusable_TestSteps;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.LogReports;
import testCases.utils.proxySettings;

public class ImporterRecall_Logs {

	public static Response GetRecallLogs(String testname) throws IOException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/logging/recall/timestamps").then().log().all().extract().response();
		return response;
	}
	public static List<String> getRecallLogID(String testname) throws IOException {
		Response recallLogIDs = ImporterRecall_Logs.GetRecallLogs(testname);
		int statuscode	=recallLogIDs.getStatusCode();
		Assert.assertEquals(statuscode, 200);
		JSONObject j = new JSONObject(recallLogIDs.asString());
		List<String> logID =new ArrayList<>();
		for(int i = 0; i<j.getJSONArray("timestamps").length(); i++) {
			String Campaign = j.getJSONArray("timestamps").getJSONObject(i).getString("importLogId").toString();
			logID.add(Campaign);
		}
		return logID;
	}
	public static List<String> getRecallTimeStamp(String testname) throws IOException {
		Response recallLogIDs = ImporterRecall_Logs.GetRecallLogs(testname);
		int statuscode	=recallLogIDs.getStatusCode();
		Assert.assertEquals(statuscode, 200);
		JSONObject j = new JSONObject(recallLogIDs.asString());
		List<String> timeStamp =new ArrayList<>();
		for(int i = 0; i<j.getJSONArray("timestamps").length(); i++) {
			String Campaign = j.getJSONArray("timestamps").getJSONObject(i).getString("timestamp").toString();
			timeStamp.add(Campaign);
		}
		return timeStamp;
	}
	public static Response GetLogDetails(String testname,String type,String logID ) throws IOException, InterruptedException {
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		JWT.GenerateJWTToken();
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken).header("Content-Type","application/json");
		request.queryParam("type", type);
		Response response = request.when().get("/onup/api/bs/oru/v1/"+brand+"/logging/importlogid/"+logID+"/records").then().log().all().extract().response();
		return response;
	}

	public static void assertLogDetails(String testname,String type,String recall,String LogCheck,LocalDateTime currentTimestamp) throws InterruptedException, IOException {
		boolean logFound = false;
		int logIdIndex=0;
		List<String> logID=ImporterRecall_Logs.getRecallLogID(testname);
		List<String> timeStamp=ImporterRecall_Logs.getRecallTimeStamp(testname);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		LocalDateTime logIDTimestamp;
		do {
			Response logs = ImporterRecall_Logs.GetLogDetails(testname,type,logID.get(logIdIndex));
			int statuscode	=logs.getStatusCode();
			Assert.assertEquals(statuscode, 200);
			JSONObject jsonObject = new JSONObject(logs.asString());
			JSONArray dataArray = jsonObject.getJSONArray("data");
			String logIDcontent = dataArray.toString();
			logIDTimestamp = LocalDateTime.parse(timeStamp.get(logIdIndex), formatter);
			if(logIDTimestamp.isAfter(currentTimestamp)) {
				if(logIDcontent.contains(recall)) {
					if(logIDcontent.contains(LogCheck)) {
						System.out.println(recall+" "+LogCheck+" Logged at Importer Recall logs of "+(logIdIndex+1)+"th "+type+" Records");
						logFound = true;
					}
				}
				logIdIndex++;
			} 
			else {
				System.out.println("No records are available after current timestamp");
				break;
			}	
		}
		while (logFound==false);
		// Assert failure if the log was not found after all attempts
		Assert.assertTrue(recall+" "+LogCheck + " not found in any logs.", logFound);

	}
}
