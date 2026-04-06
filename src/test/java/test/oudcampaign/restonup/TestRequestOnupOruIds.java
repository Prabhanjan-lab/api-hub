/**
 *
 */
package test.oudcampaign.restonup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.sulzer.oudcampaign.restonup.RequestOnupOruIds;
/**
 * code stolen from:
 * 1) https://www.torsten-horn.de/techdocs/jee-rest.htm#JaxRsMitMaven
 * 2) https://examples.javacodegeeks.com/enterprise-java/rest/jersey/json-example-with-jersey-jackson/
 * 3) https://stackoverflow.com/a/32043313
 *
 * maybe useful:
 * https://howtodoinjava.com/jersey/jersey-restful-client-examples/
 *
 * @author Bege
 *
 */
public class TestRequestOnupOruIds {

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {

	}

	/**
	 * Testing (with WITHOUT ssl) connection to ONUP rest server (Audi ONUP).
	 * @throws Exception 
	 *
	 */
	@Test
	void testAudiOru() throws Exception {

		RequestOnupOruIds rooi = new RequestOnupOruIds();

		String response = rooi.requestOru(RequestOnupOruIds.ONUP_AUDI_v1);

		System.out.println(response);

	}

	/**
	 * Testing (with WITHOUT ssl) connection to ONUP rest server (Audi ONUP).
	 * 
	 * Recall ID used in ORU.
	 * @throws Exception 
	 *
	 */
	@Test
	void testAudiRecallOruUsedTrue() throws Exception {

		RequestOnupOruIds rooi = new RequestOnupOruIds();

		String response = rooi.requestRecallOruUsedTrue(RequestOnupOruIds.ONUP_AUDI_v1);

		System.out.println(response);

	}

	/**
	 * Testing (with WITHOUT ssl) connection to ONUP rest server (Audi ONUP).
	 * 
	 * Recall ID NOT used in ORU.
	 * @throws Exception 
	 *
	 */
	@Test
	void testAudiRecallOruUsedFalse() throws Exception {

		RequestOnupOruIds rooi = new RequestOnupOruIds();

		String response = rooi.requestRecallOruUsedFalse(RequestOnupOruIds.ONUP_AUDI_v1);

		System.out.println(response);

	}

	/**
	 * Testing (with WITHOUT ssl) connection to ONUP rest server (Audi ONUP).
	 * 
	 * Service42 ID newer than 100 days.
	 * @throws Exception 
	 *
	 */
	@Test
	void testAudiService42Newer100Days() throws Exception {

		RequestOnupOruIds rooi = new RequestOnupOruIds();

		String response = rooi.requestService42Newer100Days(RequestOnupOruIds.ONUP_AUDI_v1);

		System.out.println(response);

	}

	/**
	 * Testing (with WITHOUT ssl) connection to ONUP rest server (Audi ONUP).
	 *
	 * Service42 ID older than 100 days.
	 * @throws Exception 
	 * 
	 */
	@Test
	void testAudiService42Older100Days() throws Exception {

		RequestOnupOruIds rooi = new RequestOnupOruIds();

		String response = rooi.requestService42Older100Days(RequestOnupOruIds.ONUP_AUDI_v1);

		System.out.println(response);

	}

}
