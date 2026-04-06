package de.sulzer.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testFramework.StartClass;
import testFramework.StartClassLocal;
import testFramework.utilities.ReusableMethods;
import testFramework.utilities.properties.PropertyReader;


/**
 * @author Sulzer GmbH
 */
public class FirefoxTestingBrowser {

    private WebDriver driver;
    private static String env = "";

    public WebDriver startONUPInFirefox() {

        String os = System.getProperty("os.name");

        System.out.println("OS: " + os);

        os = os.toLowerCase();

        // assuming running on RHEL VM
        if (os.contains("linux")) {
            setupFirefoxGrid();
            // assuming running locally with Windows
        } else if (os.contains("windows")) {
            setupFirefoxDriver();
            // pretty sure a mistake in assuming, it is Windows, could be local Linux too
        } else {
            setupFirefoxDriver();
        }

        PropertyReader pr = new PropertyReader();
        String LOCAL_CONFIG_PROPERTIES = "local-config.properties";
        String ENVIRONMENT = "environment";

        if (null != StartClassLocal.environment) {
            env = StartClassLocal.environment.toUpperCase();
        }
        if (os.contains("linux")) {
            env = StartClass.environment.toUpperCase();
        } else {
            try {
                env = pr.readProperty(LOCAL_CONFIG_PROPERTIES, ENVIRONMENT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Found environment: " + env);

        if ("DEMO_ECE".equals(env)) {
            this.driver.get(ImportantOnupURLs.LOGIN_URL_DEMO);
            waitForLoginElementsToBeLoaded();
        }

        if ("APPROVAL_ECE".equals(env)) {
            this.driver.get(ImportantOnupURLs.LOGIN_URL_APPROVAL);
            waitForLoginElementsToBeLoaded();
        }

        if ("DEMO_LS".equals(env)) {
            this.driver.get(ImportantOnupURLs.LOGIN_URL_LS_DEMO);
            waitForLoginElementsToBeLoaded();
        }

        if ("APPROVAL_LS".equals(env)) {
            this.driver.get(ImportantOnupURLs.LOGIN_URL_LS_APPROVAL);
            waitForLoginElementsToBeLoaded();
        }

        if ("DEMO_ECE_PKI".equals(env)) {

            this.driver.get(ImportantOnupURLs.LOGIN_URL_DEMO_PKI);

            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            assert robot != null;

            String textToType = "953201";
            for (char c : textToType.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            }


            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            driver.navigate().refresh();
        }

        if ("APPROVAL_ECE_PKI".equals(env)) {
            this.driver.get(ImportantOnupURLs.LOGIN_URL_APPROVAL_PKI);

            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            assert robot != null;

            String textToType = "953201";
            for (char c : textToType.toCharArray()) {
                int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
            }


            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

            driver.navigate().refresh();
        }

        if ("DEMO_ECE_DEBUGGING".equals(env)) {
            this.driver.get(ImportantOnupURLs.LOGIN_URL_DEMO);
            ReusableMethods.switchToDebuggingMode(driver);
            waitForLoginElementsToBeLoaded();
        }

        if ("APPROVAL_ECE_DEBUGGING".equals(env)) {
            this.driver.get(ImportantOnupURLs.LOGIN_URL_APPROVAL);
            ReusableMethods.switchToDebuggingMode(driver);
            waitForLoginElementsToBeLoaded();
        }


        return this.driver;
    }

//    private void setupFirefoxDriver() {
//
//            // Edge tarayıcı ayarlarını yapılandırma
////            EdgeOptions options = new EdgeOptions();
////            options.addArguments("start-maximized"); // Tarayıcı tam ekran modunda açılsın
////            options.addArguments("disable-infobars"); // Tarayıcı bilgi çubuğunu kapat
////            options.addArguments("disable-infobars"); // Tarayıcı bilgi çubuğunu kapat
////
////            // Tarayıcı penceresinin boyutunu ve konumunu ayarla
////            Dimension dimension = new Dimension(1920, 1080);
////            Point point = new Point(1920, 0);
////            options.addArguments("window-size=" + dimension.getWidth() + "," + dimension.getHeight());
////            options.addArguments("window-position=" + point.getX() + "," + point.getY());
////            System.out.println("testing --> " + this.getClass().getCanonicalName());
//            System.setProperty("webdriver.edge.driver", "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Selenium dependencies\\drivers\\msedgedriver.exe");
//
//            this.driver = new EdgeDriver();
//
//            driver.manage().window().maximize();
//            this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//    }


    private void setupFirefoxDriver() {

        try {

//            System.setProperty("webdriver.gecko.driver",
//                    "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Selenium dependencies\\drivers\\geckodriver.exe");
        	PropertyReader pr = new PropertyReader();
        	String geckoDriverPath = pr.readProperty("local-config.properties", "geckoDriverPath");
        	System.setProperty("webdriver.gecko.driver", geckoDriverPath);
        	
            FirefoxProfile profile = new FirefoxProfile();
            profile.setPreference("security.default_personal_cert", "Ask Every Time"); // always ask for certificate
            profile.setPreference("security.osclientcerts.autoload", true); // auto-load smart card certs if available
            
            FirefoxOptions options = new FirefoxOptions();
            options.addPreference("browser.tabs.remote.autostart", true);
            options.addPreference("browser.tabs.remote.autostart.2", true);
            options.addPreference("setLegacy", (true));
            options.addPreference("security.sandbox.content.level", 3);
            options.addPreference("dom.ipc.useNativeEventProcessing.content ", false);
//            options.addArguments("--headless");
            options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            options.setProfile(profile);

            this.driver = new FirefoxDriver(options);
            this.driver.manage().window().maximize();
//            this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//        } catch (Exception e) {
//            System.out.println(e);
//        }

        //WebDriverWait wait = new WebDriverWait(driver, 20);
       // wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inf-recall-tbl-id")));
    } catch (Exception e) {
        System.out.println(e);
    }


// eski hali
//            System.out.println("testing --> " + this.getClass().getCanonicalName());
//            System.setProperty("webdriver.edge.driver", "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Selenium dependencies\\drivers\\msedgedriver.exe");
//            this.driver = new EdgeDriver();
////        driver.get("https://pre-portal.epp.audi.vwg/onup-admin/#/app/landing-page");
////		driver.navigate().to("https://pre-portal.epp.audi.vwg/onup-admin/#/app/landing-page");
//
//
//            driver.manage().window().maximize();
//            this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }


// yeni yazılan
//            System.out.println("testing --> " + this.getClass().getCanonicalName());
//            System.setProperty("webdriver.edge.driver", "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Selenium dependencies\\drivers\\msedgedriver.exe");
//            EdgeOptions options = new EdgeOptions();
//            options.setCapability("language", "en-GB");
//
//            this.driver = new EdgeDriver(options);
////        driver.get("https://pre-portal.epp.audi.vwg/onup-admin/#/app/landing-page");
////		driver.navigate().to("https://pre-portal.epp.audi.vwg/onup-admin/#/app/landing-page");
//
//
//            driver.manage().window().maximize();
//            this.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//
//        } catch (Exception e) {
//            System.out.println(e);
//        }


    }

    private void setupFirefoxGrid() {

        System.setProperty("webdriver.firefox.marionette", "true");

        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("browser.tabs.remote.autostart", false);
        options.addPreference("browser.tabs.remote.autostart.2", false);
        options.addPreference("security.sandbox.content.level", 3);
        options.addPreference("dom.ipc.useNativeEventProcessing.content ", false);
        options.addArguments("--headless");
        // download options (set in order to have no download dialogue)
        options.addPreference("browser.download.folderList", "1");
        options.addPreference("browser.download.manager.showWhenStarting", false);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/zip;application/x-zip-compressed;application/octet-stream;application/csv;text/csv;application/vnd.ms-excel;text/plain;text/comma-separated-values");
        options.addPreference("browser.helperApps.alwaysAsk.force", false);
        options.setLogLevel(FirefoxDriverLogLevel.TRACE);

//		options.addPreference("intl.accept_languages", "de-DE");
        options.addArguments("--lang=de-DE");

        try {
//		driver = new FirefoxDriver(options);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

//				driver.manage().window().setSize(new Dimension(1980, 1050));
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.out.println("headless not able to maximize (exception message: " + e.getMessage() + ")");
        }


//        try {
//            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
//            desiredCapabilities.setBrowserName(BrowserType.FIREFOX);
//            desiredCapabilities.setCapability("platform", Platform.ANY);
//            driverPool.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), desiredCapabilities));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }

    
    /**
     * Navigates to the specified URL and enters the given password using the Robot class.
     * This method uses two threads: one for navigation and one for entering the password.
     *
     * @param url      The URL to navigate to.
     * @param password The password to enter.
     */
    public void navigateAndEnterPasswordWithPKI(String url, String password, int cardPosition) {
        // Thread for navigating to the URL
        Thread navigationThread = new Thread(() -> {
            try {
                driver.get(url);
            } catch (Exception e) {
                System.err.println("Error occurred while navigating to URL: " + e.getMessage());
            }
        });

        // Thread for entering the password using the Robot class
        Thread robotThread = new Thread(() -> {
            try {
                // Wait for 5 seconds to ensure the page is loaded
                Thread.sleep(5000);

                // Initialize Robot class
                Robot robot = new Robot();
                
                
                for (int i = 1; i < cardPosition; i++) {
                    robot.keyPress(KeyEvent.VK_DOWN);
                    robot.keyRelease(KeyEvent.VK_DOWN);
                    Thread.sleep(200); // small delay to ensure selection moves
                }

                // Press Enter to confirm certificate
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);

                // Wait for PIN/password dialog to appear
                Thread.sleep(90000);

                // Type the password character by character

                for (char c : password.toCharArray()) {
                    int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
                    if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                        throw new RuntimeException("Unsupported password character: " + c);
                    }
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                    Thread.sleep(1000);
                }

                // Press Enter key
                robot.keyPress(KeyEvent.VK_ENTER);
                robot.keyRelease(KeyEvent.VK_ENTER);

            } catch (AWTException e) {
                System.err.println("Error occurred while creating Robot instance: " + e.getMessage());
            } catch (InterruptedException e) {
                System.err.println("Thread was interrupted while sleeping: " + e.getMessage());
            }
        });

        // Start both threads
        navigationThread.start();
        robotThread.start();

        try {
            // Wait for both threads to complete
            navigationThread.join();
            robotThread.join();
        } catch (InterruptedException e) {
            System.err.println("Thread was interrupted while waiting: " + e.getMessage());
        }
    }
    
    public WebDriver startSplunkInFirefox(boolean splunk, String endPoint) throws IOException {

        String os = System.getProperty("os.name");
        PropertyReader pr = new PropertyReader();
        String pkiPin = pr.readProperty("local-config.properties", "PKIpin");
        		
        System.out.println("OS: " + os);

        os = os.toLowerCase();

        // assuming running on RHEL VM
        if (os.contains("linux")) {
            setupFirefoxGrid();
            // assuming running locally with Windows
        } else if (os.contains("windows")) {
            setupFirefoxDriver();
            // pretty sure a mistake in assuming, it is Windows, could be local Linux too
        } else {
            setupFirefoxDriver();
        }

        if(splunk == true) {
        	navigateAndEnterPasswordWithPKI("https://splunk.audi.de/en-GB/app/UI-SH-0200-ONLINEUPDATE-2_3-ECE-SUPPORT"+endPoint, pkiPin, 5);
        }
        
        
        return this.driver;  
    }
    
    private void waitForLoginElementsToBeLoaded() {

//        WebDriverWait driverWait = new WebDriverWait(driver, 180);
        WebDriverWait driverWait = new WebDriverWait(driver, 30);


        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inpt-login-username")));
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inpt-login-password")));
        driverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btn-login-submit")));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(FirefoxTestingBrowser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}