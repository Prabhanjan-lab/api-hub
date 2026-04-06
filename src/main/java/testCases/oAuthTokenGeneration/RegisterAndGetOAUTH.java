package testCases.oAuthTokenGeneration;

import java.io.IOException;

import org.apache.http.auth.Credentials;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.TestVariables;
import testCases.utils.proxySettings;

public class RegisterAndGetOAUTH extends TestVariables {
	public static String OAuthToken;

	public static void registerAndGetOAUTH(String testname,String vin) throws IOException {
		Environments.setRegistrationAndOauthURL();
		String board = ReadTestParameters.getAttributeValue(testname, "board");
		String scope = ReadTestParameters.getAttributeValue(testname, "scope");
		String brand = ReadTestParameters.getAttributeValue(testname, "brand");
		proxySettings.proxy();
		RestAssured.useRelaxedHTTPSValidation();
		Api_RA_Reg apiRaReg = new Api_RA_Reg();
		Api_RA_Oauth apiRaOauth = new Api_RA_Oauth();

		String accessToken = "";
		try {
			// Register Vehicle
			Response respReg = apiRaReg.RunT1DIGESTRegisterRA(Environments.RegisterVehicleURL, vin, board,
					"SNR-" + vin + "-2");
			Credentials T1Credentials = apiRaReg.GetT1DIGESTCredsRA(respReg);

			// Get oauth token

			Response respOauth = apiRaOauth.RunOauthRA(Environments.OauthTokenURL, T1Credentials, scope);
			accessToken = apiRaOauth.GetOauthTokenRA(respOauth);
		} catch (AssertionError | Exception e) {

			System.out.println("Error in TestStep GET Vehicle OAuth Token AND Upload Installed Base!"
					+ System.lineSeparator() + e);

		}

		// Hier wird accessToken gesetzt
		OAuthToken = accessToken;

	}

}
