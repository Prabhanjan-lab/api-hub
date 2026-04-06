/**
 *
 */
package de.sulzer.pages.campaignmanagement.rolloutstatus;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer Gmbh
 *
 */
public class DialogueCampaignRolloutProgressDetails extends Page {

    @FindBy(xpath="//h4[@class='modal-title']")
    private WebElement dialogueTitle;

    @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[1]//p-autocomplete//span//input[@type='text']")
    private WebElement textFieldCampaignId;

    @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[1]//p-autocomplete//span//button")
    private WebElement buttonSelectCampaignId;

    @FindAll(
        @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[1]//p-autocomplete//span//div//li")
    )
    private List<WebElement> dropDownCampaignIds;

    @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[2]//p-autocomplete//span//input[@type='text']")
    private WebElement textFieldCriteria;

    @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[2]//p-autocomplete//span//button")
    private WebElement buttonSelectCriteria;

    @FindAll(
        @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[2]//p-autocomplete//span//div//li")
    )
    private List<WebElement> dropDownCriterias;

    @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[3]//p-autocomplete//span//input[@type='text']")
    private WebElement textFieldBatchName;

    @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[3]//p-autocomplete//span//button")
    private WebElement buttonSelectBatchName;

    @FindAll(
        @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[3]//p-autocomplete//span//div//li")
    )
    private List<WebElement> dropDownBatchNames;

    @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[4]//p-autocomplete//span//input[@type='text']")
    private WebElement textFieldVinGroups;

    @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[4]//p-autocomplete//span//button")
    private WebElement buttonVinGroups;

    @FindAll(
        @FindBy(xpath="//div[@class='rollout-progress modal-body']/div[4]//p-autocomplete//span//div//li")
    )
    private List<WebElement> dropDownVinGroups;

    @FindBy(xpath="//div[@class='input-group']//input[@type='text']")
    private WebElement textFieldVinGroupSearch;

    @FindAll(
        @FindBy(xpath="(//table//tbody)[2]/tr/td[1]")
    )
    private List<WebElement> listVinGroupVins;

    @FindAll(
        @FindBy(xpath="//table//tbody/tr/td[1]")
    )
    private List<WebElement> listVinGroupVinsColumnVin;

    @FindAll(
        @FindBy(xpath="//table//tbody/tr/td[2]")
    )
    private List<WebElement> listVinGroupVinsColumnVcso;

    @FindBy(xpath="//button[contains(text(),'Refresh')]")
    private WebElement buttonRefresh;

    @FindBy(xpath="//button[contains(text(),'×')]")
    private WebElement buttonClose;

    @FindBy(css = "th.p-element:nth-child(1)")
//    @FindBy(css = "th.ui-sortable-column:nth-child(1)")
    private WebElement vinHeader;

//    @FindBy(css = "div.row:nth-child(7) > div:nth-child(1) > p-paginator:nth-child(2) > div:nth-child(1) > a:nth-child(4) > span:nth-child(1)")
    @FindBy(xpath = "(//span[@class = 'p-paginator-icon pi pi-angle-right'])[2]")
    private WebElement nextButton;

//    @FindBy(css = ".ui-table-tbody > tr:nth-child(1) > td:nth-child(1)")
//    @FindBy(xpath = "//input[@class = 'form-control']")
//    private WebElement vinSearchResult;

    /**
     * @param driver
     */
    public DialogueCampaignRolloutProgressDetails(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the dialogueTitle
     */
    public WebElement getDialogueTitle() {
        return dialogueTitle;
    }

    /**
     * @return the textFieldCampaignId
     */
    public WebElement getTextFieldCampaignId() {
        return textFieldCampaignId;
    }

    public void setTextFieldCampaignIdSearch(String campaignId) {
        this.textFieldCampaignId.clear();
        this.textFieldCampaignId.sendKeys(campaignId);
    }

    /**
     * @return the buttonSelectCampaignId
     */
    public WebElement getButtonSelectCampaignId() {
        return buttonSelectCampaignId;
    }

    public void clickButtonSelectCampaignId() {
        this.getButtonSelectCampaignId().click();
    }

    /**
     * @return the dropDownCampaignIds
     */
    public List<WebElement> getDropDownCampaignIds() {
        return dropDownCampaignIds;
    }

    /**
     * @return the textFieldCriteria
     */
    public WebElement getTextFieldCriteria() {
        return textFieldCriteria;
    }

    public void setTextFieldCampaignIdCriteria(String criteria) {
        this.textFieldCriteria.clear();
        this.textFieldCriteria.sendKeys(criteria);
    }

    /**
     * @return the buttonSelectCriteria
     */
    public WebElement getButtonSelectCriteria() {
        return buttonSelectCriteria;
    }

    public void clickButtonSelectCriteria() {
        this.getButtonSelectCriteria().click();
    }

    /**
     * @return the dropDownCriterias
     */
    public List<WebElement> getDropDownCriterias() {
        return dropDownCriterias;
    }

    /**
     * @return the textFieldBatchName
     */
    public WebElement getTextFieldBatchName() {
        return textFieldBatchName;
    }

    public void setTextFieldBatchName(String batchName) {
        this.textFieldBatchName.clear();
        this.textFieldBatchName.sendKeys(batchName);
    }

    /**
     * @return the buttonSelectBatchName
     */
    public WebElement getButtonSelectBatchName() {
        return buttonSelectBatchName;
    }

    public void clickButtonSelectBatchName() {
        this.getButtonSelectBatchName().click();
    }

    /**
     * @return the dropDownBatchNames
     */
    public List<WebElement> getDropDownBatchNames() {
        return dropDownBatchNames;
    }

    /**
     * @return the textFieldVinGroups
     */
    public WebElement getTextFieldVinGroups() {
        return textFieldVinGroups;
    }

    /**
     * @return the buttonSelectBatchName
     */
    public WebElement getButtonSelectVinGroup() {
        return buttonVinGroups;
    }

    public void clickButtonSelectVinGroup() {
        this.getButtonSelectVinGroup().click();
    }

    /**
     * @return the dropDownVinGroups
     */
    public List<WebElement> getDropDownVinGroups() {
        return dropDownVinGroups;
    }

    /**
     * @return the textFieldVinGroupSearch
     */
    public WebElement getTextFieldVinGroupSearch() {
        return textFieldVinGroupSearch;
    }

    /**
     * @return the listVinGroupVins
     */
    public List<WebElement> getListVinGroupVins() {
        return listVinGroupVins;
    }

    /**
     * @return the listVinGroupVinsColumnVin
     */
    public List<WebElement> getListVinGroupVinsColumnVin() {
        return listVinGroupVinsColumnVin;
    }

    /**
     * @return the listVinGroupVinsColumnVcso
     */
    public List<WebElement> getListVinGroupVinsColumnVcso() {
        return listVinGroupVinsColumnVcso;
    }

    /**
     * @return the buttonRefresh
     */
    public WebElement getButtonRefresh() {
        return buttonRefresh;
    }

    public void clickButtonRefresh() {
        this.getButtonRefresh().click();
    }

    /**
     * @return the buttonClose
     */
    public WebElement getButtonClose() {
        return buttonClose;
    }

    public void clickButtonClose() {
        this.getButtonClose().click();
    }

    public WebElement getVinHeader() {return vinHeader;}

    public WebElement getNextButton() {
        return nextButton;
    }

    public WebElement getVinSearchResult(String vin, WebDriver driver) {
        WebElement element = driver.findElement(By.xpath("//td[.= " + "' " + vin + " '" + "]"));
        return element;
    }

}
