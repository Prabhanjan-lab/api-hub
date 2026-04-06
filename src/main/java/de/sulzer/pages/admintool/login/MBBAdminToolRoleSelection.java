/**
 *
 */
package de.sulzer.pages.admintool.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Bege
 *
 */
public class MBBAdminToolRoleSelection extends Page {

	@FindBy(xpath="//select[@name='systemId']")
	private WebElement selectDomainElement;

	private Select selectDomain;

	@FindBy(xpath="//input[@type='submit']")
	WebElement buttonSelection;

	/**
	 *
	 */
	public MBBAdminToolRoleSelection(WebDriver driver) {
		super(driver);
	}

	public WebElement getSelectDomainElement() {
		return this.selectDomainElement;
	}

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

	/**
	 * @return the buttonLogin
	 */
	public WebElement getButtonSelection() {
		return buttonSelection;
	}

	public void clickButtonSelection() {
		this.getButtonSelection().click();
	}

}
