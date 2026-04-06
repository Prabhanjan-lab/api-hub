package de.sulzer.pages.caradministration;

import java.util.ArrayList;
import java.util.List;

import de.sulzer.pages.BrandSelectionPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import de.sulzer.pages.genericelements.Page;
import testCases.util.ConstantsMisc;
import testFramework.utilities.ReusableMethods;

public class VehicleOverviewPageSpecificVIN extends Page {

    WebDriver webDriver;

    public VehicleOverviewPageSpecificVIN(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }


    /*
     * common attributes/elements of page
     */
    @FindBy(css = ".panel-title")
    private WebElement vinTitlePage;

    public String getPanelTitleText() {
        return this.vinTitlePage.getText();
    }

    /*
     * MBT calculation
     */
    @FindBy(xpath = "//a[@aria-controls='mc']")
    private WebElement mbtCalculationPanelHeader;

    @FindBy(id = "btn-veh-panels-mc-load")
    private WebElement mbtCalculationPanelButtonLoad;

    /*
     * MBT tabs
     */
    @FindBy(xpath = "//span[.='1. Step: MBV']")
    private WebElement mbtTabStep1MBT;

    @FindBy(xpath = "//span[.='2. Step: Model year']")
    private WebElement mbtTabStep2ModelYear;

    @FindBy(xpath = "//span[.='3. Step: Equipment']")
    private WebElement mbtTabStep3Equipment;

    /*
     * MBT step 1 tab
     */
    @FindBy(xpath = "(//div[@class='mb-label-used'])[1]")
    private WebElement mbtTabStep1LabelUsed;

    @FindBy(xpath = "//*[.='Result after filtering MBV (first two letter):']")
    private WebElement mbtTabStep1PanelHeading;

    @FindBy(xpath = "//div//textarea")
    private WebElement mbtTabStep1PanelBody;

    /*
     * MBT step 2 tab
     */
    @FindBy(xpath = "(//div[@class='mb-label-used'])[2]")
    private WebElement mbtTabStep2LabelUsed;

    @FindBy(xpath = "//*[.='Result after filtering by MBV (first two letters) and model year:']")
    private WebElement mbtTabStep2PanelHeading;

    @FindBy(xpath = "(//div/textarea)[2]")
    private WebElement mbtTabStep2PanelBody;

    /*
     * MBT step 3 tab
     */
    @FindBy(xpath = "(//div[@class='mb-label-used'])[3]")
    private WebElement mbtTabStep3LabelUsed;

    @FindBy(xpath = "//*[.='Result after filtering by MBV (first two letters), model year and equipment:']")
    private WebElement mbtTabStep3PanelHeading;

    @FindBy(xpath = "(//div/textarea)[3]")
    private WebElement mbtTabStep3PanelBody;

    /*
     * Vehicle Configuration
     */
    @FindBy(xpath = "//a[contains(text(),'Vehicle Configuration')]")
    private WebElement vehicleConfigurationPanelHeader;

    @FindBy(xpath = "(//*[.=' Load'])[2]")
    private WebElement vehicleConfigurationButtonLoad;

    @FindBy(xpath = "//span[contains(text(),'Infotainment')]")
    private WebElement infotainment;

    @FindBy(css = ".col-lg-12.form-inline.ng-star-inserted")
    private WebElement infotainmentHistoryPanelSuccess;

    @FindBy(xpath = "//span[contains(text(),'cGW NAD')]")
    private WebElement cGWNAD;

    @FindBy(xpath = "//span[contains(text(),'OCU2/OCU3')]")
    private WebElement OCU2OCU3;

    @FindBy(css = "pre.form-control")
    private WebElement vehicleCofigurationArea;

    public static final String VC_VALID = "The VC history is valid.";
    public static final String VC_INVALID = "The VC history is invalid.";

    @FindBy(xpath = "//button[@id='historyPanelSetButton']")
    private WebElement buttonSetReferenceVc;

    @FindBy(xpath = "//pre[contains(@class, 'form-control')]")
    private WebElement vehicleConfigurationTextArea;

    /*
     * Options
     */
    @FindBy(xpath = "//a[@aria-controls='set']")
    private WebElement optionsPanelHeader;

    @FindBy(id = "btn-veh-panels-set-load")
    private WebElement optionsPanelButtonLoad;

    @FindBy(id = "cb-veh-settings-test-veh")
    private WebElement optionsChBTestVehicle;

    @FindBy(id = "cb-veh-settings-prot-veh")
    private WebElement optionsChBPrototypeVehicle;

    @FindBy(id = "cb-veh-settings-mib-swup")
    private WebElement optionsChBSoftwareVersionComparisonDisabled;

    @FindBy(id = "cb-veh-settings-skip-mass-notif")
    private WebElement optionsChBMassNotification;

    @FindBy(id = "btn-veh-settings-save")
    private WebElement optionsSaveButton;

    @FindBy(xpath = "//span[@id='inf-veh-settings-success']//strong[contains(text(), 'Successfully saved')]")
    private WebElement optionsSaveMessage;

    /*
     * Active campaign
     */
    @FindBy(xpath = "//a[@aria-controls='ac']")
    private WebElement activeCampaignPanelHeader;

    @FindBy(id = "btn-veh-panels-ac-load")
    private WebElement activeCampaignPanelButtonLoad;

    @FindAll({
            @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[1]/tr") // all rows of table
    })
    private List<WebElement> activeCampaignRows;

    @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[1]//span[contains(text(),'No records found.')]")
    private WebElement activeCampaignNoRecordsFound;

    @FindBy(xpath = "(//*[.=' Domain '])[1]")
    private WebElement activeCampaignDomainHeader;

    @FindBy(id = "href-veh-panels-ac-details")
    private WebElement activeCampaignDetailsText;

    /*
     * Scheduled campaign
     */
    @FindBy(xpath = "//a[@aria-controls='sc']")
    private WebElement scheduledCampaignPanelHeader;

