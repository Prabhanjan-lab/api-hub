/**
 *
 */
package de.sulzer.pages.configurationlist;

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
public class ConfigurationDialogueDNDAMappingMIB extends Page {

    @FindBy(xpath="//h4[@class='modal-title pull-left']")
    private WebElement titleConfigurationDialogueDNDAMapping;

    public static final String TITLE = "Device Name and Diagnostic Address mapping look-up table for MIB2plus and MIB3";

    @FindBy(xpath="//button[@id='create-da-mapping-btn']")
    private WebElement buttonCreateDNDAMapping;

    @FindBy(xpath="//button[@class='btn btn-default']")
    private WebElement buttonCloseDialogue;

    @FindBy(xpath="//button[contains(text(),'Close')]")
    private WebElement buttonCloseModal;

    public WebElement getButtonCloseModal() {
        return buttonCloseModal;
    }

    @FindAll({
        @FindBy(xpath="//table//thead//tr//th")
    })
    private List<WebElement> listTableHeader;

//    public static final String LISTTABLEROWS = "//tbody[@class='ui-table-tbody']//tr";
    public static final String LISTTABLEROWS = "//tbody[@class='p-element p-datatable-tbody']//tr";

    @FindAll({
        @FindBy(xpath=LISTTABLEROWS)
    })
    private List<WebElement> listTableRows;

    public ConfigurationDialogueDNDAMappingMIB(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the titleConfigurationDialogueDNDAMapping
     */
    public WebElement getTitleConfigurationDialogueDNDAMapping() {
        return titleConfigurationDialogueDNDAMapping;
    }

    /**
     * @return the buttonCreateDNDAMapping
     */
    public WebElement getButtonCreateDNDAMapping() {
        return buttonCreateDNDAMapping;
    }

    public void clickButtonCreateDNDAMapping() {
        this.getButtonCreateDNDAMapping().click();
    }

    /**
     * @return the buttonCloseDialogue
     */
    public WebElement getButtonCloseDialogue() {
        return buttonCloseDialogue;
    }

    public void clickButtonCloseDialogue() {
        this.getButtonCloseDialogue().click();
    }

    /**
     * @return the listTableHeader
     */
    public List<WebElement> getListTableHeader() {
        return listTableHeader;
    }

    /**
     * @return the listTableRows
     */
    public List<WebElement> getListTableRows() {
        return listTableRows;
    }

    public boolean checkTableHeaders(String[] headers) {

        boolean result = true;

        List<WebElement> elements = this.getListTableHeader();

        for (int i = 0; i < elements.size(); i++) {
            if (! elements.get(i).getText().trim().equals(headers[i])) {
                result = false;
                break;
            }
        }

        return result;
    }

    public String getFirstRowDeviceName() {
        return this.getListTableRows().get(0).findElement(By.xpath("//td[1]")).getText().trim();
    }

    public WebElement getRowOfEntry (String devicenameValid) {

        List<WebElement> elements = this.getListTableRows();

        WebElement element = null;

        // find element
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getText().trim().contains(devicenameValid)) {
                element = elements.get(i);
                break;
            }
        }

        return element;

    }

    public void clickButtonCloseModal() {
        this.getButtonCloseModal().click();
    }
}
