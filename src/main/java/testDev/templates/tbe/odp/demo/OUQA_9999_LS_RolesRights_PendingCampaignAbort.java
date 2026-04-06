package testDev.templates.tbe.odp.demo;

import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.NoSuchElementException;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.caradministration.VehicleOverviewPage;
import de.sulzer.pages.caradministration.VehicleOverviewPageSpecificVIN;
import de.sulzer.pages.genericelements.PageWithNavigation;
import de.sulzer.pages.useradministration.UserOverview;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import de.sulzer.pages.util.constants.ConstantsRoles;
import de.sulzer.utils.guistandardfunctions.ActionsOnupOruCampaign;
import de.sulzer.utils.guistandardfunctions.ActionsOnupUserOverview;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import de.sulzer.utils.guistandardfunctions.VOFlashAssignment;
import testFramework.constants.ValuesRolesRights;
import testFramework.StartClass;
import testFramework.AbstractStandardBehaviour;

public class OUQA_9999_LS_RolesRights_PendingCampaignAbort extends AbstractStandardBehaviour {

    private AdminPortletHomepage _adminPortletHomepage;
    private UserOverview _userOverview;
    private VehicleOverviewPage _vehicleOverviewPage;
    private VehicleOverviewPageSpecificVIN _vehicleOverviewPageSpecificVIN;
    private CampaignManagementSearch _searchPage;
    private PageWithNavigation _pageWithNav;

    private static final String VIN = "BAUSLZ8YZ19112517";

    private static final String CAMPAIGN_PC = ValuesRolesRights.VIN_CAMPAIGN_NR_PENDING_ABORT;
    private static final String PENDING_CAMPAIGNS = "Pending Campaigns";
    private static final String RECALL_ID = ValuesRolesRights.RECALL_ID_PENDING_ABORT;
    private static final String AM_CODE = ValuesRolesRights.AMCODE_PENDING_ABORT;
    private static final String TRANSLATION_OUTPUT1 = "Test456";

//    private static final String VIN = ValuesRolesRights.VIN_PENDING;
//
//    private static final String CAMPAIGN_PC = ValuesRolesRights.VIN_CAMPAIGN_NR_PENDING_ABORT;
//    private static final String PENDING_CAMPAIGNS = "Pending Campaigns";
//    private static final String RECALL_ID = ValuesRolesRights.RECALL_ID_PENDING_ABORT;
//    private static final String AM_CODE = ValuesRolesRights.AMCODE_PENDING_ABORT;
//    private static final String TRANSLATION_OUTPUT1 = "Test456";

    private ActionsOnupUserOverview changeUserRolesRights = null;

    private ActionsOnupOruCampaign actionsOnupOruCampaign;

    @Override
    @org.junit.jupiter.api.BeforeEach
    protected void setUp() throws Throwable {

        try {
            super.setUp();
        } catch (Throwable e) {
            this.setThrowable(e);
            throw e;
        } finally {
            this.initTestContainer(11); // test has 11 test steps
        }
    }

    @Override
    protected void testHook() throws Throwable {

        this.changeUserRolesRights = new ActionsOnupUserOverview();

        this.actionsOnupOruCampaign = new ActionsOnupOruCampaign(getWebDriver());

        // Roles & Rights
        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());
        this._adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());
        _userOverview = new UserOverview(this.getWebDriver());

        // Assigning root role to the user
        this.changeUserRolesRights.changeUserRoleToRoot(this.getWebDriver(), this._adminPortletHomepage, this._userOverview, StartClass.userName);
