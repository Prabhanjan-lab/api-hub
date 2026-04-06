package testFramework.utilities;


import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.campaignmanagement.CampaignManagementDisplayCampaign;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.campaignmanagement.InboxPage;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignCreation;
import de.sulzer.pages.campaignmanagement.inbox.InboxPageDomainSelection;
import de.sulzer.pages.campaignmanagement.inbox.InboxPageTranslationProperties;
import de.sulzer.pages.campaignmanagement.inbox.popUpDialogues.DialogueInspectVehicles;
import de.sulzer.pages.oruoverviewpage.Batch_info;
import de.sulzer.pages.oruoverviewpage.ORUOverviewPage;
import de.sulzer.pages.sectorlog.LoggingDetail;
import de.sulzer.pages.sectorlog.LoggingOverview;
import de.sulzer.pages.testingmenu.recall.AddRecallSystemModal;
import de.sulzer.pages.testingmenu.recall.ManualRecallSystemPage;
import de.sulzer.pages.testingmenu.recall.RecallSystemModal;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.pages.util.constants.ConstantsBrands;
import de.sulzer.utils.guistandardfunctions.*;
import de.sulzer.utils.screenshots.ScreenshotsSelenium;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import testCases.util.ConstantsDownloadFiles;
import testFramework.constants.ValuesGlobal;
import testFramework.utilities.zipfiles.ZipFileOperations;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



public interface ReusableMethods {

    FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
    Faker faker = new Faker();

    //    String RECALL_ID = fakeValuesService.bothify("??##").toUpperCase(Locale.ROOT);
//    String recallUniqueID = fakeValuesService.bothify("TA##").toUpperCase(Locale.ROOT);
    String recallUniqueID = faker.regexify("TA[A-Z0-9]{2}");
    String recallUniqueIDOneTime = faker.regexify("T[B-Z0-9][A-Z0-9]{2}");
    String RECALL_ID_TITLE = "Testautomatisierung Sulzer  ";
    String RECALL_ID_01 = "KT68";
    String RECALL_ID_NOT_DEL = "TA69";
    String RECALL_ID_02_TITLE = "DN50 Campaign";
    String CAMPAIGN_ID = RECALL_ID_01;
    String AM_Code = "118100";  // Older than 100 days
    String AM_Code_Old = "TAAA";  // Older than 100 days
    String CAMPAIGN_ID_NOT_DEL = "TA04";
    String AM_ID = "060000";
    String AM_DESCRIPTION = "Testautomatisierung Sulzer";
    String AM_TNR_NUMBER = de.sulzer.utils.CreateRandomChars.generateRandomChars("0123456789", 11);
//    String FLASH_MEDIUM = ValuesGlobal.cGW2;
//    String FLASH_MEDIUM = ValuesGlobal.LOCAL_FM_CGW2;

    String FLASH_MEDIUM = ValuesGlobal.FLASH_MEDIUM;

    int DOMAIN_DATA_NAME_MIB2 = 0;
    int DOMAIN_DATA_NAME_cGW2 = 1;
    int DOMAIN_DATA_NAME_OCU2 = 2;
    int DOMAIN_DATA_NAME_OCU3 = 3;
    int AMOUNT_CRITERIA = 1;
    boolean ENCRYPTION = true;
    String PATH_TO_FILE_TO_BE_UPLOADED_cGW2 = ValuesGlobal.cGW2;
    String PATH_TO_FILE_TO_BE_UPLOADED_cGW2_LOCAL = ValuesGlobal.LOCAL_FM_CGW2;
    String PATH_TO_FILE_TO_BE_UPLOADED_MIB2 = ValuesGlobal.MIB2;
    int configuration = 1; // Audi, Porsche etc.
    String AM_COMMENT = ActionsOnupReCall.COMMENT;
    String VIN1 = "BAUSLZ4N819805134";
    String VIN2 = "BAUZ1212429993210";
    String VIN_FOR_BATCH_CREATE = "BAUSLZ8W218102922";
    String VIN_FOR_VEHICLE_CONFIGURATION = "BAUSLZ4N818102999";
    String PR_NUMBER_FOR_BATCH_CREATE = "+K8C";
    String VIN_APR_VW_PKW_BRAND = "WVWEEE5G119011101";
    String VIN_DEMO_VW_PKW_BRAND = "BVWSLZ3G218030901";
    String VIN_EXCEPTIONLIST = "BAUSLZ4N818102933";
    String VIN_APR_FOR_CARPORT = "WAUEE9GEN18121705";
    String VIN_DEMO_FOR_CARPORT = "WAUZZZF82JN001912";
    String VIN_APR = "WAUEE9GEN18121705";
    String disabledCountry = "";
    String RECALL_DISABLED_COUNTRIES = "AGO Angola";
    String RC_DISABLED_COUNTRY = "ATA";
    String TRANSLATION_INPUT = ActionsOnupOruCampaign.TRANSLATION_INPUT;
    String TRANSLATION_OUTPUT = ActionsOnupOruCampaign.TRANSLATION_OUTPUT;

    String BATCH_A = "AAA";
    String BATCH_B = "BBB";
    String IMPORTER = "321";

    static VOFlashAssignment vOFlashAssignment() {
        return new VOFlashAssignment();
    }


//    String UNIQUE_ID = fakeValuesService.bothify("??##").toUpperCase(Locale.ROOT);


    /**
     * =========== Login with Brand Audi ==============
     */
    static void loginWithBrandAudi(WebDriver driver) {
        ConvienentActionsInONUP.userLoginWithBrand(driver, "Audi");
    }


    /**
     * ============= CREATE RECALL CAMPAIGN =================
     */
    static String create_Recall(
            ActionsOnupReCall actionsOnupReCall,
            AdminPortletHomepage adminPortletHomepage,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            WebDriver driver,
            String VIN,
            String recallUniqueID
    ) {

        actionsOnupReCall.createReCall(
                adminPortletHomepage,
                manualRecallSystemPage,
                addRecallSystemModal,
                recallUniqueID,
                RECALL_ID_TITLE + recallUniqueID,
                "",
                2,
                VIN,
                driver);

        return recallUniqueID;
    }


    /**
     * ============= CREATE RECALL CAMPAIGN WITH ONE VIN =================
     */

    static void createRecallWithOneVin(ActionsOnupReCall actionsOnupReCall,
                                       AdminPortletHomepage adminPortletHomepage,
                                       ManualRecallSystemPage manualRecallSystemPage,
                                       AddRecallSystemModal addRecallSystemModal,
                                       String recallId,
                                       String recallTitle,
                                       String disabledCountry,
                                       String recallVin,
                                       WebDriver webDriver) {
        actionsOnupReCall.createReCall(
                adminPortletHomepage,
                manualRecallSystemPage,
                addRecallSystemModal,
                recallId,
                recallTitle,
                disabledCountry,  // diables country
                recallVin,
                webDriver);
    }


