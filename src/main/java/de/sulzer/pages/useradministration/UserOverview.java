package de.sulzer.pages.useradministration;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindAll;
import de.sulzer.pages.genericelements.PageWithNavigation;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

/**
 *
 * @author Sulzer GmbH
 */
public class UserOverview extends PageWithNavigation {

    private WebDriver webDriver;

    public UserOverview(WebDriver driver) {

        super(driver);

        this.webDriver = driver;

    }

    WebDriverWait getWebdriverWait = new WebDriverWait(_driver, 20);

    @FindBy(xpath="//h3[@class='panel-title ng-star-inserted']")
    private WebElement titlePage;

    /*
     * search field user id
     */

    @FindBy(xpath = "(//*[contains(text(), 'User ID')])[1]")
    private WebElement inputUserID;

    @FindBy(xpath = "//div/div/input[@role='textbox']")
    private WebElement inputUserIDSearch;

    @FindBy(css = "span.p-multiselect-close-icon")
    private WebElement closeUserIdSearchDropDownInputField;

    /*
     * search field user name
     */

    @FindBy(xpath = "//div/div[contains(text(), 'User Name')]")
    private WebElement inputUserName;

    @FindBy(xpath = "//div/div/input[@role='textbox']")
    private WebElement inputUserNameSearchTextBox;

    @FindBy(xpath = "//p-multiselect/div/div[4]/div[1]/button")
    private WebElement closeUserNameSearchDropDownInputField;

    /*
     * search field LDAP role
     */

    @FindBy(xpath = "//div/div[contains(text(), 'LDAP Role')]")
    private WebElement inputLdapRole;

    @FindBy(xpath = "//div/div/input[@role='textbox']")
    private WebElement inputLdapRoleSearchTextBox;

    @FindBy(xpath = "//p-multiselect/div/div[4]/div[1]/button")
    private WebElement closeLdapRoleSearchDropDownInputField;

    /*
     * search field ONUP role
     */

    @FindBy(xpath = "//div/div[contains(text(), 'ONUP Role')]")
    private WebElement inputOnupRole;

    @FindBy(xpath = "//div/div/input[@role='textbox']")
    private WebElement inputOnupRoleSearchTextBox;

    @FindBy(xpath = "//p-multiselect/div/div[4]/div[1]/button")
    private WebElement closeOnupRoleSearchDropDownInputField;

    @FindAll({
            @FindBy(xpath = "//*[@id='cb-role-checkbox-choose-role']//span[@class='p-checkbox-icon pi pi-check']")
    })
    private List<WebElement> selectedOnupRoleBox;

    /*
     * misc. functions and elements
     */

    @FindBy(id = "inf-number-of-entries-recs")
    private WebElement numberEntries;

    @FindBy(id = "href-main-user-mngmt-edit-roles")
    private WebElement editLink;

    @FindAll({
        @FindBy(xpath="//table/tbody/tr")
    })
    private List<WebElement> listUser;

    @FindBy (id = "btn-ex-csv")
    private WebElement buttonExportUserList;

    @FindAll({
        @FindBy(css = "#tbl-edit-user-roles-mod-market td.align-right") //user roles
    })
    private List<WebElement> listRoles;

    @FindAll({
            @FindBy(id = "cb-role-checkbox-choose-role")
    })
    private List<WebElement> boxList;

    @FindBy(xpath="//button[@class='close fix-close-button']")
    private WebElement closeModal;

    @FindAll(
            @FindBy(id = "href-main-user-mngmt-copy-roles")
    )
    private List<WebElement> copyRoles;


    /**
     * @return the titlePage
     */
    public WebElement getTitlePage() {
        return titlePage;
    }

    public WebElement getInputUserID() {
        return inputUserID;
    }

    public WebElement getInputUserIDSearch() {
        return inputUserIDSearch;
    }

    public WebElement getUserEntry(String userName) throws Exception {
        return this.getSelectionByText(userName);
    }

    public void clickUserEntry(String userName) throws Exception {
        this.getUserEntry(userName).click();
    }

    public WebElement getNumberEntries() {
        return numberEntries;
    }

    public List<WebElement> getSelectedOnupRoleBox() {
        return selectedOnupRoleBox;
    }

    public WebElement getEditLink() {
        return editLink;
    }

