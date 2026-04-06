/**
 *
 */
package test.jirareporting.abstractions;

import org.junit.jupiter.api.AfterEach;

import testFramework.AbstractStandardBehaviour;

/**
 * @author Bege
 *
 */
public abstract class AbstractUnitTest extends AbstractStandardBehaviour {

	/**
	 */
	@AfterEach
	protected void tearDown() {

		System.out.println(this.getTestContainer().getJSON());

	}

}
