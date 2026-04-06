/**
 *
 */
package de.sulzer.pages.campaignmanagement;

import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import testFramework.utilities.ReusableMethods;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Sulzer GmbH
 *
 */
public class CampaignManagementDisplayCampaign extends Page {

    WebDriver webDriver;

    public CampaignManagementDisplayCampaign(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }


    @FindBy(xpath="//p[@class='error-msg']")
    private WebElement errorMessageCriterionDisabled;

    @FindBy(xpath = "(//div/h3)[1]")
    private WebElement titleDisplayedORUCampaign;

    @FindBy(xpath = "(//div/h3)[1]/span")
    private WebElement titleDisplayedORUCampaignId;

    @FindBy(xpath="//span[contains(@class, 'fa fa-language campaign-translation-button')]")
    private WebElement buttonCampaignTranslation;

    @FindBy(id="btn-camp-det-encrypted-ic")
    private WebElement buttonEncryptionDownload;

    @FindBy(id = "btn-camp-det-del-ic")
    private WebElement buttonDeleteOruCampaign;

    @FindBy(xpath = "(//span[@id='btn-camp-det-crit-del-ic'])[1]")
    private WebElement buttonDeleteCriterionFirstTab;

    @FindBy(xpath = "(//span[@id='btn-camp-det-crit-del-ic'])[2]")
    private WebElement buttonDeleteCriterionSecondTab;

    @FindBy(xpath = "//span[@id='tab-camp-det-flash-del-ic']")
    private WebElement buttonDeleteFlashMedium;

    @FindBy(xpath="//div[@class='panel-body']/h4[contains(text(), 'Available:')]")
    private WebElement linkAvailableVehicles; // <-- h4, but no real link, but works as such

    @FindBy(xpath="//div[contains(@class, 'total-amount')]/a")
    private WebElement linkTotalAmountVehicles;

    @FindBy(xpath = "//*[@id='btn-batch-tables-add-batch']")
    private WebElement buttonAddBatch;

    @FindAll(
          @FindBy(xpath = "(//*[@class='p-tabview-nav-content']//ul)[1]//a[contains(@class, 'p-tabview-nav-link') and not(span[text()='+'])]/span[contains(@class, 'p-tabview-title')]")
    )
    private List<WebElement> listCriteriaButtons;

    @FindBy(xpath="//button[contains(text(),'Add Criterion to ORU Campaign')]")
    private WebElement buttonAddCriterionToOruCampaign;

    @FindBy(xpath = "//span[contains(@class, 'p-tree-toggler-icon')]")
    private WebElement addNewCriterionDownButton;

    @FindAll(
            @FindBy(css = ".p-checkbox-icon.pi.pi-check")
    )
    private List<WebElement> newCriterionCheckBox;

    @FindBy(xpath = "//div[@class='modal-footer']/button[1]")
    private WebElement AddNewCriterionApplyButton;

    @FindBy(xpath = "(//span[@class='pi pi-chevron-right'])[1]")
    private WebElement criteriumNextButton;

    @FindBy(xpath="//h4[@class='text-success']")
    private WebElement vehiclePoolSuccessMessage;

    @FindAll(
//            @FindBy(xpath = "//p-tabpanel/div/p-tabview/div/div/div/ul/li//a")
            @FindBy(xpath = "(//*[@class='p-tabview-nav-content']//ul)[2]//a[contains(@class, 'p-tabview-nav-link') and not(span[text()='+'])]/span[contains(@class, 'p-tabview-title')]")
    )
    private List<WebElement> listFlashMediumButtons;

    @FindBy(xpath = "(//span[.='+'])[1]")
    private WebElement addFlashMediumPlusButton;

    @FindBy(xpath = "//button[.=' Add Flashmedium to current criterion ']")
    private WebElement addFlashmediumToCurrentCriterion;

    @FindBy(xpath = "//p-tabpanel//a[@class='fa fa-angle-right']")
    private WebElement linkStateFirmwareProcessingUp;

    @FindBy(xpath = "//a[@id='href-proc-status-steps-collapse']")
    private WebElement statusOfFirmwareProcessing;

