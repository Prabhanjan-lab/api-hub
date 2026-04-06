/**
 *
 */
package test.jirareporting.testgroups;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import de.sulzer.container.TestContainer;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.util.ConstantsTestResult;
import de.sulzer.use.util.CreateJsonElements;
import test.jirareporting.abstractions.AbstractUnitTest;

/**
 * @author Bege
 *
 */
//class QATEST_621_DummyTest_PASS extends AbstractStandardBehaviour {
class QATEST_621_DummyTest_PASS extends AbstractUnitTest {

	/**
	 */
	@BeforeEach
	protected void setUp() {

		this.setTestContainer(new TestContainer(3)); // test has only three test steps

	}

	@Override
	protected void testHook() throws Exception {

		// PASS/FAIL/TODO not yet to set

		/*
		 * setting test results for steps
		 */

		// test step 1
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 1", new ArrayList<ItemEvidence>());

		List<ItemEvidence> evidences = new ArrayList<>();
		evidences.addAll(CreateJsonElements.initTestEvidence());

		// test step 2
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 2", evidences);

		// test step 3
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 3", new ArrayList<ItemEvidence>());

		// setting end time/date of test and result PASS/FAIL/TODO
		this.setEndTimeAndResult(this.getTestContainer());

	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {

	}

}
