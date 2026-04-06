package testCases.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import testCases.readData.RecallIDFilePath;
import testCases.readData.TestParameterFilePath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
 
public class NewRecallIDGenerator {
 
    private static String FILE_NAME ;
 
    public static String GenerateNewRecallID(String testname) throws IOException {
        String currentString = readCurrentString();
        String nextString = incrementString(currentString);
        writeNextString(nextString);
        updateCampaignInJson(nextString,testname);
        return nextString;
    }
 
    private static String readCurrentString() throws IOException {
    	FILE_NAME= RecallIDFilePath.setRecallIDFilePath();
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return "TC00"; // Initial string if the file doesn't exist
        }
 
        try {
            return new String(Files.readAllBytes(Paths.get(FILE_NAME))).trim();
        } catch (IOException e) {
            e.printStackTrace();
            return "TC00";
        }
    }

    private static void writeNextString(String nextString) throws IOException {
    	FILE_NAME= RecallIDFilePath.setRecallIDFilePath();
        try {
            Files.write(Paths.get(FILE_NAME), nextString.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    private static String incrementString(String currentString) {
        char[] array = currentString.toCharArray();
        increment(array, array.length - 1);
        return new String(array);
    }
 
    private static void increment(char[] array, int position) {
        if (position < 2) { 
            return;
        }
 
        if (array[position] == '9') {
            array[position] = 'A';
        } else if (array[position] == 'Z') {
            array[position] = '0';
            increment(array, position - 1);
        } else if (array[position] >= '0' && array[position] <= '8') {
            array[position]++;
        } else if (array[position] >= 'A' && array[position] < 'Z') {
            array[position]++;
        } else {
            increment(array, position - 1);
        }
    }
 
    private static void updateCampaignInJson(String newCampaign,String testname) throws IOException {
    	String JSON_FILE_NAME = TestParameterFilePath.setTestParamerterFilePath();
    	ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, List<Map<String, Object>>> jsonMap = mapper.readValue(new File(JSON_FILE_NAME), new TypeReference<Map<String, List<Map<String, Object>>>>() {});
            List<Map<String, Object>> testDataList = jsonMap.get("testdata");
            for (Map<String, Object> entry : testDataList) {
                if (testname.equals(entry.get("name"))) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> dataList = (List<Map<String, Object>>) entry.get("data");
                    for (Map<String, Object> data : dataList) {
                        data.put("campaign", newCampaign);
                    }
                }
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_NAME), jsonMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}