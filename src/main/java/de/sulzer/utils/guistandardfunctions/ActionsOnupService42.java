/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.blockingdialogues.overlay.OverlayHandler;
import de.sulzer.pages.campaignmanagement.InboxPage;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.utils.CreateRandomChars;
import testFramework.utilities.ReusableMethods;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sulzer GmbH
 */
public class ActionsOnupService42 {

    private WebDriver webDriver;

    public ActionsOnupService42(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param amCode
     * @param description
     * @param amId
     * @param TnrNumber
     * @param flashMedium
     * @param comment
     * @param webDriver
     */
    public void createService42WaitUploadCompleted(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            String amCode,
            String description,
            String amId,
            String TnrNumber,
            String flashMedium,
            String comment,
            WebDriver webDriver) {

        System.out.println("Creating a Service42 AM " + amCode + "...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.openService42Menu(adminPortletHomepage);
        manualService42SystemPage.clickCreateService42Button();
        addService42SystemModal.createService42(amCode, description, amId, TnrNumber, flashMedium, comment);
        manualService42SystemPage.waitForUploadCompleted(amCode, "small");

        System.out.println("Service42 AM " + amCode + " is created successfully!");

    }


    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param amCode
     * @param flashMedium
     * @param webDriver
     */
    public void createService42WaitUploadCompleted(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            String amCode,
            String flashMedium,
            WebDriver webDriver) {

        System.out.println("Creating a Sercice42 AM " + amCode + "...");

        this.openService42Menu(adminPortletHomepage);

        manualService42SystemPage.clickCreateService42Button();

//        String description = CreateRandomChars.getLetters35(15);
        String description;
        description = amCode;

        addService42SystemModal.createService42(
                amCode,
                description,
                CreateRandomChars.getNumbers10(20),
                CreateRandomChars.getLetters35(11),
                flashMedium,
                ActionsOnupReCall.COMMENT);

        manualService42SystemPage.waitForUploadCompleted(description, "small");

        System.out.println("Service42 AM " + amCode + " is created successfully!");

    }

    /**
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param amCode
     * @param flashMedium
     * @param webDriver
     */
    public void createService42WaitUploadCompleted5G(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            String amCode,
            String flashMedium,
            WebDriver webDriver) {

        System.out.println("Creating a Sercice42 AM " + amCode + "...");

        this.openService42Menu(adminPortletHomepage);

        manualService42SystemPage.clickCreateService42Button();

        String description = CreateRandomChars.getLetters35(15);

        addService42SystemModal.createService42(
                amCode,
                description,
                CreateRandomChars.getNumbers10(20),
                CreateRandomChars.getLetters35(11),
                flashMedium,
                ActionsOnupReCall.COMMENT);

        manualService42SystemPage.waitForUploadCompleted(description, "humongous");

        System.out.println("Service42 AM " + amCode + " is created successfully!");

    }

    /**
     * Creatig a given Service42 and NOT waiting for finished upload of flash medium.
     *
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param amCode
     * @param description
     * @param amId
     * @param TnrNumber
     * @param flashMedium
     * @param comment
     * @param webDriver
     */
    public void createService42NoWaitUploadCompleted(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            String amCode,
            String description,
            String amId,
            String TnrNumber,
            String flashMedium,
            String comment,
            WebDriver webDriver) {

        System.out.println("Creating a Sercice42 AM " + amCode + "...");

        this.openService42Menu(adminPortletHomepage);

        manualService42SystemPage.clickCreateService42Button();

        addService42SystemModal.createService42(amCode, description, amId, TnrNumber, flashMedium, comment);

        System.out.println("Service42 AM " + amCode + " is created successfully!");
        // intentionally not waiting for finished upload!
    }

    /**
     * Creatig a given Service42 and NOT waiting for finished upload of flash medium.
     *
     * @param adminPortletHomepage
     * @param manualService42SystemPage
     * @param addService42SystemModal
     * @param amCode
     * @param flashMedium
     * @param webDriver
     */
    public void createService42NoWaitUploadCompleted(
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            AddService42SystemModal addService42SystemModal,
            String amCode,
            String flashMedium,
            WebDriver webDriver) {

        System.out.println("Creating a Sercice42 AM " + amCode + "...");

        this.openService42Menu(adminPortletHomepage);

        manualService42SystemPage.clickCreateService42Button();

        String description = CreateRandomChars.getLetters35(15);

        addService42SystemModal.createService42(
                amCode,
                description,
                CreateRandomChars.getNumbers10(20),
                CreateRandomChars.getLetters35(11),
                flashMedium,
                ActionsOnupReCall.COMMENT);

        System.out.println("Service42 AM " + amCode + " is created successfully!");
        // intentionally not waiting for finished upload!
    }

    /**
     * This method is searching for a service42 in 'Campaign Management'. It
     * selects it independently from younger/older 100 days.
     *
     * @param webDriver
     * @param wait
     * @param amCode
     * @param inboxPage
     * @throws InterruptedException
     * @throws Exception
     */
    public void searchService42InCampaignMgmInbox(WebDriver webDriver,
                                                  WebDriverWait wait,
                                                  String amCode,
                                                  InboxPage inboxPage) {


        System.out.println("Searching Service42 AM " + amCode + "...");
        inboxPage.clickOlderThan100DaysSwitch();
        ReusableMethods.waitForClickablility(inboxPage.getService42SearchField(), 25, webDriver);
        inboxPage.getService42SearchField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        inboxPage.getService42SearchField().sendKeys(amCode);

        try {
            ReusableMethods.waitForVisibility(webDriver.findElement(By.xpath("//span[.='" + amCode + "']")), 30, webDriver);
            System.out.println("Service42 AM " + amCode + " found.");
        } catch (Exception e) {
            System.out.println("This Service42 AM " + amCode + " does not exist.");
        }

    }

//    }

    /**
     * @param wait
     * @param adminPortletHomepage
     * @param amCode
     * @param manualService42SystemPage
     * @return
     */
//    public void deleteService42(
//            WebDriverWait wait,
//            AdminPortletHomepage adminPortletHomepage,
//            ManualService42SystemPage manualService42SystemPage,
//            String amCode) {
//
//
//        openService42Menu(adminPortletHomepage);
//
//            // searching for element, if already existing
//            manualService42SystemPage.searchService42Simple(amCode);
//            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));
//            if (!manualService42SystemPage.getNumberOfEntriesParagraph().getText().trim().equals(ConstantsGuiTexts.ZERO_OF_ZERO_FROM_ZERO_RECORDS)) {
//
//                // deleting created Service42 by AM-Code
//                for (WebElement element : manualService42SystemPage.getWebElements()) {
//
//                    WebElement firstCell = element.findElement(By.xpath("./td[1]"));
//
//                    if (firstCell.getText().trim().contains(amCode)) {
//                        element.findElement(By.xpath(".//button[contains(@class, 'btn btn-danger btn-xs')][1]")).click();
//                        manualService42SystemPage.clickConfirmDeletion();
//                        try {
//                            assertTrue(manualService42SystemPage.getFileDeletionFailedMessage().isDisplayed(), "Service42 AM " + amCode + " cannot be deleted!" );
//                            System.out.println("Service42 AM " + amCode + " cannot be deleted! ");
//                        } catch (Exception e) {
//                            System.out.println("Service42 AM " + amCode + " has been deleted successfully!");
//                        }
//                    }
//                }
//            }
//    }





//    public void deleteFlashFileAndService42(
//            WebDriverWait wait,
//            AdminPortletHomepage adminPortletHomepage,
//            ManualService42SystemPage manualService42SystemPage,
//            String amCode) {
//
//        openService42Menu(adminPortletHomepage);
//
//        // searching for element, if already existing
//        manualService42SystemPage.searchService42Simple(amCode);
//        boolean isS42Present = manualService42SystemPage.isService42ElementPresent(amCode, webDriver);
//
//        if (isS42Present) {
//
//            manualService42SystemPage.clickDeleteService42Attachment();
//
//            try {
//                adminPortletHomepage.getNotAuthorisedOkButton().click();
//                System.out.println("The flash file has already been deleted.");
//            } catch (Exception ignored) {
//                manualService42SystemPage.clickFlashmediumButtonDeleteYes();
//            }
//
//            // implicit and explicit wait does not work here.
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            ReusableMethods.clickElement(manualService42SystemPage.getDeleteButton(), 5, webDriver);
//
//            // implicit and explicit wait does not work here.
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
////            manualService42SystemPage.clickConfirmDeletion();
//            ReusableMethods.clickJS(manualService42SystemPage.getConfirmDeleteButton(), webDriver);
//
//            try {
//                ReusableMethods.waitForClickablility(manualService42SystemPage.getNoRecordsFound(), 5, webDriver);
//                System.out.println("Service42 AM " + amCode + " has been deleted successfully!");
//            } catch (Exception e) {
//                System.out.println("Service42 AM " + amCode + " cannot be deleted! ");
//            }
//
//        } else System.out.println("Service42 AM " + amCode +  " was not found, so the deletion did not happen.");
//    }






//    public void deleteFlashFileAndService42(
//            WebDriverWait wait,
//            AdminPortletHomepage adminPortletHomepage,
//            ManualService42SystemPage manualService42SystemPage,
//            String amCode) {
//
//        openService42Menu(adminPortletHomepage);
//
//        // searching for element, if already existing
//        manualService42SystemPage.searchService42Simple(amCode);
//        boolean isS42Present = manualService42SystemPage.isService42ElementPresent(amCode, webDriver);
//
//        if (isS42Present) {
//
//            manualService42SystemPage.clickDeleteService42Attachment();
//            boolean isGetNotAuthorisedOkButton = ReusableMethods.isWebElementPresent(adminPortletHomepage.getNotAuthorisedOkButton(), webDriver);
//
//                if (isGetNotAuthorisedOkButton) {
//                    adminPortletHomepage.getNotAuthorisedOkButton().click();
//                    System.out.println("The flash file has already been deleted.");
//                } else manualService42SystemPage.clickFlashmediumButtonDeleteYes();
//
//            // implicit and explicit wait does not work here.
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            ReusableMethods.clickElement(manualService42SystemPage.getDeleteButton(), 5, webDriver);
//
//            // implicit and explicit wait does not work here.
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            ReusableMethods.clickJS(manualService42SystemPage.getConfirmDeleteButton(), webDriver);
//
//            boolean isGetNoRecordsFound = ReusableMethods.isWebElementPresent(manualService42SystemPage.getNoRecordsFound(), webDriver);
//                if (isGetNoRecordsFound) {
//                    ReusableMethods.waitForClickablility(manualService42SystemPage.getNoRecordsFound(), 5, webDriver);
//                    System.out.println("Service42 AM " + amCode + " has been deleted successfully!");
//                } else {
//                    System.out.println("Service42 AM " + amCode + " cannot be deleted! ");
//                }
//
//        } else System.out.println("Service42 AM " + amCode +  " was not found, so the deletion did not happen.");
//    }




    public void deleteFlashFileAndService42(
            WebDriverWait wait,
            AdminPortletHomepage adminPortletHomepage,
            ManualService42SystemPage manualService42SystemPage,
            String amCode) {

        openService42Menu(adminPortletHomepage);

        // searching for element, if already existing
        manualService42SystemPage.searchService42Simple(amCode);
//        ReusableMethods.checkTime(426);
        boolean isS42Present = manualService42SystemPage.isService42ElementPresent(amCode, webDriver);

        if (isS42Present) {
//            ReusableMethods.checkTime(430);
            manualService42SystemPage.clickDeleteService42Attachment();
//            ReusableMethods.checkTime(432);
//            boolean isGetNotAuthorisedOkButton = ReusableMethods.isWebElementPresent(adminPortletHomepage.getNotAuthorisedOkButton(), webDriver);
//
//            if (isGetNotAuthorisedOkButton) {
//                adminPortletHomepage.getNotAuthorisedOkButton().click();
//                System.out.println("The flash file has already been deleted.");
//            } else manualService42SystemPage.clickFlashmediumButtonDeleteYes();

            try {
                ReusableMethods.clickElement(manualService42SystemPage.getFlashmediumButtonDeleteYes(), 5, webDriver);
//                manualService42SystemPage.clickFlashmediumButtonDeleteYes();
            } catch (Exception e) {
                ReusableMethods.clickElement(adminPortletHomepage.getNotAuthorisedOkButton(), webDriver);
                System.out.println("The flash file has already been deleted.");
            }

            // implicit and explicit wait does not work here.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ReusableMethods.clickElement(manualService42SystemPage.getDeleteButton(), 5, webDriver);

            // implicit and explicit wait does not work here.
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ReusableMethods.clickJS(manualService42SystemPage.getConfirmDeleteButton(), webDriver);

            boolean isGetNoRecordsFound = ReusableMethods.isWebElementPresent(manualService42SystemPage.getNoRecordsFound(), webDriver);
            if (isGetNoRecordsFound) {
                ReusableMethods.waitForClickablility(manualService42SystemPage.getNoRecordsFound(), 5, webDriver);
                System.out.println("Service42 AM " + amCode + " has been deleted successfully!");
            } else {
                System.out.println("Service42 AM " + amCode + " cannot be deleted! ");
            }

        } else System.out.println("Service42 AM " + amCode +  " was not found, so the deletion did not happen.");
    }



    public void deleteService42(

        WebDriverWait wait,
        AdminPortletHomepage adminPortletHomepage,
        ManualService42SystemPage manualService42SystemPage,
        String amCode) {

            openService42Menu(adminPortletHomepage);

            // searching for element, if already existing
            manualService42SystemPage.searchService42Simple(amCode);
            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));
            boolean isS42Present = manualService42SystemPage.isService42ElementPresent(amCode, webDriver);

            if (isS42Present) {
                manualService42SystemPage.clickDeleteService42();
                manualService42SystemPage.clickConfirmDeletion();

                try {
                    assertTrue(manualService42SystemPage.getFileDeletionFailedMessage().isDisplayed(), "Service42 AM " + amCode + " cannot be deleted!");
                    System.out.println("Service42 AM " + amCode + " cannot be deleted! ");
                } catch (Exception e) {
                    System.out.println("Service42 AM " + amCode + " has been deleted successfully!");
                }
            }
    }

