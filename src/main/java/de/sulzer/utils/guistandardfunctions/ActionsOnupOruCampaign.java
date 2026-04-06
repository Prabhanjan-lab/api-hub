/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.campaignmanagement.CampaignManagementDisplayCampaign;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.campaignmanagement.InboxPage;
import de.sulzer.pages.campaignmanagement.inbox.*;
import de.sulzer.pages.campaignmanagement.inbox.popUpDialogues.DialogueAddNewCriterion;
import de.sulzer.pages.campaignmanagement.inbox.popUpDialogues.DialogueInspectVehicles;
import de.sulzer.pages.genericelements.Page;
import de.sulzer.pages.oruoverviewpage.Batch_info;
import de.sulzer.pages.oruoverviewpage.ORUOverviewPage;
import de.sulzer.pages.testingmenu.recall.AddRecallSystemModal;
import de.sulzer.pages.testingmenu.recall.ManualRecallSystemPage;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.pages.util.constants.ConstantsCampaignCreation;
import de.sulzer.utils.CreateRandomChars;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sulzer GmbH
 */
public class ActionsOnupOruCampaign extends Page {

    /**
     *
     */
    WebDriver webDriver;

    public ActionsOnupOruCampaign(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }

    static final String GOCAT = "GOCAT";
    public static final String TRANSLATION_INPUT = "malo código fuente";
    public static final String TRANSLATION_OUTPUT = "bad sourcecode";

    /**
     * @param recallID
     * @param amcode
     * @param webDriver
     * @param translationInput1
     * @param translationOutput
     * @param voFlashAssignment
     * @throws Exception
     */

    public void createORUCampaign(
            String recallID,
            String amcode,
            int domainDataName, // e.g.: MIB2+, cGW, OCU2 etc.
            int configuration, // Audi, Porsche etc.
            WebDriver webDriver,
            String translationInput1,
            String translationOutput,
            boolean encryption,
            VOFlashAssignment voFlashAssignment) throws Exception {

        InboxPage inboxPage = new InboxPage(webDriver);
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(webDriver);
        WebDriverWait getWebDriverWait = new WebDriverWait(webDriver, 180);
        InboxPageDomainSelection domainSelection = new InboxPageDomainSelection(webDriver);
        InboxPageTranslationProperties translationProperties = new InboxPageTranslationProperties(webDriver);


        ReusableMethods.openCampaignManagement(adminPortletHomepage, webDriver);
        adminPortletHomepage.clickCampaignManagementInbox();

        getWebDriverWait.until(ExpectedConditions.elementToBeClickable(inboxPage.getInboxRecallSearchField()));
        inboxPage.setInboxRecallSearchField(recallID);

        // recall elements to be greater or equal 1
//        getWebDriverWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tbody[@id='recallTBody']//i[@class='fa fa-user']|//tbody[@id='recallTBody']//i[@class='fa fa-cogs']"), 0));
        ReusableMethods.waitForVisibility(inboxPage.getRecallElement(), 20, webDriver);
        inboxPage.clickRecallTableListItemCheckBox(recallID, webDriver);
        inboxPage.clickOlderThan100DaysSwitch();
        getWebDriverWait.until(ExpectedConditions.elementToBeClickable(inboxPage.getService42SearchField()));
        inboxPage.setService42SearchField(amcode);

        // service42 elements to be 1
//        getWebDriverWait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody[@id='service42TBody']//i[@class='fa fa-user']"), 1));
        ReusableMethods.waitForVisibility(inboxPage.getService42FirstElement(), 20, webDriver);
        inboxPage.clickService42TableListItemCheckBox(amcode, webDriver);

        // finishing campaign creation
        getWebDriverWait.until(ExpectedConditions.elementToBeClickable(inboxPage.getCreateCampaignButton()));

        // create campaign
        assertTrue(inboxPage.getCreateCampaignButton().isEnabled(), "There is a problem with CreateCampaignButton");
        inboxPage.clickCreateCampaignButton();
        ReusableMethods.waitForClickablility(domainSelection.getTranslationPropertiesButton(), 5, webDriver);

        InboxPageDomainSelection _InboxPageDomainSelection = new InboxPageDomainSelection(webDriver);
        Thread.sleep(1000);
        _InboxPageDomainSelection.selectDomainByIndex(domainDataName);

        if (encryption) {

            String booleanEncryption = _InboxPageDomainSelection.getEncryptFlashfile().getAttribute("ng-reflect-model");

            if (!Boolean.valueOf(booleanEncryption)) {
                _InboxPageDomainSelection.clickEncryptFlashfile();
            }

        }


        assertTrue(domainSelection.getTranslationPropertiesButton().isEnabled(), "TranslationPropertiesButton is not enabled");
        domainSelection.clickTranslationPropertiesButton();

        ReusableMethods.waitForClickablility(translationProperties.getButtonNext(), 10, webDriver);
        translationProperties.clickButtonNext();

        ReusableMethods.waitForClickablility(translationProperties.getInputTranslationField(), 10, webDriver);
        translationProperties.setInputTranslationField(translationInput1);
        translationProperties.setOutputTranslationField(translationOutput);
        assertTrue(translationProperties.getButtonCreateCampaign().isEnabled(), "ButtonCreateCampaign is not enabled");
        translationProperties.clickButtonCreateCampaign();
        Thread.sleep(2000);

        if (voFlashAssignment.getAmountFlashMedia() >= 1) {
            addingCriteriaFlashMediaAssignment(voFlashAssignment, webDriver);
        }

        if (voFlashAssignment.getAmountFlashMedia() > 1) {
            Thread.sleep(500);
            String a;

            for (int i = 1; i <= voFlashAssignment.getAmountFlashMedia(); i++) {
                if (i < 10) {
                    a = "0" + i;
                } else {
                    a = i + "";
                }

                webDriver.findElement(By.xpath("//div[@id='" + recallID + "." + a + "']//input[@type='checkbox']")).click();
            }

            webDriver.findElement(By.xpath("//button[contains(text(),'Apply')]")).click();
        }
    }




