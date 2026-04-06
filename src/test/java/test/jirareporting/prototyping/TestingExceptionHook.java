/**
 *
 */
package test.jirareporting.prototyping;

/**
 * @author firedancer
 *
 */
public class TestingExceptionHook extends AbstractTest {

	/* (non-Javadoc)
	 * @see testing.AbstractTest#testHook()
	 */
	@Override
	protected void testHook() throws Exception {

		throw new Exception("test exception");

	}

}
