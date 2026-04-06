package de.sulzer.pages.testingmenu.service42;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;

public class DeleteService42SystemModal extends Page {

	public DeleteService42SystemModal(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//div[@class='modal-content']//div[@class='container-fluid']")
	private WebElement modelDialog;

	@FindBy(xpath="//button[@type='submit']")
	private WebElement buttonDeleteYes;

	@FindBy(xpath="//button[@id='cancel-delete']")
	private WebElement buttonDeleteNo;

	@FindBy(xpath = "//td[.=' No records found. ']")
	private WebElement noRecordsFound;

	@FindBy(xpath = "//button[.=' Ok ']")
	private WebElement confirmDeletionOkButton;

	public WebElement getButtonDeleteYes() {
		return buttonDeleteYes;
	}
	
	public WebElement getButtonDeleteNo() {
		return buttonDeleteNo;
	}

	public WebElement getNoRecordsFound() {
		return noRecordsFound;
	}

	public WebElement getConfirmDeletionOkButton() {
		return confirmDeletionOkButton;
	}


	public void clickButtonDeleteYes() {
		
		this.getButtonDeleteYes().click();
//		try{
//			Thread.sleep(10000);
//		}
//		catch(Exception e){
//			System.out.println(e);
//		}
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