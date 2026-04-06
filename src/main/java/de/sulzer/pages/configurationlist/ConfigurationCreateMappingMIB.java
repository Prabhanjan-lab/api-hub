/**
 *
 */
package de.sulzer.pages.configurationlist;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class ConfigurationCreateMappingMIB extends Page {

    @FindBy(xpath="//h4[contains(text(), 'Create new mapping parameters for MIB2plus and MIB3')]")
    private WebElement titleConfigurationCreateDNDAMapping;

    public static final String TITLE = "Create new mapping parameters for MIB2plus and MIB3";

    @FindBy(xpath="//div[contains(text(),'This device name is already in use.')]")
    private WebElement warningDeviceAlreadyInUse;

    @FindBy(xpath="//input[@id='da-device-name']")
    private WebElement inputDaDeviceName;

    @FindBy(xpath="//span//span[contains(text(), 'The Device Name is invalid.')]")
    private WebElement inputDaDeviceNameErrorMessage;

    @FindBy(xpath="//input[@id='master-da']")
    private WebElement inputDaMaster;

    @FindBy(xpath="//span//span[contains(text(), 'The Master DA is invalid.')]")
    private WebElement inputDaMasterErrorMessage;

    @FindBy(xpath="//input[@id='slave-da']")
    private WebElement inputDaSlave;

    @FindBy(xpath="//span//span[contains(text(), 'The Slave DA is invalid.')]")
    private WebElement inputDaSlaveErrorMessage;

    @FindBy(xpath="//button[contains(text(), 'Cancel')]")
    private WebElement buttonCancel;

    @FindBy(xpath="//button[contains(text(), 'Save')]")
    private WebElement buttonSave;

    public ConfigurationCreateMappingMIB(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the titleConfigurationCreateDNDAMapping
     */
    public WebElement getTitleConfigurationDNDAMapping() {
        return titleConfigurationCreateDNDAMapping;
    }

    /**
     * @return the warningDeviceAlreadyInUse
     */
    public WebElement getWarningDeviceAlreadyInUse() {
        return warningDeviceAlreadyInUse;
    }

    /**
     * @return the inputDaDeviceName
     */
    public WebElement getInputDaDeviceName() {
        return inputDaDeviceName;
    }

    /**
     * @param input
     */
    public void setInputDaDeviceName(String input) {
        this.getInputDaDeviceName().clear();
        this.getInputDaDeviceName().sendKeys(input);
    }

    /**
     * @return the inputDaDeviceNameErrorMessage
     */
    public WebElement getInputDaDeviceNameErrorMessage() {
        return inputDaDeviceNameErrorMessage;
    }

    /**
     * @return the inputDaMaster
     */
    public WebElement getInputDaMaster() {
        return inputDaMaster;
    }

    public void setInputDaMaster(String input) {
        this.getInputDaMaster().clear();
        this.getInputDaMaster().sendKeys(input);
    }

    /**
     * @return the inputDaMasterErrorMessage
     */
    public WebElement getInputDaMasterErrorMessage() {
        return inputDaMasterErrorMessage;
    }

    /**
     * @return the inputDaSlave
     */
    public WebElement getInputDaSlave() {
        return inputDaSlave;
    }

    public void setInputDaSlave(String input) {
        this.getInputDaSlave().clear();
        this.getInputDaSlave().sendKeys(input);
    }

    /**
     * @return the inputDaSlaveErrorMessage
     */
    public WebElement getInputDaSlaveErrorMessage() {
        return inputDaSlaveErrorMessage;
    }

    /**
     * @return the buttonCancel
     */
    public WebElement getButtonCancel() {
        return buttonCancel;
    }

    public void clickButtonCancel() {
        this.getButtonCancel().click();
    }

    /**
     * @return the buttonSave
     */
    public WebElement getButtonSave() {
        return buttonSave;
    }

    public void clickButtonSave() {
        this.getButtonSave().click();
    }

}
