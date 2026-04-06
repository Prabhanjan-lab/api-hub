package de.sulzer.pages.vehicleadministration.template;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditTemplate extends AddTemplate {

	public EditTemplate(WebDriver driver) {
		super(driver);
	}

	public void saveClick(WebElement element) {
		element.isDisplayed();
		element.isEnabled();
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		element.click();
	}
	
	// update button
	@FindBy(id = "submit-save")
	private WebElement update;

	public WebElement getUpdate() {
		return update;
	}

	public void clickUpdate() {
		this.saveClick(this.getUpdate());
	}

}
