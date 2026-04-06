/**
 *
 */
package de.sulzer.pages.documentation;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class DocumentationCarPort extends Page {

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
    private List<WebElement> listCarPortEntries;

    /**
     * @param driver
     */
    public DocumentationCarPort(WebDriver driver) {
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
    public List<WebElement> getListCarPortEntries() {
        return listCarPortEntries;
    }

    public void clickCheckBoxTableRowFirstRow() {

        List<WebElement> entries = this.getListCarPortEntries();

        this.clickCheckBoxTableRow(entries.get(0));

    }

    private void clickCheckBoxTableRow(WebElement webElement) {

        WebElement checkbox = webElement.findElement(By.xpath(".//input[@type='checkbox']"));

        checkbox.click();

    }

}
