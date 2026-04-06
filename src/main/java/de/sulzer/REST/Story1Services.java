/**
 *
 */
package de.sulzer.REST;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import de.sulzer.REST.common.ReceiveResponse;
import de.sulzer.REST.common.UtilitiesHttp;

/**
 * @author Sulzer GmbH
 *
 */
public class Story1Services {

	private UtilitiesHttp utilitiesHttp;
	private ReceiveResponse receiveResponse;

	private static final String HTTPS_CARIT_AUDI_DE_JIRA_REST_RAVEN_1_0_API_TEST_KEYS =
			ConstantsURLs.HOST_JIRA +
			ConstantsURLs.URL_JIRA_TEST_KEYS;

	public Story1Services() {
		this.utilitiesHttp = new UtilitiesHttp();
		this.receiveResponse = new ReceiveResponse();
	}

	/**
	 * This method sends a JSON document to Jira server.
	 *
	 * throws declarations are intentionally.
	 *
	 * @param ouqaNumber
	 * @param properties
	 * @param devstackApiToken
	 * @return
	 * @throws KeyManagementException
	 * @throws UnrecoverableKeyException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 * @throws IOException
	 */
	public String getStepCountOfTestCase(
							String ouqaNumber,
							XrayProperties properties,
							String devstackApiToken) throws KeyManagementException,
											                  UnrecoverableKeyException,
											                  KeyStoreException,
											                  NoSuchAlgorithmException,
											                  CertificateException,
											                  IOException {

		//
		String keyStoreFile = properties.getKeyStorePath().toString();
		//
		CloseableHttpClient httpclient = null;
		//
		String responseJson = "";

		try {

			//
			httpclient = this.utilitiesHttp.createHttpClientWithSSL(keyStoreFile, properties.getKeyStorePassword());

			HttpGet httpget = new HttpGet(HTTPS_CARIT_AUDI_DE_JIRA_REST_RAVEN_1_0_API_TEST_KEYS + ouqaNumber);

			this.utilitiesHttp.createStandardHeaderFields(httpget, this.utilitiesHttp.encodeToBase64(devstackApiToken));

			CloseableHttpResponse response = httpclient.execute(httpget);

			responseJson = this.receiveResponse.getReceiveResponse(response);

		} finally {

			try {

				if (httpclient != null) {
					httpclient.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// returning response from
		return responseJson;

	}


}
