/**
 *
 */
package testFramework.utilities.testservices;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import de.sulzer.container.TestContainer;
import testCases.util.UtilFileHandling;

/**
 * @author Sulzer GmbH
 *
 */
public class ReportingTestCaseResult {

    private static final String TESTEXECUTIONS = "testexecutions";

    static final String JSON = ".json";

    //
    public static final String REPORTING_JIRA = "jira";
    public static final String REPORTING_JSON_LOCAL_FILE = "jsonlocalfile";
    public static final String REPORTING_CONSOLE_SHORT = "console_short";
    public static final String REPORTING_CONSOLE_LONG = "console_long";

    /**
     *
     */
    public ReportingTestCaseResult() {

    }

    public void reportTestResults(TestContainer testContainer, String reportingType, String[] classNameParts) {

        System.out.println("***Methode reportTestResults wird aufgerufen");

        if (REPORTING_JIRA.equals(reportingType)) {
            this.logToJira(testContainer, classNameParts);
        } else if (REPORTING_JSON_LOCAL_FILE.equals(reportingType)) {
            this.writeJsonToWorkspaceAsFile(testContainer, classNameParts);
        } else if (REPORTING_CONSOLE_SHORT.equals(reportingType)) {
            this.printConsoleReportShort(testContainer, classNameParts);
        } else if (REPORTING_CONSOLE_LONG.equals(reportingType)) {
            this.printConsoleReportLong(testContainer);
        } else {
            this.printConsoleReportShort(testContainer, classNameParts);
        }

    }

    /**
     * @param testContainer
     *
     */
    private void logToJira(TestContainer testContainer, String[] classNameParts) {


        this.printConsoleReportShort(testContainer, classNameParts);
        System.out.println("***MEthode logToJira aufgerufen1");
        ReporterJira rj = new ReporterJira();

        rj.logToJira(testContainer, classNameParts);
        System.out.println("***MEthode logToJira aufgerufen3");
    }

    private void writeJsonToWorkspaceAsFile(TestContainer testContainer, String[] classNameParts) {

        this.printConsoleReportShort(testContainer, classNameParts);

       try{

           // create directory for JSON
           File directoryTestExecutions = new File(
                   System.getProperty("user.dir") +
                   File.separator + TESTEXECUTIONS);

           System.out.println("Jira Reporting1: " +directoryTestExecutions.getAbsolutePath() + "<-absolut path, path-> " +directoryTestExecutions.getPath());

           if (! directoryTestExecutions.exists()) {
               directoryTestExecutions.mkdir();
           }

           //create a file
           File temp = new File(
                   directoryTestExecutions.getAbsolutePath() +
                   File.separator +
                   testContainer.getTest().getTestKey() + JSON);

           System.out.println("Jira Reporting2: " +temp.getName() +"<-name");

           UtilFileHandling ufh = new UtilFileHandling();
           ufh.writeStringContentToFile(testContainer.getJSON(), temp);

       } catch (IOException e) {
           e.printStackTrace();
       }

    }

    /**
     * Giving a short overview over executed test.
     * @param testContainer
     * @param classNameParts
     */
    private void printConsoleReportShort(TestContainer testContainer, String[] classNameParts) {

        int maxCountPassed = testContainer.getExpectedStepsPassed();
        int countPassed = testContainer.getStepsPassed();

        String testCase = classNameParts[0] + "_" + classNameParts[1];

        System.out.println(
                LocalDateTime.now() + " | " + testCase + " | " +
                "Status " + testContainer.checkTestResult() + " --> " +
                countPassed + " steps out of " + maxCountPassed + " passed");

    }

    /**
     * Giving an all information over executed test on console.
     */
    private void printConsoleReportLong(TestContainer testContainer) {
        System.out.println(testContainer.getAsJSON().toString());
    }

    public void writeJsonToTargetFolder(TestContainer testContainer, String[] classNameParts) {


        try {
            // Create directory for JSON in the target folder
            File directoryTarget = new File(
                    System.getProperty("user.dir") + File.separator + "target" +
                            File.separator + "test-executions");

            if (!directoryTarget.exists()) {
                directoryTarget.mkdirs();
            }

            // List existing JSON files
            File[] existingFiles = directoryTarget.listFiles();
            if (existingFiles != null && existingFiles.length >= 5) {
                // Sort files by last modified timestamp in ascending order
                Arrays.sort(existingFiles, Comparator.comparingLong(File::lastModified));

                // Delete the oldest file
                existingFiles[0].delete();
            }

            // Create a file in the target directory
            File outputFile = new File(
                    directoryTarget.getAbsolutePath() + File.separator +
                            testContainer.getTest().getTestKey() + ".json");

            // Write JSON content to the file
            try (FileWriter writer = new FileWriter(outputFile)) {
                writer.write(testContainer.getJSON());
            }

            // Print the file path
            System.out.println("Test results are written to: " + outputFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