    @FindBy(id = "href-proc-status-steps-collapse-icon")
    private WebElement linkStateFirmwareProcessingDown;

    @FindBy(xpath="//div[@class='collapse show']/div[1]//div[@class='panel-body']/div[@class='row']/div[4]")
    private WebElement labelStep1DownloadFile100Percent;

    @FindBy(xpath="//div[@class='collapse show']/div[3]//div[@class='panel-body']/div[@class='row']/div[4]")
    private WebElement labelStep3SchemaAnalysisRepackaging100Percent;

    @FindBy(xpath = "(//span[@id='inf-proc-status-step-info-ic'])[3]")
    private WebElement iconStep3SchemaAnalysisRepackagingInformation;

    @FindAll(
            @FindBy(xpath = "(//table[@id='tbl-batch-table-batch']//tbody)[2]//tr")
    )
    private List<WebElement> manuallyApprovedBatches;

    @FindAll(
            @FindBy(id = "btn-batch-table-edit")
    )
    private List<WebElement> buttonManuallyEditBatch;

    @FindAll(
        @FindBy(id="btn-batch-table-del")
    )
    private List<WebElement> buttonManuallyDeleteBatch;

    @FindAll(
        @FindBy(id="btn-batch-table-move")
    )
    private List<WebElement> buttonManuallyMoveBatch;

    @FindAll(
        @FindBy(xpath="(//table[@id='tbl-batch-table-batch']//tbody)[1]//tr")
    )
    private List<WebElement> automaticallyApprovedBatches;

    @FindAll(
        @FindBy(xpath="(//table[@id='tbl-batch-table-batch'])[1]//button[@title='Edit batch']")
    )
    private List<WebElement> buttonAutoEditBatch;

    @FindAll(
        @FindBy(xpath="(//table[@id='tbl-batch-table-batch'])[1]//button[@title='Delete batch']")
    )
    private List<WebElement> buttonAutoDeleteBatch;

    @FindAll(
        @FindBy(xpath="(//table[@id='tbl-batch-table-batch'])[1]//button[@title='Move batch']")
    )
    private List<WebElement> buttonAutoMoveBatch;

    @FindBy(id="btn-camp-det-archive")
    private WebElement buttonActionArchiving;

    @FindBy(id="btn-camp-det-save")
    private WebElement buttonVerifiyAndApplyChanges;

    @FindBy(xpath = "(//table[@id='tbl-batch-table-batch']//td/span)[1]")
    private WebElement firsCurrent;

    @FindBy(id = "btn-err-mod-ok")
    private WebElement notAuthorizedOkButton;

    @FindBy(xpath = "(//span[.='+'])[1]")
    private WebElement addCriteriumPlusButton;

    @FindAll(
            @FindBy(xpath = "(//*[@class='p-tabview-nav-content']//ul)[1]//a[contains(@class, 'p-tabview-nav-link') and not(span[text()='+'])]/span[contains(@class, 'p-tabview-title')]")
    )
    private List<WebElement> criteriumTabList;

    public WebElement getNotAuthorizedOkButton() {
        return notAuthorizedOkButton;
    }

    public WebElement getAddCriteriumPlusButton() {
        return addCriteriumPlusButton;
    }

    public WebElement getAddNewCriterionDownButton() {
        return addNewCriterionDownButton;
    }

    public List<WebElement> getNewCriterionCheckBox() {
        return newCriterionCheckBox;
    }

    public WebElement getAddNewCriterionApplyButton() {
        return AddNewCriterionApplyButton;
    }

    public List<WebElement> getCriteriumTabList() {
        return criteriumTabList;
    }

    public WebElement getCriteriumNextButton() {
        return criteriumNextButton;
    }


    /**
     * @return the errorMessageCriterionDisabled
     */
    public WebElement getErrorMessageCriterionDisabled() {
        return errorMessageCriterionDisabled;
    }

    public WebElement getTitleDisplayedORUCampaign() {
        return titleDisplayedORUCampaign;
    }

