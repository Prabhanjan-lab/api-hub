package de.sulzer.pages.genericelements;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.utils.guistandardfunctions.ActionsOnupVehicleOverview;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import testFramework.utilities.ReusableMethods;

public class PageWithNavigation extends Page {

    protected WebDriver _driver;

    public PageWithNavigation(WebDriver driver) {
        super(driver);
        _driver = driver;
    }

    @FindBy(tagName = "h1")
    private WebElement welcomeHeader;

    @FindBy(xpath = "//span[.='Campaign Management']")
    private WebElement campaignManagement;

    @FindBy(id = "href-app-nav-inbox-link")
    private WebElement campaignManagementInbox;

    @FindBy(id = "href-app-nav-search-list")
    private WebElement campaignMgmtSearch;

    @FindBy(id = "href-app-nav-status-list")
    private WebElement cmRolloutStatus;

    @FindBy(id = "href-app-nav-status-rep")
    private WebElement campaignMgmStatusReport;

    @FindBy(id = "head-app-nav-veh-adm-list")
    private WebElement vehicleAdministrationList;

    @FindBy(id = "href-app-nav-veh-overview-list")
    private WebElement vehicleOverview;

    @FindBy(id = "href-app-nav-except-list")
    private WebElement exceptionListManagement;

    @FindBy(id = "href-app-nav-template-mngmt-list")
    private WebElement templateManagement;

    @FindBy(id = "head-app-nav-test-menu-list")
    private WebElement testingMenu;

    @FindBy(id = "href-app-nav-manual-recall")
    private WebElement manualRecallSystem;

    @FindBy(id = "href-app-nav-manual-s42")
    private WebElement manualService42System;

    @FindBy(id = "inf-app-nav-sector-log-list-ic")
    private WebElement sectorLog;

    @FindBy(id = "href-app-nav-log-overview")
    private WebElement loggingOverview;

    @FindBy(id = "href-app-nav-log-report")
    private WebElement loggingReporting;

    @FindBy(id = "href-app-nav-log-detail")
    private WebElement loggingDetails;

    @FindBy(id = "inf-app-nav-user-mngmt-ic")
    private WebElement userManagement;

    @FindBy(id = "href-app-nav-user-overview")
    private WebElement userOverview;

    @FindBy(id = "href-app-nav-user-config")
    private WebElement configurationFileEditing;

    @FindBy(id = "inf-app-nav-config-ic")
    private WebElement menuConfigurationList;

    @FindBy(id = "href-app-nav-config-list")
    private WebElement menuConfigurationListEntry;

    @FindBy(id = "inf-app-nav-doc-ic")
    private WebElement documentation;

    @FindBy(id = "href-app-nav-doc-recall")
    private WebElement documentationReCall;

    @FindBy(id = "href-app-nav-doc-carport")
    private WebElement documentationCarPort;

    @FindBy(id = "href-app-head-user-profile")
    private WebElement userProfile;

    @FindBy(xpath = "//select")
    private WebElement brandSelectionOptionen;

    @FindBy(id = "brandSelector")
    private WebElement brandSelectElement;
    private Select brandSelect;

    @FindBy(id = "btn-brand-select-apply")
    private WebElement applyBrandButton;

    //saveClick Overlays
    @FindAll({
            @FindBy(xpath = "//*[contains(@class,'overlay')] | //*[contains(@class,'loading')] | //*[contains(@class,'modal-fade')]")
    })
    private List<WebElement> overlay;




    public boolean getVisibilityOfTemplateMenu() {
        return this.templateManagement.isDisplayed();
    }

    public WebElement getTemplateManagement() {
        return this.templateManagement;
    }

    public WebElement getExceptionListManagement() {
        return this.exceptionListManagement;
    }

    public List<WebElement> getOverlay() {
        return overlay;
    }

