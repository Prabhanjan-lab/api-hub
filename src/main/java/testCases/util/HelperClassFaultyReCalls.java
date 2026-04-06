/**
 *
 */
package testCases.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.ws.rs.core.Response;

import testFramework.connectionMockRestServer.CommunicatonRestServer;
import testFramework.constants.ConstantsCommunicationRestServer;

/**
 * This class serves as helper for several tests which tests faulty ReCall data
 * and have very simiar or identical code.
 *
 *
 * @author Bege
 *
 */
public class HelperClassFaultyReCalls {

	/**
	 *
	 */
	public HelperClassFaultyReCalls() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public String loadJsonContentTemplate(String templateFile) throws IOException {

		String pathReCallJson = System.getProperty("user.dir") + templateFile;
		String content = new String(Files.readAllBytes(Paths.get(pathReCallJson)));

		return content;
	}

	/**
	 * @param content
	 * @return
	 */
	public JsonObject createJsonRecall(String content, String faultCode) {

		JsonReader reader = Json.createReader(new StringReader(content));

		JsonObjectBuilder builder = Json.createObjectBuilder();

		builder.add(ConstantsCommunicationRestServer.FAULT_CODE, faultCode);
		builder.add(ConstantsCommunicationRestServer.PAYLOAD, reader.readObject());

		JsonObject json = builder.build();

		reader.close();

		return json;
	}

	/**
	 * @param response
	 * @return
	 */
	public String extractionNameTemporaryFileJsonDocumentFromServer(Response response) {

		String jsonResponse = response.readEntity(String.class);

		StringReader sr = new StringReader(jsonResponse);

		//
		JsonReader jr = Json.createReader(sr);
		JsonObject jsonObject = jr.readObject();

		JsonValue jvFileName = jsonObject.getJsonString(ConstantsCommunicationRestServer.TEMPFILE);

		String fileName = jvFileName.toString();
		fileName = fileName.replaceAll("\"", "");

		jr.close();

		sr.close();

		return fileName;
	}

	/**
	 * @param ignoredZipFilename
	 * @param baseurRecallPostFileIgnored
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	public Response checkExistenceIgnoredZipFile(
			String ignoredZipFilename,
			CommunicatonRestServer crs,
			String baseurRecallPostFileIgnored) throws Exception {

		String content;
		Response response;

		content = "{\"" + ConstantsCommunicationRestServer.FILENAME + "\": \"" + ignoredZipFilename + "\"}";

		response = crs.doPostApplicationJson(baseurRecallPostFileIgnored, content);

	    return response;
	}

	/**
	 * @param ignoredZipFilename
	 * @param baseurRecallGetFileContent
	 * @param crs
	 * @return
	 * @throws Exception
	 */
	public String loadIgnoredZipFileContentFromServer(
			String ignoredZipFilename,
			CommunicatonRestServer crs,
			String baseurRecallGetFileContent) throws Exception {

		Response response = crs.doGet(
				baseurRecallGetFileContent + ConstantsCommunicationRestServer.URL_FILE_PARAMETER + ignoredZipFilename);

		return response.readEntity(String.class);
	}

}
