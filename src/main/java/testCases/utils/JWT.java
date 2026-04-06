package testCases.utils;

import de.sulzer.service.rest.services.authentication.jwt.JwtGenerator;

public class JWT extends TestVariables{
	public static String JWTToken;
	public static void GenerateJWTToken() {
		
		String header = "{\"cty\":\"adminUiToken_v1_0_6\",\"kid\":\"CN=Sulzer, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=DE\"}";
		String payload = "{\"this\":\"json\", \"data\", \"more data\"}";

		String result = "";

		JwtGenerator jg = new JwtGenerator();

		try {
			result = jg.generateJwt(header, payload);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != result && result.length() > 0) {
				//System.out.println(result);
			} else {
				System.out.println("no jwt content available");
			}
		}
		JWTToken= result;
	}
	
}