    public void createORUCampaignWith_cGW2_FlashMedium(
            String recallID,
            String amcode,
            WebDriver webDriver,
            String translationInput1,
            String translationOutput,
            VOFlashAssignment voFlashAssignment) throws Exception {

        System.out.println("Creating a Oru Campaign with Recall:" + recallID + " and AM:" + amcode + "...");

        createORUCampaignWith_cGW2_FlashMedium(recallID, amcode, 1, 0, webDriver, translationInput1, translationOutput, true, voFlashAssignment);

    }

    public void createORUCampaignWith_cGW2_FlashMedium(
            String recallID,
            String amcode,
            int domainDataName, // e.g.: MIB2+, cGW, OCU2 etc.
            int configuration, // Audi, Porsche etc.
            WebDriver webDriver,
            String translationInput1,
            String translationOutput,
            VOFlashAssignment voFlashAssignment) throws Exception {

        System.out.println("Creating a Oru Campaign with Recall ID : " + recallID + " and AM : " + amcode + "...");

        createORUCampaignWith_cGW2_FlashMedium(recallID, amcode, domainDataName, configuration, webDriver, translationInput1, translationOutput, false, voFlashAssignment);
    }

    /**
     * @param recallID
     * @param amcode
     * @param webDriver
     * @param translationInput1
     * @param translationOutput
     * @param voFlashAssignment
     * @throws Exception
     */
    public void createORUCampaignWith_cGW2_FlashMedium(
            String recallID,
            String amcode,
            int domainDataName, // e.g.: MIB2+, cGW, OCU2 etc.
            int configuration, // Audi, Porsche etc.
            WebDriver webDriver,
            String translationInput1,
            String translationOutput,
            boolean encryption,
            VOFlashAssignment voFlashAssignment) throws Exception {

        InboxPage inboxPage = new InboxPage(webDriver);
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(webDriver);
        WebDriverWait getWebDriverWait = new WebDriverWait(webDriver, 180);
        InboxPageDomainSelection domainSelection = new InboxPageDomainSelection(webDriver);
        InboxPageTranslationProperties translationProperties = new InboxPageTranslationProperties(webDriver);
        InboxPageDomainSelection _InboxPageDomainSelection = new InboxPageDomainSelection(webDriver);


        ReusableMethods.openCampaignManagement(adminPortletHomepage, webDriver);
        adminPortletHomepage.clickCampaignManagementInbox();

        getWebDriverWait.until(ExpectedConditions.elementToBeClickable(inboxPage.getInboxRecallSearchField()));
        inboxPage.setInboxRecallSearchField(recallID);

        // recall elements to be greater or equal 1
        ReusableMethods.waitForVisibility(inboxPage.getRecallElement(), 20, webDriver);
        inboxPage.clickRecallTableListItemCheckBox(recallID, webDriver);
        inboxPage.clickOlderThan100DaysSwitch();
        getWebDriverWait.until(ExpectedConditions.elementToBeClickable(inboxPage.getService42SearchField()));
        inboxPage.setService42SearchField(amcode);

        // service42 elements to be 1
        ReusableMethods.waitForVisibility(inboxPage.getService42FirstElement(), 20, webDriver);
        inboxPage.clickService42TableListItemCheckBox(amcode, webDriver);

        // finishing campaign creation
        getWebDriverWait.until(ExpectedConditions.elementToBeClickable(inboxPage.getCreateCampaignButton()));

        // create campaign
        assertTrue(inboxPage.getCreateCampaignButton().isEnabled(), "There is a problem with CreateCampaignButton");
        inboxPage.clickCreateCampaignButton();
        ReusableMethods.waitForClickablility(domainSelection.getTranslationPropertiesButton(), 5, webDriver);


        Thread.sleep(1000);
        _InboxPageDomainSelection.selectDomainByIndex(domainDataName);

        if (encryption) {

            String booleanEncryption = _InboxPageDomainSelection.getEncryptFlashfile().getAttribute("ng-reflect-model");

            if (!Boolean.parseBoolean(booleanEncryption)) {
                _InboxPageDomainSelection.clickEncryptFlashfile();
            }
        }

        assertTrue(domainSelection.getTranslationPropertiesButton().isEnabled(), "TranslationPropertiesButton is not enabled");
        domainSelection.clickTranslationPropertiesButton();

        ReusableMethods.waitForClickablility(translationProperties.getButtonNext(), 10, webDriver);
        translationProperties.clickButtonNext();

        ReusableMethods.waitForClickablility(translationProperties.getInputTranslationField(), 10, webDriver);
        translationProperties.setInputTranslationField(translationInput1);
        translationProperties.setOutputTranslationField(translationOutput);
        assertTrue(translationProperties.getButtonCreateCampaign().isEnabled(), "ButtonCreateCampaign is not enabled");
        translationProperties.clickButtonCreateCampaign();
        Thread.sleep(2000);

        if (voFlashAssignment.getAmountFlashMedia() >= 1) {
            addingCriteriaFlashMediaAssignment(voFlashAssignment, webDriver);
        }

        if (voFlashAssignment.getAmountFlashMedia() > 1) {
            Thread.sleep(500);
            String a;

            for (int i = 1; i <= voFlashAssignment.getAmountFlashMedia(); i++) {
                if (i < 10) {
                    a = "0" + i;
                } else {
                    a = i + "";
                }

                webDriver.findElement(By.xpath("//div[@id='" + recallID + "." + a + "']//input[@type='checkbox']")).click();
            }

            webDriver.findElement(By.xpath("//button[contains(text(),'Apply')]")).click();
        }
    }

