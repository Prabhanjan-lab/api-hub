/**
 *
 */
package de.sulzer.pages.sectorlog;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.sulzer.pages.genericelements.Page;
import de.sulzer.pages.model.ModelLoggingDetail;
import testFramework.utilities.ReusableMethods;

/**
 * @author Sulzer GmbH
 *
 */
public class LoggingDetail extends Page {

    protected WebDriver webDriver;

    public LoggingDetail(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }

//    @FindBy(xpath = "//h3[@class='panel-title'][contains(text(), 'Logging Detail')]")
    @FindBy(xpath = "//h3[contains(text(), 'Logging Detail')]")
    private WebElement titlePageLoggingDetails;

    @FindBy(xpath = "//button[contains(text(), 'Delete Log')]")
    private WebElement buttonDeleteLogDetail;

    @FindBy(xpath = "//select[@id='sourceComponent']")
    private WebElement selectionSourceComponent;

    @FindBy(xpath = "//select[@id='importTimestamp']")
    private WebElement selectionImportTimeStamp;

    @FindBy(xpath = "//div[@id='importResult']/span")
    private WebElement importResult;

    @FindBy(xpath = "//select[@id='dataRecords']")
    private WebElement selectionDataRecords;

    @FindBy(xpath = "//input[@id='record-search-field']")
    private WebElement inputSearchDataRecords;

    @FindAll({
        @FindBy(xpath = "//div//table//tbody//tr")
    })
    private List<WebElement> listLogDetailEntries;

    @FindBy(xpath="//span[@class='fa fa-forward']")
    private WebElement buttonOnePageForward;

    @FindBy(xpath = "//tbody/tr/td[contains(text(), 'No records found.')]")
    private WebElement tableMessageNoRecordsFound;

    // Delete log modal heading
    @FindBy(xpath = "//div[@class='modal-header']//h4[@class='modal-title']")
    private WebElement deletelogheading;

    // YES button in confirm log deletion pop up
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement logDeletionYes;

    // Added records data
//    @FindBy(xpath = "//table//tr")
    @FindBy(xpath = "//tbody//tr")
    private WebElement dataRecordsTableText;

    // Error pop up text
    @FindBy(xpath = "//h4//following::p")
    private WebElement textOfErrorPopup;

    // Ok button in error popup
    @FindBy(xpath = "//button[contains(text(),'Ok')]")
    private WebElement okButton;

    // Message after clicking 'Delete Log'
//    @FindBy(xpath = "//span[contains(@class, 'ui-messages-detail')]")
    @FindBy(xpath = "//span[contains(@class, 'p-message-detail')]")
    private WebElement deleteLogMessage;

    // Search box
    @FindBy(css = ".margin-bottom input")
    private WebElement loggingInbox;

    /*
     * types of data record
     */
    public static final String DATA_RECORDS = "Data records:";
    public static final String IMPORT_ERRORS = "Import errors";
    public static final String DELETED_RECORDS = "Deleted records";
    public static final String ADAPTED_RECORDS = "Adapted records";
    public static final String ADDED_RECORDS = "Added records";


    public WebElement getDeleteLogMessage() {
        return this.deleteLogMessage;
    }

    public void clickButtonDeleteLogMessage() {
        this.getButtonDeleteLogDetail();
    }

    public WebElement getOkbuttonOfErrorpopup() {
        return this.okButton;
    }

    public WebElement getTextOfErrorPopup() {
        return this.textOfErrorPopup;
    }

    public WebElement getlogDeletionYes() {
        return this.logDeletionYes;
    }

    public WebElement getDataRecordsTable() {
        return this.dataRecordsTableText;
    }

    /**
     * @return the titlePageLoggingDetails
     */
    public WebElement getTitlePageLoggingDetails() {
        return this.titlePageLoggingDetails;
    }

    /**
     * @return the buttonDeleteLog
     */
    public WebElement getButtonDeleteLogDetail() {
        return this.buttonDeleteLogDetail;
    }

    public void clickButtonDeleteLogDetail() {
        this.getButtonDeleteLogDetail().click();
    }

    /**
     * @return the selectionSourceComponent
     */
    public WebElement getSelectionSourceComponent() {
        return this.selectionSourceComponent;
    }

    /**
     * @return the selectionImportTimeStamp
     */
    public WebElement getSelectionImportTimeStamp() {
        return selectionImportTimeStamp;
    }

