package testDev.templates.tbe.q22021;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.sulzer.oudcampaign.CampaignIdOUDFileBased;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.blockingdialogues.overlay.OverlayHandler;
import de.sulzer.pages.campaignmanagement.CampaignManagementDisplayCampaign;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.campaignmanagement.InboxPage;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignCreateNewBatchSequence;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignSavingChangesORU;
import de.sulzer.pages.campaignmanagement.inbox.popUpDialogues.DialogueReCallCampaignDetails;
import de.sulzer.pages.oruoverviewpage.Batch_info;
import de.sulzer.pages.sectorlog.LoggingDetail;
import de.sulzer.pages.sectorlog.LoggingOverview;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.utils.guistandardfunctions.ActionsCampaignMgmtInbox;
import de.sulzer.utils.guistandardfunctions.ActionsOnupOruCampaign;
import de.sulzer.utils.guistandardfunctions.ActionsOnupService42;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import de.sulzer.utils.guistandardfunctions.VOFlashAssignment;
import testCases.util.ConstantsCampaignMgmt;
import testCases.util.ConstantsCampaignMgmtBatch;
import testCases.util.ConstantsRestServerCommunication;
import testCases.util.ConstantsSectorLog;
import testCases.util.ReCallMassImport;
import testCases.util.SectorLogChecking;
import testFramework.AbstractStandardBehaviour;
import testFramework.connectionMockRestServer.CommunicatonRestServer;
import testFramework.constants.ConstantsCommunicationRestServer;
import testFramework.constants.ValuesGlobal;

/**
 * @author Sulzer GmbH
 *
 */
@Tag("OUQA-34907")
@Tag("WC")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("Sequentiell")
@Tag("21Q2")
@Tag("Mock")
public class OUQA_34907_Create_and_Approve_ORU_Campaign_with_50_000_VINs_and_5_GB_Flash_Medium extends AbstractStandardBehaviour {


    // TODO: SUNB - APROVAL  ISFV - DEMO Testing can be done using these AMs.

    private AdminPortletHomepage adminPortletHomepage;

    private InboxPage inboxPage;
    private DialogueReCallCampaignDetails dialogueReCallCampaignDetails;
    private CampaignManagementSearch campaignManagementSearch;
    private CampaignManagementDisplayCampaign campaignManagementDisplayCampaign;
    private InboxCampaignCreateNewBatchSequence inboxCampaignCreateNewBatchSequence;
    private InboxCampaignSavingChangesORU inboxCampaignSavingChangesORU;

    private Batch_info batchInfo;

    private LoggingOverview loggingOverview;
    private LoggingDetail loggingDetail;

    private ManualService42SystemPage manualService42SystemPage;
    private AddService42SystemModal addService42SystemModal;

    // Campaigns to create
    private String CAMPAIGN_ID = "";

    // System42 entries
    private static String AM_CODE = "";
    //
    private static final String PATH_TO_FILE_TO_BE_UPLOADED = ValuesGlobal.MIB2_5GB;

    // ReCall entries
    private static String RECALL_ID = "";

    private String UNIQUE_ID = "";

    //
    private CommunicatonRestServer crs = null;
    private SectorLogChecking sectorLogChecking = null;

    private ActionsCampaignMgmtInbox actionsCampaignMgmtInbox;

    private ActionsOnupService42 actionsOnupService42;
    private ActionsOnupOruCampaign actionsOnupOruCampaign;

    /*
     *
     */

    private static final int AMOUNT_VINS = 50000;

    @Override
    @org.junit.jupiter.api.BeforeEach
    protected void setUp() throws Throwable {

        try {
            super.setUp();
        } catch (Throwable e) {
            this.setThrowable(e);
            throw e;
        } finally {
            this.initTestContainer(9);
        }

    }

