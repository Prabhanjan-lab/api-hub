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
 * @author Bege
 *
 */
public class DialogueReCallCampaignDetails extends Page {

    private static final String NON_EDITABLE_TEXT = "ng-reflect-model";

    @FindBy(xpath="//div[contains(@class, 'modal-dialog')]//div[contains(@class, 'modal-header')]//h4[contains(@class, 'modal-title')]")
    private WebElement headerDialogueReCallCampaignDetails;

    @FindBy(xpath="//div/div/input[contains(@class, 'form-control')][@name='campaignNumber']")
    private WebElement textCampaignNumber;

    @FindBy(xpath="//div/div/input[contains(@class, 'form-control')][@name='campaignTitle']")
    private WebElement textCampaignTitle;

    @FindBy(xpath="//div//input[@name='totalVinsNumber']")
    private WebElement textFieldTotalVins;

    @FindBy(xpath="//div/div/input[contains(@class, 'form-control')][@name='brand']")
    private WebElement textBrand;

    @FindAll({
        @FindBy(xpath="//table[@id='recallCriterionsTable']/tbody/tr")
    })
    private List<WebElement> tableCriterionInfoCriterias;

    @FindAll({
        @FindBy(xpath="//table[@id='recallCriterionsTable']/tbody/tr/td[5]")
    })
    private List<WebElement> tableCriterionInfoNumberOfVins;

    @FindAll({
        @FindBy(xpath="//table[@id='recallCriterionsTable']/tbody/tr/td[6]/button")
    })
    private List<WebElement> tableButtonCriterionInfoDetailsCountryInformation;

    @FindBy(xpath="//recall-detail-modal//button[@class='btn btn-primary']")
    private WebElement buttonClose;

    /**
     *
     */
    public DialogueReCallCampaignDetails(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the headerDialogueReCallCampaignDetails
     */
    public WebElement getHeaderDialogueReCallCampaignDetails() {
        return headerDialogueReCallCampaignDetails;
    }

    public WebElement getCampaignNumber() {
        return this.textCampaignNumber;
    }

    public String getTextCampaignNumber() {
        return textCampaignNumber.getAttribute("value").trim();
    }

    public String getTextCampaignTitle() {
        return textCampaignTitle.getAttribute("value").trim();
    }

    /**
     * @return the textFieldTotalVins
     */
    public String getTextFieldTotalVins() {
        return textFieldTotalVins.getAttribute("value").trim();
    }

    public String getTextFieldTotalVinsValue() {
        return textFieldTotalVins.getText().trim();
    }

    public String getTextBrand() {
        return textBrand.getAttribute("value").trim();
    }

    /**
     * @return the tabelCriterionInfoCriterias
     */
    public List<WebElement> getTableCriterionInfoCriterias() {
        return tableCriterionInfoCriterias;
    }

    /**
     * @return the tabelCriterionInfoNumberOfVins
     */
    public List<WebElement> getTableCriterionInfoNumberOfVins() {
        return tableCriterionInfoNumberOfVins;
    }

    /**
     * @return the tableButtonCriterionInfoDetailsCountryInformation
     */
    public List<WebElement> getTableButtonCriterionInfoDetailsCountryInformation() {
        return tableButtonCriterionInfoDetailsCountryInformation;
    }

    /**
     * @return the buttonClose
     */
    public WebElement getButtonClose() {
        return buttonClose;
    }

    public void clickButtonClose() {
        this.getButtonClose().click();
    }

}
