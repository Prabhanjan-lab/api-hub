package testDev.templates.tbe.q42020;

import de.sulzer.oudcampaign.CampaignIdOUDFileBased;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.blockingdialogues.overlay.OverlayHandler;
import de.sulzer.pages.campaignmanagement.CampaignManagementDisplayCampaign;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.campaignmanagement.InboxPage;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignCreation;
import de.sulzer.pages.campaignmanagement.inbox.popUpDialogues.DialogueInspectVehicles;
import de.sulzer.pages.caradministration.VehicleOverviewPage;
import de.sulzer.pages.caradministration.VehicleOverviewPageSpecificVIN;
import de.sulzer.pages.oruoverviewpage.Batch_info;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.pages.vehicleadministration.exceptionlistmanagement.ExceptionListOverviewPage;
import de.sulzer.utils.guistandardfunctions.*;
import de.sulzer.utils.screenshots.ScreenshotsShutterbug;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testCases.util.ConstantsReCallImports;
import testCases.util.ConstantsRestServerCommunication;
import testCases.util.ConstantsVehicleManagement;
import testCases.util.HelperClassFaultyReCalls;
import testFramework.AbstractStandardBehaviour;
import testFramework.connectionMockRestServer.CommunicatonRestServer;
import testFramework.constants.ConstantsCommunicationRestServer;
import testFramework.constants.ValuesGlobal;

import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Sulzer GmbH
 *
 */
@Tag("OUQA-2494")
@Tag("WC")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("Sequentiell")
@Tag("20Q3")
@Tag("Mock")
public class OUQA_2494_ReCall_approval_is_given_by_setting_the_testing_vehicle_flag_to_true extends AbstractStandardBehaviour {

    private AdminPortletHomepage adminPortletHomepage;

    private InboxPage inboxPage;
    private CampaignManagementSearch campaignManagementSearch;
    private CampaignManagementDisplayCampaign campaignManagementDisplayCampaign;
    private DialogueInspectVehicles dialogueInspectVehicles;
    private InboxCampaignCreation inboxCampaignCreation;

    private Batch_info batchInfo;

    private VehicleOverviewPage vehicleOverviewPage;
    private VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN;
    private ExceptionListOverviewPage exceptionListOverviewPage;

    private ManualService42SystemPage manualService42SystemPage;
    private AddService42SystemModal addService42SystemModal;

    private String UNIQUE_ID_01 = "";
    private String UNIQUE_ID_02 = "";

    private static final String VIN_01 = "BAUSLZF8A21041227";
    private static final String VIN_02 = "BAUSLZF8A21041228";

    private List<String> listVINs = new ArrayList<>();

    // System42 entries
    private static String AM_CODE = "";
    //
    private static final String PATH_TO_FILE_TO_BE_UPLOADED = ValuesGlobal.MIB2;

    // ReCall entries
    private static String RECALL_ID = "";

    private static final String NO_RECORDS_FOUND = "No records found.";

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
            this.initTestContainer(8); // test has 8 test steps
        }

    }

    @Override
    protected void testHook() throws Throwable {

        /*
         * Test initialisation.
         */

        this.initialiseWebPages();

        this.actionsOnupOruCampaign = new ActionsOnupOruCampaign(getWebDriver());
        this.actionsOnupOruCampaignBatches = new ActionsOnupOruCampaignBatches(this.getWebDriver());
        this.actionsOnupVehicleOverview = new ActionsOnupVehicleOverview(getWebDriver());

        this.crs = new CommunicatonRestServer();
        this.hclrc = new HelperClassFaultyReCalls();

        this.UNIQUE_ID_01 = CampaignIdOUDFileBased.getInstance().ascertainNewId();
        AM_CODE = this.UNIQUE_ID_01;
        RECALL_ID = this.UNIQUE_ID_01;

        this.UNIQUE_ID_02 = CampaignIdOUDFileBased.getInstance().ascertainNewId();

        this.printMessage("READ ID 01: " + this.UNIQUE_ID_01);
        this.printMessage("READ ID 02: " + this.UNIQUE_ID_02);

//    	this.listVINs.add(VIN_01);
        this.listVINs.add(VIN_02);

        /*
         * Test preparation for first VIN for Campaign in Active Queue (test step 1)
         */

        String template_content = this.hclrc.loadJsonContentTemplate(
                "/selenium.gui.testing/data/importReCall/recall-ouqa-2494-b-minimal-data-record.json");

        // Changes data to VIN_01 (= BAUSLZ4N819805041)
        String content = template_content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, this.UNIQUE_ID_02);

        /*
         * Sending JSON document with fault code to Mock-/REST-Server
         */

        Response response = this.crs.doPostApplicationJson(
                ConstantsRestServerCommunication.BASEURL_ReCall_POST_CORRECT_RECALL_JSON,
                content);

        String jsonResponse = response.readEntity(String.class);
        int statusCode = response.getStatus();

        if (200 != statusCode) {
                   throw new Exception("ReCall ID '" + this.UNIQUE_ID_02 + "': POST of JSON resulted in HTTP status code " + statusCode +
                   " (" + jsonResponse + "), but expected was HTTP status code 200.");
        }

        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);

        //

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

        this.getWebDriverWait().until(ExpectedConditions.visibilityOf(this.adminPortletHomepage.getVehicleAdministrationList()));

        this.actionsOnupVehicleOverview.openVehicleOverview(this.adminPortletHomepage);

        this.actionsOnupVehicleOverview.initVehicleOverviewQueuesOfVINs(
                this.listVINs,
                this.adminPortletHomepage,
                this.vehicleOverviewPage,
                this.vehicleOverviewPageSpecificVIN);

