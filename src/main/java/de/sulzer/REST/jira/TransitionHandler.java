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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import de.sulzer.REST.ConstantsURLs;
import de.sulzer.REST.common.ReceiveResponse;
import de.sulzer.REST.common.UtilitiesHttp;

/**
 * @author Bege
 *
 */
public final class TransitionHandler {

	private UtilitiesHttp utilitiesHttp;
	private ReceiveResponse receiveResponse;

	private static final String HTTPS_CARIT_AUDI_DE_JIRA_REST_API_2_ISSUE =
			ConstantsURLs.HOST_JIRA +
			ConstantsURLs.JIRA_REST_API_2_ISSUE;

	public TransitionHandler() {

		this.utilitiesHttp = new UtilitiesHttp();
		this.receiveResponse = new ReceiveResponse();

	}

	public String requestTransition(String testExecIssue,
			                           Path keyStorePath,
			                           String keyStorePassword,
			                           String devstackApiToken) throws KeyManagementException,
	                                                      UnrecoverableKeyException,
	                                                      KeyStoreException,
	                                                      NoSuchAlgorithmException,
	                                                      CertificateException,
	                                                      IOException {

		//
		String keyStoreFile = keyStorePath.toString();
		//
		CloseableHttpClient httpclient = null;
		//
		String responseJson = "";

		try {

			//
			httpclient = this.utilitiesHttp.createHttpClientWithSSL(keyStoreFile, keyStorePassword);

			HttpGet httpget = new HttpGet(HTTPS_CARIT_AUDI_DE_JIRA_REST_API_2_ISSUE + testExecIssue + ConstantsURLs.TRANSITIONS_SUFFIX_TEST_EXECUTION_ISSUE);

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

	public String doTransition(String testExecIssue,
			                          Path keyStorePath,
			                          String keyStorePassword,
			                          String jsonDocument,
			                          String devstackApiToken) throws KeyManagementException,
	                                                     UnrecoverableKeyException,
	                                                     KeyStoreException,
	                                                     NoSuchAlgorithmException,
	                                                     CertificateException,
	                                                     IOException {

		//
		String keyStoreFile = keyStorePath.toString();
		//
		CloseableHttpClient httpclient = null;
		//
		String responseJson = "";

		try {

			//
			httpclient = this.utilitiesHttp.createHttpClientWithSSL(keyStoreFile, keyStorePassword);

			HttpPost httppost = new HttpPost(HTTPS_CARIT_AUDI_DE_JIRA_REST_API_2_ISSUE + testExecIssue + ConstantsURLs.TRANSITIONS_EXPAND_TRANSITIONS_FIELDS);

			this.utilitiesHttp.createStandardHeaderFields(httppost, this.utilitiesHttp.encodeToBase64(devstackApiToken));

			httppost.setEntity(this.utilitiesHttp.creatingHttpEntity(jsonDocument));

			CloseableHttpResponse response = httpclient.execute(httppost);

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
