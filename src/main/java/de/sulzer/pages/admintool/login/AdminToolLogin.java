/**
 *
 */
package de.sulzer.pages.admintool.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Bege
 *
 */
public class AdminToolLogin extends Page {

	@FindBy(id="inpt-login-username")
	WebElement inputUsername;

	@FindBy(id="inpt-login-password")
	WebElement inputPassword;

	@FindBy(id="btn-login-submit")
	WebElement buttonLogin;

	/**
	 *
	 */
	public AdminToolLogin(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the inputUsername
	 */
	public WebElement getInputUsername() {
		return inputUsername;
	}

	/**
	 * @return the inputPassword
	 */
	public WebElement getInputPassword() {
		return inputPassword;
	}

	/**
	 * @return the buttonLogin
	 */
	public WebElement getButtonLogin() {
		return buttonLogin;
	}

	public void clickButtonLogin() {
		this.getButtonLogin().click();
	}

}
