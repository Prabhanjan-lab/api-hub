package de.sulzer.pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import de.sulzer.pages.genericelements.PageWithNavigation;
import testFramework.utilities.ReusableMethods;

public class UserProfilePage extends PageWithNavigation{

	WebDriver webDriver;
	public UserProfilePage(WebDriver driver) {
		super(driver);
		webDriver = driver;
	}

	@FindAll({
			@FindBy(xpath = "//button[contains(.,'Load')]")
	})
	private List<WebElement> loadButtons;


	@FindAll({
			@FindBy(xpath = "//*[@id='dl']/div/p-tabview/div/div[1]//li/a")
	})
	private List<WebElement> inspectBrandsLink;

	@FindAll({
//			@FindBy(xpath = "(//*[@id='dl']//div/div[1]/div)[2]/ul//a")
			@FindBy(xpath = "//*[@id=\"dl\"]/div/p-tabview/div/div[1]/div//a")
	})
	private List<WebElement> inspectCurrentRolesLink;

	@FindAll({
			@FindBy(xpath = "(//*[@id='dl']//div/role-tabs/p-tabview/div/div[2])[1]/p-tabpanel")
	})
	private List<WebElement> inspectFirstCurrentRolesProperties;

//	@FindBy(xpath = "/html/body/div/boot-component/angular4-app/div/main-user-profile/div/inspect-roles/div/div[1]/div/div/div[3]/button")
	@FindBy(xpath = "//inspect-roles//button")
	private WebElement loadRolesButton;

	@FindBy(xpath = "(//div[@class='radio'])[2])")
	private WebElement selectTranslationRadioButton;
	
	@FindBy(id = "languageSelector")
	private WebElement languageSelectorWebElement;
	private Select languageSelect;
	
	@FindBy(className = "applyButton")
	private WebElement applyLanguageChangeButton;


//	public UserProfilePage(WebDriver driver) {
//		this.languageSelect = new Select(getLoadButtons());
//		this.defineUILanguageButton = this.loadButtons.get(1);
//	}
//
//	public WebElement getDefineUILanguageButton() {
//		return defineUILanguageButton;
//	}
//
//	public void clickDefineUILanguageButton() {
//		this.getDefineUILanguageButton().click();
//	}
	
	public WebElement getSelectTranslationRadioButton() {
		return selectTranslationRadioButton;
	}
	
	public void clickSelectTranslationRadioButton() {
		this.getSelectTranslationRadioButton().click();
	}
	
	public Select getLanguageSelect() {
		return languageSelect;
	}
	
	public void selectGermanLanguage() {
		this.getLanguageSelect().selectByVisibleText("de-DE");
	}
	
	public void selectEnglishLanguage() {
		this.getLanguageSelect().selectByVisibleText("en-GB");
	}
	
	public WebElement getApplyLanguageChangeButton() {
		return applyLanguageChangeButton;
	}
	
	public void clickApplyLanguageChangeButton() {
		this.getApplyLanguageChangeButton().click();
	}

	public void clickLoadRolesButton(){
		ReusableMethods.clickElement(getLoadRolesButton(), 3, _driver);
	}

	public WebElement getLoadRolesButton(){return loadRolesButton;};

	public List<WebElement> getLoadButtons() {
		return loadButtons;
	}

	public List<WebElement> getInspectBrandsLink() {
		return inspectBrandsLink;
	}

	public List<WebElement> getInspectCurrentRolesLink() {
		return inspectCurrentRolesLink;
	}

	public List<WebElement> getInspectCurrentRolesLink(int i) {
		int j = i + 2;
		List<WebElement> elements;
		elements = _driver.findElements(By.xpath("(//*[@id='dl']//div/div[1]/div)[" + j + "]/ul//a"));
		return elements;
	}

	public List<WebElement> getInspectCurrentRolesProperties(int i) {
		int j = i + 1;
		List<WebElement> elements;
		elements = _driver.findElements(By.xpath("(//*[@id='dl']//div/role-tabs/p-tabview/div/div[2])[" + j + "]//p-tabpanel"));
		return elements;
	}

	public List<WebElement> getInspectFirstCurrentRolesProperties() {
		return inspectFirstCurrentRolesProperties;
	}
}
