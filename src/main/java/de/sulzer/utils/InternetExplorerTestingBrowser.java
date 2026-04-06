package de.sulzer.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.StartClass;

public class InternetExplorerTestingBrowser {
	
	private WebDriver driver;
	
	public WebDriver startONUPInInternetExplorer() {
		setupInternetExplorerDriver();
                String env = StartClass.environment.toUpperCase();
                if("DEMO".equals(env)){
                    this.driver.get(ImportantOnupURLs.LOGIN_URL_DEMO);
                }
                if("APPROVAL".equals(env)){
                    this.driver.get(ImportantOnupURLs.LOGIN_URL_APPROVAL);
                }
		waitForLoginElementsToBeLoaded();
		
		return this.driver;
	}

	@SuppressWarnings("deprecation")
	private void setupInternetExplorerDriver() {
		setCorrectInternetExplorerDriverLocation();
		
		DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		this.driver = new InternetExplorerDriver(caps);
	}

	private void setCorrectInternetExplorerDriverLocation(){
		System.setProperty("webdriver.ie.driver", DriverLocations.INTERNET_EXPLORER_DRIVER_PATH);
	}

	private void waitForLoginElementsToBeLoaded() {
		WebDriverWait driverWait = new WebDriverWait(driver, 10);
		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
		driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submitBtn")));
	}
}