    /**
     * @return the titleDisplayedORUCampaignId
     */
    public WebElement getTitleDisplayedORUCampaignId() {
        return titleDisplayedORUCampaignId;
    }

    /**
     * @return the buttonCampaignTranslation
     */
    public WebElement getButtonCampaignTranslation() {
        return buttonCampaignTranslation;
    }

    public void clickButtonCampaignTranslation() {
        this.getButtonCampaignTranslation().click();
    }

    /**
     * @return the buttonEncryptionDownload
     */
    public WebElement getButtonEncryptionDownload() {
        return buttonEncryptionDownload;
    }

    public void clickButtonEncryptionDownload() {
        this.getButtonEncryptionDownload().click();
    }

    /**
     * @return the buttonDeleteOruCampaign
     */
    public WebElement getButtonDeleteOruCampaign() {
        return buttonDeleteOruCampaign;
    }

    /**
     *
     */
    public void clickButtonDeleteOruCampaign() {
        this.getButtonDeleteOruCampaign().click();
    }

    /**
     * @return the buttonDeleteCriterion
     */
    public WebElement getButtonDeleteCriterionFirstTab() {
        return buttonDeleteCriterionFirstTab;
    }

    public WebElement getButtonDeleteCriterionSecondTab() {
        return buttonDeleteCriterionSecondTab;
    }

    public void clickButtonDeleteCriterion() {
        this.getButtonDeleteCriterionFirstTab().click();
    }

    /**
     * @return the buttonDeleteFlashMedium
     */
    public WebElement getButtonDeleteFlashMedium() {
        return buttonDeleteFlashMedium;
    }

    public void clickButtonDeleteFlashMedium() {
        this.getButtonDeleteFlashMedium().click();
    }

    public WebElement getLinkAvailableVehicles() {
        return this.linkAvailableVehicles;
    }

    public void clickLinkAvailableVehicles() {
        this.getLinkAvailableVehicles().click();
    }

    /**
     * @return the linkTotalAmountVehicles
     */
    public WebElement getLinkTotalAmountVehicles() {
        return linkTotalAmountVehicles;
    }

    public void clickLinkTotalAmountVehicles() {
        this.getLinkTotalAmountVehicles().click();
    }

    public WebElement getButtonAddBatch() {
        return this.buttonAddBatch;
    }

    public void clickButtonAddBatch() {
        this.getButtonAddBatch().click();
    }

    /**
     * @return List with all found criteria links (tabs).
     */
    public List<WebElement> getListCriteraiButtons(){
        return this.listCriteriaButtons;
    }

    public WebElement getStatusOfFirmwareProcessing() {
        return statusOfFirmwareProcessing;
    }
    /**
     * Pressing criteria button according given index.
     *
     * @param index
     * @throws Exception
     */
    public void pressCriteriaButton(int index) throws Exception {

//        int count = this.listCriteriaButtons.size();
//
//        if (index >= count) {
//            throw new Exception("Criteria button/link with index '" + index + "' was pushed, but only '" + count + "' were available.");
//        }
//
//        this.listCriteriaButtons.get(index).click();
        ReusableMethods.screenShot("pressCriteriaButton", webDriver);
        getAddCriteriumPlusButton().click();
    }

    /**
     * @return the vehiclePoolSuccessMessage
     */
    public WebElement getVehiclePoolSuccessMessage() {
        return vehiclePoolSuccessMessage;
    }

    /**
     * @return the vehiclePoolSuccessMessage
     */
    public void clickVehiclePoolSuccessMessage() {
        this.vehiclePoolSuccessMessage.click();
    }

    /**
     * @return the buttonAddCriterionToOruCampaign
     */
    public WebElement getButtonAddCriterionToOruCampaign() {
        return buttonAddCriterionToOruCampaign;
    }

    public WebElement getAddFlashMediumPlusButton() {
        return addFlashMediumPlusButton;
    }

    public void clickButtonAddCriterionToOruCampaign() {
        this.getButtonAddCriterionToOruCampaign().click();
    }

    /**
     * @return List with all found Flash Media links (tabs).
     */
    public List<WebElement> getListFlashMediumButtons(){
        return this.listFlashMediumButtons;
    }

