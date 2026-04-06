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
public class ConfigurationDeleteMappingMIB extends Page {

	@FindBy(xpath="//h4[@class='modal-title'][contains(text(), 'Warning')]")
	private WebElement titleConfigurationDeleteMappingWarning;

	@FindBy(xpath="//button[@class='btn btn-default'][contains(text(), 'No')]")
	private WebElement buttonNo;

	@FindBy(xpath="//button[@class='btn btn-primary'][contains(text(), 'Yes')]")
	private WebElement buttonYes;

	public ConfigurationDeleteMappingMIB(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the titleConfigurationDeleteMappingWarning
	 */
	public WebElement getTitleConfigurationDeleteMappingWarning() {
		return titleConfigurationDeleteMappingWarning;
	}

	/**
	 * @return the buttonNo
	 */
	public WebElement getButtonNo() {
		return buttonNo;
	}

	public void clickButtonNo() {
		this.getButtonNo().click();
	}
	
	/**
	 * @return the buttonYes
	 */
	public WebElement getButtonYes() {
		return buttonYes;
	}

	public void clickButtonYes() {
		this.getButtonYes().click();
	}

}
