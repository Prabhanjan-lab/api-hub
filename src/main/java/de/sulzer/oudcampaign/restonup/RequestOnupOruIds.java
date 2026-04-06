/**
 *
 */
package de.sulzer.oudcampaign.restonup;

import de.sulzer.service.rest.services.authentication.jwt.JwtGenerator;
import testFramework.StartClass;
import testFramework.utilities.properties.PropertyReader;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * code stolen from:
 * 1) https://www.torsten-horn.de/techdocs/jee-rest.htm#JaxRsMitMaven
 * 2) https://examples.javacodegeeks.com/enterprise-java/rest/jersey/json-example-with-jersey-jackson/
 * 3) https://stackoverflow.com/a/32043313
 * <p>
 * maybe useful:
 * https://howtodoinjava.com/jersey/jersey-restful-client-examples/
 *
 * @author Bege
 */
public class RequestOnupOruIds {

    // base URL for REST service without SSH
    private static String BASEURL_ONUP_v1;
    private static final String DEMO_BASE = "https://tui2-mbbonup04.audi-connect.web.audi.vwg/onup/api/bs/oru/v1/";
    //private static final String DEMO_BASE = "https://tui2-mbbonup-mgmt04.audi-connect.web.audi.vwg/onup/api/bs/oru/v1/";
    private static final String APPROVAL_BASE = "https://pre2-mbbonup04.audi-connect.web.audi.vwg/onup/api/bs/oru/v1/";
    //private static final String APPROVAL_BASE = "https://pre2-mbbonup-mgmt04.audi-connect.web.audi.vwg/onup/api/bs/oru/v1/";
    private static final String DEMO_ODP = "https://mbbonupadmin.moria.dmo.apl.eu.dp.odp.cloud.vwgroup.com/onup/api/bs/oru/v1/";
    private static final String APPROVAL_ODP = "https://mbbonupadmin.moria.app.apl.eu.dp.odp.cloud.vwgroup.com/onup/api/bs/oru/v1/";
    //
    public static final String ONUP_AUDI_v1 = "A/";
    public static final String ONUP_VW_v1 = "V/";
    public static final String ONUP_PORSCHE_v1 = "P/";
    public static final String ONUP_VWN_v1 = "N/";
    public static final String ONUP_SEAT_v1 = "S/";
    public static final String ONUP_SKODA_v1 = "C/";
    public static final String ONUP_BENTLEY_v1 = "E/";
    public static final String ONUP_LAMBO_v1 = "L/";
    //
    private static final String ORU = "oru";
    //
    private static final String RECALL = "recalls/inbox";
    private static final String RECALL_ORU_USED_TRUE = "?oruUsed=true";
    private static final String RECALL_ORU_USED_FALSE = "?oruUsed=false";
    //
    private static final String RECALL_ARCHIVE = "recalls/inbox/archive";
    private static final String RECALL_ORU_OLDER100DAYS_TRUE = "?olderThan100Days=true";
    private static final String RECALL_ORU_OLDER100DAYS_FALSE = "?olderThan100Days=false";
    //
    private static final String SERVICE42 = "service42/inbox";
    private static final String SERVICE42_OLDER100DAYS_TRUE = "?olderThan100Days=true";
    private static final String SERVICE42_OLDER100DAYS_FALSE = "?olderThan100Days=false";
    //
    private static final String HEADER = "{\"cty\":\"adminUiToken_v1_0_6\",\"kid\":\"CN=86111_MBBA_ONUP, OU=Unknown, O=Unknown, L=Unknown, ST=Bayern, C=DE\"}";
    PropertyReader pr = new PropertyReader();

    public RequestOnupOruIds() {

        String os = System.getProperty("os.name").toLowerCase();
        String environment = StartClass.environment.toUpperCase();

        PropertyReader pr = new PropertyReader();

        // Base Demo
        switch (environment) {
            case "DEMO_ECE":
                BASEURL_ONUP_v1 = DEMO_BASE;
                // Base Approval
                break;
            case "APPROVAL_ECE":
                BASEURL_ONUP_v1 = APPROVAL_BASE;
                // ODP Demo
                break;
            case "DEMO_LS":
                BASEURL_ONUP_v1 = DEMO_ODP;
                // ODP Approval
                break;
            case "APPROVAL_LS":
                BASEURL_ONUP_v1 = APPROVAL_ODP;
                break;
            default:  // Base Demo
                BASEURL_ONUP_v1 =
                        DEMO_BASE;
                break;
        }

        System.out.println("Reading IDs (ReCall, Service42, ORU) from server system (loading from sytem/environment '" + environment + "').");

    }