    public List<WebElement> getListRoles() {
        return listRoles;
    }

    public List<WebElement> getBoxList() {
        return boxList;
    }

    /**
     * @return the buttonExportUserList
     */
    public WebElement getButtonExportUserList() {
        return this.buttonExportUserList;
    }

    public void clickButtonExportUserList() {
        this.getButtonExportUserList().click();
    }

    public List<WebElement> getListUser(){
        return this.listUser;
    }

    public List<WebElement> getCopyRoles() {
        return copyRoles;
    }

    public void clickLinkCopyRolesFirstUserInList() {

        if (getCopyRoles().get(0) != null) {
            getCopyRoles().get(0).click();
        }

    }

    /**
     * Looks into table, and searches in first element of user list, if "Copy roles" link
     * is available.
     *
     * @param text
     * @return if text of found element is equals to given text/string parameter
     */
    public boolean isLinkCopyRolesAvailable(String text) {

        if (this.listUser.size() <= 0) {
            return false;
        }

        WebElement element = this.listUser.get(0);

        WebElement elementCopyRoles = element.findElement(By.xpath(".//div[1]/a"));

        return text.equals(elementCopyRoles.getText().trim());
    }

    /**
     * Looks into table, and searches in first element of user list, and returns "Copy roles" link.
     *
     * @param text
     * @return returns first found link "Copy roles", if available
     */
    public WebElement getLinkCopyRoles(String text) {

        if (this.listUser.size() <= 0) {
            return null;
        }

        WebElement element = this.listUser.get(0);

        WebElement elementCopyRoles = element.findElement(By.xpath(".//div[1]/a"));

        if (text.equals(elementCopyRoles.getText().trim())) {
            return elementCopyRoles;
        } else {
            return null;
        }

    }

    /**
     * Looks into table, and searches in first element of user list, if "Edit roles" link
     * is available.
     *
     * @param text
     * @return
     */
    public boolean isLinkEditRolesAvailable(String text) {

        if (this.listUser.size() <= 0) {
            return false;
        }

        WebElement element = this.listUser.get(0);

        WebElement elementEditRoles = element.findElement(By.xpath(".//div[2]/a"));

        return text.equals(elementEditRoles.getText().trim());
    }

    /**
     * Looks into table, and searches in first element of user list, and returns "Edit roles" link.
     *
     * @param text
     * @return returns first found link "Edit roles", if available
     */
    public WebElement getLinkEditRoles(String text) {

        if (this.listUser.size() <= 0) {
            return null;
        }

        WebElement element = this.listUser.get(0);

        WebElement elementEditRoles = element.findElement(By.xpath(".//div[2]/a"));

        if (text.equals(elementEditRoles.getText().trim())) {
            return elementEditRoles;
        } else {
            return null;
        }

    }


