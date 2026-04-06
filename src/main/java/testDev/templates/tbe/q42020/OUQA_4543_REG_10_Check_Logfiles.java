package testDev.templates.tbe.q42020;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.Tag;

import de.sulzer.pages.admintool.login.AdminToolLogin;
import de.sulzer.pages.admintool.login.MBBAdminToolRoleSelection;
import de.sulzer.pages.admintool.login.MBBApplicationSelection;
import de.sulzer.pages.admintool.mainpages.mbbapplicationselectionadminaudi.downloadtracing.DialogueCalendarSelection;
import de.sulzer.pages.admintool.mainpages.mbbapplicationselectionadminaudi.downloadtracing.DownloadTracingSearchCriteria;
import testCases.util.ConstantsDownloadFiles;
import testCases.util.ConstantsRestServerCommunication;
import testFramework.AbstractStandardBehaviour;
import testFramework.connectionMockRestServer.CommunicatonRestServer;

/**
 * @author Sulzer GmbH
 *
 */
@Tag("OUQA-4543")
@Tag("WC")
@Tag("Demo_ECE")
@Tag("Approval_ECE")
@Tag("Sequentiell")
@Tag("20Q2")
@Tag("Mock")
public class OUQA_4543_REG_10_Check_Logfiles extends AbstractStandardBehaviour {

	private AdminToolLogin adminToolLogin;
	private MBBAdminToolRoleSelection mbbAdminToolRoleSelection;
	private DownloadTracingSearchCriteria downloadTracingSearchCriteria;
	private MBBApplicationSelection mbbApplicationSelection;
	private DialogueCalendarSelection dialogueCalendarSelection;

	private final String CAMPAIGN = "TAQ5";
	private final String VIN = "BAUSLZ4N820041533";

    private String downloadDirectory = ConstantsDownloadFiles.DOWNLOAD_DIRECTORY;
    private File downloadFile;

	//
	private CommunicatonRestServer crs = null;

