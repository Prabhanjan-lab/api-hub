package de.sulzer.pages.campaignmanagement.inbox;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;

public class DragDrop extends Page {
	public DragDrop(WebDriver driver) {
		super(driver);
		
	}
	//related to Drag and Drop
	
		//@FindBy(xpath="//div[@class='flashmedium img-rounded']//following::h1[1]")
		@FindBy(xpath="//legend[.='Selected Flashmedia']/..//div[@class='panel-body']//div[@class='flashmedium img-rounded'][1]")
		private WebElement flashfileOne;
		
		public WebElement getflshfileOne(){
			return flashfileOne;
		}
		public void clickflashfileOne(){
			this.getflshfileOne().click();
		}
		//@FindBy(xpath="//div[@class='flashmedium img-rounded']//following::h1[2]")
		@FindBy(xpath="//legend[.='Selected Flashmedia']/..//div[@class='panel-body']//div[@class='flashmedium img-rounded'][2]")
		private WebElement flashfileTwo;
		
		public WebElement getflshfileTwo(){
			return flashfileTwo;
		}
		public void clickflashfileTwo(){
			this.getflshfileTwo().click();
		}
		//@FindBy(xpath="//div[@class='flashmedium img-rounded']//following::h1[2]")
		@FindBy(xpath="//legend[.='Selected Flashmedia']/..//div[@class='panel-body']//div[@class='flashmedium img-rounded'][3]")
		private WebElement flashfileThree;
		
		public WebElement getflshfileThree(){
			return flashfileThree;
		}
		public void clickflashfileThree(){
			this.getflshfileThree().click();
		}
		@FindBy(xpath="//*[contains(text(),'01-Step 01')]")
		private WebElement recallCriterionOne;
		
		public WebElement getrecallCriterionOne(){
			return recallCriterionOne;
		}
		
		@FindBy(xpath="//*[contains(text(),'02-Step 02')]")
		private WebElement recallCriterionTwo;
		
		public WebElement getrecallCriterionTwo(){
			return recallCriterionTwo;
		}
		
		@FindBy(xpath="//*[contains(text(),'03-Step 03')]")
		private WebElement recallCriterionThree;
		
		public WebElement getrecallCriterionThree(){
			return recallCriterionThree;
		}
		//apply button
		@FindBy(xpath="//button[.='Apply']")
		private WebElement finalApply;
		
		public WebElement getfinalApply(){
			return finalApply;
		}
		public void clickfinalApply(){
			this.getfinalApply().click();
		}
}
