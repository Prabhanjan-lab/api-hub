package de.sulzer.pages.testingmenu.service42;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.campaignmanagement.InboxPage;
import de.sulzer.pages.genericelements.PageWithNavigation;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ManualService42SystemPage extends PageWithNavigation {

    private final WebDriver driver;

    public ManualService42SystemPage(WebDriver driver) {

        super(driver);
        this.driver = driver;
    }

    @FindBy(id = "btn-s42-add-s42")
    private WebElement createService42;

    @FindBy(id = "p-panel-22-titlebar")
    private WebElement panelTitle;

    @FindBy(id = "fltr-s42-tbl-tbl")
    private WebElement searchField;

    @FindBy(xpath = "(//table/tbody/tr/td[1])[1]")
    private WebElement firstAmCode;

    @FindBy(xpath = "(//table/tbody/tr/td[2])[1]")
    private WebElement amDescription;

    @FindBy(xpath = "(//table/tbody/tr/td[3])[1]")
    private WebElement amId;

    @FindBy(xpath = "(//table/tbody/tr/td[4])[1]")
    private WebElement tnr;

    @FindBy(xpath = "(//table/tbody/tr/td[5])[1]")
    private WebElement nameFlashmedium;

    @FindBy(xpath = "(//table/tbody/tr/td[6])[1]")
    private WebElement statusFlashmedium;

    @FindBy(xpath = "(//table/tbody/tr/td[7])[1]")
    private WebElement editor;

    @FindBy(xpath = "(//table/tbody/tr/td[8])[1]")
    private WebElement oruCampaign;

    @FindBy(xpath = "(//table/tbody/tr/td[9])[1]")
    private WebElement oruCriteria;

    @FindBy(xpath = "(//button[@id='btn-s42-tbl-edit'])[1]")
    private WebElement editService42;

    @FindBy(id = "btn-s42-mod-save")
    private WebElement saveEditService42;

    @FindBy(xpath = "(//button[@id='btn-s42-tbl-del'])[1]")
    private WebElement deleteService42;

    @FindBy(id = "btn-del-confirm-confirm")
    private WebElement confirmDeletion;

    @FindBy(xpath = "(//button[@id='btn-s42-tbl-del-flash'])[1]")
    private WebElement deleteService42Attachment;

    @FindBy(id = "btn-s42-del-flash-mod-apply")
    private WebElement modalDialogDeleteFlashMediumConfirmationYes;

    @FindBy(id = "comp-number-of-entries")
    private WebElement numberOfEntriesParagraph;

    @FindBy(xpath = "//span[.='File deletion failed.']")
    private WebElement fileDeletionFailedMessage;

    @FindAll({
            @FindBy(xpath = "//div//tbody//tr") // all rows of table
    })
    private List<WebElement> listService42;

    @FindBy(id = "btn-s42-del-flash-mod-apply")
    private WebElement buttonDeleteYes;

    @FindBy(id = "btn-del-confirm-ok")
    private WebElement deleteConfirmFailOkButton;

    @FindBy(id = "inf-s42-tbl-entries-empty")
    private WebElement noRecordsFound;

    @FindBy(id = "btn-err-mod-ok")
    private WebElement flashmediumErrorOkButton;

    public WebElement getCreateService42() {
        return createService42;
    }

    public void clickCreateService42Button() {
        saveClick(getCreateService42());
    }

    public WebElement getPanelTitle() {
        return panelTitle;
    }

    public String getPanelTitleText() {
        return this.getPanelTitle().getText().trim();
    }

    public WebElement getNumberOfEntriesParagraph() {
        return numberOfEntriesParagraph;
    }

    public List<WebElement> getWebElements() {
        return this.listService42;
    }

    public WebElement getConfirmDeleteButton() {
        return confirmDeletion;
    }

    public void clickConfirmDeletion() {
        this.getConfirmDeleteButton().click();
    }

    public WebElement getEditButton() {
        return editService42;
    }

    public void clickEditService42() {
        this.getEditButton().click();
    }

    public WebElement getSaveEditService42Button() {
        return saveEditService42;
    }

    public void clickSaveEditService42() {
        this.getSaveEditService42Button().click();
    }

    public WebElement getDeleteButton() {
        return deleteService42;
    }

    public void clickDeleteService42() {
        this.getDeleteButton().click();
    }

    public WebElement getDeleteButtonAttachment() {
        return deleteService42Attachment;
    }

    public void clickDeleteService42Attachment() {
        this.getDeleteButtonAttachment().click();
    }


    public WebElement getModalDialogDeleteFlashMediumConfirmationYes() {
        return modalDialogDeleteFlashMediumConfirmationYes;
    }

    public void clickModalDialogDeleteFlashMediumConfirmationYes() {
        this.getModalDialogDeleteFlashMediumConfirmationYes().click();
    }

    public WebElement getFirstAmCode() {
        return firstAmCode;
    }

    public String getAmCodeText() {
        return this.getFirstAmCode().getText();
    }

    public WebElement getAmDescription() {
        return amDescription;
    }

    public String getAmDescriptionText() {
        return this.getAmDescription().getText().trim();
    }

    public WebElement getAmId() {
        return amId;
    }

    public String getAmIdText() {
        return this.getAmId().getText();
    }

    public WebElement getTnr() {
        return tnr;
    }

    public String getTnrText() {
        return this.getTnr().getText();
    }

    public WebElement getNameFlashmedium() {
        return nameFlashmedium;
    }

    public String getNameFlashmediumText() {
        return this.getNameFlashmedium().getText().trim();
    }

    public WebElement getStatusFlashmedium() {
        return statusFlashmedium;
    }

    public String getStatusFlashmediumText() {
        return this.getStatusFlashmedium().getText().trim();
    }

    public WebElement getEditor() {
        return editor;
    }

    public String getEditorText() {
        return this.getEditor().getText();
    }

    public WebElement getOruCampaign() {
        return oruCampaign;
    }

    public String getOruCampaignText() {
        return this.getOruCampaign().getText();
    }

    public WebElement getOruCriteria() {
        return oruCriteria;
    }

    public String getOruCriteriaText() {
        return this.getOruCriteria().getText();
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public WebElement getFileDeletionFailedMessage() {
        return fileDeletionFailedMessage;
    }

    public WebElement getDeleteConfirmFailOkButton() {
        return deleteConfirmFailOkButton;
    }

    public WebElement getNoRecordsFound() {
        return noRecordsFound;
    }

    public WebElement getFlashmediumErrorOkButton() {
        return flashmediumErrorOkButton;
    }


    // TODO
    public void searchService42(String input) {

        ReusableMethods.clearField(getSearchField());
        this.getSearchField().sendKeys(Keys.ENTER);

        ReusableMethods.waitForClickablility(getSearchField(), 3, _driver);
        this.getSearchField().sendKeys(input);
        this.getSearchField().sendKeys(Keys.ENTER);

        ReusableMethods.waitForVisibility(getNumberOfEntriesParagraph(), 5, _driver);
        int i3 = 0;
        int f = 15;
        while (i3 == 0 && f > 0) {
            String numberOfEntriesText = this.getNumberOfEntriesParagraph().getText();
            System.out.println(numberOfEntriesText);
            if (numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
                i3 = 1;
            } else {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            f--;
            if (f == 1) {
                throw new java.lang.Error("Timeout after 1 minute!");
            }
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void searchService42Simple(String searchItem) {

        System.out.println("Searching Service42 AM " + searchItem + "...");
        ReusableMethods.waitForClickablility(getSearchField(), 5, _driver);
        ReusableMethods.clearField(getSearchField());
        this.getSearchField().sendKeys(searchItem);
        this.getSearchField().sendKeys(Keys.ENTER);
        if (isService42ElementPresent(searchItem,_driver)){
            System.out.println("Service42 AM " + searchItem + " found.");
        } else System.out.println("Service42 AM " + searchItem + " is not found.");

    }


    public void searchService42Inbox(String input) {

        InboxPage inboxPage = new InboxPage(_driver);
        System.out.println("Searching Service42 AM " + input + "...");
        inboxPage.clickOlderThan100DaysSwitch();
        ReusableMethods.waitForClickablility(inboxPage.getService42SearchField(), 25, _driver);
        inboxPage.getService42SearchField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        inboxPage.getService42SearchField().sendKeys(input);

        try {
            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("//span[.='" + input + "']")), 25, _driver);
            ReusableMethods.screenShot("Searching Service42 AM ", _driver);
            System.out.println("Service42 AM " + input + " found.");
        } catch (Exception e) {
            System.out.println("This Service42 AM " + input + " does not exist.");
        }

    }

    public void searchService42TestingMenu(String AM_ID, WebDriver driver) {

        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);

        ReusableMethods.openTestingMenu(adminPortletHomepage, driver);
        ReusableMethods.clickElement(adminPortletHomepage.getManualService42(), driver);
        System.out.println("Searching Service42 AM " + AM_ID + "...");
        ReusableMethods.waitForClickablility(getSearchField(), 10, driver);
        getSearchField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        getSearchField().sendKeys(AM_ID);

        try {
            ReusableMethods.waitForVisibility(driver.findElement(By.xpath("(//tr/td[.=' " + AM_ID + " '])[1]")), 15, driver);
            System.out.println("Service42 AM " + AM_ID + " found.");
        } catch (Exception e) {
            System.out.println("This Service42 AM " + AM_ID + " does not exist.");
        }
    }

    //method needs size as input - when calling function you need to pass "small" as parameter to have max 2 minute timeout - otherwise it's 45 minutes for testing e.g. the upload of a 5 GB file.
    public void waitForUploadCompleted(String input, String size) {

        getSearchField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        getSearchField().sendKeys(input);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String numberOfEntriesText = this.getNumberOfEntriesParagraph().getText();
        assertTrue(numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS), numberOfEntriesText);

        int i = 0;
        int i2 = 0;
        int timeout;
        String errorMessage;
        int intervall;

        if (size.toLowerCase().contentEquals("small")) {
            timeout = 360;
            errorMessage = "Timeout after 6 minutes!";
            intervall = 1000;
        } else {
            timeout = 45;
            errorMessage = "Timeout after 45 minutes!";
            intervall = 60000;
        }

        while (i == 0) {

            String status;

            try {
                status = this.getStatusFlashmediumText();
            } catch (Exception e) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ManualService42SystemPage.class.getName()).log(Level.SEVERE, null, ex);
                }

                status = this.getStatusFlashmediumText();

            }

            if (status.contains("UPLOADED")) {
                i = 1;
            } else {

                if (i2 > timeout) {
                    throw new java.lang.Error(errorMessage);
                } else {

                    i2++;

                    try {
                        Thread.sleep(intervall);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ManualService42SystemPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    //method needs size as input - when calling function you need to pass "small" as parameter to have max 2 minute timeout - otherwise it's 45 minutes for testing e.g. the upload of a 5 GB file.
    public void waitForUploadCompleted(String service42) {

        String numberOfEntriesText = this.getNumberOfEntriesParagraph().getText();

        assertTrue(numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS));

        WebDriverWait wait = new WebDriverWait(this.driver, 60 * 3);

        WebElement we = this.driver.findElement(By.xpath("//table//tr//td[6]"));

        wait.until(ExpectedConditions.textToBePresentInElement(
                we,
                "UPLOADED"));

    }


    public boolean deleteService42(String searchItem, WebDriver driver) {

        searchService42Simple(searchItem);
        boolean isS42Present = isService42ElementPresent(searchItem, driver);
        if (isS42Present) {
            try {
//                this.searchService42(searchItem);
                this.clickDeleteService42();
                this.clickConfirmDeletion();
                assertTrue(getDeleteConfirmFailOkButton().isDisplayed());
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletion of first entry in table!
     *
     * @return true, in case entry was deleted, otherwise false
     */
    public boolean deleteService42() {
        try {
            this.clickDeleteService42();
            this.clickConfirmDeletion();
            assertTrue(getDeleteConfirmFailOkButton().isDisplayed());
        } catch (Exception e) {
            return true;
        }

        return false;
    }

    public WebElement service42WebElementWithText(String s42_am_code, WebDriver webDriver) {

        String xpathExpression = "//span[@id='inf-s42-tbl-am-code' and text()=' "+ s42_am_code +" ']";

        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
        } catch (TimeoutException ignored) {
        }

        return webDriver.findElement(By.xpath(xpathExpression));

    }


    public boolean isService42ElementPresent(String s42_am_code, WebDriver webDriver) {

        try {
            WebDriverWait wait = new WebDriverWait(webDriver, 5);
            wait.until(ExpectedConditions.visibilityOf(service42WebElementWithText(s42_am_code, webDriver)));
            return true;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }

    }


    public WebElement getFlashmediumButtonDeleteYes() {
        return buttonDeleteYes;
    }


    public void clickFlashmediumButtonDeleteYes() {
        this.getFlashmediumButtonDeleteYes().click();
    }

}
