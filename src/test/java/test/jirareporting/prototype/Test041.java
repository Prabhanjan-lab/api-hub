/**
 * 
 */
package test.jirareporting.prototype;


import org.junit.jupiter.api.Test;

import de.sulzer.container.TestContainer;
import de.sulzer.model.Info;
import de.sulzer.model.JsonObject;
import de.sulzer.use.util.CreateJsonElements;
import static org.junit.jupiter.api.Assertions.assertFalse;

import test.jirareporting.abstractions.AbstractUnitTest;
import test.jirareporting.jsonvalidation.JsonValidator;

/**
 * @author Bege
 *
 */
class Test041 extends AbstractUnitTest {

	/**
	 * @throws java.lang.Exception
	 */
	@org.junit.jupiter.api.BeforeEach
	protected void setUp() {
		
		this.setTestContainer(new TestContainer(3));
		
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
		jo.addTests(CreateJsonElements.initTests());
		
		this.getTestContainer().setJso(jo);
		
		assertFalse(JsonValidator.isValidJson("{test{}"));

	}

	@Override
	protected void tearDownHook() throws Exception, Error, Throwable {
		
	}

}
