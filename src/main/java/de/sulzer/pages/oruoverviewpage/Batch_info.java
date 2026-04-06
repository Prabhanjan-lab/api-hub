package de.sulzer.pages.oruoverviewpage;

import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import testFramework.utilities.ReusableMethods;

import java.util.ArrayList;
import java.util.List;


public class Batch_info extends Page {
    WebDriver webDriver;

    public Batch_info(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }

    @FindBy(xpath = "//td[@class='action-column']/button[1]")
    private WebElement editdefaultbatch;

    @FindBy(xpath = "//div[@class='container-fluid']/div[3]/button[2]")
    private WebElement buttonVehicleFiltering;

    @FindBy(xpath = "//div[@class='container-fluid']/div[3]/button[2]")
    private WebElement buttonTestFilter;

    @FindBy(id = "cb-new-veh-selected")
    private WebElement checkBoxMoveVehicleFromVehiclePool;

    @FindBy(id = "btn-create-update-batch-mod-next")
    private WebElement Editbatch;

    @FindBy(id = "inf-overall-proc-available")
    private WebElement progressbarstatus;

    @FindBy(xpath = "//td[@class=\"batch-column\" and div[@class=\"batch-name\" and text()=\"Default\"]]/preceding-sibling::td[@class=\"approved-column\"]//div[@class=\"p-checkbox-box\"]")
    private WebElement defaultcheckbox;

    @FindBy(id = "btn-saving-batch-changes-mod-save")
    private WebElement applybutton;

    @FindBy(id = "btn-camp-det-save")
    private WebElement buttonVerifyAndApplyChanges;

    // *************batch creation process*************//

    @FindBy(xpath = "//h4[@class='modal-title pull-left']")
    private WebElement commonPreferencesModalTitle;

    @FindBy(xpath = "//div[@class='col-sm-6']//input[contains(@class, 'form-control')]")
    private WebElement batchTitleInputField;

    @FindBy(id = "opt-common-preferences-mod-auto-approval")
    private WebElement automaticApprovalRadioButton;

    @FindBy(xpath = "//td[@class=\"batch-column\" and div[@class=\"batch-name\" and text()=\"ManualBatch\"]]/preceding-sibling::td[@class=\"approved-column\"]//div[@class=\"p-checkbox-box\"]")
    private WebElement manualApprovalRadioButton;

    @FindBy(xpath="//div//select[contains(@class, 'form-control chargeSelect')]")
    private WebElement dropDownPredecessorBatch;

    @FindBy(xpath="//div/input[contains(@class, 'form-control')][@size='3']")
    private WebElement inputPercentage;

    @FindBy(xpath = "//h4[@class='modal-title pull-left']")
    private WebElement vehicleFilteringModalTitle;

    @FindBy(xpath = "//input[@placeholder='e.g. +7UG/B29/ABC+B29/BDS/123+UX2+SBU/UBS']")
    private WebElement prNumbersInputField;

    @FindBy(xpath = "//h4[@class='modal-title pull-left']")
    private WebElement addVehiclesToBatchModalTitle;

    @FindBy(xpath = "//button[contains(.,'Back')]")
    private WebElement backButton;

    @FindBy(xpath = "//button[contains(.,'Create batch')]")
    private WebElement buttonCreateBatch;

    @FindAll(
        @FindBy(xpath = "(//*[@id='tbl-batch-table-batch'])[2]//tbody//tr") // all rows of manual batch table
    )
    private List<WebElement> manualBatchesRows;

    @FindAll(
        @FindBy(xpath = "(//*[@id='tbl-batch-table-batch'])[1]//tbody//tr") // all rows of auto batch table
    )
    private List<WebElement> autoBatchesRows;

    @FindBy(xpath = "(//*[@id='tbl-batch-table-batch'])[2]") // manual batch table drop area
    private WebElement manualBatchesTable;

    @FindBy(xpath = "(//*[@id='tbl-batch-table-batch'])[1]") // auto batch table drop area
    private WebElement autoBatchesTable;

    // Delete default batch
    @FindBy(xpath = "//button[@title='Delete batch']")
    private WebElement deleteDefaultBatch;

    @FindBy(id = "inpt-batch-fltr-mod-importer")
    private WebElement importerInputField;

    @FindBy(id = "inpt-batch-fltr-mod-mbt")
    private WebElement mbtInputField;