    public void saveClick(WebElement element) {
        _driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        int helper;
        for (int count = 0; count < 10; count++) {

            if (null == this.getOverlay()) {
                helper = this.getOverlay().size();
            } else {
                helper = 0;
            }

            if (helper == 0) {
                count = 10;
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PageWithNavigation.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Overlay found or data not completely loaded");
            }
        }

        element.click();
        _driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void clickVehicleAdministrationList() {
        this.saveClick(this.vehicleAdministrationList);
    }

    public WebElement getCampaignManagementInbox() {
        return this.campaignManagementInbox;
    }

    public void clickCampaignManagementInbox() {
        this.saveClick(this.getCampaignManagementInbox());
    }

    public boolean isAvailableMenuEntryCampaignMgmtInbox() {
        return (null != this.getCampaignManagementInbox());
    }

    public WebElement getCmSearch() {
        return this.campaignMgmtSearch;
    }

    public boolean isAvailableMenuEntryCampaignMgmtSearch() {
        return (null != this.getCmSearch());
    }

    public void clickCampaignManagementSearch() {
        this.saveClick(this.getCmSearch());
    }

    public WebElement getCmRolloutStatus() {
        return this.cmRolloutStatus;
    }

    public void clickCmRolloutStatus() {
        this.saveClick(this.getCmRolloutStatus());
    }

    /**
     * @return the campaignMgmStatusReport
     */
    public WebElement getCampaignMgmStatusReport() {
        return campaignMgmStatusReport;
    }

    public WebElement getWelcomeHeader() {
        return this.welcomeHeader;
    }

    public WebElement getCampaignManagement() {
        return this.campaignManagement;
    }

    public void clickStatusReport() {
        this.getCampaignMgmStatusReport().click();
    }

    public void clickCampaignManagement() {
        this.saveClick(this.getCampaignManagement());
    }

    public WebElement getVehicleAdministrationList() {
        return this.vehicleAdministrationList;
    }

    public WebElement getUserProfile() {
        return userProfile;
    }

    public WebElement getBrandSelectionOptionen() {
        return brandSelectionOptionen;
    }

    public void clickBrandSelectionAudiButton() {
        this.saveClick(this.getBrandSelectionOptionen());
        this.brandSelect = new Select(this.brandSelectElement);
    }

    public Select getBrandSelect() {
        return brandSelect;
    }

    public void selectBrandByName(String name) {
        this.getBrandSelect().selectByValue(name);
    }

    public WebElement getApplyBrandButton() {
        return applyBrandButton;
    }

    public void clickApplyBrandButton() {
        this.saveClick(this.getApplyBrandButton());
    }

    public WebElement getUserManagement() {
        return userManagement;
    }

    public void clickUserManagement() {
        ReusableMethods.clickJS(getUserManagement(), _driver);
    }

    public WebElement getUserOverview() {
        return userOverview;
    }

    public boolean isAvailableMenuEntryUserOverview() {
        return (null != this.getUserOverview());
    }

    public void clickUserOverview() {
        ReusableMethods.clickJS(getUserOverview(), _driver);
    }

    public WebElement getConfigurationFileEditing() {
        return this.configurationFileEditing;
    }

    public void clickConfigurationFileEditing() {
        this.saveClick(this.getConfigurationFileEditing());
    }

    public WebElement getMenuConfigurationList() {
        return this.menuConfigurationList;
    }

    public void clickMenuConfigurationList() {
        this.saveClick(this.getMenuConfigurationList());
    }

    public WebElement getMenuConfigurationListEntry() {
        return this.menuConfigurationListEntry;
    }

    public void clickMenuConfigurationListEntry() {
        this.saveClick(this.getMenuConfigurationListEntry());
    }

    public WebElement getDocumentation() {
        return this.documentation;
    }

    public void clickDocumentation() {
        this.saveClick(this.getDocumentation());
    }

    public WebElement getDocumentationReCall() {
        return this.documentationReCall;
    }

    public void clickDocumentationReCall() {
        this.getDocumentationReCall().click();
    }

    public WebElement getDocumentationCarPort() {
        return this.documentationCarPort;
    }

    public void clickDocumentationCarPort() {
        this.getDocumentationCarPort().click();
    }

    public void switchFromAudiToBentley() {
        this.clickBrandSelectionAudiButton();
        this.selectBrandByName("Audi");
        this.clickApplyBrandButton();
    }

    public void switchFromBentleyToAudi() {
        this.clickBrandSelectionBentleyButton();
        this.selectBrandByName("Bentley");
        this.clickApplyBrandButton();
    }

    public void clickBrandSelectionBentleyButton() {
        this.getBrandSelectionOptionen().click();
        this.brandSelect = new Select(this.brandSelectElement);
    }

    public void clickUserProfile() {
        ReusableMethods.clickElement(getUserProfile(), 5, _driver);
    }

    public void expandCarAdministration() {
        this.saveClick(this.getVehicleAdministrationList());
    }

    public WebElement getVehicleOverview() {
        return this.vehicleOverview;
    }

    public void clickVehicleOverview() {
//        this.saveClick(vehicleOverview);

        ActionsOnupVehicleOverview actionsOnupVehicleOverview = new ActionsOnupVehicleOverview(_driver);
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(_driver);
        actionsOnupVehicleOverview.openVehicleOverview(adminPortletHomepage);
    }

    public boolean isAvailableMenuEntryExceptionListManagement() {
        return (null != this.getExceptionListManagement());
    }

    public void clickExceptionListManagement() {
//        this.exceptionListManagement.click();
//        ReusableMethods.clickElement(getExceptionListManagement(), 5, _driver);
        saveClick(getExceptionListManagement());
    }

    public void clickTemplateManagement() {
        this.templateManagement.click();
    }

    public WebElement getTestingMenu() {
        return testingMenu;
    }

    public void clickTestingMenu() {
//        this.getTestingMenu().click();
//        ReusableMethods.clickElement(getTestingMenu(), 20, _driver);
        saveClick(getTestingMenu());
    }

    public WebElement getManualRecallSystem() {
        return manualRecallSystem;
    }

    public boolean isAvailableMenuEntryReCall() {
        return (null != this.getManualRecallSystem());
    }

    public void clickManualRecallSystem() {

        ReusableMethods.clickElement(getManualRecallSystem(), 5, _driver);
    }

    public WebElement getManualService42() {
        return manualService42System;
    }

    public boolean isAvailableMenuEntryService42() {
        return (null != this.getManualService42());
    }

    public void clickManualService42System() {
        this.getManualService42().click();
    }

    public WebElement getSectorLog() {
        return this.sectorLog;
    }

    public void clickSectorLog() {
        ReusableMethods.clickJS(getSectorLog(), _driver);
        ReusableMethods.waitForClickablility(getLoggingOverview(), 5, _driver);
    }

    public WebElement getLoggingOverview() {
        return this.loggingOverview;
    }

    public boolean isAvailableMenuEntryLoggingOverview() {
        return (null != this.getLoggingOverview());
    }

    public void clickLoggingOverview() {
        this.saveClick(this.getLoggingOverview());
    }

    public WebElement getLoggingReporting() {
        return this.loggingReporting;
    }

    public void clickLoggingReporting() {
        this.saveClick(this.getLoggingReporting());
    }

    public WebElement getLoggingDetails() {
        return this.loggingDetails;
    }

    public void clickLoggingDetails() {
        this.getLoggingDetails().click();
    }

}
