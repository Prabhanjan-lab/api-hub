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
public class DialogueOruDeletionConfirmation extends Page {

    @FindBy(xpath="//h4[.='Warning']")
    private WebElement headerWarning;

    @FindBy(xpath="//div[@class='modal-body']/p/strong")
    private WebElement idDlpPackages;

    @FindBy(xpath="//button[@id='submit-delete'][contains(text(), 'OK')]")
    private WebElement buttonConfirmation;

    /**
     * @param driver
     */
    public DialogueOruDeletionConfirmation(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the headerWarning
     */
    public WebElement getHeaderWarning() {
        return headerWarning;
    }

    /**
     * @return the idDlpPackages
     */
    public WebElement getIdDlpPackages() {
        return idDlpPackages;
    }

    /**
     * @return the buttonConfirmation
     */
    public WebElement getButtonConfirmation() {
        return buttonConfirmation;
    }

    public void clickButtonConfirmation() {
        this.getButtonConfirmation().click();
    }

}
