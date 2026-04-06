package testCases.reusable_TestSteps;

import io.restassured.response.ValidatableResponse;
import testCases.utils.JWT;

public class RewindCampaignWithoutSkipMassnotification extends JWT{
	
	public static void assertRewindCampaignWithoutSkipMassnotification(String testname,String vin,String campaign,String criterion) throws Exception {
		JWT.GenerateJWTToken();
		SkipMassnotificationFalse.assertSkipMassnotificationFalse(testname,vin);
		ValidatableResponse rewindResponse= RewindCampaignWithSkipMassnotification.rewind(testname,vin,campaign,criterion);
		rewindResponse.assertThat().statusCode(200);
	}
}
