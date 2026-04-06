/**
 *
 */
package testCases.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Sulzer GmbH
 *
 */
public class UtilDateTime {

	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

	/**
	 *
	 */
	public UtilDateTime() {

	}

	public String getDateInFutureByDays(int days) {

		LocalDate ld = LocalDate.now().plusDays(days);

		return ld.format(dateFormatter);

	}

}