    @FindBy(id = "btn-veh-panels-sc-load")
    private WebElement scheduledCampaignPanelButtonLoad;

    @FindAll({
            @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[2]/tr") // all rows of table
    })
    private List<WebElement> scheduledCampaignRows;

    @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[2]//span[contains(text(),'No records found.')]")
    private WebElement scheduledCampaignNoRecordsFound;

    @FindBy(xpath = "(//*[.=' Domain '])[2]")
    private WebElement scheduledCampaignDomainHeader;

    /*
     * Pending Campaign
     */
    @FindBy(xpath = "//a[@aria-controls='pc']")
    private WebElement pendingCampaignPanelHeader;

    @FindBy(id = "btn-veh-panels-pc-load")
    private WebElement pendingCampaignPanelButtonLoad;

    @FindAll({
            @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[3]/tr") // all rows of table
    })
    private List<WebElement> pendingCampaignRows;

    @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[3]//span[contains(text(),'No records found.')]")
    private WebElement pendingCampaignNoRecordsFound;

    @FindBy(xpath = "(//*[.=' Domain '])[3]")
    private WebElement pendingCampaignDomainHeader;

    @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[3]")
    private WebElement pendingCampaignTable;

    /*
     * Campaign historical
     */
    @FindBy(xpath = "//a[@aria-controls='ch']")
    private WebElement campaignHistoryPanelHeader;

    @FindBy(id = "btn-veh-panels-ch-load")
    private WebElement campaignHistoryPanelButtonLoad;

    @FindAll({
            @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[4]/tr") // all rows of table
    })
    private List<WebElement> campaignHistoryRows;

    @FindBy(xpath = "(//tbody[@class='p-element p-datatable-tbody'])[4]//span[contains(text(),'No records found.')]")
    private WebElement campaignHistoryNoRecordsFound;

    @FindBy(xpath = "(//*[.=' Domain '])[4]")
    private WebElement campaignHistoryDomainHeader;

    @FindAll({
            @FindBy(xpath = "//div[contains(.,'Campaign Details')]//table//tbody//tr") // Campaign history details table
    })
    private List<WebElement> campaignHistoryDetailsTable;

    public List<WebElement> getCampaignHistoryDetailsTable() {
        return campaignHistoryDetailsTable;
    }

    /*
     * Vehicle Attributes
     */
    @FindBy(xpath = "(//h4[@class=\"text-danger\"]/a)[8]")
    private WebElement vehicleAttributes;

    @FindBy(id = "btn-veh-panels-load-attributes")
    private WebElement vehicleAttributeLoadButton;

    @FindBy(css = "div.ui-g:nth-child(4) > button:nth-child(1)")
    private WebElement vehicleAttributesForceCarPortUpdate;

    @FindBy(xpath = "//div/div[@class='alert alert-danger'][contains(text(), 'Warning')]")
    private WebElement warningAlertNoCarPortData;

    @FindBy(xpath = "//span[@class='carport-update-msg carport-error']")
    private WebElement messageCarPortDataUpdateError;

    @FindBy(css = ".ui-g.flex.justify-content-end.mt-2 span")
    private WebElement messageCarPortDataUpdateSuccess;

    @FindBy(xpath = "(//span[@id='inf-veh-attrs-cache-data'])[1]")
    private WebElement vinAttribute;

    // Vehicle data source row
    @FindBy(xpath = "(//span[@id='inf-veh-attrs-cache-data'])[26]")
    private WebElement vehDataSourceValue;

    // Attending country row
    @FindBy(xpath = "(//span[@id='inf-veh-attrs-cache-data'])[10]")
    private WebElement attendingCountryValue;

    // Attending partner number row
    @FindBy(xpath = "(//span[@id='inf-veh-attrs-cache-data'])[11]")
    private WebElement attendingPNValue;

    // Delivering country row
    @FindBy(xpath = "(//span[@id='inf-veh-attrs-cache-data'])[14]")
    private WebElement deliveringCountryValue;

    // Delivering partner number row
    @FindBy(xpath = "(//span[@id='inf-veh-attrs-cache-data'])[15]")
    private WebElement deliveringPNValue;

    // Ordering country row
    @FindBy(xpath = "(//span[@id='inf-veh-attrs-cache-data'])[18]")
    private WebElement orderingCountryValue;

    // Ordering partner number row
    @FindBy(xpath = "(//span[@id='inf-veh-attrs-cache-data'])[19]")
    private WebElement orderingPNValue;

    /**
     * Button from Modal dialogue is usable from following drop-down lists: -
     * active campaign - campaign history
     */
    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/campaign-status-details/div/div[2]/button")
    private WebElement closeButtonDetailsDialog;

    /*
     * error dialogue
     */
    @FindBy(xpath = "//error-modal//div//button[@class='btn btn-primary']")
    private WebElement okButtonErrorDialogueRewindOperation;

    /*
     * error dialogue when click on force abort
     */
    @FindBy(xpath = "//ngb-modal-window//error-modal//h4")
    private WebElement headingInerrorDialogueOfForceAbortOperation;

    @FindBy(xpath = "//ngb-modal-window//error-modal//h4//following::p")
    private WebElement errorTextInDialogueOfForceAbortOperation;

    @FindBy(xpath = "//ngb-modal-window//error-modal//h4//following::button[contains(.,'Ok')]")
    private WebElement okButtonInErrorDialogueOfForceAbortOperation;

    @FindBy(css = "#head-veh-attrs-title")
    private WebElement vehicleAttributesText;

    @FindBy(id = "href-veh-panels-ch-rewind")
    private WebElement rewindButton;

    @FindBy(xpath = "//error-modal/div/div/div[3]/button")
    private WebElement notAuthorisedOkButton;

    public WebElement getNotAuthorisedOkButton() {
        return notAuthorisedOkButton;
    }

