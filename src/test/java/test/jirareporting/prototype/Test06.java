/**
 *
 */
package test.jirareporting.prototype;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import de.sulzer.model.IJson;
import de.sulzer.model.ItemCustomField;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.ItemExample;
import de.sulzer.model.ItemKeyValuePair;
import de.sulzer.model.ItemResult;
import de.sulzer.model.ItemStep;
import de.sulzer.use.util.CreateJsonElements;
import test.jirareporting.jsonvalidation.JsonValidator;

/**
 * @author Bege
 *
 */
class Test06 {

	/**
	 * @throws java.lang.Exception
	 */
	@org.junit.jupiter.api.BeforeEach
	protected void setUp() {

	}

	/**
	 * Testing, if valid JSON is produced: ItemEvidence.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test01() {

		ItemEvidence ie = new ItemEvidence("data", "/root/secret", "URI");

		System.out.println(ie.toJSON());

		assertTrue(JsonValidator.isValidJson(ie.toJSON()));

	}

	/**
	 * Testing, if valid JSON is produced: ItemExample.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test02() {

		ItemExample ie = new ItemExample("example");

		System.out.println(ie.toJSON());

		assertTrue(JsonValidator.isValidJson(ie.toJSON()));

	}

	/**
	 * Testing, if valid JSON is produced: ItemResult without ItemExample.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test03() {

		ItemResult ir = new ItemResult("name", "duration", "log entry", "status code", new ArrayList<ItemExample>());

		System.out.println(ir.toJSON());

		assertTrue(JsonValidator.isValidJson(ir.toJSON()));

	}

	/**
	 * Testing, if valid JSON is produced: ItemResult with two ItemExample.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test04() {

		List<ItemExample> list = new ArrayList<ItemExample>();
		//
		list.addAll(CreateJsonElements.initTestExamples());

		//
		ItemResult ir = new ItemResult("name", "duration", "log entry", "status code", list);

		System.out.println(ir.toJSON());

		assertTrue(JsonValidator.isValidJson(ir.toJSON()));

	}

	/**
	 * Testing, if valid JSON is produced: ItemResult with one ItemExample.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test05() {

		List<ItemExample> list = new ArrayList<ItemExample>();
		//
		list.add(new ItemExample("type"));

		//
		ItemResult ir = new ItemResult("name", "duration", "log entry", "status code", list);

		System.out.println(ir.toJSON());

		assertTrue(JsonValidator.isValidJson(ir.toJSON()));

	}

	/**
	 * Testing, if valid JSON is produced: ItemStep.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test06() {

		ItemStep is = new ItemStep("status", "comment", new ArrayList<ItemEvidence>());

		System.out.println(is.toJSON());

		assertTrue(JsonValidator.isValidJson(is.toJSON()));

	}

	/**
	 * Testing, if valid JSON is produced: ItemStep with ItemEvidences.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test07() {

		List<ItemEvidence> list = new ArrayList<ItemEvidence>();
		//
		list.addAll(CreateJsonElements.initTestEvidence());

		//
		ItemStep is = new ItemStep("status", "comment", list);

		System.out.println(is.toJSON());

		assertTrue(JsonValidator.isValidJson(is.toJSON()));

	}

	/**
	 * Testing, if valid JSON is produced: ItemStep with one ItemEvidence.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test08() {

		List<ItemEvidence> list = new ArrayList<ItemEvidence>();
		//
		list.add(new ItemEvidence("data", "filename", "contentType"));

		//
		ItemStep is = new ItemStep("status", "comment", list);

		System.out.println(is.toJSON());

		assertTrue(JsonValidator.isValidJson(is.toJSON()));

	}

	/**
	 * Testing, if valid JSON is produced: ItemKeyValuePair.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test09() {

		ItemKeyValuePair ikvp = new ItemKeyValuePair("key", "value");

		String json = "{" + ikvp.toJSON() + "}";

		System.out.println(json);

		assertTrue(JsonValidator.isValidJson(json));

	}

	/**
	 * Testing, if valid JSON is produced: ItemCustomField without list.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test10() {

		ItemCustomField icf = new ItemCustomField("custom field");

		String json = "{" + icf.toJSON() + "}";

		System.out.println(json);

		assertTrue(JsonValidator.isValidJson(json));

	}

	/**
	 * Testing, if valid JSON is produced: ItemCustomField with list.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test11() {

		List<IJson> list = new ArrayList<IJson>();
		//
		list.addAll(CreateJsonElements.initFields());

		//
		ItemCustomField icf = new ItemCustomField("custom field", list);

		String json = "{" + icf.toJSON() + "}";

		System.out.println(json);

		assertTrue(JsonValidator.isValidJson(json));

	}

	/**
	 * Testing, if valid JSON is produced: ItemCustomField with one list element.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test12() {

		List<IJson> list = new ArrayList<IJson>();
		//
		list.addAll(CreateJsonElements.initFields().subList(0, 1));

		//
		ItemCustomField icf = new ItemCustomField("custom field", list);

		String json = "{" + icf.toJSON() + "}";

		System.out.println(json);

		assertTrue(JsonValidator.isValidJson(json));

	}

	/**
	 * Testing, if valid JSON is produced: ItemCustomField with list and without name for list.
	 *
	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
	 *
	 */
	@Test
	void test13() {

		List<IJson> list = new ArrayList<IJson>();
		//
		list.addAll(CreateJsonElements.initFields().subList(0, 1));

		//
		ItemCustomField icf = new ItemCustomField("", list);

		String json = "{" + icf.toJSON() + "}";

		System.out.println(json);

		assertTrue(JsonValidator.isValidJson(json));

	}

//	/**
//	 * Testing, if valid JSON is produced: Item.
//	 *
//	 * Here: all elements according https://confluence.xpand-it.com/display/XRAY/Import+Execution+Results#ImportExecutionResults-XrayJSONformat
//	 *
//	 */
//	@Test
//	void test0x() {
//
//
//		assertTrue(this.isValidJson(.toJSON()));
//
//	}

}
