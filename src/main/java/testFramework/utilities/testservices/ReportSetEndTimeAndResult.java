/**
 *
 */
package testFramework.utilities.testservices;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import de.sulzer.container.TestContainer;

/**
 * @author Sulzer GmbH
 *
 */
public class ReportSetEndTimeAndResult {

	/**
	 *
	 */
	public ReportSetEndTimeAndResult() {

	}

	public void setEndTimeAndResult(TestContainer testContainer) {

	    if (testContainer != null) {

	        // setting end time/date of test
	        if (testContainer.getInfo() != null) {
	            testContainer.getInfo().setFinishDate(this.getLocalDateTime());
	        }

	        if (testContainer.getTest() != null) {
	            // set result PASS/FAIL/TODO

	            testContainer.getTest().setStatus(this.getTestRunStatus(testContainer));
	            // setting end time/date of test
	            testContainer.getTest().setFinish(this.getLocalDateTime());
	        }

	    }

	}

	/*private String getLocalDateTime() {

	    Instant instant = Instant.now();

	    ZonedDateTime timestamp = instant.atZone(ZoneId.systemDefault());

	    String time = timestamp.toString();

		System.out.println("***Datumsformat: "+time);

	    // removing written indication of time zone
	    int i = time.indexOf("[");
	    time = time.substring(0, i);
	    // removing 'Z' from timestamp (ohterwise date is refused by JIRA)
	    time = time.replaceAll("Z", "");

		System.out.println("***Datumsformat am Ende: "+time.replaceAll("[.][0-9]*[+]", "+"));

	    return time.replaceAll("[.][0-9]*[+]", "+");

	}*/

	protected String getLocalDateTime() {
		// Erstellen Sie einen Instant mit der aktuellen Zeit
		Instant instant = Instant.now();

		// Konvertieren Sie den Instant in die Zeitzone des Systems
		ZonedDateTime timestamp = instant.atZone(ZoneId.systemDefault());

		// Verwenden Sie einen DateTimeFormatter, um das korrekte ISO-8601-Format zu erstellen
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+02:00");
		String time = timestamp.format(formatter);

		return time;
	}

	private String getTestRunStatus(TestContainer testContainer) {
	    return testContainer.checkTestResult();
	}

}
