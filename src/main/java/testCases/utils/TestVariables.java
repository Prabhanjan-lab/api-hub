package testCases.utils;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import testFramework.constants.ValuesGlobal;

public class TestVariables extends Environments {
	
	public static String checksum;
	public static String PutVCHash;
	public static String PutVC2Hash ;

	public static void setChecksum() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
		String currenttime = dateFormat.format(now);
		checksum = currenttime ;		
	}
	public static void setPutVCHash() {
		int random = 	(int) (Math.random()*(1000));
		PutVCHash = "94b6eed0f5d3e423bba29a3ec2fdf42dc3d423b4aae73ee987a34142349873"+String.valueOf(random);
	}
	
	public static void setPutVC2Hash() {
		int random = 	(int) (Math.random()*(1000));
		PutVC2Hash = "94b6eed0f5d3e423bba29a3ec2fdf42dc3d423b4aae73ee987a34142349873868"+String.valueOf(random);	
	}
	public static String OUQA_62274_filePath = "resources/Payload_Jsons/OUQA_62274_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_59776_filePath = "resources/Payload_Jsons/OUQA_59776_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_62270_filePath = "resources/Payload_Jsons/OUQA_62270_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_62292_filePath = "resources/Payload_Jsons/OUQA_62292_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_62277_filePath = "resources/Payload_Jsons/OUQA_62277_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_60473_filePath = "resources/Payload_Jsons/OUQA_60473_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_1587_filePath = "resources/Payload_Jsons/OUQA_1587_ManualRecall.json";
	public static String ORUCreationWithTwoCriterions_filePath = "resources/ORUCampaigns/ORUCreationWithTwoCriterions.json"; // Read the Json content from the file
	public static String OUQA_34934_filePath = "resources/Payload_Jsons/OUQA_34934_MovingVinFromBatchesToOtherBatch.json"; // Read the Json content from the file
	public static String CreateDeleteRecall_filePath = "resources/Payload_Jsons/CreateDeleteManualRecallForImport.json";
	public static String OUQA_63874_filePath = "resources/Payload_Jsons/OUQA_63874_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_64865_filePath = "resources/Payload_Jsons/OUQA_64865_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_64052_filePath = "resources/Payload_Jsons/OUQA_64052_ManualRecall.json"; // Read the Json content from the file
	public static String OUQA_64053_filePath = "resources/Payload_Jsons/OUQA_64053_ManualRecall.json"; // Read the Json content from the file

	
	public static String getKeyStorePath(String filename) {
		 String os = System.getProperty("os.name").toLowerCase();
		 String path = null;
	        // assuming running on RHEL VM
	        if (os.contains("linux")) {
	        	path=System.getProperty("user.dir") +"/keystore/"+filename+".keystore.p12";
	            // assuming running locally with Windows
	        } else if (os.contains("windows")) {
	        	path=System.getProperty("user.dir") +"\\keystore\\"+filename+".keystore.p12";
	        }
			return path;	
	}
}