    @FindBy(xpath = "(//button[.=' Ok '])[2]")
    private WebElement securityOkButton;

    @FindBy(xpath = "(//p-paginator/div/button[1]/span)[4]")
    private WebElement campHistoryFirstPaginator;

    @FindBy(xpath = "(//p-paginator/div/button[4]/span)[4]")
    private WebElement campHistoryLastPaginator;


    public WebElement getVehicleAttributes() {
        return vehicleAttributes;
    }

    public WebElement getVehicleAttributesText() {
        return vehicleAttributesText;
    }

    public WebElement getCampHistoryFirstPaginator() {
        return campHistoryFirstPaginator;
    }

    public WebElement getCampHistoryLastPaginator() {
        return campHistoryLastPaginator;
    }

    public WebElement getSecurityOkButton() {
        return securityOkButton;
    }

    public WebElement getWarningAlertNoCarPortData() {
        return this.warningAlertNoCarPortData;
    }

    public WebElement getRewindButton() {
        return rewindButton;
    }

    /**
     * @return the messageCarPortDataUpdateError
     */
    public WebElement getMessageCarPortDataUpdateError() {
        return messageCarPortDataUpdateError;
    }

    public WebElement getMessageCarPortDataUpdateSuccess() {
        return messageCarPortDataUpdateSuccess;
    }

    public WebElement getVehicleDataSourceValue() {
        return vehDataSourceValue;
    }

    public String getVehicleDataSourceValueText() {
        return this.getVehicleDataSourceValue().getText();
    }

    public WebElement getAttendingCountryValue() {
        return attendingCountryValue;
    }

    public WebElement getAttendingPNValue() {
        return attendingPNValue;
    }

    public String getAttendingPNValueText() {
        return this.getAttendingPNValue().getText();
    }

    public WebElement getDeliveringCountryValue() {
        return deliveringCountryValue;
    }

    public WebElement getDeliveringPNValue() {
        return deliveringPNValue;
    }

    public String getDeliveringPNValueText() {
        return this.getDeliveringPNValue().getText();
    }

    public WebElement getOrderingCountryValue() {
        return orderingCountryValue;
    }

    public WebElement getOrderingPNValue() {
        return orderingPNValue;
    }

    public String getOrderingPNValueText() {
        return this.getOrderingPNValue().getText();
    }

    public WebElement getVinAttribute() {
        return vinAttribute;
    }


    /**
     * @return the vinTitlePage
     */
    public WebElement getVinTitlePage() {
        return vinTitlePage;
    }

    /**
     * @param vin
     * @return true, if strings are equal, otherwise false
     */
    public boolean isVinPageTitle(String vin) {
        return this.getVinTitlePage().getText().trim().contains(vin);
    }

    /**
     * @return the mbtCalculationPanelHeader
     */
    public WebElement getMbtCalculationPanelHeader() {
        return this.mbtCalculationPanelHeader;
    }

    public WebElement getMbtCalculationPanelButtonLoad() {
        return this.mbtCalculationPanelButtonLoad;
    }

    public void clickMbtCalculationPanelButtonLoad() {
        this.getMbtCalculationPanelButtonLoad().click();
    }

    /**
     * @return the mbtTabStep1MBT
     */
    public WebElement getMbtTabStep1MBT() {
        return mbtTabStep1MBT;
    }

    public void clickMbtTabStep1MBT() {
        this.getMbtTabStep1MBT().click();
    }

    /**
     * @return the mbtTabStep2ModelYear
     */
    public WebElement getMbtTabStep2ModelYear() {
        return mbtTabStep2ModelYear;
    }

    public void clickMbtTabStep2ModelYear() {
        this.getMbtTabStep2ModelYear().click();
    }

    /**
     * @return the mbtTabStep3Equipment
     */
    public WebElement getMbtTabStep3Equipment() {
        return mbtTabStep3Equipment;
    }

    public void clickMbtTabStep3Equipment() {
        this.getMbtTabStep3Equipment().click();
    }

    /**
     * @return the mbtTabStep1LabelUsed
     */
    public WebElement getMbtTabStep1LabelUsed() {
        return mbtTabStep1LabelUsed;
    }

    /**
     * @return the mbtTabStep1PanelHeading
     */
    public WebElement getMbtTabStep1PanelHeading() {
        return mbtTabStep1PanelHeading;
    }

    /**
     * @return the mbtTabStep1PanelBody
     */
    public WebElement getMbtTabStep1PanelBody() {
        return mbtTabStep1PanelBody;
    }

    /**
     * @return the mbtTabStep2LabelUsed
     */
    public WebElement getMbtTabStep2LabelUsed() {
        return mbtTabStep2LabelUsed;
    }

    /**
     * @return the mbtTabStep2PanelHeading
     */
    public WebElement getMbtTabStep2PanelHeading() {
        return mbtTabStep2PanelHeading;
    }

    /**
     * @return the mbtTabStep2PanelBody
     */
    public WebElement getMbtTabStep2PanelBody() {
        return mbtTabStep2PanelBody;
    }

    /**
     * @return the mbtTabStep3LabelUsed
     */
    public WebElement getMbtTabStep3LabelUsed() {
        return mbtTabStep3LabelUsed;
    }

    /**
     * @return the mbtTabStep3PanelHeading
     */
    public WebElement getMbtTabStep3PanelHeading() {
        return mbtTabStep3PanelHeading;
    }

    /**
     * @return the mbtTabStep3PanelBody
     */
    public WebElement getMbtTabStep3PanelBody() {
        return mbtTabStep3PanelBody;
    }

    /**
     * @return the optionsPanelHeader
     */
    public WebElement getOptionsPanelHeader() {
        return this.optionsPanelHeader;
    }

    public void clickOptionsPanelHeader() {
        this.getOptionsPanelHeader().click();
    }

    public WebElement getOptionsPanelButtonLoad() {
        return this.optionsPanelButtonLoad;
    }

