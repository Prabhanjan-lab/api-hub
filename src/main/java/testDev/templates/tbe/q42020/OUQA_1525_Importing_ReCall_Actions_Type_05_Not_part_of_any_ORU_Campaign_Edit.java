package testDev.templates.tbe.q42020;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Tag;
import org.openqa.selenium.WebElement;

import de.sulzer.oudcampaign.CampaignIdOUDFileBased;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.blockingdialogues.overlay.OverlayHandler;
import de.sulzer.pages.caradministration.VehicleOverviewPage;
import de.sulzer.pages.caradministration.VehicleOverviewPageSpecificVIN;
import de.sulzer.pages.caradministration.VehicleOverviewSpecificVinAttributes;
import de.sulzer.pages.oruoverviewpage.Batch_info;
import de.sulzer.pages.sectorlog.LoggingDetail;
import de.sulzer.pages.sectorlog.LoggingOverview;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.utils.guistandardfunctions.ActionsOnupVehicleOverview;
import de.sulzer.utils.guistandardfunctions.ActionsSectorLog;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import testCases.util.ConstantsReCallImports;
import testCases.util.ConstantsRestServerCommunication;
import testCases.util.ConstantsSectorLog;
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
@Tag("OUQA-1525")
@Tag("WC")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("Sequentiell")
@Tag("20Q3")
@Tag("Mock")
public class OUQA_1525_Importing_ReCall_Actions_Type_05_Not_part_of_any_ORU_Campaign_Edit extends AbstractStandardBehaviour {

	private AdminPortletHomepage adminPortletHomepage;

	private Batch_info batchInfo;

	private VehicleOverviewPage vehicleOverviewPage;
	private VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN;
	private VehicleOverviewSpecificVinAttributes vehicleOverviewSpecificVinAttributes;

//	private ExceptionListOverviewPage exceptionListOverviewPage;
//	private EditExceptionList editExceptionList;

	private ManualService42SystemPage manualService42SystemPage;
    private AddService42SystemModal addService42SystemModal;

    private LoggingOverview loggingOverview;
    private LoggingDetail loggingDetail;

	// System42 entries
	private static String AM_CODE = "";
	//
	private static final String PATH_TO_FILE_TO_BE_UPLOADED = ValuesGlobal.MIB2;

	// ReCall entries
	private static String RECALL_ID = "";

	private String UNIQUE_ID = "";

	private static final String VIN = "BAUSLZ4N819805012";

	private static final String A_MODEL_MBV = "FV";
	private static final String A_MODEL_YEAR = "2017";

	private static final String A_DEALER_COUNTRY_ATTENDING = "DEU";
	private static final String A_DEALER_IMPORTER_ATTENDING = "678";
	private static final String A_DEALER_DEALER_ATTENDING = "12345";

	private static final String A_DEALER_COUNTRY_DELIVERY = "GBR";
	private static final String A_DEALER_IMPORTER_DELIVERY = "789";
	private static final String A_DEALER_DEALER_DELIVERY = "23456";

	private static final String A_DEALER_COUNTRY_ORDER = "LAO";
	private static final String A_DEALER_IMPORTER_ORDER = "890";
	private static final String A_DEALER_DEALER_ORDER = "34567";

	private static final String A_PKN_WEEK = "2";
	private static final String A_PKN_DAY = "3";
	private static final String A_PKN_PLANT = "4";
	private static final String A_PKN_YEAR = "2015";

	private static final String A_EQUIPMENT = "7UG";
//	private static final String A_EQUIPMENT = "K8C";

	private static final String B_MODEL_MBV = "FV";
	private static final String B_MODEL_YEAR = "2018";

	private static final String B_DEALER_COUNTRY_ATTENDING = "AUS";
	private static final String B_DEALER_IMPORTER_ATTENDING = "789";
	private static final String B_DEALER_DEALER_ATTENDING = "23456";

	private static final String B_DEALER_COUNTRY_DELIVERY = "KGZ";
	private static final String B_DEALER_IMPORTER_DELIVERY = "678";
	private static final String B_DEALER_DEALER_DELIVERY = "12345";

	private static final String B_DEALER_COUNTRY_ORDER = "SJM";
	private static final String B_DEALER_IMPORTER_ORDER = "567";
	private static final String B_DEALER_DEALER_ORDER = "01234";

