package de.sulzer.service.rest.services.authentication.jwt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import javax.json.Json;
import javax.json.JsonObject;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.Requirement;
import com.nimbusds.jose.crypto.RSASSASigner;
import testFramework.utilities.ReusableMethods;
import testFramework.utilities.properties.PropertyReader;

import javax.json.JsonArray;

/**
 * @author GCH9F5D
 *
 */
public class JwtGenerator {

	public static final JWSAlgorithm RS256 = new JWSAlgorithm("RS256", Requirement.RECOMMENDED);

	private JwtDataPackage jwtDataPackage;

	public String generateJwt(/*String cty, String kid,*/ String header, String payload) throws Exception {

		String os = System.getProperty("os.name");
		os = os.toLowerCase();

		try {

			PropertyReader pr = new PropertyReader();
			String kypsw = "";
			String keystorePath = "";

			// assuming running on RHEL VM
			if (os.contains("linux")) {
				kypsw = System.getProperty("OUD_Password");
				keystorePath = System.getProperty("OUD_Keystore");
			} else if (os.contains("windows")) {
				kypsw = pr.readProperty("local-config.properties", "OUD_Password");
				keystorePath = pr.readProperty("local-config.properties", "OUD_Keystore");
				
			}

			this.jwtDataPackage = new JwtDataPackage("180", header /*JSON wie im beispiel*/, payload);

			final FileInputStream inputStream = new FileInputStream(keystorePath);
			char[] password = kypsw.toCharArray();

			final KeyStore keyStore = KeyStore.getInstance("JKS");

			keyStore.load(inputStream, password);

			String alias = keyStore.aliases().nextElement();
			
			KeyStore.ProtectionParameter entryPassword = new KeyStore.PasswordProtection(password);

			KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, entryPassword);


			// Creating JWS header
			JWSHeader jwsHeader = new JWSHeader.Builder(RS256).
					//					contentType(cty).
					//					keyID(kid).
					contentType(jwtDataPackage.getHeader().readObject().getString("cty")).
					keyID(jwtDataPackage.getHeader().readObject().getString("kid")).
					build();


			// Calculating time
			long currentTimeInSec = System.currentTimeMillis() / 1000;

        JsonArray ListLdapRoles = Json.createArrayBuilder().add("O").add("UA").add("U").build();

        JsonObject BrandP = Json.createObjectBuilder().add("domains", ListLdapRoles).add("brand", "P").build();
        JsonObject BrandA = Json.createObjectBuilder().add("domains", ListLdapRoles).add("brand", "A").build();
        JsonObject BrandV = Json.createObjectBuilder().add("domains", ListLdapRoles).add("brand", "V").build();

        JsonArray List2 = Json.createArrayBuilder()
                  .add(BrandP)

                .add(BrandA)
                .add(BrandV)
                .build();
			JsonObject payloadObject = Json.createObjectBuilder().
					add("iat", currentTimeInSec).
					add("exp", currentTimeInSec + (Long.parseLong(jwtDataPackage.getExpirationPeriod()) * 60)).
                                        add("tnt", List2).
					//
					add("iss", "CN=Sulzer, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=DE").
					add("sub", "admin").
					add("aud", "MBBB_ONUP").
					add("cid", "Testing_Sulzer").
					add("name", "admin").
					//
					build();

			jwtDataPackage.setPayload(payloadObject.toString());

			String s = jwtDataPackage.getPayload().readObject().asJsonObject().toString();

			Payload pl = new Payload(s);

			JWSObject jwsObject = new JWSObject(jwsHeader, pl);
			jwsObject.sign(new RSASSASigner(privateKeyEntry.getPrivateKey()));

			String jws = jwsObject.serialize();

			return jws;

		} catch (JOSEException | KeyStoreException | NoSuchAlgorithmException | UnrecoverableEntryException | IOException | CertificateException e) {
			throw new Exception(e);
		}

	}

}