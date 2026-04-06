/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sulzer.pages.checkpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;

/**
 *
 * @author GP4KM3W
 */
public class CheckdetailsMgmt extends Page {
    
    public CheckdetailsMgmt(WebDriver driver) {
        super(driver);
    }
    
    @FindBy(xpath="(//*[contains(text(),'Operation Mode')]/..//td)[2]")
	private WebElement operationMode;
    
    public String getOperationMode(){
        return operationMode.getText();
    }
    
}
