/**
 *
 */
package de.sulzer.pages.campaignmanagement.inbox.popUpDialogues;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class DialogueAnalysisPackaging extends Page {

    @FindBy(xpath="//div[@class='modal-content']//div[@class='modal-header']/h4")
    private WebElement headerDialogueAnalysisPackaging;

    @FindBy(xpath="//button[@class='close']")
    private WebElement closeDialogueXIcon;

    @FindBy(xpath="//div[@class='modal-body']/p-table//table/thead/tr/th[1]")
    private WebElement tableHeaderTime;

    @FindBy(xpath="//div[@class='modal-body']/p-table//table/thead/tr/th[2]")
    private WebElement tableHeaderDescription;

    @FindBy(xpath="//div[@class='modal-body']/p-table//table/thead/tr/th[3]")
    private WebElement tableHeaderFileNumber;

    @FindBy(xpath="//div[@class='modal-body']/p-table//table/thead/tr/th[4]")
    private WebElement tableHeaderFileName;

    @FindBy(xpath="//div[@class='modal-body']/p-table//table/thead/tr/th[5]")
    private WebElement tableHeaderFileSizeUncompressed;

    @FindBy(xpath="//div[@class='modal-body']/p-table//table/thead/tr/th[7]")
    private WebElement tableHeaderFileCompressed;

    @FindBy(xpath="//div[@class='modal-body']/p-table//table/thead/tr/th[8]")
    private WebElement tableHeaderProcessingRate;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']/p-table//table/tbody/tr/td[1]")
    )
    private List<WebElement> listColumnElementsTime;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']/p-table//table/tbody/tr/td[2]")
    )
    private List<WebElement> listColumnElementsDescription;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']/p-table//table/tbody/tr/td[3]")
    )
    private List<WebElement> listColumnElementsFileNumber;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']/p-table//table/tbody/tr/td[4]")
    )
    private List<WebElement> listColumnElementsFileName;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']/p-table//table/tbody/tr/td[5]")
    )
    private List<WebElement> listColumnElementsSizeUncompressed;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']/p-table//table/tbody/tr/td[7]")
    )
    private List<WebElement> listColumnElementsSizeCompressed;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']/p-table//table/tbody/tr/td[8]")
    )
    private List<WebElement> listColumnElementsProcessingRate;

    public DialogueAnalysisPackaging(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the headerDialogueAnalysisPackaging
     */
    public WebElement getHeaderDialogueAnalysisPackaging() {
        return headerDialogueAnalysisPackaging;
    }

    /**
     * @return the closeDialogueXIcon
     */
    public WebElement getCloseDialogueXIcon() {
        return closeDialogueXIcon;
    }

    public void clickCloseDialogueXIcon() {
        this.getCloseDialogueXIcon().click();
    }

    /**
     * @return the tableHeaderTime
     */
    public WebElement getTableHeaderTime() {
        return tableHeaderTime;
    }

    /**
     * @return the tableHeaderDescription
     */
    public WebElement getTableHeaderDescription() {
        return tableHeaderDescription;
    }

    /**
     * @return the tableHeaderFileNumber
     */
    public WebElement getTableHeaderFileNumber() {
        return tableHeaderFileNumber;
    }

    /**
     * @return the tableHeaderFileName
     */
    public WebElement getTableHeaderFileName() {
        return tableHeaderFileName;
    }

    /**
     * @return the tableHeaderFileSizeUncompressed
     */
    public WebElement getTableHeaderFileSizeUncompressed() {
        return tableHeaderFileSizeUncompressed;
    }

    /**
     * @return the tableHeaderFileCompressed
     */
    public WebElement getTableHeaderFileCompressed() {
        return tableHeaderFileCompressed;
    }

    /**
     * @return the tableHeaderProcessingRate
     */
    public WebElement getTableHeaderProcessingRate() {
        return tableHeaderProcessingRate;
    }

    /**
     * @return the listTimeColumnElements
     */
    public List<WebElement> getListColumnElementsTime() {
        return listColumnElementsTime;
    }

    /**
     * @return the listColumnElementsDescription
     */
    public List<WebElement> getListColumnElementsDescription() {
        return listColumnElementsDescription;
    }

    /**
     * @return the listColumnElementsFileNumber
     */
    public List<WebElement> getListColumnElementsFileNumber() {
        return this.listColumnElementsFileNumber;
    }

    /**
     * @return the listColumnElementsFileName
     */
    public List<WebElement> getListColumnElementsFileName() {
        return this.listColumnElementsFileName;
    }

    /**
     * @return the listColumnElementsSizeUncompressed
     */
    public List<WebElement> getListColumnElementsSizeUncompressed() {
        return listColumnElementsSizeUncompressed;
    }

    /**
     * @return the listColumnElementsSizeCompressed
     */
    public List<WebElement> getListColumnElementsSizeCompressed() {
        return listColumnElementsSizeCompressed;
    }

    /**
     * @return the listColumnElementsSizeCompressed
     */
    public List<WebElement> getListColumnElementsProcessingRate() {
        return listColumnElementsProcessingRate;
    }

}
