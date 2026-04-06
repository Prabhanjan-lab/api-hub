package testCases.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class SetDateTime {
	
	
	public static String logTime1;
	public static String logTime2;
	public static void setDateAndTime() {
		
	 ZonedDateTime FromLogTime= Instant.now()
		       .atZone( ZoneId.of ( "Europe/Berlin" ) );
	 ZonedDateTime fromtime= FromLogTime.toLocalDateTime().minusMinutes(63).atZone(ZoneId.of("Europe/Berlin" ));
	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");        
	 logTime1 = formatter.format(fromtime);
	
	 ZonedDateTime totime= FromLogTime.toLocalDateTime().minusMinutes(60).atZone(ZoneId.of("Europe/Berlin" ));
	 DateTimeFormatter toformatter = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss.SSS'Z'");        
	 logTime2 = toformatter.format(totime);
	}
	
}

