/**
 * 
 */
package de.sulzer.use.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import de.sulzer.model.IJson;
import de.sulzer.model.Info;
import de.sulzer.model.ItemCustomField;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.ItemExample;
import de.sulzer.model.ItemKeyValuePair;
import de.sulzer.model.ItemResult;
import de.sulzer.model.ItemStep;
import de.sulzer.model.ItemTest;
import de.sulzer.model.util.Base64Converter;

/**
 * @author Bege
 *
 */
public final class CreateJsonElements {

	private CreateJsonElements() {

	}

	public static void initInfo(Info info) {
		info.setDescription("test description");
		info.setFinishDate("test finish date");
		info.setProject("test project");
		info.setRevision("test revision 0.9");
		info.setStartDate("test start date");
		info.setSummary("test summary");
		info.setUser("test user");
		info.setVersion("test version");
		info.setReporter("test reporter");
		info.setAssginee("test assginee");
		info.setTestEnvironments("test testEnvironment"); // TODO --> Test-OUQA-6207-v7.json
	}

	public static List<ItemTest> initTests() {

		List<ItemTest> items = new ArrayList<>();

		ItemTest it = new ItemTest();
		//
		it.setComment("test comment");
		it.setExecutedBy("test execution by 2018-08-16");
		it.setFinish("test finish by 2018-08-16");
		it.setStart("test finish by 2018-08-16");
		it.setStatus("test status OK :)");
		it.setTestKey("test key 42");

		it.setItemsEvidences(initTestEvidence());

		it.setItemsResults(initTestResults());

		it.setItemsSteps(initTestSteps());

		it.setItemsExamples(initTestExamples());

		items.add(it);

		return items;
	}

	public static List<ItemEvidence> initTestEvidence() {

		List<ItemEvidence> items = new ArrayList<>();

		ItemEvidence ie1 = new ItemEvidence();
		ie1.setFilename("background2000.jpg");
		ie1.setContentType("image/jpeg");
		try {
			ie1.setData(Base64Converter.getImageAsBase64(new File("data/evidence/background2000.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ItemEvidence ie2 = new ItemEvidence();
		ie2.setFilename("795401804118.png");
		ie2.setContentType("image/png");
		try {
			ie2.setData(Base64Converter.getImageAsBase64(new File("data/evidence/795401804118.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		items.add(ie1);
		items.add(ie2);

		return items;
	}

	public static List<ItemResult> initTestResults() {

		List<ItemResult> items = new ArrayList<>();

		ItemResult ir1 = new ItemResult();
		ir1.setDuration("test result #1 duration");
		ir1.setLog("test result #1 log");
		ir1.setName("test result #1 name");
		ir1.setStatus("test result #1 status");
		ir1.setItemsExamples(initTestExamples());

		ItemResult ir2 = new ItemResult();
		ir2.setDuration("test result #2 duration");
		ir2.setLog("test result #2 log");
		ir2.setName("test result #2 name");
		ir2.setStatus("test result #2 status");
		ir2.setItemsExamples(initTestExamples());

		items.add(ir1);
		items.add(ir2);

		return items;
	}

	public static List<ItemExample> initTestExamples() {

		List<ItemExample> items = new ArrayList<>();

		ItemExample ie1 = new ItemExample();
		ie1.setType("test example #1 type");

		ItemExample ie2 = new ItemExample();
		ie2.setType("test example #2 type");

		items.add(ie1);
		items.add(ie2);

		return items;
	}

	public static List<ItemStep> initTestSteps() {

		List<ItemStep> items = new ArrayList<>();

		ItemStep is1 = new ItemStep();
		is1.setComment("test step #1 comment");
		is1.setStatus("test step #1 status");
		is1.setItemsEvidences(initTestEvidence());

		ItemStep is2 = new ItemStep();
		is2.setComment("test step #2 comment");
		is2.setStatus("test step #2 status");
		is2.setItemsEvidences(initTestEvidence());

		items.add(is1);
		items.add(is2);

		return items;
	}

	public static List<ItemCustomField> initFields() {

		List<ItemCustomField> items = new ArrayList<>();

		ItemCustomField icf;

		//
		icf = new ItemCustomField("customfield_13471");
		//
		icf.getItemCustomFields().add(new ItemKeyValuePair("self", "https:\\/\\/carit.audi.de\\/jira\\/rest\\/api\\/2\\/customFieldOption\\/12304"));
		icf.getItemCustomFields().add(new ItemKeyValuePair("id", "12304"));
		icf.getItemCustomFields().add(new ItemKeyValuePair("value", "ECE"));
		//
		items.add(icf);

		//
		icf = new ItemCustomField("customfield_13371");
		// intentionally commented for test, just as a reminder, what should be there normally
		//		icf.getItemCustomFields().add(new ItemKeyValuePair("self", "https:\\/\\/carit.audi.de\\/jira\\/rest\\/api\\/2\\/customFieldOption\\/12039"));
		//		icf.getItemCustomFields().add(new ItemKeyValuePair("id", "12039"));
		//		icf.getItemCustomFields().add(new ItemKeyValuePair("value", "Demo"));
		//
		items.add(icf);

		//
		icf = new ItemCustomField("customfield_13183");
		//
		icf.getItemCustomFields().add(new ItemKeyValuePair("self", "https:\\/\\/carit.audi.de\\/jira\\/rest\\/api\\/2\\/customFieldOption\\/12039"));
		icf.getItemCustomFields().add(new ItemKeyValuePair("id", "12039"));
		icf.getItemCustomFields().add(new ItemKeyValuePair("value", "Demo"));
		//
		items.add(icf);

		return items;
	}

}