    public String requestOru(String brand) throws Exception {
        return this.requestGet(BASEURL_ONUP_v1 + brand + ORU).readEntity(String.class);
    }

    public String requestRecallOruUsedTrue(String brand) throws Exception {
        return this.requestGet(BASEURL_ONUP_v1 + brand + RECALL + RECALL_ORU_USED_TRUE).readEntity(String.class);
    }

    public String requestRecallOruUsedFalse(String brand) throws Exception {
        return this.requestGet(BASEURL_ONUP_v1 + brand + RECALL + RECALL_ORU_USED_FALSE).readEntity(String.class);
    }

    public String requestRecallArchiveOruOlder100Days(String brand) throws Exception {
        return this.requestGet(BASEURL_ONUP_v1 + brand + RECALL_ARCHIVE + RECALL_ORU_OLDER100DAYS_TRUE).readEntity(String.class);
    }

    public String requestRecallArchiveOruNewer100Days(String brand) throws Exception {
        return this.requestGet(BASEURL_ONUP_v1 + brand + RECALL_ARCHIVE + RECALL_ORU_OLDER100DAYS_FALSE).readEntity(String.class);
    }

    public String requestService42Older100Days(String brand) throws Exception {
        return this.requestGet(BASEURL_ONUP_v1 + brand + SERVICE42 + SERVICE42_OLDER100DAYS_TRUE).readEntity(String.class);
    }

    public String requestService42Newer100Days(String brand) throws Exception {
        return this.requestGet(BASEURL_ONUP_v1 + brand + SERVICE42 + SERVICE42_OLDER100DAYS_FALSE).readEntity(String.class);
    }


    /**
     * Sends a GET request to the specified URL using a secure HTTP client.
     * This method sets up an SSLContext with default TrustManagers to ensure
     * proper certificate validation and prevent man-in-the-middle attacks.
     *
     * @param url The URL to send the GET request to.
     * @return The HTTP response from the server.
     * @throws Exception If an error occurs during the request.
     */
    private Response requestGet(String url) throws Exception {
        try {
        	 String adminPortletKeystorePath="";
        	 String os = System.getProperty("os.name").toLowerCase();
        	if (os.contains("linux")) {
        		adminPortletKeystorePath=System.getProperty("user.dir") +"/data/tempFolder/mycacerts.jks";
	            // assuming running locally with Windows
	        } else if (os.contains("windows")) {
	        	adminPortletKeystorePath=System.getProperty("user.dir") +"\\data\\tempFolder\\mycacerts.jks";
	        }
            // Load the custom keystore (mycacerts)
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            String password = "changeit";
           
            FileInputStream fis = new FileInputStream(adminPortletKeystorePath);
            keyStore.load(fis, password.toCharArray());

            // Initialize TrustManagerFactory with the custom keystore
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // Initialize SSLContext with the custom TrustManagers
            SSLContext sslCtx = SSLContext.getInstance("SSL");
            sslCtx.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());

            // Build the HTTP client with the SSLContext
            Client client = ClientBuilder.newBuilder()
                    .sslContext(sslCtx)
                    .build();

            // Create the WebTarget with the specified URL
            WebTarget webTarget = client.target(url);

            System.out.println("URL: " + webTarget.getUri().getRawPath());

            // Build the HTTP GET request with authorization header
            Invocation.Builder invocationBuilder;
            String token = new JwtGenerator().generateJwt(HEADER, "");
            invocationBuilder = webTarget
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", token);

            // Send the GET request and get the response
            Response response = invocationBuilder.get();

            // Print and return the status code of the response
            System.out.println("STATUS CODE : " + response.getStatus());
            return response;

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
            return Response.noContent().build(); // intentionally empty response
        }
    }

}
