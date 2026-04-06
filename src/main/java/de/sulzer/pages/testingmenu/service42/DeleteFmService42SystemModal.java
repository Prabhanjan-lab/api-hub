package de.sulzer.pages.testingmenu.service42;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;

public class DeleteFmService42SystemModal extends Page {

	public DeleteFmService42SystemModal(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//div[@class='modal-content']//div[@class='container-fluid']")
	private WebElement modelDialog;

	@FindBy(xpath="//button[text()='Yes' and @class='btn btn-primary']")
	private WebElement buttonDeleteYes;

	@FindBy(xpath="//button[text()='No' and @class='btn btn-default']")
	private WebElement buttonDeleteNo;

	public WebElement getButtonDeleteYes() {
		return buttonDeleteYes;
	}
	
	public WebElement getButtonDeleteNo() {
		return buttonDeleteNo;
	}

	public void clickButtonDeleteYes() {
		
		this.getButtonDeleteYes().click();
		try{
			Thread.sleep(10000);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void clickButtonDeleteNo() {
		
		this.getButtonDeleteNo().click();
		try{
			Thread.sleep(10000);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
}