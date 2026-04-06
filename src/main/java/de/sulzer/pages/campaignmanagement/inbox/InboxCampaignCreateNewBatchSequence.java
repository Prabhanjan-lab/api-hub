/**
 *
 */
package de.sulzer.pages.campaignmanagement.inbox;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.model.ModelCampaignManualBatchTestData;
import testFramework.utilities.ReusableMethods;

/**
 * @author Sulzer GmbH
 *
 */
public class InboxCampaignCreateNewBatchSequence extends InboxCampaignEditBatchSequence {

   protected WebDriver webDriver;

   public InboxCampaignCreateNewBatchSequence(WebDriver driver){
       super(driver);
       webDriver = driver;
   }

    /*
     * Manual creation Step 1
     */

    // modal dialogue: Create new batch Step 1 - Common Preferences
    @FindBy(xpath="//h4[@class='modal-title pull-left']")
    private WebElement titleCreateNewBatchStep1;

    @FindBy(xpath="//div[@class='col-sm-6']//input[@type='text']")
    private WebElement titleNewBatch;

//    @FindBy(id="inpt-batch-fltr-mod-importer")
    @FindBy(id = "opt-common-preferences-mod-manual-approval")
    private WebElement radioButtonManualApproval;

    /*
     * Manual creation Step 2
     */

    // modal dialogue: Create new batch Step 1 - Common Preferences
    @FindBy(xpath="//h4[@class='modal-title pull-left'][contains(text(), 'Create new batch Step 2 - Vehicle Filtering')]")
    private WebElement titleCreateNewBatchStep2;

    @FindBy(xpath="//input[@placeholder='e.g. +7UG/B29/ABC+B29/BDS/123+UX2+SBU/UBS']")
    private WebElement fieldPRNumbers;

    @FindBy(xpath="//input[@placeholder='e.g. 4N0[2018-2019]/4NX[2018-*]/4NO[*-2019]/4NU[*-2030]/4N8']")
    private WebElement fieldMBT;

    @FindBy(xpath="//input[@placeholder='e.g. 123,234,412']")
    private WebElement fieldImporter;

    // list of two input fields (first input field from, second to)
    @FindAll({
        @FindBy(xpath="//input[@class='form-control number']")
    })
    private List<WebElement> fieldsControlNumber;

    // list of two input fields (first input field from, second to)
    @FindAll({
        @FindBy(xpath="//form//div//input[@name='week']")
    })
    private List<WebElement> fieldsWeek;

    // list of two input fields (first input field from, second to)
    @FindAll({
        @FindBy(xpath="//form//div//input[@name='day']")
    })
    private List<WebElement> fieldsDay;

    // list of two input fields (first input field from, second to)
    @FindAll({
        @FindBy(xpath="//form//div//input[@name='factory']")
    })
    private List<WebElement> fieldsFactory;

    // list of two input fields (first input field from, second to)
    @FindAll({
        @FindBy(xpath="//form//div//input[@name='year']")
    })
    private List<WebElement> fieldsYear;

    // error messages PRNumbers
    @FindBy(xpath="//span[contains(text(), 'Invalid PR Numbers')]")
    private WebElement errorMsgPRNumberInvalid;
    @FindBy(xpath="//span[contains(text(), 'PRN has to follow the syntax ((+|/|+!)([A-Z0-9]{3}))+.')]")
    private WebElement errorMsgPRNumberInvalidRegEx;

    // error messages from
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Invalid Week in From section')]")
    private WebElement errorMsgWeekFrom;
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Invalid Day in From section')]")
    private WebElement errorMsgDayFrom;
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Invalid Factory in From section')]")
    private WebElement errorMsgFactoryFrom;
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Invalid Year in From section')]")
    private WebElement errorMsgYearFrom;

    // error messages to
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Invalid Week in To section')]")
    private WebElement errorMsgWeekTo ;
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Invalid Day in To section')]")
    private WebElement errorMsgDayTo ;
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Invalid Factory in To section')]")
    private WebElement errorMsgFactoryTo ;
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Invalid Year in To section')]")
    private WebElement errorMsgYearTo ;

    // error messages both
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Calendar week has to follow the syntax [01-53]{2}|*.')]")
    private WebElement errorMsgCalendarWeekRegEx;
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Day  has to follow the syntax [1-7]{1}|*.')]")
    private WebElement errorMsgDayRegEx;
    @FindBy(xpath="//span[contains(text(), 'Factory has to follow the syntax [a-zA-Z0-9]{2}|*.')]")
    private WebElement errorMsgFactoryRegEx;
    @FindBy(xpath="//div//fieldset//div//span[contains(text(), 'Year has to follow the syntax [1950-2100]{4}|*.')]")
    private WebElement errorMsgYearRegEx;

