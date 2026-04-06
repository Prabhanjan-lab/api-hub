/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import org.openqa.selenium.WebDriver;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.useradministration.UserOverview;
import de.sulzer.pages.util.constants.ConstantsRoles;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;


/**
 * @author Sulzer GmbH
 *
 */
public class ActionsOnupUserOverview {



    public ActionsOnupUserOverview() {

    }



    public void changeRoleRightByUserId(AdminPortletHomepage adminPortletHomepage, UserOverview userOverview, String userName, String ...rights) {

        this.openMenuUserOverview(adminPortletHomepage);

        userOverview.selectUserById(userName);

        userOverview.deselectAllRoles();

        for (String right: rights) {
            userOverview.selectRole(right);
        }

        userOverview.closeModal();

    }


    public void changeRoleRightByUserIdRemoveAllRoles(
            AdminPortletHomepage adminPortletHomepage,
            UserOverview userOverview,
            String userName,
            String ...rights) {

        this.openMenuUserOverview(adminPortletHomepage);

        userOverview.selectUserById(userName);

        userOverview.deselectAllRoles();

        userOverview.closeModal();

    }

    public void reLogIn(AdminPortletHomepage adminPortletHomepage, WebDriver webDriver) {

        adminPortletHomepage.clickLinkLogout();

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(webDriver);

    }

    public void changeRoleRightByUserIdAndReLogIn(
            AdminPortletHomepage adminPortletHomepage,
            UserOverview userOverview,
            String userName,
            WebDriver webDriver,
            String ...rights) {

        this.changeRoleRightByUserId(adminPortletHomepage, userOverview, userName, rights);

        this.reLogIn(adminPortletHomepage, webDriver);

    }

    public void openMenuUserOverview(AdminPortletHomepage adminPortletHomepage) {

        // checking open menu
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (
                !(adminPortletHomepage.isAvailableMenuEntryUserOverview()) ||
                !(adminPortletHomepage.getUserOverview().isDisplayed())
                ) {
            adminPortletHomepage.clickUserManagement();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        adminPortletHomepage.clickUserOverview();

    }

    public void changeUserRoleToNothing(AdminPortletHomepage adminPortletHomepage,
                                        UserOverview userOverview,
                                        String userName) {

        adminPortletHomepage.clickUserManagement();
        adminPortletHomepage.clickUserOverview();
        userOverview.selectUserById(userName);
        userOverview.deselectAllRoles();
        userOverview.closeModal();

    }

    public void changeUserRoleToRoot(
            WebDriver webDriver,
            AdminPortletHomepage adminPortletHomepage,
            UserOverview userOverview,
            String userName) {

        this.openMenuUserOverview(adminPortletHomepage);

        userOverview.selectUserById(userName);
        userOverview.deselectAllRoles();
        userOverview.selectRole(ConstantsRoles.OU_ROLE_ROOT);
        userOverview.closeModal();

    }

    public void changeUserRoleToRoot(AdminPortletHomepage adminPortletHomepage,
                                            UserOverview userOverview,
                                            String userName) {


        this.openMenuUserOverview(adminPortletHomepage);

        userOverview.selectUserById(userName);
        userOverview.deselectAllRoles();
        userOverview.selectRole(ConstantsRoles.OU_ROLE_ROOT);
        userOverview.closeModal();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
