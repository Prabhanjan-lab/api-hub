package de.sulzer.pages.vehicleadministration.exceptionlistmanagement;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.PageWithNavigation;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import testFramework.utilities.ReusableMethods;

public class ExceptionListOverviewPage extends PageWithNavigation {

    protected WebDriver _driver;

    public ExceptionListOverviewPage(WebDriver driver) {
        super(driver);
        _driver = driver;
    }

    // get exception list panel title
    @FindBy(xpath = "//div/h3[.='Exception-List-Management ']")
    private WebElement panelTitle;

    // search exception list text box
    @FindBy(xpath = "//input[@placeholder='Global Filter']")
    private WebElement searchExceptionListfield;

    // whole table
    @FindBy(xpath = "//table//tbody")
    private WebElement table;

    @FindAll(
            @FindBy(xpath = "//table/tbody/tr")
    )
    private List<WebElement> rowsExeptionElements;

    // MBT column of first table row (only useful after searching for certain VIN)
    @FindBy(xpath = "//table//tbody//tr[1]//td[3]")
    private WebElement mbt;

    // PKN column of first table row (only useful after searching for certain VIN)
    @FindBy(xpath = "//table//tbody//tr[1]//td[4]")
    private WebElement pkn;

    // Importer column of first table row (only useful after searching for certain VIN)
    @FindBy(xpath = "//table//tbody//tr[1]//td[5]")
    private WebElement importer;

    // Effective country column of first table row (only useful after searching for certain VIN)
    @FindBy(xpath = "//table//tbody//tr[1]//td[8]")
    private WebElement effectiveCountry;

    // add exception list button
    @FindBy(id = "btn-veh-except-list-create")
    private WebElement addExceptionListButton;

    @FindBy(xpath = "//number-of-entries")
    private WebElement numberOfEntriesParagraph;

    // Exception list edit (this is only for editing first and only table row entry)
    @FindBy(xpath = "//button[@class='btn btn-default btn-xs']")
    private WebElement editExceptionList;

    // Exception list delete
    @FindAll(
            @FindBy(xpath = "//button[@class='btn btn-danger btn-xs']")
    )
    private List<WebElement> deleteExceptionList;

    // confirm delete
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement confirmDeletion;

    @FindBy(id = "btn-del-confirm-confirm")
    private WebElement delYesButton;

    @FindBy(id = "inf-veh-except-list-tbl-data-empty")
    private List<WebElement> noRecordsFound;




    public List<WebElement> getNoRecordsFound() {
        return noRecordsFound;
    }

    public WebElement getPanelTitle() {
        return this.panelTitle;
    }

    public String getPanelTitleText() {
        return this.getPanelTitle().getText().trim();
    }

    public WebElement getSearchExceptionListfield() {
        return this.searchExceptionListfield;
    }

    public WebElement getDelYesButton() {
        return delYesButton;
    }

    /**
     * @return the table
     */
    public WebElement getTable() {
        return table;
    }

    /**
     * @return the rowsExecptionElements
     */
    public List<WebElement> getRowsExceptionElements() {
        return rowsExeptionElements;
    }

    /**
     * @return the mbt
     */
    public WebElement getMbt() {
        return this.mbt;
    }

    /**
     * @return the pkn
     */
    public WebElement getPkn() {
        return this.pkn;
    }

    /**
     * @return the importer
     */
    public WebElement getImporter() {
        return this.importer;
    }


    /**
     * @return the effectiveCountry
     */
    public WebElement getEffectiveCountry() {
        return effectiveCountry;
    }

    public WebElement getAddExceptionListButton() {
        return addExceptionListButton;
    }

    public void clickAddExceptionListButton() {
        this.getAddExceptionListButton().click();
    }

    public boolean verifyAddExceptionListButtonEnabled() {
        return addExceptionListButton.isEnabled();
    }

    public WebElement getNumberOfEntriesParagraph() {
        return numberOfEntriesParagraph;
    }

    public String getNumberOfEntriesParagraphText() {
        return this.getNumberOfEntriesParagraph().getText().trim();
    }

    public WebElement getEditExceptionList() {
        return editExceptionList;
    }

