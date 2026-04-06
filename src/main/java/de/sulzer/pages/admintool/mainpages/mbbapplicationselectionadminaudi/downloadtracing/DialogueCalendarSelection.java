/**
 *
 */
package de.sulzer.pages.admintool.mainpages.mbbapplicationselectionadminaudi.downloadtracing;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Bege
 *
 */
public class DialogueCalendarSelection extends Page {

//	@FindBy(xpath="")
//	private WebElement ;
//
//	@FindBy(xpath="")
//	private WebElement ;

	private WebDriver webDriver;

	/**
	 *
	 */
	public DialogueCalendarSelection(WebDriver driver) {

		super(driver);

		this.webDriver = driver;

	}

	public void clickDateStart(String date) {
		this.webDriver.findElement(By.xpath("//span[@id='id8Dp']//tr/td/a[contains(text(), '" + date + "')]")).click();
//		this.webDriver.findElement(By.xpath("//table//tr/td/a[contains(text(), '" + date + "')]")).click();
//		this.webDriver.findElement(By.xpath("//table//tr/td/a[contains(text(), '" + date + "')]")).click();
//		clickCalendarDate(date);
	}

	public void clickDateEnd(String date) {
//		this.webDriver.findElement(By.xpath("//table//tr/td/a[contains(text(), '" + date + "')]")).click();
//		clickCalendarDate(date);
		WebElement element = this.webDriver.findElement(By.xpath("//span[@id='iddDp']//tr/td/a[contains(text(), '" + date + "')]"));
//		WebElement element = this.webDriver.findElement(By.xpath("//table//tr/td/a[contains(text(), '" + date + "')]"));

		((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView(true);", element);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		element.sendKeys(Keys.ENTER);
		element.click();
	}

	/**
	 * @param date
	 */
//	private void clickCalendarDate(String date) {
//
//		WebElement element = this.webDriver.findElement(By.xpath("//table//tr/td/a[contains(text(), '" + date + "')]"));
//
//		((JavascriptExecutor) this.webDriver).executeScript("arguments[0].scrollIntoView(true);", element);
//
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		element.sendKeys(Keys.ENTER);
//		element.click();
//
////		this.webDriver.findElement(By.xpath("//table//tr/td/a[contains(text(), '" + date + "')]")).click();
//	}

}
