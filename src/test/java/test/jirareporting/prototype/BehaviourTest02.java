/**
 * 
 */
package test.jirareporting.prototype;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.sulzer.container.TestContainer;
import de.sulzer.model.IJson;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.util.ConstantsTestResult;
import test.jirareporting.abstractions.AbstractUnitTest;

/**
 * @author Bege
 *
 */
class BehaviourTest02 extends AbstractUnitTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@org.junit.jupiter.api.BeforeEach
	protected void setUp() {

		this.setTestContainer(new TestContainer(3));
		
	}

	@Override
	protected void testHook() throws Exception {

		// test step 1
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 1", new ArrayList<ItemEvidence>());

		// test step 2
		this.testMehtod();
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 2", new ArrayList<ItemEvidence>());

		// test step 3
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 3", new ArrayList<ItemEvidence>());

	}

	public void testMehtod() throws Exception {
		throw new Exception();
	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {
		
	}

}
