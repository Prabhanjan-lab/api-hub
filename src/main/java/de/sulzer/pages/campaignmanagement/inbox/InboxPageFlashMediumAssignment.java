package de.sulzer.pages.campaignmanagement.inbox;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;

public class InboxPageFlashMediumAssignment extends Page {

	public InboxPageFlashMediumAssignment(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//div/h4[@class='modal-title pull-left'][contains(text(), 'Flashmedium Assignment')]")
	private WebElement dialogueTitleFlashMediumAssignment;

	@FindAll(
		@FindBy(xpath="//div//div[contains(@id, '.')]/label/input") // id contains ID of ReCall/Service42
	)
	List<WebElement> checkBoxesCriteriaForFlashMediumAssignment;

	@FindBy(xpath="//div[@class='modal-footer']/button[contains(text(), 'Apply')]")
	private WebElement buttonApply;

	@FindBy(xpath="//div[@class='modal-footer']/button[contains(text(), 'Cancel')]")
	private WebElement buttonCancel;

	/**
	 * @return the dialogueTitleFlashMediumAssignment
	 */
	public WebElement getDialogueTitleFlashMediumAssignment() {
		return dialogueTitleFlashMediumAssignment;
	}

	public void selectAllCheckBoxesFlashMediumAssignment() {

		for (WebElement we : this.checkBoxesCriteriaForFlashMediumAssignment) {
			we.click();
		}

	}

	public void selectCheckBoxesFlashMediumAssignment(int amount) {

		if (amount < 0) {
			return;
		}

		if (0 == amount) {

			for (WebElement we : this.checkBoxesCriteriaForFlashMediumAssignment) {
				we.click();
			}

		} else {

			for (int i = 0; i < amount; i++) {
				this.checkBoxesCriteriaForFlashMediumAssignment.get(i).click();
			}

		}

	}

	public WebElement getButtonApply(){
		return buttonApply;
	}

	public void clickButtonApply(){
		this.getButtonApply().click();
	}

	public WebElement getButtonCancel(){
		return buttonCancel;
	}

	public void clickButtonCancel(){
		this.getButtonCancel().click();
	}

}