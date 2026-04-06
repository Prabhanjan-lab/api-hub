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
public class ConfigurationTabMBBA extends Page {

    @FindBy(xpath="//landing-page-configuration//legend[contains(text(), 'Edit Landing Page')]")
    private WebElement mbbaGroupTitleEditLandingPage;

    public ConfigurationTabMBBA(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the mbbaGroupTitleEditLandingPage
     */
    public WebElement getMbbaGroupTitleEditLandingPage() {
        return mbbaGroupTitleEditLandingPage;
    }

}
