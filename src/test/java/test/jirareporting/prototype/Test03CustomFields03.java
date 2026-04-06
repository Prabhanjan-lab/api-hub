/**
 * 
 */
package test.jirareporting.prototype;

import de.sulzer.container.TestContainer;
import de.sulzer.model.Info;
import de.sulzer.model.JsonObject;
import de.sulzer.use.util.CreateJsonElements;
import test.jirareporting.abstractions.AbstractUnitTest;

/**
 * @author Bege
 *
 */
class Test03CustomFields03 extends AbstractUnitTest {

	/**
	 * @throws java.lang.Exception
	 */
	@org.junit.jupiter.api.BeforeEach
	protected void setUp() {
		
		this.setTestContainer(new TestContainer(0));
		
	}

	@Override
	protected void testHook() throws Exception {

		// Info
		Info info = new Info();
		CreateJsonElements.initInfo(info);
		
		// JsonObject
		JsonObject jo = new JsonObject();
		jo.setInfo(info);
		jo.addFields(CreateJsonElements.initFields());
		// without test steps
//		jo.addTests(CreateJsonElements.initTests());
		
		this.getTestContainer().setJso(jo);

	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {
		
	}

}
