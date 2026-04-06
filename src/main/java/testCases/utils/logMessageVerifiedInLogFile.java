package testCases.utils;


import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class logMessageVerifiedInLogFile {

	 
	public static boolean isLogMessagePresentInRecentTime(
	        List<String> logLines,
	        String expectedMessage,
	        int minutesBack) {
	 
	    ZoneId germanZone = ZoneId.of("Europe/Berlin");
	    LocalTime nowGermanTime = LocalTime.now(germanZone);
	    LocalTime fromTime = nowGermanTime.minusMinutes(minutesBack);
	 
	    DateTimeFormatter timeFormatter =
	            DateTimeFormatter.ofPattern("HH:mm:ss");
	 
	    Pattern timePattern =
	            Pattern.compile("\\b\\d{2}:\\d{2}:\\d{2}\\b");
	 
	    for (String line : logLines) {
	 
	        Matcher matcher = timePattern.matcher(line);
	        if (!matcher.find()) {
	            continue; // no timestamp in line
	        }
	 
	        LocalTime logTime =
	                LocalTime.parse(matcher.group(), timeFormatter);
	 
	        // Logs are from 00:00 → now
	        if (!logTime.isBefore(fromTime)
	&& line.contains(expectedMessage)) {
	        	System.out.println("Macthed line :" + line);
	            return true;
	        }
	    }
	    return false;
	}

}
