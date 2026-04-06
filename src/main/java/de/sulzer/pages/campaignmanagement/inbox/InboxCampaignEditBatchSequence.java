/**
 *
 */
package de.sulzer.pages.campaignmanagement.inbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class InboxCampaignEditBatchSequence extends Page {

    @FindBy(xpath="//button[contains(text(), 'Vehicle Filtering')]")
    private WebElement buttonNextVehicleFiltering;

    @FindBy(xpath="//button[contains(text(), 'Test filter')]")
    private WebElement buttonNextTestFilter;

    @FindBy(xpath = "//new-vehicles-component//input[@type='checkbox' ]")
    private WebElement checkBoxMoveVehicleFromVehiclePool;

    @FindBy(xpath="//button[contains(text(), 'Edit batch')]")
    private WebElement buttonEditBatch;

    @FindBy(xpath="//button[contains(text(), 'Create batch')]")
    private WebElement buttonCreateBatch;

    @FindBy(xpath="//button[contains(@class, 'btn btn-default pull-right')][contains(text(), 'Back')]")
    private WebElement buttonBack;

    @FindBy(xpath="//button[contains(@class, 'btn btn-default pull-left')][contains(text(), 'Cancel')]")
    private WebElement buttonCancel;

    public InboxCampaignEditBatchSequence(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the buttonNextVehicleFiltering
     */
    public WebElement getButtonNextVehicleFiltering() {
        return this.buttonNextVehicleFiltering;
    }

    public void clickButtonNextVehicleFiltering() {
        this.getButtonNextVehicleFiltering().click();
    }

    /**
     * @return the buttonNextTestFilter
     */
    public WebElement getButtonNextTestFilter() {
        return buttonNextTestFilter;
    }

    public void clickButtonNextTestFilter() {
        this.getButtonNextTestFilter().click();
    }

    /**
     * @return the checkBoxMoveVehicleFromVehiclePool
     */
    public WebElement getCheckBoxMoveVehicleFromVehiclePool() {
        return this.checkBoxMoveVehicleFromVehiclePool;
    }

    public void clickCheckBoxMoveVehicleFromVehiclePool() {
        this.getCheckBoxMoveVehicleFromVehiclePool().click();
    }

    /**
     * @return the buttonEditBatch
     */
    public WebElement getButtonEditBatch() {
        return buttonEditBatch;
    }

    public void clickButtonEditBatch() {
        this.getButtonEditBatch().click();
    }


    /**
     * @return the buttonCreateBatch
     */
    public WebElement getButtonCreateBatch() {
        return buttonCreateBatch;
    }

    public void clickButtonCreateBatch() {
        this.getButtonCreateBatch().click();
    }

    /**
     * @return the buttonBack
     */
    public WebElement getButtonBack() {
        return buttonBack;
    }

    public void clickButtonBack() {
        this.getButtonBack().click();
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

}
