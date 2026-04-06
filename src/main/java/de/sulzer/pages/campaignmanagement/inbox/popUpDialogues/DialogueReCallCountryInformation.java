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
public class DialogueReCallCountryInformation extends Page {

    @FindBy(xpath="//div[contains(@class, 'modal-dialog')]//div[contains(@class, 'modal-header')]//h4[contains(@class, 'modal-title')]")
    private WebElement headerDialogueReCallCampaignDetailsCountryInformation;

    @FindAll({
        @FindBy(xpath="//div[@class='modal-body']//p-table[2]//tbody/tr/td[1]")
//		@FindBy(xpath="//recall-country-information-modal//div[@class='modal-body']//p-datatable[2]//tbody/tr/td[1]")
    })
    private List<WebElement> tableCriterionCountryInformationCountryCodes;

    @FindBy(xpath="//recall-country-information-modal//button[@class='btn btn-primary']")
    private WebElement buttonClose;

    /**
     *
     */
    public DialogueReCallCountryInformation(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the headerDialogueReCallCampaignDetailsCountryInformation
     */
    public WebElement getHeaderDialogueReCallCampaignDetailsCountryInformation() {
        return headerDialogueReCallCampaignDetailsCountryInformation;
    }

    /**
     * @return the tableCriterionCountryInformationCountryCodes
     */
    public List<WebElement> getTableCriterionCountryInformationCountryCodes() {
        return tableCriterionCountryInformationCountryCodes;
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