    /**
     * @return the importResult
     */
    public WebElement getImportResult() {
        return importResult;
    }

    /**
     * @return the selectionDataRecords
     */
    public WebElement getSelectionDataRecords() {
        return selectionDataRecords;
    }

    /**
     * @return the inputSearchDataRecords
     */
    public WebElement getInputSearchDataRecords() {
        return inputSearchDataRecords;
    }

    public void searchInputSearchDataRecords(String searchItem) {
        this.getInputSearchDataRecords().clear();
        this.getInputSearchDataRecords().sendKeys(searchItem);
    }

    /**
     * @return the listLogDetailEntries
     */
    public List<WebElement> getListLogDetailEntries() {
        return listLogDetailEntries;
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

    /**
     * @return the tableMessageNoRecordsFound
     */
    public WebElement getTableMessageNoRecordsFound() {
        return tableMessageNoRecordsFound;
    }

    /**
     * @return the SearchBox Webelement
     */
    public WebElement getLoggingInbox() {return loggingInbox;}

    /**
     * @return the selectionSourceComponent
     */
    private Select sourceComponentMenu;

    public Select getSourceComponentSelectMenu() {

        if (null == this.sourceComponentMenu) {
            this.sourceComponentMenu = new Select(selectionSourceComponent);
        }
        return this.sourceComponentMenu;
    }

    public WebElement getSelectedSourceComponent() {
        return this.getSourceComponentSelectMenu().getFirstSelectedOption();
    }

    public boolean checkDetails(ModelLoggingDetail mld) {

//        boolean result = true;
//        Select select = null;
//
//        // thread sleep to ensure rendering of following components to test
//        try {
//            Thread.sleep(4000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // source IT component
//        select = new Select(this.getSelectionSourceComponent());
//        if (!this.checkEquals(mld.getSourceItComponent(), select)) {
//            return false;
//        }
//        // time stamp
//        select = new Select(this.getSelectionImportTimeStamp());
//        if (!this.checkEquals(mld.getImportTimeStamp(), select)) {
//            return false;
//        }
//        // import result
//        if (!mld.getImportResult().equals(this.getImportResult().getText().trim())) {
//            return false;
//        }
//
//        // data records
//        select = new Select(this.getSelectionDataRecords());
//        WebElement firstSelection = select.getFirstSelectedOption();
//        // decision, which data to test (depending on followed link from loggin
//        // overview)
//        int listSize = this.listLogDetailEntries.size();
//        String selection = firstSelection.getText().trim();
//
//        System.out.println(selection);
//
//        // no records found
//        if (null != this.getTableMessageNoRecordsFound() && listSize == 1) {
//            return true;
//        }
//
//        //
//        switch (selection) {
//
//        case IMPORT_ERRORS:
//            result = (listSize == mld.getImportErrors());
//            System.out.println(mld.getImportErrors());
//            break;
//
//        case DELETED_RECORDS:
//            result = (listSize == mld.getDeletedRecords());
//            System.out.println(mld.getDeletedRecords());
//            break;
//
//        case ADAPTED_RECORDS:
//            result = (listSize == mld.getAdaptedRecords());
//            System.out.println(mld.getAdaptedRecords());
//            break;
//
//        case ADDED_RECORDS:
//            result = (listSize == mld.getAddedRecords());
//            System.out.println(mld.getAddedRecords());
//            break;
//
//        case DATA_RECORDS:
//            result = true;
//            break;
//
//        default:
//            result = false;
//            break;
//
//        }
//
//        //
//        return result;









        // Method new rewritten

        boolean result = true;
        Select select = null;

        // thread sleep to ensure rendering of following components to test
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // source IT component
        select = new Select(this.getSelectionSourceComponent());
        if (!this.checkEquals(mld.getSourceItComponent(), select)) {
            return false;
        }

        // time stamp
        select = new Select(this.getSelectionImportTimeStamp());
        select.selectByVisibleText(mld.getImportTimeStamp());

        if (!this.checkEquals(mld.getImportTimeStamp(), select)) {
            return false;
        }


        // import result
        if (!mld.getImportResult().equals(this.getImportResult().getText().trim())) {
            return false;
        }

        // data records
        select = new Select(this.getSelectionDataRecords());
        WebElement firstSelection = select.getFirstSelectedOption();
        // decision, which data to test (depending on followed link from loggin overview)
        int listSize = this.listLogDetailEntries.size();
        System.out.println("List size : " + listSize);
        String selection = firstSelection.getText().trim();
        System.out.println("Selection : " + selection);

        // no records found
//        if (null != this.getTableMessageNoRecordsFound() && listSize == 1) {
            if (null != this.getTableMessageNoRecordsFound()) {
            return true;
        }

        //
        switch (selection) {

            case IMPORT_ERRORS:
                result = (listSize == mld.getImportErrors());
                System.out.println(mld.getImportErrors());
                break;

            case DELETED_RECORDS:
                result = (listSize == mld.getDeletedRecords());
                System.out.println(mld.getDeletedRecords());
                break;

            case ADAPTED_RECORDS:
                result = (listSize == mld.getAdaptedRecords());
                System.out.println(mld.getAdaptedRecords());
                break;

            case ADDED_RECORDS:
                result = (listSize == mld.getAddedRecords());
                System.out.println(mld.getAddedRecords());
                break;

            case DATA_RECORDS:
                result = true;
                break;

            default:
                result = false;
                break;

        }


        return result;
    }



    private boolean checkEquals(String stringValue, Select select) {
        System.out.println("'" + stringValue + "' = '" + stringValue.equals(select.getFirstSelectedOption().getText().trim()) + "'");
        return stringValue.equals(select.getFirstSelectedOption().getText().trim());
    }

    public String getSelectedSourceComponentText() {
        return this.getSelectedSourceComponent().getText().trim();
    }

    public void clickSourceComponent() {
        this.getSelectionSourceComponent().click();
    }

    public void setSourceComponentByVisibleText(String sourceComponent) {
        this.getSourceComponentSelectMenu().selectByVisibleText(sourceComponent);
    }

    private Select importTimeStampMenu;

    public Select getImportTimestampSelectMenu() {

        if (null == this.importTimeStampMenu) {
            this.importTimeStampMenu = new Select(selectionImportTimeStamp);
        }
        return this.importTimeStampMenu;
    }

    public void setImportTimestampByVisibleText(String impTimestamp) {
        this.getImportTimestampSelectMenu().selectByVisibleText(impTimestamp);
    }

    public void setimportTimestampByPosition(int index){
        this.getImportTimestampSelectMenu().selectByIndex(index);
    }

    public String getParticularImportTimestamp(Integer index) {
        return this.getImportTimestampSelectMenu().getOptions().get(index).getText();
    }

    // Validating the Vehicle timestamp
    public boolean verifyImportTimestampFormat(String ImportTimestamp) {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            Date d = dateFormat.parse(ImportTimestamp);
            if (ImportTimestamp.equals(dateFormat.format(d))) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            System.out.println("Format of date '" + ImportTimestamp + "' is Wrong ");
            return false;
        }
    }