    public void clickOptionsPanelButtonLoad() {
        this.getOptionsPanelButtonLoad().click();
    }

    public String getOptionsPanelHeaderText() {
        return this.optionsPanelHeader.getText();
    }

    /**
     * @return the optionsChBTestVehicle
     */
    public WebElement getOptionsChBTestVehicle() {
        return optionsChBTestVehicle;
    }

    public void clickOptionsChBTestVehicle() {
        this.getOptionsChBTestVehicle().click();
    }

    /**
     * )
     *
     * @return the optionsChBPrototypeVehicle
     */
    public WebElement getOptionsChBPrototypeVehicle() {
        return optionsChBPrototypeVehicle;
    }

    public void clickOptionsChBPrototypeVehicle() {
        this.getOptionsChBPrototypeVehicle().click();
    }

    /**
     * @return the optionsChBSoftwareVersionComparisonDisabled
     */
    public WebElement getOptionsChBSoftwareVersionComparisonDisabled() {
        return optionsChBSoftwareVersionComparisonDisabled;
    }

    public void clickOptionsChBSoftwareVersionComparisonDisabled() {
        this.getOptionsChBSoftwareVersionComparisonDisabled().click();
    }

    /**
     * @return the optionsChBMassNotification
     */
    public WebElement getOptionsChBMassNotification() {
        return optionsChBMassNotification;
    }

    public void clickOptionsChBMassNotification() {
        this.getOptionsChBMassNotification().click();
    }

    /**
     * @return the optionsSaveButton
     */
    public WebElement getOptionsSaveButton() {
        return optionsSaveButton;
    }

    public void clickOptionsSaveButton() {
        this.getOptionsSaveButton().click();
    }

    /**
     * @return the optionsSaveMessage
     */
    public WebElement getOptionsSaveMessage() {
        return optionsSaveMessage;
    }

    /**
     * @return the activeCampaignPanelHeader
     */
    public WebElement getActiveCampaignPanelHeader() {
        return this.activeCampaignPanelHeader;
    }

    public WebElement getActiveCampaignPanelButtonLoad() {
        return this.activeCampaignPanelButtonLoad;
    }

    public List<WebElement> getActiveCampaignRows() {
        return this.activeCampaignRows;
    }

    public WebElement getActiveCampaignNoRecordsFound() {
        return this.activeCampaignNoRecordsFound;
    }



    public boolean isActiveCampaignAvailable(String campaignId) {

        if (null != this.searchActiveCampaignEntryById(campaignId)) {
            return true;
        } else {
            return false;
        }

    }

    public void clickActiveCampaignPanelButtonLoad() {
        this.getActiveCampaignPanelButtonLoad().click();
    }

    public WebElement getActiveCampaignById(String campaignId) {
        return this.searchActiveCampaignEntryById(campaignId);
    }

    public WebElement getActiveCampaignDetailsLink(String campaignId) {

        WebElement element = this.getActiveCampaignById(campaignId);

        if (element != null) {
            return getActiveCampaignDetails();
        } else {
            return null;
        }

    }

    public WebElement getActiveCampaignDetails() {
        return this.activeCampaignDetailsText;
    }

    public String getActiveCampaignDetailsText() {
        return this.activeCampaignDetailsText.getText().trim();
    }

    public void clickActiveCampaignDetailsLink(String campaignId) {
        this.getActiveCampaignDetailsLink(campaignId).click();
    }

    public WebElement getActiveCampaignStatusByCampaignId(String campaignId) {

        WebElement element = this.getActiveCampaignById(campaignId);

        if (element != null) {
            return element.findElement(By.xpath(".//td[3]"));
        } else {
            return null;
        }

    }

    public WebElement getActiveCampaignNotifyLink(String campaignId) {

        WebElement element = this.getActiveCampaignById(campaignId);

        if (element != null) {
            return element.findElement(By.xpath(".//a[contains(text(),'Notify')]"));
        } else {
            return null;
        }

    }

    public void clickActiveCampaignNotifyLink(String campaignId) {
        this.getActiveCampaignNotifyLink(campaignId).click();
    }

    public void clickActiveCampaignAbortLink(String campaignId) {
        this.getActiveCampaignAbortLink(campaignId).click();
    }

    public WebElement getActiveCampaignAbortLink(String campaignId) {

        WebElement element = this.getActiveCampaignById(campaignId);

        if (element != null) {
            return element.findElement(By.xpath(".//a[contains(text(),'Abort')]"));
        } else {
            return null;
        }

    }

    public WebElement getActiveCampaignForceAbortLink(String campaignId) {

        WebElement element = this.searchActiveCampaignEntryById(campaignId);

        if (element != null) {
//            return element.findElement(By.xpath(".//a[contains(text(),'Force Abort')]"));
            return webDriver.findElement(By.xpath("(//*[.=' " + campaignId + " '])/following-sibling::td[3]/ul/li[3]/a"));
        }

        return null;

    }

    public WebElement getActiveCampaignDomainHeader() {
        return this.activeCampaignDomainHeader;
    }

    /**
     * 'Force Abort' of active campaign.
     *
     * @param campaignId
     */
    public void clickActiveCampaignForceAbortByCampaignId(String campaignId) {

        WebElement we = this.getActiveCampaignForceAbortLink(campaignId);

        if (null != we) {
            we.click();
        }

        // New added
        try {
            if (getHeadingInErrorDialogueOfForceAbortOperation().isDisplayed()) {
                System.out.println("following error message was shown after clicking force abort button ***"
                        + getErrorTextInDialogueOfForceAbortOperation().getText() + "***");
                getOkButtonInErrorDialogueOfForceAbortOperation().click();
            }
        } catch (NoSuchElementException ignored) {
        }

    }

