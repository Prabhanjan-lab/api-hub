package de.sulzer.utils.guistandardfunctions;

import de.sulzer.pages.AdminPortletHomepage;
import org.openqa.selenium.WebDriver;
import testFramework.utilities.ReusableMethods;

public class ActionsSectorLog {

	WebDriver driver;

	public ActionsSectorLog(WebDriver driver) {
		this.driver = driver;
	}

	public void openMenuSectorLogLoggingOverview(AdminPortletHomepage adminPortletHomepage) {

		// checking open menu
		ReusableMethods.waitForClickablility(adminPortletHomepage.getSectorLog(), 5, driver);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		if (! adminPortletHomepage.isAvailableMenuEntryLoggingOverview() ||
				!adminPortletHomepage.getLoggingOverview().isDisplayed()) {
			adminPortletHomepage.clickSectorLog();
		}
		ReusableMethods.waitForClickablility(adminPortletHomepage.getLoggingOverview(), 5, driver);
		adminPortletHomepage.clickLoggingOverview();

	}

}
