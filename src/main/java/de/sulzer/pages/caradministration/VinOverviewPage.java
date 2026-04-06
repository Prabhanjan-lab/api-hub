package de.sulzer.pages.caradministration;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.PageWithNavigation;

public class VinOverviewPage extends PageWithNavigation{

    public VinOverviewPage(WebDriver driver) {
        super(driver);
    }


    @FindBy(id="inf-veh-panels-title")
    private WebElement headerVehicleHeader;

    @FindBy(id="href-veh-panels-vc-load")
    private WebElement vehicleConfigurationPanelLink;

    @FindBy(xpath="//span[contains(text(), 'Infotainment')]")
    private WebElement InfotainmentTab;

    @FindBy(xpath="//span[contains(text(), 'cGW NAD')]")
    private WebElement cgwNadTab;

    @FindBy(xpath="//span[contains(text(), 'OCU2/OCU3')]")
    private WebElement ocuTwoOcuThreeTab;

    @FindBy(xpath="//a[contains(text(), 'MBT Calculation')]")
    private WebElement mbtCalculationPanelLink;

    @FindBy(id="href-veh-panels-set-load")
    private WebElement optionsPanelLink;

    @FindBy(id="cb-veh-settings-test-veh")
    private WebElement testVehicleCheckbox;

    @FindBy(id="cb-veh-settings-prot-veh")
    private WebElement prototypeVehicleCheckbox;

    @FindBy(id="cb-veh-settings-mib-swup")
    private WebElement softVrsCompDisb;

    @FindBy(id="cb-veh-settings-skip-mass-notif")
    private WebElement skipMassNotificationCheckbox;

    @FindBy(id="href-veh-panels-ac-load")
    private WebElement activeCampaignPanelLink;

    @FindBy(xpath="//a[contains(text(), 'Scheduled Campaigns')]")
    private WebElement scheduledCampaignsPanelLink;

    @FindBy(id = "href-veh-panels-pc-load")
    private WebElement pendingCampaignsPanelLink;

    @FindBy(xpath="//a[contains(text(), 'Campaign History')]")
    private WebElement campaignHistoryPanelLink;

    @FindBy(xpath="//a[contains(text(), 'Vehicle Attributes')]")
    private WebElement vehicleAttributesPanelLink;

    @FindBy(id="btn-veh-settings-save")
    private WebElement saveButton;

    @FindBy(xpath="//*[@class='pull-right settingsMessage']")
    private WebElement settingsMessage;

    @FindAll(
            @FindBy(id = "#inf-veh-panels-ac-camp-crit")
    )
    private List<WebElement> activeCampaignID;

    @FindAll(
            @FindBy(id = "inf-veh-panels-sc-camp-crit")
    )
    private List<WebElement> scheduledCampaignID;

    @FindAll(
            @FindBy(id = "inf-veh-panels-pc-camp-crit")
    )
    private List<WebElement> pendingCampaignsID;

    @FindAll(
            @FindBy(id = "href-veh-panels-pc-cancel")
    )
    private List<WebElement> pendingCancelButton;

    @FindAll(
            @FindBy(id = "inf-veh-panels-ch-camp-crit")
    )
    private List<WebElement> campaignHistoryID;

    @FindBy(id="inf-veh-panels-ac-camp-crit")
    private WebElement campaignIDofActivecampaign;

    @FindBy(id="href-veh-panels-ac-force-abort")
    private WebElement forceAbort;

    public WebElement getforceabort() {
        return forceAbort;
    }

    public WebElement getMbtCalculationPanelLink() {
        return mbtCalculationPanelLink;
    }

    public WebElement getInfotainmentTab() {
        return InfotainmentTab;
    }

    public WebElement getCgwNadTab() {
        return cgwNadTab;
    }

    public WebElement getOcuTwoOcuThreeTab() {
        return ocuTwoOcuThreeTab;
    }

    public WebElement getOptionsPanelLink() {
        return optionsPanelLink;
    }

    public void clickOptionsPanelLink() {
        this.getOptionsPanelLink().click();
    }

    public WebElement getTestVehicleCheckbox() {
        return testVehicleCheckbox;
    }

    public WebElement getPrototypeVehicleCheckbox() {
        return prototypeVehicleCheckbox;
    }

    public WebElement getSoftVrsCompDisb() {
        return softVrsCompDisb;
    }

    public WebElement getSkipMassNotificationCheckbox() {
        return skipMassNotificationCheckbox;
    }

    public WebElement getActiveCampaignPanelLink() {
        return activeCampaignPanelLink;
    }

    public List<WebElement> getActiveCampaignID() {
        return activeCampaignID;
    }

    public List<WebElement> getScheduledCampaignID() {
        return scheduledCampaignID;
    }

    public List<WebElement> getPendingCampaignsID() {
        return pendingCampaignsID;
    }

    public List<WebElement> getPendingCancelButton() {
        return pendingCancelButton;
    }

    public List<WebElement> getCampaignHistoryID() {
        return campaignHistoryID;
    }

    public void clickActiveCampaignPanelLink() {
        this.getActiveCampaignPanelLink().click();
    }

    public WebElement getScheduledCampaignsPanelLink() {
        return scheduledCampaignsPanelLink;
    }

    public WebElement getPendingCampaignsPanelLink() {
        return pendingCampaignsPanelLink;
    }

    public void clickPendingCampaignsPanelLink() {
        this.getPendingCampaignsPanelLink().click();
    }

    public WebElement getCampaignHistoryPanelLink() {
        return campaignHistoryPanelLink;
    }

    public void clickCampaignHistoryPanelLink() {
        this.getCampaignHistoryPanelLink().click();
    }

    public int getRowsOfPendingCampaignsSize(){

        return getPendingCampaignsID().size();
    }

    public int getRowsOfCampaignHistorySize() {

        return getCampaignHistoryID().size();
    }

    public int getRowsofActiveCampaign() {

        return getActiveCampaignID().size();
    }

    public String presentActiveCampaignID() {
        return this.getActiveCampaignID().get(0).getText();
    }

    public void clickforceabort() {
        this.getforceabort().click();
    }

    public WebElement getSaveButton() {
        return saveButton;
    }

    public void clickSaveOptionsButton() {
        getSaveButton().click();
    }

    public WebElement getSettingsMessage() {
        return settingsMessage;
    }


}
