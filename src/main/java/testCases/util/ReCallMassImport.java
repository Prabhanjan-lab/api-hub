/**
 *
 */
package testCases.util;

import testFramework.constants.ConstantsCommunicationRestServer;

/**
 * @author Sulzer GmbH
 *
 */
public class ReCallMassImport {

	/**
	 *
	 */
	private ReCallMassImport() {

	}

	public static synchronized String createReCallMassImportJsonRequest(String id, int amountVins) {

		return "{\"" + ConstantsCommunicationRestServer.RECALLID + "\" : \"" + id + "\"," +
	            "\"" + ConstantsCommunicationRestServer.COUNT + "\" : " + amountVins + "}";

	}

}