    /**
     * @param inboxPage
     * @param recallId
     * @param amcode
     * @param domainDataName
     * @param configuration
     * @param webDriver
     * @param translationInput
     * @param translationOutput
     * @param voFlashAssignment
     * @throws Exception
     */
    public void createApproveORUCampaign(
            InboxPage inboxPage,
            String recallId,
            String amcode,
            int domainDataName, // e.g.: MIB2+, cGW, OCU2 etc.
            int configuration, // Audi, Porsche etc.
            WebDriver webDriver,
            String translationInput,
            String translationOutput,
            VOFlashAssignment voFlashAssignment) throws Exception {

        WebDriverWait wait = new WebDriverWait(webDriver, 900); // waiting for 15 minutes, increased from 10min due to upload times

        ORUOverviewPage oruOverviewPage = new ORUOverviewPage(webDriver);

        selectReCallAndService42(inboxPage, recallId, amcode, webDriver, wait);

        //
        InboxPageDomainSelection inboxPageDomainSelection = new InboxPageDomainSelection(webDriver);
        //
        wait.until(ExpectedConditions.visibilityOf(inboxPageDomainSelection.getTranslationPropertiesButton()));
        // domain selection
        inboxPageDomainSelection.selectDomainByIndex(domainDataName);
        inboxPageDomainSelection.clickTranslationPropertiesButton();

        //
        InboxPageTranslationProperties inboxPageTranslationProperties = new InboxPageTranslationProperties(webDriver);
        //
        wait.until(ExpectedConditions.visibilityOf(inboxPageTranslationProperties.getButtonNext()));
        if (this.isEqual(translationOutput, ActionsOnupOruCampaign.GOCAT)) {
            inboxPageTranslationProperties.clickTranslateGoCatCheckbox();
        }

        inboxPageTranslationProperties.clickButtonNext();
        //
        wait.until(ExpectedConditions.visibilityOf(inboxPageTranslationProperties.getInputTranslationField()));
        //
        inboxPageTranslationProperties.setInputTranslationField(translationInput);

        if (!ActionsOnupOruCampaign.GOCAT.equals(translationOutput)) {
            inboxPageTranslationProperties.setOutputTranslationField(translationOutput);
        }
        ReusableMethods.screenShot("A2", webDriver);
        if (this.isEqual(translationOutput, ActionsOnupOruCampaign.GOCAT)) {
            inboxPageTranslationProperties.clickRedButtonTranslateViaGocat();
        }
        ReusableMethods.screenShot("A3", webDriver);
        //
        inboxPageTranslationProperties.clickButtonCreateCampaign();
        ReusableMethods.screenShot("A4", webDriver);
        if (this.isEqual(translationOutput, ActionsOnupOruCampaign.GOCAT)) {
            inboxPageTranslationProperties.clickConfirmButton();
        }

        addingCriteriaFlashMediaAssignment(voFlashAssignment, webDriver);
        ReusableMethods.screenShot("A5", webDriver);
        //
        InboxCampaignCreation inboxCampaignCreation = new InboxCampaignCreation(webDriver);
        //
        // unfolding smaller/single progress bars
        wait.until(ExpectedConditions.visibilityOf(inboxCampaignCreation.getFoldingSingleProgressBars()));
        // open/showing all smaller progress bars
        inboxCampaignCreation.clickFoldingSingleProgressBars();
        ReusableMethods.screenShot("A6", webDriver);
        // waiting for progress bar reaching 100%
        wait.until(ExpectedConditions.textToBePresentInElement(inboxCampaignCreation.getProgressBarOverall(), ConstantsCampaignCreation.PERCENT100));
//        wait.until(ExpectedConditions.textToBePresentInElement(oruOverviewPage.getHundertProzent(), "- 100 %"));
        new ActionsOnupOruCampaignBatches(webDriver).moveVINsToDefaultBatch(webDriver, wait, inboxCampaignCreation);

        //
        wait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(inboxCampaignCreation.getCheckBoxApproved())));
        Thread.sleep(15000);

        this.approveCampaign(webDriver, wait, inboxCampaignCreation, new CampaignManagementDisplayCampaign(webDriver));

    }

    /**
     * @param wait
     * @param inboxCampaignCreation
     */
    public void approveCampaign(
            WebDriver webDriver,
            WebDriverWait wait,
            InboxCampaignCreation inboxCampaignCreation,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign) {

        // set approval
        inboxCampaignCreation.clickCheckBoxApproved();

        // verify and apply
        wait.until(ExpectedConditions.elementToBeClickable(campaignManagementDisplayCampaign.getButtonVerifiyAndApplyChanges()));
        campaignManagementDisplayCampaign.clickButtonVerifiyAndApplyChanges();

        // apply process
        InboxCampaignSavingChangesORU inboxCampaignSavingChangesORU = new InboxCampaignSavingChangesORU(webDriver);
        //
        wait.until(ExpectedConditions.elementToBeClickable(inboxCampaignSavingChangesORU.getButtonApply()));
        //
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inboxCampaignSavingChangesORU.getButtonApply().sendKeys(Keys.ENTER);

    }

    /**
     * @param wait
     * @param inboxCampaignCreation
     * @param campaignManagementDisplayCampaign
     * @param inboxCampaignSavingChangesORU
     */
    public void approveCampaign(
            WebDriverWait wait,
            InboxCampaignCreation inboxCampaignCreation,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            InboxCampaignSavingChangesORU inboxCampaignSavingChangesORU) {

        // verify and apply
        wait.until(ExpectedConditions.elementToBeClickable(campaignManagementDisplayCampaign.getButtonVerifiyAndApplyChanges()));
        campaignManagementDisplayCampaign.clickButtonVerifiyAndApplyChanges();

        // apply process
        wait.until(ExpectedConditions.elementToBeClickable(inboxCampaignSavingChangesORU.getButtonApply()));
        //
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inboxCampaignSavingChangesORU.getButtonApply().sendKeys(Keys.ENTER);

    }

    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param batchInfo
     * @param AM_CODE
     * @param PATH_TO_FILE_TO_BE_UPLOADED
     * @param RECALL_ID
     * @param webDriver
     * @throws Exception
     */

    //        TODO: As of 24.08.2022, since the system gave an error in AM creation and this problem is still not fixed, the previously created AM was used.

// Old method

