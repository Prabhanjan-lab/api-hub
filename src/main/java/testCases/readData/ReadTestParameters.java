package testCases.readData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadTestParameters {

	public static JsonNode testdataArray() throws IOException {
		String filepath=TestParameterFilePath.setTestParamerterFilePath();
		File jsonFile = new File(filepath);
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode rootNode = objectMapper.readTree(jsonFile);
		JsonNode testDataArray = rootNode.get("testdata");
		return testDataArray;	
	}

	public static HashMap<String, String>  getAttributes(String testname) {
		HashMap<String, String> attributeMap=null;
		JsonNode testDataArray;
		try {
			testDataArray = ReadTestParameters.testdataArray();
		
		if (testDataArray.isArray()) {
			for(JsonNode testdata:testDataArray){
				String testName = testdata.get("name").asText();
				if (testName.equalsIgnoreCase(testname)) {
					JsonNode dataArray = testdata.get("data");
					attributeMap = new HashMap<>();
	                if (dataArray.isArray() && dataArray.size() > 0) {
	                	JsonNode attributeNode = dataArray.get(0);
	                
	                Iterator<String> FieldNames = attributeNode.fieldNames();
	                while (FieldNames.hasNext()) {
	                    String fieldName = FieldNames.next();
	                    String value = attributeNode.get(fieldName).asText();
	                    attributeMap.put(fieldName, value);
	                }
	            }
	                    }
	                }
				}
		}
		catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
			}
		return attributeMap;
	}
	public static String getAttributeValue(String testname,String key) {
		HashMap<String, String> attribute=getAttributes(testname);
		return attribute.get(key);	
	}
}