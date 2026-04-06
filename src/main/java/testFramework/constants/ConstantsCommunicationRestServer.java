/**
 *
 */
package testFramework.constants;

/**
 * @author Bege
 *
 */
public class ConstantsCommunicationRestServer {

	public static final String FAULT_CODE_OUQA_10747 = "oruRelevanceDataSetType04Faulty";
	public static final String FAULT_CODE_OUQA_10738 = "criterionIdDataSetType02EmptyField";
	public static final String FAULT_CODE_OUQA_12099 = "criterionIdDataSetType02Faulty";
	public static final String FAULT_CODE_OUQA_10752 = "modelCodeEmpty";
	public static final String FAULT_CODE_OUQA_10923 = "dst01NoApprovalCountryInformation";
	public static final String FAULT_CODE_OUQA_2722 = "dst05ModelNonAscii";
	public static final String FAULT_CODE_OUQA_1115 = "dst04NoProgressField";

	/*
	 * REST related constants
	 */

	/*
	 * JSON related constants
	 */
	public static final String FAULT_CODE = "faultCode";
	public static final String PAYLOAD = "payload";

	public static final String TEMPFILE = "temp-file";
	public static final String FILENAME = "filename";
	public static final String EXTENSION_IGNORED_ZIP = "_ignored.zip";
	public static final String URL_FILE_PARAMETER = "?file=";
	public static final String DIRECTORY_DATA = "data/";
	public static final String BOOLEAN_TRUE = "true";
	public static final String PREFIX_FILE_TEMP = "temp";
	public static final String SUFFIX_FILE_MAINFRAME = ".mainframe";
	public static final String UTF_8 = "UTF-8";

	// for ReCall mass imports
	public static final String RECALLID = "recallId";
	public static final String COUNT = "count";
	public static final String UNIQUE_ID_PLACEHOLDER = "UNIQUE_ID";

	/*
	 * Waiting time for checking files on server
	 */
	public static final long ONE_MINUTES = 1000 * 60 * 1;
	public static final long TWO_MINUTES = 1000 * 60 * 2;
	public static final long THREE_MINUTES = 1000 * 60 * 3;
	public static final long FIVE_MINUTES = 1000 * 60 * 5;
	public static final long SIX_MINUTES = 1000 * 60 * 6;
	public static final long ELEVEN_MINUTES = 1000 * 60 * 11;
	public static final long FORTY_FIVE_MINUTES = 1000 * 60 * 45;

	public static final long TWO_AND_A_HALF_HOURS = (long) (1000 * 60 * 60 * 2.5f);
	public static final long THREE_HOURS = 1000 * 60 * 60 * 3;


	public static String getPath(String fileName) {
		String os = System.getProperty("os.name").toLowerCase();
		if (os.contains("linux")) {
			return (System.getProperty("user.dir") + "/selenium.gui.testing/data/importReCall/" + fileName);
		} else if (os.contains("windows")) {
			return "data/importReCall/" + fileName;
		} else {
			return "";
		}
	}

}
