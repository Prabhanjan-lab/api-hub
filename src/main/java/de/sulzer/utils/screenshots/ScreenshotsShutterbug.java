/**
 *
 */
package de.sulzer.utils.screenshots;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.WebDriver;

//import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

/**
 * @author Sulzer GmbH
 *
 */
public class ScreenshotsShutterbug implements ScreenshotTaker {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss.SSS");

	/**
	 *
	 */
	public ScreenshotsShutterbug() {

	}

	@Override
	public void takeScreenshot(WebDriver webDriver) {

		String stringDate  = dateFormat.format(new Date());
		String fileName = "screenshot-" + stringDate + ".png";

		Shutterbug.shootPage(webDriver, ScrollStrategy.WHOLE_PAGE, 100, true).
        withName(fileName).
        save(".");

	}

	@Override
	public void takeScreenshot(String filename, String path, WebDriver webDriver) {

		Shutterbug.shootPage(webDriver, ScrollStrategy.WHOLE_PAGE, 100, true).
			withName(filename).
			save(path);

	}

}
