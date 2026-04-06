package testCases.readData;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateTestParameters {

	public static void updateAttributeValueInJson( String attributeKey,String value,String testname) throws IOException {
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
                        data.put(attributeKey, value);
                    }
                }
            }
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSON_FILE_NAME), jsonMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
