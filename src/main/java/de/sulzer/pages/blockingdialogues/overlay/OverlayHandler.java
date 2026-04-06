/**
 * 
 */
package de.sulzer.pages.blockingdialogues.overlay;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.sulzer.pages.genericelements.Page;

/**
 * @author GCH9F5D
 *
 */
public class OverlayHandler extends Page {

	@FindBy(xpath="//div[contains(@class, 'ui-widget-overlay')]")
	private WebElement overlay;

	public static final String MULTIPLEOVERLAYS = "//*[contains(@class,'overlay')] | //*[contains(@class,'loading')] | //*[contains(@class,'modal-fade')] | //*[contains(@class,'modal fade show')]";
	
    @FindAll({
        @FindBy(xpath = MULTIPLEOVERLAYS)
    })
    private List<WebElement> overlays;

	public OverlayHandler(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the overlay
	 */
	public WebElement getOverlay() {
		return this.overlay;
	}
	
	/**
	 * @return the overlays
	 */
	public List<WebElement> getOverlays() {
		return this.overlays;
	}

	public static synchronized void waitingForOverlay(WebDriverWait wait) {
		wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(OverlayHandler.MULTIPLEOVERLAYS), 0));
	}
	
}
