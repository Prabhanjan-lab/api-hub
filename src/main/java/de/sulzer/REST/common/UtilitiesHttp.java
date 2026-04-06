/**
 *
 */
package de.sulzer.REST.common;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.HttpMessage;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;

import de.sulzer.REST.ConstantsURLs;

/**
 * @author Sulzer GmbH
 *
 */
public final class UtilitiesHttp {

	public UtilitiesHttp() {

	}

	public CloseableHttpClient createHttpClientWithSSL(String keyStoreFile, String keyStorePassword) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, KeyManagementException, UnrecoverableKeyException {

		//
		SSLContext sslcontext;
		SSLConnectionSocketFactory sslsf;
		CloseableHttpClient httpclient = null;

		//
		char[] password = keyStorePassword.toCharArray();
		//
		final KeyStore clientStore = KeyStore.getInstance("JKS");
    	//
		final File file = new File(keyStoreFile);
		final FileInputStream inputStream = new FileInputStream(file);

		clientStore.load(inputStream, password);

		// Trust own CA and all self-signed certs
		sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy())
				.loadKeyMaterial(clientStore, password).build();

		// Allow TLSv1 protocol only
		sslsf = new SSLConnectionSocketFactory(
				sslcontext,
				new String[] { "TLSv1", "TLSv1.2" },
				null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());

		httpclient = HttpClients.custom().
				setSSLSocketFactory(sslsf).
				// dummy comment --> to un-comment
				//TODO: proxy auskommentiert!!! setProxy(new HttpHost(ConstantsURLs.PROXY_WEBPRX2_WEB_AUDI_VWG,ConstantsURLs.PROXY_PORT)).
				build();

		//
		return httpclient;
	}

	public String encodeToBase64(String devstackApiToken) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString((devstackApiToken).getBytes("UTF-8"));
	}

	/**
	 * Setting the credentials which are always needed.
	 *
	 * @param httpMessage
	 * @param base64DevstackApiToken
	 */
	public void createStandardHeaderFields(HttpMessage httpMessage, String base64DevstackApiToken) {

		httpMessage.setHeader("Authorization", "Basic " + base64DevstackApiToken);
		httpMessage.setHeader("Content-Type", "application/json");
		httpMessage.setHeader("Accept", "application/json");

	}

	/**
	 * Processing a given string (JSON document) into a http conform entity.
	 *
	 * see as sources:
	 * - http://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientChunkEncodedPost.java
	 * - https://www.mkyong.com/java/how-to-convert-string-to-inputstream-in-java/
	 * - https://stackoverflow.com/a/782183
	 *
	 * @return InputStreamEntity as paylod for an http request
	 */
	public InputStreamEntity creatingHttpEntity(String jsonDocument) {

        InputStreamEntity reqEntity = new InputStreamEntity(
        		new ByteArrayInputStream(jsonDocument.getBytes(StandardCharsets.UTF_8)));
        reqEntity.setChunked(true);

        return reqEntity;
	}

}
