package de.sulzer.pages.caradministration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.PageWithNavigation;
import testFramework.utilities.ReusableMethods;

public class VehicleOverviewPage extends PageWithNavigation{

	public VehicleOverviewPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h3[.= 'Vehicle Overview']")
	private WebElement vehicleOverviewHeading;
	
	@FindBy(id="inpt-veh-search-vin")
	private WebElement vinInputField;
	
	@FindBy(xpath="//button[@type='submit']")
	private WebElement okButton;
	
	public WebElement getVehicleOverviewHeading() {
		return this.vehicleOverviewHeading;
	}
	
	public WebElement getVinInputField() {
		return this.vinInputField;
	}
	
	public void setVinInputField(String vin) {
		ReusableMethods.waitForClickablility(vinInputField, 3, _driver);
		this.vinInputField.sendKeys(vin);
	}
	
	public WebElement getOkButton() {
		return this.okButton;
	}
	
	public void clickOkButton() {
		this.okButton.click();
	}
	
	public void searchForVin(String vin) {
		this.setVinInputField(vin);
		this.clickOkButton();
	}
	
	 public String getPanelTitleText() {
	    return this.getVehicleOverviewHeading().getText();
    }
}
