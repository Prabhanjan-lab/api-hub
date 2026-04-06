package testCases.oAuthTokenGeneration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import testCases.utils.proxySettings;
public class KeystoreAndSSLConnection{
	
public static KeyStore loadKeystore(String keystorePath, char[] password) throws Exception {
    // Check if the file exists
    File keystoreFile = new File(keystorePath);
    KeyStore keystore = KeyStore.getInstance("PKCS12");
    try (FileInputStream fis = new FileInputStream(keystoreFile)) {
        keystore.load(fis, password);
    }
    return keystore;
	}

public static String connectWithRelaxedMutualTLS(String url, String keystorePath, char[] password) throws Exception {
	File keystoreFile = new File(keystorePath);
    KeyStore keystore = KeyStore.getInstance("PKCS12");
    try (FileInputStream fis = new FileInputStream(keystoreFile)) {
        keystore.load(fis, password);
    }
    
 // Initialize KeyManager with your keystore
    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    kmf.init(keystore, password); // Use the same password if applicable
    
 // Initialize TrustManager (accept all or load a truststore as needed)
    TrustManager[] trustAllCerts = new TrustManager[] {
        new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
            public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
        }
    };
     
    // Set up SSLContext with KeyManagers for client authentication
    SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
    sslContext.init(kmf.getKeyManagers(), trustAllCerts, new SecureRandom());
    // Open HTTPS connection (without proxy)
    
    URL urlObject = new URL(url);
    HttpsURLConnection connection = (HttpsURLConnection) urlObject.openConnection(Proxy.NO_PROXY);
    connection.setSSLSocketFactory(sslContext.getSocketFactory());
    connection.setRequestMethod("GET");
    connection.connect();
   
    StringBuilder response = new StringBuilder();
    
    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
       String inputLine;
       while ((inputLine = in.readLine()) != null) {
           response.append(inputLine);
       }
    }
    String token= new JsonPath(response.toString()).getString("access_token");
    return token;
}
	 
public static String connectWithMutualTLS(String url, KeyStore keystore, char[] password) throws Exception {
	
    // Initialize SSL context
    SSLContext sslContext = SSLContext.getInstance("TLS");
    // Initialize KeyManagerFactory for mutual TLS
    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    kmf.init(keystore, password);
    // Initialize TrustManager (basic implementation, should be improved for production)
    TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] certs, String authType) {
            // Basic implementation, should verify client certificates
        }
        @Override
        public void checkServerTrusted(X509Certificate[] certs, String authType) {
            // Basic implementation, should verify server certificates
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }};
    sslContext.init(kmf.getKeyManagers(), trustManagers, new SecureRandom());
    // Set up proxy if needed
    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxySettings.getProxyHost(), Integer.parseInt(proxySettings.getProxyPort())));
    // Create HTTS connection
    URL urlObject = new URL(url);
    HttpsURLConnection connection = (HttpsURLConnection) urlObject.openConnection(proxy);
    // Set SSL socket factory
    connection.setSSLSocketFactory(sslContext.getSocketFactory());
    // Send request
    connection.setRequestMethod("GET");
    connection.connect();
    StringBuilder response = new StringBuilder();
    try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
    }
    String token= new JsonPath(response.toString()).getString("access_token");
    return token;
}
}