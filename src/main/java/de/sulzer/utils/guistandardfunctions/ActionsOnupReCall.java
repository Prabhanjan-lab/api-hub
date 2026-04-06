/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.campaignmanagement.CampaignManagementDisplayCampaign;
import de.sulzer.pages.campaignmanagement.CampaignManagementSearch;
import de.sulzer.pages.genericelements.Page;
import de.sulzer.pages.testingmenu.recall.AddRecallSystemModal;
import de.sulzer.pages.testingmenu.recall.EditRecallSystemModal;
import de.sulzer.pages.testingmenu.recall.ManualRecallSystemPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Sulzer GmbH
 */
public class ActionsOnupReCall extends Page {

    protected WebDriver webDriver;
    public static final String COMMENT = "Testautomatisierung Sulzer";

    /**
     *
     */
    public ActionsOnupReCall(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }

    /**
     * This method creates a ReCall and assumes, that @see AdminPortletHomepage is available and usable.
     *
     * @param adminPortletHomepage
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param recallId
     * @param recallTitle
     * @param disabledCountry
     * @param recallVin
     * @param webDriver
     */
    public void createReCall(
            AdminPortletHomepage adminPortletHomepage,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            String recallId,
            String recallTitle,
            String disabledCountry,
            String recallVin,
            WebDriver webDriver) {

        System.out.println("Recall " + recallId + " is being created...");

        openReCallMenu(adminPortletHomepage);

        manualRecallSystemPage.clickAddRecallButton();

        addRecallSystemModal.createRecallnCriteria(recallId, recallTitle, 1, disabledCountry, recallVin, ActionsOnupReCall.COMMENT, webDriver);

        System.out.println("Recall " + recallId + " is created successfully!");
    }

    /**
     * This method creates a ReCall and assumes, that @see AdminPortletHomepage is available and usable.
     *
     * @param adminPortletHomepage
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param recallId
     * @param recallTitle
     * @param disabledCountry
     * @param recallVin
     * @param webDriver
     */
    public void createReCall(
            AdminPortletHomepage adminPortletHomepage,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            String recallId,
            String recallTitle,
            String disabledCountry,
            int amountCriteria,
            String recallVin,
            WebDriver webDriver) {

        System.out.println("Creating a Recall " + recallId + "...");

        openReCallMenu(adminPortletHomepage);

        manualRecallSystemPage.clickAddRecallButton();

        addRecallSystemModal.createRecallnCriteria(
                recallId,
                recallTitle,
                amountCriteria,
                disabledCountry,
                recallVin,
                ActionsOnupReCall.COMMENT,
                webDriver);

        System.out.println("Recall " + recallId + " is created successfully!");
    }

    /**
     * This method creates a ReCall and assumes, that @see AdminPortletHomepage is available and usable.
     *
     * @param adminPortletHomepage
     * @param manualRecallSystemPage
     * @param addRecallSystemModal
     * @param recallId
     * @param disabledCountry
     * @param recallVin
     * @param webDriver
     */
    public void createReCall(
            AdminPortletHomepage adminPortletHomepage,
            ManualRecallSystemPage manualRecallSystemPage,
            AddRecallSystemModal addRecallSystemModal,
            String recallId,
            String disabledCountry,
            String recallVin,
            WebDriver webDriver) {

        System.out.println("Creating a Recall " + recallId + "...");

        openReCallMenu(adminPortletHomepage);

        manualRecallSystemPage.clickAddRecallButton();

        ReusableMethods.waitForPageToLoad(5, webDriver);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        addRecallSystemModal.createRecallnCriteria(
                recallId,
//                CreateRandomChars.getLetters35(15),
                recallId,
                1,
                disabledCountry,
                recallVin,
                COMMENT,
                webDriver);

        System.out.println("Recall " + recallId + " is created successfully!");
    }

