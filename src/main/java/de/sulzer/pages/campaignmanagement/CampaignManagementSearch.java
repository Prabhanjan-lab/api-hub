package de.sulzer.pages.campaignmanagement;

import de.sulzer.pages.genericelements.PageWithNavigation;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CampaignManagementSearch extends PageWithNavigation{

    public CampaignManagementSearch(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//search-oru-campaign-table//input")
    private WebElement campaignSearchField;

    @FindBy(xpath="//table/tbody/tr/td[1]")
    private WebElement campaignTitle;

    @FindBy(xpath="//table/tbody/tr/td[1]")
    private WebElement s42Title;

    @FindBy(xpath="//table/tbody/tr/td[1]")
    private WebElement recallTitle;

    @FindBy(xpath="//table/tbody/tr/td[2]")
    private WebElement campaignNumber;

    @FindBy(xpath="//table/tbody/tr/td[3]")
    private WebElement createdOn;

    @FindBy(xpath="//table/tbody/tr/td[4]")
    private WebElement creator;

    @FindAll({
        @FindBy(xpath="//tbody//tr//td//button[contains(@class, 'btn btn-default btn-xs')]")
    })
    private List<WebElement> listButtonsEdit;

    @FindAll({
        @FindBy(xpath="//tbody//tr//td//button[contains(@class, 'btn btn-danger btn-xs')]")
    })
    private List<WebElement> listButtonsDelete;

    @FindBy(xpath="//button[contains(text(),'Apply')]")
    private WebElement applyDeleteButton;

    @FindBy(xpath="//button[text()='Cancel']")
    private WebElement cancelDelete;

    @FindBy(xpath="//button[text()='Apply']")
    private WebElement confirmDelete;

    @FindAll({
        @FindBy(xpath="//table//tbody//tr")
    })
    private List<WebElement> tableEntriesCampaigns;

    @FindBy(id = "inf-number-of-entries-recs")
    private WebElement numberOfEntriesParagraph;

    @FindBy(id = "submit-delete")
    private WebElement okButton;

    public WebElement getOkButton() {
        return okButton;
    }

    public WebElement getConfirmDelete(){
        return confirmDelete;
    }

    public void clickConfirmDelete(){
        this.getConfirmDelete().click();
    }

    public WebElement getCancelDelete(){
        return cancelDelete;
    }

    public void clickCancelDelete(){
        this.getCancelDelete().click();
    }

    public WebElement getCampaignSearchField(){
        return campaignSearchField;
    }

    public WebElement getCampaignTitle(){
        return campaignTitle;
    }

    public String getCampaignTitleText(){
        return this.getCampaignTitle().getText().trim();
    }

    public WebElement getS42Title(){
        return s42Title;
    }
    public String s42TitleText(){
        return this.getS42Title().getText();
    }

    public WebElement getRecallTitle(){
        return recallTitle;
    }
    public String recallTitleText(){
        return this.getRecallTitle().getText();
    }

    public WebElement getCampaignNumber(){
        return campaignNumber;
    }
    public String campaignNumberText(){
        return this.getCampaignNumber().getText();
    }

    public WebElement getCreatedOn(){
        return createdOn;
    }
    public String createdOnText(){
        return this.getCreatedOn().getText();
    }

    public WebElement getCreator(){
        return creator;
    }
    public String creatorText(){
        return this.getCreator().getText();
    }

    public List<WebElement> getListButtonsEdit(){
        return listButtonsEdit;
    }

    public WebElement getFirstButtonEdit(){
        return listButtonsEdit.get(0);
    }

    public void clickFirstButtonEdit(){
        this.getFirstButtonEdit().click();
    }

    public List<WebElement> getListButtonsDelete(){
        return listButtonsDelete;
    }

    public WebElement getFirstButtonDelete(){
        return listButtonsDelete.get(0);
    }

    public void clickFirstButtonDelete(){
        this.getFirstButtonDelete().click();
    }

    public WebElement getApplyDeleteButton(){
        return applyDeleteButton;
    }

    public void clickApplyDeleteButton(){
        this.getApplyDeleteButton().click();
    }

    public List<WebElement> getTableEntries(){
        return this.tableEntriesCampaigns;
    }

    public WebElement getNumberOfEntriesParagraph() {
        return numberOfEntriesParagraph;
    }

    public String getNumberOfEntriesParagraphText() {
        return getNumberOfEntriesParagraph().getText().trim();
    }

    public void searchCampaign(String input){

        ReusableMethods.waitForClickablility(getCampaignSearchField(), 10, _driver);
        this.getCampaignSearchField().clear();
        this.getCampaignSearchField().sendKeys(input);

        // implicit and explicit wait does not work here.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        ReusableMethods.waitForVisibility(getCampaignTitle(), 5, _driver);

//        try {
//            String element = "(//td[contains(text(), '"+ input + "')])[1]";
//            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath(element)
//            ), 10, _driver);
//        } catch (Exception ignored){
//        }

    }

//    public boolean isFirstEntryByCampaignId(String campaignId) {
//
//        try {
//            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("(//td[contains(text(), '"+ campaignId + "')])[1]")
//            ), 10, _driver);
//            return true;
//        } catch (Exception e){
//            return false;
//        }
//    }

    public boolean isFirstEntryByCampaignId(String campaignId) {

//        try {
//            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("(//td[contains(text(), '"+ campaignId + "')])[1]")
//            ), 10, _driver);
//            return true;
//        } catch (Exception e){
//            return false;
//        }


//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        WebDriverWait wait = new WebDriverWait(_driver, 5); // Bekleme süresi 10 saniye olarak ayarlanmıştır
        String numberOfEntriesText = "";
        try {
            WebElement numberOfEntriesParagraph = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inf-number-of-entries-recs")));
            numberOfEntriesText = numberOfEntriesParagraph.getText();
            if (numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
                return true;
            } else if (numberOfEntriesText.contains(ConstantsGuiTexts.ZERO_OF_ZERO_FROM_ZERO_RECORDS)) {
                return false;
            } else {
                throw new AssertionError("Invalid number of entries: " + numberOfEntriesText);
            }
        } catch (AssertionError e) {
            System.err.println(e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }







//        String numberOfEntriesText = this.getNumberOfEntriesParagraph().getText();
//        try {
//            if (numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
//                return true;
//            } else if (numberOfEntriesText.contains(ConstantsGuiTexts.ZERO_OF_ZERO_FROM_ZERO_RECORDS)) {
//                return false;
//            } else {
//                throw new AssertionError("Invalid number of entries: " + numberOfEntriesText);
//            }
//        } catch (AssertionError e) {
//            System.err.println(e.getMessage());
//            return false;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }

//        try {
//            ReusableMethods.waitForTextToBePresentInElement(getNumberOfEntriesParagraph(), ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS, 5, _driver);
//            return true;
//        } catch (Exception e) {
//            ReusableMethods.waitForTextToBePresentInElement(getNumberOfEntriesParagraph(), ConstantsGuiTexts.ZERO_OF_ZERO_FROM_ZERO_RECORDS, 5, _driver);
//            return false;
//        }


    }





}