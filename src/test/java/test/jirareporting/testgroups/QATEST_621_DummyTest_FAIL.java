/**
 *
 */
package test.jirareporting.testgroups;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import de.sulzer.container.TestContainer;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.util.ConstantsTestResult;
import testFramework.AbstractStandardBehaviour;

/**
 * @author Bege
 *
 */
class QATEST_621_DummyTest_FAIL extends AbstractStandardBehaviour {

	/**
	 */
	@BeforeEach
	protected void setUp() {

		this.setTestContainer(new TestContainer(2)); // test has only three test steps

	}

	@Override
	protected void testHook() throws Exception {

		// PASS/FAIL/TODO not yet to set

		/*
		 * setting test results for steps
		 */

		// test step 1
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 1", new ArrayList<ItemEvidence>());

		// test step 2
		this.testMehtod();
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 2", new ArrayList<ItemEvidence>());

		// test step 3
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 3", new ArrayList<ItemEvidence>());

		// setting end time/date of test and result PASS/FAIL/TODO
		this.setEndTimeAndResult(this.getTestContainer());

	}

	public void testMehtod() throws Exception {
		throw new Exception();
	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {

	}

}
