package de.sulzer.pages.testingmenu.recall;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import testFramework.utilities.ReusableMethods;

public class EditRecallSystemModal extends RecallSystemModal {

    protected WebDriver _driver;

    public EditRecallSystemModal(WebDriver driver) {
        super(driver);
        this._driver = driver;
    }
    @FindBy(xpath="//div//textarea[@id='vehiclesControl']")
    private WebElement textAreaVin;

    @FindBy(xpath="//button[contains(text(), 'Save')]")
//    @FindBy(xpath = "//*[@id=\"onupAppRoot\"]/body/ngb-modal-window/div/div/edit-modal-component/div/div/form/div[2]/button[1]")
//    @FindBy(css = ".btn-primary")
    private WebElement saveButton;

//    @FindBy(xpath="//*[@aria-hidden='false']//button")
    @FindBy(xpath = "(//p-tabpanel/div/div/div/div/div//button)[2]/i")
    private WebElement deleteCriterionButton;

    @FindBy(xpath="//confirm-dialog//button[text()='Confirm deletion']")
    private WebElement confirmDelete;


    /**
     * @return the textAreaVin
     */
    public WebElement getTextAreaVin() {


        // explicit search of VIN textarea in editing dialogue
        WebElement textAreaVin = _driver.findElement(
//                By.xpath(
//                        "//p-tabview/div/ul/li/a[@role='tab'][@aria-selected='true']/parent::*/parent::*/following-sibling::*/p-tabpanel[1]/div/div/div[4]/div/textarea"));
        By.cssSelector("#vehiclesControl0"));

        return textAreaVin;
    }

    public void setTextAreaVin(String vinInput) {
        this.getTextAreaVin().clear();
        this.getTextAreaVin().sendKeys(vinInput);
    }

    public WebElement getConfirmDelete() {
        return confirmDelete;
    }

    public void clickConfirmDelete() {
        this.getConfirmDelete().click();
    }

    public WebElement getDeleteCriterionButton() {
        return deleteCriterionButton;
    }

    public void clickDeleteCriterionButton() {
//        this.getDeleteCriterionButton().click();
        ReusableMethods.clickJS(getDeleteCriterionButton(), webDriver);
    }

    public WebElement getSaveButton() {
        return saveButton;
    }

    public void clickSaveButton() {
        ReusableMethods.hoverJS(getSaveButton(), _driver);
//        ReusableMethods.clickJS(getSaveButton(), getWebDriver());
        ReusableMethods.clickElement(getSaveButton(), _driver);
//        this.saveButton.click();
    }
}
