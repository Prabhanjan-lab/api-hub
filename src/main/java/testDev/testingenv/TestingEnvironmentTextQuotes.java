package testDev.testingenv;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import testFramework.AbstractStandardBehaviour;

public class TestingEnvironmentTextQuotes extends AbstractStandardBehaviour {

    @Override
    @org.junit.jupiter.api.BeforeEach
	protected void setUp() throws Throwable {
    	
		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(new Throwable(e));
			throw e;
		} finally {
			this.initTestContainer(1); // test has 1 test steps
		}
		
    	System.out.println("setUp()");
    }

    @Override
	protected void testHook() throws Throwable {

		String fixVersion = System.getProperty("fixVersion");
		System.out.println("raw from jenkins: " + fixVersion);

		fixVersion = fixVersion.replaceAll("\"", "\\\\\"");
		System.out.println("refined by java: " + fixVersion);

    }

    @Override
    public void tearDownHook() {
    	System.out.println("tearDown()");
    }

}