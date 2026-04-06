/**
 *
 */
package testCases.testplanreporting.output;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;

/**
 * @author Sulzer GmbH
 *
 */
public class FileLoader {

	/**
	 *
	 */
	public FileLoader() {

	}

	/**
	 * Current code based on https://stackoverflow.com/a/10960438
	 *
	 * @param fileUrl
	 * @param directory
	 * @param hrv
	 * @param properties
	 * @param devstackApiToken
	 * @return
	 * @throws IOException
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 */
	public File loadFileByLink(
			String fileUrl,
			File directory,
			HandlerRestVariable hrv,
			XrayProperties properties,
			String devstackApiToken) throws IOException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {

		File file = hrv.getResultByUrlAsFile(
				fileUrl,
				properties,
				devstackApiToken);

		return file;

	}

}
