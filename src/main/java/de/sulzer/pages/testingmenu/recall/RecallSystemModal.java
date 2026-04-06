package de.sulzer.pages.testingmenu.recall;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.sulzer.pages.genericelements.Page;
import testFramework.utilities.ReusableMethods;

public class RecallSystemModal extends Page {

    protected WebDriver webDriver;

    public RecallSystemModal(WebDriver driver) {

        super(driver);
        this.webDriver = driver;

    }

    @FindBy(id = "btn-recall-ctry-det-tbl-add")
    private WebElement addCountryToRecallButton;

    @FindBy(id = "btn-recall-ctry-det-tbl-edit")
    private List<WebElement> editCountryDetailsButtons;

    @FindBy(id = "btn-recall-ctry-det-tbl-del")
    private List<WebElement> deleteCountryDetailsButtons;

    @FindBy(xpath = "//p-calendar/span/input")
    private WebElement rolloutDateInputField;

    @FindBy(css = "select.form-control")
    private WebElement selectCountryCodeElement;
    private Select selectCountryCode;

    @FindBy(className="modal-title")
    private WebElement modalTitle;

    @FindBy(xpath = "//input[@id = 'inpt-new-recall-mod-id' or @id = 'inpt-edit-recall-mod-id']")
    private WebElement recallActionIdInputField;

    @FindBy(xpath="//input[@id ='inpt-new-recall-mod-title' or @id = 'inpt-edit-recall-mod-title']")
    private WebElement recallActionTitleInputField;

    @FindBy(xpath = "//textarea[@id = 'inpt-new-recall-mod-ctries' or @id = 'inpt-edit-recall-mod-disabled-ctries']")
    private WebElement disabledCountriesInputField;

    @FindBy (xpath = "//input[@id = 'inpt-add-crit-id' or @id = 'inpt-edit-crit-id']")
    private WebElement criterionIdControlInputField;

    @FindBy (xpath = "//input[@id ='inpt-add-crit-title' or @id = 'inpt-edit-crit-title']")
    private WebElement criterionTitleControlInputField;

    @FindBy(xpath = "//textarea[@id = 'inpt-add-crit-vehs' or @id = 'inpt-edit-crit-vehs']")
    private WebElement vehiclesInputField;

    @FindBy(xpath = "//textarea[@id = 'inpt-new-recall-mod-comment' or @id = 'inpt-edit-recall-mod-comment']")
    private WebElement commentInputField;

    @FindBy(id = "btn-new-recall-mod-create")
    private WebElement createRecallCampaignButton;

    @FindBy(css = "strong")
    private WebElement errorMessage;


    public WebElement getAddCountryToRecallButton() {
        return addCountryToRecallButton;
    }

    public void clickAddCountryToRecallButton() {
        this.getAddCountryToRecallButton().click();
    }

    public WebElement getEditCountryDetailsButton(Integer index) {
        return editCountryDetailsButtons.get(index);
    }

    public WebElement getDeleteCountryDetailsButton(Integer index) {
        return deleteCountryDetailsButtons.get(index);
    }

    public void clickEditCountryDetailsButton(Integer index) {
        getEditCountryDetailsButton(index).click();
    }

    public void clickDeleteCountryDetailsButton(Integer index) {
        getDeleteCountryDetailsButton(index).click();
    }

    public Select getSelectCountryCode() {
        if (null == this.selectCountryCode) {
            this.selectCountryCode = new Select(selectCountryCodeElement);
        }
        return selectCountryCode;
    }

    public void selectCountryCodeByName(String countryCode) {
        this.getSelectCountryCode().selectByValue(countryCode);
    }

    public void selectCountryCodeByIndex(Integer index) {
        this.getSelectCountryCode().selectByIndex(index);
    }

    public void selectGermanCountryCode() {
        this.selectCountryCodeByIndex(61);
    }

    public String getCurrentSelectedCountryCode() {
        return this.getSelectCountryCode().getFirstSelectedOption().getText();
    }

    public WebElement getModalTitle() {
        return modalTitle;
    }

    public String getModalTitleText() {
        return this.getModalTitle().getText();
    }

    public WebElement getRolloutDateInputField() {
        return rolloutDateInputField;
    }

    public void setRolloutDateInputField(String input) {

        this.getRolloutDateInputField().sendKeys(input + Keys.ENTER);
    }

    public WebElement getRecallActionIdInputField() {
        return recallActionIdInputField;
    }

    public String getRecallActionIdInputfieldValue() {
        return this.getRecallActionIdInputField().getAttribute("value");
    }

    public void clearRecallActionIdInputfield() {
        ReusableMethods.clearField(getRecallActionIdInputField());
    }

    public void setRecallActionIdInputFieldText(String input) {
        this.getRecallActionIdInputField().sendKeys(input);
    }

    public WebElement getRecallActionTitleInputField() {
        return recallActionTitleInputField;
    }

    public void clearRecallActionTitleInputField() {
        ReusableMethods.clearField(getRecallActionTitleInputField());
    }

    public String getRecallActionTitleInputFieldValue() {
        return this.getRecallActionTitleInputField().getAttribute("value");
    }

    public void setRecallActionTitleInputField(String input) {
        this.getRecallActionTitleInputField().sendKeys(input);
    }

    public WebElement getDisabledCountriesInputField() {
        return disabledCountriesInputField;
    }

    public void clearDisabledCountriesInputfield() {
        this.getDisabledCountriesInputField().clear();
    }

    public String getDisabledCountriesInputfieldValue() {
        return this.getDisabledCountriesInputField().getAttribute("value");
    }

    public void setDisabledCountriesInputField(String input) {
        this.getDisabledCountriesInputField().sendKeys(input);
    }

    public WebElement getCriterionIdControlInputField() {
        return criterionIdControlInputField;
    }

    public String getCriterionIdControlInputFieldValue() {
        return this.getCriterionIdControlInputField().getAttribute("value");
    }

    public void setCriterionIdControlInputField(String input) {
        this.getCriterionIdControlInputField().sendKeys(input);
    }

    public WebElement getCriterionTitleControlInputField() {
        return criterionTitleControlInputField;
    }

    public String getCriterionTitleControlInputFieldValue() {
        return this.getCriterionTitleControlInputField().getAttribute("value");
    }

    public void setCriterionTitleInputField(String input) {
        this.getCriterionTitleControlInputField().sendKeys(input);
    }

    public WebElement getVehiclesInputField() {
        return vehiclesInputField;
    }

    public void setVehiclesInputField(String input) {
        this.getVehiclesInputField().sendKeys(input);
    }

    public WebElement getCommentInputField() {
        return commentInputField;
    }


    public String getCommentInputFieldValue() {
        return this.getCommentInputField().getAttribute("value");
    }

    public void setCommentInputField(String input) {
        this.getCommentInputField().sendKeys(input);
    }

    public WebElement getErrorMessage() {
        return errorMessage;
    }

}
