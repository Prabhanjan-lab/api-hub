/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.caradministration.VehicleOverviewPage;
import de.sulzer.pages.caradministration.VehicleOverviewPageSpecificVIN;
import de.sulzer.pages.caradministration.VehicleOverviewSpecificVinAttributes;
import de.sulzer.pages.caradministration.VinOverviewPage;
import de.sulzer.pages.genericelements.Page;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testFramework.utilities.ReusableMethods;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sulzer GmbH
 */
public class ActionsOnupVehicleOverview extends Page {

    /**
     *
     */
    WebDriver webDriver;

    public ActionsOnupVehicleOverview(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }

    /**
     * @param vin
     * @param vehicleOverviewPageSpecificVIN2
     * @param vehicleOverviewPage2
     * @param adminPortletHomepage2
     * @throws InterruptedException
     */
    public void initVehicleOverviewQueues(
            String vin,
            AdminPortletHomepage adminPortletHomepage,
            VehicleOverviewPage vehicleOverviewPage,
            VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN) throws InterruptedException {

        final String NO_RECORDS_FOUND = ConstantsGuiTexts.NO_RECORDS_FOUND;
        final String TEXT_CANCEL = ".//a[contains(text(),'Cancel')]";
        final String TEXT_FORCE_ABORT = "//a[contains(text(),'Force Abort')]";
        openVehicleAdministrationList(adminPortletHomepage);

        searchVehicleOverviewWithVin(vin, adminPortletHomepage, vehicleOverviewPage);
//        vehicleOverviewPageSpecificVIN.clickPendingCampaignPanelButtonLoad();
        ReusableMethods.clickElement(vehicleOverviewPageSpecificVIN.getPendingCampaignPanelButtonLoad(), 5, webDriver);

//        Thread.sleep(2000);
        ReusableMethods.waitForVisibility(vehicleOverviewPageSpecificVIN.getPendingCampaignRows().get(0), 5, webDriver);

        do {

            List<WebElement> listWebElements = vehicleOverviewPageSpecificVIN.getPendingCampaignRows();

            if (0 == listWebElements.size()) {
                break;
            }

            String row = listWebElements.get(0).getText().trim();

            if (row.contains(NO_RECORDS_FOUND)) {
                break;
            }

//            listWebElements.get(0).findElement(By.xpath(TEXT_CANCEL)).click();
            ReusableMethods.clickElement(listWebElements.get(0).findElement(By.xpath(TEXT_CANCEL)),3, webDriver);

//            Thread.sleep(5000);
            ReusableMethods.waitForVisibility(vehicleOverviewPageSpecificVIN.getPendingCampaignRows().get(0), 5, webDriver);

        } while (vehicleOverviewPageSpecificVIN.getPendingCampaignRows().size() > 0);

        vehicleOverviewPageSpecificVIN.clickScheduledCampaignPanelButtonLoad();

//        Thread.sleep(2000);
        ReusableMethods.waitForVisibility(vehicleOverviewPageSpecificVIN.getScheduledCampaignRows().get(0), 5, webDriver);
        do {

            List<WebElement> listWebElements = vehicleOverviewPageSpecificVIN.getScheduledCampaignRows();

            if (0 == listWebElements.size()) {
                break;
            }

            String row = listWebElements.get(0).getText().trim();

            if (row.contains(NO_RECORDS_FOUND)) {
                break;
            }

            listWebElements.get(0).findElement(By.xpath(TEXT_CANCEL)).click();
//            Thread.sleep(5000);
            ReusableMethods.waitForVisibility(vehicleOverviewPageSpecificVIN.getScheduledCampaignRows().get(0), 5, webDriver);

        } while (vehicleOverviewPageSpecificVIN.getScheduledCampaignRows().size() > 0);
        vehicleOverviewPageSpecificVIN.clickActiveCampaignPanelButtonLoad();

//        Thread.sleep(5000);
        Thread.sleep(1000);
//        ReusableMethods.waitForVisibility(vehicleOverviewPageSpecificVIN.getActiveCampaignRows().get(0), 5, webDriver);
        do {

            List<WebElement> listWebElements = vehicleOverviewPageSpecificVIN.getActiveCampaignRows();

            if (0 == listWebElements.size()) {
                break;
            }

            String row = listWebElements.get(0).getText().trim();

            if (row.contains(NO_RECORDS_FOUND)) {
                break;
            }
//            listWebElements.get(0).findElement(By.xpath(TEXT_FORCE_ABORT)).click();
            webDriver.findElement(By.xpath(TEXT_FORCE_ABORT)).click();

            // New added
            try {

                if (vehicleOverviewPageSpecificVIN.getHeadingInErrorDialogueOfForceAbortOperation().isDisplayed()) {
                    System.out.println("following error message was shown after clicking force abort button ***"
                            + vehicleOverviewPageSpecificVIN.getErrorTextInDialogueOfForceAbortOperation().getText() + "***");
                    vehicleOverviewPageSpecificVIN.getOkButtonInErrorDialogueOfForceAbortOperation().click();
                    vehicleOverviewPageSpecificVIN.clickActiveCampaignPanelButtonLoad();
                    vehicleOverviewPageSpecificVIN.clickActiveCampaignPanelButtonLoad();
                }
            } catch (NoSuchElementException ignored) {
            }
            Thread.sleep(1000);
//            ReusableMethods.waitForVisibility(vehicleOverviewPageSpecificVIN.getActiveCampaignRows().get(0), 5, webDriver);
        } while (vehicleOverviewPageSpecificVIN.getActiveCampaignRows().size() > 0);

    }