    /**
     * @param wait
     * @param adminPortletHomepage
     * @param manualRecallSystemPage
     * @param recallId
     */
//    public void deleteReCall(WebDriverWait wait,
//                             AdminPortletHomepage adminPortletHomepage,
//                             ManualRecallSystemPage manualRecallSystemPage,
//                             String recallId) {
//
//        WebElement firstCell = manualRecallSystemPage.getFirstRowActionID();
//
//        try {
//            if (recallId == null) {
//                System.out.println("Recall is null");
//            } else {
//                manualRecallSystemPage.searchReCallSimple(recallId);
//                ReusableMethods.waitForVisibility(firstCell, 20, webDriver);
//            }
//        } catch (Exception ignored) {
//        }
//
//        boolean isPresent = false;
//        try {
//            assert recallId != null;
//            isPresent = firstCell.getText().trim().contains(recallId);
//        } catch (Exception ignored) {
//            System.out.println("Recall " + recallId + " was not found, so the deletion did not happen.");
//        }
//
//        // checking if table row matches searched recall ID
//        if (isPresent) {
//            // click delete button
//            ReusableMethods.clickElement(manualRecallSystemPage.getDeleteRecallButton(), 3, webDriver);
//
//            // click confirmation button
//            manualRecallSystemPage.clickConfirmDeletion();
//            System.out.println("Recall " + recallId + " has been deleted successfully!");
//        }
//
//    }
    public void deleteReCall(WebDriverWait wait,
                             AdminPortletHomepage adminPortletHomepage,
                             ManualRecallSystemPage manualRecallSystemPage,
                             String recallId) {

        manualRecallSystemPage.searchReCallSimple(recallId);

        boolean isPresent = manualRecallSystemPage.isRecallElementPresent(webDriver, recallId);

        if (isPresent) {
            // click delete button
            ReusableMethods.clickElement(manualRecallSystemPage.getDeleteRecallButton(), 3, webDriver);

            // click confirmation button
            manualRecallSystemPage.clickConfirmDeletion();
            System.out.println("Recall " + recallId + " has been deleted successfully!");
        } else
            System.out.println("Recall " + recallId + " was not found, so the deletion did not happen.");
    }

    /**
     * @param adminPortletHomepage
     */
    public void openReCallMenu(AdminPortletHomepage adminPortletHomepage) {
        // checking open menu
        ReusableMethods.openTestingMenu(adminPortletHomepage, webDriver);

        // making sure, ReCall is selected, and not service42
        ReusableMethods.clickElement(adminPortletHomepage.getManualRecallSystem(), 3, webDriver);
    }

    /**
     *
     */
    public void checkCampaignExistence(
            AdminPortletHomepage adminPortletHomepage,
            CampaignManagementSearch campaignManagementSearch,
            CampaignManagementDisplayCampaign campaignManagementDisplayCampaign,
            String campaignId,
            WebDriver webDriver) {

        new ActionsOnupOruCampaign(webDriver).searchAndOpenCampaign(
                adminPortletHomepage,
                campaignManagementSearch,
                campaignId,
                webDriver);

        String campaignTitle = campaignManagementDisplayCampaign.getTitleDisplayedORUCampaignId().getText().trim();

        assertTrue(campaignTitle.contains(campaignId), "ORU Campaign '" + campaignId + "' not found, but '" + campaignTitle + "'.");

    }

    /**
     * This method searches for ReCall and assumes, that @see AdminPortletHomepage is available and usable.
     *
     * @param adminPortletHomepage
     * @param manualRecallSystemPage
     * @param recallId               //     * @param recallVin
     * @param webDriver
     * @return boolean - ReCall ID was found in case of true, otherwise false
     */
    public boolean isReCallExisiting(
            AdminPortletHomepage adminPortletHomepage,
            ManualRecallSystemPage manualRecallSystemPage,
            String recallId,
            WebDriver webDriver) {

        openReCallMenu(adminPortletHomepage);

        searchReCallId(manualRecallSystemPage, recallId);

        List<WebElement> elements = manualRecallSystemPage.getWebElements();

        boolean found = false;

        for (WebElement element : elements) {

            String str = element.getText().trim();

            if (str.contains(recallId)) {
                found = true;
                break;
            }

        }

        return found;

    }

    public void changeReCallId(
            AdminPortletHomepage adminPortletHomepage,
            ManualRecallSystemPage manualRecallSystemPage,
            EditRecallSystemModal editRecallSystemModal,
            String recall01Id,
            String recallChangeId,
            WebDriver webDriver) {

        openReCallMenu(adminPortletHomepage);

        searchReCallId(manualRecallSystemPage, recall01Id);

        manualRecallSystemPage.clickEditRecallButton();

        editRecallSystemModal.clearRecallActionIdInputfield();

        editRecallSystemModal.setRecallActionIdInputFieldText(recallChangeId);

        ReusableMethods.waitForClickablility(editRecallSystemModal.getSaveButton(), 5, webDriver);
        editRecallSystemModal.clickSaveButton();

    }

    /**
     * @param manualRecallSystemPage
     * @param recallId
     */
    public void searchReCallId(ManualRecallSystemPage manualRecallSystemPage, String recallId) {

        manualRecallSystemPage.searchReCallSimple(recallId);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