    // Sorting the dates in Descending order (recent one on the top)
    public List<Date> sortDates() throws ParseException {
        List<Date> sortdates = this.getSortedDatesinDateFormat();
        Collections.sort(sortdates, Collections.reverseOrder());
        return sortdates;

    }

    // Dates in String format are parsed to Date format
    public List<Date> getSortedDatesinDateFormat() throws ParseException {
        List<Date> datesFormat = new ArrayList<>();
        SimpleDateFormat d = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        for (String str : this.getDateList()) {

            datesFormat.add(d.parse(str));
        }
        datesFormat.remove("Import Timestamp:");

        return datesFormat;
    }

    @FindAll({ @FindBy(xpath = "//select[@id='importTimestamp']//option") })
    private List<WebElement> listOfImportedTimeStamps;

    public List<WebElement> listOfImportedTimeStamps() {
        return this.listOfImportedTimeStamps;
    }

    // Getting the whole list of Import Time stamps
    public List<String> getDateList() {

        List<String> temp_ImportTimestamp = new ArrayList<String>();

        for (WebElement one : listOfImportedTimeStamps()) {
            String listImportTimestamp = one.getText();
            temp_ImportTimestamp.add(listImportTimestamp);
        }
        temp_ImportTimestamp.remove("Import Timestamp:");
        return temp_ImportTimestamp;
        // for(String one: temp_ImportTimestamp) { System.out.println(one); }

    }

    private Select dataRecordsMenu;

    public Select getDataRecordsSelectMenu() {

        if (null == this.dataRecordsMenu) {
            this.dataRecordsMenu = new Select(selectionDataRecords);
        }
        return this.dataRecordsMenu;
    }

