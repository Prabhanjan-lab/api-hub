package de.sulzer.pages.campaignmanagement;

import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import testFramework.utilities.ReusableMethods;

import java.util.List;

/**
 * @author Sulzer Gmbh
 *
 */
public class RolloutStatusPage extends Page {

    private WebDriver webDriver;

    public RolloutStatusPage(WebDriver driver){
        super(driver);
        webDriver = driver;
    }
//    @FindBy(xpath = "//table-search//input")
    @FindBy(css = ".input-group.search-input input")
    private WebElement campaignSearchField;

//    @FindBy(xpath = "//h3[@class='panel-title']")
    @FindBy(xpath = "//h3[@class='panel-title ng-star-inserted']")
    private WebElement title;

    @FindBy(xpath="number-of-entries")
    private WebElement numberOfEntries;

    @FindAll(
        @FindBy(xpath="//table//tbody")
    )
    private List<WebElement> listTableRollOutStatus;

    @FindBy(xpath="//table//tbody[1]//a[contains(@class, 'toggler')]")
    private WebElement firstTableExpansionLink;

    @FindBy(xpath="//table//tbody[1]//td[4]")
    private WebElement firstTableStatus;

    @FindBy(xpath="//table//tbody[1]//td[6]")
    private WebElement firstTableWaitingVins;

    @FindBy(xpath="//table//tbody[1]//td[6]/span")
    private WebElement firstTableWaitingVinsOpenDetailsDialogue;

    @FindBy(xpath="//table//tbody[1]//td[7]")
    private WebElement firstTableVinsRunning;

    @FindBy(xpath="//table//tbody[1]//td[7]/span")
    private WebElement firstTableRunningVinsOpenDetailsDialogue;

    @FindBy(xpath="//table//tbody[1]//td[8]")
    private WebElement firstTableVinsBlocked;

    @FindBy(xpath="//table//tbody[1]//td[8]/span")
    private WebElement firstTableBlockedVinsOpenDetailsDialogue;

    @FindBy(xpath="//table//tbody[1]//td[9]")
    private WebElement firstTableVinsAborted;

    @FindBy(xpath="//table//tbody[1]//td[9]/span")
    private WebElement firstTableAbortedVinsOpenDetailsDialogue;

    @FindBy(xpath="//table//tbody[1]//td[10]")
    private WebElement firstTableVinsFinished;

    @FindBy(xpath="//table//tbody[1]//td[10]/span")
    private WebElement firstTableFinishedVinsOpenDetailsDialogue;

    @FindBy(xpath="//table//tbody[1]//td[11]")
    private WebElement firstTableTotalRecordsVins;

    @FindBy(xpath="//table//tbody[1]//td[11]/span[2]")
    private WebElement firstTableTotalRecordsOpenDetailsDialogue;

    @FindAll(
        @FindBy(xpath="//table//tbody//td[2]")
    )
    private List<WebElement> listColumnRollOutOruCampaign;

    @FindAll(
        @FindBy(xpath="//table//tbody//td[4]")
    )
    private List<WebElement> listColumnRollOutStatus;

    public WebElement getCampaignSearchField(){
        return campaignSearchField;
    }

    public void setCampaignSearchField(String input){
        this.getCampaignSearchField().sendKeys(input + Keys.ENTER);
    }

    public WebElement getTitle(){
        return this.title;
    }

    /**
     * @return the numberOfEntries
     */
    public WebElement getNumberOfEntries() {
        return numberOfEntries;
    }

    /**
     * @return the listTableRollOutStatus
     */
    public List<WebElement> getListTableRollOutStatus() {
        return listTableRollOutStatus;
    }

    /**
     * @return the firstTableExpansionLink
     */
    public WebElement getFirstTableExpansionLink() {
        return firstTableExpansionLink;
    }

    public void clickFirstTableExpansionLink() {
        this.getFirstTableExpansionLink().click();
    }

    /**
     * @return the firstTableStatus
     */
    public WebElement getFirstTableStatus() {
        return firstTableStatus;
    }

    /**
     * @return the firstTableWaitingVins
     */
    public WebElement getFirstTableWaitingVins() {
        return firstTableWaitingVins;
    }

    public WebElement getFirstTableWaitingVinsOpenDetailsDialogue() {
        return firstTableWaitingVinsOpenDetailsDialogue;
    }

    public void clickFirstTableWaitingVinsOpenDetailsDialogue() {
        this.firstTableWaitingVinsOpenDetailsDialogue.click();
    }

    /**
     * @return the firstTableVinsRunning
     */
    public WebElement getFirstTableVinsRunning() {
        return firstTableVinsRunning;
    }

    public void clickFirstTableRunningVinsOpenDetailsDialogue() {
        this.firstTableRunningVinsOpenDetailsDialogue.click();
    }

    /**
     * @return the firstTableVinsBlocked
     */
    public WebElement getFirstTableVinsBlocked() {
        return firstTableVinsBlocked;
    }

    public void clickFirstTableBlockedVinsOpenDetailsDialogue() {
        this.firstTableBlockedVinsOpenDetailsDialogue.click();
    }

    /**
     * @return the firstTableVinsAborted
     */
    public WebElement getFirstTableVinsAborted() {
        return firstTableVinsAborted;
    }

    public void clickFirstTableAbortedVinsOpenDetailsDialogue() {
        this.firstTableAbortedVinsOpenDetailsDialogue.click();
    }

    /**
     * @return the firstTableVinsFinished
     */
    public WebElement getFirstTableFinishedVins() {
        return firstTableVinsFinished;
    }

    public void clickFirstTableFinishedVinsOpenDetailsDialogue() {
        this.firstTableFinishedVinsOpenDetailsDialogue.click();
    }

    /**
     * @return the firstTableTotalRecordsVins
     */
    public WebElement getFirstTableTotalRecordsVins() {
        return firstTableTotalRecordsVins;
    }

    public void clickFirstTableTotalRecordsVinsOpenDetailsDialogue() {
        this.firstTableTotalRecordsOpenDetailsDialogue.click();
    }

    /**
     * @return the listColumnRollOutOruCampaign
     */
    public List<WebElement> getListColumnRollOutOruCampaign() {
        return listColumnRollOutOruCampaign;
    }

    /**
     * @return the listColumnRollOutStatus
     */
    public List<WebElement> getListColumnRollOutStatus() {
        return listColumnRollOutStatus;
    }

    public void searchCampaignInRolloutStatus(String CAMPAIGN_ID){

        ReusableMethods.waitForClickablility(getCampaignSearchField(), 10, webDriver);
        this.getCampaignSearchField().clear();
        this.getCampaignSearchField().sendKeys(CAMPAIGN_ID);
        try {
            ReusableMethods.waitForVisibility(webDriver.findElement(By.xpath("//td[.=' " + CAMPAIGN_ID + " ']")), 10, webDriver);
        } catch (Exception ignored){
        }

    }

    public boolean isCampaignInRolloutStatusPresent(String CAMPAIGN_ID) {

        try {
            ReusableMethods.waitForVisibility(webDriver.findElement(By.xpath("//td[.=' " + CAMPAIGN_ID + " ']")), 10, webDriver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickOruCampaignInRolloutStatus(String CAMPAIGN_ID){
        WebElement element = webDriver.findElement(By.xpath("//td/a[.=' " + CAMPAIGN_ID + " ']"));
        ReusableMethods.clickJS(element, webDriver);
    }

}