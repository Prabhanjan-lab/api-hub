/**
 *
 */
package testCases.testplanreporting.util;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * @author Bege
 *
 */
public interface StrategyFilterFile {

	void checkAndAddFile(Set<String> list, File f);

}
