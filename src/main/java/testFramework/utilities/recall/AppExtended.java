/**
 *
 */
package testFramework.utilities.recall;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;
import de.audi.oru.recall.faultyvalues.EnumFaultyValues;
import de.audi.oru.recall.faultyvalues.FaultyValueChecker;
import de.audi.oru.recall.parser.StreamingJsonParser;
import de.audi.services.logging.loaderLogging.LoadLoggingConfiguration;
import de.audi.services.logging.loaderLogging.LogConfiguration;
import de.sulzer.logging.LogFormatter;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class AppExtended {

	private static final String AUDIRECALLPROCESSOR_LOG =
			ConstantsLogger.AUDIRECALLPROCESSOR +
			ConstantsLogger.LOG_FILE_EXTENSION;

	public static final String VERSION = "v0.9.0";

	private static final Logger logger =
	        Logger.getLogger(AppExtended.class.getName());
	private static Handler handler;

	public static void main(String[] args) throws SecurityException, IOException {

		AppExtended ae = new AppExtended();

		try {
			ae.process(args);
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public void process(String[] args) throws Throwable {

		logger.setUseParentHandlers(false);

		// logging related
		if (handler == null) {

			handler = new FileHandler(
					AUDIRECALLPROCESSOR_LOG,
					ConstantsLogger.LOG_FILE_SIZE,
					ConstantsLogger.LOG_FILE_COUNT,
					true);

			handler.setFormatter(new LogFormatter());
			logger.addHandler(handler);

			logger.setLevel(Level.parse(LoadLoggingConfiguration.loadLogLevel(LogConfiguration.LOGLEVEL)));

		}

		// standard program behaviour
//		System.out.println("FP-721 RECALL Export Generator v0.9.0 (enhanced by Sulzer GmbH)\n");

		StartParameters params = new StartParameters();
		CmdLineParser cmdArgParser = new CmdLineParser(params);

		logger.fine("Analysing and checking call parameters (" + params.getInputFile() + ").");

		try {
			cmdArgParser.parseArgument(args);
		} catch (CmdLineException e) {
			logger.severe("Error: " + e.getMessage());
//			System.err.println(e.getMessage());
//			cmdArgParser.printUsage(System.err);
//			System.err.println();
			return;
		}

		File fileJson = params.getInputFile();
		if (fileJson == null) {
//			printUsageWithHint("Error: You need to give us an input file using -i", cmdArgParser);
			logger.log(Level.SEVERE, "Error: You need to give us an input file using -i");
			return;
		}

		if (!fileJson.exists()) {
//			printUsageWithHint(
//					"Error: input file not found at " + inputFile.getAbsolutePath(), cmdArgParser);
			logger.log(Level.SEVERE, "Error: input file not found at " + fileJson.getAbsolutePath());
			return;
		}

		File inputSchemaFile = params.getInputSchemaFile();
		if (inputSchemaFile == null) {
//			printUsageWithHint("Error: You need to give us a valid JSON Schema file for validating your input using -s", cmdArgParser);
			logger.log(Level.SEVERE, "Error: You need to give us a valid JSON Schema file for validating your input using -s");
			return;
		}

		if (!inputSchemaFile.exists()) {
//			printUsageWithHint(
//					"Error: JSON Schema not found at " + inputSchemaFile.getAbsolutePath(), cmdArgParser);
			logger.log(Level.SEVERE, "Error: JSON Schema not found at " + inputSchemaFile.getAbsolutePath());
			return;
		}

		String faultyValue = params.getFaultyData();
		//
		String faultyValueKey;
		//
		try {
			faultyValueKey = EnumFaultyValues.valueOf(faultyValue).toString();
			logger.info("Found fault code: '" + faultyValue + "'.");
		} catch (Exception e) {
			faultyValueKey = "";
		}
		//
		FaultyValueChecker.getInstance().initFaultList();
		//
		if (faultyValueKey.length() > 0) {
			FaultyValueChecker.getInstance().setFaultyValue(faultyValue);
			logger.info("Fault code count: '" + FaultyValueChecker.getInstance().getFaultCodeCount() + "'.");
		}

		logger.fine("Parameter checking done (" + params.getInputFile() + ").");

		ObjectMapper jsonMapper = new ObjectMapper();
		JsonNode schema = null;
		try {
			schema = jsonMapper.readTree(inputSchemaFile);
		} catch (Throwable e) {
//			printUsageWithHint(
//					"Error parsing your schema file: " + e.getMessage(), cmdArgParser);
			logger.log(Level.SEVERE, "Error parsing your schema file: " + e.getMessage());
			return;
		}

		JsonNode inputJson = null;
		try {
			inputJson = jsonMapper.readTree(fileJson);
		} catch (Throwable e) {
//			printUsageWithHint(
//					"Error parsing your input file: " + e.getMessage(), cmdArgParser);
			logger.log(Level.SEVERE, "Error parsing your input file: " + e.getMessage());
			return;
		}


		try {
			JsonValidator mValidator = JsonSchemaFactory.byDefault().getValidator();
			ProcessingReport report = mValidator.validate(schema, inputJson, true);

			if (!report.isSuccess()) {

				StringBuffer fullMessage = new StringBuffer("\n");

				for (ProcessingMessage msg : report) {
					fullMessage.append(" - ");
					fullMessage.append(msg.getMessage());
					fullMessage.append("\n");
				}
				throw new IllegalArgumentException(fullMessage.toString());
			}

		} catch (Throwable e) {
//			printUsageWithHint(
//					"Validation error for your input file: " + e.getMessage(), cmdArgParser);
			logger.log(Level.SEVERE, "Received json was not according schema (validation error), see message report " + e.getMessage() + ", for file " + fileJson.getAbsolutePath());
			return;
		}

		assert ((fileJson != null) && (fileJson.exists()));

		FileInputStream fisFileJson = null;
		OutputStream fosFileMainFrame = null;

		logger.fine("Preparing file writing (" + params.getInputFile() + ").");

		try {

			logger.info("ReCall ID processing: Generating RECALL Fixed Width File ...");

			File fileMainFrame = new File(fileJson.getAbsolutePath() + ".out.txt");

			fisFileJson = new FileInputStream(fileJson);
			fosFileMainFrame = new FileOutputStream(fileMainFrame);

			String recallId = this.jsonExtractId(new String(Files.readAllBytes(Paths.get(fileJson.toURI()))));

			logger.info("Found ID = " + recallId);

			StreamingJsonParser parser = new StreamingJsonParser();
			parser.parseStream(fisFileJson, fosFileMainFrame);

			logger.info("ReCall ID '" + recallId + "': Writing output/main frame file to " + fileMainFrame.getAbsolutePath());

			fosFileMainFrame.flush();
			fosFileMainFrame.close(); // added additionally to this, in order to delete this file later

		} catch (Exception e) {
			logger.severe("Exception with main frame file writing: " + e.getMessage());
		} finally {

			if (fisFileJson != null) {
				fisFileJson.close();
			}

			if (fosFileMainFrame != null) {
				fosFileMainFrame.flush();
				fosFileMainFrame.close();
			}

			logger.fine("File written (" + params.getInputFile() + ").");

		}

	}

	public static void printUsageWithHint(String msg, CmdLineParser cmdArgParser) {
		System.out.println(msg + "\n");
		cmdArgParser.printSingleLineUsage(System.out);
		System.out.println("\n");
		cmdArgParser.printUsage(System.out);
	}


	public synchronized String jsonExtractId(String json) {

		   String recallId = "";

		   StringReader sr = new StringReader(json);

		   //
		   JsonReader jr = Json.createReader(sr);
		   JsonObject jsonObject = jr.readObject();

	 	   JsonObject action = jsonObject.getJsonObject("action");

	 	   JsonValue id = action.getJsonString("id");

	 	   recallId = id.toString();

		   jr.close();

		   sr.close();

		   return recallId.replaceAll("\"", "");
	   }

}
