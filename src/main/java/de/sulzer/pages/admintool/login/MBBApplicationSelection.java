/**
 *
 */
package de.sulzer.pages.admintool.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Bege
 *
 */
public class MBBApplicationSelection extends Page {

	@FindBy(xpath="//li/a[@href='./traceDownload']")
	WebElement linkDownloadTracing;

	/**
	 *
	 */
	public MBBApplicationSelection(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the linkDownloadTracing
	 */
	public WebElement getLinkDownloadTracing() {
		return linkDownloadTracing;
	}

	public void clickLinkDownloadTracing() {
		this.getLinkDownloadTracing().click();
	}

}
