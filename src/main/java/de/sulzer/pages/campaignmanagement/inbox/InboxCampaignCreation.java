/**
 *
 */
package de.sulzer.pages.campaignmanagement.inbox;

import java.util.List;

import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.sulzer.pages.genericelements.Page;

/**
 * Upload of Firmware and creation of campaign.
 *
 * @author Sulzer GmbH
 *
 */
// TODO chould be renamed to 'InboxCampaingApprovalProcess'?
public class InboxCampaignCreation extends Page {

    private WebDriver driver;

    public InboxCampaignCreation(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(xpath="//span[@class='fa fa-language campaign-translation-button']")
    private WebElement buttonTranslationProperty;

    @FindBy(xpath="//a[contains(text(), 'Status of firmware processing')]")
    private WebElement statusFirmwareProcessing;

//    @FindBy(xpath="//ngb-progressbar[contains(@class, 'processing-status-overall')]/div[@class='progress']/div[contains(@class, 'progress-bar')]/span/span[7]")
    @FindBy(xpath = "//span[.='- 100 %']")
    private WebElement progressBarOverall;

    @FindBy(xpath="//a[contains(@class,'fa fa-angle-')]")
    private WebElement foldingSingleProgressBars;

    @FindBy(xpath="//p-checkbox")
    private WebElement checkBoxApproved;

    @FindBy(xpath="//button[contains(@title, 'Edit batch')]")
//    @FindBy(xpath = "//button[@title ='Edit batch']")
    private WebElement buttonEditBatch;

    @FindBy(xpath="//td[@class='vin-column']/a")
    private WebElement countVINs;

    @FindAll({
        @FindBy(xpath="//table[@id='manual']//tbody//tr")
    })
    private List<WebElement> manualTableRows;



    /**
     * @return the buttonTranslationProperty
     */
    public WebElement getButtonTranslationProperty() {
        return this.buttonTranslationProperty;
    }

    public void clickButtonTranslationProperty() {
        this.getButtonTranslationProperty().click();
    }

    /**
     * @return the statusFirmwareProcessing
     */
    public WebElement getStatusFirmwareProcessing() {
        return this.statusFirmwareProcessing;
    }

    public void clickStatusFirmwareProcessing() {
        this.getStatusFirmwareProcessing().click();
    }

    /**
     * @return the foldingSingleProgressBars
     */
    public WebElement getFoldingSingleProgressBars() {
        return this.foldingSingleProgressBars;
    }

    /**
     * @return the progressBarOverall
     */
    public WebElement getProgressBarOverall() {
        return this.progressBarOverall;
    }

    public void clickFoldingSingleProgressBars() {
        this.getFoldingSingleProgressBars().click();
    }

    /**
     * @return the checkBoxApproved
     */
    public WebElement getCheckBoxApproved() {
        return this.checkBoxApproved;
    }

    public void clickCheckBoxApproved() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebDriverWait wait = new WebDriverWait(this.driver, 25);
        wait.until(ExpectedConditions.elementToBeClickable(this.getCheckBoxApproved()));
        this.getCheckBoxApproved().click();
    }

    /**
     * @return the buttonEditBatch
     */
    public WebElement getButtonEditBatch() {
        return this.buttonEditBatch;
    }

    public void clickButtonEditBatch() {
        ConvienentActionsInONUP.saveClick(getButtonEditBatch());
//        this.getButtonEditBatch().click();
    }

    /**
     * @return the countVINs
     */
    public WebElement getCountVINs() {
        return this.countVINs;
    }

    public void clickCountVINs() {
        this.getCountVINs().click();
    }

    /**
     * @return the manualTableRows
     */
    public List<WebElement> getManualTableRows() {
        return manualTableRows;
    }

    public int getVinCountOfBatchByName(String batchName) throws Exception {

        String vinCount = "0";
        WebElement element = this.getManualBatchByName(batchName);

        if (null == element) {
            throw new Exception("Searched batch element not found (expected '" + batchName + "')");
        }

        WebElement vin = element.findElement(By.xpath(".//td[contains(@class, 'vin-column')]"));

        if (null == vin) {
            throw new Exception("Searched batch element has no VIN info (VIN of '" + batchName + "' batch)");
        }

        return Integer.parseInt(vin.getText().trim());
    }

    public void deleteManualBatchByName(String batchName) {

        WebElement element = this.getManualBatchByName(batchName);

        if (null != element) {
            element.findElement(By.xpath(".//button[contains(@title, 'Delete batch')]")).click();
        }

    }

    public String getManualBatchStatusByName(String batchName) {

        String status = "";
        WebElement element = this.getManualBatchByName(batchName);

        if (null != element) {
            return element.findElement(By.xpath(".//td[contains(@class, 'status-column')]")).getText().trim();
        } else {
            return status;
        }

    }

    public void clickCheckBoxApprovedByName(String batchName) {

        WebElement element = this.getManualBatchByName(batchName);

        if (null != element) {
            element.findElement(By.xpath(".//p-checkbox")).click();
        }

    }

    private WebElement getManualBatchByName(String batchName) {

        WebElement element = null;

        for (WebElement ele : this.manualTableRows) {
            if (ele.getText().trim().contains(batchName)) {
                element = ele;
                break;
            }
        }

        return element;
    }

}
