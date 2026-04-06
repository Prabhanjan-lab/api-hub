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
import testCases.testplanreporting.PrintTestExecutionResult;
import testCases.testplanreporting.TestExecutionResult;

/**
 * @author Sulzer GmbH
 *
 */
public class PrintTableJiraIssueTable implements PrintTestExecutionResult {

    private static final String SUFFIX_FILE = ".txt";
    private static final String CSV_HEADER = "||Test case||Test Case Title||Bug(s)||Priority/-ies||Remarks||Current State||";
    private static final String SEMICOLON = ";";
    private static final String PIPE_SIMPLE = "|";

    private String fileNameOutput;

    /**
     *
     */
    public PrintTableJiraIssueTable(String fileName) {
        this.fileNameOutput = fileName + SUFFIX_FILE;
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

            sb.append(PIPE_SIMPLE);
            sb.append(ter.getTestCaseId());
            sb.append(PIPE_SIMPLE);
            sb.append(ter.getTestCaseTitle());
            sb.append(PIPE_SIMPLE);

            if (ter.getBugs().size() > 0) {
                sb.append(String.join(", ", ter.getBugs()));
            } else {
                sb.append(" ");
            }

            sb.append(PIPE_SIMPLE);

            if (ter.getBugs().size() > 0) {
                sb.append(String.join(", ", ter.getBugPriorities()));
            } else {
                sb.append(" ");
            }

            sb.append(PIPE_SIMPLE);
            sb.append(" ");
            sb.append(PIPE_SIMPLE);
            sb.append("FAIL");
            sb.append(PIPE_SIMPLE);
            sb.append(System.lineSeparator());

            Files.write(path, sb.toString().getBytes(), StandardOpenOption.APPEND);

        }

    }

}
