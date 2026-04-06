package testCases.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class CurrentDateTime {
	
	public static LocalDateTime getCurrentTimeStamp() {
		LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS);
		System.out.println("Current UTC Time : "+currentDateTime);
		return currentDateTime;
	}
public static void main(String[] args) {
	System.out.println(getCurrentTimeStamp());
}
}
