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

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;

import de.sulzer.REST.ConstantsURLs;
import de.sulzer.REST.common.ReceiveResponse;
import de.sulzer.REST.common.UtilitiesHttp;

/**
 * @author Sulzer GmbH
 *
 */
public final class SendTestRunResult {

	private UtilitiesHttp utilitiesHttp;
	private ReceiveResponse receiveResponse;

	private static final String HTTPS_CARIT_AUDI_DE_JIRA_REST_RAVEN_1_0_IMPORT_EXECUTION =
			ConstantsURLs.HOST_JIRA +
			ConstantsURLs.URL_JIRA_TEST_EXECUTION_IMPORT;

	public SendTestRunResult() {

		this.utilitiesHttp = new UtilitiesHttp();
		this.receiveResponse = new ReceiveResponse();

	}

	/**
	 * This method sends a JSON document to Jira server.
	 *
	 * throws declarations are intentionally.
	 *
	 * @param testExecKey
	 * @param keyStorePath
	 * @param keyStorePassword
	 * @param jsonDocument
	 * @param user
	 * @param pwd
	 * @return return response from REST service of Jira
	 * @throws IOException
	 * @throws CertificateException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyStoreException
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 */
	public String postJsonWithSSL(String testExecKey,
			                      Path keyStorePath,
			                      String keyStorePassword,
			                      String jsonDocument,
			                      String devstackApiToken)
			                    		  throws
			                    		  KeyManagementException,
			                    		  UnrecoverableKeyException,
			                    		  KeyStoreException,
			                    		  NoSuchAlgorithmException,
			                    		  CertificateException,
			                    		  IOException {

		System.out.println("***Jira 5");
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
			//System.out.println("***devstack64: " +base64DevstackApiToken);

			HttpPost httppost = new HttpPost(HTTPS_CARIT_AUDI_DE_JIRA_REST_RAVEN_1_0_IMPORT_EXECUTION);

			this.utilitiesHttp.createStandardHeaderFields(httppost, base64DevstackApiToken);

			httppost.setEntity(this.utilitiesHttp.creatingHttpEntity(jsonDocument));

			CloseableHttpResponse response = httpclient.execute(httppost);
			/*for (Header header: response.getAllHeaders()) {

					System.out.println("***Headers: " + header.getName() +"::" + header.getValue());

			}*/

			responseJson = this.receiveResponse.getReceiveResponse(response);
			//System.out.println("***responseJson: "+responseJson);

		} catch (Exception e){
			System.out.println("***Exception: " +e.getMessage() + "aaa: " +e.getLocalizedMessage());
		}

			finally {

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
