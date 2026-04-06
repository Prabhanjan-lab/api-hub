/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.blockingdialogues.overlay.OverlayHandler;
import de.sulzer.pages.campaignmanagement.CampaignManagementDisplayCampaign;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignCreateNewBatchSequence;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignCreation;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignEditBatchSequence;
import de.sulzer.pages.oruoverviewpage.Batch_info;
import de.sulzer.pages.oruoverviewpage.ORUOverviewPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sulzer GmbH
 */
public class ActionsOnupOruCampaignBatches {

    private final WebDriver webDriver;

    public ActionsOnupOruCampaignBatches(WebDriver driver) {
        this.webDriver = driver;
    }


    /**
     * @param webDriver
     * @param wait
     * @param inboxCampaignCreation
     */
    public void moveVINsToDefaultBatch(
            WebDriver webDriver,
            WebDriverWait wait,
            InboxCampaignCreation inboxCampaignCreation) {

        ORUOverviewPage oruOverviewPage = new ORUOverviewPage(webDriver);

        // edit batch
        inboxCampaignCreation.clickButtonEditBatch();
        provessMoveVINToBatch(webDriver, wait);

    }

    public void moveVINToManualBatchByName(
            String vin,
            String batchName,
            WebDriver webDriver,
            WebDriverWait webDriverWait,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign) throws Exception {

        List<WebElement> manualBatches = campaignManagementDisplayCampaign.getManuallyApprovedBatches();

        boolean found = clickButtonEditBatch(batchName, manualBatches);

        if (!found) {
            throw new Exception("VIN '" + vin + "' can't be transferred to batch '" + batchName + "', because batch is not in manual batches.");
        }

        provessMoveVINToBatch(webDriver, webDriverWait);

    }

