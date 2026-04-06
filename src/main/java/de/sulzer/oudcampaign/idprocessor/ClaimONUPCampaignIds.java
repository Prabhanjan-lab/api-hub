/**
 *
 */
package de.sulzer.oudcampaign.idprocessor;

import de.sulzer.oudcampaign.restonup.RequestOnupOruIds;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author GCH9F5D
 *
 */
public class ClaimONUPCampaignIds {

	private RequestOnupOruIds requestOnupOruIds;

	String[] brands = {RequestOnupOruIds.ONUP_AUDI_v1, RequestOnupOruIds.ONUP_VW_v1, RequestOnupOruIds.ONUP_PORSCHE_v1, RequestOnupOruIds.ONUP_BENTLEY_v1,
			RequestOnupOruIds.ONUP_LAMBO_v1,RequestOnupOruIds.ONUP_SEAT_v1,RequestOnupOruIds.ONUP_SKODA_v1,RequestOnupOruIds.ONUP_VWN_v1};

	private String regexId;

	/**
	 *
	 */
	public ClaimONUPCampaignIds(String regexId) {

		super();

		this.requestOnupOruIds = new RequestOnupOruIds();

		this.regexId = regexId;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		ClaimONUPCampaignIds cci = new ClaimONUPCampaignIds("[T][A-Z0-9]{3}");
		ClaimONUPCampaignIds cci = new ClaimONUPCampaignIds("TA[A-Z0-9]{2}");

		Set<String> ids;
		try {
			ids = cci.createIdList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ids = new HashSet<String>();
		}

		System.out.println("Number count of ORU/Recall/Service42 IDs: " + ids.size());

	}

	public Set<String> createIdList() throws Exception {
		Set<String> allIds = new HashSet<>();

		//
		allIds.addAll(this.loadOruCampaignIds());
		//
		allIds.addAll(this.loadRecallIds());
		//
		allIds.addAll(this.loadService42Ids());

		return allIds;
	}

	private Collection<? extends String> loadOruCampaignIds() throws Exception {

		Set<String> allIds = new HashSet<>();
		JsonObject jo = null;

		for (String brand : this.brands) {

//			String jsonResponse = this.requestOnupOruIds.requestOru(brand);
//			System.out.println("JSON Response for " + brand + ": " + jsonResponse);


			JsonReader jsonReader = Json.createReader(new StringReader(this.requestOnupOruIds.requestOru(brand)));
			jo = jsonReader.readObject();
			jsonReader.close();

			allIds.addAll(this.readIds(jo, "campaignNumber"));
		}

		return allIds;
	}

	private Collection<? extends String> loadRecallIds() throws Exception {

		Set<String> allIds = new HashSet<>();

		for (String brand : this.brands) {

			// read used IDs
			JsonReader jsonReader = Json.createReader(new StringReader(
					this.requestOnupOruIds.requestRecallOruUsedTrue(brand)));
			JsonObject jo = jsonReader.readObject();
			jsonReader.close();
			//
			allIds.addAll(this.readIds(jo, "id"));


			// read unused IDs
			jsonReader = Json.createReader(new StringReader(
					this.requestOnupOruIds.requestRecallOruUsedFalse(brand)));
			jo = jsonReader.readObject();
			jsonReader.close();
			//
			allIds.addAll(this.readIds(jo, "id"));

			// read used IDs (from archived IDs/campaigns)
			jsonReader = Json.createReader(new StringReader(
					this.requestOnupOruIds.requestRecallArchiveOruNewer100Days(brand)));
			jo = jsonReader.readObject();
			jsonReader.close();
			//
			allIds.addAll(this.readIds(jo, "id"));


			// read unused IDs (from archived IDs/campaigns)
			jsonReader = Json.createReader(new StringReader(
					this.requestOnupOruIds.requestRecallArchiveOruOlder100Days(brand)));
			jo = jsonReader.readObject();
			jsonReader.close();
			//
			allIds.addAll(this.readIds(jo, "id"));

		}

		return allIds;
	}

	private Collection<? extends String> loadService42Ids() throws Exception {

		Set<String> allIds = new HashSet<>();

		for (String brand : this.brands) {

			// read used IDs
			JsonReader jsonReader = Json.createReader(new StringReader(
					this.requestOnupOruIds.requestService42Newer100Days(brand)));
			JsonObject jo = jsonReader.readObject();
			jsonReader.close();
			//
			allIds.addAll(this.readIds(jo, "amCode"));


			// read unused IDs
			jsonReader = Json.createReader(new StringReader(
					this.requestOnupOruIds.requestService42Older100Days(brand)));
			jo = jsonReader.readObject();
			jsonReader.close();
			//
			allIds.addAll(this.readIds(jo, "amCode"));

		}

		return allIds;
	}

	/**
	 * Because of data structure, following IDs are read by this method:
	 * - ORU campaign IDs
	 * - Recall IDs
	 *
	 * @param jo
	 * @return HashSet with found IDs (obviously without duplicates).
	 */
	private Collection<? extends String> readIds(JsonObject jo, String searchId) {

		Set<String> ids = new HashSet<>();

		// reading JSON array see:
		// https://www.oracle.com/technetwork/articles/java/json-1973242.html
		// Listing 3
		JsonArray data = jo.getJsonArray("data");

		if (null == data || data.isEmpty()) {
			return ids;
		}

		for (JsonObject element : data.getValuesAs(JsonObject.class)) {

			String str = element.getString(searchId).trim();

			if (null != this.regexId &&
					this.regexId.length() > 0) {

				if (Pattern.matches(this.regexId, str)) {
					ids.add(str);
				}

			} else {
				ids.add(str);
			}

		}

		return ids;
	}

}
