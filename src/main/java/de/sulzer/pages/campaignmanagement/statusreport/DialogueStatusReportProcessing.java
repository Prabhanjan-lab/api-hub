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
public class DialogueStatusReportProcessing extends Page {

    @FindBy(xpath="//h4[contains(text(),'Status report processing')]")
    private WebElement dialogueTitle;

    @FindBy(xpath="//ngb-modal-window//div[@class='modal-body']/h4")
    private WebElement dialogueMessage;

    @FindBy(xpath="//button[@class='btn btn-primary']")
    private WebElement dialogueButtonOk;

    /**
     * @param driver
     */
    public DialogueStatusReportProcessing(WebDriver driver) {
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
     * @return the dialogueButtonOk
     */
    public WebElement getDialogueButtonOk() {
        return dialogueButtonOk;
    }

    public void clickDialogueButtonOk() {
        this.getDialogueButtonOk().click();
    }

}
