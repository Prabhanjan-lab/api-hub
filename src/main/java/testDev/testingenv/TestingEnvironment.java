package testDev.testingenv;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import testFramework.AbstractStandardBehaviour;

public class TestingEnvironment extends AbstractStandardBehaviour {

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
    	
    	File f1 = new File("data/evidence/H96566k.jpg");
    	File f2 = new File(System.getProperty("user.dir") + "/selenium.gui.testing/data/evidence/H96566k.jpg");
    	
    	System.out.println(f1);
    	System.out.println("f1 exists: " + f1.exists());
    	System.out.println(f2);
    	System.out.println("f2 exists: " + f2.exists());

    }

    @Override
    public void tearDownHook() {
    	System.out.println("tearDown()");
    }

}