    public void selectUserById(String user) {

        try {
            Thread.sleep(2000); //
        } catch (InterruptedException ex) {
            Logger.getLogger(UserOverview.class.getName()).log(Level.SEVERE, null, ex);
        }

        ReusableMethods.clickElement(getInputUserID(), 10, _driver);

        this.getInputUserIDSearch().sendKeys(user);


        try {
            this.clickUserEntry(user);

            getWebdriverWait.until(
                    ExpectedConditions.textToBePresentInElement(
                            this.getNumberEntries(),
                            ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS));
            //edit
            this.getEditLink().click();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Intention of this method is to select one user, and display it's
     * attributes in table without further actions.
     *
     * @param user
     */
    public void displayUserById(String user) {

        ReusableMethods.clickElement(getInputUserID(), 5, _driver);

        ReusableMethods.clearField(getInputUserIDSearch());
        this.getInputUserIDSearch().sendKeys(user);

        try {
            ReusableMethods.clickElement(getUserEntry(user), 3, _driver);
        } catch (Exception e) {
            Logger.getLogger(UserOverview.class.getName()).log(Level.SEVERE, null, e);
        }

    }


    public void deselectAllRoles() {
        //Schleife bauen

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserOverview.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (WebElement onupRoleBox : selectedOnupRoleBox) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }

            onupRoleBox.click();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserOverview.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public void selectRole(String role) {

        int index = -1;

        for (int i = 0; i < this.getListRoles().size(); i++) {

                if (this.getListRoles().get(i).getText().trim().equals(role)) {
                this.getBoxList().get(i).click();
                index = i;
                break;
            }

        }

        //Waiting period as roles are saved automatically in background
        if (index < 0) {
            throw new RuntimeException("Role '" + role + "' not found");
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(UserOverview.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * @param idRole
     * @return
     */
    private String sanitiseRole(String idRole) {

        idRole = idRole.replace("AUDI", ""); // replaces suffix in GUI element ID
        idRole = idRole.replace("VW", ""); // replaces suffix in GUI element ID
        idRole = idRole.replace("PORSCHE", ""); // replaces suffix in GUI element ID

        return idRole;

    }

    public void selectAllRolesExcept(String ...rightsException) {

        for (int i = 0; i < this.boxList.size(); i++) {

            WebElement element = this.boxList.get(i);

            String idRole = element.getAttribute("id");

            idRole = this.sanitiseRole(idRole);

            if ((! element.isSelected()) &&
                    (! this.checkEquality(idRole, rightsException))) {
                element.click();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    private boolean checkEquality(String idRole, String[] rightsException) {

        for (String right : rightsException) {

            if (idRole.equals(right)) {
                return true;
            }

        }

        return false;

    }

    public WebElement getCloseModal(){
        return this.closeModal;
    }

    public void closeModal(){
        this.closeModal.click();
    }

    /**
     * This method copies roles from first entry in user list, to specified/given
     * target user.
     *
     * @param targetUser
     */
    public void copyRole(String targetUser) {

    }

    public String getOnupRolesUser() {

        WebElement firstUser = this.listUser.get(0);

        WebElement onupCellOfRow = firstUser.findElement(By.xpath("//td[5]"));

        return onupCellOfRow.getText();
    }

    public void clearFieldUserId(WebDriver webDriver, String user) throws Exception {

        WebElement element = webDriver.findElement(By.xpath("//div[contains(text(), '" + user + "')]"));
        element.click();

        ReusableMethods.clearField(getInputUserIDSearch());
        // double click on checkbox, in order to get all user selected
        this.clickUserEntry(user);

    }

    /**
     * If a user is searched by UserID search field, it will stay as opened drop down,
     * after putting in search name and ticking corresponding checkbox.
     * This method is intended for closing this drop down.
     *
//     * @param webDriver //
//     * @param user //
     */
    public void closeUserIdDropDownInputField() {

        if (this.closeUserIdSearchDropDownInputField != null )
//                || this.closeUserIdSearchDropDownInputField.isDisplayed())
        {
            this.closeUserIdSearchDropDownInputField.click();
        }
    }

    public String getLdapRolesUser() {

        WebElement firstUser = this.listUser.get(0);

        WebElement ldapCellOfRow = firstUser.findElement(By.xpath("//td[4]"));

        return ldapCellOfRow.getText();
    }

    /*
     * search field user name
     */

    /**
     * @return the inputUserName
     */
    public WebElement getInputUserName() {
        return inputUserName;
    }

    /**
     * @return the inputUserNameSearchTextBox
     */
    public WebElement getInputUserNameSearchTextBox() {
        return inputUserNameSearchTextBox;
    }

    public WebElement getUserNameEntry(String userName) throws Exception {
        return this.getSelectionByText(userName);
    }

    public void clickUserNameEntry(String userName) throws Exception {
        this.getUserNameEntry(userName).click();
    }

    /**
     * @return the closeUserNameSearchDropDownInputField
     */
    public WebElement getCloseUserNameSearchDropDownInputField() {
        return closeUserNameSearchDropDownInputField;
    }

    public void clickCloseUserNameSearchDropDownInputField() {
        this.getCloseUserNameSearchDropDownInputField().click();
    }

    /**
     * Intention of this method is to select one user, and display it's
     * attributes in table without further actions.
     *
     * @param name
     */
    public void displayUserByName(String name) {

        ReusableMethods.clickElement(getInputUserName(), 5, _driver);

        ReusableMethods.waitForClickablility(getInputUserNameSearchTextBox(), 2, _driver);
        this.getInputUserNameSearchTextBox().clear();

        this.getInputUserNameSearchTextBox().sendKeys(name);

        try {
            ReusableMethods.clickElement(getUserNameEntry(name), 3, _driver);
            this.getNumberEntries();
        } catch (Exception e) {
            Logger.getLogger(UserOverview.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * @return the inputLdapRole
     */
    public WebElement getInputLdapRole() {
        return inputLdapRole;
    }

    /**
     * @return the inputLdapRoleSearchTextBox
     */
    public WebElement getInputLdapRoleSearchTextBox() {
        return inputLdapRoleSearchTextBox;
    }

    public WebElement getLdapRoleEntry(String roleLdap) throws Exception {
        return this.getSelectionByText(roleLdap);
    }

    public void clickLdapRoleEntry(String roleLdap) throws Exception {
        this.getLdapRoleEntry(roleLdap).click();
    }

    /**
     * @return the closeLdapRoleSearchDropDownInputField
     */
    public WebElement getCloseLdapRoleSearchDropDownInputField() {
        return closeLdapRoleSearchDropDownInputField;
    }

    public void clickCloseLdapRoleSearchDropDownInputField() {
        this.getCloseLdapRoleSearchDropDownInputField().click();
    }

    /**
     * Intention of this method is to select one LDAP Role, and display it's
     * attributes in table without further actions.
     *
     * @param ldpaRole
     */
    public void displayUserByLdapRole(String ldpaRole) {

        ReusableMethods.clickElement(getInputLdapRole(), 5, _driver);

        ReusableMethods.waitForClickablility(getInputLdapRoleSearchTextBox(), 2, _driver);
        this.getInputLdapRoleSearchTextBox().clear();
        this.getInputLdapRoleSearchTextBox().sendKeys(ldpaRole);

        try {
            ReusableMethods.clickElement(getLdapRoleEntry(ldpaRole), 3, _driver);
            this.getNumberEntries();

        } catch (Exception e) {
            Logger.getLogger(UserOverview.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * @return the inputOnupRole
     */
    public WebElement getInputOnupRole() {
        return inputOnupRole;
    }

    /**
     * @return the inputOnupRoleSearchTextBox
     */
    public WebElement getInputOnupRoleSearchTextBox() {
        return inputOnupRoleSearchTextBox;
    }

    public WebElement getOnupRoleEntry(String roleOnup) throws Exception {
        return this.getSelectionByText(roleOnup);
    }

    public void clickOnupRoleEntry(String roleLdap) throws Exception {
        this.getLdapRoleEntry(roleLdap).click();
    }

    /**
     * @return the closeOnupRoleSearchDropDownInputField
     */
    public WebElement getCloseOnupRoleSearchDropDownInputField() {
        return closeOnupRoleSearchDropDownInputField;
    }

    public void clickCloseOnupRoleSearchDropDownInputField() {
        this.getCloseOnupRoleSearchDropDownInputField().click();
    }

    /**
     * Intention of this method is to select one ONUP Role, and display it's
     * attributes in table without further actions.
     *
     * @param role
     */
    public void displayUserByOnupRole(String role) {

        ReusableMethods.clickElement(getInputOnupRole(), 5, _driver);

        ReusableMethods.waitForClickablility(getInputOnupRoleSearchTextBox(), 2, _driver);
        this.getInputOnupRoleSearchTextBox().clear();
        this.getInputOnupRoleSearchTextBox().sendKeys(role);

        try {
            ReusableMethods.clickElement(getOnupRoleEntry(role), 3, _driver);
            this.getNumberEntries();
        } catch (Exception e) {
            Logger.getLogger(UserOverview.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public WebElement getSelectionByText(String text) {

        List<WebElement> found = this.webDriver.findElements(
                By.xpath(
                        "//div/div/ul/p-multiselectitem/li/span[contains(text(), '" + text + "')]"));

      WebElement we = null;

      for (WebElement item : found) {

          if (item.getText().trim().equals(text)) {

              we = item;
              break;

          }

      }

      return we;

    }



    public void saveClick(WebElement element) {
        _driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        int helper = 1;
        for (int count = 0; count < 10; count++) {

            if (null == this.getOverlay()) {
                helper = this.getOverlay().size();
            } else {
                helper = 0;
            }

            if (helper == 0) {
                count = 10;
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PageWithNavigation.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("Overlay found or data not completely loaded");
            }
        }

        element.click();
        _driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }


}
