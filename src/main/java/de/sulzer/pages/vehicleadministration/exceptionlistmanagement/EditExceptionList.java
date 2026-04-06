package de.sulzer.pages.vehicleadministration.exceptionlistmanagement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditExceptionList extends AddExceptionList {

	
	public EditExceptionList(WebDriver driver) {
		super(driver);
	}


	public void clickUpdate() {
		this.saveClick(this.getUpdate());
	}
	
	public void clickCancel() {
		this.saveClick(this.getCancel());
	}

}