    /**
     * @return the buttonAddFlashMediaToOruCampaign
     */
    public WebElement getAddFlashmediumToCurrentCriterion() {
        return addFlashmediumToCurrentCriterion;
    }

    public void clickButtonAddFlashMediaToOruCampaign() {
        this.getAddFlashmediumToCurrentCriterion().click();
    }

    /**
     * @return the linkStateFirmwareProcessing
     */
    public WebElement getLinkStateFirmwareProcessingUp() {
        return linkStateFirmwareProcessingUp;
    }

    public void clickLinkStateFirmwareProcessingUp() {
//        this.getLinkStateFirmwareProcessingUp().click();
        ReusableMethods.clickElement(getLinkStateFirmwareProcessingUp(), 2, webDriver);
    }

    public WebElement getLinkStateFirmwareProcessingDown() {
        return linkStateFirmwareProcessingDown;
    }

    public void clickLinkStateFirmwareProcessingDown() {
//        this.getLinkStateFirmwareProcessingDown().click();
        ReusableMethods.clickElement(getLinkStateFirmwareProcessingDown(), 2, webDriver);
    }

    /**
     * @return the labelStep1DownloadFile100Percent
     */
    public WebElement getLabelStep1DownloadFile100Percent() {
        return labelStep1DownloadFile100Percent;
    }

    /**
     * @return the labelStep3SchemaAnalysisRepackaging100Percent
     */
    public WebElement getLabelStep3SchemaAnalysisRepackaging100Percent() {
        return labelStep3SchemaAnalysisRepackaging100Percent;
    }

    /**
     * @return the iconStep3SchemaAnalysisRepackagingInformation
     */
    public WebElement getIconStep3SchemaAnalysisRepackagingInformation() {
        return iconStep3SchemaAnalysisRepackagingInformation;
    }

    public void clickIconStep3SchemaAnalysisRepackagingInformation() {
        this.getIconStep3SchemaAnalysisRepackagingInformation().click();
    }

    /**
     * @return the manuallyApprovedBatches
     */
    public List<WebElement> getManuallyApprovedBatches() {
        return manuallyApprovedBatches;
    }

    public List<WebElement> getButtonManuallyEditBatch() {
        return buttonManuallyEditBatch;
    }

    public List<WebElement> getButtonManuallyDeleteBatch() {
        return buttonManuallyDeleteBatch;
    }

    public List<WebElement> getButtonManuallyMoveBatch() {
        return buttonManuallyMoveBatch;
    }

    public String getManualBatchStatusByBatchName(String batchName) throws Exception {

        List<WebElement> manualBatches = this.getManuallyApprovedBatches();

        return this.getStatusBatchByName(batchName, manualBatches);

    }

    /**
     * @param batchName
     * @param manualBatches
     * @return empty string or status as string
     * @throws Exception
     */
    private String getStatusBatchByName(
            String batchName,
            List<WebElement> manualBatches) throws Exception {

        boolean found = false;

        for (WebElement batch : manualBatches) {

            String text = batch.getText().trim();

            if (text.contains(batchName)) {

                found = true;

                return batch.findElement(By.xpath(".//td[6]")).getText().trim();

            }

        }

        if (! found) {
            throw new Exception("Batch name '" + batchName + "' not found in table of batches.");
        }

        return "";
    }

    public WebElement getManualBatchByBatchName(String batchName) throws Exception {

        List<WebElement> manualBatches = this.getManuallyApprovedBatches();

        return this.getBatchByName(batchName, manualBatches);

    }

    private WebElement getBatchByName(
            String batchName,
            List<WebElement> manualBatches) throws Exception {

        for (WebElement batch : manualBatches) {

            String text = batch.getText().trim();

            if (text.contains(batchName)) {

                return batch;

            }

        }

        return null;

    }

    public int getManuallyApprovedBatchVinsByName(String batchName) throws Exception {

        List<WebElement> manuallyBatches = this.getManuallyApprovedBatches();

        return this.getVinCountByBatchName(batchName, manuallyBatches);

    }

