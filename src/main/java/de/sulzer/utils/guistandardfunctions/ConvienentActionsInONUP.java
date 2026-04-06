package de.sulzer.utils.guistandardfunctions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.apache.maven.shared.utils.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.BrandSelectionPage;
import de.sulzer.pages.LoginPage;
import de.sulzer.pages.testingmenu.recall.ManualRecallSystemPage;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import testFramework.utilities.ReusableMethods;

/**
 * @author Sulzer GmbH
 *
 */
public class ConvienentActionsInONUP {

    private static AdminPortletHomepage _adminPortletHomepage;

    private ConvienentActionsInONUP() {

    }

    public static synchronized void loginAsAdminToPortletHomepage(WebDriver driver){

        _adminPortletHomepage = new AdminPortletHomepage(driver);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin();

        selectBrandDefault(driver);
        _adminPortletHomepage.clickTitleWebpageAdminPortlet();
    }

    public static synchronized void loginAsUserToPortletHomepage(WebDriver driver){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsUser();
        selectBrandDefault(driver);
    }

    /**
     * @param driver
     */
    static synchronized void selectBrandDefault(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, 40);

        BrandSelectionPage brandSelectionPage = new BrandSelectionPage(driver);

        wait.until(ExpectedConditions.visibilityOf(brandSelectionPage.getChooseBrandHeader()));

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String brandSelectionHeader = brandSelectionPage.getChooseBrandHeader().getText();

//        assertTrue(brandSelectionHeader.contains("Please select a Brand"), "expected 'Please select a Brand', but found '" + brandSelectionHeader + "'");

        wait.until(ExpectedConditions.elementToBeClickable(brandSelectionPage.getBrandSelectApplyButton()));

        brandSelectionPage.clickApplyBrandButton();

    }

    public static synchronized void adminLoginWithBrand(WebDriver driver,String brand){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsAdmin();

        selectBrandSelected(driver, brand);
    }

    public static synchronized void userLoginWithBrand(WebDriver driver,String brand){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsUser();


        selectBrandSelected(driver, brand);
    }

    public static synchronized void loginAsUserWithLowRightsToPortletHomepage(WebDriver driver){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAsUserWithLowRights();

        ConvienentActionsInONUP.selectBrandDefault(driver);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.switchTo().activeElement().click();
        driver.findElement(By.cssSelector(".container-fluid div:nth-child(3) button ")).click();
    }

    public static synchronized void loginAsUserWithMiddleRightsToPortletHomepage(WebDriver driver, boolean withBrandSelection){

        new LoginPage(driver).loginAsUserWithLdapOperatingRights();

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (withBrandSelection) {
            ConvienentActionsInONUP.selectBrandDefault(driver);
        }

    }

    private static synchronized void selectBrandSelected(WebDriver driver, String brand) {
        try{
            BrandSelectionPage brandSelectionPage = new BrandSelectionPage(driver);
            String brandSelectionHeader = brandSelectionPage.getChooseBrandHeader().getText();
            brandSelectionHeader.contains("Please select a Brand");
            Thread.sleep(5000);
            brandSelectionPage.setBrandByVisibleText(brand);
            brandSelectionPage.clickApplyBrandButton();
        }
        catch(Exception e){
            try{
                _adminPortletHomepage = new AdminPortletHomepage(driver);
                _adminPortletHomepage.getLinkLogout();
            }
            catch (Exception x){
                throw new RuntimeException();
            }
        }
    }

    public static synchronized void checkThatOnlyOneRecordIsFound(ManualRecallSystemPage manualRecallSystemPage) {
        String numberOfEntriesText = manualRecallSystemPage.getNumberOfEntriesParagraph().getText();
        assertTrue(numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS));
    }

    public static synchronized void errorHandling(WebDriver driver, Exception x) throws Exception{

        System.out.println("Test failed");

        StringWriter sw = new StringWriter();
        x.printStackTrace(new PrintWriter(sw));
        String exception = sw.toString();
        System.out.println(exception);

        String path;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            String stringDate  = dateFormat.format(new Date());
            File source = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            path = "\\\\Client\\Q$\\Selenium\\Reporting\\"+stringDate+".png";
            FileUtils.copyFile(source, new File(path));
        }
        catch(IOException e) {
            path = "Failed to capture screenshot: " + e.getMessage();
        }

        driver.quit();
        throw new Exception("Test Case failed");
    }

    public static synchronized String currentDateAndTime() {
        Date myDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String myDateString = sdf.format(myDate);
        return myDateString;

    }

    /**
     * Brands/parameter values can be:
     * - 'Audi'
     * - 'VW PKW'
     * - further values see/open dialogue above/right side with link of car brand(s)
     *
     * @param adminPortletHomepage
     * @param brand
     * @throws Exception
     */
    public static synchronized void changeBrand(AdminPortletHomepage adminPortletHomepage, String brand) throws Exception {
        adminPortletHomepage.changeBrand(brand);
    }

    /**
     * Centralising a widely used functionality within codebase.
     *
     * @param element
     */
    public static synchronized void saveClick(WebElement element) {
        element.isDisplayed();
        element.isEnabled();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        element.click();
    }

    public static void takeScreenshot(WebDriver webDriver, String filename, String path) {
        Shutterbug.shootPage(webDriver, ScrollStrategy.WHOLE_PAGE, 100, true).
                withName(filename).
                save(path);
    }

    /**
     * This method creates a throwaway VIN for certain test cases. It is based
     * on time stamps and therefore can only produce one VIN per second for
     * distinction of numbers/VINs.
     *
     * @param recallId
     * @return a throwaway VIN, which isn't valid but needed for certain test cases
     */
//    public static synchronized String createThrowAwayVin(String recallId) {
//
//        /*
//         * Ensures that generated VINs, dependent on current time stamp are
//         * all individual to each other.
//         */
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // getting current time stamp for throwaway-VIN generation
//        LocalDateTime ldt = LocalDateTime.now();
//
//        String str = ldt.toString().replace("-", "").replace("T", "").replace(":", "").replace(".", "");
//
//        str = "TAQ8" + new StringBuilder(str).reverse();
//
//        return str.substring(0, 17);
//
//    }


    public static synchronized String createThrowAwayVin(String recallId) {

        /*
         * Ensures that generated VINs, dependent on current time stamp are
         * all individual to each other.
         */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // getting current time stamp for throwaway-VIN generation
        LocalDateTime ldt = LocalDateTime.now();

        String str = ldt.toString().replace("-", "").replace("T", "").replace(":", "").replace(".", "");

        str = recallId + new StringBuilder(str).reverse();

        return str.substring(0, 17);

    }

}
