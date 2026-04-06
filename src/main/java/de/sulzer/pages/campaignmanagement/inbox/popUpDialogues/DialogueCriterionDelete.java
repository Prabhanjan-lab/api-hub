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
public class DialogueCriterionDelete extends Page {

    @FindBy(xpath="//legend[contains(text(),'Deletion - Criterion')]")
    private WebElement titleCriterionDeletion;

    @FindBy(xpath="//div//div[1]/input")
    private WebElement inputTextCriterionName;

    @FindBy(xpath="//div//div[2]/input")
    private WebElement inputTextCriterionAffectedVinCount;

    @FindBy(xpath="//button[contains(text(),'Cancel')]")
    private WebElement buttonCancel;

    @FindBy(xpath="//button[contains(text(),'Apply')]")
    private WebElement buttonApply;

    @FindBy(css = ".modal-footer button.btn.btn-primary")
    private WebElement criterionDeletionOkButton;

    public DialogueCriterionDelete(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the titleCriterionDeletion
     */
    public WebElement getTitleCriterionDeletion() {
        return titleCriterionDeletion;
    }

    /**
     * @return the inputTextCriterionName
     */
    public WebElement getInputTextCriterionName() {
        return inputTextCriterionName;
    }

    /**
     * @return the inputTextCriterionAffectedVinCount
     */
    public WebElement getInputTextCriterionAffectedVinCount() {
        return inputTextCriterionAffectedVinCount;
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

    public WebElement getCriterionDeletionOkButton() {
        return criterionDeletionOkButton;
    }

}
