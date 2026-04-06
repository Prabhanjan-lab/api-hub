/**
 * 
 */
package test.jirareporting.testgroups_refactored;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import de.sulzer.model.IJson;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.util.ConstantsTestResult;
import test.jirareporting.abstractions.AbstractUnitTest;

/**
 * @author Bege
 *
 */
class QATEST_621_DummyTest_PASS extends AbstractUnitTest {

	/**
	 */
	@BeforeEach
	protected void setUp() {

		this.initTestContainer(3); // test has only three test steps
		
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
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 2", new ArrayList<ItemEvidence>());

		// test step 3
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 3", new ArrayList<ItemEvidence>());

	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {
		
	}
	
}
