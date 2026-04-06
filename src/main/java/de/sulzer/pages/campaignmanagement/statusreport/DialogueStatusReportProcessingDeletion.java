/**
 *
 */
package de.sulzer.pages.campaignmanagement.statusreport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class DialogueStatusReportProcessingDeletion extends Page {

    @FindBy(xpath="//span[@class='confirm-modal-header']")
    private WebElement dialogueTitle;

    @FindBy(xpath="//h4")
    private WebElement dialogueMessage;

    @FindBy(xpath="//button[contains(text(),'Confirm')]")
    private WebElement dialogueButtonConfirm;

    @FindBy(xpath="//button[contains(text(),'Cancel')]")
    private WebElement dialogueButtonCancel;

    /**
     * @param driver
     */
    public DialogueStatusReportProcessingDeletion(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the dialogueTitle
     */
    public WebElement getDialogueTitle() {
        return dialogueTitle;
    }

    /**
     * @return the dialogueMessage
     */
    public WebElement getDialogueMessage() {
        return dialogueMessage;
    }

    /**
     * @return the dialogueButtonConfirm
     */
    public WebElement getDialogueButtonConfirm() {
        return dialogueButtonConfirm;
    }

    public void clickDialogueButtonConfirm() {
        this.getDialogueButtonConfirm().click();
    }

    /**
     * @return the dialogueCancel
     */
    public WebElement getDialogueButtonCancel() {
        return dialogueButtonCancel;
    }

    public void clickDialogueCancel() {
        this.getDialogueButtonCancel().click();
    }

}
