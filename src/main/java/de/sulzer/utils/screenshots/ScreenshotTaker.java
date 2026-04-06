package de.sulzer.utils.screenshots;

import org.openqa.selenium.WebDriver;

/**
 * @author Sulzer GmbH
 *
 */
public interface ScreenshotTaker {

	public void takeScreenshot(WebDriver webDriver);

	public void takeScreenshot(String filename, String path, WebDriver webDriver);

}
