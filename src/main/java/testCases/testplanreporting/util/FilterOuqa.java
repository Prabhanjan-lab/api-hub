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
public class FilterOuqa implements StrategyFilterFile {

	/**
	 *
	 */
	public FilterOuqa() {

	}

	@Override
	public void checkAndAddFile(Set<String> list, File f) {

		if (f.getName().startsWith("OUQA")) {

			String name = f.getName();

			String[] parts = name.split("_");

			list.add(parts[0] + "-" + parts[1]);

		}

	}

}