    @FindBy(xpath = "//button[contains(. ,'Confirm')]") // confirmation button in batch moving process
    private WebElement confirmBatchMove;

//    @FindBy(xpath = "//label[contains(.,'Starting condition for subsequent batch:')]//following::input[1]")
    private WebElement targetPercentageOfCompletion;

    @FindBy(xpath = "//button[@class='btn btn-danger pull-right']")
    private WebElement confirmBatchDeletionBtn;

    /**************
     *
     * to click Autobatch CheckBox
     *
     **************/
    /*
     * TODO Method assumes, that in auto batch table is only one row!
     */
    @FindBy(xpath = "//table[@id='cb-batch-table-approved']//tr[1]//td[1]//p-checkbox")
    private WebElement autoBatchCheckBox;

    @FindAll({
        @FindBy(xpath = "//select[@id='suggestions']//option") // All batches of drop down
    })
    private List<WebElement> allBatchesValuesOfdropDown;

    @FindBy(xpath = "//button[contains(text(),'Cancel')]")
    private WebElement cancelButton;

    @FindAll({
        @FindBy(xpath = "//td[@class='vin-column']/a") // VIN links/column of all manually approved batches
    })


    private List<WebElement> linksManuallyApprovedBatchesVinColumn;

    public WebElement getButtonCreateBatch() {
        return buttonCreateBatch;
    }

    public void clickButtonCreateBatch() {
        this.getButtonCreateBatch().click();
    }

    public WebElement getCheckBoxMoveVehicleFromVehiclePool() {
        return checkBoxMoveVehicleFromVehiclePool;
    }

    public void clickCheckBoxMoveVehicleToBatch() {
        this.getCheckBoxMoveVehicleFromVehiclePool().click();
    }

    public boolean onlyOneCheckBoxPresent(WebDriver driver) {
        if (driver.findElements(By.xpath("//new-vehicles-component//div")).size() > 1)
            return false;
        else
            return true;
    }

    public WebElement getBackButton() {
        return backButton;
    }

    public void clickBackButton() {
        this.getBackButton().click();
    }

    public WebElement getAddVehiclesToBatchModalTitle() {
        return addVehiclesToBatchModalTitle;
    }

    public String AddVehiclesToBatchModalTitle() {
        return this.getAddVehiclesToBatchModalTitle().getText();
    }

    public WebElement getPRNumbersInputField() {
        return prNumbersInputField;
    }

    public void setPRNumbersInputField(String input) {
        this.getPRNumbersInputField().sendKeys(input);
    }

    public WebElement getVehicleFilteringModalTitle() {
        return vehicleFilteringModalTitle;
    }

    public String VehicleFilteringModalTitle() {
        return this.getVehicleFilteringModalTitle().getText();
    }

    /**
     * @return the automaticApprovalRadioButton
     */
    public WebElement getAutomaticApprovalRadioButton() {
        return automaticApprovalRadioButton;
    }

    public void clickAutomaticApprovalRadioButton() {
        this.getAutomaticApprovalRadioButton().click();
    }

    public WebElement getManualApprovalRadioButton() {
        return manualApprovalRadioButton;
    }

    public void clickManualApprovalRadioButton() {
//        this.getManualApprovalRadioButton().click();
        ReusableMethods.clickElement(getManualApprovalRadioButton(), 10, webDriver);
    }

    /**
     * @return the dropDownPredecessorBatch
     */
    public WebElement getDropDownPredecessorBatch() {
        return dropDownPredecessorBatch;
    }

    public void selectPredecessorByBatchTitle(String batchTitle) {

        Select selectorBatchTitle = new Select(this.getDropDownPredecessorBatch());
        selectorBatchTitle.selectByVisibleText(batchTitle);

    }

    public WebElement getSelectedPredecessorOption() {

        Select selectorBatchTitle = new Select(this.getDropDownPredecessorBatch());
        return selectorBatchTitle.getFirstSelectedOption();

    }

    /**
     * @return the inputPercentage
     */
    public WebElement getInputPercentage() {
        return inputPercentage;
    }

    public void setInputPercentage(String text) {
        this.getInputPercentage().sendKeys(text);
    }

    public WebElement getBatchTitleInputField() {
        return batchTitleInputField;
    }

    public void setbatchTitleInputField(String input) {
        this.getBatchTitleInputField().sendKeys(input);
    }

    public WebElement getCommonPreferencesModalTitle() {
        return commonPreferencesModalTitle;
    }

    public String commonPreferencesModalTitle() {
        return this.getCommonPreferencesModalTitle().getText();
    }

