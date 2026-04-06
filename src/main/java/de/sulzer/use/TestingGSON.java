package de.sulzer.use;

import com.google.gson.Gson;

import de.sulzer.model.Info;
import de.sulzer.model.JsonObject;
import de.sulzer.use.util.CreateJsonElements;

/**
 * @author Bege
 *
 */
public class TestingGSON {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestingGSON tg = new TestingGSON();
		
		tg.generateJSON();
	}

	private void generateJSON() {

		// Info
		Info info = new Info();
		//
		CreateJsonElements.initInfo(info);
		//
		info.setDescription("");

//		// Tests
//		Tests tests = new Tests();
//		tests.setItemTest(CreateJsonElements.initTests());
		
		// JsonObject
		JsonObject jo = new JsonObject();
		jo.setInfo(info);
//		jo.setTests(tests);
		jo.addTests(CreateJsonElements.initTests());
		
		Gson gson = new Gson();
		
		//
//		System.out.println(info.toJSON());
		System.out.println("info object:");
		System.out.println(gson.toJson(info));
		//
////		System.out.println(tests.toJSON());
//		System.out.println("tests object:");
//		System.out.println(gson.toJson(tests));
		//
		System.out.println("full object:");
		System.out.println(gson.toJson(jo));
	}

}