//		for (String vin : listVINs) {
//
//			ActionsOnupVehicleOverview.setVehicleVinOptions(
//					this.adminPortletHomepage,
//					this.vehicleOverviewPage,
//					this.vehicleOverviewPageSpecificVIN,
//					vin,
//					true,
//					false,
//					false,
//					false);
//
//		}

//    	ActionsOnupVehicleOverview.openExceptionList(this.adminPortletHomepage);
//
//    	for (String vin : listVINs) {
//
//    		this.exceptionListOverviewPage.searchExceptionListItem(vin);
//
//    		OverlayHandler.waitingForOverlay(this.getWebDriverWait());
//
//    		// explicitely first row of table
//    		String entryText = this.exceptionListOverviewPage.getRowsExecptionElements().get(0).getText().trim();
//
//    		assertFalse(entryText.contains(vin), "VIN found in Exception List: '" + vin + "', but shouldn't be found.");
//
//    	}

        this.actionsOnupOruCampaign.createApprovedOruCampaignWithImportedReCall(
                this.adminPortletHomepage,
                this.inboxPage,
                this.manualService42SystemPage,
                this.addService42SystemModal,
                this.batchInfo,
                this.UNIQUE_ID_02,
                PATH_TO_FILE_TO_BE_UPLOADED,
                this.UNIQUE_ID_02,
                new VOFlashAssignment(),
                this.getWebDriver());

        this.actionsOnupVehicleOverview.openVehicleOverview(this.adminPortletHomepage);

//    	for (String vin : listVINs) {

            this.adminPortletHomepage.clickVehicleOverview();

            Thread.sleep(2000);

            this.vehicleOverviewPage.searchForVin(VIN_02);

            Thread.sleep(5000);

            this.vehicleOverviewPageSpecificVIN.clickActiveCampaignPanelButtonLoad();

            Thread.sleep(10000); // waiting for loaded data row, not single "No records found." row!

            List<WebElement> activeCampaigns = this.vehicleOverviewPageSpecificVIN.getActiveCampaignRows();

            boolean found = false;

            for (WebElement campaign : activeCampaigns) {

                String text = campaign.getText().trim();

                if (text.contains(this.UNIQUE_ID_02)) {
                    found = true;
                }

            }

            if (! found) {
                throw new Exception("No active campaign found for ReCall '" + this.UNIQUE_ID_02 + "' and VIN '" + VIN_02 + "' in VCSO satust '" + ConstantsVehicleManagement.VCSO_01 + "'.");
            }

//    	}

        this.adminPortletHomepage.clickLinkLogout();

        /*
         * End of test preparation
         */

        /*
         * Begin test step 1
         */

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

