/**
 *
 */
package de.sulzer.pages.testingmenu.recall;

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
public class ReCallCampaignDetailsPanel extends Page {

	@FindBy(xpath="//div[@class='input-sm form-control div-style']")
	private WebElement textFieldCampaignId;

	@FindAll(
		@FindBy(xpath="//country-release-details-table//p-datatable//div[@class='ui-datatable ui-widget ui-datatable-scrollable']//div[@class='ui-datatable-scrollable-wrapper ui-helper-clearfix']//div[@class='ui-datatable-scrollable-view']//div[@class='ui-datatable-scrollable-body']//tbody[@class='ui-datatable-data ui-widget-content']//tr/td[1]")
	)
	private List<WebElement> listApprovedCountryCodes;

	/**
	 *
	 */
	public ReCallCampaignDetailsPanel(WebDriver webDriver) {
		super(webDriver);
	}

	/**
	 * @return the textFieldCampaignId
	 */
	public WebElement getTextFieldCampaignId() {
		return textFieldCampaignId;
	}

	/**
	 * @return the listApprovedCountryCodes
	 */
	public List<WebElement> getListApprovedCountryCodes() {
		return listApprovedCountryCodes;
	}

}
