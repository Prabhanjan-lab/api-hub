package testDev.templates.tbe.q12021;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebElement;

import de.sulzer.oudcampaign.CampaignIdOUDFileBased;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.blockingdialogues.overlay.OverlayHandler;
import de.sulzer.pages.campaignmanagement.CampaignManagementDisplayCampaign;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.campaignmanagement.InboxPage;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignCreation;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignEditBatchSequence;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignSavingChangesORU;
import de.sulzer.pages.campaignmanagement.inbox.popUpDialogues.DialogueInspectVehicles;
import de.sulzer.pages.caradministration.VehicleOverviewPage;
import de.sulzer.pages.caradministration.VehicleOverviewPageSpecificVIN;
import de.sulzer.pages.caradministration.popUpDialogues.DialogueCampaignDetails;
import de.sulzer.pages.oruoverviewpage.Batch_info;
import de.sulzer.pages.sectorlog.LoggingDetail;
import de.sulzer.pages.sectorlog.LoggingOverview;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.utils.guistandardfunctions.ActionsOnupOruCampaign;
import de.sulzer.utils.guistandardfunctions.ActionsOnupOruCampaignBatches;
import de.sulzer.utils.guistandardfunctions.ActionsOnupVehicleOverview;
import de.sulzer.utils.guistandardfunctions.ActionsSectorLog;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import testCases.util.ConstantsReCallImports;
import testCases.util.ConstantsRestServerCommunication;
import testCases.util.ConstantsSectorLog;
import testCases.util.ConstantsVehicleManagement;
import testCases.util.HelperClassFaultyReCalls;
import testCases.util.SectorLogChecking;
import testFramework.AbstractStandardBehaviour;
import testFramework.connectionMockRestServer.CommunicatonRestServer;
import testFramework.constants.ConstantsCommunicationRestServer;
import testFramework.constants.ValuesGlobal;

/**
 * @author Sulzer GmbH
 *
 */
@Tag("OUQA-6825")
@Tag("WC")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("Sequentiell")
@Tag("21Q1")
@Tag("Mock")
public class OUQA_6825_7_5_Importing_ReCall_Actions_Type_04_Part_of_ORU_Campaign_Add_VIN_one_matching_batch_Two_manual_approved_batches extends AbstractStandardBehaviour {

    private static final String MBT_321 = "321";

    private static final String MBT_123 = "123";

    private static final String MBT_FV1 = "FV1";

    private static final String MBT_FV0 = "FV0";

    private AdminPortletHomepage adminPortletHomepage;


    private CampaignManagementSearch campaignManagementSearch;
    private CampaignManagementDisplayCampaign campaignManagementDisplayCampaign;
    private DialogueInspectVehicles dialogueInspectVehicles;

    private Batch_info batchInfo;
    private InboxCampaignEditBatchSequence inboxCampaignEditBatchSequence;

    private VehicleOverviewPage vehicleOverviewPage;
    private VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN;

    private ManualService42SystemPage manualService42SystemPage;
    private AddService42SystemModal addService42SystemModal;

    private LoggingOverview loggingOverview;
    private LoggingDetail loggingDetail;

    private static String UNIQUE_ID = "";

    private static String SERVICE42_AM_CODE = "";
    private static final String PATH_TO_FILE_TO_BE_UPLOADED = ValuesGlobal.MIB2;

    private static String RECALL_ID = "";

    private static final String VIN_1 = "BAUSLZ4N819805117";
    private static final String VIN_2 = "BAUSLZ4N819805118";

    private static final String BATCH_TITLE = "First automatically approved batch";
    private static final String BATCH_A = "AAA";
    private static final String BATCH_B = "BBB";

    private static final String PERCENT_50 = "50";

    private static final String IMPORTER_1 = MBT_123;
    private static final String IMPORTER_2 = MBT_321;

    //

    private ActionsSectorLog actionsSectorLog;
    private SectorLogChecking sectorLogChecking;

    //
    private CommunicatonRestServer crs = null;
    private HelperClassFaultyReCalls hclrc = null;

    private ActionsOnupOruCampaign actionsOnupOruCampaign;
    private ActionsOnupOruCampaignBatches actionsOnupOruCampaignBatches;
    private ActionsOnupVehicleOverview actionsOnupVehicleOverview;

