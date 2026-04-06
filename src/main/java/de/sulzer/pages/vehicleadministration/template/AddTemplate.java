package de.sulzer.pages.vehicleadministration.template;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddTemplate extends TemplateOverviewPage {

	public AddTemplate(WebDriver driver) {
		super(driver);
	}
	
	public void saveClick(WebElement element){
		element.isDisplayed();
		element.isEnabled();
		try{
			Thread.sleep(2000);
		}
		catch (Exception e){
			throw new RuntimeException();
		}
		element.click();
	}

	// Template name text box
	@FindBy(id = "name")
	private WebElement templateName;

	public WebElement templateName() {
		return templateName;
	}

	public void templateName(String tempName) {
		this.templateName().sendKeys(tempName);
	}

	// MBV text box
	@FindBy(id = "mbv")
	private WebElement mbvTextbox;

	public WebElement mbv() {
		return mbvTextbox;
	}

	public void mbv(String mbv) {
		this.mbv().sendKeys(mbv);
	}

	// PRN text box
	@FindBy(id = "prNumbers")
	private WebElement prnTextbox;

	public WebElement prNumbers() {
		return prnTextbox;
	}

	public void prNumbers(String prn) {
		this.prNumbers().sendKeys(prn);
	}

	// TODO: model year

	// MBT Text box
	@FindBy(id = "mbt")
	private WebElement mbtTextbox;

	public WebElement mbt() {
		return this.mbtTextbox;
	}

	public void mbt(String mbt) {
		this.mbt().sendKeys(mbt);
	}

	// comment text box
	@FindBy(id = "comment")
	private WebElement commentTextbox;

	public WebElement comment() {
		return commentTextbox;
	}

	public void comment(String comment) {
		this.comment().sendKeys(comment);
	}

	// PKN tab
	@FindBy(xpath = "//span[.='PKN']")
	private WebElement pknTab;

	public WebElement pknTab() {
		return pknTab;
	}

	public void clickPknTab() {
		this.pknTab().click();
	}

	// Calendar week text box
	@FindBy(id = "pknWeek")
	private WebElement calendarWeekTextbox;

	public WebElement calendarWeekTextbox() {
		return calendarWeekTextbox;
	}

	public void calendarWeek(String week) {
		this.calendarWeekTextbox().sendKeys(week);
	}

	// PKN day text box
	@FindBy(id = "pknDay")
	private WebElement pknDayTextbox;

	public WebElement pknDayTextbox() {
		return pknDayTextbox;
	}

	public void pknDay(String day) {
		this.pknDayTextbox().sendKeys(day);
	}

	// PKN Factory text box
	@FindBy(id = "pknFactory")
	private WebElement pknFactoryTextbox;

	public WebElement pknFactoryTextbox() {
		return pknFactoryTextbox;
	}

	public void pknFactory(String factory) {
		this.pknFactoryTextbox().sendKeys(factory);
	}

	// PKN Factory text box
	@FindBy(id = "pknYear")
	private WebElement pknYearTextbox;

	public WebElement pknYearTextbox() {
		return pknYearTextbox;
	}

	public void pknYear(String year) {
		this.pknYearTextbox().sendKeys(year);
	}

	// DealerShips tab
	@FindBy(xpath = "//span[.='Dealerships']")
	private WebElement dealerShipsTab;

	public WebElement dealerShipsTab() {
		return dealerShipsTab;
	}

	public void clickDealerShipsTab() {
		this.dealerShipsTab().click();
	}

	// Attending tab
	@FindBy(xpath = "//span[.='Attending']")
	private WebElement attendingTab;

	public WebElement attendingTab() {
		return attendingTab;
	}

	public void clickAttendingTab() {
		this.attendingTab().click();
	}
	
	// Delivering tab
	@FindBy(xpath = "//span[.='Delivering']")
	private WebElement deliveringTab;

	public WebElement deliveringTab() {
		return deliveringTab;
	}

	public void clickDeliveringTab() {
		this.deliveringTab().click();
	}
	
	// Ordering tab
	@FindBy(xpath = "//span[.='Ordering']")
	private WebElement orderingTab;

	public WebElement orderingTab() {
		return orderingTab;
	}

	public void clickOrderingTab() {
		this.orderingTab().click();
	}

	// Create button
	@FindBy(id = "submit-save")
	private WebElement create;

	public WebElement getcreate() {
		return create;
	}
	
	public void clickCreate() {
		this.saveClick(this.getcreate());
	}
}