    public boolean isActiveCampaignAvailableEmptyMessage() {

        if (this.activeCampaignRows.size() < 1) {
            return false;
        }

        WebElement element = this.activeCampaignRows.get(0);

        if (ConstantsMisc.NO_RECORDS_FOUND.equals(element.getText().trim())) {
            return true;
        }

        return false;
    }


    /**
     * @return the buttonSetReferenceVc
     */
    public WebElement getButtonSetReferenceVc() {
        return this.buttonSetReferenceVc;
    }

    public void clickButtonSetReferenceVc() {
        this.getButtonSetReferenceVc().click();
    }

    /**
     * @return the vehicleConfigurationHeader
     */
    public WebElement getVehicleConfigurationHeader() {
        return this.vehicleConfigurationPanelHeader;
    }

    public String getVehicleConfigurationPanelHeaderText() {
        return this.vehicleConfigurationPanelHeader.getText();
    }

    public WebElement getVehicleConfigurationButtonLoad() {
        return this.vehicleConfigurationButtonLoad;
    }

    public void clickVehicleConfigurationButtonLoad() {
        this.getVehicleConfigurationButtonLoad().click();
    }

    public WebElement getInfotainmentTab() {
        return this.infotainment;
    }

    public void clickInfotainmentTab() {
        this.getInfotainmentTab().click();
    }

    public WebElement getInfotainmentHistoryPanelSuccess() {
        return this.infotainmentHistoryPanelSuccess;
    }

    public WebElement getvehicleConfigurationTextArea() {
        return this.vehicleConfigurationTextArea;
    }

    public WebElement getcGWNADTab() {
        return this.cGWNAD;
    }

    public void clickcGWNADTab() {
        this.getcGWNADTab().click();
    }

    public WebElement getOCU2OCU3Tab() {
        return this.OCU2OCU3;
    }

    public void clickOCU2OCU3Tab() {
        this.getOCU2OCU3Tab().click();
    }

    public WebElement getVehicleConfigurationArea() {
        return this.vehicleCofigurationArea;
    }

    public String getVehicleConfigurationAreaText() {
        return this.vehicleCofigurationArea.getText();
    }

    /**
     * @return the spanVehicleConfigurationStatement
     */

    public String getMbtCalculationPanelHeaderText() {
        return this.mbtCalculationPanelHeader.getText();
    }

    /**
     * @return the scheduledCampaignHeader
     */

    public WebElement getScheduledCampaignPanelHeader() {
        return this.scheduledCampaignPanelHeader;
    }

    public WebElement getScheduledCampaignPanelButtonLoad() {
        return this.scheduledCampaignPanelButtonLoad;
    }

    public boolean isScheduledCampaignAvailable(String campaignId) {

        if (null != this.searchScheduledCampaignEntryById(campaignId)) {
            return true;
        } else {
            return false;
        }

    }

    public List<WebElement> getScheduledCampaignRows() {
        return this.scheduledCampaignRows;
    }

    public void clickScheduledCampaignPanelButtonLoad() {
        this.getScheduledCampaignPanelButtonLoad().click();
        this.getScheduledCampaignDomainHeader();
    }

    public WebElement getScheduledCampaignById(String campaignId) {
        return this.searchScheduledCampaignEntryById(campaignId);
    }

    public WebElement getScheduledCampaignDetailsLink(String campaignId) {

////        WebElement element = this.getScheduledCampaignById(campaignId);
//        WebElement element = webDriver.findElement(By.xpath("(//tbody[@class='p-element p-datatable-tbody'])[2]//td[.='" + campaignId + " ']"));
//
//        if (element != null) {
////            return element.findElement(By.xpath(".//a[@class='break-word' and contains(text(),'Details')]"));
//            return element.findElement(By.xpath(".//a[contains(text(),'Details')]"));
//        } else {
//            return null;
//        }

        WebElement element;
        element = webDriver.findElement(By.xpath("(//tbody[@class='p-element p-datatable-tbody'])//td[.=' " + campaignId + " ']/following-sibling::td/div[2]/a"));
        return element;
    }

    public void clickScheduledCampaignDetailsLink(String campaignId) {
//        this.getScheduledCampaignDetailsLink(campaignId).click();
        ReusableMethods.clickJS(getScheduledCampaignDetailsLink(campaignId), webDriver);
    }

    public WebElement getScheduledCampaignCancelLink(String campaignId) throws InterruptedException {

        /*
         * Thread sleep inserted for Approval testing (Demo doesn't need it).
         */
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
        }

        WebElement element = this.getScheduledCampaignById(campaignId);

