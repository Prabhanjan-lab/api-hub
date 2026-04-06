package de.sulzer.pages.caradministration.popUpDialogues;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

public class DialogueCampaignDetails extends Page {

	@FindBy(xpath="//h4[@class='modal-title']")
	private WebElement headerDialogueCampaignDetails;

	@FindAll(
		@FindBy(xpath="//vehicle-campaign-status-table//table/tbody/tr")
	)
	private List<WebElement> listCampaignDetailStates;

	@FindBy(xpath="//button[@class='btn btn-primary']")
	private WebElement buttonCloseDialogue;

	public DialogueCampaignDetails(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the headerDialogueCampaignDetails
	 */
	public WebElement getHeaderDialogueCampaignDetails() {
		return headerDialogueCampaignDetails;
	}

	/**
	 * @return the listCampaignDetailStates
	 */
	public List<WebElement> getListCampaignDetailStates() {
		return listCampaignDetailStates;
	}

	/**
	 * @return the buttonCloseDialogue
	 */
	public WebElement getButtonCloseDialogue() {
		return buttonCloseDialogue;
	}

	public void clickButtonCloseDialogue() {
		this.getButtonCloseDialogue().click();
	}

}
