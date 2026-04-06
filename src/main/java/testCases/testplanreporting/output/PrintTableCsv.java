/**
 *
 */
package testCases.testplanreporting.output;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;
import testCases.testplanreporting.PrintTestExecutionResult;
import testCases.testplanreporting.TestExecutionResult;

/**
 * @author Sulzer GmbH
 *
 */
public class PrintTableCsv implements PrintTestExecutionResult {

    private static final String SUFFIX_CSV = ".csv";
    private static final String CSV_HEADER = "Test case;Test Execution;Error message (partial);Bug(s);Priority/-ies;Remarks;Test Execution Test Steps Link";
    private static final String SEMICOLON = ";";

    private HandlerRestVariable hrv;

    private XrayProperties properties;

    private ReadZipForErrorMessage readZipForErrorMessage;

    private String devstackApiToken;

    private String fileNameOutput;

    /**
     *
     */
    public PrintTableCsv(
            String fileName,
            HandlerRestVariable hrv,
            XrayProperties properties,
            String devstackApiToken) {

        this.hrv = hrv;

        this.properties = properties;

        this.readZipForErrorMessage = new ReadZipForErrorMessage();

        this.devstackApiToken = devstackApiToken;

        this.fileNameOutput = fileName + SUFFIX_CSV;

    }

    @Override
    public void print(File directory, Map<String, TestExecutionResult> map) throws IOException {

        if (! directory.exists()) {
            directory.mkdir();
        }

        File out = new File(directory, this.fileNameOutput);

        if (out.exists()) {
            out.delete();
        }

        Path path = out.toPath();

        // writing header of CSV
        Files.write(path, CSV_HEADER.getBytes(), StandardOpenOption.CREATE_NEW);
        Files.write(path, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);

        // writing FAIL entries
        for (String key : map.keySet()) {

            TestExecutionResult ter = map.get(key);

            StringBuilder sb = new StringBuilder(250);

            sb.append(ter.getTestCaseId());
            sb.append(SEMICOLON);
            sb.append(ter.getTestExecutionId());
            sb.append(SEMICOLON);
            // line breaks according https://stackoverflow.com/a/2163056
            sb.append(this.getErrorMessage(ter, directory).replace("\n", "").replace("\r", ""));
            sb.append(SEMICOLON);
            sb.append(String.join(", ", ter.getBugs()));
            sb.append(SEMICOLON);
            sb.append(String.join(", ", ter.getBugPriorities()));
            sb.append(SEMICOLON);
            sb.append(SEMICOLON);
            sb.append(ter.getTestExecutionTestStepLink());
            sb.append(SEMICOLON);
            sb.append(System.lineSeparator());

            Files.write(path, sb.toString().getBytes(), StandardOpenOption.APPEND);

        }

    }

    private String getErrorMessage(TestExecutionResult ter, File directory) {

//		String errorMessage = ter.getErrorMessageShortened();

        String errorMessageAssembled = this.readZipForErrorMessage.loadAndReadZipFile(
                ter,
                directory,
//				errorMessage,
                this.hrv,
                this.properties,
                this.devstackApiToken);

        return errorMessageAssembled;

    }

}