    public void clickEditExceptionList() {
        this.saveClick(this.getEditExceptionList());
    }

    public WebElement getConfirmDeletion() {
        return confirmDeletion;
    }

    public List<WebElement> getDeleteExceptionList() {
        return deleteExceptionList;
    }


    // The method is rewritten
    public void searchExceptionListItem(String input) {

        ReusableMethods.waitForClickablility(getTable(), 10, _driver);
        ReusableMethods.clearField(getSearchExceptionListfield());
        this.getSearchExceptionListfield().sendKeys(Keys.ENTER + input);

        boolean isPresent = false;

        try {
            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("//table/tbody/tr/td[.=' " + input + " ']")), 10, _driver);
            isPresent = _driver.findElement(By.xpath("//table/tbody/tr/td[.=' " + input + " ']")).isDisplayed();
        } catch (Exception ignored) {
        }

        if (isPresent) {
            System.out.println("VIN :" + input + " is present");
        } else System.out.println("VIN :" + input + " is not available.");

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    // TODO This method can be improved.
    public void searchExceptionList(String input) {

        ReusableMethods.waitForClickablility(getTable(), 10, _driver);


        ReusableMethods.clearField(getSearchExceptionListfield());

        this.getSearchExceptionListfield().sendKeys(input);
        this.getSearchExceptionListfield().sendKeys(Keys.ENTER);

        try {
            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("//table/tbody/tr/td[.=' " + input + " ']")), 10, _driver);
        } catch (RuntimeException ex) {
            Logger.getLogger(ManualService42SystemPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i3 = 0;
        int f = 5;
        while (i3 == 0 && f > 0) {

            String numberOfEntriesText = this.getNumberOfEntriesParagraph().getText();
            System.out.println(numberOfEntriesText);
            if (numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
                i3 = 1;
            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            f--;
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void clickDeleteExceptionList() {
//        this.saveClick(this.getDeleteExceptionList().get(0));
        getDelFirstExeptionList();
    }

    public void getDelFirstExeptionList() {

        getDeleteExceptionList().get(0).click();
        clickConfirmDeletion();
    }

    public void clickConfirmDeletion() {

//        this.getConfirmDeletion().click();
        getDelYesButton().click();
        ReusableMethods.checkTime(275);
//        try {
//            ReusableMethods.waitForInvisibilityOf(getConfirmDeletion(), 4, _driver);
//            ReusableMethods.checkTime(278);
//        } catch (RuntimeException ignored) {
//        }
//
//        boolean isPresent = false;
//
//        try {
//            isPresent = getConfirmDeletion().isDisplayed();
//            ReusableMethods.checkTime(286);
//        } catch (Exception ignored) {
//        }
//
//        if (isPresent) {
//            System.out.println("CarPort update failed.");
//        } else System.out.println("The vin was successfully deleted!");
    }

    public void vinSearchAndDelete(String vin){

        ReusableMethods.waitForClickablility(getTable(), 10, _driver);
        ReusableMethods.clearField(getSearchExceptionListfield());
        getSearchExceptionListfield().sendKeys(vin);
        ReusableMethods.checkTime(301);

//        if (getNoRecordsFound().size() > 0){
//            ReusableMethods.checkTime(310);
//        }else {
//            ReusableMethods.checkTime(312);
//            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("//table/tbody/tr/td[.=' " + vin + " ']")), 10, _driver);
//            clickDeleteExceptionList();
//        }
//        ReusableMethods.checkTime(316);

        boolean isPresent = false;

        try {
            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("//table/tbody/tr/td[.=' " + vin + " ']")), 10, _driver);
            isPresent = _driver.findElement(By.xpath("//table/tbody/tr/td[.=' " + vin + " ']")).isDisplayed();
        } catch (Exception ignored) {
        }

        if (isPresent) {
            ReusableMethods.checkTime(312);
//            ReusableMethods.waitForVisibility(_driver.findElement(By.xpath("//table/tbody/tr/td[.=' " + vin + " ']")), 10, _driver);
            clickDeleteExceptionList();
        } else System.out.println("VIN :" + vin + " is not available.");

    }

}