    /**
     * =========== Create Recall with many Vin =====================
     */
    static void createRecallWithManyVin(WebDriver driver, String RECALL_ID, int vinCount) throws InterruptedException {
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        ManualRecallSystemPage recallSystemPage = new ManualRecallSystemPage(driver);
        AddRecallSystemModal addRecallSystemModal = new AddRecallSystemModal(driver);
        RecallSystemModal recallSystemModal = new RecallSystemModal(driver);

        openTestingMenu(adminPortletHomepage, driver);
        adminPortletHomepage.clickManualRecallSystem();
        recallSystemPage.clickAddRecallButton();
        System.out.println("RECALL_ID : " + RECALL_ID);
        addRecallSystemModal.setRecallActionIdInputFieldText(RECALL_ID);
        addRecallSystemModal.setRecallActionTitleInputField(RECALL_ID + " - " + RECALL_ID_TITLE);

        recallSystemModal.setCriterionIdControlInputField("01");
        recallSystemModal.setCriterionTitleInputField("01");

        int j = 0;
        for (int i = 1; i < vinCount; i++) {
            String VIN = fakeValuesService.bothify("???##############").toUpperCase(Locale.ROOT);
            recallSystemModal.setVehiclesInputField(VIN + ",");
            System.out.println(i + "-VIN : " + VIN);
            j = i;
            Thread.sleep(100);
        }

        String vinEnde = fakeValuesService.bothify("???##############").toUpperCase(Locale.ROOT);
        System.out.println((j + 1) + "-VIN : " + vinEnde);
        recallSystemModal.setVehiclesInputField(vinEnde);

        hoverJS(addRecallSystemModal.getCreateRecallCampaignButton(), driver);

        assertTrue(addRecallSystemModal.getCreateRecallCampaignButton().isEnabled(), "CreateRecallCampaignButton has problem");
        addRecallSystemModal.clickCreateRecallCampaignButton();

    }


    /**
     * =========== Create a Oru Campaign out of an imported Recall with many VINs and an S42 ==============
     */
    static void createOruCampaignWithImportedRecallAndVin(
            WebDriver driver,
            String RECALL_ID,
            String AM_ID,
            String TRANSLATION_INPUT1,
            String TRANSLATION_OUTPUT1) throws Exception {

        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        ManualRecallSystemPage recallSystemPage = new ManualRecallSystemPage(driver);
        AddRecallSystemModal addRecallSystemModal = new AddRecallSystemModal(driver);
        RecallSystemModal recallSystemModal = new RecallSystemModal(driver);
        InboxPage inboxPage = new InboxPage(driver);
        InboxPageDomainSelection domainSelection = new InboxPageDomainSelection(driver);
        InboxPageTranslationProperties translationProperties = new InboxPageTranslationProperties(driver);
        ORUOverviewPage oruOverviewPage = new ORUOverviewPage(driver);


        adminPortletHomepage.clickCampaignManagement();
        adminPortletHomepage.clickCampaignManagementInbox();

        waitForClickablility(inboxPage.getInboxRecallSearchField(), 20, driver);
        inboxPage.setInboxRecallSearchField(RECALL_ID);
        new ScreenshotsSelenium().takeScreenshot("4.png", ".", driver); // DEL

        // recall elements to be greater or equal 1
        waitForNumberOfElementsToBeMoreThanOne(By.xpath("//tbody[@id='recallTBody']//i[@class='fa fa-user']|//tbody[@id='recallTBody']//i[@class='fa fa-cogs']"), 0, driver);
        inboxPage.clickRecallTableListItemCheckBox(RECALL_ID, driver);
        new ScreenshotsSelenium().takeScreenshot("5.png", ".", driver); // DEL

        waitForClickablility(inboxPage.getService42SearchField(), 20, driver);
        inboxPage.setService42SearchField(AM_ID);

        // service42 elements to be 1
        waitForNumberOfElementsToBeMoreThanOne(By.xpath("//tbody[@id='service42TBody']//i[@class='fa fa-user']"), 1, driver);
        inboxPage.clickService42TableListItemCheckBox(AM_ID, driver);
        new ScreenshotsSelenium().takeScreenshot("6.png", ".", driver); // DEL

        // finishing campaign creation
        waitForClickablility(inboxPage.getCreateCampaignButton(), 20, driver);

        // create campaign
        new ScreenshotsSelenium().takeScreenshot("7.png", ".", driver); // DEL
        inboxPage.clickCreateCampaignButton();

        waitForClickablility(domainSelection.getTranslationPropertiesButton(), 20, driver);
        new ScreenshotsSelenium().takeScreenshot("8.png", ".", driver); // DEL
        domainSelection.clickTranslationPropertiesButton();

        waitForClickablility(translationProperties.getButtonNext(), 20, driver);
//        new ScreenshotsSelenium().takeScreenshot("9.png", ".", driver); // DEL
        translationProperties.clickButtonNext();

        waitForClickablility(translationProperties.getInputTranslationField(), 20, driver);
        translationProperties.setInputTranslationField(TRANSLATION_INPUT1);
        translationProperties.setOutputTranslationField(TRANSLATION_OUTPUT1);
        new ScreenshotsSelenium().takeScreenshot("10.png", ".", driver); // DEL
        translationProperties.clickButtonCreateCampaign();

        waitForVisibility(oruOverviewPage.getHundertProzent(), 180, driver);
        new ScreenshotsSelenium().takeScreenshot("11.png", ".", driver); // DEL

    }