    public WebElement getapplybutton() {
        return applybutton;
    }

    public void clickapplybutton() {
        this.getapplybutton().click();
    }

    public WebElement getButtonVerifyAndApplyChanges() {
        return buttonVerifyAndApplyChanges;
    }

    public boolean isButtonVerifyAndApplyChangesEnabled() {
        if (this.getButtonVerifyAndApplyChanges().isEnabled()) {
            return true;
        } else {
            return false;
        }

    }

    public void clickButtonVerifyAndApplyChanges() {
        this.getButtonVerifyAndApplyChanges().click();
    }

    public WebElement getdefaultcheckbox() {
        return defaultcheckbox;
    }

    public void clickdefaultcheckbox() {
        this.getdefaultcheckbox().click();
    }

    public WebElement getprogressbarstatus() {
        return progressbarstatus;
    }

    public String checkprogressbarstatus() {
        return this.getprogressbarstatus().getText().trim();
    }

    public WebElement geteditdefaultbatch() {
        return editdefaultbatch;
    }

    public void clickeditdefaultbatch() {
        this.geteditdefaultbatch().click();
    }

    public WebElement getButtonVehicleFilter() {
        return buttonVehicleFiltering;
    }

    public void clickVehicleFilter() {
        this.getButtonVehicleFilter().click();
    }

    public WebElement getButtonTestFilter() {
        return buttonTestFilter;
    }

    public void clickButtonTestFilter() {
        this.getButtonTestFilter().click();
    }

    public void clickCheckBoxMoveVehicleFromVehiclePool() {
        this.getCheckBoxMoveVehicleFromVehiclePool().click();
    }

    public WebElement getEditbatch() {
        return Editbatch;
    }

    public void clickEditbatch() {
        this.getEditbatch().click();
    }

