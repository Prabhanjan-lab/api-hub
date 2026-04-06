package de.sulzer.pages.vehicleadministration.exceptionlistmanagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import testFramework.utilities.ReusableMethods;

import java.util.concurrent.TimeUnit;

public class AddExceptionList extends ExceptionListOverviewPage {

    protected WebDriver webDriver;

    public AddExceptionList(WebDriver driver) {

        super(driver);

        this.webDriver = driver;
    }

    //Get the Add Vehicle to Exception List Panel Title text
    @FindBy(xpath="//h4[contains(@class, 'modal-title')]")
    private WebElement panelTitle;

    // vin text box
    @FindBy(id = "inpt-veh-list-mod-vin")
    private WebElement vin;

    @FindBy(xpath="//span[contains(text(),'Dealerships')]")
    private WebElement dealershipsTab;

    @FindBy(xpath="//span[contains(text(),'Attending')]")
    private WebElement attendingTab;

    @FindBy(xpath="//span[contains(text(),'Delivering')]")
    private WebElement deliveringTab;

    @FindBy(xpath="//span[contains(text(),'Ordering')]")
    private WebElement orderingTab;

    /******General Settings Tab Inputs********/
    //MBV text box
    @FindBy(id = "inpt-veh-list-mod-mbv")
    private WebElement mbv;

    //PRN text box
    @FindBy(id = "inpt-veh-list-mod-pr-nums")
    private WebElement prn;

    @FindBy(id = "drop-veh-list-mod-template")
    private WebElement template;

    @FindBy(xpath = "(//span[contains(text(), 'Sulzer')])[1]")
    private WebElement templateSulzer;

    //Model Year text box
    @FindBy(id="inpt-veh-list-mod-model-year")
    private WebElement modelYear;

    //Model Year error message field (@me)
    @FindBy(xpath = "//span[contains(text(),'Model year must be between 1950 and 2100')]")
    private WebElement errorMessageModelYearOutOfRange;

    // MBT Value by clicking Calculate button
    @FindBy(xpath="//button[contains(text(),'Calculate')]")
    private WebElement calculate;

    //MBT text box
    @FindBy(id="inpt-veh-list-mod-mbt")
    private WebElement mbt;

    // comment text box
    @FindBy(id = "inpt-veh-list-mod-comment")
    private WebElement comment;

    /********************  PKN tab Inputs **************/
    // Calendar Week text box
    @FindBy(id = "inpt-veh-list-mod-pkn-week")
    private WebElement calenderWeek;

    // PKN tab label check
    @FindBy(xpath = "//label[contains(text(), 'Calendar week')]")
    private WebElement cweeklabel;

    //Day text box
    @FindBy(id = "inpt-veh-list-mod-pkn-day")
    private WebElement pkdnDay;

    //Factory text box
    @FindBy(id = "inpt-veh-list-mod-pkn-factory")
    private WebElement pknFactory;

    //Year text box
    @FindBy(id="inpt-veh-list-mod-pkn-year")
    private WebElement pknYear;

    // Create button
    @FindBy(id = "inf-veh-list-mod-exception-created")
    private WebElement create;

    // update button
    @FindBy(id = "inf-veh-list-mod-exception-updated")
    private WebElement update;

    @FindBy(id = "btn-veh-list-mod-cancel")
    private WebElement cancel;

    //Concatenation
    @FindBy(id="inpt-veh-list-mod-pkn-num")
    private WebElement pknConcatenated;

    /**
    ************DealerShips Tab Inputs *************
    ************     Attending Tab     *************
     */

    @FindBy(id="drop-veh-list-mod-attend-cntry-code")
    private WebElement attendingCountryCode;

    @FindBy(id="btn-veh-list-mod-attend-brand")
    private WebElement attendingBrand;

    @FindBy(id="inpt-veh-list-mod-attend-partner-num")
    private WebElement attendingPartnerNumber;

    @FindBy(id="inpt-veh-list-mod-attend-wholesaler")
    private WebElement attendingWholesaler;

    @FindBy(id="inpt-veh-list-mod-attend-concat")
    private WebElement attendingImporterNumber;

    /**************Delivering Tab inputs*************/
    //CountryCode dropdown
    @FindBy(id="drop-veh-list-mod-deliver-cntry-code")
    private WebElement deliveringCountryCode;

    //Brand dropdown
    @FindBy(id="drop-veh-list-mod-deliver-brand")
    private WebElement deliveringBrand;