    public void searchVehicleAdminstrationWithVin(
            AdminPortletHomepage adminPortletHomepage,
            VehicleOverviewPage vehicleOverviewPage,
            String vin) throws InterruptedException {

//        openVehicleAdministrationList(adminPortletHomepage);
        searchVehicleOverviewWithVin(vin, adminPortletHomepage, vehicleOverviewPage);

    }

    /**
     * @param adminPortletHomepage
     */
    public void openVehicleAdministrationList(AdminPortletHomepage adminPortletHomepage) {

        ReusableMethods.waitForClickablility(adminPortletHomepage.getVehicleAdministrationList(), 5, webDriver);
        if (!adminPortletHomepage.getVehicleOverview().isDisplayed()) {
            adminPortletHomepage.clickVehicleAdministrationList();
        }
    }


    /**
     * @param vin
     * @param adminPortletHomepage
     * @param vehicleOverviewPage
     */
    private void searchVehicleOverviewWithVin(String vin, AdminPortletHomepage adminPortletHomepage,
                                              VehicleOverviewPage vehicleOverviewPage) {

        try {
//            adminPortletHomepage.clickVehicleOverview();
//            ReusableMethods.waitForClickablility(vehicleOverviewPage.getVinInputField(), 2, webDriver);
            openVehicleAdministrationList(adminPortletHomepage);
            vehicleOverviewPage.searchForVin(vin);
        } catch (RuntimeException ignored) {
        }
    }

    public void checkcampaigninActivequeue(WebDriver driver, String recallandcriterionID) throws InterruptedException {

        VinOverviewPage _VinOverviewPage;
        _VinOverviewPage = new VinOverviewPage(driver);
        int i = 0;
        while (i == 0) {

            Thread.sleep(2000);
            _VinOverviewPage.clickActiveCampaignPanelLink();
            String ActivecampaignID = _VinOverviewPage.presentActiveCampaignID();

            if (ActivecampaignID.equals(recallandcriterionID)) {
                System.out.println("Campaign is in active campaign panel");
                break;
            } else {
                _VinOverviewPage.clickforceabort();
                Thread.sleep(5000);
                int j = _VinOverviewPage.getRowsofActiveCampaign();
                if (j == 0) {
                    System.out.println("campaign is not in active campaign panel");
                    break;
                }

                _VinOverviewPage.clickActiveCampaignPanelLink();

            }

        }

    }

    public void executeActiveCampaignForceAbort(AdminPortletHomepage adminPortletHomepage,
                                                VehicleOverviewPage vehicleOverviewPage,
                                                VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN,
                                                String vin,
                                                String campaignNr) {

        openVehicleAdministrationList(adminPortletHomepage);

        searchVehicleOverviewWithVin(vin, adminPortletHomepage, vehicleOverviewPage);

        vehicleOverviewPageSpecificVIN.clickActiveCampaignPanelButtonLoad();

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ActionsOnupVehicleOverview.class.getName()).log(Level.SEVERE, null, ex);
//        }

