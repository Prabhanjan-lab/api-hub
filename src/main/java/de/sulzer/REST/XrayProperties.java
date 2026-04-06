/**
 *
 */
package de.sulzer.REST;

/**
 * This class serves as container for environment variables, requested from
 * environment (here Jenkins).
 *
 * @author Sulzer GmbH
 *
 */
public class XrayProperties {

	private String keyStorePath;
	private String keyStorePassword;
	private String jiraUser;
	private String jiraUserPassword;
	private String adminportletuser;
	private String adminportletuserpassword;

	/**
	 *
	 */
	public XrayProperties() {

	}

	/**
	 * @param keyStore
	 * @param keyStorePassword
	 * @param jiraUser
	 * @param jiraUserPassword
	 */
	public XrayProperties(String keyStore, String keyStorePassword, String jiraUser, String jiraUserPassword, String adminPortletUser, String adminPortletUserPassword) {
		super();
		this.keyStorePath = keyStore;
		this.keyStorePassword = keyStorePassword;
		this.jiraUser = jiraUser;
		this.jiraUserPassword = jiraUserPassword;
		this.adminportletuser =  adminPortletUser;
		this.adminportletuserpassword = adminPortletUserPassword;
	}

	/**
	 * @return the keyStore
	 */
	public String getKeyStorePath() {
		return keyStorePath;
	}

	/**
	 * @param keyStore the keyStore to set
	 */
	public void setKeyStorePath(String keyStore) {
		this.keyStorePath = keyStore;
	}

	/**
	 * @return the keyStorePassword
	 */
	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	/**
	 * @param keyStorePassword the keyStorePassword to set
	 */
	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	/**
	 * @return the jiraUser
	 */
	public String getJiraUser() {
		return jiraUser;
	}

	/**
	 * @param jiraUser the jiraUser to set
	 */
	public void setJiraUser(String jiraUser) {
		this.jiraUser = jiraUser;
	}

	/**
	 * @return the jiraUserPassword
	 */
	public String getJiraUserPassword() {
		return jiraUserPassword;
	}

	/**
	 * @param jiraUserPassword the jiraUserPassword to set
	 */
	public void setJiraUserPassword(String jiraUserPassword) {
		this.jiraUserPassword = jiraUserPassword;
	}

	/**
	 * @return the adminportletUser
	 */
	public String getAdminPortletUser() {
		return adminportletuser;
	}

	/**
	 * @param adminportletUser the adminportletUser to set
	 */
	public void setAdminPortletUser(String adminPortletuser) {
		this.adminportletuser = adminPortletuser;
	}

	/**
	 * @return the adminportletPassword
	 */
	public String getAdminPortletPassword() {
		return adminportletuserpassword;
	}

	/**
	 * @param adminportletUser the adminportletUserpassword to set
	 */
	public void setAdminPortletUserPassword(String adminPortletuserpassword) {
		this.adminportletuserpassword = adminPortletuserpassword;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jiraUser == null) ? 0 : jiraUser.hashCode());
		result = prime * result + ((jiraUserPassword == null) ? 0 : jiraUserPassword.hashCode());
		result = prime * result + ((keyStorePath == null) ? 0 : keyStorePath.hashCode());
		result = prime * result + ((keyStorePassword == null) ? 0 : keyStorePassword.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		XrayProperties other = (XrayProperties) obj;
		if (jiraUser == null) {
			if (other.jiraUser != null)
				return false;
		} else if (!jiraUser.equals(other.jiraUser))
			return false;
		if (jiraUserPassword == null) {
			if (other.jiraUserPassword != null)
				return false;
		} else if (!jiraUserPassword.equals(other.jiraUserPassword))
			return false;
		if (keyStorePath == null) {
			if (other.keyStorePath != null)
				return false;
		} else if (!keyStorePath.equals(other.keyStorePath))
			return false;
		if (keyStorePassword == null) {
			if (other.keyStorePassword != null)
				return false;
		} else if (!keyStorePassword.equals(other.keyStorePassword))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XrayProperties [keyStorePath=" + keyStorePath + ", keyStorePassword=" + keyStorePassword + ", jiraUser="
				+ jiraUser + ", jiraUserPassword=" + jiraUserPassword + "]";
	}

}
