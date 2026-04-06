/**
 * 
 */
package test.oudcampaign.pattern;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.sulzer.oudcampaign.idprocessor.ClaimONUPCampaignIds;

/**
 * @author GCH9F5D
 *
 */
class TestReadPattern {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	void testReadingRegExIdsOnly() throws Exception {

		String regex = "[T][A-Z0-9]{3}";
		
		ClaimONUPCampaignIds cci = new ClaimONUPCampaignIds(regex);
		
		Set<String> ids = cci.createIdList();
		
		for (String id : ids) {
			assertTrue(Pattern.matches(regex, id), "found id (" + id + ") is not compliant to " + regex);
		}
		
	}

}
