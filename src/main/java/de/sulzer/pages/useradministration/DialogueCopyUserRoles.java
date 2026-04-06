/**
 *
 */
package de.sulzer.pages.useradministration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class DialogueCopyUserRoles extends Page {

    WebDriver driver;

    @FindBy(xpath="//h4[@class='modal-title'][contains(text(), 'Copy user roles')]")
    private WebElement titlePageCopyUserRoles;

    @FindBy(xpath="//button[contains(text(), 'Copy roles')]")
    private WebElement buttonCopyRoles;

    @FindBy(xpath="//div/button[@class='close']")
    private WebElement buttonCloseCopyRoles;

//    @FindBy(xpath="//p-dropdown/div/div/span[contains(text(), 'Select a user')]")  // Anpassung
    @FindBy(xpath = "//span[.='Select a user']")
    private WebElement openDropDownUserSearch;

//    @FindBy(xpath="//input[@class='ui-dropdown-filter ui-inputtext ui-widget ui-state-default ui-corner-all']") // Anpassung
//    @FindBy(xpath="//input[@class='p-dropdown-filter p-inputtext p-component ng-tns-c44-65']']")
    @FindBy(css = "input.p-dropdown-filter.p-inputtext")
    private WebElement inputTextFieldDropDownSearchUser;

    @FindBy(xpath="//div[contains(@class, 'ui-helper-clearfix primeng-custom-family')]/div[1]")
    private WebElement foundUserByInputField;

    public DialogueCopyUserRoles(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the titlePageCopyUserRoles
     */
    public WebElement getTitlePageCopyUserRoles() {
        return titlePageCopyUserRoles;
    }

    public WebElement getButtonCopyRoles() {
        return this.buttonCopyRoles;
    }

    public void clickButtonCopyRoles() {
        this.getButtonCopyRoles().click();
    }

    /**
     * @return the buttonCloseCopyRoles
     */
    public WebElement getButtonCloseCopyRoles() {
        return this.buttonCloseCopyRoles;
    }

    public void clickButtonCloseCopyRoles() {
        this.getButtonCloseCopyRoles().click();
    }

    /**
     * @return the openDropDownUserSearch
     */
    public WebElement getOpenDropDownUserSearch() {
        return this.openDropDownUserSearch;
    }

    public void clickOpenDropDownUserSearch() {
        this.getOpenDropDownUserSearch().click();
    }

    /**
     * @return the inputTextFieldDropDownSearchUser
     */
    public WebElement getInputTextFieldDropDownSearchUser() {
        return this.inputTextFieldDropDownSearchUser;
    }

    public void searchNameInputTextFieldDropDownSearchUser(String name) throws InterruptedException {
        Thread.sleep(1000);
        this.getInputTextFieldDropDownSearchUser().sendKeys(name);
    }

//    public void searchNameInputTextFieldDropDownSearchUser(String text) throws InterruptedException {
//        Thread.sleep(1000);
//        JavascriptExecutor jse = (JavascriptExecutor)driver;
//        jse.executeScript("arguments[0].value='"+ text +"';", getInputTextFieldDropDownSearchUser());
//    }

    /**
     * @return the foundUserByInputField
     */
    public WebElement getFoundUserByInputField() {
        return this.foundUserByInputField;
    }

    public void clickFoundUserByInputField() {
        this.getFoundUserByInputField().click();
    }

}
