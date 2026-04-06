package testDev.testingenv;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import testFramework.AbstractStandardBehaviour;

public class TestingEnvironmentJenkins extends AbstractStandardBehaviour {

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

    	System.out.println("BUILD_NUMBER :" + System.getenv("BUILD_NUMBER"));
    	System.out.println("BUILD_ID :" + System.getenv("BUILD_ID"));
    	System.out.println("BUILD_URL :" + System.getenv("BUILD_URL"));
    	System.out.println("NODE_NAME :" + System.getenv("NODE_NAME"));
    	System.out.println("JOB_NAME :" + System.getenv("JOB_NAME"));
    	System.out.println("BUILD_TAG :" + System.getenv("BUILD_TAG"));
    	System.out.println("JENKINS_URL :" + System.getenv("JENKINS_URL"));
    	System.out.println("EXECUTOR_NUMBER :" + System.getenv("EXECUTOR_NUMBER"));
    	System.out.println("JAVA_HOME :" + System.getenv("JAVA_HOME"));
    	System.out.println("WORKSPACE :" + System.getenv("WORKSPACE"));
    	System.out.println("SVN_REVISION :" + System.getenv("SVN_REVISION"));
    	System.out.println("CVS_BRANCH :" + System.getenv("CVS_BRANCH"));
    	System.out.println("GIT_COMMIT :" + System.getenv("GIT_COMMIT"));
    	System.out.println("GIT_URL :" + System.getenv("GIT_URL"));
    	System.out.println("GIT_BRANCH :" + System.getenv("GIT_BRANCH"));
    	System.out.println("PROMOTED_URL :" + System.getenv("PROMOTED_URL"));
    	System.out.println("PROMOTED_JOB_NAME :" + System.getenv("PROMOTED_JOB_NAME"));
    	System.out.println("PROMOTED_NUMBER :" + System.getenv("PROMOTED_NUMBER"));
    	System.out.println("PROMOTED_ID :" + System.getenv("PROMOTED_ID"));

    	
    	File f = new File(System.getenv("WORKSPACE") + "/ws/");
    	System.out.println("WORKSPACE :" + f);
    	System.out.println(f.exists());
    	
    	f = new File(System.getenv("WORKSPACE"));
    	System.out.println("WORKSPACE :" + f);
    	System.out.println(f.exists());

    	System.out.println("Comparison with user.dir");
    	
    	System.out.println(System.getProperty("user.dir"));
    	
    }

    @Override
    public void tearDownHook() {
    	System.out.println("tearDown()");
    }

}