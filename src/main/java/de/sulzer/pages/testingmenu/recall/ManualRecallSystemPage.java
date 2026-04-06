package de.sulzer.pages.testingmenu.recall;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.sulzer.pages.AdminPortletHomepage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.PageWithNavigation;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

public class ManualRecallSystemPage extends PageWithNavigation {

    protected WebDriver webDriver;

    public ManualRecallSystemPage(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }


    @FindBy(id = "btn-recall-add")
    private WebElement addRecallButton;

    @FindBy(xpath = "//h3[.='Recall - Campaigns ']")
    private WebElement panelTitle;

    @FindBy(id = "fltr-recall-tbl-recalls")
    private WebElement searchRecallCampaignInputfield;

    @FindBy(id = "inf-number-of-entries-recs")
    private WebElement numberOfEntriesParagraph;

    @FindAll({
            @FindBy(xpath = "//table//tbody//tr")
    })
    private List<WebElement> listReCall;

    @FindBy(id = "btn-recall-tbl-edit")
    private WebElement editRecallButton;

    @FindBy(id = "btn-recall-tbl-del")
    private WebElement deleteRecallButton;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement applyDeleteRecallButton;

    @FindBy(xpath = "//*[@type='submit']")
    private WebElement confirmDeletion;

    //    @FindBy(xpath = "//table/tbody[@class='ui-table-tbody']/tr[1]/td[1]")
//    @FindBy(css = "tr.p-element > td:nth-child(1)")
    @FindBy(xpath = "(//tr[@class='p-element p-selectable-row ng-star-inserted']//td[1])[1]")
    private WebElement firstRowActionID;

    //    @FindBy(xpath = "//table/tbody[@class='ui-table-tbody']/tr[1]/td[2]")
    @FindBy(css = "tr.p-element > td:nth-child(2)")
    private WebElement firstRowActionTitle;

    //element found locally
//    @FindBy(xpath = "//table//tr/td[3]/a")
    @FindBy(xpath = "//table//tr//td[3]/restricted-block/a")
    private WebElement actionNumberVins;

    @FindBy(xpath = "//tr[1]/td[3]/span/a")
    private WebElement firstRowVinCount;

    @FindAll({
            @FindBy(xpath = "//*[@class='modal-body']//td")
    })
    private List<WebElement> vins;

    @FindBy(xpath = "//button[contains(text(),'Close')]")
    private WebElement closeModal;

    @FindBy(xpath = "//button[contains(text(),'Ok')]")
    private WebElement closeModalOk;

    public void clickCloseModal() {
        this.closeModal.click();
    }

    public void clickCloseModalOk() {
        this.closeModalOk.click();
    }

    public void checkIfVinInList(String inputVin) {
        int i = vins.size();
        if (i == 0) {
            throw new NoSuchElementException("No elements found in VIN list");
        }
        String check = "";
        for (int x = 0; x < i; x++) {
            if (vins.get(x).getText().contains(inputVin)) {
                check = "available";
            }
        }

        if (check.length() < 2) {
            throw new RuntimeException("Vin not found");
        }
    }

    public void clickActionNumberVins() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ManualRecallSystemPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        actionNumberVins.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ManualRecallSystemPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the firstRowVinCount
     */
    public WebElement getFirstRowVinCount() {
        return firstRowVinCount;
    }

    public void clickFirstRowVinCount() {
        this.getFirstRowVinCount().click();
    }

    public WebElement getFirstRowActionID() {
        return firstRowActionID;
    }

    public String getFirstActionIDText() {
        return this.getFirstRowActionID().getText();
    }

    public WebElement getFirstRowActionTitle() {
        return firstRowActionTitle;
    }

    public String getFirstRowActionTitleText() {
        return this.getFirstRowActionTitle().getText();
    }

    public WebElement getAddRecallButton() {
        ReusableMethods.pageUpAct(_driver);
        return addRecallButton;
    }

    public void clickAddRecallButton() {
        ReusableMethods.pageUpAct(_driver);
        this.getAddRecallButton().click();
    }

    public WebElement getPanelTitle() {
        return panelTitle;
    }

    public String getPanelTitleText() {
        return this.getPanelTitle().getText();
    }