    /**
     * =========== Create a Recall Unique ID ==============
     */
    static String createRecallUniqueIDWithSixStep(String recallID, WebDriver driver) throws Exception {

        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        ActionsOnupOruCampaign actionsOnupOruCampaign = new ActionsOnupOruCampaign(driver);
        InboxPage inboxPage = new InboxPage(driver);
        CampaignManagementSearch campaignManagementSearch = new CampaignManagementSearch(driver);
        boolean create = true;
        String recall_ID = recallID;

        while (create) {

            String availableBrand = adminPortletHomepage.getLinkBrand().getText();
            if (!availableBrand.contains(ConstantsBrands.AUDI)) {
                adminPortletHomepage.changeBrand(ConstantsBrands.AUDI);
            }

            int presentTest = 1;
            switch (presentTest) {
                case 1:
                    System.out.println("New Recall ID : " + recall_ID);
                    openCampaignManagement(adminPortletHomepage, driver);
                    adminPortletHomepage.clickCampaignManagementInbox();
                    waitForClickablility(inboxPage.getInboxRecallSearchField(), 25, driver);
                    inboxPage.setInboxRecallSearchField(recall_ID);
                    boolean isRecallPresent = inboxPage.isRecallElementPresent(recall_ID, driver);

                    if (isRecallPresent) {
                        System.out.println("Recall ID : " + recall_ID + " is present in Inbox Recall Campaign.");
                        recall_ID = fakeRecallID();
                        break;
                    }

                    System.out.println("Recall ID - " + recall_ID + " is not found in Inbox Recall Campaign.");

                case 2:
                    inboxPage.clickTabExistingCampaign();
                    inboxPage.searchRecallExistingCampaign(recall_ID);
                    isRecallPresent = inboxPage.isRecallElementPresent(recall_ID, driver);

                    if (isRecallPresent) {
                        System.out.println("Recall ID - " + recall_ID + " is present in Inbox Existing Recall Campaign.");
                        inboxPage.getTabCreateOruCampaign().click();
                        recall_ID = fakeRecallID();
                        break;
                    }

                    System.out.println("Recall ID - " + recall_ID + " is not found in Inbox Existing Recall Campaign.");

                case 3:
                    actionsOnupOruCampaign.openCampaignManagementSearch(adminPortletHomepage);
                    waitForClickablility(campaignManagementSearch.getCampaignSearchField(), 10, driver);
                    campaignManagementSearch.searchCampaign(recall_ID);
                    isRecallPresent = campaignManagementSearch.isFirstEntryByCampaignId(recall_ID);

                    if (isRecallPresent) {
                        System.out.println("Campaign ID - " + recall_ID + " is present in Search.");
                        recall_ID = fakeRecallID();
                        break;
                    }

                    System.out.println("Campaign ID - " + recall_ID + " is not found in Search.");

                case 4:
                    adminPortletHomepage.changeBrand(ConstantsBrands.VW_PKW);
                    waitForClickablility(adminPortletHomepage.getCampaignManagement(), 10, driver);
                    actionsOnupOruCampaign.openInboxCampaign(adminPortletHomepage);
                    waitForClickablility(inboxPage.getInboxRecallSearchField(), 25, driver);
                    inboxPage.setInboxRecallSearchField(recall_ID);
                    isRecallPresent = inboxPage.isRecallElementPresent(recall_ID, driver);

                    if (isRecallPresent) {
                        System.out.println("Recall ID - " + recall_ID + " is present in VW_PKW Inbox Recall Campaign.");
                        recall_ID = fakeRecallID();
                        break;
                    }

                    System.out.println("Recall ID - " + recall_ID + " is not found in VW_PKW Inbox Recall Campaign.");

                case 5:
                    inboxPage.clickTabExistingCampaign();
                    inboxPage.searchRecallExistingCampaign(recall_ID);
                    isRecallPresent = inboxPage.isRecallElementPresent(recall_ID, driver);
                    if (isRecallPresent) {
                        System.out.println("Recall ID - " + recall_ID + " is present in VW_PKW Inbox Existing Recall Campaign.");
                        inboxPage.getTabCreateOruCampaign().click();
                        recall_ID = fakeRecallID();
                        break;
                    }

                    System.out.println("Recall ID - " + recall_ID + " is not found in VW_PKW Inbox Existing Recall Campaign.");

                case 6:
                    actionsOnupOruCampaign.openCampaignManagementSearch(adminPortletHomepage);
                    waitForClickablility(campaignManagementSearch.getCampaignSearchField(), 20, driver);
                    campaignManagementSearch.searchCampaign(recall_ID);
                    isRecallPresent = campaignManagementSearch.isFirstEntryByCampaignId(recallID);
                    if (isRecallPresent) {
                        System.out.println("Campaign ID - " + recall_ID + " is present in VW_PKW Search.");
                        recall_ID = fakeRecallID();
                    } else {
                        create = false;
                    }

                    System.out.println("Campaign ID - " + recall_ID + " is not found in VW_PKW Search.");

                    break;
            }
        }

        adminPortletHomepage.changeBrand(ConstantsBrands.AUDI);
        waitForClickablility(adminPortletHomepage.getCampaignManagement(), 10, driver);

        return recall_ID;

    }


    static String createRecallUniqueID(String recallID, WebDriver driver) {

        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        ActionsOnupReCall actionsOnupReCall = new ActionsOnupReCall(driver);
        ManualRecallSystemPage manualRecallSystemPage = new ManualRecallSystemPage(driver);
        AddRecallSystemModal addRecallSystemModal = new AddRecallSystemModal(driver);
        ManualService42SystemPage manualService42SystemPage = new ManualService42SystemPage(driver);
        ActionsOnupService42 actionsOnupService42 = new ActionsOnupService42(driver);
        WebDriverWait wait = new WebDriverWait(driver, 5);

        System.out.println("New Recall ID : " + recallID);
        System.out.println("Checking whether the Recall ID " + recallID + " is unique...");
        actionsOnupReCall.openReCallMenu(adminPortletHomepage);

        clickElement(manualRecallSystemPage.getAddRecallButton(), 5, driver);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        addRecallSystemModal.setRecallActionIdInputFieldText(recallID);
        addRecallSystemModal.setRecallActionTitleInputField(recallID);
        addRecallSystemModal.setCriterionTitleInputField("01");
        addRecallSystemModal.setCriterionIdControlInputField("01");
        addRecallSystemModal.setVehiclesInputField(fakeVIN());
        addRecallSystemModal.clickCreateRecallCampaignButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        while (isWebElementPresent(addRecallSystemModal.getErrorMessage(), driver)) {

            recallID = fakeRecallID();

            System.out.println("New Recall ID : " + recallID);

            ReusableMethods.deleteField(addRecallSystemModal.getRecallActionIdInputField());
            addRecallSystemModal.setRecallActionIdInputFieldText(recallID);
            ReusableMethods.deleteField(addRecallSystemModal.getRecallActionTitleInputField());
            addRecallSystemModal.setRecallActionTitleInputField(recallID);
            addRecallSystemModal.clickCreateRecallCampaignButton();
        }

        manualRecallSystemPage.getSearchRecallCampaignInputfield().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        manualRecallSystemPage.getSearchRecallCampaignInputfield().sendKeys(recallID);

        try {
            ReusableMethods.waitForVisibility(manualRecallSystemPage.recallWebElementWithText(driver,recallID), 20, driver);
            System.out.println("Recall " + recallID + " found.");
            ReusableMethods.clickElement(manualRecallSystemPage.getDeleteRecallButton(), 3, driver);

            // click confirmation button
            manualRecallSystemPage.clickConfirmDeletion();
            System.out.println("Recall " + recallID + " has been deleted successfully!");
        } catch (Exception e) {
            System.out.println("This Recall " + recallID + " does not exist.");
        }

        // Delete FlashFile And Service42
            actionsOnupService42.deleteFlashFileAndService42(
                    wait,
                    adminPortletHomepage,
                    manualService42SystemPage,
                    recallID
            );

        System.out.println("Recall ID: " + recallID + " and AM Code: " + recallID +  " Unique.");

        return recallID;
    }

    /**
     * =========== Create a AM Unique Code ==============
     */

    static String createAMUniqueCode(WebDriver driver, String AM_Code) {

        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        ManualService42SystemPage manualService42SystemPage = new ManualService42SystemPage(driver);

        waitForClickablility(adminPortletHomepage.getTestingMenu(), 10, driver);
        String AM = AM_Code;
        manualService42SystemPage.searchService42TestingMenu(AM, driver);

        while (manualService42SystemPage.isService42ElementPresent(AM, driver)){
            AM = fakeAM_ID();
            manualService42SystemPage.searchService42Simple(AM);
        }

        return AM;
    }