//    public void createOruCampaignWithImportedReCall(
//            AdminPortletHomepage adminPortletHomepage,
//            ManualService42SystemPage manualService42SystemPage,
//            AddService42SystemModal addService42SystemModal,
//            Batch_info batchInfo,
//            String AM_CODE,
//            String PATH_TO_FILE_TO_BE_UPLOADED,
//            String RECALL_ID,
//            WebDriver webDriver) throws Exception {
//
//        //
//        new ActionsOnupService42(webDriver).createService42WaitUploadCompleted(
//                adminPortletHomepage,
//                manualService42SystemPage,
//                addService42SystemModal,
//                AM_CODE,
//                CreateRandomChars.getLetters35(15),
//                CreateRandomChars.getNumbers10(20),
//                CreateRandomChars.getLetters35(11),
//                PATH_TO_FILE_TO_BE_UPLOADED,
//                ActionsOnupReCall.COMMENT,
//                webDriver);
//
//        this.openInboxCampaign(adminPortletHomepage);
//
//        //
//        this.createORUCampaign(
//                RECALL_ID,
//                AM_CODE,
//                webDriver,
//                ActionsOnupOruCampaign.TRANSLATION_INPUT,
//                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
//                new VOFlashAssignment());
//
//        batchInfo.waitforORUprocessing();
//
//    }
    public void createOruCampaignWithImportedReCall(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            Batch_info batchInfo,
            String AM_CODE,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            String RECALL_ID,
            WebDriver webDriver) throws Exception {

        ActionsOnupReCall actionsOnupReCall = new ActionsOnupReCall(webDriver);
//        new ActionsOnupService42(webDriver).createService42WaitUploadCompleted(
//                adminPortletHomepage,
//                manualService42SystemPage,
//                addService42SystemModal,
//                AM_CODE,
//                CreateRandomChars.getLetters35(15),
//                CreateRandomChars.getNumbers10(20),
//                CreateRandomChars.getLetters35(11),
//                PATH_TO_FILE_TO_BE_UPLOADED,
//                ActionsOnupReCall.COMMENT,
//                webDriver);


        ReusableMethods.create_Recall(
                actionsOnupReCall,
                adminPortletHomepage,
                new ManualRecallSystemPage(webDriver),
                new AddRecallSystemModal(webDriver),
                webDriver,
                ReusableMethods.VIN1,
                ReusableMethods.recallUniqueID
        );

        this.openInboxCampaign(adminPortletHomepage);

        //
        this.createORUCampaignWith_cGW2_FlashMedium(
                RECALL_ID,
                AM_CODE,
                webDriver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                new VOFlashAssignment());

        batchInfo.waitForOruProcessing();

    }

    /**
     * @param adminPortletHomepage
     * @param inboxPage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param batchInfo
     * @param AM_CODE
     * @param PATH_TO_FILE_TO_BE_UPLOADED
     * @param RECALL_ID
     * @param voFlashAssignment
     * @param webDriver
     * @throws Exception
     */

//        TODO: As of 23.08.2022, since the system gave an error in AM creation and this problem is still not fixed, the previously created AM was used.
    public void createApprovedOruCampaignWithImportedReCall(
            AdminPortletHomepage adminPortletHomepage,
            InboxPage inboxPage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            Batch_info batchInfo,
            String AM_CODE,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            String RECALL_ID,
            VOFlashAssignment voFlashAssignment,
            WebDriver webDriver) throws Exception {


//        new ActionsOnupService42(webDriver).createService42WaitUploadCompleted(
//                adminPortletHomepage,
//                manualService42SystemPage,
//                addService42SystemModal,
//                AM_CODE,
//                CreateRandomChars.getLetters35(15),
//                CreateRandomChars.getNumbers10(20),
//                CreateRandomChars.getLetters35(11),
//                PATH_TO_FILE_TO_BE_UPLOADED,
//                ActionsOnupReCall.COMMENT,
//                webDriver);

        ActionsOnupReCall actionsOnupReCall = new ActionsOnupReCall(webDriver);

//        This method was added later.
        ReusableMethods.create_Recall(
                actionsOnupReCall,
                adminPortletHomepage,
                new ManualRecallSystemPage(webDriver),
                new AddRecallSystemModal(webDriver),
                webDriver,
                ReusableMethods.VIN1,
                ReusableMethods.recallUniqueIDOneTime
        );


        this.openInboxCampaign(adminPortletHomepage);

        //
        this.createApproveORUCampaign(
                inboxPage,
                RECALL_ID,
                AM_CODE,
                0, // MIB2+ == Infotainment
                0, // configuration: 1 = standard, 2 = standards Porsche MIB, 3 = Porsche - all MIB
                webDriver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                voFlashAssignment);

        batchInfo.waitForOruProcessing();

    }

    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param batchInfo
     * @param AM_CODE
     * @param PATH_TO_FILE_TO_BE_UPLOADED
     * @param RECALL_ID
     * @param RECALL_DISABLED_COUNTRY
     * @param RECALL_VIN
     * @param webDriver
     * @throws Exception
     */

//    Old version

//    public void createOruCampaignWithManualReCall(
//            AdminPortletHomepage adminPortletHomepage,
//            ManualService42SystemPage manualService42SystemPage,
//            AddService42SystemModal addService42SystemModal,
//            ManualRecallSystemPage manualRecallSystemPage,
//            AddRecallSystemModal addRecallSystemModal,
//            Batch_info batchInfo,
//            String AM_CODE,
//            String PATH_TO_FILE_TO_BE_UPLOADED,
//            String RECALL_ID,
//            String RECALL_DISABLED_COUNTRY,
//            String RECALL_VIN,
//            WebDriver webDriver) throws Exception {
//
//        creatingManuallyReCallAndService42(adminPortletHomepage, manualService42SystemPage, addService42SystemModal,
//                manualRecallSystemPage, addRecallSystemModal, AM_CODE, PATH_TO_FILE_TO_BE_UPLOADED, RECALL_ID,
//                RECALL_DISABLED_COUNTRY, RECALL_VIN, webDriver);
//
//        //
//        createORUCampaign(
//                RECALL_ID,
//                AM_CODE,
//                webDriver,
//                ActionsOnupOruCampaign.TRANSLATION_INPUT,
//                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
//                new VOFlashAssignment());
//
//        batchInfo.waitforORUprocessing();
//
//    }


