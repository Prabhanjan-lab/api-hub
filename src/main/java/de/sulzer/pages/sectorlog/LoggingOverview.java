/**
 *
 */
package de.sulzer.pages.sectorlog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;
import de.sulzer.pages.model.ModelLoggingDetail;
import testCases.util.ConstantsSectorLog;

/**
 * @author Sulzer GmbH
 *
 */
public class LoggingOverview extends Page {


//    @FindBy(xpath="//h3[@class='panel-title'][contains(text(), 'Logging Overview')]")
    @FindBy(xpath = "//h3[@class='panel-title ng-star-inserted'][contains(text(), 'Logging Overview')]")
    private WebElement titlePageLoggingOverview;

    @FindBy(xpath="//button[contains(text(), 'Delete Log')]")
    private WebElement buttonDeleteLog;

    @FindBy(xpath="//div[contains(@class, 'ui-table-scrollable-view')]")
    private WebElement logTable;

    @FindAll({
        @FindBy(xpath="//table/thead/tr/th")
    })
    private List<WebElement> listTableHeaderLogEntries;

    @FindAll({
        @FindBy(css = "div table tbody tr")
    })
    private List<WebElement> listLogEntries;

//    @FindBy(xpath="//input[contains(@class, 'ui-inputtext ui-corner-all ui-state-default ui-widget') and contains(@placeholder, 'Source IT')]")
    @FindBy(xpath = "//input[@placeholder='Source IT component, Timestamp, Import Result:']")
    private WebElement searchLogInputField;

    @FindAll({
//        @FindBy(xpath="//div[contains(@class, 'ui-table-scrollable-body')]//table//tbody//tr/td[2]")
            @FindBy(xpath = "//tbody/tr/td[2]")
    })
    private List<WebElement> listLogEntriesTimeStamps;

    @FindAll({
//        @FindBy(xpath="//div[contains(@class, 'ui-table-scrollable-body')]//table//tbody//tr/td[8]")
            @FindBy(xpath = "//tbody/tr/td[8]")
    })
    private List<WebElement> listLogEntriesAddedRecords;

//    @FindBy(xpath="//span[@class='ui-paginator-icon pi pi-caret-right']")
    @FindBy(xpath = "//span[@class='p-paginator-icon pi pi-angle-right']")
    private WebElement buttonOnePageForward;

    /*
     * indeces of table columns
     */
    private final int SOURCEITCOMPONENT = 0;
    private final int IMPORTTIMESTAMP = 1;
    private final int IMPORTRESULT = 2;
    private final int BRAND = 3;
    private final int IMPORTERRORS = 4;
    private final int DELETEDRECORDS = 5;
    private final int ADAPTEDRECORDS = 6;
    private final int ADDEDRECORDS = 7;