        try {
//            vehicleOverviewPageSpecificVIN.clickActiveCampaignForceAbortByCampaignId(campaignNr);
            ReusableMethods.clickElement(vehicleOverviewPageSpecificVIN.getActiveCampaignForceAbortLink(campaignNr), 5, webDriver);
            System.out.println("Active Campaign ForceAbort clicked");
        } catch (RuntimeException ignored) {
        }
//        ReusableMethods.clickElement(vehicleOverviewPageSpecificVIN.getActiveCampaignForceAbortLink(campaignNr), 5, webDriver);
    }

    public void openExceptionList(AdminPortletHomepage adminPortletHomepage) {

        openVehicleAdministrationList(adminPortletHomepage);

        adminPortletHomepage.clickExceptionListManagement();

    }

    public void openVehicleOverview(AdminPortletHomepage adminPortletHomepage) {

        openVehicleAdministrationList(adminPortletHomepage);

        adminPortletHomepage.getVehicleOverview().click();

    }

    /**
     * @throws InterruptedException
     */
    public void checkVinAttributes(
            AdminPortletHomepage adminPortletHomepage,
            VehicleOverviewPage vehicleOverviewPage,
            VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN,
            VehicleOverviewSpecificVinAttributes vehicleOverviewSpecificVinAttributes,
            String vin,

            String modelMbv,
            String equipment,
            String modelYear,

            String pknWeek,
            String pknDay,
            String pknPlant,
            String pknYear,

            String dealerCountryAttending,
            String dealerDealerAttending,
            String dealerImporterAttending,

            String dealerCountryDelivery,
            String dealerDealerDelivery,
            String dealerImporterDelivery,

            String dealerCountryOrder,
            String dealerDealerOrder,
            String dealerImporterOrder) throws InterruptedException {


        this.openVehicleOverview(adminPortletHomepage);
        vehicleOverviewPage.searchForVin(vin);

        ReusableMethods.clickElement(vehicleOverviewPageSpecificVIN.getVehicleAttributeLoadButton(), 3, webDriver);

        // implicit wait und explicit wait does not work here.
        Thread.sleep(3000);

        // General Settins

        String value = vehicleOverviewSpecificVinAttributes.getMbvText();
        assertEquals(modelMbv, value, "MBV: Found wrong value. Expected '" + modelMbv + "', but found '" + value + "'.");

        value = vehicleOverviewSpecificVinAttributes.getPrNumberText();
        assertEquals(equipment, value, "PRN: Found wrong value. Expected '" + equipment + "', but found '" + value + "'.");

        value = vehicleOverviewSpecificVinAttributes.getModelYearText();
        assertEquals(modelYear, value, "Model Year: Found wrong value. Expected '" + modelYear + "', but found '" + value + "'.");

        // PKN

        value = vehicleOverviewSpecificVinAttributes.getPknText();
        String pkn = checkCalendarWeek2Digits(pknWeek) + pknDay + checkCalendarWeek2Digits(pknPlant) + pknYear;
        assertEquals(pkn, value, "PKN: Found wrong value. Expected '" + pkn + "', but found '" + value + "'.");

        // Attending dealer

        value = vehicleOverviewSpecificVinAttributes.getAttendingDealershipCountryCodeText();

        assertTrue(
                value.contains(dealerCountryAttending),
                "Attending Dealer Country: Found wrong value. Expected '" + dealerCountryAttending + "', but found '" + value + "'.");

        value = vehicleOverviewSpecificVinAttributes.getAttendingDealershipPartnernumberText();
        assertEquals(value, dealerDealerAttending, "Attending Dealer Importer/Partnernumber : Found wrong value. Expected '" + dealerDealerAttending + "', but found '" + value + "'.");

        value = vehicleOverviewSpecificVinAttributes.getAttendingDealershipWholesalerText();
        assertEquals(value, dealerImporterAttending, "Attending Dealer Wholesaler: Found wrong value. Expected '" + dealerImporterAttending + "', but found '" + value + "'.");

        // Delivering dealer

        value = vehicleOverviewSpecificVinAttributes.getDeliveringDealershipCountryCodeText();
        assertTrue(
                value.contains(dealerCountryDelivery),
                "Delivering Dealer Country: Found wrong value. Expected '" + dealerCountryDelivery + "', but found '" + value + "'.");

        value = vehicleOverviewSpecificVinAttributes.getDeliveringDealershipPartnernumberText();
        assertEquals(value, dealerDealerDelivery, "Delivering Dealer Importer/Partnernumber : Found wrong value. Expected '" + dealerDealerDelivery + "', but found '" + value + "'.");

        value = vehicleOverviewSpecificVinAttributes.getDeliveringDealershipWholesalerText();
        assertEquals(value, dealerImporterDelivery, "Delivering Dealer Wholesaler: Found wrong value. Expected '" + dealerImporterDelivery + "', but found '" + value + "'.");

        // Ordering dealer

        value = vehicleOverviewSpecificVinAttributes.getOrderingDealershipCountryCodeText();
        assertTrue(
                value.contains(dealerCountryOrder),
                "Ordering Dealer Country: Found wrong value. Expected '" + dealerCountryOrder + "', but found '" + value + "'.");

        value = vehicleOverviewSpecificVinAttributes.getOrderingDealershipPartnernumberText();
        assertEquals(value, dealerDealerOrder, "Ordering Dealer Importer/Partnernumber : Found wrong value. Expected '" + dealerDealerOrder + "', but found '" + value + "'.");

        value = vehicleOverviewSpecificVinAttributes.getOrderingDealershipWholesalerText();
        assertEquals(value, dealerImporterOrder, "Ordering Dealer Wholesaler: Found wrong value. Expected '" + dealerImporterOrder + "', but found '" + value + "'.");
    }

    /**
     * @param value
     * @return
     */
    public String checkCalendarWeek2Digits(String value) {

        if (value.length() < 2) {
            return "0" + value;
        } else {
            return value;
        }

    }

    public void setVehicleVinOptions(
            AdminPortletHomepage adminPortletHomepage,
            VehicleOverviewPage vehicleOverviewPage,
            VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN,
            String vin,
            boolean testVehicle,
            boolean prototypeVehicle,
            boolean softwareVersionComparisonDisabled,
            boolean skipMassNotification) throws InterruptedException {

        searchVehicleAdminstrationWithVin(adminPortletHomepage, vehicleOverviewPage, vin);

        Thread.sleep(5000);

        vehicleOverviewPageSpecificVIN.clickOptionsPanelButtonLoad();

        Thread.sleep(2000);

        // Test vehicle
        boolean boolOption = Boolean.parseBoolean(getOptionTestVehicle(vehicleOverviewPageSpecificVIN));

        if (hasToChange(testVehicle, boolOption)) {
            vehicleOverviewPageSpecificVIN.clickOptionsChBTestVehicle();
            vehicleOverviewPageSpecificVIN.clickOptionsSaveButton();
        }

        // prototype vehicle
        boolOption = Boolean.parseBoolean(getOptionPrototypeVehicle(vehicleOverviewPageSpecificVIN));

        if (hasToChange(prototypeVehicle, boolOption)) {
            vehicleOverviewPageSpecificVIN.clickOptionsChBPrototypeVehicle();
            vehicleOverviewPageSpecificVIN.clickOptionsSaveButton();
        }

        // software version comparison disabled
        boolOption = Boolean.parseBoolean(getOptionSoftwareVersionComparisonDisabled(vehicleOverviewPageSpecificVIN));

        if (hasToChange(softwareVersionComparisonDisabled, boolOption)) {
            vehicleOverviewPageSpecificVIN.clickOptionsChBSoftwareVersionComparisonDisabled();
            vehicleOverviewPageSpecificVIN.clickOptionsSaveButton();
        }

        // mass notification
        boolOption = Boolean.parseBoolean(getOptionMassNotification(vehicleOverviewPageSpecificVIN));

        if (hasToChange(skipMassNotification, boolOption)) {
            vehicleOverviewPageSpecificVIN.clickOptionsChBMassNotification();
            vehicleOverviewPageSpecificVIN.clickOptionsSaveButton();
        }

    }

    /**
     * @param toSet
     * @param foundBoolean
     * @return
     */
    private boolean hasToChange(boolean toSet, boolean foundBoolean) {

        boolean setToTrue = toSet && !foundBoolean;
        boolean setToFalse = !toSet && foundBoolean;

        return setToTrue || setToFalse;
    }

    private String getOptionTestVehicle(VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN) {
        return vehicleOverviewPageSpecificVIN.getOptionsChBTestVehicle().getAttribute("ng-reflect-model");
    }

    private String getOptionPrototypeVehicle(VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN) {
        return vehicleOverviewPageSpecificVIN.getOptionsChBPrototypeVehicle().getAttribute("ng-reflect-model");
    }

    private String getOptionSoftwareVersionComparisonDisabled(VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN) {
        return vehicleOverviewPageSpecificVIN.getOptionsChBSoftwareVersionComparisonDisabled().getAttribute("ng-reflect-model");
    }

    private String getOptionMassNotification(VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN) {
        return vehicleOverviewPageSpecificVIN.getOptionsChBMassNotification().getAttribute("ng-reflect-model");
    }

    /**
     * Initialise VIN queues in exception list.
     *
     * @param vehicleOverviewPageSpecificVIN
     * @param vehicleOverviewPage
     * @param adminPortletHomepage
     */
    public void initVehicleOverviewQueuesOfVINs(
            List<String> listVINs,
            AdminPortletHomepage adminPortletHomepage,
            VehicleOverviewPage vehicleOverviewPage,
            VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN) {

        try {

            for (String vin : listVINs) {

                initVehicleOverviewQueues(
                        vin,
                        adminPortletHomepage,
                        vehicleOverviewPage,
                        vehicleOverviewPageSpecificVIN);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean isCampaignInActiveQueue(
            WebDriver webDriver,
            String recallId,
            VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN)
            throws InterruptedException {

        vehicleOverviewPageSpecificVIN.clickActiveCampaignPanelButtonLoad();

        Thread.sleep(5000);

        WebElement we = vehicleOverviewPageSpecificVIN.getActiveCampaignById(recallId);

        if (null != we) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isCampaignInPendingQueue(
            WebDriver webDriver,
            String recallId,
            VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN)
            {

        vehicleOverviewPageSpecificVIN.clickPendingCampaignPanelButtonLoad();

        WebElement we = vehicleOverviewPageSpecificVIN.getPendingCampaignById(recallId);

        if (null != we) {
            return true;
        } else {
            return false;
        }

    }

}