//        TODO: As of 23.08.2022, since the system gave an error in AM creation and this problem is still not fixed, the previously created AM was used.
//    public void createOruCampaignWithManualReCall(
//            AdminPortletHomepage adminPortletHomepage,
//            ManualService42SystemPage manualService42SystemPage,
//            AddService42SystemModal addService42SystemModal,
//            ManualRecallSystemPage manualRecallSystemPage,
//            AddRecallSystemModal addRecallSystemModal,
//            Batch_info batchInfo,
//            String AM_CODE,
//            String PATH_TO_FILE_TO_BE_UPLOADED,
//            String RECALL_ID,
//            String RECALL_DISABLED_COUNTRY,
//            String RECALL_VIN,
//            WebDriver webDriver) throws Exception {
//
////        creatingManuallyReCallAndService42(adminPortletHomepage, manualService42SystemPage, addService42SystemModal,
////                manualRecallSystemPage, addRecallSystemModal, AM_CODE, PATH_TO_FILE_TO_BE_UPLOADED, RECALL_ID,
////                RECALL_DISABLED_COUNTRY, RECALL_VIN, webDriver);
//
//        ActionsOnupReCall actionsOnupReCall = new ActionsOnupReCall(webDriver);
//
//        actionsOnupReCall.createReCall(
//                adminPortletHomepage,
//                manualRecallSystemPage,
//                addRecallSystemModal,
//                RECALL_ID,
//                RECALL_ID,
//                "",
//                2,
//                RECALL_VIN,
//                webDriver);
//
//        Thread.sleep(5000);
//
//        openInboxCampaign(adminPortletHomepage);
//
//        //
//        createORUCampaignWith_cGW2_FlashMedium(
//                RECALL_ID,
//                AM_CODE,
//                webDriver,
//                ActionsOnupOruCampaign.TRANSLATION_INPUT,
//                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
//                new VOFlashAssignment());
//
//        batchInfo.waitForOruProcessing();
//
//    }


    public void createOruCampaignWithManualReCall(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            Batch_info batchInfo,
            String AM_CODE,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            String RECALL_ID,
            String RECALL_DISABLED_COUNTRY,
            String RECALL_VIN,
            WebDriver webDriver) throws Exception {


        creatingManuallyReCallAndService42(
                adminPortletHomepage,
                manualService42SystemPage,
                addService42SystemModal,
                manualRecallSystemPage,
                addRecallSystemModal,
                AM_CODE,
                PATH_TO_FILE_TO_BE_UPLOADED,
                RECALL_ID,
                RECALL_DISABLED_COUNTRY,
                RECALL_VIN,
                webDriver);

        Thread.sleep(5000);

        openInboxCampaign(adminPortletHomepage);


        createORUCampaignWith_cGW2_FlashMedium(
                RECALL_ID,
                AM_CODE,
                webDriver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                new VOFlashAssignment());

        batchInfo.waitForOruProcessing();

    }

    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param batchInfo
     * @param AM_CODE
     * @param PATH_TO_FILE_TO_BE_UPLOADED
     * @param RECALL_ID
     * @param RECALL_DISABLED_COUNTRY
     * @param RECALL_VIN
     * @param webDriver
     * @throws Exception
     */


    //        TODO: As of 23.08.2022, since the system gave an error in AM creation and this problem is still not fixed, the previously created AM was used.
    public void createOruCampaignWithManualReCallAndGoCatTranslation(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            Batch_info batchInfo,
            String AM_CODE,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            String RECALL_ID,
            String RECALL_DISABLED_COUNTRY,
            String RECALL_VIN,
            WebDriver webDriver) throws Exception {

//        creatingManuallyReCallAndService42(adminPortletHomepage, manualService42SystemPage, addService42SystemModal,
//                manualRecallSystemPage, addRecallSystemModal, AM_CODE, PATH_TO_FILE_TO_BE_UPLOADED, RECALL_ID,
//                RECALL_DISABLED_COUNTRY, RECALL_VIN, webDriver);

        //
        createORUCampaignWith_cGW2_FlashMedium(
                RECALL_ID,
                AM_CODE,
                webDriver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.GOCAT,
                new VOFlashAssignment());

        batchInfo.waitForOruProcessing();

    }

    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param batchInfo
     * @param AM_CODE
     * @param PATH_TO_FILE_TO_BE_UPLOADED
     * @param domainDataName
     * @param configuration
     * @param RECALL_ID
     * @param RECALL_DISABLED_COUNTRY
     * @param RECALL_VIN
     * @param webDriver
     * @throws Exception
     */
    public void createOruCampaignWithManualReCall(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            Batch_info batchInfo,
            String AM_CODE,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            int domainDataName, // e.g.: MIB2+, cGW, OCU2 etc.
            int configuration, // Audi, Porsche etc.
            String RECALL_ID,
            String RECALL_DISABLED_COUNTRY,
            String RECALL_VIN,
            WebDriver webDriver) throws Exception {

        creatingManuallyReCallAndService42(
                adminPortletHomepage,
                manualService42SystemPage,
                addService42SystemModal,
                manualRecallSystemPage,
                addRecallSystemModal,
                AM_CODE,
                PATH_TO_FILE_TO_BE_UPLOADED,
                RECALL_ID,
                RECALL_DISABLED_COUNTRY,
                RECALL_VIN,
                webDriver);

        //
        createORUCampaignWith_cGW2_FlashMedium(
                RECALL_ID,
                AM_CODE,
                domainDataName,
                configuration,
                webDriver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                new VOFlashAssignment());

        batchInfo.waitForOruProcessing();

    }

    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param AM_CODE
     * @param PATH_TO_FILE_TO_BE_UPLOADED
     * @param RECALL_ID
     * @param RECALL_DISABLED_COUNTRY
     * @param RECALL_VIN
     * @param webDriver
     */
    private void creatingManuallyReCallAndService42(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            String AM_CODE,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            String RECALL_ID,
            String RECALL_DISABLED_COUNTRY,
            String RECALL_VIN,
            WebDriver webDriver) {

        new ActionsOnupReCall(webDriver).createReCall(
                adminPortletHomepage,
                manualRecallSystemPage,
                addRecallSystemModal,
                RECALL_ID,
                RECALL_DISABLED_COUNTRY,
                RECALL_VIN,
                webDriver);
        //
        new ActionsOnupService42(webDriver).createService42WaitUploadCompleted(
                adminPortletHomepage,
                manualService42SystemPage,
                addService42SystemModal,
                AM_CODE,
                ReusableMethods.AM_DESCRIPTION, // Description
                ReusableMethods.AM_ID, // AM ID
                ReusableMethods.AM_TNR_NUMBER, // TNR number
                PATH_TO_FILE_TO_BE_UPLOADED,
                ReusableMethods.AM_COMMENT,
                webDriver);

        openInboxCampaign(adminPortletHomepage);
    }


    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param batchInfo
     * @param AM_CODE
     * @param RECALL_ID
     * @param RECALL_DISABLED_COUNTRY
     * @param RECALL_VIN
     * @param webDriver
     * @throws Exception
     */
    public void createOruCampaignWithManualReCallImportedService42(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            Batch_info batchInfo,
            String AM_CODE,
            int domainDataName,
            int configuration,
            String RECALL_ID,
            String RECALL_DISABLED_COUNTRY,
            String RECALL_VIN,
            WebDriver webDriver) throws Exception {

        new ActionsOnupReCall(webDriver).createReCall(
                adminPortletHomepage,
                manualRecallSystemPage,
                addRecallSystemModal,
                RECALL_ID,
                RECALL_DISABLED_COUNTRY,
                RECALL_VIN,
                webDriver);

        this.openInboxCampaign(adminPortletHomepage);

        //
        createORUCampaignWith_cGW2_FlashMedium(
                RECALL_ID,
                AM_CODE,
                domainDataName,
                configuration,
                webDriver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                new VOFlashAssignment());

        batchInfo.waitForOruProcessing();

    }

    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param batchInfo
     * @param AM_CODE
     * @param PATH_TO_FILE_TO_BE_UPLOADED
     * @param RECALL_ID
     * @param RECALL_DISABLED_COUNTRY
     * @param RECALL_VIN
     * @param webDriver
     * @throws Exception
     */
    public void createApprovedOruCampaignWithManualReCall(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            Batch_info batchInfo,
            String AM_CODE,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            String RECALL_ID,
            String RECALL_DISABLED_COUNTRY,
            String RECALL_VIN,
            WebDriver webDriver) throws Exception {

        this.creatingManuallyReCallAndService42(
                adminPortletHomepage,
                manualService42SystemPage,
                addService42SystemModal,
                manualRecallSystemPage,
                addRecallSystemModal,
                AM_CODE,
                PATH_TO_FILE_TO_BE_UPLOADED,
                RECALL_ID,
                RECALL_DISABLED_COUNTRY,
                RECALL_VIN,
                webDriver);

        //
        this.createApproveORUCampaign(
                new InboxPage(webDriver),
                RECALL_ID,
                AM_CODE,
                0, // MIB2+ == Infotainment
                0, // configuration: 1 = standard, 2 = standards Porsche MIB, 3 = Porsche - all MIB
                webDriver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                new VOFlashAssignment());

        batchInfo.waitForOruProcessing();

    }

    /**
     * @param voFlashAssignment
     * @param webDriver
     */
    private void addingCriteriaFlashMediaAssignment(VOFlashAssignment voFlashAssignment, WebDriver webDriver) {

        /*
         * If several criteria are present for ORU campaign, flash medium will be assigned
         * to all criteria (simple solution).
         */
        InboxPageFlashMediumAssignment inboxPageFlashMediumAssignment =
                new InboxPageFlashMediumAssignment(webDriver);

        // Check if flash assignment is required
        boolean flashAssignement =
                webDriver.findElements(
                        By.xpath("//div/h4[@class='modal-title pull-left'][contains(text(), 'Flashmedium Assignment')]")).size() > 0;

        if (flashAssignement) {
            inboxPageFlashMediumAssignment.selectCheckBoxesFlashMediumAssignment(voFlashAssignment.getAmountFlashMedia());
            inboxPageFlashMediumAssignment.clickButtonApply();
        }

    }

    public void startCreateAndAbortCampaignCreation(
            AdminPortletHomepage adminPortletHomepage,
            InboxPage inboxPage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            Batch_info batchInfo,
            String AM_CODE,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            String RECALL_ID,
            VOFlashAssignment voFlashAssignment,
            WebDriver webDriver) throws Exception {

        //
        new ActionsOnupService42(webDriver).createService42WaitUploadCompleted(
                adminPortletHomepage,
                manualService42SystemPage,
                addService42SystemModal,
                AM_CODE,
                CreateRandomChars.getLetters35(15),
                CreateRandomChars.getNumbers10(20),
                CreateRandomChars.getLetters35(11),
                PATH_TO_FILE_TO_BE_UPLOADED,
                ActionsOnupReCall.COMMENT,
                webDriver);

        this.openInboxCampaign(adminPortletHomepage);

        //
        this.createAndAbortCampaignCreation(
                inboxPage,
                RECALL_ID,
                AM_CODE,
                0, // MIB2+ == Infotainment
                0, // configuration: 1 = standard, 2 = standards Porsche MIB, 3 = Porsche - all MIB
                webDriver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                voFlashAssignment);

    }

    private void createAndAbortCampaignCreation(
            InboxPage inboxPage,
            String recallId,
            String amcode,
            int domainDataName, // e.g.: MIB2+, cGW, OCU2 etc.
            int configuration, // Audi, Porsche etc.
            WebDriver webDriver,
            String translationInput,
            String translationOutput,
            VOFlashAssignment voFlashAssignment) throws Exception {

        WebDriverWait wait = new WebDriverWait(webDriver, 900); // waiting for 15 minutes, increased from 10min due to upload times

        this.selectReCallAndService42(inboxPage, recallId, amcode, webDriver, wait);

        //
        InboxPageDomainSelection inboxPageDomainSelection = new InboxPageDomainSelection(webDriver);
        //
        wait.until(ExpectedConditions.visibilityOf(inboxPageDomainSelection.getTranslationPropertiesButton()));
        //
        inboxPageDomainSelection.clickCancelButton();

    }

    /**
     * @param wait
     * @param adminPortletHomepage
     * @param campaignManagementSearch
     * @param campaignId
     */
