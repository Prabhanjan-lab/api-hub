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
 * FIXME checking produced json validity
 * 
 * @author Bege
 *
 */
class BehaviourTest03_Exceptions05 extends AbstractUnitTest {
	
	/**
	 * @throws java.lang.Exception
	 */
	@org.junit.jupiter.api.BeforeEach
	protected void setUp() {
		
		this.initTestContainer(3);

	}

	@Override
	protected void testHook() throws Exception {

		// test step 1
		this.testMehtod();
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 1", new ArrayList<ItemEvidence>());

		// test step 2
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 2", new ArrayList<ItemEvidence>());

		// test step 3
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 3", new ArrayList<ItemEvidence>());

		// test step 4
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 4", new ArrayList<ItemEvidence>());
	
	}

	public void testMehtod() throws Exception {
		throw new Exception();
	}

	protected void initTestContainer(int i) {

		this.setTestContainer(new TestContainer(i));
		
	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {
		
	}

}
