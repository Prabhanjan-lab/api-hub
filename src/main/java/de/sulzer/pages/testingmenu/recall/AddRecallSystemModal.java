package de.sulzer.pages.testingmenu.recall;

import de.sulzer.utils.guistandardfunctions.ChecksReCallCreation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

public class AddRecallSystemModal extends RecallSystemModal {

    protected WebDriver webDriver;

    public AddRecallSystemModal(WebDriver driver) {
        super(driver);
        this.webDriver = driver;
    }

    @FindBy(id = "btn-new-recall-mod-create")
    private WebElement createRecallCampaignButton;

    @FindBy(id = "inf-recall-add-ic")
    private WebElement addRecallButton;

    @FindBy(id = "btn-new-recall-mod-close")
    private WebElement closeButton;

    @FindBy(css = ".p-tabview-left-icon")
    private WebElement addCriterionButton;

    @FindBy(xpath = "//span[@class='pi pi-chevron-right']")
    private WebElement rightIcon;

    public WebElement getCloseButton() {
        return closeButton;
    }

    public WebElement getAddRecallButton() {
        return addRecallButton;
    }

    public WebElement getAddCriterionButton() {
        return addCriterionButton;
    }

    public WebElement getRightIcon() {
        return rightIcon;
    }

    public void clickAddCriterionButton() {
        this.getAddCriterionButton().click();
    }

    public String asString(int i) {
        if (i < 10) {
            return "0" + i;
        } else {
            return i + "";
        }
    }

    public void createRecallnCriteria(String id, String title, int counter, String disabledCountry, String recallVin, String comment, WebDriver driver) {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.setRecallActionIdInputFieldText(id);
        this.setRecallActionTitleInputField(title);
        if (disabledCountry != null) {
            this.setDisabledCountriesInputField(disabledCountry);
        }

        WebDriverWait wait = new WebDriverWait(driver, 10);
        ManualRecallSystemPage manualRecallSystemPage = new ManualRecallSystemPage(driver);
        ChecksReCallCreation checksReCallCreation = new ChecksReCallCreation(driver, wait);
        WebElement firstCell = manualRecallSystemPage.getFirstRowActionID();

        for (int i = 1; i <= counter; i++) {

            checksReCallCreation.checkVisibilityOfNewInputFields(i);
            String criterionIdTitle = this.asString(i);

            this.setCriterionValues(i, (i - 1), criterionIdTitle, criterionIdTitle, recallVin);
            if (i == counter) {
                break;
            }

            try {
                getRightIcon().click();
            } catch (Exception ignored) {
            }

            this.clickAddCriterionButton();

        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.setCommentInputField(comment);
        this.clickCreateRecallCampaignButton();

        ReusableMethods.clearField(manualRecallSystemPage.getSearchRecallCampaignInputfield());
        ReusableMethods.waitForVisibility(firstCell, 20, webDriver);
    }


    public void createRecallCriteriaWithCriterionTittle(String id, String title, String criterionIdTitle, int counter, String disabledCountry, String recallVin, String comment, WebDriver driver) {

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.setRecallActionIdInputFieldText(id);
        this.setRecallActionTitleInputField(title);
        if (disabledCountry != null) {
            this.setDisabledCountriesInputField(disabledCountry);
        }

        WebDriverWait wait = new WebDriverWait(driver, 10);
        ManualRecallSystemPage manualRecallSystemPage = new ManualRecallSystemPage(driver);
        ChecksReCallCreation checksReCallCreation = new ChecksReCallCreation(driver, wait);
        WebElement firstCell = manualRecallSystemPage.getFirstRowActionID();

        for (int i = 1; i <= counter; i++) {

            checksReCallCreation.checkVisibilityOfNewInputFields(i);
            String criterionId = this.asString(i);

            this.setCriterionValues(i, (i - 1), criterionId, criterionIdTitle, recallVin);
            if (i == counter) {
                break;
            }

            try {
                getRightIcon().click();
            } catch (Exception ignored) {
            }

            this.clickAddCriterionButton();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.setCommentInputField(comment);
        this.clickCreateRecallCampaignButton();

        ReusableMethods.waitForVisibility(firstCell, 20, webDriver);

    }

    public WebElement getCreateRecallCampaignButton() {
        return createRecallCampaignButton;
    }

    public void clickCreateRecallCampaignButton() {
        this.getCreateRecallCampaignButton().click();
    }

    /**
     * @param criteriaNumber
     * @param id
     * @param title
     * @param VIN
     * @param driver         This method is used for multiple criterion as well as single
     *                       criterion creation by giving criterion number which you want to
     *                       create(example: For first criterion creation variable
     *                       "criteriaNumber" as "1"; for second "criteriaNumber" as "2" and
     *                       so on...)
     */
    public void criterionCreation(String criteriaNumber, String id, String title, String VIN, WebDriver driver) {

        int index = Integer.parseInt(criteriaNumber);

        this.setCriterionValues(index, (index - 1), id, title, VIN);

    }

    public void setCriterionValues(int tabPanelCriteriaIndex, int inputControlIndex, String id, String title, String vin) {

        this.webDriver.findElement(By.xpath("//p-tabpanel[" + tabPanelCriteriaIndex + "]//input[@id='inpt-add-crit-id']")).sendKeys(id);
        this.webDriver.findElement(By.xpath("//p-tabpanel[" + tabPanelCriteriaIndex + "]//input[@id='inpt-add-crit-title']")).sendKeys(title);
        this.webDriver.findElement(By.xpath("//p-tabpanel[" + tabPanelCriteriaIndex + "]//textarea[@id='inpt-add-crit-vehs']")).sendKeys(vin);
    }


}