    public LoggingOverview(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the titlePageLoggingOverview
     */
    public WebElement getTitlePageLoggingOverview() {
        return titlePageLoggingOverview;
    }

    /**
     * @return the buttonDeleteLog
     */
    public WebElement getButtonDeleteLog() {
        return buttonDeleteLog;
    }

    /**
     * @return the logTable
     */
    public WebElement getLogTable() {
        return logTable;
    }

    public void clickSortColumnHeadSourceITComponent() {
        this.getTableHeaderSourceItComponent().click();
    }

    public void clickSortColumnHeadImportTimeStamp() {
        this.getTableHeaderImportTimeStamp().click();
    }

    public void clickSortColumnHeadImportResult() {
        this.getTableHeaderImportResult().click();
    }

    public void clickSortColumnHeadBrand() {
        this.getTableHeaderBrand().click();
    }

    public void clickSortColumnHeadImportErrors() {
        this.getTableHeaderImportErrors().click();
    }

    public void clickSortColumnHeadDeletedRecords() {
        this.getTableHeaderDeletedRecords().click();
    }

    public void clickSortColumnHeadAdaptedRecords() {
        this.getTableHeaderAdaptedRecords().click();
    }

    public void clickSortColumnHeadAddedRecords() {
        this.getTableHeaderAddedRecords().click();
    }

    public WebElement getTableHeaderSourceItComponent() {
        return this.getTableHeaderLogEntry(this.SOURCEITCOMPONENT);
    }

    public WebElement getTableHeaderImportTimeStamp() {
        return this.getTableHeaderLogEntry(this.IMPORTTIMESTAMP);
    }

    public WebElement getTableHeaderImportResult() {
        return this.getTableHeaderLogEntry(this.IMPORTRESULT);
    }

    public WebElement getTableHeaderBrand() {
        return this.getTableHeaderLogEntry(this.BRAND);
    }

    public WebElement getTableHeaderImportErrors() {
        return this.getTableHeaderLogEntry(this.IMPORTERRORS);
    }

    public WebElement getTableHeaderDeletedRecords() {
        return this.getTableHeaderLogEntry(this.DELETEDRECORDS);
    }

    public WebElement getTableHeaderAdaptedRecords() {
        return this.getTableHeaderLogEntry(this.ADAPTEDRECORDS);
    }

    public WebElement getTableHeaderAddedRecords() {
        return this.getTableHeaderLogEntry(this.ADDEDRECORDS);
    }

    private WebElement getTableHeaderLogEntry(int index) {
        return this.listTableHeaderLogEntries.get(index);
    }

    /**
     * @return the listLogEntries
     */
    public List<WebElement> getListLogEntries(){
        return this.listLogEntries;
    }

    /**
     * Click 'Source IT component' first entry for opening details, and giving
     * back identifying details about opend log entry.
     *
     *  @return string array with identifying values of details of selected log entry
     */
    public ModelLoggingDetail openDetailsFirstLogEntry() {

        if (this.listLogEntries.size() <= 0) {
            return new ModelLoggingDetail();
        }

        WebElement element = this.listLogEntries.get(0);

        ModelLoggingDetail mld = this.getLoggingDetails(element);

        // open details of log entry
        element.findElement(By.xpath(".//td[1]")).click();

        // giving back identifying details
        return mld;
    }

    public ModelLoggingDetail getFirstEntryLoggingOverview() {

        if (this.listLogEntries.size() <= 0) {
            return new ModelLoggingDetail();
        }

        WebElement element = this.listLogEntries.get(0);

        ModelLoggingDetail mld = this.getLoggingDetails(element);

        // giving back identifying details
        return mld;
    }

    private ModelLoggingDetail getLoggingDetails(WebElement element) {

        ModelLoggingDetail mld = new ModelLoggingDetail(
                element.findElement(By.xpath(".//td[1]")).getText().trim(),
                element.findElement(By.xpath(".//td[2]")).getText().trim(),
                element.findElement(By.xpath(".//td[3]")).getText().trim(),
                Integer.parseInt(element.findElement(By.xpath(".//td[5]")).getText().trim()),
                Integer.parseInt(element.findElement(By.xpath(".//td[6]")).getText().trim()),
                Integer.parseInt(element.findElement(By.xpath(".//td[7]")).getText().trim()),
                Integer.parseInt(element.findElement(By.xpath(".//td[8]")).getText().trim()));

        return mld;
    }

    /**
     * @return the logSearchInputField
     */
    public WebElement getSearchLogInputField() {
        return searchLogInputField;
    }

    public void searchLogEntries(String searchItem) {
        this.getSearchLogInputField().clear();
        this.getSearchLogInputField().sendKeys(searchItem);
    }

    public void clearSearchLogInputField() {
        this.getSearchLogInputField().clear();
    }

    public void clickFirstSourceItComponent() {
        this.listLogEntries.get(0).findElement(By.xpath(ConstantsSectorLog.LOG_OVERVIEW_COL1_IT_COMPONENT)).click();
    }

    public void clickFirstImportTimeStamp() {
        this.listLogEntries.get(0).findElement(By.xpath(ConstantsSectorLog.LOG_OVERVIEW_COL2_TIMESTAMP)).click();
    }

    public void clickFirstImportErrors() {
        this.listLogEntries.get(0).findElement(By.xpath(ConstantsSectorLog.LOG_OVERVIEW_COL5_ERRORS)).click();
    }

    public void clickFirstDeletedRecords() {
        this.listLogEntries.get(0).findElement(By.xpath(ConstantsSectorLog.LOG_OVERVIEW_COL6_DELETED)).click();
    }

    public void clickFirstAdaptedRecords() {
        this.listLogEntries.get(0).findElement(By.xpath(ConstantsSectorLog.LOG_OVERVIEW_COL7_ADAPTED)).click();
    }

    public void clickFirstAddedRecords() {
        this.listLogEntries.get(0).findElement(By.xpath(ConstantsSectorLog.LOG_OVERVIEW_COL8_ADDED)).click();
    }

    public boolean isSortedAscendingSourceITComponent() {
        return this.compareStringsAscending(this.retrieveListSourceITComponent());
    }

    public boolean isSortedDescendingSourceITComponent() {
        return this.compareStringsDescending(this.retrieveListSourceITComponent());
    }

    private List<WebElement> retrieveListSourceITComponent() {
        return this.getTableColumEntries(this.SOURCEITCOMPONENT);
    }

    /**
     * @return true in case elements are sorted ascending, otherwise false
     */
    public boolean isSortedAscendingTimeStamp() {

        List<LocalDateTime> listLdt = this.retrieveListImportTimeStamp();

        if (listLdt.size() > 1) {
            return (listLdt.get(0).isBefore(listLdt.get(listLdt.size() - 1)) ||
                    (listLdt.get(0).isEqual(listLdt.get(listLdt.size() - 1))));
        } else {
            return false;
        }
    }

    /**
     * @return true in case elements are sorted descending, otherwise false
     */
    public boolean isSortedDescendingTimeStamp() {

        List<LocalDateTime> listLdt = this.retrieveListImportTimeStamp();

        if (listLdt.size() > 1) {
            return (listLdt.get(0).isAfter(listLdt.get(listLdt.size() - 1)) ||
                    (listLdt.get(0).isEqual(listLdt.get(listLdt.size() - 1))));
        } else {
            return false;
        }
    }

    private List<LocalDateTime> retrieveListImportTimeStamp() {

        List<WebElement> elements = this.getTableColumEntries(this.IMPORTTIMESTAMP);
        List<LocalDateTime> listLdt = new ArrayList();

        for (WebElement element : elements) {
            listLdt.add(this.convertStringLocalDateTime(element.getText().trim()));
        }
        return listLdt;
    }

    public boolean isSortedAscendingOrderTimeStamp() {

        List<LocalDateTime> listLdt = retrieveListImportTimeStamp();

        // checking of ascending order of given timestamps
        if (listLdt.size() > 1) {

            boolean sorted = true;

            for (int i = 0; i < (listLdt.size() - 1); i++) {

                if (listLdt.get(i).isAfter(listLdt.get(i + 1))) {
                    sorted = false;
                    break;
                }

            }

            return sorted;

        } else {
            return true; // one or less element is always sorted (as meaningful that is)
        }
    }

    public boolean isSortedAscendingImportResult() {
        return this.compareStringsAscending(this.retrieveListImportResult());
    }

    public boolean isSortedDescendingImportResult() {
        return this.compareStringsDescending(this.retrieveListImportResult());
    }

    private List<WebElement> retrieveListImportResult() {
        return this.getTableColumEntries(this.IMPORTRESULT);
    }

    public boolean isSortedAscendingImportErrors() {
        return this.compareNumbersAscending(this.retrieveListImportErrors());
    }

    public boolean isSortedDescendingImportErrors() {
        return this.compareNumbersDescending(this.retrieveListImportErrors());
    }

    private List<WebElement> retrieveListImportErrors() {
        return this.getTableColumEntries(this.IMPORTERRORS);
    }

    public boolean isSortedAscendingDeletedRecords() {
        return this.compareNumbersAscending(this.retrieveListDeletedRecords());
    }

    public boolean isSortedDescendingDeletedRecords() {
        return this.compareNumbersDescending(this.retrieveListDeletedRecords());
    }

    private List<WebElement> retrieveListDeletedRecords() {
        return this.getTableColumEntries(this.DELETEDRECORDS);
    }

    public boolean isSortedAscendingAdaptedRecords() {
        return this.compareNumbersAscending(this.retrieveListAdaptedRecords());
    }

    public boolean isSortedDescendingAdaptedRecords() {
        return this.compareNumbersDescending(this.retrieveListAdaptedRecords());
    }

    private List<WebElement> retrieveListAdaptedRecords() {
        return this.getTableColumEntries(this.ADAPTEDRECORDS);
    }

    public boolean isSortedAscendingAddedRecords() {
        return this.compareNumbersAscending(this.retrieveListAddedRecords());
    }

    public boolean isSortedDescendingAddedRecords() {
        return this.compareNumbersDescending(this.retrieveListAddedRecords());
    }

    private List<WebElement> retrieveListAddedRecords() {
        return this.getTableColumEntries(this.ADDEDRECORDS);
    }

    /**
     * @param index
     * @return List of WebElements according given table index (1-based, not zero based!)
     */
    private List<WebElement> getTableColumEntries(int index) {

        List<WebElement> elements = new ArrayList();

        for (WebElement element : this.getListLogEntries()) {
            if (index < 3 && index > 4) { // not import result and not brand
                elements.add(element.findElement(By.xpath(".//td[" + (index + 1) + "]//a"))); // + 1 for xpath (not zero based counting)
            } else {
                elements.add(element.findElement(By.xpath(".//td[" + (index + 1) + "]"))); // + 1 for xpath (not zero based counting)
            }
        }

        return elements;
    }

    /**
     * @param elements
     * @return true in case, given list of strings is in lexicographically ascending order, false otherwise
     */
    private boolean compareStringsAscending(List<WebElement> elements) {

        boolean ascending = true;

        for (int i = 0; i < (elements.size() - 1); i++) {

            String text1 = elements.get(i).getText().trim();
            String text2 = elements.get(i + 1).getText().trim();

            if(!(text1.compareTo(text2) <= 0)) {
                ascending = false;
                break;
            }
        }

        return ascending;
    }

    /**
     * @param elements
     * @return true in case, given list of strings is in lexicographically descending order, false otherwise
     */
    private boolean compareStringsDescending(List<WebElement> elements) {

        boolean descending = true;

        for (int i = 0; i < (elements.size() - 1); i++) {

            String text1 = elements.get(i).getText().trim();
            String text2 = elements.get(i + 1).getText().trim();

            if(!(text1.compareTo(text2) >= 0)) {
                descending = false;
                break;
            }
        }

        return descending;
    }

    private boolean compareNumbersAscending(List<WebElement> elements) {

        boolean ascending = true;

        for (int i = 0; i < (elements.size() - 1); i++) {

            Integer int1 = Integer.parseInt(elements.get(i).getText().trim());
            Integer int2 = Integer.parseInt(elements.get(i + 1).getText().trim());

            if(!(int1.compareTo(int2) <= 0)) {
                ascending = false;
                break;
            }
        }

        return ascending;
    }

    private boolean compareNumbersDescending(List<WebElement> elements) {

        boolean descending = true;

        for (int i = 0; i < (elements.size() - 1); i++) {

            Integer int1 = Integer.parseInt(elements.get(i).getText().trim());
            Integer int2 = Integer.parseInt(elements.get(i + 1).getText().trim());

            if(!(int1.compareTo(int2) >= 0)) {
                descending = false;
                break;
            }
        }

        return descending;
    }

    private LocalDateTime convertStringLocalDateTime(String dateTime) throws NumberFormatException {

        LocalDateTime ldt;

        if (dateTime.matches("[0-9]{2}[.][0-9]{2}[.][0-9]{4}[' '][0-9]{2}[:][0-9]{2}[:][0-9]{2}")) {

            ldt = LocalDateTime.of(Integer.parseInt(dateTime.substring(6, 10)),
                                   Integer.parseInt(dateTime.substring(3, 5)),
                                   Integer.parseInt(dateTime.substring(0, 2)),
                                   Integer.parseInt(dateTime.substring(11, 13)),
                                   Integer.parseInt(dateTime.substring(14, 16)),
                                   Integer.parseInt(dateTime.substring(17, 19)));
        } else {
            throw new NumberFormatException("given timestamp '" + dateTime + "' doesn't mathc pattern (e.g.: '22.06.2018 15:14:30')");
        }

        return ldt;
    }

    /**
     * FIXME
     * ATTENTION: As of now (06/2019) it is assumed as given, that a recall entry exists!
     *            This has to be revised in the future, in order to guaranty an existing recall entry.
     *            Tools are already planned, but installation and functionality with given infrastructure
     *            is not yet working.
     *
     * @param recall
     * @return true in case of searchItem is found in all of the table entries, otherwise false
     */
    public boolean isTableSelectionWorking(String searchItem) {

        boolean existing = true;

        List<WebElement> elements = this.getTableColumEntries(SOURCEITCOMPONENT);

        for (WebElement element : elements) {
            if (! searchItem.equals(elements.get(0).getText().trim())) {
                existing = false;
                break;
            }
        }

        return existing;
    }

    /**
     * @return Gives back time stamp of first entry in table (or empty string).
     */
    public String getTimeStampFirstEntry() {

        if (this.getListLogEntries().size() > 0) {

            WebElement row = this.getListLogEntries().get(0);
            return row.findElement(By.xpath(ConstantsSectorLog.LOG_OVERVIEW_COL2_TIMESTAMP)).getText().trim();

        } else {
            return "";
        }
    }

    /**
     * @param timestamp
     * @return true, in case currently display log entries have same time stamp
     *         as given time stamp. Otherwise, false.
     */
    public boolean checkEntriesTimeStamp(String timestamp) {
        return this.checkStringContains(timestamp);
    }

    /**
     * @param import result
     * @return true, in case currently display log entries have same import result
     *         as given import result. Otherwise, false.
     */
    public boolean checkEntriesImportResult(String success) {
        return this.checkStringContains(success);
    }

    private boolean checkStringContains(String searchItem) {

        boolean existing = true;

        if (this.listLogEntries.size() == 0) {
            return false;
        }

        for (WebElement element : this.listLogEntries) {
            if (! element.getText().trim().contains(searchItem)) {
                existing = false;
                break;
            }
        }

        return existing;
    }

    /**
     * @return the listLogEntriesTimeStamps
     */
    public List<WebElement> getListLogEntriesTimeStamps() {
        return listLogEntriesTimeStamps;
    }

    /**
     * @return the listLogEntriesAddedRecords
     */
    public List<WebElement> getListLogEntriesAddedRecords() {
        return listLogEntriesAddedRecords;
    }

    /**
     * @return the buttonOnePageForward
     */
    public WebElement getButtonOnePageForward() {
        return buttonOnePageForward;
    }

    /**
     * @return void
     */
    public void clickButtonOnePageForward() {
        this.getButtonOnePageForward().click();
    }

}
