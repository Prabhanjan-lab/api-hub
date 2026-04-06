package testCases.reusable_TestSteps;

import java.io.IOException;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.JWT;
import testCases.utils.proxySettings;

 public class CreateORUCampaign extends JWT{
 public static  ValidatableResponse CreateORU(String testname,String campaign,String criterion) throws IOException {
	 JWT.GenerateJWTToken();
	 String brand=ReadTestParameters.getAttributeValue(testname, "brand");
	 String brandContext=ReadTestParameters.getAttributeValue(testname, "brandContext");
	 String amId= ReadTestParameters.getAttributeValue(testname, "amId");
	 String amCode= ReadTestParameters.getAttributeValue(testname, "amCode");
	 String TNR= ReadTestParameters.getAttributeValue(testname, "TNR");
	 String domain = ReadTestParameters.getAttributeValue(testname, "domain");
		Environments.setEnvironmentURL();
		RestAssured.baseURI= Environments.OUDMockURL;
		proxySettings.proxy();
		RequestSpecification request = RestAssured.given().log().all();
		request.header("Authorization","Bearer " + JWT.JWTToken)
		.config(RestAssured.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
		.header("Content-Type","application/json")
		.body("{ \"campaignNumber\": \""+campaign+"\",\"campaignTitle\":\""+campaign+"\", \"username\": \"Ranjith Kumar Bethini\", \"createTimestamp\": \"\", \"domainData\": \""+domain+"\", \"downloadPlatform\": \"SOKO\", \"oruDescription\": { \"defaultLanguage\": \"de_DE\", \"defaultText\": \"jha\", \"translationType\": \"MANUAL\", \"brandContext\": \""+brandContext+"\", \"translationPackage\": \"World\", \"translationPackageRefId\": \"LC_01\", \"oruTranslations\": [ { \"language\": \"en_KR\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"sl_SI\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"et_EE\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"ar_SA\", \"translation\": \"ok\", \"erroneous\": false, \"translationFinished\": true }, { \"language\": \"sk_SK\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"es_ES\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"nb_NO\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"ru_RU\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"bs_BA\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"hu_HU\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"es_MX\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"pl_PL\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"it_IT\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"da_DK\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"pt_BR\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"lt_LT\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"zh_TW\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"en_US\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"bg_BG\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"uk_UA\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"lv_LV\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"fr_FR\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"ko_KR\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"en_GB\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"ms_MY\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"en_JP\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"es_US\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"ro_RO\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"nl_NL\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"de_DE\", \"translation\": \"de_DE\", \"erroneous\": false, \"translationFinished\": true }, { \"language\": \"fr_CA\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"sr_RS\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"he_IL\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"hr_HR\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"fi_FI\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"en_CN\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"pt_PT\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"ja_JP\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"zh_HK\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"en_TW\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"cs_CZ\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"el_GR\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"sv_SE\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"tr_TR\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"en_SA\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"no_NO\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false }, { \"language\": \"zh_CN\", \"translation\": \"\", \"erroneous\": false, \"translationFinished\": false } ] }, \"archived\": true, \"encrypted\": true, \"criteria\": [ { \"title\": \""+criterion+"\", \"criterionId\": \"01\", \"flashmediums\": [ { \"amCode\": \""+amCode+"\", \"amId\": \""+amId+"\", \"flashIdentifier\": \""+TNR+"\" } ], \"recallPoolNumber\": 0 } ], \"error\": null } }");
		ValidatableResponse response = request.when().post("onup/api/bs/oru/v1/"+brand+"/oru").then().log().all();
		return response;
 }
 
 public static void assertCreateORU(String testname,String campaign,String criterion) throws IOException, InterruptedException {
	 ValidatableResponse response= CreateORUCampaign.CreateORU(testname,campaign,criterion);
	 response.assertThat().statusCode(201);
	 System.out.println("ORU Campaign created sucessfully. Waiting for campaign to flash file upload");
	 Thread.sleep(240000);
 }

}