    //partnerNumber  textbox
    @FindBy(id="inpt-veh-list-mod-deliver-partner-num")
    private WebElement deliveringPartnerNumber;

    //Wholesaler textbox
    @FindBy(id="inpt-veh-list-mod-deliver-wholesaler")
    private WebElement deliveringWholesaler;

    @FindBy(id="inf-veh-list-mod-deliver-importer")
    private WebElement deliveringImporterNum;

    /*********Ordering Tab Inputs*********/
    @FindBy(id="drop-veh-list-mod-order-cntry-code")
    private WebElement orderingCountryCode;

    @FindBy(id = "drop-veh-list-mod-order-brand")
    private WebElement orderingBrand;

    @FindBy(id="inpt-veh-list-mod-order-partner-num")
    private WebElement orderingPartnerNumber;

    @FindBy(id="inpt-veh-list-mod-order-wholesaler")
    private WebElement orderingWholesaler;

    // Error message below VIN input field in EL creation dialog
    @FindBy(xpath = "//span[contains(@class, 'error-msg')]")
    private WebElement vinerrormsg;

    // Carport button
    @FindBy(xpath = "//button[@class='btn fetch-carport-btn']")
    private WebElement buttonCarportFetchData;

    // VIN already exists error message
    @FindBy(xpath = "//div[contains(@class, 'alert alert-danger')]//div[.='This VIN already exist as an exception list vehicle.']")
    private WebElement vinexistserror;

    // PKN tab
    @FindBy(xpath = "//span[.='PKN']")
    private WebElement pkntab;

    // Close dialog
    @FindBy(xpath = "//button[@class='close']")
    private WebElement close;

    // Create Exception list dialog header
    @FindBy(xpath = "//h4[contains(text(), 'Add Vehicle to Exception-List')]")
    private WebElement addVehicleToExceptionListHeader;

    @FindBy(id="inf-veh-list-mod-order-importer")
    private WebElement orderingImporterNum;



    public void saveClick(WebElement element) {
        element.isDisplayed();
        element.isEnabled();
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        element.click();
    }
    public WebElement getPanelTitle() {
        return this.panelTitle;
    }

    public String getPanelTitleText() {
        return this.getPanelTitle().getText();
    }

    public WebElement getVin() {
        return this.vin;
    }

    public void vinInput(String vin) {
        this.getVin().sendKeys(vin);
    }

    public void clickPknTab() {
        this.saveClick(this.getPknTab());
    }

    public WebElement getTemplate(){
        return this.template;
    }

    public WebElement getTemplateSulzer(){
        return this.templateSulzer;
    }

    public WebElement getDealershipsTab() {
        return this.dealershipsTab;
    }
    //click on DealerShips Tab
    public void clickDealershipsTab() {
        this.saveClick(this.getDealershipsTab());
    }

    public WebElement getAttendingTab() {
        return this.attendingTab;
    }
    //click on Attending Tab
    public void clickAttendingTab() {
        this.saveClick(this.getAttendingTab());
    }

    public WebElement getDeliveringTab() {
        return this.deliveringTab;
    }
    //click on Delivering Tab
    public void clickDeliveringTab() {
        this.saveClick(this.getDeliveringTab());
    }

    public WebElement getOrderingTab() {
        return this.orderingTab;
    }

    // click on Ordering Tab
    public void clickOrderingTab() {
        this.saveClick(this.getOrderingTab());
    }

    public WebElement getMbv() {
        return this.mbv;
    }

    public void MbvInput(String mbv) {
        this.getMbv().sendKeys(mbv);
    }

    public void PrnInput(String prn) {
        this.getPrn().sendKeys(prn);
    }

    public WebElement getModelYear() {
        return this.modelYear;
    }

    public void ModelYearInput(String modelYear) {


        this.getModelYear().sendKeys(modelYear);

        WebElement we = this.webDriver.findElement(
                By.xpath(
                        "//p-autocomplete//div/ul/li/span[contains(text(), '" + modelYear + "')]"));

        we.click();

    }

