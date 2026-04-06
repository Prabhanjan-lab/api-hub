/**
 *
 */
package de.sulzer.utils.rest.id.util;

/**
 * @author Bege
 *
 */
public class ConstantsServices {

	//
	public static final String CONNECT = "/connect";

	// services around OUD
	public static final String OUD = "/oud";
	public static final String ROD = "/rod";

	// service for GoCat
	public static final String GOCAT = "/gocat";
	public static final String TRANSLATIONS = "/translations";

	// service for recall
	public static final String RECALL = "/recall";
	public static final String RCTEMPLATES = "/rctemplates";
	public static final String FAULTCODEWITHJSON = "/faultcodewithjson";

	public static final String CHECKFILE = "/checkfile";
	public static final String IGNOREFOLDER = "/ignorefolder";
	public static final String FILECONTENT = "/filecontent";

	// service for id generation OUD campaign
	public static final String IDGENERATOR = "/idgenerator";
	//
	public static final String REQUESTNEWID = "/requestNewId";
	public static final String REQUESTLASTID = "/requestLastId";
	//
	public static final String LOADIDS = "/loadIds";
	public static final String CLEANUPIDS = "/cleanUpIds";

	// services special cases for non-standard/-straight forward test cases
	public static final String OUQA2361 = "/ouqa2361";
	public static final String VINMASSIMPORT = "/vinmassimport";
	public static final String MAINFRAME_FILE = "/mainframeFile";

	// service for Admin Tool logs
	public static final String POST_ADMINTOOL_LOG = "/postadmintoollog";

	// versioning (common to all)
	public static final String V1 = "/v1";

}
