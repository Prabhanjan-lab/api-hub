/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testDev.templates;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.sulzer.pages.AdminPortletHomepage;
import testFramework.StartClass;
import de.sulzer.utils.ImportantOnupURLs;

/**
 * @author GCH9F5D
 *
 */
public class LocalDevPrototypeTemplateRetro {

	private WebDriver driver;
	private WebDriverWait wait;

	/*
	 *
	 */

	private AdminPortletHomepage adminPortletHomepage;

	/*
	 *
	 */

	private short p = 1;

	public static void main(String args[]) throws InterruptedException {

		LocalDevPrototypeTemplateRetro ldpt = new LocalDevPrototypeTemplateRetro();

		try {
			ldpt.setUp();
			ldpt.testHook();
			ldpt.tearDown();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	protected void setUp() {

		/*
		 * Using 64-bit version (FirefoxPortable & GeckDriver) causes 45 s timeout error.
		 */

    	System.out.println("testing --> " + this.getClass().getCanonicalName());
    	System.setProperty("webdriver.gecko.driver", "//AUDIINHOME015.IN.AUDI.VWG/GCH9F5D$/Eigene Dateien/dev_infrastructure/selenium-geckodriver-32/geckodriver.exe");
    	FirefoxOptions options = new FirefoxOptions();
    	options.addPreference("browser.tabs.remote.autostart", false);
    	options.addPreference("browser.tabs.remote.autostart.2", false);
    	options.addPreference("security.sandbox.content.level", 3);
    	options.addPreference("dom.ipc.useNativeEventProcessing.content ", false);
    	options.setBinary("C:/Program Files (x86)/Mozilla Firefox/firefox.exe");

    	this.driver = new FirefoxDriver(options);
    	try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	this.driver.manage().window().maximize();
    	this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

    }

    protected void testHook() throws Throwable {

    	//Roles & Rights
    	this.getWebDriver().get(ImportantOnupURLs.LOGIN_URL_DEMO);

    	// set default user for testing
    	StartClass.userName = "";
    	StartClass.userPassword = "";

    	this.wait = new WebDriverWait(this.getWebDriver(), 30);

    	/*
    	 *
    	 */

//    	// setting time for implicitely waiting time (otherwise test duration is too long)
//    	this.getWebDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//
//    	// campaign management
//    	try {
//    		this.adminPortletHomepage.clickCampaignManagement();
//    		throw new RuntimeException("Driver was able to click menu 'Campaign Management'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// campaign management - Search
//    	try {
//    		this.adminPortletHomepage.clickCampaignManagementSearch();
//    		throw new RuntimeException("Driver was able to click menu 'Campaign Management Search'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// campaign management - Rollout Status
//    	try {
//    		this.adminPortletHomepage.clickCmRolloutStatus();
//    		throw new RuntimeException("Driver was able to click menu 'Campaign Management Rollout Status'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// vehicle management
//    	try {
//			this.adminPortletHomepage.clickVehicleAdministrationList();
//    		throw new RuntimeException("Driver was able to click menu 'Vehicle Administration List'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// vehicle management - sub menu 'Template Management'
//    	try {
//    		this.vehicleOverviewPage.clickExceptionListManagement();
//    		throw new RuntimeException("Driver was able to click menu 'Template Management'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// vehicle management - sub menu 'Exception List Managenemt'
//    	try {
//    		this.vehicleOverviewPage.clickTemplateManagement();
//    		throw new RuntimeException("Driver was able to click menu 'Exception List Managenemt'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// testing menu
//    	try {
//    		this.adminPortletHomepage.clickTestingMenu();
//    		throw new RuntimeException("Driver was able to click menu 'Testingmenu'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// sector log
//    	try {
//    		this.adminPortletHomepage.clickSectorLog();
//    		throw new RuntimeException("Driver was able to click menu 'Sector Log'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// user management
//    	try {
//    		this.adminPortletHomepage.clickUserManagement();
//    		throw new RuntimeException("Driver was able to click menu 'User Management'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// configuration list
//    	try {
//    		this.adminPortletHomepage.clickConfigurationList();
//    		throw new RuntimeException("Driver was able to click menu 'Configuration List'");
//    	} catch (NoSuchElementException e) {
//    	}
//    	// documentation
//    	try {
//    		this.adminPortletHomepage.clickDocumentation();
//    		throw new RuntimeException("Driver was able to click menu 'Documentation'");
//    	} catch (NoSuchElementException e) {
//    	}
//        this.logStep();


    }

    public void tearDown() {

    	/*
    	 * aftermath: changing roles of user and deleting created test data record
    	 */

    	this.getWebDriver().quit();

    }

	private WebDriver getWebDriver() {
		return this.driver;
	}

	/**
	 * @return the wait
	 */
	private WebDriverWait getWebDriverWait() {
		return this.wait;
	}

	private void logStepPassed() {
    	System.out.println("pass " + p++);
	}
}
