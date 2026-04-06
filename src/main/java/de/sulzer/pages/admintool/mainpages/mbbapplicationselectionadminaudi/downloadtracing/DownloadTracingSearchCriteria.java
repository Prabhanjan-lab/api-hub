/**
 *
 */
package de.sulzer.pages.admintool.mainpages.mbbapplicationselectionadminaudi.downloadtracing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Bege
 *
 */
public class DownloadTracingSearchCriteria extends Page {

	@FindBy(xpath="//select[@name='criteriasContainer:criterias:0:category']")
	WebElement selectCriteriumElement;

	private Select selectCriterium;

	@FindBy(xpath="//input[@name='criteriasContainer:criterias:0:value']")
	WebElement inputCriteriumValue;

	@FindBy(xpath="//input[@id='id6']")
	WebElement buttonNewCriterium;

	@FindBy(xpath="//select[@name='brand']")
	private WebElement selectBrand;

	@FindBy(xpath="//select[@name='country']")
	private WebElement selectCountryElement;

	private Select selectCountry;

	@FindBy(xpath="//input[@name='startDate:dateField']")
	private WebElement dateFieldStartDate;

	@FindBy(xpath="//span[@class='dateTimeFields']//img[@id='id8Icon']")
	private WebElement iconDateStart;

	@FindBy(xpath="//input[@name='startDate:hourField']")
	private WebElement hourFieldStartDate;

	@FindBy(xpath="//input[@name='startDate:minuteField']")
	private WebElement minuteFieldStartDate;

	@FindBy(xpath="//select[@name='startDate:timezones']")
	private WebElement selectTimeZoneStartDateElement;

	private Select selectTimeZoneStartDate;

	@FindBy(xpath="//input[@name='endDate:dateField']")
	private WebElement dateFieldEndDate;

//	@FindBy(xpath="//img[@id='idcIcon']")
	@FindBy(xpath="//span[@class='dateTimeFields']//img[@id='iddIcon']")
//	@FindBy(xpath="//span[@class='dateTimeFields']//img[@id='idcIcon']")
	private WebElement iconDateEnd;

	@FindBy(xpath="//input[@name='endDate:hourField']")
	private WebElement hourFieldEndDate;

	@FindBy(xpath="//input[@name='endDate:minuteField']")
	private WebElement minuteFieldEndDate;

	@FindBy(xpath="//select[@name='endDate:timezones']")
	private WebElement selectTimeZoneEndDateElement;

	private Select selectTimeZoneEndDate;

	@FindBy(xpath="//input[@id='id19']")
//	@FindBy(xpath="//input[@id='id17']")
	private WebElement buttonTraceDownload;

	@FindBy(xpath="//input[@class='logout']")
	private WebElement buttonLogout;

	/**
	 *
	 */
	public DownloadTracingSearchCriteria(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the buttonNewCriterium
	 */
	public WebElement getButtonNewCriterium() {
		return buttonNewCriterium;
	}

	public void clickButtonNewCriterium() {
		this.getButtonNewCriterium().click();
	}

	/**
	 * @return the inputCriteriumValue
	 */
	public WebElement getInputCriteriumValue() {
		return inputCriteriumValue;
	}

	public Select getSelectCriterium() {
		if (null == this.selectCriterium) {
			this.selectCriterium = new Select(this.selectCriteriumElement);
		}
		return selectCriterium;
	}

	public void selectCriteriumElementByName(String domainCode) {
		this.getSelectCriterium().selectByValue(domainCode);
	}

	public void selectCriteriumElementByIndex(Integer index) {
		this.getSelectCriterium().selectByIndex(index);
	}

	/**
	 * @return the selectBrand
	 */
	public WebElement getSelectBrand() {
		return selectBrand;
	}

//	/**
//	 * @return the selectCountry
//	 */
//	public WebElement getSelectCountry() {
//		return selectCountry;
//	}

	public Select getSelectCountry() {
		if (null == this.selectCountry) {
			this.selectCountry = new Select(this.selectCountryElement);
		}
		return selectCountry;
	}

	public void selectCountryElementByName(String domainCode) {
		this.getSelectCountry().selectByValue(domainCode);
	}

	public void selectCountryElementByIndex(Integer index) {
		this.getSelectCountry().selectByIndex(index);
	}

	/**
	 * @return the dateFieldStartDate
	 */
	public WebElement getDateFieldStartDate() {
		return dateFieldStartDate;
	}

	/**
	 * @return the iconDateStart
	 */
	public WebElement getIconDateStart() {
		return iconDateStart;
	}

	public void clickIconDateStart() {
		this.getIconDateStart().click();
	}

	/**
	 * @return the hourFieldStartDate
	 */
	public WebElement getHourFieldStartDate() {
		return hourFieldStartDate;
	}

	/**
	 * @return the minuteFieldStartDate
	 */
	public WebElement getMinuteFieldStartDate() {
		return minuteFieldStartDate;
	}

//	/**
//	 * @return the timeZoneStartDate
//	 */
//	public WebElement getSelectTimeZoneStartDateElement() {
//		return selectTimeZoneStartDateElement;
//	}

	public Select getSelectTimeZoneStartDateElement() {
		if (null == this.selectTimeZoneStartDate) {
			this.selectTimeZoneStartDate = new Select(this.selectTimeZoneStartDateElement);
		}
		return selectTimeZoneStartDate;
	}

	public void selectTimeZoneStartDateElementByName(String domainCode) {
		this.getSelectTimeZoneStartDateElement().selectByValue(domainCode);
	}

	public void selectTimeZoneStartDateElementByIndex(Integer index) {
		this.getSelectTimeZoneStartDateElement().selectByIndex(index);
	}

	/**
	 * @return the dateFieldEndDate
	 */
	public WebElement getDateFieldEndDate() {
		return dateFieldEndDate;
	}

	/**
	 * @return the iconDateEnde
	 */
	public WebElement getIconDateEnd() {
		return iconDateEnd;
	}

	public void clickIconDateEnd() {
		this.getIconDateEnd().click();
	}

	/**
	 * @return the hourFieldEndDate
	 */
	public WebElement getHourFieldEndDate() {
		return hourFieldEndDate;
	}

	/**
	 * @return the minuteFieldEndDate
	 */
	public WebElement getMinuteFieldEndDate() {
		return minuteFieldEndDate;
	}

//	/**
//	 * @return the timeZoneEndDate
//	 */
//	public WebElement getSelectTimeZoneEndDateElement() {
//		return selectTimeZoneEndDateElement;
//	}

	public Select getSelectTimeZoneEndDateElement() {
		if (null == this.selectTimeZoneEndDate) {
			this.selectTimeZoneEndDate = new Select(this.selectTimeZoneEndDateElement);
		}
		return selectTimeZoneEndDate;
	}

	public void selectTimeZoneEndDateElementByName(String domainCode) {
		this.getSelectTimeZoneEndDateElement().selectByValue(domainCode);
	}

	public void selectTimeZoneEndDateElementByIndex(Integer index) {
		this.getSelectTimeZoneEndDateElement().selectByIndex(index);
	}

	/**
	 * @return the buttonTraceDownload
	 */
	public WebElement getButtonTraceDownload() {
		return buttonTraceDownload;
	}

	public void clickButtonTraceDownload() {
		this.getButtonTraceDownload().click();
	}

	/**
	 * @return the buttonLogout
	 */
	public WebElement getButtonLogout() {
		return buttonLogout;
	}

	public void clickButtonLogout() {
		this.getButtonLogout().click();
	}

}