    @Override
    @org.junit.jupiter.api.BeforeEach
    protected void setUp() throws Throwable {

        try {
            super.setUp();
        } catch (Throwable e) {
            this.setThrowable(e);
            throw e;
        } finally {
            this.initTestContainer(8);
        }

    }

    @Override
    protected void testHook() throws Throwable {

        /*
         * Test initialisation.
         */

        this.initialiseWebPages();

        this.sectorLogChecking = new SectorLogChecking(this.getWebDriver());

        this.actionsOnupOruCampaign = new ActionsOnupOruCampaign(getWebDriver());
        this.actionsOnupOruCampaignBatches = new ActionsOnupOruCampaignBatches(this.getWebDriver());
        this.actionsOnupVehicleOverview = new ActionsOnupVehicleOverview(getWebDriver());

        this.crs = new CommunicatonRestServer();
        this.hclrc = new HelperClassFaultyReCalls();

        /*
         *
         */

        UNIQUE_ID = CampaignIdOUDFileBased.getInstance().ascertainNewId();
        SERVICE42_AM_CODE = UNIQUE_ID;
        RECALL_ID = UNIQUE_ID;

        this.printMessage("READ ID: " + UNIQUE_ID);

        String template_content = this.hclrc.loadJsonContentTemplate(
                "/selenium.gui.testing/data/importReCall/recall-ouqa-6825-a-minimal-data-record.json");

        String content = template_content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, UNIQUE_ID);

        /*
         * Sending JSON document with fault code to Mock-/REST-Server
         */

        Response response = this.crs.doPostApplicationJson(
                ConstantsRestServerCommunication.BASEURL_ReCall_POST_CORRECT_RECALL_JSON,
                content);

        String jsonResponse = response.readEntity(String.class);
        int statusCode = response.getStatus();

        if (200 != statusCode) {
                   throw new Exception("ReCall ID '" + UNIQUE_ID + "': POST of JSON resulted in HTTP status code " + statusCode +
                   " (" + jsonResponse + "), but expected was HTTP status code 200.");
        }

        // handling timing problems with file creation which has to be tested
        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);