    // error message no filter defined
//    @FindBy(xpath="//span[@class='batchFormValidator'][contains(text(), 'At least one filter has to be defined')]")
    @FindBy(xpath = "//*[.='At least one filter has to be defined']")
    private WebElement errorMsgNoFilterDefined;

    // message MBT
    @FindBy(xpath="//span[contains(text(), 'MBT invalid')]")
    private WebElement errorMsgMBTInvalid;

    @FindBy(xpath="//span[contains(text(), 'MBT has to follow the syntax [A-Z0-9]{3}.')]")
    private WebElement errorMsgMBTInvalidRegEx;

    // error message importer
    @FindBy(xpath="//span[contains(text(), 'Importeur invalid')]")
    private WebElement errorMsgImporterInvalid;

    @FindBy(xpath="//span[contains(text(), 'Importernumber has to follow the syntax [0-9]{3}.')]")
    private WebElement errorMsgImporterInvalidRegEx;

    /*
     * Manual creation Step 3
     */
    @FindBy(xpath="//button[contains(text(), 'Create batch')]")
    private WebElement buttonCreateBatch;

    @FindAll({
        @FindBy(xpath="//div[@class='modal-content']//div[@class='tab-pane active']//table//tbody//tr")
    })
    private List<WebElement> tableVINs;

    @FindBy(xpath="//div[@class='tab-pane active']//td[contains(text(), 'No records found')]")
    private WebElement emptyTableMessage;


    /**
     * @return the titleCreateNewBatchStep1
     */
    public WebElement getTitleCreateNewBatchStep1() {
        return titleCreateNewBatchStep1;
    }

    /**
     * @return the titleNewBatch
     */
    public WebElement getTitleNewBatch() {
        return titleNewBatch;
    }

    public void setTitleNewBatch(String value) {
        this.getTitleNewBatch().sendKeys(value);
    }

    /**
     * @return the radioButtonManualApproval
     */
    public WebElement getRadioButtonManualApproval() {
        return radioButtonManualApproval;
    }

    public void clickRadioButtonManualApproval() {
        this.getRadioButtonManualApproval().click();
    }

    /**
     * @return the titleCreateNewBatchStep2
     */
    public WebElement getTitleCreateNewBatchStep2() {
        return titleCreateNewBatchStep2;
    }

    /**
     * @return the fieldPRNumbers
     */
    public WebElement getFieldPRNumbers() {
        return fieldPRNumbers;
    }

    public void setValuePRNumbers(String value) throws Exception {

        ReusableMethods.waitForClickablility(getFieldPRNumbers(), 3, webDriver);
        value = value.replace(" ", "");

        String[] prn = value.split(",");

        if (prn.length > 0) {

            String prnumber = String.join("/", prn);

            this.getFieldPRNumbers().clear();
            this.getFieldPRNumbers().sendKeys("+" + prnumber);

        } else {
            throw new Exception("PRN wasn't available!");
        }

    }

    public void setValueSinglePRNumber(String prnNumber) throws Exception {

        if (prnNumber.length() > 0) {

            this.getFieldPRNumbers().clear();
            if (! prnNumber.startsWith("+")) {
                prnNumber = "+" + prnNumber;
            }

            this.getFieldPRNumbers().sendKeys(prnNumber);

        } else {
            throw new Exception("PRN wasn't available!");
        }

    }

    public void clearValuePRNumbers() {
        this.getFieldPRNumbers().clear();
    }

    /**
     * @return the fieldMBT
     */
    public WebElement getFieldMBT() {
        return fieldMBT;
    }

    public void setValueMBT(String value) {
        this.getFieldMBT().clear();
        this.getFieldMBT().sendKeys(value);
    }

    /**
     * @return the fieldImporter
     */
    public WebElement getFieldImporter() {
        return fieldImporter;
    }

    public void setValueImporter(String value) {
        this.getFieldImporter().clear();
        this.getFieldImporter().sendKeys(value);
    }

    /**
     * @return the fieldsControlNumber
     */
    public List<WebElement> getFieldsControlNumber() {
        return fieldsControlNumber;
    }

    public WebElement getControlNumberFrom() {
        return this.getFieldsControlNumber().get(0);
    }

    public WebElement getControlNumberTo() {
        return this.getFieldsControlNumber().get(1);
    }

    /**
     * @return the fieldsWeek
     */
    public List<WebElement> getFieldsWeek() {
        return fieldsWeek;
    }

    public WebElement getWeekFrom() {
        return this.getFieldsWeek().get(0);
    }

