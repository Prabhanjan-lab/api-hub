package testCases.testStep_MQTT;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import testCases.oAuthTokenGeneration.RegisterAndGetOAUTH;
import testCases.readData.ReadTestParameters;
import testCases.utils.Environments;
import testCases.utils.proxySettings;

public class GetServiceList_VW_MQTT extends RegisterAndGetOAUTH {


    public static Response getRequestForServiceList(String testname, String vin) throws IOException {

        Environments.setEnvironmentURL();
        RestAssured.baseURI = BaseURL;
        proxySettings.proxy();

        RequestSpecification request = RestAssured.given().log().all();
        Response response = request
                .header("Authorization", "Bearer " + OAuthToken)
                .header("Accept", "application/xml", "application/vnd.vwg.mbb.serviceList_v5_0_1+xml")
                .when().get("/mbb/services/v1/downloadSL/" + checksum)
                .then().log().all().assertThat().statusCode(200).extract().response();
        return response;
    }

    public static void assertGetServiceList_VW_MQTT(String testname, String vin)
            throws IOException, TransformerException {

        Response response = getRequestForServiceList(testname, vin);
        String board = ReadTestParameters.getAttributeValue(testname, "board");
        String serviceListId = ReadTestParameters.getAttributeValue(testname, "board");
        String serviceId = "uota_v1";
        String xmlResponse = response.asString();

        // Strip namespaces
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");

        StringReader reader = new StringReader(xmlResponse);
        StringWriter writer = new StringWriter();
        transformer.transform(new StreamSource(reader), new StreamResult(writer));
        String xmlWithoutNs = writer.toString().replaceAll("xmlns[:a-zA-Z0-9]*=\"[^\"]*\"", "");

        try {
            // Parse XML without namespace
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new java.io.ByteArrayInputStream(xmlWithoutNs.getBytes()));

            XPath xpath = XPathFactory.newInstance().newXPath();

            // Build XPath expressions
            String baseTopicXPath = String.format(
                    "/serviceListCollection/serviceList[@id='%s']/service[@serviceId='%s']/mqttOperation[@id='updateJob']/baseTopic",
                    serviceListId, serviceId);
            String brokerIdXPath = String.format(
                    "/serviceListCollection/serviceList[@id='%s']/service[@serviceId='%s']/mqttOperation[@id='updateJob']/brokerId",
                    serviceListId, serviceId);
            String topicXPath = String.format(
                    "/serviceListCollection/serviceList[@id='%s']/service[@serviceId='%s']/mqttOperation[@id='updateJob']/topic",
                    serviceListId, serviceId);

            Node baseTopicNode = (Node) xpath.evaluate(baseTopicXPath, doc, XPathConstants.NODE);
            Node brokerIdNode = (Node) xpath.evaluate(brokerIdXPath, doc, XPathConstants.NODE);
            Node topicNode = (Node) xpath.evaluate(topicXPath, doc, XPathConstants.NODE);

            assertEquals("ECU/"+vin+"/"+board, baseTopicNode.getTextContent(), "baseTopic mismatch");
            assertEquals("CommandBroker", brokerIdNode.getTextContent(), "brokerId mismatch");
            assertEquals("uota_v1/updateJob", topicNode.getTextContent(), "topic mismatch");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to parse XML and assert mqttOperation values");
        }
    }
}
