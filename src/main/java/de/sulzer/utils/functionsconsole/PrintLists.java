/**
 *
 */
package de.sulzer.utils.functionsconsole;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * @author Bege
 *
 */
public class PrintLists {

	/**
	 *
	 */
	public PrintLists() {

	}

	public void printStringArray(String [] array) {

		for (String element : array) {
			System.out.println("Array element: " + element);
		}

	}

	public void printWebElementListText(List<WebElement> elements) {

		for (WebElement element : elements) {
			System.out.println("List WebElement element: " + element.getText().trim());
		}

	}

}
