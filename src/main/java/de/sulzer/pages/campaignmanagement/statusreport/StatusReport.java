/**
 *
 */
package de.sulzer.pages.campaignmanagement.statusreport;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class StatusReport extends Page {

    private WebDriver webDriver;

    /**
     * @param driver
     */
    public StatusReport(WebDriver driver) {

        super(driver);
        this.webDriver = driver;
    }

//    @FindBy(xpath="//p[@class='status-report-header-name']")
//    @FindBy(xpath = "//*[@id='p-panel-32']/div/h3")
    @FindBy(xpath = "//*[.=' Status report processing ']")
    private WebElement pageTitle;

//    @FindBy(xpath="//p[@class='campaign-active-info']")
    @FindBy(xpath = "(//*[.=' Campaign Status: ACTIVE '])[2]")
    private WebElement pageTitleRightSide;

    @FindBy(xpath="//span[contains(text(),'Campaign ID:')]")
    private WebElement labelCampaignId;

//    @FindBy(xpath="//span[@ng-reflect-text='Only campaigns that have the s']")
    @FindBy(xpath = "//div/status-report-table/div[1]/span[2]")
    private WebElement iconInfoCampaignActive;

    @FindBy(css = "div select#campaign-numbers")
    private WebElement dropDownCampaign;

    @FindBy(xpath="//label[@class='file-selection-checkbox']")
    private WebElement labelCheckBoxSelectAllDisplayedFiles;

    @FindBy(xpath="//label[@class='file-selection-checkbox']//input[@type='checkbox']")
    private WebElement checkBoxSelectAllDisplayedFiles;

    @FindBy(xpath="//table/thead/tr/th[1]")
    private WebElement tableHeaderSelectFiles;

    @FindBy(xpath="//table/thead/tr/th[2]")
    private WebElement tableHeaderStatusReports;

    @FindBy(xpath="//table/thead/tr/th[3]")
    private WebElement tableHeaderButtonDownloadFile;

    @FindAll({
            @FindBy(xpath = "//table/tbody/tr/td[3]")
    })
    private List<WebElement> tableButtonDownloadFile;

    @FindBy(css = "tbody.p-element > tr:nth-child(1) > td:nth-child(3) > button:nth-child(1)")
    private WebElement firstTableButtonDownloadFile;

    @FindAll(
            @FindBy(xpath="//status-report-table//tbody/tr")
    )
    private List<WebElement> tableRowsStatusReports;

    @FindAll(
        @FindBy(xpath="//status-report-table//tbody/tr/td[2]")
    )
    private List<WebElement> tableStatusReports;

    @FindBy(xpath="//button[contains(text(),'Create File')]")
    private WebElement buttonFileCreate;

    @FindBy(xpath="//button[contains(text(),'Delete File')]")
    private WebElement buttonFileDelete;

//    @FindBy(xpath="//div[contains(@class, 'ui-dropdown-trigger')]/span[contains(@class, 'ui-dropdown-trigger-icon')]")
    @FindBy(xpath="//div[contains(@class, 'p-dropdown-trigger')]/span[contains(@class, 'p-dropdown-trigger-icon')]")
    private WebElement dropDownPagination;

//    @FindBy(xpath="//span[contains(@class, 'pi pi-step-backward')]")
    @FindBy(xpath = "//span[contains(@class, 'p-paginator-icon pi pi-angle-double-left')]")
    private WebElement buttonPaginatonLeftFirst;

    @FindBy(xpath="//span[contains(@class, 'pi pi-caret-left')]")
    private WebElement buttonPaginatonLeft;

    @FindBy(xpath="//span[contains(@class, 'pi pi-step-forward')]")
    private WebElement buttonPaginatonRightLast;

//    @FindBy(xpath="//span[contains(@class, 'pi pi-caret-right')]")
    @FindBy(xpath = "//span[contains(@class, 'p-paginator-icon pi pi-angle-right')]")
    private WebElement buttonPaginatonRight;

    @FindBy(xpath="//div/number-of-entries")
    private WebElement numberOfEntriesTable;

    @FindBy(xpath = "//button[.='Ok']")
    private WebElement notAuthorisedOkButton;

    public WebElement getNotAuthorisedOkButton() {
        return notAuthorisedOkButton;
    }


    /**
     * @return the pageTitle
     */
    public WebElement getPageTitle() {
        return pageTitle;
    }

    /**
     * @return the pageTitleRightSide
     */
    public WebElement getPageTitleRightSide() {
        return pageTitleRightSide;
    }

    /**
     * @return the labelCampaignId
     */
    public WebElement getLabelCampaignId() {
        return labelCampaignId;
    }

    /**
     * @return the iconInfoCampaignActive
     */
    public WebElement getIconInfoCampaignActive() {
        return iconInfoCampaignActive;
    }

    /**
     * @return the dropDownCampaign
     */
    public WebElement getDropDownCampaign() {
        return dropDownCampaign;
    }

    public List<WebElement> getTableButtonDownloadFile() {
        return tableButtonDownloadFile;
    }

    public void clickDropDownCampaign() {
        this.getDropDownCampaign().click();
    }

    public List<WebElement> getDropDownCampaignOptions() {

        Select selectableCampaigns = new Select(this.getDropDownCampaign());

        return selectableCampaigns.getOptions();

    }

    public WebElement getDropDownCampaignSelection() {

        Select selectableCampaigns = new Select(this.getDropDownCampaign());

        return selectableCampaigns.getFirstSelectedOption();

    }

    public void selectCampaignById(String campaignId) {

        Select selectorCampaignId = new Select(this.getDropDownCampaign());

        selectorCampaignId.selectByVisibleText(campaignId);

    }

    public void selectCampaignByIndex(int index) {

        Select selectorCampaignId = new Select(this.getDropDownCampaign());
        selectorCampaignId.selectByIndex(index);

    }

    /**
     * @return the checkBoxSelectAllDisplayedFiles
     */
    public WebElement getCheckBoxSelectAllDisplayedFiles() {
        return checkBoxSelectAllDisplayedFiles;
    }

    public void clickCheckBoxSelectAllDisplayedFiles() {
        this.getCheckBoxSelectAllDisplayedFiles().click();
    }

    public boolean isCheckBoxSelectAllDisplayedFilesChecked() {
        return this.getCheckBoxSelectAllDisplayedFiles().isSelected();
    }

    /**
     * @return the labelCheckBoxSelectAllDisplayedFiles
     */
    public WebElement getLabelCheckBoxSelectAllDisplayedFiles() {
        return labelCheckBoxSelectAllDisplayedFiles;
    }

    /**
     * @return the tableHeaderSelectFiles
     */
    public WebElement getTableHeaderSelectFiles() {
        return tableHeaderSelectFiles;
    }

    public void clickTableHeaderSelectFiles() {
        this.getTableHeaderSelectFiles();
    }

    /**
     * @return the tableHeaderStatusReports
     */
    public WebElement getTableHeaderStatusReports() {
        return tableHeaderStatusReports;
    }

    public void clickTableHeaderStatusReports() {
        this.getTableHeaderStatusReports();
    }

    /**
     * @return the tableHeaderButtonDownloadFile
     */
    public WebElement getTableHeaderButtonDownloadFile() {
        return tableHeaderButtonDownloadFile;
    }

    public void clickTableHeaderButtonDownloadFile() {
        this.getTableHeaderButtonDownloadFile();
    }

    /**
     * @return the tableRowsStatusReports
     */
    public List<WebElement> getTableRowsStatusReports() {
        return tableRowsStatusReports;
    }

    public List<WebElement> getTableStatusReports() {
        return tableStatusReports;
    }

    public WebElement getFirstTableButtonDownloadFile() {
        return firstTableButtonDownloadFile;
    }

    /**
     * @return the buttonFileCreate
     */
    public WebElement getButtonFileCreate() {
        return buttonFileCreate;
    }

    public void clickButtonFileCreate() {
        this.getButtonFileCreate().click();
    }

    /**
     * @return the buttonFileDelete
     */
    public WebElement getButtonFileDelete() {
        return buttonFileDelete;
    }

    public void clickButtonFileDelete() {
        this.getButtonFileDelete().click();
    }

    /**
     * @return the dropDownPagination
     */
    public WebElement getDropDownPagination() {
        return dropDownPagination;
    }

    private void clickDropDownPagination() {
        this.getDropDownPagination().click();
    }

    public String getDropDownPaginationSelectedValueAsText() {

//        WebElement selectionValue = this.getDropDownPagination().findElement(By.xpath("//p-dropdown//div[@class='ui-dropdown-label-container']/span"));
        WebElement selectionValue = webDriver.findElement(By.xpath("//p-dropdown/div/span"));
        return selectionValue.getText().trim();
    }

    public void selectPaginationByValue(String elementsPerPage) {

        this.clickDropDownPagination();

//        List<WebElement> webElements = this.webDriver.findElements(By.xpath("//div[contains(@class, 'ng-trigger ng-trigger-overlayAnimation')]//div[contains(@class, 'ui-dropdown-items-wrapper')]/ul/p-dropdownitem/li/span"));
        List<WebElement> webElements = this.webDriver.findElements(By.xpath("//ul/p-dropdownitem/li/span"));

        for (WebElement we : webElements) {

            if (we.getText().trim().equals(elementsPerPage)) {
                we.click();
                break;
            }

        }

    }

    /**
     * @return the buttonPaginatonLeftFirst
     */
    public WebElement getButtonPaginatonLeftFirst() {
        return buttonPaginatonLeftFirst;
    }

    public void clickButtonPaginatonLeftFirst() {
        this.getButtonPaginatonLeftFirst().click();
    }

    /**
     * @return the buttonPaginatonLeft
     */
    public WebElement getButtonPaginatonLeft() {
        return buttonPaginatonLeft;
    }

    public void clickButtonPaginatonLeft() {
        this.getButtonPaginatonLeft().click();
    }

    /**
     * @return the buttonPaginatonRight
     */
    public WebElement getButtonPaginatonRight() {
        return buttonPaginatonRight;
    }

    public void clickButtonPaginatonRight() {
        this.getButtonPaginatonRight().click();
    }

    /**
     * @return the buttonPaginatonRightLast
     */
    public WebElement getButtonPaginatonRightLast() {
        return buttonPaginatonRightLast;
    }

    public void clickButtonPaginatonRightlast() {
        this.getButtonPaginatonRightLast().click();
    }

    /**
     * @return the numberOfEntriesTable
     */
    public WebElement getNumberOfEntriesTable() {
        return numberOfEntriesTable;
    }

}