    public String getParticularDataRecord(Integer index) {
        return this.getDataRecordsSelectMenu().getOptions().get(index).getText();
    }

    public void setDataRecordByIndex(Integer index) {
        this.getDataRecordsSelectMenu().selectByIndex(index);
    }

    // Verifying content in Data records and sort
    public void verifyContentInDataRecordsSectionAndSort() throws Exception {

        String beforeSortArrow = this.getsortDataRecordsUpArrowButton().getAttribute("class");
        assertTrue(beforeSortArrow.contains("pi-sort-amount-up-alt"));
        this.clickSortDataRecords();
        ReusableMethods.waitForVisibility(getsortDataRecordsDownArrowButton(), 3, webDriver);
        System.out.println("Sort clicked");
        String afterSortArrow = this.getsortDataRecordsDownArrowButton().getAttribute("class");
        assertTrue(afterSortArrow.contains("pi-sort-amount-down"));
    }

    // Sort Up arrow button
//    @FindBy(xpath = "//i[@class='ui-sortable-column-icon pi pi-fw pi-sort-amount-up-alt']")
    @FindBy(xpath = "//i[@class='p-sortable-column-icon pi pi-fw pi-sort-amount-up-alt']")
    private WebElement sortDataRecordsUpArrow;

    public WebElement getsortDataRecordsUpArrowButton() {
        return this.sortDataRecordsUpArrow;
    }

    // Sort Down arrow button
//    @FindBy(xpath = "//i[@class='ui-sortable-column-icon pi pi-fw pi-sort-amount-down']")
    @FindBy(xpath = "//i[@class='p-sortable-column-icon pi pi-fw pi-sort-amount-down']")
    private WebElement sortDataRecordsDownArrow;

    public WebElement getsortDataRecordsDownArrowButton() {
        return this.sortDataRecordsDownArrow;
    }

    public void clickSortDataRecords() {
        this.getsortDataRecordsButton().click();
    }

    // Sort data records button
//    @FindBy(xpath = "//th[@class='ui-sortable-column ui-state-highlight']")
    @FindBy(xpath = "//th[@class='p-element p-sortable-column p-highlight']")
    private WebElement sortDataRecords;

    public WebElement getsortDataRecordsButton() {
        return this.sortDataRecords;
    }

    // Delete log button Cancel option
    @FindBy(xpath = "//div[@class='modal-footer']//div/button[@id='cancel-delete']")
    private WebElement deletelogcancel;

    public WebElement getDeleteLogCancelButton() {
        return this.deletelogcancel;
    }

    @FindAll({ @FindBy(xpath = "//select[@id='sourceComponent']//option") })
    private List<WebElement> listSourceITComponets;

    public List<WebElement> getListSourceITComponets() {
        return listSourceITComponets;
    }

    public List<String> sourceITComponents() {
        List<String> components = new ArrayList<String>();
        for (WebElement component : listSourceITComponets) {
            components.add(component.getText());
        }
        return components;
    }

    // Verifying sorting order of Source IT Component (Ascending Order)
    public boolean verifySourceComponentSortingOrder() {
        List<String> componentsAfterSort = new ArrayList<String>();
        selectionSourceComponent.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        componentsAfterSort = this.sourceITComponents();
        Collections.sort(componentsAfterSort);

        if (componentsAfterSort.equals(this.sourceITComponents())) {
            return true;
        } else {
            System.out.println("Source IT components are not in alphabetical order");
            return false;
        }

    }

    public WebElement getSelectedDataRecord() {
        return this.getDataRecordsSelectMenu().getFirstSelectedOption();
    }

    public String getSelectedDataRecordText() {
        return this.getSelectedDataRecord().getText();
    }

    public WebElement getDeleteLogHeader() {
        return this.deletelogheading;
    }

    public String dataRecordsTableText() {
        return this.getDataRecordsTable().getText().trim();
    }

    public void clickLogDeletionYes() {
        this.getlogDeletionYes().click();
    }

    public String errorPopupText() {
        return this.textOfErrorPopup.getText();
    }

    public void clickOkButtonOfErrorPopup() {
        this.getOkbuttonOfErrorpopup().click();
    }

    // UI Message after clicking 'Delete log' button
    public String deleteLogMessage() {
        return this.getDeleteLogMessage().getText();
    }
}