//    	this.importReCall();

        // for VIN_01/UNIQUE_ID_01: loading with true as preparation
        this.importReCall("20170803");

        // for VIN_01/UNIQUE_ID_01: loading with false for testing
        this.importReCall("20270803");

        /*
         *
         */

        this.listVINs.add(VIN_01);

        this.actionsOnupVehicleOverview.openVehicleOverview(this.adminPortletHomepage);

        this.actionsOnupVehicleOverview.initVehicleOverviewQueuesOfVINs(
                this.listVINs,
                this.adminPortletHomepage,
                this.vehicleOverviewPage,
                this.vehicleOverviewPageSpecificVIN);

        for (String vin : listVINs) {

            this.actionsOnupVehicleOverview.setVehicleVinOptions(
                    this.adminPortletHomepage,
                    this.vehicleOverviewPage,
                    this.vehicleOverviewPageSpecificVIN,
                    vin,
                    true,
                    false,
                    false,
                    true);

        }

        this.actionsOnupVehicleOverview.openExceptionList(this.adminPortletHomepage);

        for (String vin : listVINs) {

            this.exceptionListOverviewPage.searchExceptionListItem(vin);

            OverlayHandler.waitingForOverlay(this.getWebDriverWait());

            // explicitely first row of table
            String entryText = this.exceptionListOverviewPage.getRowsExceptionElements().get(0).getText().trim();

            assertFalse(entryText.contains(vin), "VIN found in Exception List: '" + vin + "', but shouldn't be found.");

        }

        this.adminPortletHomepage.clickLinkLogout();

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

        //
        //this.logStepPassed(); // 1

        // ignored, as Service42 is created with test step 3
        //this.logStepPassed(); // 2

        this.actionsOnupOruCampaign.createOruCampaignWithImportedReCall(
                this.adminPortletHomepage,
                this.manualService42SystemPage,
                this.addService42SystemModal,
                this.batchInfo,
                AM_CODE, // UNIQUE_ID_01
                PATH_TO_FILE_TO_BE_UPLOADED,
                RECALL_ID, // UNIQUE_ID_01
                this.getWebDriver());

        //
        //this.logStepPassed(); // 3

        this.adminPortletHomepage.clickVehicleAdministrationList();

        for (String vin : listVINs) {

            this.adminPortletHomepage.clickVehicleOverview();

            Thread.sleep(2000);

            this.vehicleOverviewPage.searchForVin(vin);

            Thread.sleep(5000);

            this.vehicleOverviewPageSpecificVIN.clickPendingCampaignPanelButtonLoad();

            Thread.sleep(10000); // waiting for loaded data row, not single "No records found." row!

            List<WebElement> pendingCampaigns = this.vehicleOverviewPageSpecificVIN.getPendingCampaignRows();

            found = false;
            String text = "";

            for (WebElement campaign : pendingCampaigns) {

                text = campaign.getText().trim();

                if (text.contains(ConstantsVehicleManagement.VCSO_01) && text.contains(this.UNIQUE_ID_01)) {
                    found = true;
                }

            }

            if (! found) {
                throw new Exception("No pending campaign found for ReCall '" + this.UNIQUE_ID_01 + "' and VIN '" + vin + "' in VCSO satust '" + ConstantsVehicleManagement.VCSO_01 + "' (" + text + ").");
            }

        }

        //
        //this.logStepPassed(); // 4

        this.actionsOnupOruCampaign.searchAndOpenCampaign(
                this.adminPortletHomepage,
                this.campaignManagementSearch,
                this.UNIQUE_ID_01,
                this.getWebDriver());

        Thread.sleep(5000);

        this.actionsOnupOruCampaignBatches.moveVINsToDefaultBatch(
                this.getWebDriver(),
                this.getWebDriverWait(),
                this.inboxCampaignCreation);

//    	new ScreenshotsShutterbug().takeScreenshot("01", ".", this.getWebDriver());

        this.inboxCampaignCreation.clickCheckBoxApproved();

//    	new ScreenshotsShutterbug().takeScreenshot("02", ".", this.getWebDriver());

        /*trial*/
        this.actionsOnupOruCampaignBatches.approveDefaultBatchOfCampaign(
                this.UNIQUE_ID_01,
                this.adminPortletHomepage,
                this.campaignManagementSearch,
                this.batchInfo,
                this.getWebDriver());
        /*trial end*/

        //
        //this.logStepPassed(); // 5

        this.actionsOnupVehicleOverview.openVehicleOverview(this.adminPortletHomepage);

//    	for (String vin : listVINs) {

            this.adminPortletHomepage.clickVehicleOverview();

            Thread.sleep(2000);

            this.vehicleOverviewPage.searchForVin(VIN_01);
//    		this.vehicleOverviewPage.searchForVin(vin);

            Thread.sleep(5000);

            this.vehicleOverviewPageSpecificVIN.clickActiveCampaignPanelButtonLoad();

            Thread.sleep(10000); // waiting for loaded data row, not single "No records found." row!

            activeCampaigns = this.vehicleOverviewPageSpecificVIN.getActiveCampaignRows();

            found = false;

            String text = "";

            for (WebElement campaign : activeCampaigns) {

                text = campaign.getText().trim();

                if (text.contains(ConstantsVehicleManagement.VCSO_05) && text.contains(this.UNIQUE_ID_01)) {
                    found = true;
                }

            }

            if (! found) {
                throw new Exception(
                        "No active campaign found for ReCall '" + this.UNIQUE_ID_01 +
                        "' and VIN '" + VIN_01 + "' in VCSO status '" +
                                ConstantsVehicleManagement.VCSO_01 + "' (" + text + ").");
            }

