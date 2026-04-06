/**
 *
 */
package de.sulzer.REST;

/**
 * @author Sulzer GmbH
 *
 */
public class ConstantsURLs {

	/*
	 * Host
	 */
	public static final String HOST_JIRA = "https://devstack.vwgroup.com";
//	static final String HOST_JIRA = "https://carit.audi.de";

	/*
	 * Sending/creating new or updating existing test execution
	 */
	public static final String URL_JIRA_TEST_EXECUTION_IMPORT = "/jira/rest/raven/1.0/import/execution";

	/*
	 * Requesting certain test case
	 */
	public static final String URL_JIRA_TEST_KEYS = "/jira/rest/raven/1.0/api/test?keys=";

	/*
	 * Used for test cases, test executions transition ticket, test execution, ..
	 */
	public static final String JIRA_REST_API_2_ISSUE = "/jira/rest/api/2/issue/";

	/*
	 * For transition tickets for test executions
	 */
	public static final String TRANSITIONS_SUFFIX_TEST_EXECUTION_ISSUE = "/transitions";

	public static final String TRANSITIONS_EXPAND_TRANSITIONS_FIELDS = "/transitions?expand=transitions.fields";

	/*
	 * Proxy for communication with Jira in Audi(/VW) network.
	 */
	public static final String PROXY_WEBPRX1_WEB_AUDI_VWG = "webprx1.web.audi.vwg";
	public static final String PROXY_WEBPRX2_WEB_AUDI_VWG = "webprx2.web.audi.vwg";
	public static final int PROXY_PORT = 8080;

	/**
	 *
	 */
	private ConstantsURLs() {

	}

}
