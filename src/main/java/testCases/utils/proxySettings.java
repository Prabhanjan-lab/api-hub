package testCases.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.restassured.RestAssured;

public class proxySettings {
	
	public static void proxy() throws IOException {
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.proxy("proxy.in.audi.vwg",8080);
	}
	public static String getProxyHost() {
		String proxyHost="10.252.76.110";
		return proxyHost;	
	}
	public static String getProxyPort() {
		String proxyPort="8080";
		return proxyPort;	
	}
}
