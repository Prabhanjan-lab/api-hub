/**
 * 
 */
package de.sulzer.logging;


import testFramework.utilities.properties.PropertyReader;

/**
 * @author GCH9F5D
 *
 */
public class LoadLoggingConfiguration {

	public static final String ALL = "ALL";
	
	public static synchronized String loadLogLevel(String property) {
		
		String logLevel = ALL;
		
		String temp = PropertyReader.loadProperty(property);
		
		if (null == temp || temp.length() == 0) {
			return logLevel;
		} else {
			return checkLevel(temp);
		}
	}

	private static String checkLevel(String logLevel) {
		
		switch(logLevel) {

			case "SEVERE":
			case "WARNING":
			case "INFO":
			case "CONFIG":
			case "FINE":
			case "FINER":
			case "FINEST":
				return logLevel;
	
			default: 
				return ALL;
	
		}
		
	}
	
}
