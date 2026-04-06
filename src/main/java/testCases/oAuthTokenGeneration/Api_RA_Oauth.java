package testCases.oAuthTokenGeneration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.apache.http.auth.Credentials;
//import reporting.BuildReportPayload;
//import reporting.RestAssuredRequestFilter;

import static io.restassured.RestAssured.given;

public class Api_RA_Oauth {

    public Api_RA_Oauth(){

    }

    public Response RunOauthRA(String oauthURL, Credentials vehicleCredentials, String scope/*, BuildReportPayload xResult*/)  {

        System.out.println("Methode gestartet");

        String AcceptHeaderValue = "application/json";
       
        RestAssured.useRelaxedHTTPSValidation();
        
        RestAssured.baseURI = oauthURL;
        /*RestAssuredRequestFilter filter = new RestAssuredRequestFilter();
        filter.setReport(xResult);*/
        RequestSpecification request = RestAssured.given()./*.filter(filter)*/log().all().auth().digest(vehicleCredentials.getUserPrincipal().getName(),vehicleCredentials.getPassword()).
                headers("Accept", AcceptHeaderValue, "Accept-Language", "en-US").
                when().log().all()
                .queryParam("brand", "Audi")
        		.queryParam("grant_type","client_credentials")
        		.queryParam("scope",scope).urlEncodingEnabled(false);
        Response resp=request.
                get("/mbbcoauth/t1/oauth2/v1/token").
                then().log().all().assertThat().statusCode(200).
                contentType(ContentType.JSON).
                extract().response();

        return resp;
    }

    public String GetOauthTokenRA(Response resp)  {


        String token = new JsonPath(resp.asString()).getString("access_token");

        System.out.println(token);

        return token;
    }

}