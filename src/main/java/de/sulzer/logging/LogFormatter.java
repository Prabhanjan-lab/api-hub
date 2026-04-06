/**
 * 
 */
package de.sulzer.logging;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * @author GCH9F5D
 *
 */
public class LogFormatter extends Formatter {

	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	
	@Override
	public String format(LogRecord record) {
		
		LocalDateTime date =
			    LocalDateTime.ofInstant(Instant.ofEpochMilli(record.getMillis()), TimeZone.getTimeZone("GMT").toZoneId());
		
		return dtf.format(date) + " | " + record.getLevel() + " | " + record.getMessage() + System.lineSeparator();
	}

	/*@Override
	public String format(LogRecord record) {
		LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochMilli(record.getMillis()), ZoneId.of("GMT"));
		return dtf.format(date) + " | " + record.getLevel() + " | " + record.getMessage() + System.lineSeparator();
	}
*/

}
