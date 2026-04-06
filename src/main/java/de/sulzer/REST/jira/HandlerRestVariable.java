package de.sulzer.REST.jira;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;

import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.common.ReceiveResponse;
import de.sulzer.REST.common.UtilitiesHttp;

/**
 * @author Sulzer GmbH
 *
 */
public class HandlerRestVariable {

	private UtilitiesHttp utilitiesHttp;
	private ReceiveResponse receiveResponse;

	public HandlerRestVariable() {

		this.utilitiesHttp = new UtilitiesHttp();
		this.receiveResponse = new ReceiveResponse();

	}

	public String getResultByUrl(
			String url,
			XrayProperties properties,
			String devstackApiToken) throws
		KeyManagementException,
		UnrecoverableKeyException,
		KeyStoreException,
		NoSuchAlgorithmException,
		CertificateException,
		IOException {

		//
		String keyStoreFile = properties.getKeyStorePath();
		//
		CloseableHttpClient httpclient = null;
		//
		String responseJson = "";

		try {

			HttpGet httpget = new HttpGet(url.replace(" ", "&"));

			httpclient = this.utilitiesHttp.createHttpClientWithSSL(keyStoreFile, properties.getKeyStorePassword());

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

		return responseJson;

	}

	public File getResultByUrlAsFile (
			String url,
			XrayProperties properties,
			String devstackApiToken) throws
		KeyManagementException,
		UnrecoverableKeyException,
		KeyStoreException,
		NoSuchAlgorithmException,
		CertificateException,
		IOException {

		//
		String keyStoreFile = properties.getKeyStorePath();
		//
		CloseableHttpClient httpclient = null;
		//

		File fileToBeLoaded = new File("tempfile");

		try {

			System.out.println(url);

			HttpGet httpget = new HttpGet(url.replace(" ", "&"));

			httpclient = this.utilitiesHttp.createHttpClientWithSSL(keyStoreFile, properties.getKeyStorePassword());

			this.utilitiesHttp.createStandardHeaderFields(httpget, this.utilitiesHttp.encodeToBase64(devstackApiToken));

			CloseableHttpResponse response = null;

			try {
				response = httpclient.execute(httpget);
			} catch (ClientProtocolException e) {
				return null;
			}

			InputStream inputStream = response.getEntity().getContent();

			if (fileToBeLoaded.exists()) {
				fileToBeLoaded.delete();
			}

			BufferedInputStream bis = new BufferedInputStream(inputStream);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileToBeLoaded));

			int inByte;

			while((inByte = bis.read()) != -1) {
				bos.write(inByte);
			}

			if (null != bis) {
				bis.close();
			}

			if (null != bos) {
				bos.close();
			}

			if (null != inputStream) {
				inputStream.close();
			}

		} finally {

			try {

				if (httpclient != null) {
					httpclient.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return fileToBeLoaded;

	}

}