//        ConvienentActionsInONUP.changeUserRoleToRoot(this._adminPortletHomepage, this._userOverview, StartClass.userName);
        //_adminPortletHomepage.clickLinkLogout();
        this.getWebDriver().get("https://mbbonupadmin.moria.dmo.apl.eu.dp.odp.cloud.vwgroup.com/onup-admin/logout");

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());
        _adminPortletHomepage.clickCampaignManagement();
        _adminPortletHomepage.clickCampaignManagementInbox();
        Thread.sleep(6000);

        // call method to create ORU campaign
        this.actionsOnupOruCampaign.createORUCampaignWith_cGW2_FlashMedium(
                RECALL_ID,
                AM_CODE,
                this.getWebDriver(),
                TRANSLATION_OUTPUT1,
                TRANSLATION_OUTPUT1,
                new VOFlashAssignment());

        // ****** Remove all roles for the user and assign particular role
        _adminPortletHomepage.clickUserManagement();
        _adminPortletHomepage.clickUserOverview();
        this._userOverview.selectUserById(StartClass.userName);
        //this.logStepPassed();// Step - 1
        _userOverview.deselectAllRoles();
        //this.logStepPassed();// Step - 2
        this._userOverview.selectRole(ConstantsRoles.OU_ROLE_SULZER_VO_PEND_CAMP_ABORT);
        //this.logStepPassed();// Step - 3
        _userOverview.closeModal();

        // ***** logout and login again ******//
        _adminPortletHomepage.clickLinkLogout();

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());
        //this.logStepPassed();// Step - 4
        // click on vehicle administration menu
        this._adminPortletHomepage.clickVehicleAdministrationList();

        // click on Vehicle Overview
        _adminPortletHomepage.clickVehicleOverview();
        //this.logStepPassed();// Step - 5
        // Search VIN
        this._vehicleOverviewPage = new VehicleOverviewPage(this.getWebDriver());
        this._vehicleOverviewPage.searchForVin(VIN);
        //this.logStepPassed();// Step - 6

        // Verify Pending Campaigns panel header
        this._vehicleOverviewPageSpecificVIN = new VehicleOverviewPageSpecificVIN(getWebDriver());
        assertTrue(this._vehicleOverviewPageSpecificVIN.getPendingCampaignHeader().getText().trim()
                .equals(PENDING_CAMPAIGNS));
        //this.logStepPassed();// Step - 7

        // Verify LOAD button for Pending Campaign
        assertTrue(this._vehicleOverviewPageSpecificVIN.getPendingCampaignPanelButtonLoad().isEnabled());

        // Click Pending Campaign Load
        this._vehicleOverviewPageSpecificVIN.clickPendingCampaignPanelButtonLoad();
        //this.logStepPassed();// Step - 8
        Thread.sleep(2000);

        // checking, if Campaign is available in PENDING CAMPAIGNS
        if (this._vehicleOverviewPageSpecificVIN.isPendingCampaignAvailable(CAMPAIGN_PC)) {
            this._vehicleOverviewPageSpecificVIN.clickPendingCampaignDetailsLink(CAMPAIGN_PC);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this._vehicleOverviewPageSpecificVIN.clickCloseButtonDetailsDialog();
        } else {
            try {
                throw new Exception(CAMPAIGN_PC + " was not available in Pending Campaigns list");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //this.logStepPassed();// Step - 9

        // Checking if Cancel button is enabled and clicked
        assertTrue(this._vehicleOverviewPageSpecificVIN.getPendingCampaignCancelLink(CAMPAIGN_PC).isEnabled());
        this._vehicleOverviewPageSpecificVIN.getPendingCampaignCancelLink(CAMPAIGN_PC).click();
        //this.logStepPassed(); // Step - 10

        // verify all other functionalities don't work
        // setting time for implicitely waiting time (otherwise test duration is too long)
        this.getWebDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);


        // // vehicle management - sub menu 'Template Management'
        try {
            this._vehicleOverviewPage.clickExceptionListManagement();
            throw new RuntimeException("Driver was able to click menu 'Template Management'");
        } catch (NoSuchElementException e) {
        }
        // // vehicle management - sub menu 'Exception List Management'
        try {
            this._vehicleOverviewPage.clickTemplateManagement();
            throw new RuntimeException("Driver was able to click menu 'Exception List Management'");
        } catch (NoSuchElementException e) {
        }
        // setting time for implicitely waiting time (otherwise test duration is too long)
        this.getWebDriver().manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        // // campaign management
        try {
            this._adminPortletHomepage.clickCampaignManagement();
            throw new RuntimeException("Driver was able to click menu 'Campaign Management'");
        } catch (NoSuchElementException e) {
        }
        // // testing menu
        try {
            this._adminPortletHomepage.clickTestingMenu();
            throw new RuntimeException("Driver was able to click menu 'Testing menu'");
        } catch (NoSuchElementException e) {
        }
        // // sector log
        try {
            this._adminPortletHomepage.clickSectorLog();
            throw new RuntimeException("Driver was able to click menu 'Sector Log'");
        } catch (NoSuchElementException e) {
        }
        // // documentation
        try {
            this._adminPortletHomepage.clickDocumentation();
            throw new RuntimeException("Driver was able to click menu 'Documentation'");
        } catch (NoSuchElementException e) {
        }
        //this.logStepPassed(); // Step - 11
        // setting time for implicitely waiting time (otherwise test duration is too long)
        this.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Override
    public void tearDownHook() {

        // Assign root role to the user and delete the created ORU campaign
        this.changeUserRolesRights.changeUserRoleToRoot(this._adminPortletHomepage, this._userOverview, StartClass.userName);
        this._adminPortletHomepage.clickLinkLogout();

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());
        this._adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());

        // click campaign management menu
        _adminPortletHomepage.clickCampaignManagement();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        this._pageWithNav = new PageWithNavigation(this.getWebDriver());

        // click search menu under campaign management menu
        _pageWithNav.clickCampaignManagementSearch();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        this._searchPage = new CampaignManagementSearch(this.getWebDriver());

        // search for created campaign
        _searchPage.searchCampaign(RECALL_ID);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        assertEquals(RECALL_ID, this._searchPage.getCampaignTitle().getText());
        assertEquals(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS, this._searchPage.getNumberOfEntriesParagraphText());

        // Delete the campaign
        this._searchPage.clickFirstButtonDelete();
        this._searchPage.clickConfirmDelete();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        this._adminPortletHomepage.clickLinkLogout();

    }
}
