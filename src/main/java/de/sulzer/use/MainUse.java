/**
 * 
 */
package de.sulzer.use;

import de.sulzer.model.Info;
import de.sulzer.model.JsonObject;
import de.sulzer.use.util.CreateJsonElements;

/**
 * @author Bege
 *
 */
public class MainUse {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		MainUse mu = new MainUse();
		
		mu.generateJSON();
	}

	private void generateJSON() {
		
		// Info
		Info info = new Info();
		CreateJsonElements.initInfo(info);
		
//		// Tests
//		Tests tests = new Tests();
//		tests.setItemTest(CreateJsonElements.initTests());
		
		// JsonObject
		JsonObject jo = new JsonObject();
		jo.setInfo(info);
//		jo.setTests(tests);
		jo.addTests(CreateJsonElements.initTests());
		
		//
		System.out.println(jo.toJSON());
//		//
//		System.out.println(tests.toJSON());
	}

}
