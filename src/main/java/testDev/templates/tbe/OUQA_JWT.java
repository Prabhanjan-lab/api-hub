package testDev.templates.tbe;

import de.sulzer.service.rest.services.authentication.jwt.JwtGenerator;
import testFramework.AbstractStandardBehaviour;

public class OUQA_JWT extends AbstractStandardBehaviour {

    @Override
    @org.junit.jupiter.api.BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(1); // test has 9 test steps
		}
    }

    @Override
	protected void testHook() throws Throwable {
    	
    	String header = "{\"cty\":\"adminUiToken_v1_0_6\",\"kid\":\"CN=86111_MBBA_ONUP, OU=Unknown, O=Unknown, L=Unknown, ST=Bayern, C=DE\"}";
		String payload = "{\"this\":\"json\", \"data\", \"more data\"}";

		String result = "";

		JwtGenerator jg = new JwtGenerator();

		try {
			result = jg.generateJwt(header, payload);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != result && result.length() > 0) {
				System.out.println(result);
			} else {
				System.out.println("no jwt content available");
			}
		}

		////this.logStepPassed();

    }

    @Override
    public void tearDownHook() {

    }

}