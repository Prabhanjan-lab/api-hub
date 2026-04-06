package de.sulzer.pages;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.support.ui.WebDriverWait;
import testFramework.utilities.ReusableMethods;

public class BrandSelectionPage extends Page {

	protected WebDriver webDriver;

	public BrandSelectionPage(WebDriver driver){
		super(driver);
		webDriver = driver;
	}

    @FindBy(tagName = "h3")
    private WebElement chooseBrandHeader;

    @FindBy(tagName = "select")
    private WebElement brandSelectMenuElement;

    private Select brandSelectMenu;

    @FindBy(id = "btn-brand-select-apply")
    private WebElement brandSelectApplyButton;


    public WebElement getChooseBrandHeader() {
        return this.chooseBrandHeader;
    }

    public Select getBrandSelectMenu() {

        if (null == this.brandSelectMenu) {
            this.brandSelectMenu = new Select(brandSelectMenuElement);
        }

        return this.brandSelectMenu;
    }

    public WebElement getBrandSelectMenuWebElement() {
        return this.brandSelectMenuElement;
    }

    public WebElement getBrandSelectApplyButton() {
        return this.brandSelectApplyButton;
    }

    public String getSelectedBrand() {
        return this.getBrandSelectMenu().getFirstSelectedOption().toString();
    }

    public String getBrand(Integer index) {
        return this.getBrandSelectMenu().getOptions().get(index).getText();
    }

    public void setBrand(String brand) {
        this.getBrandSelectMenu().selectByValue(brand);
    }

    public void setBrandByVisibleText(String brand) {
        this.getBrandSelectMenu().selectByVisibleText(brand);
    }

    /**
     * These asserts are necessary, in order to be able to click the apply button EVERYTIME.
     * NOTE: different brands for different Users!!! --> Admin User have 8 entries, other one have less
     *
     * @throws InterruptedException
     */
    public void clickApplyBrandButton() {


        String Audi = this.getBrand(0);
        assertTrue(Audi.contains("Audi"));

//		String Bentley = this.getBrand(1);
//		assertTrue(Bentley.contains("Bentley"));
//		String Lamborghini = this.getBrand(2);
//		assertTrue(Lamborghini.contains("Lamborghini"));
//		String Porsche = this.getBrand(3);
//		assertTrue(Porsche.contains("Porsche"));
//		String Seat = this.getBrand(4);
//		assertTrue(Seat.contains("Seat"));
//		String Skoda = this.getBrand(5);
//		assertTrue(Skoda.contains("Skoda"));
//		String VWcv = this.getBrand(6);
//		assertTrue(VWcv.contains("VW Commercial Vehicles"));
//		String VWPkw = this.getBrand(7);
//		assertTrue(VWPkw.contains("VW PKW"));

//		this.brandSelectApplyButton.click();
        ReusableMethods.clickElement(getBrandSelectApplyButton(), 10, webDriver);
    }
}