    public void setWeekFrom(String value) {
        this.getFieldsWeek().get(0).clear();
        this.getFieldsWeek().get(0).sendKeys(value);
    }

    public WebElement getWeekTo() {
        return this.getFieldsWeek().get(1);
    }

    public void setWeekTo(String value) {
        this.getFieldsWeek().get(1).clear();
        this.getFieldsWeek().get(1).sendKeys(value);
    }

    /**
     * @return the fieldsDay
     */
    public List<WebElement> getFieldsDay() {
        return fieldsDay;
    }

    public WebElement getDayFrom() {
        return this.getFieldsDay().get(0);
    }

    public void setDayFrom(String value) {
        this.getFieldsDay().get(0).sendKeys(value);
    }

    public WebElement getDayTo() {
        return this.getFieldsDay().get(1);
    }

    public void setDayTo(String value) {
        this.getFieldsDay().get(1).sendKeys(value);
    }

    /**
     * @return the fieldsFactory
     */
    public List<WebElement> getFieldsFactory() {
        return fieldsFactory;
    }

    public WebElement getFactoryFrom() {
        return this.getFieldsFactory().get(0);
    }

    public void setFactoryFrom(String value) {
        this.getFieldsFactory().get(0).sendKeys(value);
    }

    public WebElement getFactoryTo() {
        return this.getFieldsFactory().get(1);
    }

    public void setFactoryTo(String value) {
        this.getFieldsFactory().get(1).sendKeys(value);
    }

    /**
     * @return the fieldsYear
     */
    public List<WebElement> getFieldsYear() {
        return fieldsYear;
    }

    public WebElement getYearFrom() {
        return this.getFieldsYear().get(0);
    }

    public void setYearFrom(String value) {
        this.getFieldsYear().get(0).sendKeys(value);
    }

    public WebElement getYearTo() {
        return this.getFieldsYear().get(1);
    }

    public void setYearTo(String value) {
        this.getFieldsYear().get(1).sendKeys(value);
    }

    /**
     * @return the errorMsgWeekFrom
     */
    public WebElement getErrorMsgWeekFrom() {
        return errorMsgWeekFrom;
    }

    /**
     * @return the errorMsgDayFrom
     */
    public WebElement getErrorMsgDayFrom() {
        return errorMsgDayFrom;
    }

    /**
     * @return the errorMsgFactoryFrom
     */
    public WebElement getErrorMsgFactoryFrom() {
        return errorMsgFactoryFrom;
    }

    /**
     * @return the errorMsgYearFrom
     */
    public WebElement getErrorMsgYearFrom() {
        return errorMsgYearFrom;
    }

    /**
     * @return the errorMsgWeekTo
     */
    public WebElement getErrorMsgWeekTo() {
        return errorMsgWeekTo;
    }

    /**
     * @return the errorMsgDayTo
     */
    public WebElement getErrorMsgDayTo() {
        return errorMsgDayTo;
    }

    /**
     * @return the errorMsgFactoryTo
     */
    public WebElement getErrorMsgFactoryTo() {
        return errorMsgFactoryTo;
    }

    /**
     * @return the errorMsgYearTo
     */
    public WebElement getErrorMsgYearTo() {
        return errorMsgYearTo;
    }

    /**
     * @return the errorMsgCalendarWeekRegEx
     */
    public WebElement getErrorMsgCalendarWeekRegEx() {
        return errorMsgCalendarWeekRegEx;
    }

    /**
     * @return the errorMsgDayRegEx
     */
    public WebElement getErrorMsgDayRegEx() {
        return errorMsgDayRegEx;
    }

    /**
     * @return the errorMsgFactoryRegEx
     */
    public WebElement getErrorMsgFactoryRegEx() {
        return errorMsgFactoryRegEx;
    }

    /**
     * @return the errorMsgYearRegEx
     */
    public WebElement getErrorMsgYearRegEx() {
        return errorMsgYearRegEx;
    }

    /**
     * @return the errorMsgNoFilterDefined
     */
    public WebElement getErrorMsgNoFilterDefined() {
        return errorMsgNoFilterDefined;
    }

    /**
     * @return the errorMsgPRNumberInvalid
     */
    public WebElement getErrorMsgPRNumberInvalid() {
        return errorMsgPRNumberInvalid;
    }

    /**
     * @return the errorMsgPRNumberInvalidRegEx
     */
    public WebElement getErrorMsgPRNumberInvalidRegEx() {
        return errorMsgPRNumberInvalidRegEx;
    }

    /**
     * @return the errorMsgMBTInvalid
     */
    public WebElement getErrorMsgMBTInvalid() {
        return errorMsgMBTInvalid;
    }

