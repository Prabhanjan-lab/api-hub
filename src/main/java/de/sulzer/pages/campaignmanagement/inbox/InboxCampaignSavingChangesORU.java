/**
 *
 */
package de.sulzer.pages.campaignmanagement.inbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author GCH9F5D
 *
 */
public class InboxCampaignSavingChangesORU extends Page {

	@FindBy(xpath="//h4[@class='modal-title pull-left'][contains(text(), 'Saving Changes to ORU Campaign')]")
	private WebElement titleApplyDialogue;

//	@FindBy(xpath="//div[@class='row']//div[contains(text(), 'Number of VINs to be released')]/input")
	@FindBy(xpath = "//batch-detail/div[2]/div[1]/div[1]/div[2]/input")
	private WebElement textFieldNumberVinsReleased;

//	@FindBy(xpath="//div[@class='row']//div[contains(text(), 'to be \"notified\"')]/input")
	@FindBy(xpath = "//batch-detail/div[2]/div[1]/div[3]/div[2]/input")
	private WebElement textFieldNumberVinsNotified;

//	@FindBy(xpath="//div[@class='row']//div[contains(text(), 'to be \"scheduled\"')]/input")
	@FindBy(xpath = "//batch-detail/div[2]/div[1]/div[2]/div[2]/input")
	private WebElement textFieldNumberVinsScheduled;

	@FindBy(xpath="//div[@class='modal-footer']//button[@class='btn btn-primary'][contains(text(), 'Apply')]")
	private WebElement buttonApply;

	public InboxCampaignSavingChangesORU(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the titleApplyDialogue
	 */
	public WebElement getTitleApplyDialogue() {
		return this.titleApplyDialogue;
	}

	/**
	 * @return the textFieldNumberVinsReleased
	 */
	public WebElement getTextFieldNumberVinsReleased() {
		return textFieldNumberVinsReleased;
	}

	/**
	 * @return the textFieldNumberVinsNotified
	 */
	public WebElement getTextFieldNumberVinsNotified() {
		return textFieldNumberVinsNotified;
	}

	/**
	 * @return the textFieldNumberVinsScheduled
	 */
	public WebElement getTextFieldNumberVinsScheduled() {
		return textFieldNumberVinsScheduled;
	}

	/**
	 * @return the buttonApply
	 */
	public WebElement getButtonApply() {
		return this.buttonApply;
	}

	public void clickButtonApply() {
		this.getButtonApply().click();
	}

}