	private static final String B_PKN_CONCATENATED = "034052016";

	private static final String B_PKN_WEEK = "3";
	private static final String B_PKN_DAY = "4";
	private static final String B_PKN_PLANT = "5";
	private static final String B_PKN_YEAR = "2016";

	private static final String B_EQUIPMENT = "X9X";
//	private static final String B_EQUIPMENT = "K8J";

	//
	private CommunicatonRestServer communicatonRestServer = null;
	private HelperClassFaultyReCalls helperClassFaultyReCalls = null;
	private SectorLogChecking sectorLogChecking = null;

	private ActionsSectorLog actionsSectorLog = null;
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
			this.initTestContainer(5); // test has 5 test steps
		}

    }

    @Override
	protected void testHook() throws Throwable {

    	this.initialiseWebPages();

    	this.communicatonRestServer = new CommunicatonRestServer();
    	this.helperClassFaultyReCalls = new HelperClassFaultyReCalls();
    	this.sectorLogChecking = new SectorLogChecking(this.getWebDriver());
    	this.actionsSectorLog = new ActionsSectorLog(getWebDriver());
    	this.actionsOnupVehicleOverview = new ActionsOnupVehicleOverview(getWebDriver());

    	this.UNIQUE_ID = CampaignIdOUDFileBased.getInstance().ascertainNewId();
    	AM_CODE = this.UNIQUE_ID;
    	RECALL_ID = this.UNIQUE_ID;

    	String content_template = this.helperClassFaultyReCalls.loadJsonContentTemplate(
    			"/selenium.gui.testing/data/importReCall/recall-ouqa-1525-minimal-data-record.json");

    	System.out.println("READ ID: " + this.UNIQUE_ID);

    	content_template = content_template.replace(ConstantsReCallImports.PLACEHOLDER_UNIQUE_ID, this.UNIQUE_ID);
    	content_template = content_template.replaceAll(ConstantsReCallImports.PLACEHOLDER_VIN, VIN);

    	String content = content_template.replace(ConstantsReCallImports.MODEL_MBV, A_MODEL_MBV);
    	content = content.replace(ConstantsReCallImports.MODEL_YEAR, A_MODEL_YEAR);

    	content = content.replace(ConstantsReCallImports.PKN_WEEK, A_PKN_WEEK);
    	content = content.replace(ConstantsReCallImports.PKN_DAY, A_PKN_DAY);
    	content = content.replace(ConstantsReCallImports.PKN_PLANT, A_PKN_PLANT);
    	content = content.replace(ConstantsReCallImports.PKN_YEAR, A_PKN_YEAR);

    	content = content.replace(ConstantsReCallImports.DEALER_COUNTRY_ATTENDING, A_DEALER_COUNTRY_ATTENDING);
    	content = content.replace(ConstantsReCallImports.DEALER_IMPORTER_ATTENDING, A_DEALER_IMPORTER_ATTENDING);
    	content = content.replace(ConstantsReCallImports.DEALER_DEALER_ATTENDING, A_DEALER_DEALER_ATTENDING);

    	content = content.replace(ConstantsReCallImports.DEALER_COUNTRY_DELIVERY, A_DEALER_COUNTRY_DELIVERY);
    	content = content.replace(ConstantsReCallImports.DEALER_IMPORTER_DELIVERY, A_DEALER_IMPORTER_DELIVERY);
    	content = content.replace(ConstantsReCallImports.DEALER_DEALER_DELIVERY, A_DEALER_DEALER_DELIVERY);

    	content = content.replace(ConstantsReCallImports.DEALER_COUNTRY_ORDER, A_DEALER_COUNTRY_ORDER);
    	content = content.replace(ConstantsReCallImports.DEALER_IMPORTER_ORDER, A_DEALER_IMPORTER_ORDER);
    	content = content.replace(ConstantsReCallImports.DEALER_DEALER_ORDER, A_DEALER_DEALER_ORDER);

    	content = content.replace(ConstantsReCallImports.EQUIPMENT, A_EQUIPMENT);

    	/*
    	 * Sending JSON document with fault code to Mock-/REST-Server
    	 */

    	Response response = this.communicatonRestServer.doPostApplicationJson(
    			ConstantsRestServerCommunication.BASEURL_ReCall_POST_CORRECT_RECALL_JSON,
    			content);

    	if (response.getStatus() != 200) {
				   throw new Exception("POST of JSON resulted in HTTP status code " + response.getStatus() +
				   " (" + response.readEntity(String.class) + "), but expected was HTTP status code 200.");
    	}

		// handling timing problems with file creation which hast to be tested
		Thread.sleep(ConstantsCommunicationRestServer.FIVE_MINUTES);

//		ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());
//
//        ActionsOnupOruCampaign.createOruCampaignWithImportedReCall(
//        		this.adminPortletHomepage,
//        		this.manualService42SystemPage,
//        		this.addService42SystemModal,
//        		this.batchInfo,
//        		AM_CODE,
//        		PATH_TO_FILE_TO_BE_UPLOADED,
//        		RECALL_ID,
//        		this.getWebDriver());

		//
        //this.logStepPassed(); // 1

    	content = content_template.replace(ConstantsReCallImports.MODEL_MBV, B_MODEL_MBV);
    	content = content.replace(ConstantsReCallImports.MODEL_YEAR, B_MODEL_YEAR);

    	content = content.replace(ConstantsReCallImports.PKN_WEEK, B_PKN_WEEK);
    	content = content.replace(ConstantsReCallImports.PKN_DAY, B_PKN_DAY);
    	content = content.replace(ConstantsReCallImports.PKN_PLANT, B_PKN_PLANT);
    	content = content.replace(ConstantsReCallImports.PKN_YEAR, B_PKN_YEAR);

    	content = content.replace(ConstantsReCallImports.DEALER_COUNTRY_ATTENDING, B_DEALER_COUNTRY_ATTENDING);
    	content = content.replace(ConstantsReCallImports.DEALER_IMPORTER_ATTENDING, B_DEALER_IMPORTER_ATTENDING);
    	content = content.replace(ConstantsReCallImports.DEALER_DEALER_ATTENDING, B_DEALER_DEALER_ATTENDING);

    	content = content.replace(ConstantsReCallImports.DEALER_COUNTRY_DELIVERY, B_DEALER_COUNTRY_DELIVERY);
    	content = content.replace(ConstantsReCallImports.DEALER_IMPORTER_DELIVERY, B_DEALER_IMPORTER_DELIVERY);
    	content = content.replace(ConstantsReCallImports.DEALER_DEALER_DELIVERY, B_DEALER_DEALER_DELIVERY);

    	content = content.replace(ConstantsReCallImports.DEALER_COUNTRY_ORDER, B_DEALER_COUNTRY_ORDER);
    	content = content.replace(ConstantsReCallImports.DEALER_IMPORTER_ORDER, B_DEALER_IMPORTER_ORDER);
    	content = content.replace(ConstantsReCallImports.DEALER_DEALER_ORDER, B_DEALER_DEALER_ORDER);

    	content = content.replace(ConstantsReCallImports.EQUIPMENT, B_EQUIPMENT);

        //
        //this.logStepPassed(); // 2

        /*
         * Sending JSON document with fault code to Mock-/REST-Server
         */

        response = this.communicatonRestServer.doPostApplicationJson(
        		ConstantsRestServerCommunication.BASEURL_ReCall_POST_CORRECT_RECALL_JSON,
        		content);

        if (response.getStatus() != 200) {
        	throw new Exception("POST of JSON resulted in HTTP status code " + response.getStatus() +
        			" (" + response.readEntity(String.class) + "), but expected was HTTP status code 200.");
        }

		// handling timing problems with file creation which hast to be tested
		Thread.sleep(ConstantsCommunicationRestServer.FIVE_MINUTES);

		ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

        //
        this.actionsSectorLog.openMenuSectorLogLoggingOverview(this.adminPortletHomepage);

		this.loggingOverview.searchLogEntries(ConstantsSectorLog.RECALL);

		OverlayHandler.waitingForOverlay(this.getWebDriverWait());

		List<WebElement> elements = this.loggingOverview.getListLogEntries();

		boolean found = false;

		//
		List<String> searchItems = new ArrayList<String>();
		searchItems.add(this.UNIQUE_ID);

		found = this.sectorLogChecking.searchInLoggingOverviewByCriterias(elements, ConstantsSectorLog.RECALL, ConstantsSectorLog.LOG_OVERVIEW_COL5_ERRORS, searchItems, this.adminPortletHomepage , this.loggingOverview, this.loggingDetail);

		if (found) {
			throw new Exception("Logging errors found for imported ReCall with searchItems '" + searchItems + "'.");
		}

        //
        //this.logStepPassed(); // 3

		elements = this.loggingOverview.getListLogEntries();

		found = false;

		//
		searchItems = new ArrayList<String>();
		searchItems.add(this.UNIQUE_ID);

		found = this.sectorLogChecking.searchInLoggingOverviewByCriterias(elements, ConstantsSectorLog.RECALL, ConstantsSectorLog.LOG_OVERVIEW_COL7_ADAPTED, searchItems, this.adminPortletHomepage , this.loggingOverview, this.loggingDetail);

		if (! found) {
			throw new Exception("No logging details found for imported ReCall with searchItems '" + searchItems + "'.");
		}

        //
        //this.logStepPassed(); // 4

        this.actionsOnupVehicleOverview.checkVinAttributes(
        		this.adminPortletHomepage,
        		this.vehicleOverviewPage,
        		this.vehicleOverviewPageSpecificVIN,
        		this.vehicleOverviewSpecificVinAttributes,
        		VIN,

        		B_MODEL_MBV,
        		B_EQUIPMENT,
        		B_MODEL_YEAR,

        		B_PKN_WEEK,
        		B_PKN_DAY,
        		B_PKN_PLANT,
        		B_PKN_YEAR,

        		B_DEALER_COUNTRY_ATTENDING,
        		B_DEALER_DEALER_ATTENDING,
        		B_DEALER_IMPORTER_ATTENDING,

        		B_DEALER_COUNTRY_DELIVERY,
        		B_DEALER_DEALER_DELIVERY,
        		B_DEALER_IMPORTER_DELIVERY,

        		B_DEALER_COUNTRY_ORDER,
        		B_DEALER_DEALER_ORDER,
        		B_DEALER_IMPORTER_ORDER);

//        ActionsOnupVehicleOverview.openExceptionList(this.adminPortletHomepage);
//
//        this.exceptionListOverviewPage.searchExceptionList(VIN);
//
//        Thread.sleep(3000);
//
//        this.exceptionListOverviewPage.clickEditExceptionList();
//
//        // General Settins tab
//
//        String value = this.editExceptionList.getMbt().getText().trim();
//        assertTrue(
//        		B_MODEL_MBV.equals(value),
//        		"MBV: Found wrong value. Expected '" + B_MODEL_MBV + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getPrn().getText().trim();
//        assertTrue(
//        		B_EQUIPMENT.equals(value),
//        		"PRN: Found wrong value. Expected '" + B_EQUIPMENT + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getModelYear().getText().trim();
//        assertTrue(
//        		B_MODEL_YEAR.equals(value),
//        		"Model Year: Found wrong value. Expected '" + B_MODEL_YEAR + "', but found '" + value + "'.");
//
//        // PKN tab
//
//        this.editExceptionList.clickPknTab();
//
//        value = this.editExceptionList.getCalenderWeek().getText().trim();
//        assertTrue(
//        		B_PKN_WEEK.equals(this.checkCalendarWeek2Digits(value)),
//        		"PKN Week: Found wrong value. Expected '" + B_PKN_WEEK + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getPknDay().getText().trim();
//        assertTrue(
//        		B_PKN_DAY.equals(value),
//        		"PKN Day: Found wrong value. Expected '" + B_PKN_DAY + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getPknFactory().getText().trim();
//        assertTrue(
//        		B_PKN_PLANT.equals(this.checkCalendarWeek2Digits(value)),
//        		"PKN Plant: Found wrong value. Expected '" + B_PKN_PLANT + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getPknFactory().getText().trim();
//        assertTrue(
//        		B_PKN_YEAR.equals(value),
//        		"PKN Year: Found wrong value. Expected '" + B_PKN_YEAR + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getPknConcatenated().getText().trim();
//        assertTrue(
//        		B_PKN_CONCATENATED.equals(value),
//        		"PKN concatenated: Found wrong value. Expected '" + B_PKN_CONCATENATED + "', but found '" + value + "'.");
//
//        // Dealer tab
//
//        this.editExceptionList.clickDealershipsTab();
//
//        // Attending dealer
//
//        this.editExceptionList.clickAttendingTab();
//
//        value = this.editExceptionList.getAttendingCountryCode().getText().trim();
//        assertTrue(
//        		value.contains(B_DEALER_COUNTRY_ATTENDING),
//        		"Attending Dealer Country: Found wrong value. Expected '" + B_DEALER_COUNTRY_ATTENDING + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getAttendingPartnerNumber().getText().trim();
//        assertTrue(
//        		value.equals(B_DEALER_IMPORTER_ATTENDING),
//        		"Attending Dealer Importer/Partnernumber : Found wrong value. Expected '" + B_DEALER_IMPORTER_ATTENDING + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getAttendingWholesaler().getText().trim();
//        assertTrue(
//        		value.equals(B_DEALER_DEALER_ATTENDING),
//        		"Attending Dealer Wholesaler: Found wrong value. Expected '" + B_DEALER_DEALER_ATTENDING + "', but found '" + value + "'.");
//
//        // Delivering dealer
//
//        this.editExceptionList.clickDeliveringTab();
//
//        value = this.editExceptionList.getDeliveringCountryCode().getText().trim();
//        assertTrue(
//        		value.contains(B_DEALER_COUNTRY_DELIVERY),
//        		"Delivering Dealer Country: Found wrong value. Expected '" + B_DEALER_COUNTRY_DELIVERY + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getDeliveringPartnerNumber().getText().trim();
//        assertTrue(
//        		value.equals(B_DEALER_IMPORTER_DELIVERY),
//        		"Delivering Dealer Importer/Partnernumber : Found wrong value. Expected '" + B_DEALER_IMPORTER_DELIVERY + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getDeliveringWholesaler().getText().trim();
//        assertTrue(
//        		value.equals(B_DEALER_DEALER_DELIVERY),
//        		"Delivering Dealer Wholesaler: Found wrong value. Expected '" + B_DEALER_DEALER_DELIVERY + "', but found '" + value + "'.");
//
//        // Ordering dealer
//
//        this.editExceptionList.clickOrderingTab();
//
//        value = this.editExceptionList.getOrderingCountryCode().getText().trim();
//        assertTrue(
//        		value.contains(B_DEALER_COUNTRY_ORDER),
//        		"Ordering Dealer Country: Found wrong value. Expected '" + B_DEALER_COUNTRY_ORDER + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getOrderingPartnerNumber().getText().trim();
//        assertTrue(
//        		value.equals(B_DEALER_IMPORTER_ORDER),
//        		"Ordering Dealer Importer/Partnernumber : Found wrong value. Expected '" + B_DEALER_IMPORTER_ORDER + "', but found '" + value + "'.");
//
//        value = this.editExceptionList.getOrderingWholesaler().getText().trim();
//        assertTrue(
//        		value.equals(B_DEALER_DEALER_ORDER),
//        		"Ordering Dealer Wholesaler: Found wrong value. Expected '" + B_DEALER_DEALER_ORDER + "', but found '" + value + "'.");

        //
        //this.logStepPassed(); // 5

    }

//	/**
//	 * @param value
//	 * @return
//	 */
//	private String checkCalendarWeek2Digits(String value) {
//
//		if (value.length() < 2) {
//			return "0" + value;
//		} else {
//			return value;
//		}
//
//	}

    @Override
	public void tearDownHook() {

	}

	private void initialiseWebPages() {

		this.adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());

		this.batchInfo = new Batch_info(this.getWebDriver());

		this.vehicleOverviewPage = new VehicleOverviewPage(this.getWebDriver());
		this.vehicleOverviewPageSpecificVIN = new VehicleOverviewPageSpecificVIN(this.getWebDriver());
		this.vehicleOverviewSpecificVinAttributes = new VehicleOverviewSpecificVinAttributes(this.getWebDriver());

		this.manualService42SystemPage = new ManualService42SystemPage(this.getWebDriver());
		this.addService42SystemModal = new AddService42SystemModal(this.getWebDriver());

		this.loggingOverview = new LoggingOverview(this.getWebDriver());
		this.loggingDetail = new LoggingDetail(this.getWebDriver());

	}

}