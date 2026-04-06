package testDev.templates.tbe.q42020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebElement;

import de.sulzer.oudcampaign.CampaignIdOUDFileBased;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.campaignmanagement.inbox.InboxCampaignCreation;
import de.sulzer.pages.caradministration.VehicleOverviewPage;
import de.sulzer.pages.caradministration.VehicleOverviewPageSpecificVIN;
import de.sulzer.pages.oruoverviewpage.Batch_info;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.utils.guistandardfunctions.ActionsOnupOruCampaign;
import de.sulzer.utils.guistandardfunctions.ActionsOnupOruCampaignBatches;
import de.sulzer.utils.guistandardfunctions.ActionsOnupVehicleOverview;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import testCases.util.ConstantsReCallImports;
import testCases.util.ConstantsRestServerCommunication;
import testCases.util.ConstantsVehicleManagement;
import testCases.util.HelperClassFaultyReCalls;
import testFramework.AbstractStandardBehaviour;
import testFramework.connectionMockRestServer.CommunicatonRestServer;
import testFramework.constants.ConstantsCommunicationRestServer;
import testFramework.constants.ValuesGlobal;

/**
 * @author Sulzer GmbH
 *
 */
@Tag("OUQA-2495")
@Tag("WC")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("Sequentiell")
@Tag("20Q3")
@Tag("Mock")
public class OUQA_2495_ReCall_approval_is_not_given extends AbstractStandardBehaviour {

    private AdminPortletHomepage adminPortletHomepage;

    private CampaignManagementSearch campaignManagementSearch;
    private InboxCampaignCreation inboxCampaignCreation;

    private Batch_info batchInfo;

    private VehicleOverviewPage vehicleOverviewPage;
    private VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN;

    private ManualService42SystemPage manualService42SystemPage;
    private AddService42SystemModal addService42SystemModal;

    private String UNIQUE_ID_01 = "";
    private String UNIQUE_ID_02 = "";

    private static final String VIN_01 = "BAUSLZ4N819805038";
    private static final String VIN_02 = "BAUSLZ4N819805039";

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
            this.initTestContainer(6); // test has 6 test steps
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

        this.listVINs.add(VIN_01);
//    	this.listVINs.add(VIN_02);

        // loading with true as preparation
        this.importReCall(ConstantsReCallImports.ORU_RELEVANCE_TRUE);

        // loading with false for testing
        this.importReCall(ConstantsReCallImports.ORU_RELEVANCE_FALSE);

        /*
         *
         */

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

        this.actionsOnupVehicleOverview.openVehicleOverview(this.adminPortletHomepage);

        this.actionsOnupVehicleOverview.initVehicleOverviewQueuesOfVINs(
                this.listVINs,
                this.adminPortletHomepage,
                this.vehicleOverviewPage,
                this.vehicleOverviewPageSpecificVIN);

        this.actionsOnupVehicleOverview.setVehicleVinOptions(
                this.adminPortletHomepage,
                this.vehicleOverviewPage,
                this.vehicleOverviewPageSpecificVIN,
                VIN_01,
                false,
                false,
                false,
                false);

        this.adminPortletHomepage.clickLinkLogout();

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

        //
        //this.logStepPassed(); // 1

        //
        //this.logStepPassed(); // 2

        this.actionsOnupOruCampaign.createOruCampaignWithImportedReCall(
                this.adminPortletHomepage,
                this.manualService42SystemPage,
                this.addService42SystemModal,
                this.batchInfo,
                AM_CODE,
                PATH_TO_FILE_TO_BE_UPLOADED,
                RECALL_ID,
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

            boolean found = false;

            for (WebElement campaign : pendingCampaigns) {

                String text = campaign.getText().trim();

                if (text.contains(ConstantsVehicleManagement.VCSO_01) && text.contains(this.UNIQUE_ID_01)) {
                    found = true;
                }

            }

            if (! found) {
                throw new Exception("No pending campaign found for ReCall '" + this.UNIQUE_ID_01 + "' and VIN '" + vin + "' in VCSO satust '" + ConstantsVehicleManagement.VCSO_01 + "'.");
            }

        }

