/**
 *
 */
package de.sulzer.REST.jira;

import java.io.IOException;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import de.sulzer.REST.ConstantsURLs;
import de.sulzer.REST.common.ReceiveResponse;
import de.sulzer.REST.common.UtilitiesHttp;

/**
 * Component handler implement for requesting components from test.
 * Implementation according
 * https://developer.atlassian.com/cloud/jira/platform/rest/v3/?utm_source=%2Fcloud%2Fjira%2Fplatform%2Frest%2F&utm_medium=302#api-api-3-component-id-get
 *
 * @author Sulzer GmbH
 *
 */
public final class ComponentHandler {

	private UtilitiesHttp utilitiesHttp;
	private ReceiveResponse receiveResponse;

	private static final String HTTPS_CARIT_AUDI_DE_JIRA_REST_API_2_ISSUE =
			ConstantsURLs.HOST_JIRA +
			ConstantsURLs.JIRA_REST_API_2_ISSUE;

	public ComponentHandler() {

		this.utilitiesHttp = new UtilitiesHttp();
		this.receiveResponse = new ReceiveResponse();

	}

	public String requestTestCaseForComponents(
			String testCaseKey,
			Path keyStorePath,
			String keyStorePassword,
			String devstackApiToken)
					throws
					KeyManagementException,
					UnrecoverableKeyException,
					KeyStoreException,
					NoSuchAlgorithmException,
					CertificateException,
					IOException {

		//
		String base64DevstackApiToken;
		//
		String keyStoreFile = keyStorePath.toString();
		//
		CloseableHttpClient httpclient = null;
		//
		String responseJson = "";

		try {

			//
			httpclient = this.utilitiesHttp.createHttpClientWithSSL(keyStoreFile, keyStorePassword);
			//
			base64DevstackApiToken = this.utilitiesHttp.encodeToBase64(devstackApiToken);

			HttpGet httpget = new HttpGet(HTTPS_CARIT_AUDI_DE_JIRA_REST_API_2_ISSUE + testCaseKey);

			this.utilitiesHttp.createStandardHeaderFields(httpget, base64DevstackApiToken);

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