    public void openInspectDialogueManuallyApprovedBatchVinsByName(String batchName) throws Exception {

        List<WebElement> manualBatches = this.getManuallyApprovedBatches();

        this.searchAndOpenVinInspectDialogue(batchName, manualBatches);

    }

    public void clickManualBatchApprovedCheckboxBatchByName(String batchName) throws Exception {

        List<WebElement> manualBatches = this.getManuallyApprovedBatches();

        this.clickBatchCheckbox(batchName, manualBatches);

    }

    public void clickManuallyApprovedBatchVinsByName(String batchName) throws Exception {

        List<WebElement> manualBatches = this.getManuallyApprovedBatches();

        this.searchAndOpenVinsOfBatch(batchName, manualBatches);

    }

    /**
     * @return the automaticallyApprovedBatches
     */
    public List<WebElement> getAutomaticallyApprovedBatches() {
        return automaticallyApprovedBatches;
    }

    public List<WebElement> getButtonAutoEditBatch() {
        return buttonAutoEditBatch;
    }

    public List<WebElement> getButtonAutoDeleteBatch() {
        return buttonAutoDeleteBatch;
    }

    public List<WebElement> getButtonAutoMoveBatch() {
        return buttonAutoMoveBatch;
    }

    public void clickAutomaticallyApprovedBatchesApprovedBatchVinsByName(String batchName) throws Exception {

        List<WebElement> automaticBatches = this.getAutomaticallyApprovedBatches();

        this.searchAndOpenVinsOfBatch(batchName, automaticBatches);

    }

    public void openEditAutomaticallyApprovedBatchesApprovedBatchVinsByName(String batchName) throws Exception {

        List<WebElement> automaticBatches = this.getAutomaticallyApprovedBatches();

        this.openBatchByName(batchName, automaticBatches);

    }

    /**
     * @param batchName
     * @param automaticBatches
     * @throws Exception
     */
    private void openBatchByName(
            String batchName,
            List<WebElement> automaticBatches) throws Exception {

        boolean found = false;

        for (WebElement batch : automaticBatches) {

            String text = batch.getText().trim();

            if (text.contains(batchName)) {

                found = true;

                batch.findElement(By.xpath(".//button[@title='Edit batch']")).click();

                break;
            }

        }

        if (! found) {
            throw new Exception("Batch name '" + batchName + "' not found in table of batches.");
        }

    }

    public void clickAutomaticBatchApprovedCheckboxBatchByName(String batchName) throws Exception {

        List<WebElement> automaticBatches = this.getAutomaticallyApprovedBatches();

        this.clickBatchCheckbox(batchName, automaticBatches);

    }

    /**
     * @param batchName
     * @param batches
     * @throws Exception
     */
    private void clickBatchCheckbox(String batchName, List<WebElement> batches) throws Exception {

        boolean found = false;

        for (WebElement batch : batches) {

            String text = batch.getText().trim();

            if (text.contains(batchName)) {

                found = true;

                batch.findElement(By.xpath(".//td[1]/p-checkbox")).click();

                break;
            }

        }

        if (! found) {
            throw new Exception("Batch name '" + batchName + "' not found in table of batches.");
        }

    }

    public boolean clickDeleteAutomaticallyApprovedBatchesApprovedBatchVinsByName(
            String batchName) {

        boolean isDeleted = false;

        List<WebElement> automaticBatches = this.getAutomaticallyApprovedBatches();

        for (WebElement batch : automaticBatches) {

            String text = batch.getText().trim();

            if (text.contains(batchName)) {

                batch.findElement(By.xpath(".//button[@title='Delete batch']")).click();

                isDeleted = true;

                break;
            }

        }

        return isDeleted;

    }

    public boolean checkExistenceAutomaticallyApprovedBatchByName(String batchName) {

        try {
            ReusableMethods.waitForVisibility(getAutomaticallyApprovedBatches().get(0), 5, webDriver);
        } catch (Exception ignored) {
        }

        List<WebElement> automaticBatches = this.getAutomaticallyApprovedBatches();

        for (WebElement autoBatch : automaticBatches) {

            String text = autoBatch.getText().trim();

            if (text.contains(batchName)) {
                return true;
            }

        }

        return false;
    }

