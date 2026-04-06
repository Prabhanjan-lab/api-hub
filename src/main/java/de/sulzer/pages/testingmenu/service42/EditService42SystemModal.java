package de.sulzer.pages.testingmenu.service42;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;
import testFramework.utilities.ReusableMethods;

public class EditService42SystemModal extends Page {

	WebDriver webDriver;
	public EditService42SystemModal(WebDriver driver) {
		super(driver);
		webDriver = driver;
	}

	@FindBy(xpath="//form[@id='service42ModalForm']//div[@class='container-fluid']")
	private WebElement modelDialog;

	@FindBy(id="amCode")
	private WebElement amCode;

	@FindBy(id="description")
	private WebElement description;

	@FindBy(id="amId")
	private WebElement amId;

	@FindBy(id="tnr")
	private WebElement tnr;

	@FindBy(name="zip")
	private WebElement inputButton;

	@FindBy(id="submit-save")
	private WebElement submitSave;

	@FindBy(id="cancel-save")
	private WebElement submitCancel;

	@FindBy(id="service42details")
	private WebElement commentField;

	@FindBy(xpath = "//button[.=' Ok ']")
	private WebElement notAuthorisedOkButton;

	public void createService42(String amCode, String description, String amId, String tnr, String filepath, String comment) {
		this.setAmCodeField(amCode);
		this.setDescriptionField(description);
		this.setAmId(amId);
		this.setTnrField(tnr);
		this.uploadFile(filepath);
		this.setCommentField(comment);
		this.clickSaveButton();
	}

	public WebElement getCommentField(){
		return commentField;
	}

	public void setCommentField(String input){
		this.getCommentField().clear();
		this.getCommentField().sendKeys(input);
	}

	public WebElement getUploadButton(){
		return inputButton;
	}

	public void uploadFile(String filepath){
		this.getUploadButton().sendKeys(filepath);
	}

	public WebElement getAmCodeField() {
		return amCode;
	}

	public void setAmCodeField(String input) {
//		this.getAmCodeField().clear();
		ReusableMethods.clearField(getAmCodeField());
		this.getAmCodeField().sendKeys(input);
	}

	public WebElement getSaveButton() {
		return submitSave;
	}
	
	public WebElement getCancelButton() {
		return submitCancel;
	}

	public void clickSaveButton() {
		
		this.getSaveButton().click();
		try{
			Thread.sleep(10000);
			//this.getCloseButton().click();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void clickCancelButton() {
		
		this.getCancelButton().click();
		try{
			Thread.sleep(10000);
			//this.getCloseButton().click();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}

	public WebElement getDescriptionField() {
		return description;
	}

	public void setDescriptionField(String input) {
		this.getDescriptionField().clear();
		this.getDescriptionField().sendKeys(input);
	}
	
	public WebElement getAmIdField() {
		return amId;
	}

	public void setAmId(String input) {
		this.getAmIdField().clear();
		this.getAmIdField().sendKeys(input);
	}
	
	public WebElement getTnrField() {
		return tnr;
	}

	public void setTnrField(String input) {
		this.getTnrField().clear();
		this.getTnrField().sendKeys(input);
	}


	public WebElement getNotAuthorisedOkButton() {
		return notAuthorisedOkButton;
	}
	
}