/**
 *
 */
package de.sulzer.oudcampaign.idprocessor;

import de.sulzer.oudcampaign.idgenerator.model.Figure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author GCH9F5D
 *
 */
public class ProcessIds {

	private Set<String> ONUPIds;
	private Set<String> generatedIds;
	private List<String> listIds;

	/**
	 * @throws Exception
	 *
	 */
	public ProcessIds(String regexOnup, String regexIdGenerator, Figure figure) throws Exception {

		super();

		this.listIds = new ArrayList<String>();

		this.init(regexOnup, regexIdGenerator, figure);

	}

	/**
	 * @param regex
	 * @param figure
	 * @throws Exception
	 */
	private void init(String regexOnup, String regexIdGenerator, Figure figure) throws Exception {

		ClaimONUPCampaignIds cci = new ClaimONUPCampaignIds(regexOnup);
		this.ONUPIds = cci.createIdList();

		ContainerIdGenerator cig = new ContainerIdGenerator();
		this.generatedIds = cig.createIdList(figure, regexIdGenerator);

		this.generatedIds.removeAll(this.ONUPIds);
		this.listIds.addAll(this.generatedIds);
		Collections.sort(this.listIds);

	}

	public String ascertainNewId() throws Exception {
		return this.listIds.remove(0);
	}

	/**
	 * @return the oNUPIds
	 */
	public Set<String> getONUPIds() {
		return ONUPIds;
	}

	/**
	 * @return the generatedIds
	 */
	public Set<String> getGeneratedIds() {
		return generatedIds;
	}

	/**
	 * @return the listIds
	 */
	public List<String> getListIds() {
		return listIds;
	}

}
