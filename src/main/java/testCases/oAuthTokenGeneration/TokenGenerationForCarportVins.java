package testCases.oAuthTokenGeneration;

import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.Properties;

import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.TestVariables;

public class TokenGenerationForCarportVins extends TestVariables{
	public static String accessToken;
    public static void GenerateOauthTokenForCarportVins(String testname,String keystorePath,char[] keystorePassword) {
        try {
        	String brandVW=ReadTestParameters.getAttributeValue(testname, "brandVW");
        	String scope=ReadTestParameters.getAttributeValue(testname, "scope");
        	String da=ReadTestParameters.getAttributeValue(testname, "da");
            // Load Keystore 
        	Environments.setRegistrationAndOauthURL();
            KeyStore keystore = KeystoreAndSSLConnection.loadKeystore(keystorePath, keystorePassword);
            // Use for Mutual TLS
            String brandHeader = "brand";  // Replace with actual parameter name          
            String regionHeader = "homeRegion";  // Replace with actual parameter name
            String homeRegionValue = "ICTO-25343"; // Replace with actual value (space should be URL-encoded)
            String grant_typeHeader = "grant_type";  // Replace with actual parameter name
            String grant_type_value = "client_credentials"; // Replace with actual value
            String scopeHeader = "scope";  // Replace with actual parameter name
            String daHeader = "da";  // Replace with actual parameter name
            

            // Encode query parameters
           String encodedParams = "?" + brandHeader + "=" + URLEncoder.encode(brandVW, "UTF-8") +
                    "&" + regionHeader + "=" + URLEncoder.encode(homeRegionValue, "UTF-8") +
                    "&" + grant_typeHeader + "=" + URLEncoder.encode(grant_type_value, "UTF-8") +
                    "&" + scopeHeader + "=" + scope + 
                    "&" + daHeader + "=" + URLEncoder.encode(da, "UTF-8");
            String fullURL = oauthTokenCarportURL + encodedParams;
            String os = System.getProperty("os.name").toLowerCase();
    		 
    		if (os.contains("linux")) {
    			accessToken=KeystoreAndSSLConnection.connectWithRelaxedMutualTLS(fullURL, keystorePath, keystorePassword);
    		}
    		else if(os.contains("windows")) {
    			accessToken=KeystoreAndSSLConnection.connectWithMutualTLS(fullURL, keystore, keystorePassword);
    		}
    		else {
    			System.out.println("System Os is undefined");
    		}
       System.out.println(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 