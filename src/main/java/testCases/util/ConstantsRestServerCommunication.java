/**
 *
 */
package testCases.util;

import testFramework.connectionMockRestServer.ConstantsRestUrls;

/**
 * @author Bege
 *
 */
public class ConstantsRestServerCommunication {

	// base URL for REST service without SSH
	public static final String HOST_AND_PORT = ConstantsRestUrls.HOST_AND_PORT_DEMO_WEBCENTRE;
	//
	public static final String BASEURL_ReCall_POST_CORRECT_RECALL_JSON =
			ConstantsRestUrls.HOST_AND_PORT_DEMO_WEBCENTRE +
			ConstantsRestUrls.BASEURL_ReCall_v1;

	public static final String BASEURL_ReCall_POST_JSON_WITH_FAULTCODE =
			HOST_AND_PORT +
			ConstantsRestUrls.RECALL_POST_JSON_WITH_FAULTCODE;

	public static final String BASEUR_ReCall_GET_TEMPORARY_FILE =
			HOST_AND_PORT +
			ConstantsRestUrls.RECALL_GET_TEMPORARY_FILE;

	public static final String BASEUR_ReCall_POST_FILE_IGNORED =
			HOST_AND_PORT +
			ConstantsRestUrls.RECALL_POST_FILE_IGNORED;

	public static final String BASEUR_ReCall_GET_FILE_CONTENT =
			HOST_AND_PORT +
			ConstantsRestUrls.RECALL_GET_FILE_CONTENT;

	public static final String BASEURL_POST_ADMINTOOL_LOG =
			HOST_AND_PORT +
			ConstantsRestUrls.RECALL_POST_ADMINTOOL_LOG;

	public static final String BASEURL_POST_MAINFRAME_FILE =
			HOST_AND_PORT +
			ConstantsRestUrls.RECALL_POST_MAINFRAME_FILE;

	public static final String BASEURL_ReCall_POST_VINMASSIMPORT_JSON =
			HOST_AND_PORT +
			ConstantsRestUrls.RECALL_POST_VINMASSIMPORT;

}
