/**
 *
 */
package de.sulzer.pages.campaignmanagement.inbox.popUpDialogues;

import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * @author Bege
 *
 */
public class DialogueAddNewCriterion extends Page {

    public DialogueAddNewCriterion(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath="//div[@class='modal-dialog']//h4[@class='modal-title pull-left']")
    private WebElement headerDialogueInspectVehicles;

//    @FindBy(xpath="//ul/p-treenode/li/div/div[contains(@class, 'ui-chkbox')]/div/span")
    @FindBy(css = ".p-checkbox-box.p-highlight span")
    private WebElement checkboxRootCheckbox;

    @FindAll(
        @FindBy(xpath="//li/ul/p-treenode/li/div/div[@class='ui-chkbox']/div")
    )
    private List<WebElement> listCriteriaCheckboxes;

//    @FindBy(xpath="//button[@class='btn btn-primary pull-left']")
    @FindBy(xpath = "//*[.=' Cancel ']")
    private WebElement buttonCancel;

    @FindBy(xpath="//button[@class='btn btn-primary pull-right']")
    private WebElement buttonApply;


    /**
     * @return the headerDialogueInspectVehicles
     */
    public WebElement getHeaderDialogueInspectVehicles() {
        return headerDialogueInspectVehicles;
    }

    /**
     * @return the checkboxRootCheckbox
     */
    public WebElement getCheckboxRootCheckbox() {
        return checkboxRootCheckbox;
    }

    public void clickCheckboxRootCheckbox() {
        this.getCheckboxRootCheckbox().click();
    }

    /**
     * @return the listCriteriaCheckboxes
     */
    public List<WebElement> getListCriteriaCheckboxes() {
        return listCriteriaCheckboxes;
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
        this.getButtonApply().click();
    }

}
