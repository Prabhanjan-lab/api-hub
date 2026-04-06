/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

/**
 * @author Sulzer GmbH
 *
 */
public class VOFlashAssignment {

	/**
	 * 0 = all flash media
	 * 1 to n = 1 to n flash media starting at first flash media
	 */
	private int amountFlashMedia;

	public VOFlashAssignment() {
		this.amountFlashMedia = 1;
	}

	public VOFlashAssignment(int amount) {
		this.amountFlashMedia = amount;
	}

	public int getAmountFlashMedia() {
		return this.amountFlashMedia;
	}

}
