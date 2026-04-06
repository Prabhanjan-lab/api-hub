/**
 *
 */
package de.sulzer.oudcampaign.idgenerator.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

/**
 * @author Bege
 *
 */
public class Figure {

	private Figure child;

	private String[] values;

	private int index;

	/**
	 * Constructor for single figure element/number.
	 */
	public Figure(String[] values) {
		this.child = null;
		this.values = values;
		this.index = 0;
	}

	/**
	 * Constuctor for multi figure element/number.
	 */
	public Figure(Figure child, String[] values) {
		this.child = child;
		this.values = values;
		this.index = 0;
	}

	/**
	 * Constructor for generating values from given string.
	 * It counts up from this value.
	 *
	 * ATTENTION: values and given string must fit.
	 */
	public Figure(String startValue, String[] values) {

		this.values = values;

		if (startValue.length() == 1) {
			this.child = null;
			this.index = this.getIndex(startValue);
		} else {
			this.child = new Figure(startValue.substring(1), values);
			this.index = this.getIndex(startValue.substring(0, 1));
		}

	}

	private int getIndex(String startValue) {

		for (int i = 0; i < this.values.length; i++) {
			if (this.values[i].equals(startValue)) {
				return i;
			}
		}
		return 0;
	}

	public boolean hasNext() {

		if (null == this.child) {

			if ((this.index + 1) >= this.values.length) {
				this.index = 0;
				return false;
			} else {
				this.index++;
				return true;
			}

		} else {

			boolean child = this.child.hasNext();

			if (child) {
				return child;
			} else {

				if ((this.index + 1) >= this.values.length) {
					this.index = 0;
					return false;
				} else {
					this.index++;
					return true;
				}

			}

		}

	}

	public String next() {

		if (null != this.child) {
			return this.values[this.index] + this.child.next();
		} else {
			return this.values[this.index];
		}

	}

	public long calculate() {

		if (null == this.child) {
			return this.values.length;
		} else {
			return this.values.length * this.child.calculate();
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
//		return "Figure [child=" + child + ", values=" + Arrays.toString(values) + ", index=" + index + "]";
		if (null != this.child) {
			return this.values[this.index] + this.child.toString();
		} else {
			return this.values[this.index];
		}
	}

}
