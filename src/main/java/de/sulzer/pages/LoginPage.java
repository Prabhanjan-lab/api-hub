package de.sulzer.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;
import testFramework.StartClass;
import testFramework.utilities.ReusableMethods;

public class LoginPage extends Page{

    protected WebDriver _driver;

    public LoginPage(WebDriver driver) {
        super(driver);
        _driver = driver;
    }

    @FindBy(id = "inpt-login-username")
    private WebElement usernameInputField;

    @FindBy(id = "inpt-login-password")
    private WebElement passwordInputField;

    @FindBy(id = "btn-login-submit")
    private WebElement submitBtn;

    @FindBy(className = "form-signin-heading")
    private WebElement loginHeader;



    public void setUsernameInputField(String username) {
        ReusableMethods.waitForClickablility(usernameInputField, 3, _driver);
        usernameInputField.sendKeys(username);
    }

    public void setPasswordInputField(String password) {
        passwordInputField.sendKeys(password);
    }

    public WebElement getLoginButton() {
        return this.submitBtn;
    }

    public void clickLoginButton() {
        submitBtn.click();
    }

    public WebElement getLoginHeader() {
        return this.loginHeader;
    }

    public String getLoginHeading() {
        return loginHeader.getText();
    }

    public void login(String username, String password) {

        ReusableMethods.waitForPageToLoad(5, _driver);
        this.setUsernameInputField(username);
        this.setPasswordInputField(password);
        this.clickLoginButton();

    }

    public void loginAsAdmin() {
        this.login("Testautomatisierung Sulzer", "Testautomatisierung Sulzer");
    }

    public void loginAsUser(){
        this.login(StartClass.userName, StartClass.userPassword);
    }

    public void loginAsUserWithLdapOperatingRights(){
        this.login(StartClass.userNameLdapOperating, StartClass.userPasswordLdapOperating);
    }

    public void loginAsUserWithLowRights(){
        this.login(StartClass.userNameUser, StartClass.userPasswordUser);
    }


}

