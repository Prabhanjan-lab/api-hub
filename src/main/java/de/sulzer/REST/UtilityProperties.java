/**
 *
 */
package de.sulzer.REST;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Sulzer GmbH
 *
 */
public final class UtilityProperties {

	private UtilityProperties() {

	}

	/**
	 * Reading properties and return those values as string array.
	 *
	 * @return a string array with four values (key store path, key store password,
	 *         jira user, and jira user password)
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static XrayProperties getProperties() throws FileNotFoundException, IOException {

		String os = System.getProperty("os.name").toLowerCase();
		XrayProperties xrayProperties = new XrayProperties();

        // getting properties
		Properties properties = new Properties();
        Properties propertiesXray = new Properties();
        Properties propertiesAdminPortlet = new Properties();

		System.out.println("***user.dir: " +System.getProperty("user.dir"));

        /*// loading properties from file
		properties.load(new FileInputStream(new File(System.getProperty("user.dir")+"/target/classes/Xray_Login.properties")));
		//
		propertiesXray.load(new FileInputStream(new File(System.getProperty("user.dir")+"/target/classes/certificate.properties")));
		//
		propertiesAdminPortlet.load(new FileInputStream(new File(System.getProperty("user.dir")+"/target/classes/AdminPortlet_Login.properties")));
		*/

		// loading properties from file
		if (os.contains("linux")) {

			properties.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/Xray_Login.properties")));
			propertiesXray.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/certificate.properties")));
			propertiesAdminPortlet.load(new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/resources/AdminPortlet_Login.properties")));
		}
		if (os.contains("windows")) {

			properties.load(new FileInputStream(new File(System.getProperty("user.dir") + "/target/classes/Xray_Login.properties")));
			propertiesXray.load(new FileInputStream(new File(System.getProperty("user.dir") + "/target/classes/certificate.properties")));
			propertiesAdminPortlet.load(new FileInputStream(new File(System.getProperty("user.dir") + "/target/classes/AdminPortlet_Login.properties")));
		}

		// preparing array
		// key store path
		//TODO braucht man diese?

		/*xrayProperties.setKeyStorePath(propertiesXray.getProperty("CTAT.KeyStore"));
		// key store password
		xrayProperties.setKeyStorePassword(propertiesXray.getProperty("CTAT.KeyStore.Password"));
		// Jira user
		xrayProperties.setJiraUser(properties.getProperty("Jira_User_TA_OUD"));
		// Jira user password
		xrayProperties.setJiraUserPassword(properties.getProperty("Jira_Password_TA_OUD"));
		// Adminportlet user
		xrayProperties.setAdminPortletUser(propertiesAdminPortlet.getProperty("AdminPortlet_User_TA_OUD"));
		// Adminportletpassword
		xrayProperties.setAdminPortletUserPassword(propertiesAdminPortlet.getProperty("AdminPortlet_Password_TA_OUD"));
*/

		// key store path
		if (os.contains("linux")) {
//            xrayProperties.setKeyStorePath(propertiesXray.getProperty("CTAT.KeyStore"));
			xrayProperties.setKeyStorePath(propertiesXray.getProperty("CTAT_KeyStore"));
		}
		if (os.contains("windows")) {
			xrayProperties.setKeyStorePath(propertiesXray.getProperty("keyStorePath"));
		}

		// key store password
		if (os.contains("linux")) {
//            xrayProperties.setKeyStorePassword(propertiesXray.getProperty("CTAT.KeyStore.Password"));
			xrayProperties.setKeyStorePassword(propertiesXray.getProperty("CTAT_KeyStore_Password"));
		}
		if (os.contains("windows")) {
			xrayProperties.setKeyStorePassword(propertiesXray.getProperty("KeyStorePassword"));
		}

		// Jira user
		if (os.contains("linux")) {
			xrayProperties.setJiraUser(properties.getProperty("Jira_User_TA_OUD"));
//            xrayProperties.setJiraUser(System.getenv("Jira_User_TA_OUD"));
		}
		if (os.contains("windows")) {
			xrayProperties.setJiraUser(properties.getProperty("Jira_User"));
		}

		// Jira user password
		if (os.contains("linux")) {
			xrayProperties.setJiraUserPassword(properties.getProperty("Jira_Password_TA_OUD"));
//            xrayProperties.setJiraUserPassword(System.getenv("Jira_Password_TA_OUD"));
		}
		if (os.contains("windows")) {
			xrayProperties.setJiraUserPassword(properties.getProperty("Jira_Password"));
		}

		// Adminportlet user
		if (os.contains("linux")) {
			xrayProperties.setAdminPortletUser(propertiesAdminPortlet.getProperty("AdminPortlet_User_TA_OUD"));
//            xrayProperties.setAdminPortletUser(System.getenv("AdminPortlet_User_TA_OUD"));
		}
		if (os.contains("windows")) {
			xrayProperties.setAdminPortletUser(propertiesAdminPortlet.getProperty("AdminPortlet_User"));
		}

		// Adminportletpassword
		if (os.contains("linux")) {
			xrayProperties.setAdminPortletUserPassword(propertiesAdminPortlet.getProperty("AdminPortlet_Password_TA_OUD"));
//            xrayProperties.setAdminPortletUserPassword(System.getenv("AdminPortlet_Password_TA_OUD"));
		}
		if (os.contains("windows")) {
			xrayProperties.setAdminPortletUserPassword(propertiesAdminPortlet.getProperty("AdminPortlet_Password"));
		}

		return xrayProperties;
	}

}