//    public void deleteOruCampaign(
//            WebDriver webDriver,
//            WebDriverWait wait,
//            AdminPortletHomepage adminPortletHomepage,
//            CampaignManagementSearch campaignManagementSearch,
//            String campaignId) {
//
//        openSearchCampaign(adminPortletHomepage);
//
//        try {
//
//            Thread.sleep(1000); // secures functionality on Approval (without, it would only work on Demo)
//
//            // searching for element, if already existing
//            campaignManagementSearch.searchCampaign(campaignId);
//
//            //
////            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));
//            //
//            String entryDisplay = campaignManagementSearch.getNumberOfEntriesParagraph().getText().trim();
//            //
//            if (!entryDisplay.equals(ConstantsGuiTexts.ZERO_OF_ZERO_FROM_ZERO_RECORDS) &&
//                    entryDisplay.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
//
//                // deleting created ORU campaign by ID
//                for (WebElement element : campaignManagementSearch.getTableEntries()) {
//
//                    WebElement firstCell = element.findElement(By.xpath("./td[2]"));
//
//                    if (firstCell.getText().trim().contains(campaignId)) {
//
//                        element.findElement(By.xpath(".//button[contains(@class, 'btn btn-danger btn-xs')]")).click();
//                        campaignManagementSearch.clickConfirmDelete();
//
//                        System.out.println("CAMPAIGN_ID : " + campaignId + " has been deleted successfully!");
//
//                        //
////                        Thread.sleep(10000);
//                        ReusableMethods.waitForPageToLoad(10, webDriver);
//
//                        DialogueOruDeletionConfirmation dialogueOruDeletionConfirmation =
//                                new DialogueOruDeletionConfirmation(webDriver);
//
//                        if (dialogueOruDeletionConfirmation.getHeaderWarning().isDisplayed()) {
//                            dialogueOruDeletionConfirmation.clickButtonConfirmation();
//                        }
//                    }
//
//                }
//
//            }
//
//        } catch (Throwable t) {
//            // intentionally blank
//        }
//
//    }




    public void deleteOruCampaign(
            WebDriver webDriver,
            WebDriverWait wait,
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            String campaignId) {

        openCampaignManagementSearch(adminPortletHomepage);

//        try {

//            Thread.sleep(1000); // secures functionality on Approval (without, it would only work on Demo)

            // searching for element, if already existing
            campaignManagementSearch.searchCampaign(campaignId);

            boolean isCampaignPresent = campaignManagementSearch.isFirstEntryByCampaignId(campaignId);

            if (isCampaignPresent){
                campaignManagementSearch.getListButtonsDelete().get(0).click();
                campaignManagementSearch.clickConfirmDelete();

                try {
                    adminPortletHomepage.getWarningOkButton().click();
                    System.out.println("CAMPAIGN_ID : " + campaignId + " has been deleted successfully!");
                } catch (Exception ignored) {
                    System.out.println("Warning Ok Button clicked.");
                    System.out.println("CAMPAIGN_ID : " + campaignId + " has been deleted successfully!");
                }
                ReusableMethods.waitForPageToLoad(10, webDriver);
            }



//            //
////            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));
//            //
//            String entryDisplay = campaignManagementSearch.getNumberOfEntriesParagraph().getText().trim();
//            //
//            if (!entryDisplay.equals(ConstantsGuiTexts.ZERO_OF_ZERO_FROM_ZERO_RECORDS) &&
//                    entryDisplay.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
//
//                // deleting created ORU campaign by ID
//                for (WebElement element : campaignManagementSearch.getTableEntries()) {
//
//                    WebElement firstCell = element.findElement(By.xpath("./td[2]"));
//
//                    if (firstCell.getText().trim().contains(campaignId)) {
//
//                        element.findElement(By.xpath(".//button[contains(@class, 'btn btn-danger btn-xs')]")).click();
//                        campaignManagementSearch.clickConfirmDelete();
//
//                        System.out.println("CAMPAIGN_ID : " + campaignId + " has been deleted successfully!");
//
//                        //
////                        Thread.sleep(10000);
//                        ReusableMethods.waitForPageToLoad(10, webDriver);
//
//                        DialogueOruDeletionConfirmation dialogueOruDeletionConfirmation =
//                                new DialogueOruDeletionConfirmation(webDriver);
//
//                        if (dialogueOruDeletionConfirmation.getHeaderWarning().isDisplayed()) {
//                            dialogueOruDeletionConfirmation.clickButtonConfirmation();
//                        }
//                    }
//
//                }
//
//            }

//        } catch (Throwable t) {
//            // intentionally blank
//        }

    }

    /**
     * @param adminPortletHomepage
     * @param campaignManagementSearch
     * @param campaignId
     * @param webDriver
     */


    public void searchAndOpenCampaign(
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            String campaignId,
            WebDriver webDriver) {

        CampaignManagementDisplayCampaign campaignManagementDisplayCampaign = new CampaignManagementDisplayCampaign(webDriver);

        openCampaignManagementSearch(adminPortletHomepage);

        // searching for element, if already existing
        campaignManagementSearch.searchCampaign(campaignId);
//        ReusableMethods.waitForClickablility(campaignManagementSearch.getCampaignSearchField(), 10, webDriver);
//        campaignManagementSearch.getCampaignSearchField().clear();
//        campaignManagementSearch.getCampaignSearchField().sendKeys(campaignId);
//        ReusableMethods.waitForVisibility(campaignManagementSearch.getCampaignTitle(), 3, webDriver);

        if (campaignManagementSearch.isFirstEntryByCampaignId(campaignId)){
            // open campaign overview
            campaignManagementSearch.clickFirstButtonEdit();
        }

        try {
            ReusableMethods.waitForClickablility(campaignManagementDisplayCampaign.getNotAuthorizedOkButton(), 2, webDriver);
            campaignManagementDisplayCampaign.getNotAuthorizedOkButton().click();
        } catch (Exception ignored) {
        }
    }

    /**
     * @param inboxPage
     * @param recallId
     * @param amcode
     * @param webDriver
     * @param wait
     * @throws Exception
     */
    private void selectReCallAndService42(InboxPage inboxPage, String recallId, String amcode,
                                          WebDriver webDriver, WebDriverWait wait) throws Exception {

        wait.until(ExpectedConditions.elementToBeClickable(inboxPage.getService42SearchField()));

        inboxPage.setService42SearchField(amcode);
        // service42 elements to be 1
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//tbody[@id='service42TBody']//i[@class='fa fa-user']"), 1));
        inboxPage.clickService42TableListItemCheckBox(amcode, webDriver);

        wait.until(ExpectedConditions.elementToBeClickable(inboxPage.getInboxRecallSearchField()));

        //
        inboxPage.setInboxRecallSearchField(recallId);
        // recall elements to be greater or equal 1
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//tbody[@id='recallTBody']//i[@class='fa fa-user']|//tbody[@id='recallTBody']//i[@class='fa fa-cogs']"), 0));
        inboxPage.clickRecallTableListItemCheckBox(recallId, webDriver);
        // finishing campaign creation
        wait.until(ExpectedConditions.elementToBeClickable(inboxPage.getCreateCampaignButton()));
        // create campaign
        inboxPage.clickCreateCampaignButton();

    }

    /**
     * @param translationOutput
     * @param compareString
     * @return
     */
    private boolean isEqual(String translationOutput, String compareString) {
        return translationOutput.toUpperCase().equals(compareString);
    }

    /**
     * @param adminPortletHomepage
     */
    public void openCampaignManagementSearch(AdminPortletHomepage adminPortletHomepage) {

        openMenuCampaignMgmt(adminPortletHomepage);
        ReusableMethods.waitForClickablility(adminPortletHomepage.getCmSearch(), 10, webDriver);
        adminPortletHomepage.clickCampaignManagementSearch();

    }

    /**
     * @param adminPortletHomepage
     */
    public void openInboxCampaign(AdminPortletHomepage adminPortletHomepage) {

        ReusableMethods.waitForClickablility(adminPortletHomepage.getCampaignManagement(), 5, webDriver);
        ReusableMethods.openCampaignManagement(adminPortletHomepage, webDriver);

//        adminPortletHomepage.clickCampaignManagementInbox();
        ReusableMethods.clickElement(adminPortletHomepage.getCampaignManagementInbox(), 5, webDriver);

    }

    /**
     * @param adminPortletHomepage
     */
    public void openMenuCampaignMgmt(AdminPortletHomepage adminPortletHomepage) {


        ReusableMethods.waitForClickablility(adminPortletHomepage.getCampaignManagement(), 10, webDriver);

        // checking open menu
        if (!adminPortletHomepage.isAvailableMenuEntryCampaignMgmtSearch() ||
                !adminPortletHomepage.getCmSearch().isDisplayed()) {
            adminPortletHomepage.clickCampaignManagement();
        }


    }

    /**
     * @param adminPortletHomepage
     */


    boolean inboxIsDisabled(AdminPortletHomepage adminPortletHomepage) {

        try {
            ReusableMethods.waitForClickablility(adminPortletHomepage.getCampaignManagementInbox(), 5, webDriver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public void openMenuCampaignMgmtInbox(AdminPortletHomepage adminPortletHomepage) {

        // checking open menu

        boolean inboxIsDisabled = inboxIsDisabled(adminPortletHomepage);

        if (!inboxIsDisabled) {
            adminPortletHomepage.clickCampaignManagement();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * @return
     * @throws InterruptedException
     */
    public boolean checkReCallInInboxExistingCampaign(
            AdminPortletHomepage adminPortletHomepage,
            InboxPage inboxPage,
            String unique_id) throws InterruptedException {

        openInboxCampaign(adminPortletHomepage);

        inboxPage.clickTabExistingCampaign();

        Thread.sleep(3000);

        List<WebElement> campaigns = inboxPage.getListExistingOruCampaigns();

        return new CommonFunctions().isListContainingText(unique_id, campaigns);

    }

    /**
     * @return
     * @throws InterruptedException
     */
    public boolean checkReCallInInboxArchivedCampaign(
            AdminPortletHomepage adminPortletHomepage,
            InboxPage inboxPage,
            String unique_id) throws InterruptedException {

        openInboxCampaign(adminPortletHomepage);

        inboxPage.clickTabArchivedCampaign();

        Thread.sleep(3000);

        List<WebElement> campaigns = inboxPage.getListArchivedOruCampaigns();

        return new CommonFunctions().isListContainingText(unique_id, campaigns);

    }

    /**
     * @param listVINs
     */
    public void areVinsInList(
            DialogueInspectVehicles dialogueInspectVehicles,
            String... listVINs) {

        for (String vin : listVINs) {

            boolean found = false;

            for (WebElement we : dialogueInspectVehicles.getListColumnElementsVINs()) {

                if (vin.equals(we.getText().trim())) {
                    found = true;
                    break;
                }

            }

            assertTrue(found, "VIN '" + vin + "' wasn't found in 'Inspect Vehicles' Dialogue.");
        }

    }

    /**
     * @param listVINs
     */
    public void areNotVinsInList(
            DialogueInspectVehicles dialogueInspectVehicles,
            String... listVINs) {

        for (String vin : listVINs) {

            boolean found = false;

            for (WebElement we : dialogueInspectVehicles.getListColumnElementsVINs()) {

                if (vin.equals(we.getText().trim())) {
                    found = true;
                    break;
                }

            }

            assertFalse(found, "VIN '" + vin + "' wasn't found in 'Inspect Vehicles' Dialogue.");
        }

    }

    /**
     * @param mapApproval
     * @param listVINs
     * @return
     */
    public byte checkApprovalStatesOfVINs(
            Map<String, String> mapApproval,
            DialogueInspectVehicles dialogueInspectVehicles,
            String... listVINs) {

        List<WebElement> vinApproval = dialogueInspectVehicles.getListRowElementsVINs();
        List<WebElement> listVin = dialogueInspectVehicles.getListColumnElementsVINs();
        List<WebElement> listVinState = dialogueInspectVehicles.getListColumnElementsVINsState();

        assertTrue(listVin.size() == vinApproval.size(), "Expected to find '" + listVin.size() + "' but found '" + vinApproval.size() + "'.");

        byte counterFindingsOK = 0;

        for (int j = 0; j < listVin.size(); j++) {

            String approvalVin = listVin.get(j).getText().trim();
            String approvalState = listVinState.get(j).getText().trim();

            for (int i = 0; i < listVINs.length; i++) {

                String vin = listVINs[i];

                if (approvalVin.equals(vin)) {

                    if (mapApproval.get(vin).equals(approvalState)) {
                        counterFindingsOK++;
                    }

                }

            }

        }

        return counterFindingsOK;
    }

    /**
     * @param criteriaButtonCount
     * @throws Exception
     */
    public void addCriteriaToOruCampaign(
            int criteriaButtonCount,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            DialogueAddNewCriterion dialogueAddNewCriterion) throws Exception {

        campaignManagementDisplayCampaign.pressCriteriaButton(criteriaButtonCount - 1);

        campaignManagementDisplayCampaign.clickButtonAddCriterionToOruCampaign();

        Thread.sleep(500);

        dialogueAddNewCriterion.clickCheckboxRootCheckbox();

        dialogueAddNewCriterion.clickButtonApply();

    }

}
