/**
 *
 */
package testFramework.connectionMockRestServer;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.json.JsonObject;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import de.sulzer.service.rest.services.authentication.jwt.JwtGenerator;

/**
 * Serves for communicating with own Mock-/REST-Server (mainy ReCall-, GoCat and
 * ID Generator services).
 *
 * @author Bege
 *
 */
public class CommunicatonRestServer {

	//
	private static final String HEADER = "{\"cty\":\"adminUiToken_v1_0_6\",\"kid\":\"CN=86111_MBBA_ONUP, OU=Unknown, O=Unknown, L=Unknown, ST=Bayern, C=DE\"}";

	/**
	 *
	 */
	public CommunicatonRestServer() {

	}

	/**
	 * Method returns a response (from POST) from given URL as JSON.
	 *
	 * Implementation based on:
	 * https://jersey.github.io/documentation/latest/client.html#d0e4859
	 *
	 * SSL implementation based on (trusts EVERY server with ANY certificate!):
	 * https://stackoverflow.com/a/1828840
	 *
	 * @param url
	 * @return Response
	 * @throws Exception
	 */
	public Response doPostApplicationJson(String url, String content) throws Exception {
		return doPostMessage(url, content, MediaType.APPLICATION_JSON);
	}

	public Response doPostApplicationJson(String url, JsonObject json) throws Exception {
		return this.doPostApplicationJson(url, json.toString());
	}

	/**
	 * Method returns a response (from POST) from given URL as JSON.
	 *
	 * Implementation based on:
	 * https://jersey.github.io/documentation/latest/client.html#d0e4859
	 *
	 * SSL implementation based on (trusts EVERY server with ANY certificate!):
	 * https://stackoverflow.com/a/1828840
	 *
	 * @param url
	 * @return Response
	 * @throws Exception
	 */
	public Response doPostTextPlain(String url, String content) throws Exception {
		return doPostMessage(url, content, MediaType.TEXT_PLAIN);
	}

	/**
	 * @param url
	 * @param content
	 * @param contentType
	 * @return
	 * @throws Exception
	 */
	private Response doPostMessage(String url, String content, String contentType) throws Exception {

		try {

			SSLContext sslCtx = SSLContext.getInstance("SSL");
			TrustManager tm = new TrustThemAll();
			sslCtx.init(null, new TrustManager[] {tm}, new SecureRandom());

			Client client = ClientBuilder.newBuilder().
				sslContext(sslCtx).
				build();

			WebTarget webTarget = client.target(url);

			//
			Invocation.Builder invocationBuilder;

			String token = new JwtGenerator().generateJwt(HEADER, "");

			invocationBuilder =
					webTarget.
					request(contentType).
					header("Authorization", token);

			Response response = invocationBuilder.post(Entity.entity(content, contentType));

			return response;

		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e);
		} catch (KeyManagementException e) {
			throw new Exception(e);
		}

	}

	/**
	 * Method returns a response (from GET) from given URL as JSON.
	 *
	 * Implementation based on:
	 * https://jersey.github.io/documentation/latest/client.html#d0e4859
	 *
	 * SSL implementation based on (trusts EVERY server with ANY certificate!):
	 * https://stackoverflow.com/a/1828840
	 *
	 * @param url
	 * @return Response
	 * @throws Exception
	 */
	public Response doGet(String url) throws Exception {

		try {

			SSLContext sslCtx = SSLContext.getInstance("SSL");
			TrustManager tm = new TrustThemAll();
			sslCtx.init(null, new TrustManager[] {tm}, new SecureRandom());

			Client client = ClientBuilder.newBuilder().
				sslContext(sslCtx).
				build();

			WebTarget webTarget = client.target(url);

			//
			Invocation.Builder invocationBuilder;

				String token = new JwtGenerator().generateJwt(HEADER, "");

				invocationBuilder =
						webTarget.
						request().
						header("Authorization", token);

			Response response = invocationBuilder.get();

			return response;

		} catch (NoSuchAlgorithmException e) {
			throw new Exception(e);
		} catch (KeyManagementException e) {
			throw new Exception(e);
		}

	}

	/**
	 * following code more or less based on:
	 * https://stackoverflow.com/a/1828840
	 *
	 * ATTENTION: With this code, EVERY certificate is trusted!
	 *
	 * @author GCH9F5D
	 *
	 */
	private static class TrustThemAll implements X509TrustManager {

	    public TrustThemAll() {
	    }

	    @Override
	    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	    }

	    @Override
	    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	    }

	    @Override
	    public X509Certificate[] getAcceptedIssuers() {
	        return null;
	    }
	}

}