    public int getAutomaticallyApprovedBatchVinCountByName(String batchName) throws Exception {

        List<WebElement> automaticBatches = this.getAutomaticallyApprovedBatches();

        return this.getVinCountByBatchName(batchName, automaticBatches);

    }

    /**
     * @param batchName
     * @param batches
     * @return
     * @throws NumberFormatException
     * @throws Exception
     */
    private int getVinCountByBatchName(
            String batchName,
            List<WebElement> batches)
            throws NumberFormatException, Exception {

        boolean found = false;
        int count = 0;

        for (WebElement autoBatch : batches) {

            String text = autoBatch.getText().trim();

            System.out.println("BATCH NAME: " + text);

            if (text.contains(batchName)) {

                WebElement cell = autoBatch.findElement(By.xpath(".//td[@class='vin-column']"));

                count = Integer.parseInt(cell.getText().trim());

                found = true;
                break;

            }

        }

        if (! found) {
            throw new Exception("Batch name '" + batchName + "' not found in table of batches.");
        }

        return count;
    }

    public void openInspectDialogueAutomaticApprovedBatchVinsByName(String batchName) throws Exception {

        List<WebElement> automaticBatches = this.getAutomaticallyApprovedBatches();

        this.searchAndOpenVinInspectDialogue(batchName, automaticBatches);

    }

    /**
     * @param batchName
     * @param batches
     * @throws Exception
     */
    private void searchAndOpenVinInspectDialogue(String batchName, List<WebElement> batches) throws Exception {

        boolean found = false;

        for (WebElement batch : batches) {

            String text = batch.getText().trim();

            if (text.contains(batchName)) {

                found = true;

                batch.findElement(By.xpath(".//td[@class='vin-column']/a")).click();

            }

        }

        if (! found) {
            throw new Exception("Batch name '" + batchName + "' not found in table of approved batches.");
        }

    }

    /**
     * @param batchName
     * @param batches
     * @throws Exception
     */
    private void searchAndOpenVinsOfBatch(String batchName, List<WebElement> batches) throws Exception {

        WebElement rowBatch = null;

        for (WebElement element: batches) {

            String text = element.getText().trim();

            if (text.contains(batchName)) {
                rowBatch = element;
            }

        }

        if (null != rowBatch) {
            rowBatch.findElement(By.xpath(".//td[@class='vin-column']/a")).click();
        } else {
            throw new Exception("Batch '" + batchName + "' not found!");
        }

    }


    public void addCreatedCriteria(){

        this.listCriteriaButtons.get(1).click();
        getButtonAddCriterionToOruCampaign().click();
        ReusableMethods.clickElement(getAddNewCriterionDownButton(), 4, webDriver);

        for (WebElement i: getNewCriterionCheckBox()) {
            ReusableMethods.clickElement(i, 2, webDriver);
            ReusableMethods.screenShot(webDriver);
        }

        getAddNewCriterionApplyButton().click();
    }

    public boolean isCriteriumCreated(int AMOUNT_CRITERIA){

//        int criteriumTabSize = getCriteriumTabs().size();
        int criteriumTabSize = getListCriteraiButtons().size();
        try {
            assertEquals(AMOUNT_CRITERIA, criteriumTabSize, "Oru Campaign has not " + AMOUNT_CRITERIA + " criteria.");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return the buttonActionArchiving
     */
    public WebElement getButtonActionArchiving() {
        return buttonActionArchiving;
    }

    public void clickButtonActionArchiving() {
        this.getButtonActionArchiving().click();
    }

    /**
     * @return the buttonVerifiyAndApplyCahnges
     */
    public WebElement getButtonVerifiyAndApplyChanges() {
        return this.buttonVerifiyAndApplyChanges;
    }

    public void clickButtonVerifiyAndApplyChanges() {
        this.getButtonVerifiyAndApplyChanges().click();
    }

    public String getFirsCurrent(){
        return this.firsCurrent.getText();
    }

}
