package de.sulzer.pages.campaignmanagement.inbox;

import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class InboxPageTranslationProperties extends Page {

    @FindBy(xpath="//div[@class='modal-content']//div/h4")
    private WebElement titleTranslationProperties;

    @FindBy(xpath="//span[contains(text(),'Default language : German (de_DE)')]")
    private WebElement labelDefaultLanguageCode;

    @FindBy(xpath="//select[contains(@title,'Select the translation package')]")
    private WebElement selectTranslationPackageElement;

    private Select selectTranslationPackage;

    @FindBy(xpath="//*[contains(@class,'translation-form')]//input")
    private WebElement translateGoCatCheckbox;

    @FindBy(xpath="//button[contains(text(),'Back')]")
    private WebElement buttonBack;

    @FindBy(xpath="//button[contains(text(),'Next')]")
    private WebElement buttonNext;

    @FindBy(id="btn-camp-transl-create")
    private WebElement buttonCreateCampaign;

    @FindBy(xpath="//span[contains(text(),'Manually translation')]")
    private WebElement headerManuallyTranslation;

    @FindBy(xpath = "//*[@id=\"defaultLangTextArea\"]")
    private  WebElement inputDefaultLanguage;

    @FindBy(id="inpt-camp-transl-def-txt")
    private WebElement inputTranslation;

    @FindBy(xpath="//span[contains(text(),'Translation')]")
    private WebElement headerTranslation;

    @FindBy(xpath="//div[contains(@class, 'translations-selection-div')]//select")
    private WebElement dropDownLanguageSelection;

    @FindBy(xpath="//div/p/textarea[contains(@class, 'translations-textarea')]")
    private WebElement outputTranslation;

    @FindBy(xpath="//*[@class='progress-bar']")
    private WebElement progressBar;

    @FindBy(xpath="//*[@class='modal-body']//select")
    private WebElement selectLanguageElement;

    private Select selectLanguage;

    @FindBy(xpath="//button[@class='btn btn-default'][contains(text(), 'Cancel')]")
    private WebElement cancelButton;

//    @FindBy(xpath="//gocat-translation//button")
    @FindBy(xpath = "//a[@class = 'no-car-port-data']")
    private WebElement redButtonTranslateViaGocat;

    @FindBy(xpath="//button[contains(text(),'Confirm')]")
    private WebElement confirmButton;

    @FindBy(xpath="//button[contains(text(),'Ok')]")
    private WebElement buttonOk;

//    @FindBy(css = "div.panel:nth-child(2) > div:nth-child(2) > div:nth-child(1) > span:nth-child(2)")
    @FindBy(xpath = "//span[contains(text(),'character')]")
    private WebElement inputTranslationLength;

//    @FindBy(css = ".col-md-4.translations-col.number-sings-col")
    @FindBy(xpath = "//div[contains(text(),'character')]")
    private WebElement outputTranslationLength;

    public InboxPageTranslationProperties(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the titleTranslationProperties
     */
    public WebElement getTitleTranslationProperties() {
        return titleTranslationProperties;
    }

    /**
     * @return the labelDefaultLanguageCode
     */
    public WebElement getLabelDefaultLanguageCode() {
        return labelDefaultLanguageCode;
    }

    /**
     * @return the headerTranslation
     */
    public WebElement getHeaderTranslation() {
        return headerTranslation;
    }

    public WebElement getDropDownLanguageSelection() {
        return this.dropDownLanguageSelection;
    }

    public WebElement getInputTranslationLength() {
        return inputTranslationLength;
    }

    public WebElement getOutputTranslationLength() {
        return outputTranslationLength;
    }

    public Select getDropDownLanguageSelectionSelect() {
        return new Select(this.dropDownLanguageSelection);
    }

    public WebElement getOutputTranslationField(){
        return outputTranslation;
    }

    public void setOutputTranslationField(String input){
        this.getOutputTranslationField().clear();
        this.getOutputTranslationField().sendKeys(input);
    }

    public WebElement getProgressBar(){
        return progressBar;
    }
    public String getProgress(){
        return this.getProgressBar().getAttribute("aria-valuenow");
    }

    /**
     * @return the headerManuallyTranslation
     */
    public WebElement getHeaderManuallyTranslation() {
        return headerManuallyTranslation;
    }

    public WebElement getInputDefaultLanguageField(){
        return inputDefaultLanguage;
    }

    public WebElement getInputTranslationField(){
        return inputTranslation;
    }
    public void setInputTranslationField(String input){
        this.getInputTranslationField().clear();
        this.getInputTranslationField().sendKeys(input);
    }

    public WebElement getButtonCreateCampaign(){
        return buttonCreateCampaign;
    }
    public void clickButtonCreateCampaign(){
        this.getButtonCreateCampaign().click();
    }

    public void clickRedButtonTranslateViaGocat(){
        this.getRedButtonTranslateViaGocat().click();
    }

    public WebElement getRedButtonTranslateViaGocat(){
        return redButtonTranslateViaGocat;
    }
    public void clickConfirmButton(){
        this.getConfirmButton().click();
    }

    public WebElement getConfirmButton(){
        return confirmButton;
    }

    public WebElement getTranslateGoCatCheckbox(){
        return translateGoCatCheckbox;
    }
    public void clickTranslateGoCatCheckbox(){
        this.getTranslateGoCatCheckbox().click();
    }
    public WebElement getButtonBack(){
        return buttonBack;
    }
    public void clickButtonBack(){
        this.getButtonBack().click();
    }
    public WebElement getButtonNext(){
        return buttonNext;
    }
    public void clickButtonNext(){
        this.getButtonNext().click();
    }

    public Select getSelectTranslationPackage() {

        if (null == this.selectTranslationPackageElement) {
            this.selectTranslationPackage = new Select(selectTranslationPackageElement);
        }
        return selectTranslationPackage;
    }

    public void selectTranslationPackageByName(String domainCode) {
        this.getSelectTranslationPackage().selectByValue(domainCode);
    }

    public void selectTranslationPackageByIndex(Integer index) {
        this.getSelectTranslationPackage().selectByIndex(index);
    }

    public Select getSelectLanguage() {
        if (null == this.selectLanguage) {
            this.selectLanguage = new Select(selectLanguageElement);
        }
        return selectLanguage;
    }

    public WebElement getCancelButton() {
        return this.cancelButton;
    }

    public void clickCancelButton() {
        this.getCancelButton().click();
    }

    public WebElement getButtonOK() {
        return this.buttonOk;
    }

    public void clickButtonOK() {
        this.getButtonOK().click();
    }

}