package de.sulzer.pages.oruoverviewpage;



import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

public class ORUOverviewPage extends Page {

    // Criterion tab01
    // flsha medium one

    WebDriver webDriver;

    public ORUOverviewPage(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }



    @FindBy(xpath = "//div[@class='panel-heading' and contains(.,'Auto Approved Batches')]")
    private WebElement autoApprovedBatchesHeader;

    @FindBy(xpath = "//div[@class='panel-heading' and contains(.,'Manually Approved Batches')]")
    private WebElement manuallyApprovedBatchesHeader;

    // Criterion tab
    @FindAll(
            @FindBy(xpath = "(//*[@class='p-tabview-nav-content']//span)[1]")
    )
    private List<WebElement> criterionTabList;

    @FindBy(xpath = "//span[.='01-01']")
    private WebElement criterion01;


    // ORU Overview Panel Title text
    @FindBy(xpath = "//h3[@class='panel-title']")
    private WebElement ORUOverviewPanelTitle;

    @FindBy(xpath = "//h3[contains(text(), 'ORU Campaign Details: ')]/span")
    private WebElement campaignID;

    @FindBy(xpath = "//div[contains(text(),'Vehicle Pool')]")
    private WebElement vehiclePoolPanelTitle;

    @FindBy(xpath = "//h4[@class='text-success']")
    private WebElement vehiclePoolDescription;

    @FindAll(
            @FindBy(xpath = "(//*[@class='p-tabview-nav-content']//span)[3]")
    )
    private List<WebElement> flashMediumTabName;


    @FindBy(xpath = "//div[contains(text(),'AutoBatch')]")
    private WebElement autoApproveBatchName;

    @FindBy(xpath = "//div[contains(text(),'Default')]")
    private WebElement manualBatchName;

    @FindBy(xpath = "//span[.='- 100 %']")
    private WebElement hundertProzent;

    // Add batch button
    @FindBy(id = "btn-batch-tables-add-batch")
    private WebElement addBatchBtn;

    // Delete button-- ORU campaign
    @FindBy(id = "btn-camp-det-del-ic")
    private WebElement deleteORUCampaign;

    @FindBy(xpath = "//button[@class='btn btn-primary']")
    private WebElement confirmORUCampaignDeletion;

    // Edit batch button
    @FindBy(xpath = "//button[@title='Edit batch']")
    private WebElement editBatchBtn;

    @FindBy(xpath = "//span[contains(@class,'campaign-translation-button')]")
    private WebElement translationButton;

    @FindBy(xpath = "(//*[@class='modal-body']//textarea)[1]")
    private WebElement inputTranslation;

    @FindBy(xpath = "//button[@title='Move batch']")
    private WebElement moveBatchButton;

    @FindBy(xpath = "//input[@value='Action archiving']")
    private WebElement actionArchiving;

    // Add criterion button

    @FindBy(xpath = "//div[@class='modal-header']/h4")
    private WebElement addCriterionModalTitle;

    @FindBy(xpath = "(//span[.='+'])[1]")
    private WebElement addButtons;

    // Add criterion center button
    @FindBy(xpath = "//button[contains(text(),'Add Criterion to ORU Campaign')]")
    private WebElement addCriterionCenterButton;

    // Add criterion pop up
    @FindBy(id = "btn-add-crit-mod-apply")
    private WebElement applyButtonInAddCriterion;

    @FindBy(id = "btn-add-crit-mod-dismiss")
    private WebElement cancelButtonInAddCriterion;

    @FindBy(xpath = "//td[@class = 'vin-column']")
    private WebElement manuallyApprovedBatchesVINsNumber;

    public WebElement getManuallyApprovedBatchesVINsNumber() {
        return manuallyApprovedBatchesVINsNumber;
    }

    public WebElement getAutoApprovedBatchesHeader() {
        return autoApprovedBatchesHeader;
    }

    public WebElement getManuallyApprovedBatchesHeader() {
        return manuallyApprovedBatchesHeader;
    }

    public List<WebElement> getCriterionTabList() {
        return criterionTabList;
    }

    public WebElement getcriterion01() {
        return criterion01;
    }

    public WebElement getORUOverviewPanelTitle() {
        return this.ORUOverviewPanelTitle;
    }

    public String getORUOverviewPanelTitleText() {
        return this.getORUOverviewPanelTitle().getText().trim();
    }

