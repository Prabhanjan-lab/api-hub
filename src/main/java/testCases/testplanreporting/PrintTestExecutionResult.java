/**
 *
 */
package testCases.testplanreporting;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Bege
 *
 */
public interface PrintTestExecutionResult {

    public void print(File directory, Map<String, TestExecutionResult> map) throws IOException;

}
