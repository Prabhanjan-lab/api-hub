/**
 *
 */
package de.sulzer.pages.documentation;

import java.util.ArrayList;
import java.util.List;

import de.sulzer.utils.WebElementWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;
import testFramework.utilities.ReusableMethods;


/**
 * @author Sulzer GmbH
 *
 */
public class DocumentationReCall extends Page {

    private WebDriver driver;

    @FindBy(xpath="//button[contains(@class, 'btn btn-primary')][contains(text(), 'Retry')]")
    private WebElement buttonRetry;

    @FindBy(xpath="//button[contains(@class, 'btn btn-primary')][contains(text(), 'Set status to successful')]")
    private WebElement buttonSetStatusToSuccessful;

    @FindBy(xpath="//label[@class='checkbox-inline']/input[@type='checkbox']")
    private WebElement checkBoxErrorsOnly;

    @FindBy(xpath="//label/input[@type='text'][@placeholder='Global filter']")
    private WebElement inputTextFilter;

    @FindBy(xpath="//span[@class='ui-column-title']//input[@type='checkbox']")
    private WebElement checkBoxSelectAllTableRows;

    @FindAll({
        @FindBy(xpath = "//tbody//tr")
    })
    private List<WebElement> listReCallEntries;

    @FindBy(xpath = "//*[.=\" Details \"]")
    private WebElement detailsHeader;

    @FindAll({
            @FindBy(css = "tbody >tr>td")
    })
    private List<WebElement> detailsColumn;

    /**
     * @param driver
     */
    public DocumentationReCall(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the buttonRetry
     */
    public WebElement getButtonRetry() {
        return buttonRetry;
    }

    public void clickButtonRetry() {
        this.getButtonRetry().click();
    }

    /**
     * @return the buttonSetStatusToSuccessful
     */
    public WebElement getButtonSetStatusToSuccessful() {
        return buttonSetStatusToSuccessful;
    }

    public void clickButtonSetStatusToSuccessful() {
        this.getButtonSetStatusToSuccessful().click();
    }

    /**
     * @return the checkBoxErrorsOnly
     */
    public WebElement getCheckBoxErrorsOnly() {
        return checkBoxErrorsOnly;
    }

    public void clickCheckBoxErrorsOnly() {
        this.getCheckBoxErrorsOnly().click();
    }

    /**
     * @return the inputTextFilter
     */
    public WebElement getInputTextFilter() {
        return inputTextFilter;
    }

    public void setTextAreaVin(String input) {
        this.getInputTextFilter().clear();
        this.getInputTextFilter().sendKeys(input);
    }

    /**
     * @return the checkBoxSelectAllTableRows
     */
    public WebElement getCheckBoxSelectAllTableRows() {
        return checkBoxSelectAllTableRows;
    }

    public void clickCheckBoxSelectAllTableRows() {
        this.getCheckBoxSelectAllTableRows().click();
    }

    /**
     * @return the listCarPortEntries
     */
    public List<WebElement> getListReCallEntries() {
        return listReCallEntries;
    }

    public void clickCheckBoxTableRowFirstRow() {

        List<WebElement> entries = this.getListReCallEntries();

        this.clickCheckBoxTableRow(entries.get(0));

    }

    private void clickCheckBoxTableRow(WebElement webElement) {

        WebElement checkbox = webElement.findElement(By.xpath(".//input[@type='checkbox']"));
        checkbox.click();
    }


    /**
     * @return the detailsHeader
     */
    public WebElement getDetailsHeader() {
        return detailsHeader;
    }


    /**
     * @return the ListDetailsColumn
     */
    public List<WebElement> getDetailsColumn() {
        return detailsColumn;
    }


    /**
     * @return the sorted
     */
    public boolean isSorted(List<WebElement> detailsColumn, WebDriver driver) {

        WebElementWait webElementWait = new WebElementWait(driver);
        getDetailsHeader().click();

        List<String> elemText = new ArrayList<>();
        for (WebElement i : detailsColumn) {
            webElementWait.waitForElement(i);
            elemText.add(i.getText());
        }

        boolean sorted = false;

        for ( int i = 1; i < elemText.size() - 1 ; i++){

            if (elemText.get(i).equals(" ")){
                break;
            }

            if (elemText.get(i).compareToIgnoreCase(elemText.get(i+1)) <= 0){
                sorted = true;
            } else {
                sorted = false;
                break;
            }
        }
        return sorted;
    }

}