    public WebElement getCampaignID() {
        return campaignID;
    }

    public String getOruCampaignIDPanelTittle(){
        return this.getCampaignID().getText().trim();
    }

    public WebElement getVehiclePoolPanelTitle() {
        return this.vehiclePoolPanelTitle;
    }

    public String getVehiclePoolPanelTitleText() {
        return this.getVehiclePoolPanelTitle().getText();
    }

    public WebElement getVehiclePooldescription() {
        return this.vehiclePoolDescription;
    }

    public String getVehiclePooldescriptionText() {
        return this.getVehiclePooldescription().getText();
    }

    public WebElement getAutoApproveBatchName() {
        return this.autoApproveBatchName;
    }

    public String getAutoApproveBatchNameText() {
        return this.getAutoApproveBatchName().getText();
    }

    public WebElement getManualBatchName() {
        return this.manualBatchName;
    }

    public String getManualBatchNameText() {
        return this.getManualBatchName().getText();
    }

    public WebElement getAddBatchBtn() {
        return this.addBatchBtn;
    }

    // click add batch button
    public void clickAddBatchBtn() {
        this.getAddBatchBtn().click();
    }

    public WebElement getDeleteORUCampaign() {
        return this.deleteORUCampaign;
    }

    public WebElement getHundertProzent() {
        return hundertProzent;
    }

    public List<WebElement> getFlashMediumTabName() {
        return flashMediumTabName;
    }



    public boolean getDeleteORUCampaignStatus() {
        if (this.getDeleteORUCampaign().getAttribute("class").contains("enabled")) {
            return true;
        } else {
            return false;
        }
    }

    public void clickDeleteORUCampaign() {
        this.getDeleteORUCampaign().click();
    }

    public WebElement getConfirmORUCampaignDeletion() {
        return this.confirmORUCampaignDeletion;
    }

    public void clickconfirmORUCampaignDeletion() {
        this.getConfirmORUCampaignDeletion().click();
    }
    // Edit batch button

    public WebElement getEditBatchBtn() {

        return this.editBatchBtn;

    }


    public WebElement getTranslationButton() {
        return this.translationButton;
    }

    public void clickTranslationButton() {
        this.getTranslationButton().click();
    }

    public WebElement getInputTranslationField() {
        return this.inputTranslation;
    }
    public WebElement getMoveBatchButton() {
        return moveBatchButton;
    }

    public WebElement getActionArchiving() {
        return this.actionArchiving;
    }

    public void clickActionArchiving() {
        this.getActionArchiving().click();
    }

    public boolean visibilityOfActionArchivingButton() {
        try {
            this.getActionArchiving().isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    public boolean translationButtonStatus() {
        if (this.translationButton.getAttribute("class").contains("disabled")) {
            return false;
        } else {
            return true;
        }
    }

    // Add criterion button

    public WebElement getAddCriterionModalTitle() {
        return addCriterionModalTitle;
    }

    public String getAddCriterionModalTitleText() {
        return this.getAddCriterionModalTitle().getText();
    }

    public WebElement getAddCriterionButton() {
        return addButtons;
    }


    public WebElement getAddCriterionCenterButton() {
        return addCriterionCenterButton;
    }

    public void clickAddCriterionCenterButton() {
        this.getAddCriterionCenterButton().click();
    }


    public WebElement getApplyInAddCriterion() {
        return applyButtonInAddCriterion;
    }

    public WebElement getCancelInAddCriterion() {
        return cancelButtonInAddCriterion;
    }

    public void clickCancelInAddCriterion() {
        this.getCancelInAddCriterion().click();
    }



    public boolean isCriteriumAndFlashmediumPresent(WebDriver driver, String criteriumTitle, String criteriumID, String flashFile) {
        String criterium = criteriumID + "-" + criteriumTitle;
        String flashMedium = "1: " + flashFile;


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        boolean result = webDriver.findElement(By.xpath("(//*[@class='p-tabview-nav-content']//span)[1]")).getText().contains(criterium) ||
                webDriver.findElement(By.xpath("(//*[@class='p-tabview-nav-content']//span)[3]")).getText().contains(flashMedium);

        if (result) {
            return true;
        } else {
            System.out.println("This particular role is not allowing to display Criteriums in ORU page/Unable to see criteria or flash medium; ");
            return false;
        }

    }

}
