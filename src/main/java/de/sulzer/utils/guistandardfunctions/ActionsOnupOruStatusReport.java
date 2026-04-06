/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.genericelements.PageWithNavigation;
import testFramework.utilities.ReusableMethods;

/**
 * @author Sulzer GmbH
 *
 */
public class ActionsOnupOruStatusReport {

    static final String GOCAT = "GOCAT";
    public static final String TRANSLATION_INPUT = "malo código fuente";
    public static final String TRANSLATION_OUTPUT = "bad sourcecode";


    private WebDriver webDriver;

    /**
     *
     */
    public ActionsOnupOruStatusReport(WebDriver driver) {
        this.webDriver = driver;
    }

    /**
     * @param adminPortletHomepage
     */
    public void openStatusReport(AdminPortletHomepage adminPortletHomepage) {

//        if (!this.webDriver.findElement(By.xpath(PageWithNavigation.CAMPAIGN_MGMT_SEARCH)).isDisplayed()){
//            adminPortletHomepage.clickCampaignManagement();
//        }
//        Thread.sleep(500);
//        adminPortletHomepage.clickStatusReport();
        if (!adminPortletHomepage.getCampaignMgmStatusReport().isDisplayed()) {
            adminPortletHomepage.clickCampaignManagement();
        }
        ReusableMethods.clickElement(adminPortletHomepage.getCampaignMgmStatusReport(), 2, webDriver);
    }

}
