package test.oudcampaign.idgenerator;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import de.sulzer.oudcampaign.idgenerator.model.Figure;
import de.sulzer.oudcampaign.idgenerator.values.Values;
import de.sulzer.oudcampaign.idprocessor.ContainerIdGenerator;
import de.sulzer.oudcampaign.idprocessor.ProcessIds;

class IdGenerator {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testBasic() {

		String startValue = "A9";
		String nextValue01 = "BA";
		String nextValue02 = "BB";

		Figure figure = new Figure(startValue, Values.lettersAZ09);

		// new figure 1
		figure.hasNext();
		String temp = figure.next();
		assertTrue(temp.equals(nextValue01), "expected value was '" + nextValue01 + "', but found '" + temp + "'.");

		// new figure 2
		figure.hasNext();
		temp = figure.next();
		assertTrue(temp.equals(nextValue02), "expected value was '" + nextValue02 + "', but found '" + temp + "'.");

	}

	@Test
	void testContainerGeneratorWithRegex() {

		String regex = "[A-Z0-9]{2}";
		
		Figure figure = new Figure("AA", Values.lettersAZ09);
		
		ContainerIdGenerator ci = new ContainerIdGenerator();
		
		Set<String> ids = ci.createIdList(figure, regex);
		
		assertTrue(ids.size() == 1296, "1296 ids expected, but found " + ids.size());
		
	}

	@Test
	void testContainerGeneratorWithoutRegexNull() {

		Figure figure = new Figure("AA", Values.lettersAZ09);
		
		ContainerIdGenerator ci = new ContainerIdGenerator();
		
		Set<String> ids = ci.createIdList(figure, null);
		
		assertTrue(ids.size() == 1296, "1296 ids expected, but found " + ids.size());
		
	}

	@Test
	void testContainerGeneratorWithoutRegexEmptyString() {

		Figure figure = new Figure("AA", Values.lettersAZ09);
		
		ContainerIdGenerator ci = new ContainerIdGenerator();
		
		Set<String> ids = ci.createIdList(figure, "");
		
		assertTrue(ids.size() == 1296, "1296 ids expected, but found " + ids.size());
		
	}

	/**
	 * Just the part with ID generation. NOT the part with requesting ID
	 * from ONUP, because those numbers are varying!
	 * 
	 * @throws Exception 
	 */
	@Test
//	@Disabled
	void testProcessIds() throws Exception {

		// loaded IDs from ONUP
		String regexOnup = "[T][A-Z0-9]{3}";
		// id range to generate
		String regexIdGenerator = "[T][B-Z0-9]{1}[A-Z0-9]{2}";
		
		Figure figure = new Figure("TBAA", Values.lettersAZ09);

		ProcessIds pi = new ProcessIds(regexOnup, regexIdGenerator, figure);

		System.out.println("loaded IDs from ONUP: " + pi.getONUPIds().size());
		System.out.println("ONUP IDs: " + pi.getONUPIds());
		System.out.println("generated IDs (left): " + pi.getGeneratedIds().size());
		
		// testing ascertain id
		System.out.println("first 100");
		for (int i = 0; i < 100; i++) {
			try {
				System.out.println(pi.ascertainNewId());
			} catch (Exception e) {
				System.out.println("No more IDs available.");
				break;
			}
		}
		
		// getting last 100 IDs
		System.out.println("last 100");
		for (int i = (pi.getListIds().size() - 100); i < pi.getListIds().size(); i++) {
			try {
				System.out.println(pi.getListIds().get(i));
			} catch (Exception e) {
				System.out.println("No more IDs available.");
				break;
			}
		}
		
	}

}
