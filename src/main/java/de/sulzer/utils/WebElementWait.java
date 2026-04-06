package de.sulzer.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class WebElementWait {
	private WebDriver driver;
	
	public WebElementWait(WebDriver driver) {
		this.driver = driver;
	}
	
	public void waitForElement(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElement(WebElement element, int second) {
		WebDriverWait wait = new WebDriverWait(driver, second);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementWithMinute(WebElement element, long minute) {
		WebDriverWait wait = new WebDriverWait(driver, TimeUnit.MINUTES.toMinutes(minute));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

}
