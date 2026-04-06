/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testDev;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.testingmenu.recall.AddRecallSystemModal;
import de.sulzer.pages.testingmenu.recall.ManualRecallSystemPage;
import de.sulzer.pages.useradministration.UserOverview;
import de.sulzer.utils.ImportantOnupURLs;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;

/**
 *
 * @author GP4KM3W
 */
public class LocalDevTemplate {
static WebDriver driver;
//    private static final String VIN = "BAUFP74N816110920"; //VIN für Demo und Approval???
    private static final String VIN = "BAUSLZ4N818102908"; //VIN für Demo und Approval???
    private static final String RECALL_ID = "TA0M";
    private static final String RECALL_TITLE = de.sulzer.utils.CreateRandomChars.generateRandomChars("ABCDEFGHIJKLMNOPQRSUVWXYZ1234567890", 15);
    private static final String RECALL_DISABLED_COUNTRY = "ATA";
    private static final String CRITERION01_ID = "01";
    private static final String CRITERION01_TITLE = "Step 01";
    private static final String COMMENT = "IT-Sulzer: Smoketest";
    
    
    public static void main(String args[]) throws InterruptedException {
        
        
        AdminPortletHomepage _adminPortletHomepage;
        UserOverview _userOverview;
        ManualRecallSystemPage _manualRecallSystemPage;
        AddRecallSystemModal _addRecallSystemModal;

        try {
            System.out.println("Test123123123");
//            System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver.exe");
            FirefoxOptions options = new FirefoxOptions();
            //options.addPreference("setLegacy", (true));
            options.addPreference("browser.tabs.remote.autostart", false);
            options.addPreference("browser.tabs.remote.autostart.2", false);
            options.addPreference("security.sandbox.content.level", 3);
            options.addPreference("dom.ipc.useNativeEventProcessing.content ", false);
//            options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            options.setBinary("C:\\Program Files (x86)\\Mozilla Firefox");
//            options.addArguments("--headless");



//            driver = new FirefoxDriver(options);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),options);
            Thread.sleep(10000);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            //Roles & Rights
            driver.get(ImportantOnupURLs.LOGIN_URL_DEMO);

            ConvienentActionsInONUP.loginAsAdminToPortletHomepage(driver);

            _adminPortletHomepage = new AdminPortletHomepage(driver);
            
             _adminPortletHomepage.clickTestingMenu();
    	_adminPortletHomepage.clickManualRecallSystem();
        _manualRecallSystemPage = new ManualRecallSystemPage(driver);
        _manualRecallSystemPage.searchReCallSimple(RECALL_ID);
        
        _manualRecallSystemPage.clickActionNumberVins();
        _manualRecallSystemPage.checkIfVinInList(VIN);
        _manualRecallSystemPage.clickCloseModal();   
   
        
        Thread.sleep(5000);
    driver.quit();
        } catch (Exception e) {
    System.out.println(e);
    Thread.sleep(5000);
    driver.quit();
        }
        
    }
}
