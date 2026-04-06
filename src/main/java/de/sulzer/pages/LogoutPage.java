/**
 *
 */
package de.sulzer.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class LogoutPage extends Page {

//    @FindBy(xpath="//logout-component//a[@routerlink='/']")
    @FindBy(xpath = "//a[.=\"login\"]")
    private WebElement linkToLoginPage;

    /**
     * @param driver
     */
    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the linkToLoginPage
     */
    public WebElement getLinkToLoginPage() {
        return linkToLoginPage;
    }

    public void clickLinkToLoginPage() {
        this.getLinkToLoginPage().click();
    }

}
