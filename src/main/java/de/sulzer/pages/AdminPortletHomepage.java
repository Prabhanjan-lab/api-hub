package de.sulzer.pages;

import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import de.sulzer.pages.genericelements.PageWithNavigation;
import testFramework.utilities.ReusableMethods;

public class AdminPortletHomepage extends PageWithNavigation {

    protected WebDriver webDriver;

    public AdminPortletHomepage(WebDriver driver) {
        super(driver);
        this.webDriver = driver;
    }


//    @FindBy(xpath = "//p[@class='navbar-brand'][contains(text(), 'Admin-Portlet')]")
    @FindBy(xpath = "//span[@class='header-navigation'][contains(text(), 'Admin-Portlet')]")
    private WebElement titleWebpageAdminPortlet;

    @FindBy(xpath = "//a[@routerlink='user-profile']")
    private WebElement linkUserProfile;

    @FindBy(xpath = "(//*[.='Logout'])[1]")
    private WebElement linkLogout;

    @FindBy(id = "href-app-head-choose-brand")
    private WebElement linkBrand;

    @FindBy(id = "brandSelector")
    private WebElement brandSelectElement;
    private Select brandSelector;

    @FindBy(xpath = "//choose-brand-modal//i[contains(@class,'refresh')]/..")
    private WebElement buttonAnwenden;

    @FindBy(id = "btn-choose-brand-mod-cancel")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[.=' Ok ']")
    private WebElement notAuthorisedOkButton;

    @FindBy(xpath = "//button[.='OK']")
    private WebElement warningOkButton;

    @FindBy(css = "div img")
    private WebElement flag;

    public WebElement getFlag() {
        return flag;
    }

    public WebElement getWarningOkButton() {
        return warningOkButton;
    }

    public WebElement getNotAuthorisedOkButton() {
        return notAuthorisedOkButton;
    }

    /**
     * @return the titleWebpageAdminPortlet
     */
    public WebElement getTitleWebpageAdminPortlet() {
        return titleWebpageAdminPortlet;
    }

    public void clickTitleWebpageAdminPortlet() {
        this.getTitleWebpageAdminPortlet().click();
    }

    /**
     * @return the linkUserProfile
     */
    public WebElement getLinkUserProfile() {
        return linkUserProfile;
    }

    /**
     * Check, if logged in user has given name.
     *
     * @param userName
     * @return true, in case given user is logged-in according profile link test,
     *         otherwise false
     */
    public boolean isUserLoggedIn(String userName) {
        WebElement element = this.getLinkUserProfile()
                .findElement(By.xpath(".//span[contains(text(), '" + userName + "')]"));
        return element.getText().trim().equals(userName);
    }

    public WebElement getLinkLogout() {
        return linkLogout;
    }

    public void clickLinkLogout() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ReusableMethods.clickElement(getLinkLogout(), 10, _driver);
        LogoutPage logoutPage = new LogoutPage(this.webDriver);

        // implicit and explicit wait does not work here.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {

        }

        ReusableMethods.clickElement(logoutPage.getLinkToLoginPage(), 10, _driver);
    }

    public WebElement getLinkBrand() {
        return linkBrand;
    }

    public void clickLinkBrand() {
        this.getLinkBrand().click();
    }

    public Select getBrandSelector() {
        return brandSelector;
    }

    public WebElement getButtonAnwenden() {
        return buttonAnwenden;
    }

    public WebElement getCmSearch() {
        return super.getCmSearch();
    }

    public void clickCmSearch() {
        this.saveClick(this.getCmSearch());
    }

    public void changeBrand(String brand) {
        this.clickLinkBrand();

        this.brandSelector = new Select(this.brandSelectElement);

        WebElement el = this.getBrandSelector().getOptions().stream().filter(o -> Objects.equals(o.getText(), brand))
                .findFirst().orElse(null);
        this.getBrandSelector().selectByVisibleText(el.getText());
        try {
            this.getButtonAnwenden().click();
            Thread.sleep(4000);
        } catch (NoSuchElementException | InterruptedException e) {
            System.out.println(e);
        }

    }

    public boolean isAvailableMenuEntryService42() {
        return super.isAvailableMenuEntryService42();
    }

    public boolean isAvailableMenuEntryReCall() {
        return super.isAvailableMenuEntryReCall();
    }

    public boolean isAvailableMenuEntryExceptionListManagement() {
        return super.isAvailableMenuEntryExceptionListManagement();
    }

    public boolean isAvailableMenuEntryCampaignMgmtSearch() {
        return super.isAvailableMenuEntryCampaignMgmtSearch();
    }

    public boolean isAvailableMenuEntryCampaignMgmtInbox() {
        return super.isAvailableMenuEntryCampaignMgmtInbox();
    }


    public WebElement getCancelButton() {
        return cancelButton;
    }


}