    /**
     * @param webDriver
     * @param webDriverWait
     */
    private void provessMoveVINToBatch(WebDriver webDriver, WebDriverWait webDriverWait) {

        InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence = new InboxCampaignEditBatchSequence(webDriver);
        //
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inboxCampaignEditBatchSequence.getButtonNextVehicleFiltering()));
        inboxCampaignEditBatchSequence.clickButtonNextVehicleFiltering();

        //
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inboxCampaignEditBatchSequence.getButtonNextTestFilter()));
        inboxCampaignEditBatchSequence.clickButtonNextTestFilter();

        clickCheckBoxIfAvailable(inboxCampaignEditBatchSequence);
        inboxCampaignEditBatchSequence.clickButtonEditBatch();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @param batchName
     * @param batches
     * @return
     */
    private static boolean clickButtonEditBatch(
            String batchName,
            List<WebElement> batches) {

        boolean found = false;

        for (WebElement batch : batches) {

            String text = batch.getText().trim();

            if (text.contains(batchName)) {

                batch.findElement(By.xpath(".//button[@title='Edit batch']")).click();

                found = true;

                break;
            }

        }

        return found;

    }

    public void moveVINToAutomaticBatchByName(
            String vin,
            String batchName,
            WebDriver webDriver,
            WebDriverWait webDriverWait,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign) throws Exception {

        List<WebElement> automaticBatches = campaignManagementDisplayCampaign.getAutomaticallyApprovedBatches();

        boolean found = clickButtonEditBatch(batchName, automaticBatches);

        if (!found) {
            throw new Exception("VIN '" + vin + "' can't be transferred to batch '" + batchName + "', because batch is not in manual batches.");
        }

        provessMoveVINToBatch(webDriver, webDriverWait);

    }

    /**
     * @throws InterruptedException
     */
    public void approveDefaultBatchOfCampaign(
            String campaignId,
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            Batch_info batchInfo,
            WebDriver webDriver) throws InterruptedException {

        new ActionsOnupOruCampaign(webDriver).searchAndOpenCampaign(
                adminPortletHomepage,
                campaignManagementSearch,
                campaignId,
                webDriver);

        //approve of default batch
        Thread.sleep(2000);
        batchInfo.clickdefaultcheckbox();
        batchInfo.clickButtonVerifyAndApplyChanges();
        Thread.sleep(2000);
        batchInfo.clickapplybutton();

        Thread.sleep(7000);

    }

    /**
     * Disapproving follows the same procedure as approving. It's just checking
     * a checkbox and conformation.
     *
     * @throws InterruptedException
     */
    public void disapproveDefaultBatchOfCampaign(
            String campaignId,
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            Batch_info batchInfo,
            WebDriver webDriver) throws InterruptedException {

        approveDefaultBatchOfCampaign(
                campaignId,
                adminPortletHomepage,
                campaignManagementSearch,
                batchInfo,
                webDriver);

    }

    /**
     * @throws InterruptedException
     */
    public void createManuallyApprovedBatchFilterPRN(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String batchName,
            String prn,
            WebDriverWait webDriverWait) throws InterruptedException {

        openDialogueBatchCreation(campaignManagementDisplayCampaign);

        processBatchCreationStep1Manual(batchInfo, batchName);

        processBatchCreationPRNStep2And3(batchInfo, campaignManagementDisplayCampaign, inboxCampaignEditBatchSequence, prn, webDriverWait);

    }

    public void createManuallyApprovedBatchFilterImporter(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String batchName,
            String importer,
            WebDriverWait webDriverWait) throws InterruptedException {

        openDialogueBatchCreation(campaignManagementDisplayCampaign);

        processBatchCreationStep1Manual(batchInfo, batchName);

        processBatchCreationImporterStep2And3(
                batchInfo,
                campaignManagementDisplayCampaign,
                inboxCampaignEditBatchSequence,
                importer,
                webDriverWait);
    }

    public void createManuallyApprovedBatchFilterImporterAndMBT(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String batchName,
            String importer,
            String mbt,
            WebDriverWait webDriverWait) throws InterruptedException {

        openDialogueBatchCreation(campaignManagementDisplayCampaign);

        processBatchCreationStep1Manual(batchInfo, batchName);

        processBatchCreationImporterAndMBTStep2And3(
                batchInfo,
                campaignManagementDisplayCampaign,
                inboxCampaignEditBatchSequence,
                importer,
                mbt,
                webDriverWait);

    }

    /**
     * @param wait
     * @param adminPortletHomepage
     * @param campaignManagementSearch
     * @param batchInfo
     * @param campaignId
     * @param batchName
     */
    public void deleteManualBatchByNameAndCamaignId(
            WebDriverWait wait,
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            Batch_info batchInfo,
            String campaignId,
            String batchName) {

        ActionsOnupOruCampaign actionsOnupOruCampaign = new ActionsOnupOruCampaign(webDriver);
        CampaignManagementDisplayCampaign campaignManagementDisplayCampaign = new CampaignManagementDisplayCampaign(webDriver);


//        try {
//
//            Thread.sleep(1000); // secures functionality on Approval (without, it would only work on Demo)
//
//            // searching for element, if already existing
//            campaignManagementSearch.searchCampaign(campaignId);
//            //
//            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));
//            //
//            String entryDisplay = campaignManagementSearch.getNumberOfEntriesParagraph().getText().trim();
//            //
//            if (!entryDisplay.equals(ConstantsGuiTexts.ZERO_OF_ZERO_FROM_ZERO_RECORDS) &&
//                    entryDisplay.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
//
//                // open campaign overview
//                campaignManagementSearch.clickFirstButtonEdit();
//                adminPortletHomepage.getNotAuthorisedOkButton().click();
//
//                // Deleting particular batch
//                assertTrue(batchInfo.getManualBatchDeleteButtonStatus(batchName));
//                batchInfo.deleteManualBatch(batchName);
//                Thread.sleep(3000);
//                batchInfo.clickConfirmBatchDeletionBtn();
//                Thread.sleep(3000);
//
//            }
//
//        } catch (Throwable t) {
//            // intentionally blank
//        }

//        Thread.sleep(1000); // secures functionality on Approval (without, it would only work on Demo)

        // searching for element, if already existing
//        campaignManagementSearch.searchCampaign(campaignId);
//        //
////        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));
//        //
//        String entryDisplay = campaignManagementSearch.getNumberOfEntriesParagraph().getText().trim();
//        //
//        if (!entryDisplay.equals(ConstantsGuiTexts.ZERO_OF_ZERO_FROM_ZERO_RECORDS) &&
//                entryDisplay.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
//
//            // open campaign overview
//            campaignManagementSearch.clickFirstButtonEdit();
//
//            try {
//                adminPortletHomepage.getNotAuthorisedOkButton().click();
//            } catch (Exception ignored) {
//            }

        actionsOnupOruCampaign.openCampaignManagementSearch(adminPortletHomepage);

        actionsOnupOruCampaign.searchAndOpenCampaign(
                adminPortletHomepage,
                campaignManagementSearch,
                campaignId,
                webDriver);

        // Deleting particular batch
        ReusableMethods.pageDownAct(webDriver);
        ReusableMethods.waitForClickablility(campaignManagementDisplayCampaign.getButtonManuallyEditBatch().get(1), 5, webDriver);

        if (batchInfo.isManualBatchAvailable(batchName)) {
            assertTrue(batchInfo.getManualBatchDeleteButtonStatus(batchName));
            batchInfo.deleteManualBatch(batchName);
            ReusableMethods.clickElement(batchInfo.getConfirmBatchDeletionBtn(), 3, webDriver);
        } else System.out.println("Manual Batch " + batchName + " does not exist.");

    }


    /**
     * Deleting automatically approved batches.
     *
     * @param batchName
     * @param campaignManagementDisplayCampaign
     * @param batchInfo
     */
    public void deleteAutomaticBatchByName(
            String batchName,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            Batch_info batchInfo) {

        if (campaignManagementDisplayCampaign.checkExistenceAutomaticallyApprovedBatchByName(batchName)) {

            campaignManagementDisplayCampaign.clickDeleteAutomaticallyApprovedBatchesApprovedBatchVinsByName(
                    batchName);

            batchInfo.clickConfirmBatchDeletionBtn();
        }
    }

    /**
     * @throws InterruptedException
     */
    public void createAutomaticallyApprovedBatchFilterMBT(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String batchName,
            String predecessorBatchName,
            String percent,
            String mbt,
            WebDriverWait webDriverWait) throws InterruptedException {

        openDialogueBatchCreation(campaignManagementDisplayCampaign);

        processBatchCreationStep1Automatic(batchInfo, batchName, predecessorBatchName, percent);

        processBatchCreationMBTStep2And3(batchInfo, campaignManagementDisplayCampaign, inboxCampaignEditBatchSequence, mbt, webDriverWait);

    }

    /**
     * @throws InterruptedException
     */
    public void createAutomaticallyApprovedBatchFilterPRN(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String batchName,
            String predecessorBatchName,
            String percent,
            String prn,
            WebDriverWait webDriverWait) throws InterruptedException {

        openDialogueBatchCreation(campaignManagementDisplayCampaign);

        processBatchCreationStep1Automatic(batchInfo, batchName, predecessorBatchName, percent);

        processBatchCreationPRNStep2And3(batchInfo, campaignManagementDisplayCampaign, inboxCampaignEditBatchSequence, prn, webDriverWait);

    }

    /**
     * @throws InterruptedException
     */
    public void createAutomaticallyApprovedBatchFilterImporter(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String batchName,
            String predecessorBatchName,
            String percent,
            String importer,
            WebDriverWait webDriverWait) throws InterruptedException {

        openDialogueBatchCreation(campaignManagementDisplayCampaign);

        processBatchCreationStep1Automatic(batchInfo, batchName, predecessorBatchName, percent);

        processBatchCreationImporterStep2And3(batchInfo, campaignManagementDisplayCampaign, inboxCampaignEditBatchSequence, importer, webDriverWait);

    }

    public void createAutomaticallyApprovedBatchFilterImporterAndMBT(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String batchName,
            String importer,
            String mbt,
            WebDriverWait webDriverWait) throws InterruptedException {

        openDialogueBatchCreation(campaignManagementDisplayCampaign);

        processBatchCreationStep1Manual(batchInfo, batchName);

        processBatchCreationImporterAndMBTStep2And3(
                batchInfo,
                campaignManagementDisplayCampaign,
                inboxCampaignEditBatchSequence,
                importer,
                mbt,
                webDriverWait);

    }

    /**
     * @throws InterruptedException
     */
    public void createAutomaticallyApprovedBatchFilterPKN(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignCreateNewBatchSequence inboxCampaignCreateNewBatchSequence,
            String batchName,
            String predecessorBatchName,
            String percent,
            String[] pknFrom,
            String[] pknTo,
            WebDriver webDriver) throws InterruptedException {

        openDialogueBatchCreation(campaignManagementDisplayCampaign);

        processBatchCreationStep1Automatic(batchInfo, batchName, predecessorBatchName, percent);

        inboxCampaignCreateNewBatchSequence.clickButtonNextVehicleFiltering();

        inboxCampaignCreateNewBatchSequence.getWeekFrom().sendKeys(pknFrom[0]);
        inboxCampaignCreateNewBatchSequence.getDayFrom().sendKeys(pknFrom[1]);
        inboxCampaignCreateNewBatchSequence.getFactoryFrom().sendKeys(pknFrom[2]);
        inboxCampaignCreateNewBatchSequence.getYearFrom().sendKeys(pknFrom[3]);

        inboxCampaignCreateNewBatchSequence.getWeekTo().sendKeys(pknTo[0]);
        inboxCampaignCreateNewBatchSequence.getDayTo().sendKeys(pknTo[1]);
        inboxCampaignCreateNewBatchSequence.getFactoryTo().sendKeys(pknTo[2]);
        inboxCampaignCreateNewBatchSequence.getYearTo().sendKeys(pknTo[3]);

        inboxCampaignCreateNewBatchSequence.clickButtonNextTestFilter();

        Thread.sleep(2000);

        List<WebElement> checkboxes = this.webDriver.findElements(By.xpath("//div[contains(@class, 'checkbox')]//input[@type='checkbox']"));

        if (1 == checkboxes.size()) {
            inboxCampaignCreateNewBatchSequence.clickCheckBoxMoveVehicleFromVehiclePool();
        } else {

            for (WebElement checkbox : checkboxes) {
                checkbox.click();
            }

        }

        inboxCampaignCreateNewBatchSequence.clickButtonCreateBatch();

    }

    /**
     * @param campaignManagementDisplayCampaign
     */
    private void openDialogueBatchCreation(CampaignManagementDisplayCampaign campaignManagementDisplayCampaign) {

//        campaignManagementDisplayCampaign.clickButtonAddBatch();
        ReusableMethods.clickJS(campaignManagementDisplayCampaign.getButtonAddBatch(), webDriver);
    }

    /**
     * @param batchInfo
     * @param batchName
     * @param predecessorBatchName
     * @param percent
     */
    private void processBatchCreationStep1Automatic(
            Batch_info batchInfo,
            String batchName,
            String predecessorBatchName,
            String percent) {

        batchInfo.setbatchTitleInputField(batchName);
        batchInfo.clickAutomaticApprovalRadioButton();
        batchInfo.selectPredecessorByBatchTitle(predecessorBatchName);
        batchInfo.setTargetPercentageOfCompletion(percent);
    }

    /**
     * @param batchInfo
     * @param batchName
     */
    private void processBatchCreationStep1Manual(Batch_info batchInfo, String batchName) {

        batchInfo.setbatchTitleInputField(batchName);
        batchInfo.clickManualApprovalRadioButton();

    }

    /**
     * @param batchInfo
     * @param inboxCampaignEditBatchSequence
     * @param mbt
     * @param webDriverWait
     * @throws InterruptedException
     */
    private void processBatchCreationMBTStep2And3(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String mbt,
            WebDriverWait webDriverWait)
            throws InterruptedException {

        //
        inboxCampaignEditBatchSequence.clickButtonNextVehicleFiltering();
        //
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inboxCampaignEditBatchSequence.getButtonNextTestFilter()));
        //
        batchInfo.setMBTInputField(mbt);

        processBatchCreationStep3(campaignManagementDisplayCampaign, inboxCampaignEditBatchSequence, webDriverWait);

    }

    /**
     * @param batchInfo
     * @param inboxCampaignEditBatchSequence
     * @param prn
     * @param webDriverWait
     * @throws InterruptedException
     */
    private void processBatchCreationPRNStep2And3(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String prn,
            WebDriverWait webDriverWait)
            throws InterruptedException {

        //
        inboxCampaignEditBatchSequence.clickButtonNextVehicleFiltering();
        //
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inboxCampaignEditBatchSequence.getButtonNextTestFilter()));
        //
        batchInfo.setPRNumbersInputField(prn);

        processBatchCreationStep3(campaignManagementDisplayCampaign, inboxCampaignEditBatchSequence, webDriverWait);

    }

    /**
     * @param batchInfo
     * @param inboxCampaignEditBatchSequence
     * @param importer
     * @param webDriverWait
     * @throws InterruptedException
     */
    private void processBatchCreationImporterStep2And3(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String importer,
            WebDriverWait webDriverWait) {

        ReusableMethods.checkTime(611);
        inboxCampaignEditBatchSequence.clickButtonNextVehicleFiltering();
        ReusableMethods.checkTime(613);
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inboxCampaignEditBatchSequence.getButtonNextTestFilter()));
        ReusableMethods.checkTime(615);
        batchInfo.setImporterInputField(importer);
        ReusableMethods.checkTime(617);
        processBatchCreationStep3(campaignManagementDisplayCampaign, inboxCampaignEditBatchSequence, webDriverWait);
        ReusableMethods.checkTime(619);
    }

    /**
     * @param batchInfo
     * @param inboxCampaignEditBatchSequence
     * @param importer
     * @param mbt
     * @param webDriverWait
     * @throws InterruptedException
     */
    private void processBatchCreationImporterAndMBTStep2And3(
            Batch_info batchInfo,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            String importer,
            String mbt,
            WebDriverWait webDriverWait)
            throws InterruptedException {

        //
        inboxCampaignEditBatchSequence.clickButtonNextVehicleFiltering();
        //
        webDriverWait.until(ExpectedConditions.elementToBeClickable(inboxCampaignEditBatchSequence.getButtonNextTestFilter()));
        //
        batchInfo.setImporterInputField(importer);
        batchInfo.setMBTInputField(mbt);

        processBatchCreationStep3(campaignManagementDisplayCampaign, inboxCampaignEditBatchSequence, webDriverWait);

    }

    /**
     * @param inboxCampaignEditBatchSequence
     * @param webDriverWait
     * @throws InterruptedException
     */
    private void processBatchCreationStep3(
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence,
            WebDriverWait webDriverWait) {

        inboxCampaignEditBatchSequence.clickButtonNextTestFilter();
        clickCheckBoxIfAvailable(inboxCampaignEditBatchSequence);
        inboxCampaignEditBatchSequence.clickButtonCreateBatch();
        //
        webDriverWait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));

    }

    /**
     * @param inboxCampaignEditBatchSequence
     * @throws InterruptedException
     */
    private void clickCheckBoxIfAvailable(InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence) {

        // waiting for loaded data in table above checkbox
        try {
            ReusableMethods.waitForClickablility(inboxCampaignEditBatchSequence.getCheckBoxMoveVehicleFromVehiclePool(), 20, webDriver);
            if (null != inboxCampaignEditBatchSequence.getCheckBoxMoveVehicleFromVehiclePool()) {
                inboxCampaignEditBatchSequence.clickCheckBoxMoveVehicleFromVehiclePool();
            }
        } catch (Exception exception) {
            System.out.println("WARNING: CheckBox wasn't available for creating or editing batch.");
        }
    }

    /**
     * @param campaignId
     * @param batchName
     * @param adminPortletHomepage
     * @param campaignManagementSearch
     * @param campaignManagementDisplayCampaign
     * @param batchInfo
     * @param webDriver
     * @throws Exception
     */
    public void approveAutomaticBatchOfCampaignByName(
            String campaignId,
            String batchName,
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            Batch_info batchInfo,
            WebDriver webDriver) throws Exception {

        new ActionsOnupOruCampaign(webDriver).searchAndOpenCampaign(
                adminPortletHomepage,
                campaignManagementSearch,
                campaignId,
                webDriver);

        //approve of default batch
        Thread.sleep(2000);

        campaignManagementDisplayCampaign.clickAutomaticBatchApprovedCheckboxBatchByName(batchName);

        batchInfo.clickButtonVerifyAndApplyChanges();
        Thread.sleep(2000);
        batchInfo.clickapplybutton();

        Thread.sleep(7000);

    }

    /**
     * @param campaignId
     * @param batchName
     * @param adminPortletHomepage
     * @param campaignManagementSearch
     * @param campaignManagementDisplayCampaign
     * @param batchInfo
     * @param webDriver
     * @throws Exception
     */
    public void approveManualBatchOfCampaignByName(
            String campaignId,
            String batchName,
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            Batch_info batchInfo,
            WebDriver webDriver) throws Exception {

        new ActionsOnupOruCampaign(webDriver).searchAndOpenCampaign(
                adminPortletHomepage,
                campaignManagementSearch,
                campaignId,
                webDriver);

        //approve of default batch
        Thread.sleep(2000);

        campaignManagementDisplayCampaign.clickManualBatchApprovedCheckboxBatchByName(batchName);

        batchInfo.clickButtonVerifyAndApplyChanges();
        Thread.sleep(2000);
        batchInfo.clickapplybutton();

        Thread.sleep(7000);

    }

}