    public void clickBackAndNext() {

        this.clickBackButton();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.clickButtonTestFilter();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public List<WebElement> getManualBatchesRows() {
        return this.manualBatchesRows;
    }

    public List<WebElement> getAutoBatchesRows() {
        return this.autoBatchesRows;
    }

    /**
     * to iterate over given batch(manual/auto) table
     *
     *
     * @param listWebElements
     * @return Row cell with text that equals given batchID (WebElement)
     */

    private WebElement searchBatchById(String batchId, List<WebElement> listWebElements) {

        for (WebElement element : listWebElements) {
//            String textBatchName = element.findElement(By.xpath(".//td[@class='batch-column']")).getText();

//            if (textBatchName.equals(batchId)) {
                if (element.getText().contains(batchId)) {
                return element;
            }
        }
        return null;

    }

    private WebElement searchManualBatchById(String batchId) {

        return this.searchBatchById(batchId, this.manualBatchesRows);
    }

    private WebElement searchAutoBatchById(String batchId) {

        return this.searchBatchById(batchId, this.autoBatchesRows);

    }

    /**
     * to find out whether the batch present with given id in manual batch table
     *
     ** @param batchId
     *
     *
     */

    public boolean isManualBatchAvailable(String batchId) {

        if (null != this.searchManualBatchById(batchId)) {

            return true;

        } else {

            return false;
        }
    }

    /**
     * to find out whether the batch present with given id in auto batch table
     *
     *      * @param batchId
     *
     *     
     */

    public boolean isAutoBatchAvailable(String batchId) {

        if (null != this.searchAutoBatchById(batchId)) {

            return true;

        } else {

            return false;

        }
    }

    /**
     * To move Batch from manual to auto batch
     *
     * *@parambatchId
     *
     *     
     */

    private WebElement getParticularBatchMoveButton(String batchId, List<WebElement> listWebElements) {

        WebElement requiredBatch = searchBatchById(batchId, listWebElements);

        WebElement requiredBatchMoveButton = requiredBatch.findElement(By.xpath(".//td//button[@title='Move batch']"));

        return requiredBatchMoveButton;

    }

    private void moveBatch(String batchId, List<WebElement> listWebElements, WebElement dropZone) {
        WebElement moveButtonOfBatchToBeMoved = getParticularBatchMoveButton(batchId, listWebElements);

        Actions actions = new Actions(webDriver);

//        moveButtonOfBatchToBeMoved.click();
        ReusableMethods.waitForClickablility(moveButtonOfBatchToBeMoved, 5, webDriver);

        actions.dragAndDrop(moveButtonOfBatchToBeMoved, dropZone).build().perform();
    }

    /*
     * @ param batchId
     */
    public void moveBatchFromManualToAutoBatch(String batchId) {

        moveBatch(batchId, manualBatchesRows, autoBatchesTable);
    }

    public Boolean getMoveBatchButtonStatus(String batchId) {

        return this.getParticularBatchMoveButton(batchId, manualBatchesRows).isEnabled();

    }

    /******
     *
     * To Delete a particular batch
     *
     *****/

    public WebElement getParticularBatchDeleteButton(String batchId, List<WebElement> listWebElements) {

        WebElement requiredBatch = searchBatchById(batchId, listWebElements);

        WebElement requiredBatchDeleteButton = requiredBatch
                .findElement(By.xpath(".//td//button[@title='Delete batch']"));

        return requiredBatchDeleteButton;

    }

    public void clickDeleteManualBatch(String batchId) {
        getParticularBatchDeleteButton(batchId, manualBatchesRows).click();
    }

    public Boolean getManualBatchDeleteButtonStatus(String batchId) {

        return this.getParticularBatchDeleteButton(batchId, manualBatchesRows).isEnabled();

    }

    public WebElement getDefaultDeleteButton() {
        return deleteDefaultBatch;
    }

    /**
     * TO get the VIN count from the VINS column
     *
     *
     */

    private WebElement getParticularBatchVinColumn(String batchId, List<WebElement> listWebElements) {

        WebElement requiredBatch = searchBatchById(batchId, listWebElements);

        WebElement requiredBatchVinColumn = requiredBatch.findElement(By.xpath(".//td[@class='vin-column']"));

        return requiredBatchVinColumn;

    }

    public void clickDeleteAutoBatch(String batchId) {
        getParticularBatchDeleteButton(batchId, autoBatchesRows).click();
    }

    public String manualBatchVinsCount(String batchId) {
        String VinCount = this.getParticularBatchVinColumn(batchId, manualBatchesRows).getText();
        return VinCount;
    }

    public WebElement getImporterInputField() {
        return this.importerInputField;
    }

    public void setImporterInputField(String importer) {
        this.getImporterInputField().sendKeys(importer);
    }

    public WebElement getMBTInputField() {
        return this.mbtInputField;
    }

    public void setMBTInputField(String importer) {
        this.getMBTInputField().sendKeys(importer);
    }

    public WebElement getconfirmBatchMove() {
        return confirmBatchMove;
    }

    public void clickconfirmBatchMove() {
        this.getconfirmBatchMove().click();

    }

    public WebElement getTargetPercentageOfCompletion() {
        return targetPercentageOfCompletion;
    }

    public void setTargetPercentageOfCompletion(String targetValue) {
        this.getTargetPercentageOfCompletion().sendKeys(targetValue);
    }

    public WebElement getConfirmBatchDeletionBtn() {
        return this.confirmBatchDeletionBtn;
    }

    public void clickConfirmBatchDeletionBtn() {
        this.getConfirmBatchDeletionBtn().click();
    }

    /*
     * To click the particular Manual batch CheckBox
     */
    private WebElement getParticularBatchCheckBoxByBatchList(String batchId, List<WebElement> listWebElements) {

        WebElement requiredBatchRow = searchBatchById(batchId, listWebElements);

        WebElement requiredBatchCheckBox = requiredBatchRow.findElement(By.xpath(".//p-checkbox/div/div/input"));
        return requiredBatchCheckBox;

    }

    private WebElement getParticularBatchCheckBoxByBatchClickList(String batchId, List<WebElement> listWebElements) {

        WebElement requiredBatchRow = searchBatchById(batchId, listWebElements);

        WebElement requiredBatchCheckBox = requiredBatchRow.findElement(By.xpath(".//p-checkbox/div/div[2]"));
        return requiredBatchCheckBox;

    }

    public String isManualBatchCheckBoxSelected(String batchId) {

        WebElement we = getParticularBatchCheckBoxByBatchList(batchId, manualBatchesRows);

        return we.getAttribute("aria-checked");
    }

    public void clickManualBatchCheckBox(String batchId) {
        this.getParticularBatchCheckBoxByBatchClickList(batchId, getManualBatchesRows()).click();
    }

    /**
     * TODO Method assumes, that in auto batch table is only one row!
     *
     * @return
     */
    private WebElement getAutoBatchCheckBox() {
        return this.autoBatchCheckBox;
    }

    public String isAutoBatchCheckBoxSelected(String batchId) {

        WebElement we = getParticularBatchCheckBoxByBatchList(batchId, this.autoBatchesRows);

        return we.getAttribute("aria-checked");
    }

    public void clickAutoBatchCheckBox(String batchId) {
        this.getParticularBatchCheckBoxByBatchClickList(batchId, this.autoBatchesRows).click();
    }

    /**
     * To move Batch from auto to manual batch
     *
     * * @parambatchId
     *
     *     
     */
    public void deleteManualBatch(String batchId) {
        getParticularBatchDeleteButton(batchId, manualBatchesRows).click();
    }

    public void moveBatchFromAutoToManualBatch(String batchId) {
        moveBatch(batchId, autoBatchesRows, manualBatchesTable);
    }

    // get count of Vins for particular batch
    /**
     *
     * @param batchID
     * @return Count of vins for particular batch given
     */
    public String getVinCountOfManualBatch(String batchID) {
        WebElement requiredBatch = this.searchManualBatchById(batchID);
        String vinCount = requiredBatch.findElement(By.xpath(".//td[@class='vin-column']")).getText();
        return vinCount;
    }

    // All drop down values of where to move the vehicles in "BATCH DELETION
    // CONFIRMATION"
    /**
     * @return all batch names which are present in "BATCH DELETION CONFIRMATION"
     *         pop up
     */
    public List<String> allDropDownValues() {
        List<String> batchesIDs = new ArrayList<String>();
        for (WebElement value : allBatchesValuesOfdropDown) {
            String batchID = value.getText();
            batchesIDs.add(batchID);
        }
        return batchesIDs;
    }

    public WebElement getCancelButton() {
        return this.cancelButton;
    }

    public void clickCancel() {
        this.getCancelButton().click();
    }

    /**
     * @return the linksManuallyApprovedBatchesVinColumn
     */
    public List<WebElement> getLinksManuallyApprovedBatchesVinColumn() {
        return linksManuallyApprovedBatchesVinColumn;
    }

    /**
     *
     *            the required manual batch delete button
     */
    public void clickDeleteOfManualBatch(String batchID) {
        WebElement requiredBatch = this.searchManualBatchById(batchID);
        requiredBatch.findElement(By.xpath(".//button[@title='Delete batch']")).click();
    }

    /**
     *
     * @return all manual batches present in that campaign
     */
    public List<String> getAllManualBatches() {
        List<String> allBatches = new ArrayList<String>();
        for (WebElement element : manualBatchesRows) {
            String batchID = element.findElement(By.xpath(".//td[@class='batch-column']")).getText();
            allBatches.add(batchID);
        }
        return allBatches;
    }

    /**
     *
     * @param batchID
     * @return given input batch status
     */
    public String getManualBatchStatus(String batchID) {
        WebElement requiredBatch = this.searchManualBatchById(batchID);
        String batchStatus = requiredBatch.findElement(By.xpath(".//td[@class='status-column']")).getText();
        return batchStatus;
    }

    // check the visibility of batches in the ORU Overview page
    public boolean visiblityOfManualBatches() {

        int countOfBatchs = this.getManualBatchesRows().size();
        if (countOfBatchs > 0) {
            return true;
        } else {
            return false;
        }
    }

    private WebElement getParticularBatchEditButton(String batchId, List<WebElement> listWebElements) {

        WebElement requiredBatch = searchBatchById(batchId, listWebElements);

        WebElement requiredBatchEditButton = requiredBatch.findElement(By.xpath(".//td//button[@title='Edit batch']"));

        return requiredBatchEditButton;

    }

    public void clickEditManualBatch(String batchId) {

        getParticularBatchEditButton(batchId, manualBatchesRows).click();
    }

    public boolean getManualBatchEditButtonStatus(String batchId) {

        return this.getParticularBatchEditButton(batchId, manualBatchesRows).isEnabled();

    }

    // check the visibility of batches in the ORU Overview page
    public boolean visiblityOfAutoBatches() {

        int countOfBatchs = this.getAutoBatchesRows().size();
        if (countOfBatchs > 0) {
            return true;
        } else {
            return false;
        }
    }


    public void waitForOruProcessing() throws InterruptedException {

        int i = 0;
        int i2 = 0;

        while (i == 0) {
            String status;
            status = this.checkprogressbarstatus();

            if (status.contains("100 %")) {
                i = 1;
            } else {
                if (i2 > 600) {
                    throw new java.lang.Error("Timeout after 6 minutes!");
                } else {
                    i2++;
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("Oru Campaign is created successfully!");
    }
}
