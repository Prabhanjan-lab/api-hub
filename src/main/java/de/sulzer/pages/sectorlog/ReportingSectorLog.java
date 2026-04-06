/**
 * 
 */
package de.sulzer.pages.sectorlog;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author GCH9F5D
 *
 */
public class ReportingSectorLog extends Page {

	@FindBy(xpath="//h3[@class='panel-title'][contains(text(), 'Log')]")
	private WebElement titlePageLoggingReporting;

	@FindBy(xpath="//div[@class='ui-datatable-tablewrapper']")
	private WebElement loggingReportingTable;

	@FindBy(xpath="//div[@class='panel panel-default']")
	private WebElement loggingReportingFilterPanel;
	
//	@FindBy(xpath="")
//	private WebElement ;

	public ReportingSectorLog(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the titlePageLoggingReporting
	 */
	public WebElement getTitlePageLoggingReporting() {
		return this.titlePageLoggingReporting;
	}

	/**
	 * @return the loggingReportingTable
	 */
	public WebElement getLoggingReportingTable() {
		return this.loggingReportingTable;
	}

	/**
	 * @return the loggingReportingFilterPanel
	 */
	public WebElement getLoggingReportingFilterPanel() {
		return this.loggingReportingFilterPanel;
	}

}