    static String createAMUniqueCode(WebDriver driver) {

        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        ManualService42SystemPage manualService42SystemPage = new ManualService42SystemPage(driver);

        waitForClickablility(adminPortletHomepage.getTestingMenu(), 10, driver);
        String AM = fakeAM_ID();
        manualService42SystemPage.searchService42TestingMenu(AM, driver);

        while (manualService42SystemPage.isService42ElementPresent(AM, driver)){
            AM = fakeAM_ID();
            manualService42SystemPage.searchService42Simple(AM);
        }

        return AM;
    }


    /**
     * ============= Fake VIN =======================
     */

    static String fakeVIN() {
        return fakeValuesService.bothify("???##############").toUpperCase(Locale.ROOT);
    }


    /**
     * ============= Fake RECALL ID =======================
     */

    static String fakeRecallID() {
//        return fakeValuesService.bothify("TA##").toUpperCase(Locale.ROOT);
        return faker.regexify("TA[A-Z0-9]{2}");
    }

    /**
     * ============= Fake AM ID =======================
     */

    static String fakeAM_ID() {
        return fakeValuesService.bothify("TA##").toUpperCase(Locale.ROOT);
    }


    /**
     * ======Implicit Wait==============
     */
    static void implicitWait(WebDriver driver, int second) {
        driver.manage().timeouts().implicitlyWait(second, TimeUnit.SECONDS);
    }


    /**
     * ======Explicit Wait==============
     */
    static WebElement waitForVisibility(WebElement element, int timeout, WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    static WebElement waitIgnoring(WebElement element, int timeout, WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(getByObject(element)));
    }

    static WebElement waitIgnoring(By locator, int timeout, WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        return wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    static void waitForInvisibilityOf(WebElement element, int timeout, WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    static void waitForClickablility(WebElement element, int timeout, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    static WebElement waitForElement(WebDriver driver, WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement webElement;
        webElement = wait.until(ExpectedConditions.presenceOfElementLocated(getByObject(element)));
        return webElement;
    }

    static WebElement waitForElement(WebDriver driver, WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement webElement;
        webElement = wait.until(ExpectedConditions.presenceOfElementLocated(getByObject(element)));
        return webElement;
    }

    static WebElement waitForElement(WebDriver driver, WebElement element, int timeout, Function<By, ExpectedCondition<WebElement>> condition) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement webElement;
        webElement = wait.until(condition.apply(getByObject(element)));
        return webElement;
    }

    static By getByObject(WebElement element) {
        String[] arr = element.toString().split(" -> ")[1].split(": ");
        String locator = arr[0];
        String term = arr[1].substring(0, arr[1].length() - 1);
        switch (locator) {
            case "xpath":
                return By.xpath(term);
            case "id":
                return By.id(term);
            case "class name":
                return By.className(term);
            case "tag name":
                return By.tagName(term);
            case "name":
                return By.name(term);
            case "link text":
                return By.linkText(term);
            case "partial link text":
                return By.partialLinkText(term);
            default:
                throw new IllegalArgumentException("Locator type '" + locator + "' not supported");
        }
    }

    static WebElement waitForElementLast(WebDriver driver, WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout, 500); // Check every half a second
        WebElement webElement;
        webElement = wait.until(ExpectedConditions.visibilityOf(element));
        return webElement;
    }

    static void waitForTextToBePresentInElement(WebElement element, String text, int timeout, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }

    static void waitForNumberOfElementsToBeMoreThanOne(By locator, int timeout, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator, 1));
    }

    static void waitForNumberOfElementsToBeOne(By locator, int timeout, WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.until(ExpectedConditions.numberOfElementsToBe(locator, 1));
        wait.until(ExpectedConditions.numberOfElementsToBe(locator, 1));
    }


    static void waitForPageToLoad(long timeOutInSeconds, WebDriver driver) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeOutInSeconds + " seconds");
        }
    }

    /**
     * ============= CLICK ELEMENT =================
     */
