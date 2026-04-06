/**
 *
 */
package de.sulzer.pages.documentation.popUpDialogues;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class DialogueDocumentationCarPort extends Page {

	@FindBy(xpath="//h4")
	private WebElement text;

	@FindBy(xpath="//div[@class='modal-footer']/button[contains(text(), 'Yes')]")
	private WebElement buttonYes;

	@FindBy(xpath="//div[@class='modal-footer']/button[contains(text(), 'Cancel')]")
	private WebElement buttonCancel;

	/**
	 * @param driver
	 */
	public DialogueDocumentationCarPort(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the text
	 */
	public WebElement getText() {
		return text;
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

	/**
	 * @return the buttonCancel
	 */
	public WebElement getButtonCancel() {
		return buttonCancel;
	}

	public void clickBttonCancel() {
		this.getButtonCancel().click();
	}

}
