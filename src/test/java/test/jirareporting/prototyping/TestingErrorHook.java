/**
 *
 */
package test.jirareporting.prototyping;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author firedancer
 *
 */
public class TestingErrorHook extends AbstractTest {

	/* (non-Javadoc)
	 * @see testing.AbstractTest#testHook()
	 */
	@Override
	protected void testHook() {

		assertTrue(1 == 2);

	}

}