    public WebElement getSearchRecallCampaignInputfield() {
        return searchRecallCampaignInputfield;
    }


//    replaced searchRecallCampaign method with searchRecallSimple method

//    public void searchRecallCampaign(String recall_ID) {
//
//        ReusableMethods.waitForClickablility(getFirstRowActionID(), 20, _driver);
//        System.out.println("Search ReCall : " + recall_ID);
//        getSearchRecallCampaignInputfield().clear();
//        getSearchRecallCampaignInputfield().sendKeys(recall_ID);
//
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ManualService42SystemPage.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        int i3 = 0;
//        int f = 15;
//
//        while (i3 == 0 && f>0) {
//            String numberOfEntriesText = this.getNumberOfEntriesParagraph().getText();
//            System.out.println(numberOfEntriesText);
//            if (numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
//                i3 = 1;
//            } else {
//                try {
//                    Thread.sleep(100);
//                } catch (Exception e){
//                    System.out.println("This Recall ID does not exist.");
//                }
//            }
//
//            f--;
//            if(f==1){
//                throw new java.lang.Error("Timeout after 1 minute!");
//            }
//        }
//    }


//    public void searchReCallSimple(String recall_ID) {
//
//        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(webDriver);
//        System.out.println("Searching ReCall " + recall_ID + "...");
////        ReusableMethods.checkTime(555);
//        ReusableMethods.waitForClickablility(adminPortletHomepage.getTestingMenu(), 10, webDriver);
////        ReusableMethods.checkTime(556);
//        ReusableMethods.openTestingMenu(adminPortletHomepage, webDriver);
//        ReusableMethods.waitForClickablility(adminPortletHomepage.getManualRecallSystem(), 10, _driver);
////        ReusableMethods.checkTime(557);
//        adminPortletHomepage.clickManualRecallSystem();
//        ReusableMethods.waitForClickablility(getSearchRecallCampaignInputfield(), 10, _driver);
////        ReusableMethods.checkTime(558);
//        // Since the clear method gave an error, this way was followed.
//        getSearchRecallCampaignInputfield().sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
//        getSearchRecallCampaignInputfield().sendKeys(recall_ID);
//
//
////        try {
////            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("(//td[contains(text(), '" + recall_ID + "')])[1]")), 20, _driver);
////            ReusableMethods.checkTime(559);
////            System.out.println("Recall " + recall_ID + " found.");
////        } catch (Exception e) {
////            ReusableMethods.checkTime(560);
////            System.out.println("This Recall " + recall_ID + " does not exist.");
////        }
//
//        try {
//            WebDriverWait wait = new WebDriverWait(webDriver, 7);
//            wait.ignoring(NoSuchElementException.class).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//td[contains(text(), '" + recall_ID + "')])[1]")));
////            ReusableMethods.waitIgnoring((By.xpath("(//td[contains(text(), '" + recall_ID + "')])[1]")), 7, webDriver);
////            ReusableMethods.checkTime(559);
//            System.out.println("Recall " + recall_ID + " found.");
//        } catch (Exception e) {
////            ReusableMethods.checkTime(560);
//            System.out.println("This Recall " + recall_ID + " does not exist.");
//        }
//
//    }


    public void searchReCallSimple(String recall_ID) {

        ManualRecallSystemPage manualRecallSystemPage = new ManualRecallSystemPage(webDriver);
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(webDriver);

        System.out.println("Searching ReCall " + recall_ID + "...");
        ReusableMethods.openTestingMenu(adminPortletHomepage, webDriver);

        ReusableMethods.clickElement(adminPortletHomepage.getManualRecallSystem(), 3, _driver);
        ReusableMethods.waitForClickablility(getSearchRecallCampaignInputfield(), 10, _driver);

        // Since the clear method gave an error, this way was followed.
        getSearchRecallCampaignInputfield().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        getSearchRecallCampaignInputfield().sendKeys(recall_ID);

        boolean isPresent = manualRecallSystemPage.isRecallElementPresent(webDriver, recall_ID);

        if (isPresent) {
            System.out.println("Recall " + recall_ID + " found.");
        } else
            System.out.println("Recall " + recall_ID + " was not found.");
    }


    public WebElement getNumberOfEntriesParagraph() {
        return numberOfEntriesParagraph;
    }

    public List<WebElement> getWebElements() {
        return this.listReCall;
    }

    public WebElement getEditRecallButton() {
        return editRecallButton;
    }

    public void clickEditRecallButton() {
        this.getEditRecallButton().click();
    }

    public WebElement getDeleteRecallButton() {
        return deleteRecallButton;
    }

    public void clickDeleteRecallButton() {
        this.getDeleteRecallButton().click();
    }


    public WebElement getApplyDeleteRecallButton() {
        return applyDeleteRecallButton;
    }

    public void clickApplyDeleteRecallButton() {
        this.getApplyDeleteRecallButton().click();
    }

    public WebElement getConfirmDeleteButton() {
        return confirmDeletion;
    }

    public void clickConfirmDeletion() {
        this.getConfirmDeleteButton().click();
    }


    public boolean isRecallElementPresent(WebDriver driver, String recall) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            wait.until(ExpectedConditions.visibilityOf(recallWebElementWithText(driver, recall)));
            return true;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }


    public WebElement recallWebElementWithText(WebDriver driver, String recall) {

        String xpathExpression = "//span[@id='inf-recall-tbl-id' and text()=' " + recall + " ']";

        try {
            WebDriverWait wait = new WebDriverWait(driver, 5);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpathExpression)));
        } catch (TimeoutException ignored) {
        }

        return driver.findElement(By.xpath(xpathExpression));
    }


}
