package de.sulzer.pages.campaignmanagement.inbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.sulzer.pages.genericelements.Page;

public class InboxPageDomainSelection extends Page {

	public InboxPageDomainSelection(WebDriver driver) {
		super(driver);
	}

	@FindBy(id="drop-domain-slc-domains")
	private WebElement selectDomainElement;

	private Select selectDomain;

	@FindBy(id="cb-domain-slc-encr")
	private WebElement encryptFlashfile;

	@FindBy(xpath="//input[@id='downloadPlatform']")
	private WebElement checkBoxAudiDlp;

	@FindBy(id="btn-domain-slc-cancel")
	private WebElement cancelButton;

	@FindBy(id = "btn-domain-slc-next")
	private WebElement translationPropertiesButton;

	public Select getSelectDomain() {
		if (null == this.selectDomain) {
			this.selectDomain = new Select(selectDomainElement);
		}
		return selectDomain;
	}

	public void selectDomainByName(String domainCode) {
		this.getSelectDomain().selectByValue(domainCode);
	}

	public void selectDomainByIndex(Integer index) {
		this.getSelectDomain().selectByIndex(index);
	}

	public WebElement getEncryptFlashfile(){
		return encryptFlashfile;
	}
	public void clickEncryptFlashfile(){
		this.getEncryptFlashfile().click();
	}

	public WebElement getCheckBoxAudiDlp(){
		return this.checkBoxAudiDlp;
	}

	public void clickCheckBoxAudiDlp(){
		this.getCheckBoxAudiDlp().click();
	}

	public WebElement getCancelButton(){
		return cancelButton;
	}

	public void clickCancelButton(){
		this.getCancelButton().click();
	}
	public WebElement getTranslationPropertiesButton(){
		return translationPropertiesButton;
	}
	public void clickTranslationPropertiesButton(){
		this.getTranslationPropertiesButton().click();
	}
}