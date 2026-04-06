package testCases.oAuthTokenGeneration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
//import reporting.BuildReportPayload;
//import reporting.RestAssuredRequestFilter;

import static io.restassured.RestAssured.given;

public class Api_RA_Reg {

    public Api_RA_Reg(){
    }

    public Response RunT1DIGESTRegisterRA(String regURL, String vin, String da, String snr/*, BuildReportPayload xResult*/){
        System.out.println("Methode gestartet");

        String AcceptHeaderValue = "application/vnd.vwg.mbb.registrationAndServices_v2_1_1+xml";
        String ContentTypeHeaderValue = "application/vnd.vwg.mbb.vehicle_v1_1_1+xml";


        RestAssured.useRelaxedHTTPSValidation();

        RestAssured.baseURI = regURL;
        /*RestAssuredRequestFilter filter = new RestAssuredRequestFilter();
        filter.setReport(xResult);*/
        Response resp = given()/*.filter(filter)*/.log().all().auth().digest("T0CGW1", "EdpsVw0BPxRYJDv").
                headers("Content-Type", ContentTypeHeaderValue+"; charset=UTF-8", "Accept", AcceptHeaderValue).
                body("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "\n" +
                        "<vehicle version=\"1.0\" >\n" +
                        " <vin>" + vin + "</vin>\n" +
                        " <bg>" + da +"</bg>\n" +
                        " <snr>" + snr + "</snr>\n" +
                        "</vehicle>").
                when().log().all().
                put("/mbb/vehicles/v1/").
                then().log().all().assertThat().statusCode(200).
                contentType(ContentType.XML).
                extract().response();
        return resp;
    }

    public Credentials GetT1DIGESTCredsRA(Response resp)  {

        Credentials vehicleCredentials = new UsernamePasswordCredentials(resp.path("registration.@username"), resp.path("registration.@challenge"));


        System.out.println(vehicleCredentials.getUserPrincipal().getName() + " " + " " + vehicleCredentials.getPassword());


        return vehicleCredentials;
    }

}