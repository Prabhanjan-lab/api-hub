/**
 *
 */
package de.sulzer.pages.useradministration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class DialogueConfigurationFileSandboxConfiguration extends Page {

	@FindBy(xpath="//div[@class='modal-header']/span[@class='configFileHeader']")
	private WebElement dialogueHeader;

	@FindBy(xpath="//div[contains(@class, 'modal-body')]/pre/code")
	private WebElement dialogueContentConfiguration;

	@FindBy(xpath="//button[@class='close']")
	private WebElement dialogueButtonCrossClose;

	/**
	 * @param driver
	 */
	public DialogueConfigurationFileSandboxConfiguration(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the dialogueHeader
	 */
	public WebElement getDialogueHeader() {
		return dialogueHeader;
	}

	/**
	 * @return the dialogueContentConfiguration
	 */
	public WebElement getDialogueContentConfiguration() {
		return dialogueContentConfiguration;
	}

	/**
	 * @return the dialogueButtonCrossClose
	 */
	public WebElement getDialogueButtonCrossClose() {
		return dialogueButtonCrossClose;
	}

	public void clickDialogueButtonCrossClose() {
		this.getDialogueButtonCrossClose().click();
	}

}
