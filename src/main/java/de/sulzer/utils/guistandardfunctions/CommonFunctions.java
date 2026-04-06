/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * @author Sulzer GmbH
 *
 */
public class CommonFunctions {

	/**
	 *
	 */
	public CommonFunctions() {

	}

	/**
	 * @param text
	 * @param webElements
	 * @param found
	 * @return
	 */
	public boolean isListContainingText(String text, List<WebElement> webElements) {

		boolean found = false;

		for (WebElement element : webElements) {

	    	String s = element.getText().trim();

	    	if (s.contains(text)) {
	    		found = true;
	    		break;
	    	}

	    }

		return found;
	}

}
