/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import java.util.List;

import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.sulzer.pages.vehicleadministration.exceptionlistmanagement.ExceptionListOverviewPage;
import testFramework.utilities.ReusableMethods;


/**
 * @author Bege
 */
public class ActionsOnupExceptionList extends Page {

    protected WebDriver webDriver;

    public ActionsOnupExceptionList(WebDriver driver) {
        super(driver);
        this.webDriver = driver;
    }

    public void deleteVin(ExceptionListOverviewPage exceptionListOverviewPage, String vin) {

//        exceptionListOverviewPage.searchExceptionListItem(vin);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
//
//        List<WebElement> rows = exceptionListOverviewPage.getRowsExceptionElements();
//
//        if (rows.size() == 1 &&
//                !rows.get(0).getText().contains(ConstantsMisc.NO_RECORDS_FOUND)) {
//            // Click on Delete button
//            exceptionListOverviewPage.clickDeleteExceptionList();
//            // click yes on the confirmation delete button
//            exceptionListOverviewPage.clickConfirmDeletion();
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e2) {
//                e2.printStackTrace();
//            }
//
//        }


        // The method rewritten (19.12.2022)

        try {
            exceptionListOverviewPage.searchExceptionListItem(vin);
//            ReusableMethods.waitForVisibility(webDriver.findElement(By.xpath("//td[.=' " + vin + " ']")), 20, webDriver);
            ReusableMethods.waitForClickablility(webDriver.findElement(By.xpath("//table/tbody/tr/td[.=' " + vin + " ']")), 10, webDriver);
            exceptionListOverviewPage.getDelFirstExeptionList();
        } catch (Exception e) {
            System.out.println("VIN " + vin + " was not found, so the deletion did not happen.");
        }
    }

}
