/**
 *
 */
package test.jirareporting.prototype;


import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import de.sulzer.container.TestContainer;
import de.sulzer.model.IJson;
import de.sulzer.model.Info;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.ItemTest;
import de.sulzer.model.util.ConstantsTestResult;
import static org.junit.jupiter.api.Assertions.assertTrue;

import test.jirareporting.abstractions.AbstractUnitTest;
import test.jirareporting.jsonvalidation.JsonValidator;

/**
 * @author Bege
 *
 */
class Test05 extends AbstractUnitTest {

	/**
	 * @throws java.lang.Exception
	 */
	@org.junit.jupiter.api.BeforeEach
	protected void setUp() {

		this.setTestContainer(new TestContainer(3));

	}

	/**
	 * Testing, if valid JSON is produced.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Override
	protected void testHook() throws Exception {

		// creating info block
		Info info = new Info();

		// creating test (test case)
		ItemTest test = new ItemTest();

		/*
		 * setting common values for test run
		 */
		info.setSummary(this.createSummary());
		info.setDescription("json test for validity");
		info.setUser("Tobias Bege (Sulzer)");
		info.setVersion("Version 1 \\\"Pavian\\\"");
		info.setRevision("1");

		// setting start time/date of test
		info.setStartDate(this.getLocalDateTime());

		info.setAssginee("Tobias Bege (Sulzer)");
		info.setReporter("Tobias Bege (Sulzer)");
		info.setTestEnvironments("MS-DOS");

		//
		this.getTestContainer().setInfo(info);

		/*
		 * setting values for test case for test run
		 */
		test.setTestKey("QATEST-621");
		test.setStart(this.getLocalDateTime());
		test.setFinish(this.getLocalDateTime());
		test.setComment("test run example");
		test.setExecutedBy(info.getReporter());

		//
		this.getTestContainer().setTest(test);

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

		// setting end time/date of test and result PASS/FAIL/TODO
		this.setEndTimeAndResult(this.getTestContainer());

		assertTrue(JsonValidator.isValidJson(this.getTestContainer().getJSON()));

	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {

	}

}