        //
        //this.logStepPassed(); // 4

        this.actionsOnupOruCampaign.searchAndOpenCampaign(
                this.adminPortletHomepage,
                this.campaignManagementSearch,
                this.UNIQUE_ID_01,
                this.getWebDriver());

        this.actionsOnupOruCampaignBatches.moveVINsToDefaultBatch(
                this.getWebDriver(),
                this.getWebDriverWait(),
                this.inboxCampaignCreation);

        this.inboxCampaignCreation.clickCheckBoxApproved();

        //
        //this.logStepPassed(); // 5

        this.actionsOnupVehicleOverview.openVehicleOverview(this.adminPortletHomepage);

        for (String vin : listVINs) {

            this.adminPortletHomepage.clickVehicleOverview();

            Thread.sleep(2000);

            this.vehicleOverviewPage.searchForVin(vin);

            Thread.sleep(5000);

            this.vehicleOverviewPageSpecificVIN.clickActiveCampaignPanelButtonLoad();

            Thread.sleep(10000); // waiting for loaded data row, not single "No records found." row!

            List<WebElement> pendingCampaigns = this.vehicleOverviewPageSpecificVIN.getActiveCampaignRows();

            boolean found = false;

            for (WebElement campaign : pendingCampaigns) {

                String text = campaign.getText().trim();

                if (text.contains(ConstantsVehicleManagement.VCSO_01) && text.contains(this.UNIQUE_ID_01)) {
                    found = true;
                }

            }

            if (! found) {
                throw new Exception("No active campaign found for ReCall '" + this.UNIQUE_ID_01 + "' and VIN '" + vin + "' in VCSO satust '" + ConstantsVehicleManagement.VCSO_01 + "'.");
            }

        }

        //
        //this.logStepPassed(); // 6

    }

    /**
     * @throws IOException
     * @throws Exception
     * @throws InterruptedException
     */
    private void importReCall(String booleanValue) throws IOException, Exception, InterruptedException {

        String template_content = this.hclrc.loadJsonContentTemplate(
                "/selenium.gui.testing/data/importReCall/recall-ouqa-2495-minimal-data-record.json");

        // Changes data to VIN_01 (= BAUSLZ4N819805036)
        String content = template_content.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, this.UNIQUE_ID_01);
        content = content.replace(ConstantsReCallImports.PLACEHOLDER_DST4_RELEVANCE, booleanValue);

        /*
         * Sending JSON document with fault code to Mock-/REST-Server
         */

        Response response = this.crs.doPostApplicationJson(
                ConstantsRestServerCommunication.BASEURL_ReCall_POST_CORRECT_RECALL_JSON,
                content);

        String jsonResponse = response.readEntity(String.class);
        int statusCode = response.getStatus();

        if (200 != statusCode) {
                   throw new Exception("ReCall ID '" + this.UNIQUE_ID_01 + "': POST of JSON resulted in HTTP status code " + statusCode +
                   " (" + jsonResponse + "), but expected was HTTP status code 200.");
        }

        Thread.sleep(ConstantsCommunicationRestServer.THREE_MINUTES);

    }

    @Override
    public void tearDownHook() {

    }

    /**
     *
     */
    private void initialiseWebPages() {

        this.adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());

        this.campaignManagementSearch = new CampaignManagementSearch(this.getWebDriver());
        this.inboxCampaignCreation = new InboxCampaignCreation(this.getWebDriver());

        this.batchInfo = new Batch_info(this.getWebDriver());

        this.vehicleOverviewPage = new VehicleOverviewPage(this.getWebDriver());
        this.vehicleOverviewPageSpecificVIN = new VehicleOverviewPageSpecificVIN(this.getWebDriver());

        this.manualService42SystemPage = new ManualService42SystemPage(this.getWebDriver());
        this.addService42SystemModal = new AddService42SystemModal(this.getWebDriver());

    }

}