//    	}

        //
        //this.logStepPassed(); // 6

//    	for (String vin : listVINs) {

            this.adminPortletHomepage.clickVehicleOverview();

            Thread.sleep(2000);

            this.vehicleOverviewPage.searchForVin(VIN_02);
//    		this.vehicleOverviewPage.searchForVin(vin);

            Thread.sleep(5000);

            this.vehicleOverviewPageSpecificVIN.clickScheduledCampaignPanelButtonLoad();

            Thread.sleep(10000); // waiting for loaded data row, not single "No records found." row!

            List<WebElement> scheduledCampaigns = this.vehicleOverviewPageSpecificVIN.getScheduledCampaignRows();

            new ScreenshotsShutterbug().takeScreenshot(this.getWebDriver());

            found = false;

            text = "";

            for (WebElement campaign : scheduledCampaigns) {

                text = campaign.getText().trim();

                if (text.contains(ConstantsVehicleManagement.VCSO_05) && text.contains(this.UNIQUE_ID_01)) {
                    found = true;
                }

            }

            new ScreenshotsShutterbug().takeScreenshot(this.getWebDriver());

            if (! found) {
                throw new Exception(
                        "No active campaign found for ReCall '" +
                                this.UNIQUE_ID_01 + "' and VIN '" + VIN_02 +
                                "' in VCSO satust '" + ConstantsVehicleManagement.VCSO_01 +
                                "' (" + text + ").");
            }

//    	}

        //
        //this.logStepPassed(); // 7
//
//    	ActionsOnupOruCampaignBatches.approveDefaultBatchOfCampaign(
//    			this.UNIQUE_ID_01,
//    			this.adminPortletHomepage,
//    			this.campaignManagementSearch,
//    			this.batchInfo,
//    			this.getWebDriver());

        //
        //this.logStepPassed(); // 8

    }

    /**
     * @throws IOException
     * @throws Exception
     * @throws InterruptedException
     */
    private void importReCall(String rolloutDate) throws IOException, Exception, InterruptedException {

        String template_content;
        String content;
        Response response;
        String jsonResponse;
        int statusCode;

        template_content = this.hclrc.loadJsonContentTemplate(
                "/selenium.gui.testing/data/importReCall/recall-ouqa-2494-minimal-data-record.json");

        // Changes data to VIN_01 (= BAUSLZ4N819805040)
        content = template_content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, this.UNIQUE_ID_01);
        content = content.replace(ConstantsReCallImports.DST01_APPROVAL_ROLLOUT, rolloutDate);

        /*
         * Sending JSON document with fault code to Mock-/REST-Server
         */

        response = this.crs.doPostApplicationJson(
                ConstantsRestServerCommunication.BASEURL_ReCall_POST_CORRECT_RECALL_JSON,
                content);

        jsonResponse = response.readEntity(String.class);
        statusCode = response.getStatus();

        if (200 != statusCode) {
                   throw new Exception("ReCall ID '" + this.UNIQUE_ID_01 + "': POST of JSON resulted in HTTP status code " + statusCode +
                   " (" + jsonResponse + "), but expected was HTTP status code 200.");
        }

        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);
    }

    @Override
    public void tearDownHook() {

        this.actionsOnupVehicleOverview.initVehicleOverviewQueuesOfVINs(
                this.listVINs,
                this.adminPortletHomepage,
                this.vehicleOverviewPage,
                this.vehicleOverviewPageSpecificVIN);

    }

    /**
     *
     */
    private void initialiseWebPages() {

        this.adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());

        this.inboxPage = new InboxPage(this.getWebDriver());
        this.campaignManagementSearch = new CampaignManagementSearch(this.getWebDriver());
        this.campaignManagementDisplayCampaign = new CampaignManagementDisplayCampaign(this.getWebDriver());
        this.inboxCampaignCreation = new InboxCampaignCreation(this.getWebDriver());
        this.dialogueInspectVehicles = new DialogueInspectVehicles(this.getWebDriver());

        this.batchInfo = new Batch_info(this.getWebDriver());

        this.exceptionListOverviewPage = new ExceptionListOverviewPage(this.getWebDriver());

        this.vehicleOverviewPage = new VehicleOverviewPage(this.getWebDriver());
        this.vehicleOverviewPageSpecificVIN = new VehicleOverviewPageSpecificVIN(this.getWebDriver());

        this.manualService42SystemPage = new ManualService42SystemPage(this.getWebDriver());
        this.addService42SystemModal = new AddService42SystemModal(this.getWebDriver());

    }

}