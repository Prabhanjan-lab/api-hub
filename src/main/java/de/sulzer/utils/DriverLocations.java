package de.sulzer.utils;


import java.io.File;

public interface DriverLocations {
	static final String GECKODRIVER_PATH = "C:" + File.separator + "Users"  + File.separator + "AM3NO10"  + File.separator + "Desktop"  + File.separator + "selenium.webdrivers"  + File.separator + "geckodriver.exe";
	static final String INTERNET_EXPLORER_DRIVER_PATH = "C:\\Users\\"+ System.getProperty("user.name") +"\\Desktop\\selenium.webdrivers\\IEDriverServer.exe";
	static final String CHROME_DRIVER_PATH = "C:" + File.separator + "Users"  + File.separator + "AM3NO10"  + File.separator + "Desktop"  + File.separator + "selenium.webdrivers"  + File.separator + "chromedriver.exe";
}
