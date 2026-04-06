/**
 *
 */
package de.sulzer.pages.campaignmanagement.inbox.popUpDialogues;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class DialogueFlashMediaDeletion extends Page {

	@FindBy(xpath="//legend[contains(text(),'Deletion - Flashmedium')]")
	private WebElement titleCriterionDeletion;

	@FindBy(xpath="//button[contains(text(),'Cancel')]")
	private WebElement buttonCancel;

	@FindBy(xpath="//button[contains(text(),'Apply')]")
	private WebElement buttonApply;

	/**
	 * @param driver
	 */
	public DialogueFlashMediaDeletion(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the titleCriterionDeletion
	 */
	public WebElement getTitleCriterionDeletion() {
	    return titleCriterionDeletion;
	}

	/**
	 * @return the buttonCancel
	 */
	public WebElement getButtonCancel() {
	    return buttonCancel;
	}

	public void clickButtonCancel() {
	    this.getButtonCancel().click();
	}

	/**
	 * @return the buttonApply
	 */
	public WebElement getButtonApply() {
	    return buttonApply;
	}

	public void clickButtonApply() {
	    this.buttonApply.click();
	}

}
