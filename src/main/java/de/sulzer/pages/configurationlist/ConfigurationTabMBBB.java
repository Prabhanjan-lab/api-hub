/**
 *
 */
package de.sulzer.pages.configurationlist;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;
import testFramework.utilities.ReusableMethods;

/**
 * @author Sulzer GmbH
 *
 */
public class ConfigurationTabMBBB extends Page {

    @FindBy(xpath="//legend[contains(text(),'Common Preferences for ORU-Campaign')]")
    private WebElement headerCommonPreferencesForORUCampaign;

    @FindBy(xpath="//input[@aria-describedby='maxRetries']")
    private WebElement inputCommonPreferencesForORUCampaign;

    @FindBy(xpath="//fieldset//div[@class='row'][1]//button[@class='btn btn-default pull-right']")
    private WebElement buttonCommonPreferencesForORUCampaignReset;

    @FindBy(xpath="//fieldset//div[@class='row'][1]//button[@class='btn btn-default btn-primary pull-right']")
    private WebElement buttonCommonPreferencesForORUCampaignApply;

    @FindBy(xpath="//legend[contains(text(), 'Setting CarPort Cache threshold')]")
    private WebElement headerSettingCarPortCacheThreshold;

    @FindBy(xpath="//input[@aria-describedby='carpotCacheThreshold']")
    private WebElement inputSettingCarPortCacheThresholdInMinutes;

    @FindBy(xpath="//fieldset//div[@class='row'][2]//button[@class='btn btn-default pull-right']")
    private WebElement buttonSettingCarPortCacheThresholdReset;

    @FindBy(xpath="//fieldset//div[@class='row'][2]//button[@class='btn btn-default btn-primary pull-right']")
    private WebElement buttonSettingCarPortCacheThresholdApply;

    @FindBy(xpath="//legend[contains(text(), 'MIB2plus and MIB3 - Device Name and Diagnostic Address Mapping')]")
    private WebElement headerGroupTitleMIB;

    @FindBy(xpath="//da-mapping-configuration//button[@class='btn pull-right btn-primary']")
    private WebElement buttonUpdateMIB;

    @FindBy(xpath="//legend[contains(text(),'Purge Datawarehouse Tables')]")
    private WebElement headerPurgeDataWarehouseTables;

    @FindBy(xpath="//button[contains(text(), 'Purge')]")
    private WebElement buttonPurgeDataWarehouseTables;

    @FindBy(xpath = "//span[.='The Cleanup has bean ended successful']")
    private WebElement purgeSuccessMessage;

    @FindBy(xpath="//legend[contains(text(),'Mass notification Settings')]")
    private WebElement headerMassNotificationSettings;

    @FindBy(xpath = "//*[.=' Yes ']")
    private WebElement confirmationYesButton;

    public ConfigurationTabMBBB(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the headerCommonPreferencesForORUCampaign
     */
    public WebElement getHeaderCommonPreferencesForORUCampaign() {
        return headerCommonPreferencesForORUCampaign;
    }

    public WebElement getInputCommonPreferencesForORUCampaign() {
        return inputCommonPreferencesForORUCampaign;
    }

    public void setValueInputCommonPreferencesForORUCampaign(String value) {
//        this.getInputCommonPreferencesForORUCampaign().clear();
        ReusableMethods.clearField(getInputCommonPreferencesForORUCampaign());
        this.getInputCommonPreferencesForORUCampaign().sendKeys(value);
    }

    public WebElement getButtonCommonPreferencesForORUCampaignReset() {
        return buttonCommonPreferencesForORUCampaignReset;
    }

    public void clickButtonCommonPreferencesForORUCampaignReset() {
        this.getButtonCommonPreferencesForORUCampaignReset().click();
    }

    public WebElement getButtonCommonPreferencesForORUCampaignApply() {
        return buttonCommonPreferencesForORUCampaignApply;
    }

    public void clickButtonCommonPreferencesForORUCampaignApply() {
        this.getButtonCommonPreferencesForORUCampaignApply().click();
    }

    public WebElement getHeaderSettingCarPortCacheThreshold() {
        return this.headerSettingCarPortCacheThreshold;
    }

    /**
     * @return the inputSettingCarPortCacheThresholdInMinutes
     */
    public WebElement getInputSettingCarPortCacheThresholdInMinutes() {
        return inputSettingCarPortCacheThresholdInMinutes;
    }

    public void setValueInputSettingCarPortCacheThresholdInMinutes(String value) {
        this.getInputSettingCarPortCacheThresholdInMinutes().clear();
        this.getInputSettingCarPortCacheThresholdInMinutes().sendKeys(value);
    }

    /**
     * @return the buttonSettingCarPortCacheThresholdReset
     */
    public WebElement getButtonSettingCarPortCacheThresholdReset() {
        return buttonSettingCarPortCacheThresholdReset;
    }

    public void clickButtonSettingCarPortCacheThresholdReset() {
        this.getButtonSettingCarPortCacheThresholdReset().click();
    }

    /**
     * @return the buttonSettingCarPortCacheThresholdApply
     */
    public WebElement getButtonSettingCarPortCacheThresholdApply() {
        return buttonSettingCarPortCacheThresholdApply;
    }

    public void clickButtonSettingCarPortCacheThresholdApply() {
        this.getButtonSettingCarPortCacheThresholdApply().click();
    }

    /**
     * @return the headerGroupTitleMIB
     */
    public WebElement getHeaderGroupTitleMIB() {
        return headerGroupTitleMIB;
    }

    /**
     * @return the buttonUpdateMIB
     */
    public WebElement getButtonUpdateMIB() {
        return buttonUpdateMIB;
    }

    public void clickButtonUpdateMIB() {
        this.getButtonUpdateMIB().click();
    }

    /**
     * @return the headerPurgeDataWarehouseTables
     */
    public WebElement getHeaderPurgeDataWarehouseTables() {
        return headerPurgeDataWarehouseTables;
    }

    public void clickPurgeButton() {
        this.getPurgeButton().click();
    }

    public WebElement getPurgeButton() {
        return this.buttonPurgeDataWarehouseTables;
    }

    public WebElement getPurgeSuccessMessage() {
        return purgeSuccessMessage;
    }

    /**
     * @return the headerMassNotificationSettings
     */
    public WebElement getHeaderMassNotificationSettings() {
        return headerMassNotificationSettings;
    }

    public WebElement getConfirmationYesButton() {
        return confirmationYesButton;
    }

}
