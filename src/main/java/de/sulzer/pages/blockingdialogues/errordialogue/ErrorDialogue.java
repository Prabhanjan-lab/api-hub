/**
 * 
 */
package de.sulzer.pages.blockingdialogues.errordialogue;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * Experimental implementation in order to prevent obscuring modal dialogues to
 * change user rights at end of test (tearDownHook() method).
 * 
 * As of 01/2020 this is only used in OUQA-822, because it is not yet known,
 * which kinds of dialogues can occure.
 * 
 * 
 * @author GCH9F5D
 *
 */
public class ErrorDialogue extends Page {

    @FindAll({
    	@FindBy(xpath="//ngb-modal-window[@class='modal fade show']")
    })
	private List<WebElement> errorDialogue;
	
    @FindAll({
    	@FindBy(xpath="//ngb-modal-window[@class='modal fade show']//button[@class='btn btn-primary'][contains(text(), 'Ok')]")
    })
	private List<WebElement> buttonOk;
	
	public ErrorDialogue(WebDriver driver) {
		super(driver);
	}

	public void unblockGui() {
		
		while (null != this.errorDialogue &&
				this.errorDialogue.size() > 0) {
			byte index = (byte) (this.errorDialogue.size() - 1);
//			System.out.println("ERROR DIALOGUE: " + this.errorDialogue.get(index).getText());
//			System.out.println("BUTTON (index ('" + index + "')): " + this.buttonOk.get(index).getText());
			this.buttonOk.get(index).click();
		}
		
	}
	
}