        if (element != null) {
            return element.findElement(By.xpath(".//a[contains(text(),'Cancel')]"));
        } else {
            return null;
        }

    }

    public void clickScheduledCampaignCancelLink(String campaignId) throws InterruptedException {
        this.getScheduledCampaignCancelLink(campaignId).click();
    }

    public WebElement getScheduledCampaignNoRecordsFound() {
        return this.scheduledCampaignNoRecordsFound;
    }

    public WebElement getScheduledCampaignDomainHeader() {
        return this.scheduledCampaignDomainHeader;
    }

    /**
     * Clear campaigns possibly stuck/available in 'Active Campaign' and
     * 'Scheduled Campaign'.
     *
     * @param webDriver
     */
    // TODO: Some improvement work can be done on this method through the OUQA-2121 test.
    public void clearActiveAndScheduledCampaigns(WebDriver webDriver) {
        WebDriverWait wait = new WebDriverWait(webDriver, 20);

        // open scheduled campaigns (if closed)
        if (this.getScheduledCampaignPanelButtonLoad().getAttribute("aria-expanded").equals("false")) {
            this.clickScheduledCampaignPanelButtonLoad();
            // waiting block for display/update of data row
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // iterate over existing scheduled campaigns
        while (this.scheduledCampaignRows.size() > 0) {

            WebElement element = this.scheduledCampaignRows.get(0);


            List<WebElement> cancelElements = element.findElements(By.xpath(".//a[contains(text(),'Cancel')]"));

            if (cancelElements.size() == 0) {

                break;

            } else {
                cancelElements.get(0).click();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    ;
                }
                break;
            }
        }

        // open active campaign (if closed)
        if (this.getActiveCampaignPanelButtonLoad().getAttribute("aria-expanded").equals("false")) {

            this.clickActiveCampaignPanelButtonLoad();

            // waiting block for display/update of data row
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // clear active campaign
        if (this.activeCampaignRows.size() > 0) {

            WebElement acElement = this.activeCampaignRows.get(0);

            WebElement forceAbortElement = null;

            try {
                forceAbortElement = acElement.findElement(By.xpath(".//a[contains(text(),'Force Abort')]"));
            } catch (Exception ignored) {
                System.out.println("No elements have been found.");
            }

            if (null != forceAbortElement) {

                wait.until(ExpectedConditions.elementToBeClickable(forceAbortElement));

                forceAbortElement.click();
                System.out.println("Force Abort worked with out any error messages");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    ;
                }

            }

            // New added
            try {

                if (getHeadingInErrorDialogueOfForceAbortOperation().isDisplayed()) {
                    System.out.println("following error message was shown after clicking force abort button ***"
                            + getErrorTextInDialogueOfForceAbortOperation().getText() + "***");
                    getOkButtonInErrorDialogueOfForceAbortOperation().click();
                }
            } catch (NoSuchElementException ignored) {
            }

        }

    }

    public void clearActiveAndScheduledCampaigns(WebDriver webDriver, String campaign_ID) {
        WebDriverWait wait = new WebDriverWait(webDriver, 20);

        // open scheduled campaigns (if closed)
        if (this.getScheduledCampaignPanelButtonLoad().getAttribute("aria-expanded").equals("false")) {
            this.clickScheduledCampaignPanelButtonLoad();
            // waiting block for display/update of data row
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // iterate over existing scheduled campaigns
        while (this.scheduledCampaignRows.size() > 0) {

//            WebElement element = this.scheduledCampaignRows.get(0);
            WebElement element = this.getScheduledCampaignById(campaign_ID);

            List<WebElement> cancelElements = null;
            try {
                cancelElements = element.findElements(By.xpath(".//a[contains(text(),'Cancel')]"));
            } catch (Exception ignored) {
            }

            if (cancelElements == null) {

                break;

            } else {
                cancelElements.get(0).click();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    ;
                }
                break;
            }
        }

        // open active campaign (if closed)
        if (this.getActiveCampaignPanelButtonLoad().getAttribute("aria-expanded").equals("false")) {

            this.clickActiveCampaignPanelButtonLoad();

            // waiting block for display/update of data row
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // clear active campaign
        if (this.activeCampaignRows.size() > 0) {

//            WebElement acElement = this.activeCampaignRows.get(0);
            WebElement acElement = this.getActiveCampaignById(campaign_ID);

            WebElement forceAbortElement = null;

            try {
                forceAbortElement = acElement.findElement(By.xpath(".//a[contains(text(),'Force Abort')]"));
            } catch (Exception ignored) {
                System.out.println("No elements have been found.");
            }

            if (null != forceAbortElement) {

                wait.until(ExpectedConditions.elementToBeClickable(forceAbortElement));

                forceAbortElement.click();
                System.out.println("Force Abort worked with out any error messages");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                    ;
                }

            }

            // New added
            try {

                if (getHeadingInErrorDialogueOfForceAbortOperation().isDisplayed()) {
                    System.out.println("following error message was shown after clicking force abort button ***"
                            + getErrorTextInDialogueOfForceAbortOperation().getText() + "***");
                    getOkButtonInErrorDialogueOfForceAbortOperation().click();
                }
            } catch (NoSuchElementException ignored) {
            }

        }

    }

    /**
     * @return the pendingCampaignPanelHeader
     */
    public WebElement getPendingCampaignHeader() {
        return this.pendingCampaignPanelHeader;
    }

    /**
     * @return the pendingCampaignPanelButtonLoad
     */
    public WebElement getPendingCampaignPanelButtonLoad() {
        return this.pendingCampaignPanelButtonLoad;
    }

    public void clickPendingCampaignPanelButtonLoad() {
        this.getPendingCampaignPanelButtonLoad().click();
    }

    /**
     * @return the pendingCampaignRows
     */
    public List<WebElement> getPendingCampaignRows() {
        return this.pendingCampaignRows;
    }

    /**
     * @return the pendingCampaignNoRecordsFound
     */
    public WebElement getPendingCampaignNoRecordsFound() {
        return this.pendingCampaignNoRecordsFound;
    }

    public WebElement getPendingCampaignTable() {
        return this.pendingCampaignTable;
    }

    public WebElement getpendingCampaignPanelBody() {
        return this.pendingCampaignDomainHeader;
    }

    public void clickPendingCampaignDetailsLink(String campaignId) {
        this.getPendingCampaignDetailsLink(campaignId).click();
    }

    public WebElement getPendingCampaignById(String campaignId) {
        return this.searchPendingCampaignEntryById(campaignId);
    }

    public WebElement getPendingCampaignStatusCampaignId(String campaignId) {

        WebElement element = this.searchPendingCampaignEntryById(campaignId);

        if (element != null) {
            return element.findElement(By.xpath(".//td[3]"));
        } else {
            return null;
        }

    }

    public WebElement getPendingCampaignDetailsLink(String campaignId) {

//        WebElement element = this.getPendingCampaignById(campaignId);
//
//        if (element != null) {
//            return element.findElement(By.xpath(".//a[@class='break-word' and contains(text(),'Details')]"));
//        } else {
//            return null;
//        }

        WebElement element;
        element = webDriver.findElement(By.xpath("(//tbody[@class='p-element p-datatable-tbody'])//td[.=' " + campaignId + " ']/following-sibling::td/div[2]/a"));
        return element;

    }

    public void clickPendingCampaignCancelLink(String campaignId) throws InterruptedException {
        this.getPendingCampaignCancelLink(campaignId).click();
    }

    public WebElement getPendingCampaignCancelLink(String campaignId) {

        WebElement element = this.getPendingCampaignById(campaignId);

        if (element != null) {
            return element.findElement(By.xpath(".//a[contains(text(),'Cancel')]"));
        } else {
            return null;
        }
    }

    public boolean isPendingCampaignAvailable(String campaignId) {

        if (null != this.searchPendingCampaignEntryById(campaignId)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * @return the campaignHistoryPanelHeader
     */
    public WebElement getCampaignHistoryPanelHeader() {
        return campaignHistoryPanelHeader;
    }

    /**
     * @return the campaignHistoryPanelButtonLoad
     */
    public WebElement getCampaignHistoryPanelButtonLoad() {
        return this.campaignHistoryPanelButtonLoad;
    }

    public void clickCampaignHistoryPanelButtonLoad() {
        this.getCampaignHistoryPanelButtonLoad().click();
    }

    /**
     * @return the campaignHistoryRows
     */
    public List<WebElement> getCampaignHistoryRows() {
        return campaignHistoryRows;
    }

    public WebElement getCampaignHistoryNoRecordsFound() {
        return this.campaignHistoryNoRecordsFound;
    }

    public WebElement getCampaignHistoryDomainHeader() {
        return this.campaignHistoryDomainHeader;
    }

    public boolean isCampaignHistoryAvailable(String campaignId) {
        return this.searchCampaignHistoryById(campaignId);
    }

    /**
     * @param campaignId
     */
    public WebElement getHistoryCampaignDetailsByCampaignId(String campaignId) {

        WebElement element = this.searchCampaignHistoryEntryById(campaignId);

        if (element != null) {
            return element.findElement(By.xpath(".//a[contains(text(),'Details')]"));
        } else {
            return null;
        }

    }

    public WebElement getCampaignHistoryById(String campaignId) {
        return this.searchCampaignHistoryEntryById(campaignId);
    }

    /**
     * @param campaignId
     */
    public void clickHistoryCampaignDetailsByCampaignId(String campaignId) {
        this.getHistoryCampaignDetailsByCampaignId(campaignId).click();
    }

    public WebElement getVehicleAttributeLoadButton() {
        return vehicleAttributeLoadButton;
    }

    public void clickVehicleAttributeLoadButton() {
        this.getVehicleAttributeLoadButton().click();
    }

    /**
     * @return the vehicleAttributesForceCarPortUpdate
     */
    public WebElement getVehicleAttributesForceCarPortUpdate() {
        return this.vehicleAttributesForceCarPortUpdate;
    }

    /**
     *
     */
    public void clickVehicleAttributesForceCarPortUpdate() {
        this.getVehicleAttributesForceCarPortUpdate().click();
    }

    private boolean searchCampaignHistoryById(String campaignId) {

        BrandSelectionPage brandSelectionPage = new BrandSelectionPage(webDriver);
        boolean result = false;

        try {
            ReusableMethods.clickElement(getCampHistoryLastPaginator(), 3, webDriver);
        } catch (Exception ignored) {
        }

//        try {
//            Thread.sleep(2000);
//            ReusableMethods.clickElement(getCampHistoryFirstPaginator(), 3, webDriver);
//            Thread.sleep(2000);
//            ReusableMethods.clickElement(getCampHistoryLastPaginator(), 3, webDriver);
//            ReusableMethods.screenShot("searchCampaignHistoryById2", webDriver);
//        } catch (Exception ignored) {
//        }
//
//        try {
//            Thread.sleep(2000);
//            webDriver.navigate().refresh();
//            brandSelectionPage.clickApplyBrandButton();
//            Thread.sleep(2000);
//            ReusableMethods.pageDownAct(webDriver);
//            ReusableMethods.clickElement(getCampHistoryLastPaginator(), 3, webDriver);
//            ReusableMethods.screenShot("searchCampaignHistoryById3", webDriver);
//        } catch (Exception ignored) {
//        }
//
//        ReusableMethods.waitForPageToLoad(3, webDriver);
//        ReusableMethods.checkTime(1270);
//        ReusableMethods.screenShot("searchCampaignHistoryById4", webDriver);
//
//        try {
//            Thread.sleep(2000);
//            webDriver.navigate().refresh();
//            brandSelectionPage.clickApplyBrandButton();
//            Thread.sleep(2000);
//            ReusableMethods.pageDownAct(webDriver);
//            ReusableMethods.clickElement(getCampHistoryLastPaginator(), 3, webDriver);
//            ReusableMethods.screenShot("searchCampaignHistoryById5", webDriver);
//        } catch (Exception ignored) {
//        }
//        ReusableMethods.checkTime(1300);
//        System.out.println(getCampaignHistoryRows().size());

        if (getCampaignHistoryRows().size() > 0) {

            WebElement campaign = searchCampaignHistoryEntryById(campaignId);

            if (campaign != null) {
                result = true;
            }

        } else {
            result = false;
        }
        return result;
    }

    private WebElement searchCampaignHistoryEntryById(String campaignId) {
        return this.searchCampaignEntryById(campaignId, getCampaignHistoryRows());
    }

    private WebElement searchActiveCampaignEntryById(String campaignId) {
        return this.searchCampaignEntryById(campaignId, getActiveCampaignRows());
    }

    private WebElement searchScheduledCampaignEntryById(String campaignId) {
        return this.searchCampaignEntryById(campaignId, getScheduledCampaignRows());
    }

    private WebElement searchPendingCampaignEntryById(String campaignId) {
        return this.searchCampaignEntryById(campaignId, getPendingCampaignRows());
    }

    /**
     * REMARK by developer: Due to lack of knowledge and given the
     * non-strictness of searched <span>-element the implementation is as
     * follows. With new knowledge and/or changes in given HTML code,
     * implementation should be changed (as soon as possible).
     *
     * @param campaignId
     * @param listWebElements
     * @return Row cell with text that equals given campaignId (WebElement)
     */
    private WebElement searchCampaignEntryById(String campaignId, List<WebElement> listWebElements) {

//        /*  built in to guaranty that table rows are available
//         *  (necessary due to timing constraints caused(?) by Angular))
//         */
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // open active campaign (if closed)
        if (this.getActiveCampaignPanelButtonLoad().getAttribute("aria-expanded").equals("false")) {

            this.clickActiveCampaignPanelButtonLoad();

//             waiting block for display/update of data row
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            ReusableMethods.waitForVisibility(listWebElements.get(0), 5, webDriver);
        }

        for (WebElement element : listWebElements) {

            String[] textParts = element.getText().split(" ");

            if (element.getText().contains(campaignId)){
                return element;
            }
            if (textParts == null || textParts.length <= 1) {
                continue;
            }
            if (textParts[1].trim().startsWith(campaignId)) {
                return element;
            }
        }
        return null;
    }

    /**
     * @return the closeButtonDetailsDialog
     */
    public WebElement getCloseButtonDetailsDialog() {
        return closeButtonDetailsDialog;
    }

    public void clickCloseButtonDetailsDialog() {
        this.getCloseButtonDetailsDialog().click();
    }

    public WebElement getCampaignHistoryRewindByCampaignId(String campaignId) {

        WebElement element = this.searchCampaignHistoryEntryById(campaignId);

        if (element != null) {
//            WebElement elementRewind = element.findElement(By.xpath(".//a[contains(text(),'Rewind')]"));
//            return elementRewind;

            return getRewindButton();

        }

        return null;

    }

    public WebElement getCampaignHistoryStatusCampaignId(String campaignId) {

        WebElement element = this.searchCampaignHistoryEntryById(campaignId);

        if (element != null) {
            return element.findElement(By.xpath(".//td[3]"));
        } else {
            return null;
        }

    }

    /**
     * 'Rewind' of historical campaign.
     *
     * @param campaignId
     */
    public void clickCampaignHistoryRewindByCampaignId(String campaignId) {

        // open campaign history (if closed)
        if (this.getCampaignHistoryPanelButtonLoad().getAttribute("aria-expanded").equals("false")) {

            this.clickCampaignHistoryPanelButtonLoad();

            // waiting block for display/update of data row
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        WebElement element = this.getCampaignHistoryRewindByCampaignId(campaignId);
        if (null != element) {
            element.click();
        }
    }

    public void openCampaignHistoryLoad() {
        // open campaign history (if closed)
        if (this.getCampaignHistoryPanelButtonLoad().getAttribute("aria-expanded").equals("false")) {

            this.clickCampaignHistoryPanelButtonLoad();

            // waiting block for display/update of data row
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void openActiveCampaignPanelButtonLoad() {
        // open campaign history (if closed)
        if (this.getActiveCampaignPanelButtonLoad().getAttribute("aria-expanded").equals("false")) {

            this.getActiveCampaignPanelButtonLoad().click();

            // waiting block for display/update of data row
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    public int getCampaignsCountInCampaignHistory() {
        return getCampaignHistoryRows().size();

    }

    public int getCampaignIndexInHistoryQue(String campaignName) {

        return getCampaignHistoryRows().indexOf(searchCampaignHistoryEntryById(campaignName));

    }


    public int getCampaignCountInCampaignHistory(String campaignName){

        List<WebElement> campaignHistory = new ArrayList<>();

        for (WebElement element : getCampaignHistoryRows()) {
            if (element.getText().contains(campaignName)){
                campaignHistory.add(element);
            }
        }
            return campaignHistory.size();
    }


    /**
     * @return the clickOKButtonErrorDialogueRewindOperation
     */
    public WebElement getOkButtonErrorDialogueRewindOperation() {
        return okButtonErrorDialogueRewindOperation;
    }

    public void clickOkButtonErrorDialogueRewindOperation() {
        this.getOkButtonErrorDialogueRewindOperation().click();
    }

    public WebElement getHeadingInErrorDialogueOfForceAbortOperation() {
        return headingInerrorDialogueOfForceAbortOperation;
    }

    public WebElement getErrorTextInDialogueOfForceAbortOperation() {
        return errorTextInDialogueOfForceAbortOperation;
    }

    public WebElement getOkButtonInErrorDialogueOfForceAbortOperation() {
        return okButtonInErrorDialogueOfForceAbortOperation;
    }

    public void clickOkButtonInErrorDialogueOfForceAbortOperation() {
        this.getOkButtonInErrorDialogueOfForceAbortOperation().click();
    }

    // After clicking the Rewind button in Campaign History, it is necessary to wait 6 minutes for the ID to be
    // displayed in Active Campaign. For this reason, a wait of about 6 minutes has been added.
    public void afterCampaignHistoryRewindToActiveCampaign(String CAMPAIGN_NAME) {
        for (int i = 1; i < 90; i++) {
//            clickActiveCampaignPanelButtonLoad();
            openActiveCampaignPanelButtonLoad();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isActiveCampaignAvailable(CAMPAIGN_NAME)) {
                break;
            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                clickActiveCampaignPanelButtonLoad();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            clickActiveCampaignPanelButtonLoad();
        }
    }


    public void afterCampaignHistoryRewindToActiveCampaign1(String CAMPAIGN_NAME) {
        for (int i = 1; i < 15; i++) {
            clickActiveCampaignPanelButtonLoad();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (isActiveCampaignAvailable(CAMPAIGN_NAME)) {
                break;
            } else {
                try {
                    Thread.sleep(14000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                clickActiveCampaignPanelButtonLoad();
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            clickActiveCampaignPanelButtonLoad();
        }
    }
}

