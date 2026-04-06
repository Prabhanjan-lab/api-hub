/**
 * 
 */
package de.sulzer.pages.campaignmanagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author GCH9F5D
 *
 */
public class CampaignManagementDeletionDialogue extends Page {

	@FindBy(xpath="//div//legend[contains(text(), 'Deletion - ORU Campaign')]")
	private WebElement titleDeletionORUCampaign;
	
	@FindBy(xpath="//button[@class='btn btn-primary'][contains(text(), 'Apply')]")
	private WebElement buttonApply;

	public CampaignManagementDeletionDialogue(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the titleDeletionORUCampaign
	 */
	public WebElement getTitleDeletionORUCampaign() {
		return titleDeletionORUCampaign;
	}

	/**
	 * @return the buttonApply
	 */
	public WebElement getButtonApply() {
		return buttonApply;
	}

	/**
	 * 
	 */
	public void clickButtonApply() {
		this.getButtonApply().click();
	}

}
