/**
 * 
 */
package de.sulzer.pages.configurationlist;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author GCH9F5D
 *
 */
public class ConfigurationList extends Page {

	@FindBy(xpath="//h3[@class='panel-title']")
	private WebElement titleConfigurationList;

	@FindBy(xpath="//ul//li//a[contains(text(), 'MBBA')]")
	private WebElement tabMBBA;

//	@FindBy(xpath="//ul//li//a[contains(text(), 'MBBB')]")
	@FindBy(xpath = "//*[contains(text(), 'MBBB')]")
	private WebElement tabMBBB;

	public ConfigurationList(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the titleConfigurationList
	 */
	public WebElement getTitleConfigurationList() {
		return this.titleConfigurationList;
	}

	/**
	 * @return the tabMBBA
	 */
	public WebElement getTabMBBA() {
		return tabMBBA;
	}

	public void clickTabMBBA() {
		this.getTabMBBA().click();
	}

	/**
	 * @return the tabMBBB
	 */
	public WebElement getTabMBBB() {
		return tabMBBB;
	}

	public void clickTabMBBB() {
		this.getTabMBBB().click();
	}

}
