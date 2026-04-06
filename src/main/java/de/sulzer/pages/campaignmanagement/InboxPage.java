package de.sulzer.pages.campaignmanagement;

import de.sulzer.pages.genericelements.PageWithNavigation;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InboxPage extends PageWithNavigation {

    private WebDriver driver;

    public InboxPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @FindBy(xpath = "//span[.='Create ORU Campaign']")
    private WebElement tabCreateOruCampaign;

    @FindBy(xpath = "//span[.='Existing Campaign']")
    private WebElement tabExistingCampaign;

    @FindBy(xpath = "//span[.='Archived Campaign']")
    private WebElement tabArchivedCampaign;

    @FindBy(xpath = "(//h4[contains(text(),'ReCall Campaign')])[1]")
    private WebElement titleDefaultRecallTable; // default view, when clicked on 'Inbox' in menu

    @FindBy(xpath = "(//input[@id='fltr-tree-fltr'])[1]")
    private WebElement inboxRecallSearchField;

    /*
     * Tab ORU Campaign
     */

    @FindBy(xpath = "(//ul[@role = 'group'])[1]/li[1]")
    private WebElement recallElement;

    // first list item of recall table/list
    @FindBy(xpath = "(//ul[@role = 'group'])[1]/li[1]")
    private WebElement recallTableFirstListItem;

    // checkbox of first list item of above declared list item
    @FindBy(xpath = "(//ul[@role = 'group'])[1]/li[1]/a")
    private WebElement recallTableListFirstItemCheckBox;

    // info icon of first list item of above declared list item
    @FindBy(xpath = "(//ul[@role = 'group'])[1]/li[1]/i[2]")
    private WebElement recallTableListFirstInfoIcon;

    // all list items of ReCall
    @FindAll(
            @FindBy(xpath = "(//ul[@role = 'group'])[1]/li")
    )
    private List<WebElement> recallTableList;

    // recall root checkbox
    @FindBy(xpath = "//table[@id='tbl-create-oru-camp-recall']//*[@class='jstree-icon jstree-checkbox']")
    private WebElement recallElementRoot;


    // SERVICE 42
    @FindBy(xpath = "(//input[@id='fltr-tree-fltr'])[2]")
    private WebElement service42SearchField;

    @FindBy(xpath = "(//ul[@role = 'group'])[2]/li[1]")
    private WebElement service42FirstElement;

    @FindBy(id = "btn-create-oru-camp-create")
    private WebElement createCampaignButton;

    @FindBy(xpath = "//h4[contains(text(),'service42 Amendment')]")
    private WebElement titleDefaultService42Table; // default view, when clicked on 'Inbox' in menu

    @FindBy(xpath = "(//*[@class='jstree-container-ul jstree-children'])[2]")
    private WebElement service42Table; // right side of site

    @FindBy(xpath = "(//tree4[@id='comp-create-oru-camp-s42-tree']//a[contains(@id, '.parent_anchor')])[1]")
    private WebElement service42TableFirstListItem; // first list item of above declared table/list

    @FindBy(xpath = "//tree4[@id='comp-create-oru-camp-s42-tree']//a[contains(@id, '.parent_anchor')]")
    private List<WebElement> service42TableListItem;

//    @FindBy(xpath = "(//ul[@role = 'group'])[3]/li[1]/a")
    @FindBy(xpath = "(//tree4[@id='comp-create-oru-camp-s42-tree']//a[contains(@id, '.parent_anchor')])[1]/i[1]")
    private WebElement service42TableFirstListItemCheckBox; // checkbox of first list item of above declared list item



    /*
     * Tab Existing ORU Campaign
     */

    @FindBy(xpath = "(//tree4[@id='comp-existing-camp-recall-tree']//i[@class='jstree-icon jstree-checkbox'])[1]")
    private WebElement existingCampaignListItemCheckBox; //Check box of first list item of recall campaign in existing campaign tab

    // info icon of first list item of above declared list item
    @FindBy(xpath = "(//input[@id='fltr-tree-fltr'])[3]")
    private WebElement recallExistingCampaignSearchField;

    // first list item of recall table/list
    @FindBy(xpath = "(//ul[@role = 'group'])[3]/li[1]")
    private WebElement firstReCallExistingTableListItem;

    // complete list of existing ORU Campaigns
    @FindAll(
            @FindBy(xpath = "(//ul[@role = 'group'])[3]/li")
    )
    private List<WebElement> listExistingOruCampaigns;

    /*
     * Tab Archived ORU Campaign
     */

    @FindBy(xpath = "(//input[@id='fltr-tree-fltr'])[4]")
    private WebElement recallArchivedCampaignSearchField;

    // first list item of recall table/list
    @FindBy(xpath = "//tbody[@id='archivedRecallTBody']//div[contains(@class, 'jstree-default jstree-checkbox-selection')]//ul[@class='jstree-container-ul jstree-children']//li[1]")
    private WebElement firstReCallArchivedTableListItem;

    // complete list of archived ORU Campaigns
    @FindAll(
            @FindBy(xpath = "(//ul[@role = 'group'])[4]/li")
    )
    private List<WebElement> listArchivedOruCampaigns;

    @FindBy(xpath = "(//ul[@role = 'group'])[3]")
    private List<WebElement> archivedCampaignRecallTableList;

    @FindBy(xpath = "//*[@title='Shows service42Ams older than 100 days']")
    private WebElement olderThan100DaysSwitch;

    @FindBy(xpath = "(//input[@id='fltr-tree-fltr'])[4]")
    private WebElement archiveCampaignSearch;

    /*
     * else elements
     */

    @FindBy(className = "panel-title")
    private WebElement panelTitle;

    @FindBy(xpath = "((//table[@id='tbl-create-oru-camp-recall']//*[@class='jstree-container-ul jstree-children'])/li/a)[1]")
    private WebElement searchedRecallText; // Searched recall text includes campaign name and campaign title(Ex:IS01 - IS01)

    @FindBy(xpath = "(//tree4[@id='comp-create-oru-camp-s42-tree']//a[contains(@id, '.parent_anchor')])[1]")
    private WebElement searchedS42FirstChildText;

    @FindBy(xpath = "((//table[@id='tbl-create-oru-camp-recall']//*[@class='jstree-container-ul jstree-children'])/li/a)[2]")
    private WebElement searchedRecallSecondChildText; // Searched recall Second child text includes criterion id and criterion text

    /*
     * display of recall/service42 which are manually or automatically created
     */

    @FindAll({
            @FindBy(xpath = "(//table[@id='tbl-create-oru-camp-recall']//*[@class='jstree-container-ul jstree-children'])/li/a")
    })
    private List<WebElement> listCreatedEntriesRecall;

    @FindAll({
            @FindBy(xpath = "//table[@id='tbl-create-oru-camp-recall']//i[@class='fa fa-user']")
    })
    private List<WebElement> listCreatedEntriesRecallManually;

    @FindAll({
            @FindBy(xpath = "//table[@id='tbl-create-oru-camp-recall']//i[@class='fa fa-cogs']")
    })
    private List<WebElement> listCreatedEntriesRecallAutomatically;

    @FindAll({
            @FindBy(xpath = "//tree4[@id='comp-create-oru-camp-s42-tree']//i[@class='fa fa-user']")
    })
    private List<WebElement> listCreatedEntriesService42Manually;

    @FindAll({
            @FindBy(xpath = "//tree4[@id='comp-create-oru-camp-s42-tree']//i[@class='fa fa-cogs']")
    })
    private List<WebElement> listCreatedEntriesService42Automatically;

    @FindBy(xpath = "//h4[@class='inboxHeaders']//span")
    private WebElement existingCampaignDetailsHeader;

    @FindBy(xpath = "//*[.='ReCall Campaigns & Criteria ']")
    private WebElement detailsRecallExistingCampaignAndCriteria;

    @FindBy(xpath = "(//ul[@role='group'])[4]")
    private WebElement recallCriteria;

    @FindBy(xpath = "//th[contains(text(),'Used service42 AM / Flash File')]")
    private WebElement detailsS42andFlashFile;

    @FindBy(xpath = "//tr//td[@class='overflow-ellipsis'][2]")
    private WebElement flashFile;

    @FindBy(xpath = "//div[@class='col-lg-12']//h5//a")
    private WebElement existingCampaignHyperlink;

    public WebElement getTabCreateOruCampaign() {
        return tabCreateOruCampaign;
    }

    public WebElement getSearchedS42FirstChildText() {
        return this.searchedS42FirstChildText;
    }

    public String textOfSearchedRecallFirstChild() {
        return this.getRecallTableFirstListItem().getText();
    }

    public String textOfSearchedS42FirstChild() {
        return this.getSearchedS42FirstChildText().getText();
    }

    public WebElement getSearchedRecallSecondChildText() {
        return this.searchedRecallSecondChildText;
    }

    public String textOfSearchedRecallSecondChild() {
        return this.getSearchedRecallSecondChildText().getText();
    }

    public WebElement getSearchedRecallText() {
        return this.searchedRecallText;
    }

    public String textOfSearchedRecall() {
        return this.getSearchedRecallText().getText();
    }

    public List<WebElement> getService42TableListItem() {
        return service42TableListItem;
    }

    public int getService42TableListItemSize() {
        return this.service42TableListItem.size();
    }

    public WebElement getPanelTitle() {
        return this.panelTitle;
    }

    public String getPanelTitleText() {
        return this.getPanelTitle().getText();
    }

    public WebElement getTabExistingCampaign() {
        return this.tabExistingCampaign;
    }

    public WebElement getTabArchivedCampaign() {
        return this.tabArchivedCampaign;
    }

    public void clickTabArchivedCampaign() {
        this.tabArchivedCampaign.click();
    }

    public void existingRecallCampaignSearchInput(String existingCampaign) {
        ReusableMethods.waitForClickablility(recallExistingCampaignSearchField, 40, driver);
        this.recallExistingCampaignSearchField.sendKeys(existingCampaign);
    }

    public String getExistingRecallElementText() {
        return this.getFirstReCallExistingTableListItem().getText();
    }

    public WebElement getExistingCampaignDetailsHeader() {
        return this.existingCampaignDetailsHeader;
    }

    public String getExistingCampainDetailsHeaderText() {
        return this.getExistingCampaignDetailsHeader().getText();
    }

    public WebElement getDetailsRecallExistingCampaignAndCriteria() {
        return this.detailsRecallExistingCampaignAndCriteria;
    }

    public String detailsRecallCampaignAndCriteriaField() {
        return this.getDetailsRecallExistingCampaignAndCriteria().getText();
    }

    public WebElement getRecallCriteria() {
        return this.recallCriteria;
    }

    public String RecallCriteria() {
        return this.getRecallCriteria().getText();
    }

    public WebElement getDetailsS42andFlashFile() {
        return this.detailsS42andFlashFile;
    }

    public String detailsS42andFlashFileField() {
        return this.getDetailsS42andFlashFile().getText();
    }

    public WebElement getflashFile() {
        return this.flashFile;
    }

    public String getFlashFileName() {
        return this.getflashFile().getText();
    }

    public WebElement getExistingCampaignHyperlinkInDetails() {
        return this.existingCampaignHyperlink;
    }

    public void clickExistingCampaignHyperlinkInDetails() {
        this.getExistingCampaignHyperlinkInDetails().click();
    }

    public WebElement getTitleDefaultRecallTable() {
        return this.titleDefaultRecallTable;
    }

    public void clickTabExistingCampaign() {
        this.tabExistingCampaign.click();
    }

    public WebElement getExistingCampaignListItemCheckBox() {
        return this.existingCampaignListItemCheckBox;
    }

    public void clickExistingCampaignListItemCheckBox() {
        this.existingCampaignListItemCheckBox.click();
    }

    public WebElement getCreateCampaignButton() {
        return createCampaignButton;
    }

    public void clickCreateCampaignButton() {
        this.getCreateCampaignButton().click();
    }

    public WebElement getService42FirstElement() {
        return service42FirstElement;
    }

    public void clickService42Element() {
        this.getService42FirstElement().click();
    }

    public WebElement getService42SearchField() {
        return service42SearchField;
    }

    //Search must select description of am code (parent element) or am ID (child element)
    public void setService42SearchField(String input) {
//		this.getService42SearchField().clear();
        this.getService42SearchField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        this.getService42SearchField().sendKeys(input);
    }

    public WebElement getInboxRecallSearchField() {
        return inboxRecallSearchField;
    }

    //Search must select description of recall (parent element) or criterion title (child element)
    public void setInboxRecallSearchField(String input) {

        this.getInboxRecallSearchField().sendKeys(input);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkInboxRecall(WebDriver driver, String Recall_ID) {
        driver.findElement(By.xpath("//*[@id='recall-tree-1']//div[contains(@class,'jstree-checkbox-selection')]//a[@id='" + Recall_ID + ".parent_anchor']//i[@class='jstree-icon jstree-checkbox']")).click();
    }

    public WebElement getRecallElement() {
        return recallElement;
    }

    public void clickRecallElement() {
        this.getRecallElement().click();
    }

    public WebElement getRecallElementRoot() {
        return recallElementRoot;
    }

    public void clickRecallElementRoot() {
        this.getRecallElementRoot().click();
    }

    /**
     * @return first ListItem of given table (intended for getting created Recall element)
     */
    public WebElement getRecallTableFirstListItem() {
        return this.recallTableFirstListItem;
    }

    public void clickRecallTableListItem() {
        this.getRecallTableFirstListItem().click();
    }

    /**
     * @return checkbox of first ListItem of given table (intended for getting created recall element)
     */
    public WebElement getRecallTableListFirstItemCheckBox() {
        return this.recallTableListFirstItemCheckBox;
    }

    public void clickRecallTableListFirstItemCheckBox() {
        this.getRecallTableListFirstItemCheckBox().click();
    }

    public void clickRecallTableListItemCheckBox(String recall_id, WebDriver webDriver) throws Exception {

        Thread.sleep(1000);
        boolean isElementPresent = getRecallTableListFirstItemCheckBox().getAttribute("id").contains(recall_id);

        // because of Bug OU-3913
//        Thread.sleep(2000);

        if (isElementPresent) {
            getRecallTableListFirstItemCheckBox().click();
        } else {
            throw new Exception("Recall element with ID " + recall_id + " not found.");
        }
    }

    /**
     * @return the recallTableListFirstInfoIcon
     */
    public WebElement getRecallTableListFirstInfoIcon() {
        return recallTableListFirstInfoIcon;
    }

    public void clickRecallTableListFirstInfoIcon() {
        this.getRecallTableListFirstInfoIcon().click();
    }

    public void clickRecallTableListItemInfoIcon(String recall_id, WebDriver webDriver) throws Exception {

        WebElement element = webDriver.findElement(By.xpath("//tbody[@id='recallTBody']//span[contains(text(), '" + recall_id + "')]"));

        // because of Bug OU-3913
        Thread.sleep(2000);

        if (null != element &&
                element.getText().trim().startsWith(recall_id)) {
            webDriver.findElement(By.xpath("//tbody[@id='recallTBody']//a[contains(@id, '" + recall_id + "')]/..//i[contains(@class, 'fa-info-circle')]")).click();
            return;
        }

        throw new Exception("Recall element with ID " + recall_id + " not found.");
    }

    public List<WebElement> getRecallTableList() {
        return this.recallTableList;
    }

    // TODO:
    public void searchRecall(String input) {

        WebDriverWait wait = new WebDriverWait(driver, 180);
        wait.until(ExpectedConditions.elementToBeClickable(this.getInboxRecallSearchField()));

        this.getInboxRecallSearchField().clear();
        this.getInboxRecallSearchField().sendKeys(Keys.ENTER);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        wait.until(ExpectedConditions.elementToBeClickable(this.getInboxRecallSearchField()));

        this.getInboxRecallSearchField().sendKeys(input);
        this.getInboxRecallSearchField().sendKeys(Keys.ENTER);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(InboxPage.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public WebElement getTitleDefaultService42Table() {
        return this.titleDefaultService42Table;
    }

    public WebElement getService42Table() {
        return this.service42Table;
    }

    /**
     * @return first ListItem of given table (intended for getting created AM_CODE element)
     */
    public WebElement getService42TableFirstListItem() {
        return this.service42TableFirstListItem;
    }

    public void clickService42TableListItem() {
        this.service42TableFirstListItem.click();
    }

    /**
     * @return checkbox of first ListItem of given table (intended for getting created AM_CODE element)
     */
    public WebElement getService42TableFirstListItemCheckBox() {
        return this.service42TableFirstListItemCheckBox;
    }

    public void clickService42TableListItemCheckBox() {
        this.getService42TableFirstListItemCheckBox().click();
    }


    public void clickService42TableListItemCheckBox(String am_code, WebDriver webDriver) throws Exception {

        getService42SearchField().click();
        getService42SearchField().sendKeys(Keys.ENTER);
        Thread.sleep(2000);
        ReusableMethods.waitForNumberOfElementsToBeOne(By.xpath("//tree4[@id='comp-create-oru-camp-s42-tree']//a[contains(@id, '.parent_anchor')]"), 10, driver);


        boolean isElementPresent = getService42TableFirstListItem().getAttribute("id").contains(am_code);

        if (isElementPresent) {
            ReusableMethods.clickJS(getService42TableFirstListItemCheckBox(), webDriver);
        } else {
            throw new Exception("Recall element with ID " + am_code + " not found.");
        }
    }


    public void searchService42(String input) {

        clickOlderThan100DaysSwitch();
        ReusableMethods.waitForClickablility(getService42SearchField(), 25, driver);
        getService42SearchField().sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        getService42SearchField().sendKeys(input);

        try {
            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("//span[.='" + input + "']")), 25, _driver);
            System.out.println("Service42 AM " + input + " found.");
        } catch (Exception e) {
            System.out.println("This Service42 AM " + input + " does not exist.");
        }


    }

    /**
     * @return the listCreatedEntriesRecall
     */
    public List<WebElement> getListCreatedEntriesRecall() {
        return this.listCreatedEntriesRecall;
    }

    /**
     * @return the listCreatedEntriesRecallManually
     */
    public List<WebElement> getListCreatedEntriesRecallManually() {
        return this.listCreatedEntriesRecallManually;
    }

    /**
     * @return the listCreatedEntriesRecallAutomatically
     */
    public List<WebElement> getListCreatedEntriesRecallAutomatically() {
        return this.listCreatedEntriesRecallAutomatically;
    }

    /**
     * @return the listCreatedEntriesService42Manually
     */
    public List<WebElement> getListCreatedEntriesService42Manually() {
        return this.listCreatedEntriesService42Manually;
    }

    /**
     * @return the listCreatedEntriesService42Automatically
     */
    public List<WebElement> getListCreatedEntriesService42Automatically() {
        return this.listCreatedEntriesService42Automatically;
    }

    /**
     * @param recall_id
     * @return true if element found, false otherwise
     */
    public boolean isRecallElementPresent(String recall_id, WebDriver webDriver) {

        try {
            ReusableMethods.waitForClickablility(webDriver.findElement(By.xpath("//*[@id='" + recall_id + ".parent_anchor']")), 30, webDriver);
            return true;
        } catch (Exception e) {
            return false;
        }

    }


    /**
     * @param service42_am_code
     * @return true if element found, false otherwise
     */
    public boolean isService42ElementPresent(String service42_am_code, WebDriver webDriver) {

        WebElement element = webDriver.findElement(By.xpath("//tbody[@id='service42TBody']//a[contains(@id, '" + service42_am_code + "')]/span"));

        return null != element;
    }

    public boolean isArchivedCampaignPresent(String CAMPAIGN_ID, WebDriver driver) {


        try {
            driver.findElement(By.xpath("//*[@id='" + CAMPAIGN_ID + ".parent_anchor']/span"));
//            driver.findElement(By.xpath("//*[@id='2']//following::li[@id='" + archivedCampaign + ".parent']"));
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public WebElement getOlderThan100DaysSwitch() {
        return this.olderThan100DaysSwitch;
    }

    public void clickOlderThan100DaysSwitch() {
        this.getOlderThan100DaysSwitch().click();
    }

    public WebElement getRecallExistingCampaignSearchField() {
        return recallExistingCampaignSearchField;
    }

    public void searchRecallExistingCampaign(String input) {

        ReusableMethods.waitForClickablility(getRecallExistingCampaignSearchField(), 30, driver);
        this.getRecallExistingCampaignSearchField().clear();
        this.getRecallExistingCampaignSearchField().sendKeys(Keys.ENTER);

        this.getRecallExistingCampaignSearchField().sendKeys(input);
        this.getRecallExistingCampaignSearchField().sendKeys(Keys.ENTER);

    }


    /**
     * @return the firstReCallExistingTableListItem
     */
    public WebElement getFirstReCallExistingTableListItem() {
        return firstReCallExistingTableListItem;
    }

    /**
     * @return the listExistingOruCampaigns
     */
    public List<WebElement> getListExistingOruCampaigns() {
        return listExistingOruCampaigns;
    }

    public WebElement getRecallArchivedCampaignSearchField() {
        return recallArchivedCampaignSearchField;
    }

    /**
     * @return the firstReCallArchivedTableListItem
     */
    public WebElement getFirstReCallArchivedTableListItem() {
        return firstReCallArchivedTableListItem;
    }

    /**
     * @return the listArchivedOruCampaigns
     */
    public List<WebElement> getListArchivedOruCampaigns() {
        return listArchivedOruCampaigns;
    }

    public void openReCallForCriteriaByReCallId(String reCallId) {

        String xpathReCallElement =
                "//table[@id='inbox-recall-table']/tbody[@id='recallTBody']//li[@id='" + reCallId + ".parent']/i[1]";

        this.clickOpenReCallTree(xpathReCallElement);

    }

    public void openReCallExistingForCriteriaByReCallId(String reCallId) {

        String xpathReCallElement =
                "//table[@id='inbox-recall-table']/tbody[@id='exisitingRecallTBody']//li[@id='" + reCallId + ".parent']/i[1]";

        this.clickOpenReCallTree(xpathReCallElement);

    }

    private void clickOpenReCallTree(String xpathReCallElement) {

        WebElement element = this.driver.findElement(
                By.xpath(xpathReCallElement));

        element.click();

    }

    public int amountCriteriaByReCallId(String reCallId) {

        List<WebElement> elements = this.driver.findElements(
                By.xpath(
                        "//li[@id='" + reCallId + ".parent']/ul/li"));

        return elements.size();

    }

}