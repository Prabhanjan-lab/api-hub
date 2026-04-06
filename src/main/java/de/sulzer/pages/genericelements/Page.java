package de.sulzer.pages.genericelements;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class Page {
	protected WebDriver driver;
	public Page(WebDriver driver) {
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
//		PageFactory.initElements(factory, this);
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 30);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
}
