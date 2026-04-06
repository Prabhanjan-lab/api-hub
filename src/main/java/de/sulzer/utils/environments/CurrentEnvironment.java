/**
 *
 */
package de.sulzer.utils.environments;

import testFramework.StartClass;
import de.sulzer.utils.ImportantOnupURLs;

/**
 * @author GCH9F5D
 *
 */
public final class CurrentEnvironment {

	private static final String WEBCENTRE_DEMO_SERVICE42_ID = "3E2D";
	private static final String WEBCENTRE_APPROVAL_SERVICE42_ID = "369A";

	private CurrentEnvironment() {

	}

	public static synchronized String getCurrentEnvironmentLoginUrl() throws Exception {

		String env = StartClass.environment.toUpperCase();

        if("DEMO_ECE".equals(env)){
            return ImportantOnupURLs.LOGIN_URL_DEMO;
        } else if("APPROVAL_ECE".equals(env)){
            return ImportantOnupURLs.LOGIN_URL_APPROVAL;
        } else if("DEMO_LS".equals(env)){
            return ImportantOnupURLs.LOGIN_URL_LS_DEMO;
        } else if("APPROVAL_LS".equals(env)){
            return ImportantOnupURLs.LOGIN_URL_LS_APPROVAL;
        } else {
        	throw new Exception("Found environment '" + env + "' not recognized.");
        }

	}

    public static synchronized String getService42ByEnvironment() {

        String env = StartClass.environment.toUpperCase();

        if ("DEMO_ECE".equals(env)) {
    		return WEBCENTRE_DEMO_SERVICE42_ID;
        } else if ("APPROVAL_ECE".equals(env)) {
    		return WEBCENTRE_APPROVAL_SERVICE42_ID;
        } else {
        	return "";
        }

	}

}