    /**
     * @return the errorMsgMBTInvalidRegEx
     */
    public WebElement getErrorMsgMBTInvalidRegEx() {
        return errorMsgMBTInvalidRegEx;
    }

    /**
     * @return the errorMsgImporterInvalid
     */
    public WebElement getErrorMsgImporterInvalid() {
        return errorMsgImporterInvalid;
    }

    /**
     * @return the errorMsgImporterInvalidRegEx
     */
    public WebElement getErrorMsgImporterInvalidRegEx() {
        return errorMsgImporterInvalidRegEx;
    }

    /**
     * @return the buttonCreateBatch
     */
    public WebElement getButtonCreateBatch() {
        return buttonCreateBatch;
    }

    public void clickButtonCreateBatch() {
        this.getButtonCreateBatch().click();
    }

    /**
     * @return the tableVINs
     */
    public List<WebElement> getTableVINs() {
        return tableVINs;
    }

    /**
     * @return the emptyTableMessage
     */
    public WebElement getEmptyTableMessage() {
        return emptyTableMessage;
    }

    public void setPKNFrom(String pkn) {

        String[] pknParts = splitPKNParts(pkn);

        this.getWeekFrom().sendKeys(pknParts[0]);
        this.getDayFrom().sendKeys(pknParts[1]);
        this.getFactoryFrom().sendKeys(pknParts[2]);
        this.getYearFrom().sendKeys(pknParts[3]);
    }

    public void setPKNTo(String pkn) {

        String[] pknParts = splitPKNParts(pkn);

        this.getWeekTo().sendKeys(pknParts[0]);
        this.getDayTo().sendKeys(pknParts[1]);
        this.getFactoryTo().sendKeys(pknParts[2]);
        this.getYearTo().sendKeys(pknParts[3]);

    }

    private String[] splitPKNParts(String pkn) {

        String[] pknParts = new String[4];

        pknParts[0] = pkn.substring(0, 2); // week
        pknParts[1] = pkn.substring(2, 3); // day
        pknParts[2] = pkn.substring(3, 5); // factory
        pknParts[3] = pkn.substring(5);    // year

        return pknParts;
    }

    public void clearFields() {

        //
        this.getFieldPRNumbers().clear();
        //
        this.getWeekFrom().clear();
        this.getDayFrom().clear();
        this.getFactoryFrom().clear();
        this.getYearFrom().clear();
        //
        this.getWeekTo().clear();
        this.getDayTo().clear();
        this.getFactoryTo().clear();
        this.getYearTo().clear();
        //
        this.getFieldMBT().clear();
        this.getFieldImporter().clear();

    }

    public boolean isElementAvailable(ModelCampaignManualBatchTestData testdata, WebDriver webDriver) {

        // standard (Selenium) wait didn't work
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.tableVINs.size() == 0) {
            return false;
        }

        boolean found = false;

        for (WebElement element : this.tableVINs) {

            //
            String cellVin = element.findElement(By.xpath(".//td[1]")).getText().trim().replaceAll("[\\W]",  "");
            String cellImporter = element.findElement(By.xpath(".//td[2]")).getText().trim().replaceAll("[\\W]",  "");
            String cellPkn = element.findElement(By.xpath(".//td[3]")).getText().trim().replaceAll("[\\W]",  "");
            String cellMbt = element.findElement(By.xpath(".//td[4]")).getText().trim().replaceAll("[\\W]",  "");

            if(cellVin.equals(testdata.getVIN().trim()) &&
                    cellPkn.equals(testdata.getPKN().trim()) &&
                    cellMbt.equals(testdata.getMBT().trim()) &&
                    cellImporter.equals(this.removeLeadingZeros(testdata.getImporter()).trim())) {

                found = true;
                break;
            }
        }

        return found;
    }

    public boolean checkVins(List<String> listVerificationVins) {

        // standard (Selenium) wait didn't work
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.tableVINs.size() == 0) {
            return false;
        }

        int counter = 0;

        for (WebElement element : this.tableVINs) {

            //
            String cellVin = element.findElement(By.xpath(".//td[1]")).getText().trim().replaceAll("[\\W]",  "");

            if (listVerificationVins.contains(cellVin)) {
                counter++;
            }
        }

        return (counter == listVerificationVins.size());
    }

    private String removeLeadingZeros(String value) {

        if (! value.startsWith("0")) {
            return value;
        }

        if (value.length() == 0) {
            return "";
        }

        String temp = value;

        while(temp.startsWith("0")) {
            temp = temp.substring(1);
        }

        return temp;
    }

}