//    static void clickElement(WebElement element, int timeout, WebDriver driver) {
//
////        waitForClickablility(element, 20, driver);
////
////        try {
////            element.click();
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }
//
////        boolean elementPresent = waitForVisibility(element, 5, driver).isDisplayed();
//        boolean elementPresent = waitForElement(driver, element).isDisplayed();
//        waitForClickablility(element, timeout, driver);
//        if (elementPresent) {
//            element.click();
//        } else {
//            System.out.println("Oops! Couldn't locate element!");
//        }
//
//
//    }


    static void clickElement(WebElement element, int timeout, WebDriver driver) {
        waitForClickablility(element, timeout, driver);

        try {
            element.click();
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    static void clickElement(WebElement element, WebDriver driver) {

        boolean elementPresent = waitForVisibility(element, 5, driver).isDisplayed();
//        boolean elementPresent = waitForElement(driver, element).isDisplayed();
        waitForClickablility(element, 20, driver);
//        if (elementPresent) {
//            element.click();
//        } else {
//            System.out.println("Oops! Couldn't locate element!");
//        }

        try {
            element.click();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static void hoverElementAndClick(WebElement element, int timeout, WebDriver driver) {

        boolean elementPresent = waitForVisibility(element, timeout, driver).isEnabled();
//        boolean elementPresent = waitForElement(driver, element, timeout).isDisplayed();
        waitForClickablility(element, 20, driver);
        hoverJS(element, driver);

        if (elementPresent) {
            element.click();
        } else {
            System.out.println("Oops! Couldn't locate element!");
        }
    }

    static boolean isElementVisible(WebDriver driver, WebElement element, int timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.invisibilityOf(element));
            return false;
        } catch (TimeoutException e) {
            return true;
        }
    }

    /**
     * =========== Take Screen shot ==============
     */

    static void screenShot(String name, WebDriver driver) {
        new ScreenshotsSelenium().takeScreenshot(name + ".png", ".", driver);
    }

    static void screenShot(int num, WebDriver driver) {
        String name = num + " | " + LocalDateTime.now() + " | " + ".png";
        new ScreenshotsSelenium().takeScreenshot(name, ".", driver);
    }

    static void screenShot(WebDriver driver) {
        String name = LocalDateTime.now() + " | " + ".png";
        new ScreenshotsSelenium().takeScreenshot(name, ".", driver);
    }

    // TODO:takeScreenshotToJira
    static void takeScreenshotToJira(String filename, WebDriver webDriver) {
        Shutterbug.shootPage(webDriver, ScrollStrategy.WHOLE_PAGE, 100, true).
                withName(filename).
                save(System.getProperty("user.dir"));
    }

    /**
     * =========== Check Time ==============
     */
    static void checkTime(int num) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println(num + " - " + time);
    }




    /**
     * ==========Return a list of string given a list of Web Element====
     */
    static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }


    /**
     * ========Hover Over=========
     */
    static void hoverAct(WebElement element, WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();
        clickJS(element, driver);
    }

    static void pageDownAct(WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).build().perform();
    }

    static void pageUpAct(WebDriver driver) {
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_UP).build().perform();
    }

    static void hoverJS(WebElement element, WebDriver driver) {
        JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    static void clickJS(WebElement element, WebDriver driver) {
        JavascriptExecutor jsExecutor = ((JavascriptExecutor) driver);
        jsExecutor.executeScript("arguments[0].click();", element);
    }

    static void sendKeysJS(WebElement element, String text, WebDriver driver) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].value='" + text + "';", element);
    }


    /**
     * =========== IS SORTED =============
     */

    static boolean isSortedWebElement(WebElement header, List<WebElement> columnList) throws InterruptedException {

        header.click();
        Thread.sleep(500);

        boolean sorted = false;
        for (int i = 1; i < columnList.size(); i++) {
            WebElement first = columnList.get(i - 1);
            WebElement next = columnList.get(i);

            if (next.getText().equals(" ")) {
                break;
            }

            if (first.getText().compareToIgnoreCase(next.getText()) <= 0) {
                sorted = true;
            } else {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

    static boolean isSortedString(List<String> columnList) {
        boolean sorted = false;
        for (int i = 1; i < columnList.size(); i++) {
            String first = columnList.get(i - 1);
            String next = columnList.get(i);

            if (next.equals(" ")) {
                break;
            }

            if (first.compareToIgnoreCase(next) <= 0) {
                sorted = true;
            } else {
                sorted = false;
                break;
            }
        }
        return sorted;
    }


    /**
     * ============== Log out page ===============
     */

    static void logOutPage(WebDriver driver) {
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        adminPortletHomepage.clickLinkLogout();
    }


    /**
     * ============== Select Dropdown =================
     */

    static void selectDropDown(String selectByVisibleText, WebElement element) {

        Select select = new Select(element);
        select.selectByVisibleText(selectByVisibleText);

    }


    /**
     * ============ Full path to the download location ==========
     */

    static File downloadFilePath(String fileName) {
        String downloadDirectory = ConstantsDownloadFiles.DOWNLOAD_DIRECTORY;
        File downloadFile = new File(downloadDirectory + "/" + fileName);
        return downloadFile;
    }


    /**
     * ========= Find and delete the file and add the names of those deleted to the list ===========
     */

    static List<String> getFoundDownloadFileAndVerify(String fileName) throws Exception {

        String downloadDirectory = ConstantsDownloadFiles.DOWNLOAD_DIRECTORY;
        File downloadFile = new File(downloadDirectory + "/" + fileName);

        ZipFileOperations zipFileOperations = new ZipFileOperations();
        List<String> statusFileLines = zipFileOperations.getFoundValues(downloadFile);

        assertTrue(
                statusFileLines.size() > 0,
                "Downloaded file " + downloadFile + " contains no lines of text.");

        return statusFileLines;
    }


    /**
     * ============= Checking whether the Web Element is clickable or not ==============
     */

    static boolean isWebElementClickable(WebElement element, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 6);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    static boolean isWebElementPresent(WebElement element, WebDriver driver) {
//        try {
//            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
//            return element.isDisplayed();
//        } catch (NoSuchElementException | StaleElementReferenceException e) {
//            return false;
//        } finally {
//            driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_TIME, TimeUnit.SECONDS);
//        }
//    }

    static boolean isWebElementPresent(WebElement element, WebDriver driver) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }



    /**
     * ============= CREATE UNIQUE RECALL_ID BY DELETING A RECALL =================
     */
    static void createUniqueRecall_IDByDeleting(
            WebDriver driver,
            AdminPortletHomepage adminPortletHomepage,
            ActionsOnupReCall actionsOnupReCall,
            ManualRecallSystemPage manualRecallSystemPage,
            ActionsOnupOruCampaign actionsOnupOruCampaign,
            CampaignManagementSearch campaignManagementSearch,
            WebDriverWait wait,
            String CAMPAIGN_ID,
            String RECALL_ID
    ) {
        deleteCampaignAndRecall(actionsOnupOruCampaign,
                actionsOnupReCall,
                driver,
                wait,
                adminPortletHomepage,
                campaignManagementSearch,
                manualRecallSystemPage,
                CAMPAIGN_ID,
                RECALL_ID);
        System.out.println("RECALL_ID : " + CAMPAIGN_ID);
    }


    /**
     * ============= CREATE ORU CAMPAIGN WITH FIX ID AND FIX AM =================
     */
    static void createOruCampaignWithFixIDAndFixAM(
            WebDriver driver,
            AdminPortletHomepage adminPortletHomepage,
            ActionsOnupReCall actionsOnupReCall,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            ActionsOnupOruCampaign actionsOnupOruCampaign,
            CampaignManagementSearch campaignManagementSearch,
            Batch_info batchInfo,
            String recallId,
            String recallTitle,
            String disabledCountry,
            int amountCriteria,
            String recallVin
    ) throws Exception {

        System.out.println("Recall_ID : " + recallId);

        actionsOnupReCall.createReCall(
                adminPortletHomepage,
                manualRecallSystemPage,
                addRecallSystemModal,
                recallId,
                recallTitle,
                disabledCountry,
                amountCriteria,
                recallVin,
                driver
        );

        waitForClickablility(adminPortletHomepage.getCampaignManagement(), 15, driver);

        actionsOnupOruCampaign.openInboxCampaign(adminPortletHomepage);

        actionsOnupOruCampaign.createORUCampaignWith_cGW2_FlashMedium(
                recallId,
                AM_Code,
                driver,
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                new VOFlashAssignment(1));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        actionsOnupOruCampaign.searchAndOpenCampaign(
                adminPortletHomepage,
                campaignManagementSearch,
                recallId,
                driver);

        batchInfo.waitForOruProcessing();
    }


    static void createOruCampaign(
            WebDriver driver,
            String recallId,
            String recallVin,
            String AM_Code,
            String PATH_TO_FILE_TO_BE_UPLOADED,
            String disabledCountry,
            int configuration, // Audi, Porsche etc.
            int amountCriteria,
            int domainDataName, // e.g.: MIB2+, cGW, OCU2 etc.
            String translationInput,
            String translationOutput,
            boolean encryption,
            VOFlashAssignment voFlashAssignment
    ) throws Exception {

        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        ActionsOnupReCall actionsOnupReCall = new ActionsOnupReCall(driver);
        ManualRecallSystemPage manualRecallSystemPage = new ManualRecallSystemPage(driver);
        AddRecallSystemModal addRecallSystemModal = new AddRecallSystemModal(driver);
        ActionsOnupOruCampaign actionsOnupOruCampaign = new ActionsOnupOruCampaign(driver);
        CampaignManagementSearch campaignManagementSearch = new CampaignManagementSearch(driver);
        Batch_info batchInfo = new Batch_info(driver);
        ActionsOnupService42 actionsOnupService42 = new ActionsOnupService42(driver);
        ManualService42SystemPage manualService42SystemPage = new ManualService42SystemPage(driver);
        AddService42SystemModal addService42SystemModal = new AddService42SystemModal(driver);


        System.out.println("Recall_ID : " + recallId);

        actionsOnupReCall.createReCall(
                adminPortletHomepage,
                manualRecallSystemPage,
                addRecallSystemModal,
                recallId,
                recallId,
                disabledCountry,
                amountCriteria,
                recallVin,
                driver
        );

        actionsOnupService42.createService42WaitUploadCompleted(
                adminPortletHomepage,
                manualService42SystemPage,
                addService42SystemModal,
                AM_Code,
                PATH_TO_FILE_TO_BE_UPLOADED,
                driver);

        waitForClickablility(adminPortletHomepage.getCampaignManagement(), 15, driver);

        actionsOnupOruCampaign.openInboxCampaign(adminPortletHomepage);

        actionsOnupOruCampaign.createORUCampaign(
                recallId,
                AM_Code,
                domainDataName,
                configuration,
                driver,
                translationInput,
                translationOutput,
                encryption,
                voFlashAssignment
        );

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        actionsOnupOruCampaign.searchAndOpenCampaign(
//                adminPortletHomepage,
//                campaignManagementSearch,
//                recallId,
//                driver);

        batchInfo.waitForOruProcessing();
    }

    /**
     * ============= DELETE CAMPAIGN - RECALL - SERVICE42 =================
     */
    static void deleteCampaignAndRecall(
            ActionsOnupOruCampaign actionsOnupOruCampaign,
            ActionsOnupReCall actionsOnupReCall,
            WebDriver driver,
            WebDriverWait wait,
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            ManualRecallSystemPage manualRecallSystemPage,
            String CAMPAIGN_ID,
            String RECALL_ID
    ) {

        actionsOnupOruCampaign.deleteOruCampaign(
                driver,
                wait,
                adminPortletHomepage,
                campaignManagementSearch,
                CAMPAIGN_ID);


        actionsOnupReCall.deleteReCall(
                wait,
                adminPortletHomepage,
                manualRecallSystemPage,
                RECALL_ID);

    }


    static void deleteCampaignRecallAndService42(WebDriver driver, String CAMPAIGN_ID, String RECALL_ID, String AM_ID){

        ActionsOnupOruCampaign actionsOnupOruCampaign = new ActionsOnupOruCampaign(driver);
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        CampaignManagementSearch campaignManagementSearch = new CampaignManagementSearch(driver);
        ActionsOnupService42 actionsOnupService42 = new ActionsOnupService42(driver);
        ManualService42SystemPage manualService42SystemPage = new ManualService42SystemPage(driver);
        ManualRecallSystemPage manualRecallSystemPage = new ManualRecallSystemPage(driver);
        ActionsOnupReCall actionsOnupReCall = new ActionsOnupReCall(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);



        actionsOnupOruCampaign.deleteOruCampaign(
                driver,
                wait,
                adminPortletHomepage,
                campaignManagementSearch,
                CAMPAIGN_ID);

        try {
            adminPortletHomepage.getWarningOkButton().click();
            ReusableMethods.clickElement(adminPortletHomepage.getWarningOkButton(), 20, driver);
        } catch (Exception ignored) {
        }

        actionsOnupService42.deleteFlashFileAndService42(
                wait,
                adminPortletHomepage,
                manualService42SystemPage,
                AM_ID);

        actionsOnupReCall.deleteReCall(
                wait,
                adminPortletHomepage,
                manualRecallSystemPage,
                RECALL_ID);

    }


    /**
     * ============= CHECKING UNAVAILABLE MENU ENTRIES =================
     */
    static void checkingUnavailableMenuEntries(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        // campaign management
        openCampaignManagement(adminPortletHomepage, driver);
        assertTrue(adminPortletHomepage.getCampaignManagementInbox().isEnabled());
        assertTrue(adminPortletHomepage.getCmSearch().isEnabled());
        assertTrue(adminPortletHomepage.getCmRolloutStatus().isEnabled());
        assertTrue(adminPortletHomepage.getCampaignMgmStatusReport().isEnabled());

        // vehicle administrationList
        openVehicleAdministrationList(adminPortletHomepage, driver);
        assertTrue(adminPortletHomepage.getVehicleOverview().isEnabled());
        assertTrue(adminPortletHomepage.getExceptionListManagement().isEnabled());
        assertTrue(adminPortletHomepage.getTemplateManagement().isEnabled());

        // testing menu
        openTestingMenu(adminPortletHomepage, driver);
        assertTrue(adminPortletHomepage.getManualRecallSystem().isEnabled());
        assertTrue(adminPortletHomepage.getManualRecallSystem().isEnabled());

        // sector log
        openSectorLog(adminPortletHomepage, driver);
        assertTrue(adminPortletHomepage.getLoggingOverview().isEnabled());
        assertTrue(adminPortletHomepage.getLoggingDetails().isEnabled());
        assertTrue(adminPortletHomepage.getLoggingReporting().isEnabled());

        // user management
        openUserManagement(adminPortletHomepage, driver);
        assertTrue(adminPortletHomepage.getUserOverview().isEnabled());
        assertTrue(adminPortletHomepage.getConfigurationFileEditing().isEnabled());

        // documentation
        openDocumentation(adminPortletHomepage, driver);
        assertTrue(adminPortletHomepage.getDocumentationReCall().isEnabled());
        assertTrue(adminPortletHomepage.getDocumentationCarPort().isEnabled());

    }


    /**
     * ============= INITIALISE WEB PAGES =================
     */
    static void initialiseWebPages(WebDriver driver) {

        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        CampaignManagementSearch campaignManagementSearch = new CampaignManagementSearch(driver);
        CampaignManagementDisplayCampaign campaignManagementDisplayCampaign = new CampaignManagementDisplayCampaign(driver);
        DialogueInspectVehicles dialogueInspectVehicles = new DialogueInspectVehicles(driver);
        InboxCampaignCreation inboxCampaignCreation = new InboxCampaignCreation(driver);
        Batch_info batchInfo = new Batch_info(driver);
        ManualService42SystemPage manualService42SystemPage = new ManualService42SystemPage(driver);
        AddService42SystemModal addService42SystemModal = new AddService42SystemModal(driver);
        LoggingOverview loggingOverview = new LoggingOverview(driver);
        LoggingDetail loggingDetail = new LoggingDetail(driver);
        ActionsOnupReCall actionsOnupReCall = new ActionsOnupReCall(driver);
        ManualRecallSystemPage manualRecallSystemPage = new ManualRecallSystemPage(driver);
        AddRecallSystemModal addRecallSystemModal = new AddRecallSystemModal(driver);

    }


    /**
     * ============= DELETE RECALL =================
     */
    static void deleteReCall(
            ActionsOnupReCall actionsOnupReCall,
            WebDriverWait webDriverWait,
            AdminPortletHomepage adminPortletHomepage,
            ManualRecallSystemPage manualRecallSystemPage,
            String recall) {

        actionsOnupReCall.deleteReCall(
                webDriverWait,
                adminPortletHomepage,
                manualRecallSystemPage,
                recall);
    }


    /**
     * ============= DELETE CAMPAIGN =================
     */

    static void deleteCampaign(
            ActionsOnupOruCampaign actionsOnupOruCampaign,
            WebDriver driver,
            WebDriverWait wait,
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            String CAMPAIGN_ID
    ) {

        actionsOnupOruCampaign.deleteOruCampaign(
                driver,
                wait,
                adminPortletHomepage,
                campaignManagementSearch,
                CAMPAIGN_ID);
    }



    static void deleteCampaign(WebDriver driver, String CAMPAIGN_ID, String AM_Code, String RECALL_ID) {

        ActionsOnupOruCampaign actionsOnupOruCampaign = new ActionsOnupOruCampaign(driver);
        WebDriverWait wait = new WebDriverWait(driver, 20);
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        CampaignManagementSearch campaignManagementSearch = new CampaignManagementSearch(driver);
        ManualService42SystemPage manualService42SystemPage = new ManualService42SystemPage(driver);
        ActionsOnupService42 actionsOnupService42 = new ActionsOnupService42(driver);
        ActionsOnupReCall actionsOnupReCall = new ActionsOnupReCall(driver);
        ManualRecallSystemPage manualRecallSystemPage = new ManualRecallSystemPage(driver);

        try {
            actionsOnupOruCampaign.deleteOruCampaign(
                    driver,
                    wait,
                    adminPortletHomepage,
                    campaignManagementSearch,
                    CAMPAIGN_ID);
        } catch (Exception ignored) {
            System.out.println("Campaign could not be deleted");
        }

        actionsOnupService42.deleteFlashFileAndService42(
                wait,
                adminPortletHomepage,
                manualService42SystemPage,
                AM_Code);

        actionsOnupReCall.deleteReCall(
                wait,
                adminPortletHomepage,
                manualRecallSystemPage,
                RECALL_ID);
    }


    /**
     * ============= OPEN EPIC =================
     */
    // TODO:
    static boolean isDisplayedCmg(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getCampaignManagement(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedInbox(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getCampaignManagementInbox(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedSearch(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getCmSearch(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedCmRolloutStatus(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getCmRolloutStatus(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedCmStatusRep(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getCampaignMgmStatusReport(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void openCampaignManagement(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {

        if (!isDisplayedInbox(adminPortletHomepage, driver)) {
            adminPortletHomepage.clickCampaignManagement();

        }
    }


    static boolean isDisplayedVehclAdmList(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getVehicleAdministrationList(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedVehicleOverview(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getVehicleOverview(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedVehclExpListMng(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getExceptionListManagement(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedVehclTemplMngm(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getTemplateManagement(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void openVehicleAdministrationList(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {

        if (!isDisplayedVehicleOverview(adminPortletHomepage, driver)) {
            adminPortletHomepage.clickVehicleAdministrationList();
        }
    }

    static void openVehicleOverview(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {

        openVehicleAdministrationList(adminPortletHomepage, driver);
        adminPortletHomepage.clickVehicleOverview();
    }

    static void openExceptionList(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {

        openVehicleAdministrationList(adminPortletHomepage, driver);
        adminPortletHomepage.clickExceptionListManagement();
    }


    static boolean isDisplayedManualRecallSystem(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getManualRecallSystem(), 10, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    static boolean isDisplayedTestingMenu(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getTestingMenu(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    static boolean isDisplayedManS42Syst(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getManualService42(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void openTestingMenu(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {


        ReusableMethods.waitForClickablility(adminPortletHomepage.getTestingMenu(), 10, driver);

        boolean isDisplayedManualRecallSystem = adminPortletHomepage.getManualRecallSystem().isDisplayed();
//        if (!isDisplayedManualRecallSystem(adminPortletHomepage, driver)) {
            if (!isDisplayedManualRecallSystem) {

//            adminPortletHomepage.clickTestingMenu();
//            clickElement(adminPortletHomepage.getTestingMenu(), 2, driver);
                clickJS(adminPortletHomepage.getTestingMenu(), driver);
        }

//        if (!adminPortletHomepage.isAvailableMenuEntryReCall() ||
//                !adminPortletHomepage.getManualRecallSystem().isDisplayed()) {
//            ConvienentActionsInONUP.saveClick(adminPortletHomepage.getTestingMenu());
//        }  // This method gives error for OUQA-830
    }


    static boolean isDisplayedSectorLog(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getSectorLog(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedLoggingOverview(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getLoggingOverview(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedLogDetail(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getLoggingDetails(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedLoggingReporting(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getLoggingReporting(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void openSectorLog(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {

        if (!isDisplayedLoggingOverview(adminPortletHomepage, driver)) {
            adminPortletHomepage.clickSectorLog();
        }
    }


    static boolean isDisplayedUserMngmt(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getUserManagement(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedUserOverview(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getUserOverview(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedUsMngConfigFileEdit(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getConfigurationFileEditing(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void openUserManagement(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {

        if (!isDisplayedUserOverview(adminPortletHomepage, driver)) {
            adminPortletHomepage.clickUserManagement();
        }
    }

    static boolean isDisplayedDocumentation(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getDocumentation(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedDocumentationRecall(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getDocumentationReCall(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static boolean isDisplayedDocumCarport(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {
        try {
            waitForClickablility(adminPortletHomepage.getDocumentationCarPort(), 5, driver);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    static void openDocumentation(AdminPortletHomepage adminPortletHomepage, WebDriver driver) {

        if (!isDisplayedDocumentationRecall(adminPortletHomepage, driver)) {
            adminPortletHomepage.clickDocumentation();
        }
    }


    /**
     * ============= IS FILE PRESENT UND CHECK DATA LINE =================
     */
    // TODO: OUQA-42207 de yazılıp kullanılmayan bir method. click download yaptıktan sonra indirilen File dosyası içerisindeki verileri okuyup doğrulama

    static final String N_A = "n/a";

    static String getFilesPresent(String downloadDirectory) {

        StringBuilder sb = new StringBuilder();

        File files = new File(downloadDirectory);

        for (File file : files.listFiles()) {
            sb.append(file.getName());
            sb.append(" ");
        }

        return sb.toString().trim();

    }

    static boolean checkDataLine(String line) {

        boolean isOk = true;

        // file/lines contains line breaks with following text, which is not a log entry with timestamp, therefore counted as 'valid'; just pragmatism
        if (!Pattern.matches("[0-9]{2}\\.[0-9]{2}\\.[0-9]{4}", line.subSequence(0, 10))) {
            return isOk;
        }

        String[] parts = line.split(";");

        assertTrue(8 == parts.length, "Expected 8 elements in CSV row, but found " + parts.length + ", line content: " + line);

        assertTrue(isTimeStampOrNA(parts[0]), "Timestamp not OK: " + parts[0] + ", " + line);

        assertTrue(isTimeStampBackendOrNA(parts[1]), "Timestamp backend not OK: " + parts[1] + ", " + line);

        assertTrue(Pattern.matches("[A-Z0-9]{17}", parts[2]), "VIN doesn't match pattern: " + parts[2] + ", " + line);

        assertTrue(isIntegerOrNA(parts[3]), "Criterion not OK: " + parts[3] + ", " + line);

        assertTrue(isVcsoStateOrNA(parts[4]), "VCSO not OK: " + parts[4] + ", " + line);

        assertTrue(isRepairCodeOrNA(parts[5]), "Repair code is not OK: " + parts[5] + ", " + line);

        assertTrue(isDetailCodeOrNA(parts[6]), "Detail code not OK: " + parts[6] + ", " + line);

        assertTrue(isDetailOrEmpty(parts[7]), "Details or empty string: " + parts[7] + ", " + line);

        return isOk;
    }

    static boolean isTimeStampOrNA(String string) {

        if (Pattern.matches("[0-9]{2}.[0-9]{2}.[0-9]{4}[ ][0-9]{2}.[0-9]{2}.[0-9]{2}", string)) {
            return true;
        } else if (string.toLowerCase().equals(N_A)) {
            return true;
        } else {
            return false;
        }

    }

    static boolean isTimeStampBackendOrNA(String string) {
        return isTimeStampOrNA(string);
    }

    static boolean isIntegerOrNA(String string) {

        if (string.toLowerCase().equals(N_A)) {
            return true;
        }

        try {
            Integer.parseInt(string);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    static boolean isVcsoStateOrNA(String string) {

        if (string.toLowerCase().equals(N_A)) {
            return true;
        }

        String status = string.substring(0, string.indexOf("("));

        return isIntegerOrNA(status.trim());

    }

    static boolean isRepairCodeOrNA(String string) {

        if (string.toLowerCase().equals(N_A)) {
            return true;
        } else {
            return isIntegerOrNA(string);
        }

    }

    static boolean isDetailCodeOrNA(String string) {

        if (string.toLowerCase().equals(N_A)) {
            return true;
        } else {
            return isIntegerOrNA(string);
        }

    }

    static boolean isDetailOrEmpty(String string) {

        if (null == string || string.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if (!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("Unzipping to " + newFile.getAbsolutePath());
                //create directories for sub directories in zip
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                //close this ZipEntry
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * ============= TIME STAMP VALIDATING =================
     */
    static boolean validateTimestamp(String timeStamp) {

        String dateFormat = "dd.MM.yyyy HH:mm:ss";

        try {
            Date date = new SimpleDateFormat(dateFormat).parse(timeStamp);
            System.out.println(date.toString());
            return true;
        } catch (ParseException e) {
            // If input date is in different format or invalid.
            System.out.println("Format of date '" + timeStamp + "' is Wrong ");
            return false;
        }
    }


    /**
     * ============= TIME STAMP FROM FILE NAME =================
     */
    static LocalDateTime getTimestampFromFileName(WebElement statusFileName) {

        String timestamp = statusFileName.getText().trim().substring(0, 14);

        return LocalDateTime.parse(timestamp,
                DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
    }

    /**
     * ============= CLEAR & DELETE FIELD =================
     */
    static void clearField(WebElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.DELETE);
    }

    static void deleteField(WebElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
    }


    /**
     * ============= FILE CREATION TIME =================
     */
    static String fileCreationTime() {

        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now().plusSeconds(30);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        String date = localDate.format(dateFormat);
        String time = localTime.format(timeFormat);
        return "File creation time : " + date + " " + time;
    }


    /**
     * ============= WEB DRIVER =================
     */

    static void changeBrowserSize800x600(WebDriver driver){
//        driver.manage().window().setSize(new Dimension(1024, 768));
//        driver.manage().window().setSize(new Dimension(1980, 1050));

//        Dimension dimension = new Dimension(800, 600);
//        driver.manage().window().setSize(dimension);

//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("document.body.style.zoom='80%'");

        // Tarayıcının boyutunu ayarla
        Point point = new Point(0, 0);
        driver.manage().window().setPosition(point);
        Dimension dimension = new Dimension(800, 600);
        driver.manage().window().setSize(dimension);
    }

    /**
     * ============= UNIQUE ID ATTRIBUTES TEST =================
     */

    static int testUniqueIdAttributes(WebDriver driver, List<String> duplicateIds) {
        // Find all elements on the page
        List<WebElement> elements = driver.findElements(By.cssSelector("*"));

        // Collect the id attributes of the web elements and store them in a list
        List<String> idList = new ArrayList<>();
        for (WebElement element : elements) {
            String id = element.getAttribute("id");
            if (id != null && !id.isEmpty()) {
                idList.add(id);
            }
        }

        // Use a set to check for uniqueness
        Set<String> uniqueIds = new HashSet<>(idList);

        // Perform the check and print duplicate IDs
        Map<String, Integer> idCountMap = new HashMap<>();
        for (String id : idList) {
            int count = idCountMap.getOrDefault(id, 0);
            idCountMap.put(id, count + 1);
        }

        String totalID = "Total ID : " + idList.size();
        duplicateIds.add(totalID);
        System.out.println(totalID);

        for (Map.Entry<String, Integer> entry : idCountMap.entrySet()) {
            String id = entry.getKey();
            int count = entry.getValue();
            if (count > 1 && !duplicateIds.contains(id)) {
//                duplicateIds.add(id);
                String duplicateNewID = "Duplicate ID found: id=\"" + id + "\", count=" + count;
                duplicateIds.add(duplicateNewID);
                System.out.println(duplicateNewID);
            }
        }

        // Perform the check using Assert
        int numDuplicates = idList.size() - uniqueIds.size();
        String urlAdresse = driver.getCurrentUrl();
        String duplicateIdCount = numDuplicates + " duplicate ids found on " + urlAdresse + " this page.";
        duplicateIds.add(duplicateIdCount);
        System.out.println(duplicateIdCount);

        return numDuplicates;
    }




    /**
     * ============= SWITCH TO DEBUGGING MODE =================
     */

    static void switchToDebuggingMode(WebDriver driver){

        // Switch to debugging mode
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("debugger;");
    }




    static void main(String[] args) {

//        LocalDate localDate = LocalDate.now();
//        LocalTime localTime = LocalTime.now().plusSeconds(30);
//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
//        String date = localDate.format(dateFormat);
//        String time = localTime.format(timeFormat);
//        System.out.println("File creation time : " + date + " " + time);
        String prnTo = "097662017";
        System.out.println("String: " + prnTo);
        Integer newYear = Integer.parseInt(prnTo.substring(5)) + 2;
        System.out.println(newYear);
        String newPrn = prnTo.substring(0, 5) + newYear;
        System.out.println("newYear: " + newPrn);


         String HEADER = "{\"cty\":\"adminUiToken_v1_0_6\",\"kid\":\"CN=86111_MBBA_ONUP, OU=Unknown, O=Unknown, L=Unknown, ST=Bayern, C=DE\"}";

        System.out.println(HEADER);
//        System.out.println("a");
    }


    static char[] stringToCharArray(String str) {
        if (str == null || str.isEmpty()) {
            return new char[0]; // Boş veya null String için boş char array döndür
        }

        char[] charArray = new char[str.length()]; // String uzunluğunda yeni bir char array oluştur

        for (int i = 0; i < str.length(); i++) {
            charArray[i] = str.charAt(i); // Her karakteri char array'e aktar
        }

        return charArray; // Sonuç char array'ini döndür
    }


    /**
     * Gets the current timestamp in the format "yyyy-MM-dd HH:mm:ss".
     *
     * @return A string representing the current timestamp with seconds.
     */
    static String getCurrentTimestampWithSecond() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define the date and time formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the current date and time as a string
        return now.format(formatter);
    }




//    static void     static void main(String[] args) {
//        AM_ID = System.getProperty("amcode");
//        VIN = System.getProperty("VIN");
//        CAMPAIGN_ID = System.getProperty("CAMPAIGN_ID");
//    }


}