    public void deleteFlashFile(

        WebDriverWait wait,
        AdminPortletHomepage adminPortletHomepage,
        ManualService42SystemPage manualService42SystemPage,
        String amCode) {

            openService42Menu(adminPortletHomepage);

            // searching for element, if already existing
            manualService42SystemPage.searchService42Simple(amCode);
            ReusableMethods.screenShot("1", webDriver);
            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));
            boolean isS42Present = manualService42SystemPage.isService42ElementPresent(amCode, webDriver);

            if (isS42Present) {
                manualService42SystemPage.clickDeleteService42Attachment();
                manualService42SystemPage.clickFlashmediumButtonDeleteYes();

                try {
                    assertTrue(manualService42SystemPage.getFileDeletionFailedMessage().isDisplayed(), "Service42 AM " + amCode + " cannot be deleted!");
                    System.out.println("Flash file " + amCode + " cannot be deleted! ");
                } catch (Exception e) {
                    System.out.println("Flash file " + amCode + " has been deleted successfully!");
                }
            }
    }


    /**
     * @param adminPortletHomepage
     */
    public void openService42Menu(AdminPortletHomepage adminPortletHomepage) {

        ReusableMethods.waitForClickablility(adminPortletHomepage.getTestingMenu(), 10, webDriver);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // checking open menu
        if (!adminPortletHomepage.isAvailableMenuEntryService42() ||
                !adminPortletHomepage.getManualService42().isDisplayed()) {
            adminPortletHomepage.clickTestingMenu();
        }

        WebDriverWait wait = new WebDriverWait(this.webDriver, 30);

        wait.until(ExpectedConditions.visibilityOf(adminPortletHomepage.getManualService42()));

        // making sure, servive42 is selected, and not ReCall
        adminPortletHomepage.clickManualService42System();

    }

}