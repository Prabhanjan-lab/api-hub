/**
 *
 */
package de.sulzer.oudcampaign;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import de.sulzer.oudcampaign.idgenerator.model.Figure;
import de.sulzer.oudcampaign.idgenerator.values.Values;
import de.sulzer.oudcampaign.idprocessor.ProcessIds;

/**
 * @author GCH9F5D
 *
 */
public class CampaignIdOUD {

	private static CampaignIdOUD campaignIdOUD;

	private static ProcessIds processIds;

	private Lock lock = new ReentrantLock(true);

	/**
	 *
	 */
	private CampaignIdOUD() {

		// loaded IDs from ONUP
		String regexOnup = "[T][B-Z0-9]{1}[A-Z0-9]{2}";
		// id range to generate
		String regexIdGenerator = "[T][B-Z0-9]{1}[A-Z0-9]{2}";

		Figure figure = new Figure("TBAA", Values.lettersAZ09);

		try {
			processIds = new ProcessIds(regexOnup, regexIdGenerator, figure);
		} catch (Exception e) {
			System.out.println("NOT ABLE TO GENERATE ID-LIST!");
			e.printStackTrace();
		}

	}

	public static CampaignIdOUD getInstance() {
		if (null == campaignIdOUD) {
			campaignIdOUD = new CampaignIdOUD();
		}
		return campaignIdOUD;
	}

	public String ascertainNewId() throws Exception {

		this.lock.lock();

		try {
			return processIds.ascertainNewId();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			this.lock.unlock();
		}

	}

}
