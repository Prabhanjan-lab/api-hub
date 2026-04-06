package test.jirareporting.prototyping;

import de.sulzer.model.ItemEvidence;
import de.sulzer.model.util.ConstantsTestResult;
import testFramework.AbstractStandardBehaviour;

import java.util.ArrayList;

public class TestDummyWithBeforeAfter extends AbstractStandardBehaviour {

    @Override
	@org.junit.jupiter.api.BeforeEach
	protected void setUp() {
		System.out.println("test heriditary");
    	System.out.println("Execute " + this.getClass().getName());
    	this.initTestContainer(1);
//
	}

	@org.junit.jupiter.api.AfterEach
    public void tearDown() {

    	System.out.println("torn down");

    }

	@Override
	protected void testHook() throws Exception {

		/*
		 * setting test results for steps
		 */
		// test step 1
		this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "comment 1", new ArrayList<ItemEvidence>());

		// setting end time/date of test and result PASS/FAIL/TODO
//		this.setEndTimeAndResult();
//		this.setEndTimeAndResult(this.getTestContainer().getInfo(),
//                                 this.getTestContainer().getTest());

		System.out.println("test run stopped");

	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {

	}

}