    @Override
    protected void testHook() throws Throwable {

        this.initialiseWebPages();

        this.actionsOnupService42 = new ActionsOnupService42(this.getWebDriver());
        this.actionsOnupOruCampaign = new ActionsOnupOruCampaign(getWebDriver());

        this.crs = new CommunicatonRestServer();
        this.sectorLogChecking = new SectorLogChecking(this.getWebDriver());

        this.actionsCampaignMgmtInbox = new ActionsCampaignMgmtInbox();

        // storing url for loading it later again (after timeout)
        String url = this.getWebDriver().getCurrentUrl();

        /*
         * Sending JSON document with fault code to Mock-/REST-Server
         */

        this.UNIQUE_ID = CampaignIdOUDFileBased.getInstance().ascertainNewId();
        this.CAMPAIGN_ID = this.UNIQUE_ID;
        AM_CODE = this.CAMPAIGN_ID;
        RECALL_ID = this.CAMPAIGN_ID;

        System.out.println("READ ID: " + this.UNIQUE_ID);

        String json = ReCallMassImport.createReCallMassImportJsonRequest(this.UNIQUE_ID, AMOUNT_VINS);

        Response response = this.crs.doPostApplicationJson(
                ConstantsRestServerCommunication.BASEURL_ReCall_POST_VINMASSIMPORT_JSON,
                json);

        if (response.getStatus() != 200) {
                   throw new Exception("POST of JSON resulted in HTTP status code " + response.getStatus() +
                   " (" + response.readEntity(String.class) + "), but expected was HTTP status code 200.");
        }

        //
        //this.logStepPassed(); // 1

        // for 50 000 VINs
        Thread.sleep(ConstantsCommunicationRestServer.TWO_AND_A_HALF_HOURS);

        // reloading website for log-in
        this.getWebDriver().get(url);

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

        this.adminPortletHomepage.clickSectorLog();
        this.adminPortletHomepage.clickLoggingOverview();

        this.loggingOverview.searchLogEntries(ConstantsSectorLog.RECALL);

        OverlayHandler.waitingForOverlay(this.getWebDriverWait());

        List<WebElement> elements = this.loggingOverview.getListLogEntries();

        //
        List<String> searchItems = new ArrayList<String>();
        searchItems.add(this.UNIQUE_ID);

        boolean found = this.sectorLogChecking.searchInLoggingOverviewByCriterias(elements, ConstantsSectorLog.RECALL, ConstantsSectorLog.LOG_OVERVIEW_COL8_ADDED, searchItems, this.adminPortletHomepage , this.loggingOverview, this.loggingDetail);

        if (! found) {
            throw new Exception("No logging details found for imported ReCall with ID '" + this.UNIQUE_ID + "'.");
        }

        //
        //this.logStepPassed(); // 2

        this.adminPortletHomepage.clickCampaignManagement();
        this.adminPortletHomepage.clickCampaignManagementInbox();

        Thread.sleep(10000);

        this.inboxPage.searchRecall(this.UNIQUE_ID);

        Thread.sleep(15000);

        WebElement element = this.inboxPage.getRecallTableFirstListItem();

        String firstReCallElement = element.getText().trim();

        assertTrue(firstReCallElement.contains(this.UNIQUE_ID), "Found first element in ReCall tree is not '" + this.UNIQUE_ID + "' element, but '" + firstReCallElement + "'.");

        //
        //this.logStepPassed(); // 3

        this.actionsCampaignMgmtInbox.checkReCallDetailsHeaderInformation(
                this.inboxPage,
                this.dialogueReCallCampaignDetails,
                UNIQUE_ID,
                AMOUNT_VINS,
                "Audi",
                this.getWebDriverWait());

        //
        //this.logStepPassed(); // 4

        this.actionsOnupService42.createService42WaitUploadCompleted5G(
                this.adminPortletHomepage,
                this.manualService42SystemPage,
                this.addService42SystemModal,
                AM_CODE,
                PATH_TO_FILE_TO_BE_UPLOADED,
                this.getWebDriver());

        //
        //this.logStepPassed(); // 5

        // open campaign management and inbox (assuming tab 'Create ORU Campaign' already opened)
        this.adminPortletHomepage.clickCampaignManagement();
        this.adminPortletHomepage.clickCampaignManagementInbox();

        // storing url for loading it later again (after timeout)
//    	url = this.getWebDriver().getCurrentUrl();

        // create campaign
        this.actionsOnupOruCampaign.createORUCampaignWith_cGW2_FlashMedium(
                RECALL_ID,
                AM_CODE,
                0,
                0,
                this.getWebDriver(),
                ActionsOnupOruCampaign.TRANSLATION_INPUT,
                ActionsOnupOruCampaign.TRANSLATION_OUTPUT,
                true,
                new VOFlashAssignment());

//    	this.getWebDriver().get(url);
//
//    	ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

        //
        //this.logStepPassed(); // 6

        this.actionsOnupOruCampaign.searchAndOpenCampaign(
                this.adminPortletHomepage,
                this.campaignManagementSearch,
                CAMPAIGN_ID,
                this.getWebDriver());

        this.openDialogueAddBatch(this.getWebDriverWait(), ConstantsCampaignMgmt.DEFAULT);

        String status = this.campaignManagementDisplayCampaign.getManualBatchStatusByBatchName(ConstantsCampaignMgmt.DEFAULT);

        assertTrue(
                ConstantsCampaignMgmt.STATUS_CREATED.equals(status),
                "Expected batch status '" + ConstantsCampaignMgmt.STATUS_CREATED + "', but found '" + status + "'.");

        //
        //this.logStepPassed(); // 7

        this.campaignManagementDisplayCampaign.clickManualBatchApprovedCheckboxBatchByName(ConstantsCampaignMgmt.DEFAULT);

        this.campaignManagementDisplayCampaign.clickButtonVerifiyAndApplyChanges();

        //
        this.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(inboxCampaignSavingChangesORU.getButtonApply()));
        //
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inboxCampaignSavingChangesORU.getButtonApply().sendKeys(Keys.ENTER);