//		this.actionsSectorLog.openMenuSectorLogLoggingOverview(this.adminPortletHomepage);
//
//		this.loggingOverview.searchLogEntries(ConstantsSectorLog.RECALL);
//
//		OverlayHandler.waitingForOverlay(this.getWebDriverWait());
//
//		List<WebElement> logEntries = this.loggingOverview.getListLogEntries();
//
//		//
//		boolean found = false;
//
//		List<String> searchItems = new ArrayList<String>();
//		searchItems.add(UNIQUE_ID);
//
//		found = this.sectorLogChecking.searchInLoggingOverviewByCriterias(
//				logEntries,
//				ConstantsSectorLog.RECALL,
//				ConstantsSectorLog.LOG_OVERVIEW_COL8_ADDED,
//				searchItems,
//				this.adminPortletHomepage,
//				this.loggingOverview,
//				this.loggingDetail);
//
//		assertTrue(found, "No logging details found for imported ReCall with searchItems '" + searchItems + "'.");
//
//		ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());
//
//        //
//    	this.adminPortletHomepage.clickCampaignManagement();
//    	this.adminPortletHomepage.clickCampaignManagementInbox();
//
//		Thread.sleep(6000); // waiting for list loading of ReCall
//
//		this.inboxPage.setRecallSearchField(UNIQUE_ID);
//
//		Thread.sleep(5000);
//
//		WebElement element = this.getWebDriver().findElement(By.xpath("//*[@id='recall-tree-1']//div[contains(@class,'jstree-checkbox-selection')]//a[@id='" + UNIQUE_ID + ".parent_anchor']//i[@class='jstree-icon jstree-checkbox']"));
//
//		assertFalse(null == element, "Imported ReCall '" + UNIQUE_ID + "' not found in Inbox/ReCall list.");

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

        this.processVINs(VIN_1);

        this.actionsOnupOruCampaign.createOruCampaignWithImportedReCall(
                this.adminPortletHomepage,
                this.manualService42SystemPage,
                this.addService42SystemModal,
                this.batchInfo,
                SERVICE42_AM_CODE,
                PATH_TO_FILE_TO_BE_UPLOADED,
                RECALL_ID,
                this.getWebDriver());

        //
        //this.logStepPassed(); // 1

        /*
         * First manually approved batch without VIN
         */
        this.actionsOnupOruCampaignBatches.createManuallyApprovedBatchFilterImporterAndMBT(
                this.batchInfo,
                this.campaignManagementDisplayCampaign,
                this.inboxCampaignEditBatchSequence,
                BATCH_A,
                IMPORTER_1,
                MBT_FV0,
                this.getWebDriverWait());

        this.campaignManagementDisplayCampaign.clickManualBatchApprovedCheckboxBatchByName(BATCH_A);

        /*
         * Second manually approved batch without VIN
         */
        this.actionsOnupOruCampaignBatches.createManuallyApprovedBatchFilterImporterAndMBT(
                this.batchInfo,
                this.campaignManagementDisplayCampaign,
                this.inboxCampaignEditBatchSequence,
                BATCH_B,
                IMPORTER_2,
                MBT_FV1,
                this.getWebDriverWait());

        this.campaignManagementDisplayCampaign.clickManualBatchApprovedCheckboxBatchByName(BATCH_B);

        //
        //this.logStepPassed(); // 2

        this.actionsOnupOruCampaignBatches.moveVINToManualBatchByName(
                VIN_1,
                BATCH_A,
                this.getWebDriver(),
                this.getWebDriverWait(),
                this.campaignManagementDisplayCampaign);

        //
        //this.logStepPassed(); // 3

        this.actionsOnupOruCampaignBatches.approveManualBatchOfCampaignByName(
                UNIQUE_ID,
                BATCH_A,
                this.adminPortletHomepage,
                this.campaignManagementSearch,
                this.campaignManagementDisplayCampaign,
                this.batchInfo,
                this.getWebDriver());

        this.actionsOnupOruCampaignBatches.approveManualBatchOfCampaignByName(
                UNIQUE_ID,
                BATCH_B,
                this.adminPortletHomepage,
                this.campaignManagementSearch,
                this.campaignManagementDisplayCampaign,
                this.batchInfo,
                this.getWebDriver());

        //
        //this.logStepPassed(); // 4

        template_content = this.hclrc.loadJsonContentTemplate(
                "/selenium.gui.testing/data/importReCall/recall-ouqa-6825-b-minimal-data-record.json");

        content = template_content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, UNIQUE_ID);

        /*
         * Sending JSON document with fault code to Mock-/REST-Server
         */

        response = this.crs.doPostApplicationJson(
                ConstantsRestServerCommunication.BASEURL_ReCall_POST_CORRECT_RECALL_JSON,
                content);

        jsonResponse = response.readEntity(String.class);
        statusCode = response.getStatus();

        if (200 != statusCode) {
                   throw new Exception("ReCall ID '" + UNIQUE_ID + "': POST of JSON resulted in HTTP status code " + statusCode +
                   " (" + jsonResponse + "), but expected was HTTP status code 200.");
        }

        // handling timing problems with file creation which has to be tested
        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);

        this.actionsOnupVehicleOverview.setVehicleVinOptions(
                this.adminPortletHomepage,
                this.vehicleOverviewPage,
                this.vehicleOverviewPageSpecificVIN,
                VIN_2,
                false,
                false,
                false,
                true);

        //
        //this.logStepPassed(); // 5

        this.actionsSectorLog.openMenuSectorLogLoggingOverview(this.adminPortletHomepage);

        this.loggingOverview.searchLogEntries(ConstantsSectorLog.RECALL);

        OverlayHandler.waitingForOverlay(this.getWebDriverWait());

        List<WebElement> logEntries = this.loggingOverview.getListLogEntries();

        //
        boolean found = false;

        List<String> searchItems = new ArrayList<String>();
        searchItems.clear();
        searchItems.add(VIN_2);

        found = this.sectorLogChecking.searchInLoggingOverviewByCriterias(
                logEntries,
                ConstantsSectorLog.RECALL,
                ConstantsSectorLog.LOG_OVERVIEW_COL8_ADDED,
                searchItems,
                this.adminPortletHomepage,
                this.loggingOverview,
                this.loggingDetail);

        assertTrue(found, "No logging details found for imported ReCall with searchItems '" + searchItems + "'.");

        //
        //this.logStepPassed(); // 6

        /*
         * Checking VIN in manual batch
         */
        this.actionsOnupOruCampaign.searchAndOpenCampaign(
                this.adminPortletHomepage,
                this.campaignManagementSearch,
                UNIQUE_ID,
                this.getWebDriver());

        found = this.checkingManualBatchesForVin(VIN_1, BATCH_A);

        assertFalse(found, "Expected NOT to find VIN '" + VIN_1 + "', but was found in batch '" + BATCH_A + "'.");

        //
        //this.logStepPassed(); // 7

        found = this.checkingManualBatchesForVin(VIN_2, BATCH_B);

        assertFalse(found, "Expected NOT to find VIN '" + VIN_2 + "', but was found in batch '" + BATCH_B + "'.");

        //
        //this.logStepPassed(); // 8

        this.actionsOnupVehicleOverview.searchVehicleAdminstrationWithVin(adminPortletHomepage, vehicleOverviewPage, VIN_2);

        boolean isCampaingInActiveQueue = this.actionsOnupVehicleOverview.isCampaignInActiveQueue(
                this.getWebDriver(),
                RECALL_ID,
                this.vehicleOverviewPageSpecificVIN);

        assertTrue(isCampaingInActiveQueue, "Campaign ID for ReCall '" + UNIQUE_ID + "' is not in Active Queue.");

        WebElement we = vehicleOverviewPageSpecificVIN.getActiveCampaignById(UNIQUE_ID);

        String activeCampaingStatusText = we.getText().trim();

        assertTrue(activeCampaingStatusText.contains(ConstantsVehicleManagement.VCSO_05), "Active campaign has not state '1', but another, see: '" + activeCampaingStatusText + "'.");

        //
        //this.logStepPassed(); // 9

    }

    /**
     * @return
     * @throws Exception
     */
    private boolean checkingManualBatchesForVin(String searchVin, String... batches) throws Exception {

        boolean found = false;

        for (String batch : batches) {

            this.campaignManagementDisplayCampaign.openInspectDialogueManuallyApprovedBatchVinsByName(batch);

            List<WebElement> vins = this.dialogueInspectVehicles.getListColumnElementsVINs();

            for (WebElement vin : vins) {

                String text = vin.getText().trim();

                if (text.contains(searchVin)) {

                    found = true;
                    break;

                }

            }

            this.dialogueInspectVehicles.clickButtonClose();

        }

        return found;

    }

    /**
     * @throws InterruptedException
     */
    private void processVINs(String... vins) throws InterruptedException {

        for (String vin : vins) {

            this.actionsOnupVehicleOverview.initVehicleOverviewQueues(
                    vin,
                    this.adminPortletHomepage,
                    this.vehicleOverviewPage,
                    this.vehicleOverviewPageSpecificVIN);

            this.actionsOnupVehicleOverview.setVehicleVinOptions(
                    this.adminPortletHomepage,
                    this.vehicleOverviewPage,
                    this.vehicleOverviewPageSpecificVIN,
                    vin,
                    false,
                    false,
                    false,
                    true);

        }

    }

    @Override
    public void tearDownHook() {

    }

    private void initialiseWebPages() {

        this.adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());

        this.campaignManagementSearch = new CampaignManagementSearch(this.getWebDriver());
        this.campaignManagementDisplayCampaign = new CampaignManagementDisplayCampaign(this.getWebDriver());
        this.dialogueInspectVehicles = new DialogueInspectVehicles(this.getWebDriver());

        this.batchInfo = new Batch_info(this.getWebDriver());
        this.inboxCampaignEditBatchSequence = new InboxCampaignEditBatchSequence(this.getWebDriver());

        this.vehicleOverviewPage = new VehicleOverviewPage(this.getWebDriver());
        this.vehicleOverviewPageSpecificVIN = new VehicleOverviewPageSpecificVIN(this.getWebDriver());

        this.manualService42SystemPage = new ManualService42SystemPage(this.getWebDriver());
        this.addService42SystemModal = new AddService42SystemModal(this.getWebDriver());

        this.loggingOverview = new LoggingOverview(this.getWebDriver());
        this.loggingDetail = new LoggingDetail(this.getWebDriver());

    }

}