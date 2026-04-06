/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.configurationlist.ConfigurationDeleteMappingMIB;
import de.sulzer.pages.configurationlist.ConfigurationDialogueDNDAMappingMIB;
import de.sulzer.pages.configurationlist.ConfigurationList;

/**
 * @author Sulzer GmbH
 *
 */
public class ActionsOnupConfigurationList {

    /**
     *
     */
    public ActionsOnupConfigurationList() {

    }

    public void openConfigurationListMBBA(
            AdminPortletHomepage adminPortletHomepage,
            ConfigurationList configurationList) throws InterruptedException {

        openMenuConfigrationList(adminPortletHomepage);

        configurationList.clickTabMBBA();

    }

    public void openConfigurationListMBBB(
            AdminPortletHomepage adminPortletHomepage,
            ConfigurationList configurationList) throws InterruptedException {

        openMenuConfigrationList(adminPortletHomepage);

        configurationList.clickTabMBBB();

    }

    /**
     * @param adminPortletHomepage
     */
    private void openMenuConfigrationList(AdminPortletHomepage adminPortletHomepage) {
        if (! adminPortletHomepage.getMenuConfigurationListEntry().isDisplayed()) {
            adminPortletHomepage.clickMenuConfigurationList();
            adminPortletHomepage.clickMenuConfigurationListEntry();
        }
    }

    /**
     * @param configurationDialogueDNDAMappingMIB
     * @param configurationDeleteMappingMIB
     * @throws Exception
     */
    public void deleteDNDAMappingEntry(
            String mappingEntry,
            ConfigurationDialogueDNDAMappingMIB configurationDialogueDNDAMappingMIB, ConfigurationDeleteMappingMIB configurationDeleteMappingMIB) throws Exception {

        WebElement element = configurationDialogueDNDAMappingMIB.getRowOfEntry(mappingEntry);

        if (null != element) {

            element.findElement(By.xpath(".//button[contains(@class, 'btn btn-danger btn-xs')]")).click();

            configurationDeleteMappingMIB.clickButtonYes();

        }

    }

}