        status = this.campaignManagementDisplayCampaign.getManualBatchStatusByBatchName(ConstantsCampaignMgmt.DEFAULT);

        assertTrue(
                ConstantsCampaignMgmt.STATUS_RELEASED.equals(status),
                "Expected batch status '" + ConstantsCampaignMgmt.STATUS_RELEASED + "', but found '" + status + "'.");

        //
        //this.logStepPassed(); // 8

        this.campaignManagementDisplayCampaign.clickManualBatchApprovedCheckboxBatchByName(ConstantsCampaignMgmt.DEFAULT);

        this.campaignManagementDisplayCampaign.clickButtonVerifiyAndApplyChanges();

        //
        this.getWebDriverWait().until(ExpectedConditions.elementToBeClickable(inboxCampaignSavingChangesORU.getButtonApply()));
        //
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        inboxCampaignSavingChangesORU.getButtonApply().sendKeys(Keys.ENTER);

        status = this.campaignManagementDisplayCampaign.getManualBatchStatusByBatchName(ConstantsCampaignMgmt.DEFAULT);

        assertTrue(
                ConstantsCampaignMgmt.STATUS_CANCELED.equals(status),
                "Expected batch status '" + ConstantsCampaignMgmt.STATUS_CANCELED + "', but found '" + status + "'.");

        //
        //this.logStepPassed(); // 9

    }

    @Override
    public void tearDownHook() {

    }

    /**
     *
     */
    private void initialiseWebPages() {

        this.adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());

        this.inboxPage = new InboxPage(this.getWebDriver());
        this.dialogueReCallCampaignDetails = new DialogueReCallCampaignDetails(this.getWebDriver());
        this.campaignManagementSearch = new CampaignManagementSearch(this.getWebDriver());
        this.campaignManagementDisplayCampaign = new CampaignManagementDisplayCampaign(this.getWebDriver());
        this.inboxCampaignCreateNewBatchSequence = new InboxCampaignCreateNewBatchSequence(this.getWebDriver());
        this.inboxCampaignSavingChangesORU = new InboxCampaignSavingChangesORU(this.getWebDriver());

        this.batchInfo = new Batch_info(this.getWebDriver());

        this.manualService42SystemPage = new ManualService42SystemPage(this.getWebDriver());
        this.addService42SystemModal = new AddService42SystemModal(this.getWebDriver());

        this.loggingOverview = new LoggingOverview(this.getWebDriver());
        this.loggingDetail = new LoggingDetail(this.getWebDriver());

    }

    private void openDialogueAddBatch(WebDriverWait wait, String batchTitle) throws Exception {

        this.campaignManagementDisplayCampaign.clickButtonAddBatch();
        wait.until(ExpectedConditions.visibilityOf(
                this.inboxCampaignCreateNewBatchSequence.getTitleCreateNewBatchStep1()));
        //
        this.inboxCampaignCreateNewBatchSequence.setTitleNewBatch(batchTitle);
        wait.until(ExpectedConditions.elementToBeClickable(
                this.inboxCampaignCreateNewBatchSequence.getRadioButtonManualApproval()));
        this.inboxCampaignCreateNewBatchSequence.clickRadioButtonManualApproval();
        //
        this.inboxCampaignCreateNewBatchSequence.clickButtonNextVehicleFiltering();

        //
        this.inboxCampaignCreateNewBatchSequence.setValueSinglePRNumber(ConstantsCampaignMgmtBatch.PRN_K8C);
        this.inboxCampaignCreateNewBatchSequence.clickButtonNextTestFilter();
        //
        Thread.sleep(2000);
        this.inboxCampaignCreateNewBatchSequence.clickCheckBoxMoveVehicleFromVehiclePool();
        this.inboxCampaignCreateNewBatchSequence.clickButtonCreateBatch();
        //
        this.getWebDriverWait().until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));

    }

}