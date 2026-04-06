/**
 * 
 */
package test.oudcampaign;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.sulzer.oudcampaign.CampaignIdOUDFileBased;

/**
 * @author GCH9F5D
 *
 */
public class TestCampaignIdFileBased {

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

	/**
	 * Test method for {@link de.sulzer.service.oudcampaign.CampaignIdOUDFileBased#getInstance()}.
	 * @throws Exception 
	 */
	@Test
	void testGetInstance() throws Exception {

		//
		int amount = 100;
		
		// init of campaing ID 'service'
		CampaignIdOUDFileBased.getInstance();
		
		Set<String> ids = new HashSet<>();

		try {
			for (int i = 0; i < amount; i++) {
				ids.add(CampaignIdOUDFileBased.getInstance().ascertainNewId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		assertTrue(ids.size() == amount, "expected " + amount + ", but received only " + ids.size());
		
		System.out.println(ids);
		
	}

}