    public void inputModelYear(String modelYear){
        this.getModelYear().clear();
        this.getModelYear().sendKeys(modelYear);
        this.webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public WebElement getCalculate() {
        return this.calculate;
    }

    public void ClickCalculateButton() {
        this.saveClick(this.getCalculate());
    }

    public WebElement getMbt() {
        return this.mbt;
    }

    public void MbtInput(String mbt) {
        this.getMbt().sendKeys(mbt);
    }

    public String getMbtText() {
        return this.getMbt().getAttribute("value");
    }

    public WebElement getComment() {
        return this.comment;
    }

    public void commentInput(String comment) {
        this.getComment().sendKeys(comment);
    }

    public WebElement getCalenderWeek() {
        return this.calenderWeek;
    }
    public void calenderWeekInput(String calenderWeek) {
        this.getCalenderWeek().sendKeys(calenderWeek);
    }


    public WebElement getPknLabel() {
        return cweeklabel;
    }

    public String pknLabelText() {
        return this.getPknLabel().getText();
    }

    public WebElement getPknDay() {
        return this.pkdnDay;
    }

    public void setPknDay(String day) {
        this.getPknDay().sendKeys(day);
    }

    public WebElement getPknFactory() {
        return this.pknFactory;
    }

    public void setPknFactory(String factory) {
        this.getPknFactory().sendKeys(factory);
    }

    public WebElement getPknYear() {
        return this.pknYear;
    }

    public void setPknYear(String year) {
        this.getPknYear().sendKeys(year);
    }

    public WebElement getCreate() {
        return create;
    }

    public WebElement getUpdate() {
        return update;
    }

    public void clickCreate() {
        this.getCreate().click();
    }

    public WebElement getCancel() {
        return this.cancel;
    }

    public void clickButtonCancelCreation() {
        this.getCancel().click();
    }

    public void addExceptionList(String vin, String comment) {

        this.vinInput(vin);
        this.commentInput(comment);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.clickCreate();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public WebElement getPknConcatenated() {
        return this.pknConcatenated;
    }

    public String getPknConcatenatedText() {
        return this.getPknConcatenated().getAttribute("value");
    }

    public WebElement getAttendingCountryCode() {
        return this.attendingCountryCode;
    }


    public void selectAttendingCountryCode(String attendingCountryCode) {
        Select atndCountryCode = new Select(getAttendingCountryCode());
        atndCountryCode.selectByVisibleText(attendingCountryCode);
    }


    public WebElement getAttendingBrand() {
        return this.attendingBrand;
    }

    public void attendingBrandInput(String  attendingBrand) {
        this.getAttendingBrand().sendKeys(attendingBrand);
    }

    public void selectAttendingBrand(String attendingBrand) {
        Select atndBrand = new Select(getAttendingBrand());
        atndBrand.selectByVisibleText(attendingBrand);
    }


    public WebElement getAttendingPartnerNumber() {
        return this.attendingPartnerNumber;
    }

    public void attendingPartnerNumberInput(String attendingPartnerNumber) {
        this.getAttendingPartnerNumber().sendKeys(attendingPartnerNumber);
    }


    public WebElement getAttendingWholesaler() {
        return this.attendingWholesaler;
    }

    public void attendingWholesalerInput(String attendingWholesaler) {
        this.getAttendingWholesaler().sendKeys(attendingWholesaler);
    }

    public WebElement getImporterNumber() {
        return this.attendingImporterNumber;
    }

    public String getImporterNumberText() {
        return this.getImporterNumber().getAttribute("value");
    }

    public WebElement getDeliveringCountryCode() {
        return this.deliveringCountryCode;
    }


    public void selectDeliverCountryCode(String deliveringCountryCode) {
        Select dlryCountryCode = new Select(getDeliveringCountryCode());
        dlryCountryCode.selectByVisibleText(deliveringCountryCode);
    }

    public WebElement getDeliveringBrand() {
        return this.deliveringBrand;
    }

    public void selectDelieveringBrand(String deliveringBrand) {
        Select dlryBrand = new Select(getDeliveringBrand());
        dlryBrand.selectByVisibleText(deliveringBrand);
    }
    public WebElement getDeliveringPartnerNumber() {
        return this.deliveringPartnerNumber;
    }

    public void deliveringPartnerNumInput(String deliveringPartnerNumber) {
        this.getDeliveringPartnerNumber().sendKeys(deliveringPartnerNumber);
    }

    public WebElement getDeliveringWholesaler() {
        return this.deliveringWholesaler;
    }

    public void deliveringWholesalerInput(String deliveringWholesaler) {
        this.getDeliveringWholesaler().sendKeys(deliveringWholesaler);
    }

    public WebElement getDeliveringImporterNum() {
        return this.deliveringImporterNum;
    }

    public String getDeliveringImporterNumText() {
        return this.getDeliveringImporterNum().getAttribute("value");
    }

    public WebElement getOrderingCountryCode() {
        return this.orderingCountryCode;
    }

    public void selectOrderingCountryCode(String orderingCountryCode) {
        Select ordCountryCode = new Select(getOrderingCountryCode());
        ordCountryCode.selectByVisibleText(orderingCountryCode);
    }

    public WebElement getOrderingBrand() {
        return this.orderingBrand;
    }

    public void selectOrderingBrand(String orderingBrand) {
        Select ordBrand = new Select(getOrderingBrand());
        ordBrand.selectByVisibleText(orderingBrand);
    }

    public WebElement getOrderingPartnerNumber() {
        return this.orderingPartnerNumber;
    }

    public void orderingPartnerNumInput(String orderingPartnerNumber) {
        this.getOrderingPartnerNumber().sendKeys(orderingPartnerNumber);
    }

    public WebElement getOrderingWholesaler() {
        return this.orderingWholesaler;
    }

    public void orderingWholesalerInput(String orderingWholesaler) {
        this.getOrderingWholesaler().sendKeys(orderingWholesaler);
    }

    public WebElement getVinErrorMsg() {
        return vinerrormsg;
    }

    public String vinErrorMsgText() {
        return this.getVinErrorMsg().getText();
    }

    public WebElement getButtonCarportFetch() {
        return this.buttonCarportFetchData;
    }

    public void clickButtonCarportFetch() {
        this.getButtonCarportFetch().click();
    }

    public WebElement getVinExistsError() {
        return vinexistserror;
    }

    public String vinAlreadyExistsError() {
        return this.getVinExistsError().getText();
    }

    public WebElement getPknTab() {
        return pkntab;
    }

    public WebElement closeDialog() {
        return close;
    }

    public WebElement getCreateElDialogHeader() {
        return addVehicleToExceptionListHeader;
    }

    public String createExceptionlistDialogHeader() {
        return this.getCreateElDialogHeader().getText();
    }

    public void enterVIN(String vin) {
        this.vinInput(vin);
    }

    public void fillGeneralSettingsTabData(String mbv, String prn, String modelYear, String mbt, String comment) {
        this.MbvInput(mbv);
        this.PrnInput(prn);
        this.ModelYearInput(modelYear);
        this.MbtInput(mbt);
        this.commentInput(comment);

    }
    public WebElement getOrderingImporterNum() {
        return this.orderingImporterNum;
    }

    public String getOrderingImporterNumText() {
        return this.getOrderingImporterNum().getAttribute("value");
    }
    public void fillDeliveringTabData(String deliveringCountryCode, String deliveringBrand, String deliveringPartnerNumber, String deliveringWholesaler) {
        this.selectDeliverCountryCode(deliveringCountryCode);
        this.selectDelieveringBrand(deliveringBrand);
        this.deliveringPartnerNumInput(deliveringPartnerNumber);
        this.deliveringWholesalerInput(deliveringWholesaler);
    }
    public void fillAttendingTabData(String attendingCountryCode, String attendingbrand, String attendingPartnerNumber, String attendingWholesaler) {
        this.selectAttendingCountryCode(attendingCountryCode);
        this.attendingBrandInput(attendingbrand);
        this.attendingPartnerNumberInput(attendingPartnerNumber);
        this.attendingWholesalerInput(attendingWholesaler);

    }
    public void fillOrderingTabData(String orderingCountryCode, String orderingBrand, String orderingPartnerNumber, String orderingWholesaler) {
        this.selectOrderingCountryCode(orderingCountryCode);
        this.selectOrderingBrand(orderingBrand);
        this.orderingPartnerNumInput(orderingPartnerNumber);
        this.orderingWholesalerInput(orderingWholesaler);
    }
    public void fillPKNTabData(String calenderWeek, String day, String factory, String year) {
        this.calenderWeekInput(calenderWeek);
        this.setPknDay(day);
        this.setPknFactory(factory);
        this.setPknYear(year);

    }

    public WebElement getErrorMessageModelYearOutOfRange(){
        return this.errorMessageModelYearOutOfRange;
    }

    public WebElement getPrn() {
        return prn;
    }

    public void chooseTemplateSulzer01(){

        ReusableMethods.clickElement(getTemplate(), 3, _driver);

        try {
            this.getTemplateSulzer().click();
        } catch (Exception e) {
            this.getTemplateSulzer().click();
        }

    }

}