    @Override
    @org.junit.jupiter.api.BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(2); // test has 2 test steps
		}
    }

    @Override
	protected void testHook() throws Throwable {

    	this.crs = new CommunicatonRestServer();

    	/*
    	 * Decision for environment
    	 */
    	String urlAdminTool = "";
		String environment = System.getProperty("environment");

		if (null != environment){
			environment = environment.toUpperCase();
		}

		// Base Demo
		if("DEMO_ECE".equals(environment)){
			urlAdminTool = "https://tui2-mbbsrvadmin04.audi-connect.web.audi.vwg/admintool/signin";
        // Base Approval
        } else if("APPROVAL_ECE".equals(environment)){
        	urlAdminTool = "https://pre2-mbbsrvadmin04.audi-connect.web.audi.vwg/admintool/signin";
       // ODP Demo
        } else if("DEMO_LS".equals(environment)){
        	urlAdminTool = "https://tui2-mbbsrvadmin04.audi-connect.web.audi.vwg/admintool/signin";
        // ODP Approval
        } else if("APPROVAL_LS".equals(environment)){
        	urlAdminTool = "https://pre2-mbbsrvadmin04.audi-connect.web.audi.vwg/admintool/signin";
        } else { // Base Demo
        	urlAdminTool  =
        			"https://tui2-mbbsrvadmin04.audi-connect.web.audi.vwg/admintool/signin";
        }

		System.out.println("Environment -> URL Admin Tool: " + urlAdminTool);

		this.initialiseWebPages();

        /*
    	 * pre-condition check, if test data is tidied up (download file)
         */
		File d = new File(this.downloadDirectory);

        this.downloadFile = new File(this.downloadDirectory + "/auditTrailDownload");

        this.deleteDownloadFile();

		/*
		 * Load login data
		 */

        //
		String loginUsername = System.getProperty("at_user");
        String loginPassword = System.getProperty("at_pw");

		/*
		 * Loading log-in page of Admin Tool
		 */

		this.getWebDriver().get(urlAdminTool);

		Thread.sleep(20000);

		this.adminToolLogin.getInputUsername().clear();
		this.adminToolLogin.getInputUsername().sendKeys(loginUsername);

		this.adminToolLogin.getInputPassword().clear();
		this.adminToolLogin.getInputPassword().sendKeys(loginPassword);

		this.adminToolLogin.clickButtonLogin();

		Thread.sleep(2000);

		//
		this.mbbAdminToolRoleSelection.getSelectDomain().selectByVisibleText("XID_ADMIN_AUDI"); // <-- ==

		this.mbbAdminToolRoleSelection.clickButtonSelection();

		System.out.println("Selection done.");

		//
		this.mbbApplicationSelection.clickLinkDownloadTracing();

		Thread.sleep(3000);

		// entering additional search data
		this.downloadTracingSearchCriteria.clickButtonNewCriterium();

		Thread.sleep(3000);

		this.downloadTracingSearchCriteria.getSelectCriterium().selectByVisibleText("VIN");
		this.downloadTracingSearchCriteria.getInputCriteriumValue().clear();
		this.downloadTracingSearchCriteria.getInputCriteriumValue().sendKeys(VIN);

		// brand already selecte (only 'Audi' selection possible)

		// getting current and earlier date
		LocalDateTime ldtEnd = LocalDateTime.now().plusMinutes(2); // current timestamp + 2 min
		LocalDateTime ldtStart = ldtEnd.minus(4, ChronoUnit.HOURS); // earlier timestamp

		// gettig and setting current time for download selection
		String[] dates = this.getDateTimeValues(ldtStart, ldtEnd);

		this.downloadTracingSearchCriteria.clickIconDateStart();

		this.dialogueCalendarSelection.clickDateStart(ldtStart.format(this.getDayOfMonth(ldtStart)));

		this.downloadTracingSearchCriteria.getHourFieldStartDate().clear();
		this.downloadTracingSearchCriteria.getHourFieldStartDate().sendKeys(dates[1].split(":")[0]);

		this.downloadTracingSearchCriteria.getMinuteFieldStartDate().clear();
		this.downloadTracingSearchCriteria.getMinuteFieldStartDate().sendKeys(dates[1].split(":")[1]);

		Thread.sleep(3000);

		this.downloadTracingSearchCriteria.clickIconDateEnd();

		new DialogueCalendarSelection(this.getWebDriver()).clickDateEnd(ldtEnd.format(this.getDayOfMonth(ldtEnd)));

		this.downloadTracingSearchCriteria.getHourFieldEndDate().clear();
		this.downloadTracingSearchCriteria.getHourFieldEndDate().sendKeys(dates[3].split(":")[0]);

		this.downloadTracingSearchCriteria.getMinuteFieldEndDate().clear();
		this.downloadTracingSearchCriteria.getMinuteFieldEndDate().sendKeys(dates[3].split(":")[1]);

		//
		this.downloadTracingSearchCriteria.getSelectCountry().selectByVisibleText("DE");

		Thread.sleep(2000);

		// download of log file
		this.downloadTracingSearchCriteria.clickButtonTraceDownload();

        // delaying check because of download duration
        Thread.sleep(300000);
//        Thread.sleep(150000);
        assertTrue(this.downloadFile.exists(), "Download file '" + this.downloadFile.getName() + "' was not existing.");

        // loading download log file content
        String contentLog = new String(Files.readAllBytes(Paths.get(this.downloadFile.toURI())));

        // Adding date range
        contentLog = ldtStart.toString() + "#" + ldtEnd.toString() + "#" + contentLog;

        // sending logfile for analysing to Mock-/REST-server
    	Response response = this.crs.doPostTextPlain(ConstantsRestServerCommunication.BASEURL_POST_ADMINTOOL_LOG, contentLog);

        // exiting Admin Tool
		this.downloadTracingSearchCriteria.clickButtonLogout();

    	//
    	//this.logStepPassed(); // 1

    	String jsonResponse = response.readEntity(String.class);
    	int statusCode = response.getStatus();

    	if (200 != statusCode) {
    		throw new Exception("POST of JSON resulted in HTTP status code " + statusCode +
    				" (" + jsonResponse + "), but expected was HTTP status code 200.");
    	}

    	int[] resultsAnalysation = this.extractResults(jsonResponse);

    	// OK
    	assertTrue(0 < resultsAnalysation[0], "Expected more than zero 'OK' messages, but found '" + resultsAnalysation[0] + "'.");

    	// not OK
    	assertTrue(0 == resultsAnalysation[1], "Expected zero 'not OK' messages, but found '" + resultsAnalysation[1] + "'.");

    	//
    	//this.logStepPassed(); // 2

    }

    private DateTimeFormatter getDayOfMonth(LocalDateTime ldtStart) {

    	int day = ldtStart.getDayOfMonth();

    	if (day >= 10) {
    		return DateTimeFormatter.ofPattern("dd");
    	} else {
    		return DateTimeFormatter.ofPattern("d");
    	}

	}

	private int[] extractResults(String jsonResponse) {

    	int[] result = {0, 0};

    	try {

			JsonReader reader = Json.createReader(new StringReader(jsonResponse));

	        JsonObject jo = reader.readObject();
	        String message = jo.getString("message");

	        reader.close();

	        String[] parts = message.split(",");

	        result[0] = Integer.parseInt(parts[0].substring(parts[0].indexOf("[") + 1, parts[0].indexOf("]")));
	        result[1] = Integer.parseInt(parts[1].substring(parts[1].indexOf("[") + 1, parts[1].indexOf("]")));

    	} catch (Exception e) {
    		System.out.println("Exception on reading response message from server: " + jsonResponse + ", exception message: " + e.getMessage());
    	}

    	return result;

    }

	private String[] getDateTimeValues(LocalDateTime ldtStart, LocalDateTime ldtEnd) {

    	String[] dateTimes = new String[4];

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

		dateTimes[0] = ldtStart.format(dateFormatter);

		dateTimes[1] = ldtStart.format(timeFormatter);

		dateTimes[2] = ldtEnd.format(dateFormatter);

		dateTimes[3] = ldtEnd.format(timeFormatter);

    	return dateTimes;
	}

	@Override
    public void tearDownHook() {

    }

	/**
	 *
	 */
	private void initialiseWebPages() {

		/*
		 * Admin Tool
		 */
		this.adminToolLogin = new AdminToolLogin(this.getWebDriver());
		this.mbbAdminToolRoleSelection = new MBBAdminToolRoleSelection(this.getWebDriver());
		this.mbbApplicationSelection = new MBBApplicationSelection(this.getWebDriver());
		this.downloadTracingSearchCriteria = new DownloadTracingSearchCriteria(this.getWebDriver());
		this.dialogueCalendarSelection = new DialogueCalendarSelection(this.getWebDriver());

	}

	// deleting download file
	private void deleteDownloadFile() {
	    if (this.downloadFile.exists()) {
	        this.downloadFile.delete();
	    }
	}

}