/**
 *
 */
package testFramework.connectionMockRestServer;

import de.sulzer.utils.rest.id.util.ConstantsServices;

/**
 * @author Bege
 *
 */
public final class ConstantsRestUrls {

	/*
	 * Base URL for REST service without SSH for Web Centre Demo.
	 */
	public static final String HOST_AND_PORT_DEMO_WEBCENTRE = "http://172.29.209.145:21000";

	/*
	 * Requesting a regular call of REST interface for ReCall.
	 */
	public static final String BASEURL_ReCall_v1 =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.RECALL +
			ConstantsServices.V1;

	/*
	 * Requesting data of faulty and ignored ReCall files.
	 */
	public static final String RECALL_POST_JSON_WITH_FAULTCODE =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.RECALL +
			ConstantsServices.FAULTCODEWITHJSON +
			ConstantsServices.V1;

	public static final String RECALL_GET_TEMPORARY_FILE =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.RECALL +
			ConstantsServices.CHECKFILE +
			ConstantsServices.V1;

	public static final String RECALL_POST_FILE_IGNORED =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.RECALL +
			ConstantsServices.CHECKFILE +
			ConstantsServices.IGNOREFOLDER +
			ConstantsServices.V1;

	public static final String RECALL_GET_FILE_CONTENT =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.RECALL +
			ConstantsServices.CHECKFILE +
			ConstantsServices.IGNOREFOLDER +
			ConstantsServices.FILECONTENT +
			ConstantsServices.V1;

	public static final String BASEURL_ReCall_OUQA2361 =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.RECALL +
			ConstantsServices.OUQA2361 +
			ConstantsServices.V1;

	public static final String RECALL_POST_ADMINTOOL_LOG =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.POST_ADMINTOOL_LOG +
			ConstantsServices.V1;

	public static final String RECALL_POST_MAINFRAME_FILE =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.RECALL +
			ConstantsServices.MAINFRAME_FILE +
			ConstantsServices.V1;

	public static final String RECALL_POST_VINMASSIMPORT =
			ConstantsServices.CONNECT +
			ConstantsServices.OUD +
			ConstantsServices.RECALL +
			ConstantsServices.VINMASSIMPORT +
			ConstantsServices.V1;

	/**
	 *
	 */
	private ConstantsRestUrls() {

	}

}
