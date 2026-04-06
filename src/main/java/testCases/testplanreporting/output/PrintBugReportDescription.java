/**
 *
 */
package testCases.testplanreporting.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Map.Entry;

import testCases.testplanreporting.PrintTestExecutionResult;
import testCases.testplanreporting.TestExecutionResult;
import testFramework.constants.ConstantsCommunicationRestServer;

/**
 * @author Sulzer GmbH
 *
 */
public class PrintBugReportDescription implements PrintTestExecutionResult {

    private static final String OUT_BUGREPORT = "-bug-report.txt";

    /**
     *
     */
    public PrintBugReportDescription() {

    }

    @Override
    public void print(File directory, Map<String, TestExecutionResult> map) throws IOException {

        if (! directory.exists()) {
            directory.mkdir();
        }

        for (Entry<String, TestExecutionResult> entry : map.entrySet()) {

            if (entry.getValue().getTestSteps().size() <= 0) {
                continue;
            }

            // crating file name
            String filename = entry.getKey() + OUT_BUGREPORT;

            StringBuffer sb = new StringBuffer();

            // Management summary
            sb.append("{panel:title=Management Summary|borderStyle=dashed|borderColor=#ccc|titleBGColor=#FAFAFA|bgColor=#FFFFCE}");
            sb.append(System.lineSeparator());
            sb.append("_<text>_");
            sb.append(System.lineSeparator());
            sb.append("{panel}");
            sb.append(System.lineSeparator());

            // Context and starting situation
            sb.append("{panel:title=Context and Starting Situation|borderStyle=dashed|borderColor=#ccc|titleBGColor=#FAFAFA|bgColor=#FFFFFF}");
            sb.append(System.lineSeparator());
            sb.append("* Test performed: DD.MM.YYYY HH24:MM ");
            sb.append(System.lineSeparator());
            sb.append("* VIN: <if applicable> ");
            sb.append(System.lineSeparator());
            sb.append("* Campaign: <AB20-01, if applicable>");
            sb.append(System.lineSeparator());
            sb.append("* Source: Carport / Excepetion List / ReCall / Deleted");
            sb.append(System.lineSeparator());
            sb.append("* Vehicle Options:");
            sb.append(System.lineSeparator());
            sb.append("||Testvehicle||Prototype Vehicle||Software Version Comparison disabled||Skip mass notification||");
            sb.append(System.lineSeparator());
            sb.append("|(/)|(x)|(/)|(/)| ");
            sb.append(System.lineSeparator());
            sb.append("{panel}");
            sb.append(System.lineSeparator());

            // Input sequence and expected result
            sb.append("{panel:title=Input Sequence and Expected Result|borderStyle=dashed|borderColor=#58af07|titleBGColor=#d1f9ac|bgColor=#FFFFFF}");
            sb.append(System.lineSeparator());

            for (String[] step : entry.getValue().getTestSteps()) {
                sb.append("# _" + step[0] + "_");
                sb.append(System.lineSeparator());
                sb.append("#* _" + step[1] + "_");
                sb.append(System.lineSeparator());
            }

            sb.append("{panel}");
            sb.append(System.lineSeparator());

            // Actual behaviour
            sb.append("{panel:title=Actual Behavior|borderStyle=dashed|borderColor=#af3f07|titleBGColor=#ffcbb7|bgColor=#FFFFFF}");
            sb.append(System.lineSeparator());
            sb.append("Step #: _<Explain what didn't go according to your expectation>_");
            sb.append(System.lineSeparator());
            sb.append("{panel}");
            sb.append(System.lineSeparator());

            // Preanalysis
            sb.append("{panel:title=Preanalysis|borderStyle=dashed|borderColor=#1874cd|titleBGColor=#6194b9|bgColor=#ffffff}");
            sb.append(System.lineSeparator());
            sb.append("{panel}");
            sb.append(System.lineSeparator());

            // First estimation
            sb.append("{panel:title=First estimation}");
            sb.append(System.lineSeparator());
            sb.append("{panel}");
            sb.append(System.lineSeparator());

            // write test steps into bug report template
            // making sure, content is written as UTF-8, from https://stackoverflow.com/a/1001568
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(directory, filename)), ConstantsCommunicationRestServer.UTF_8));
            bw.write(sb.toString());
            bw.close();

        }

    }

}
