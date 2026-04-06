package testCases.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Environments {
	public static String BaseURL;
	public static String OUDMockURL;
	public static String RegisterVehicleURL;
	public static String OauthTokenURL;
	public static String oauthTokenCarportURL;
	public static String BaseURLCarport;
	
	public static String getEnvironment() throws IOException {
		String os = System.getProperty("os.name").toLowerCase();
		Properties properties = new Properties();
		String environment = "";
		if (os.contains("linux")) {

			environment = System.getProperty("environment");
			//System.out.println("***Lin environment: " +environment);
		}
		if (os.contains("windows")) {

			FileInputStream fileInputStream = new FileInputStream("local-config.properties");
			properties.load(fileInputStream);
			fileInputStream.close();
			environment = properties.getProperty("environment");
		}
		return environment;
	}
	
	
	 public static void setEnvironmentURL() throws IOException {
		 String env= Environments.getEnvironment();
		 if(env.equalsIgnoreCase("DEMO_ECE")) {
			BaseURL="https://mod3digestmbbrun-3a.dmo.eu.dp.vwg-connect.com";
			BaseURLCarport="https://mod3csambbrundev-3a.dmo.eu.dp.vwg-connect.com";
			OUDMockURL="https://tui2-mbbonup-mgmt04.audi-connect.web.audi.vwg";
			 
		 }
		 else if(env.equalsIgnoreCase("APPROVAL_ECE")) {
			 BaseURL="https://mod3digestmbbrun-3a.app.eu.dp.vwg-connect.com";
			 BaseURLCarport="https://mod3csambbrundev-3a.app.eu.dp.vwg-connect.com";
			 OUDMockURL="https://pre2-mbbonup04.audi-connect.web.audi.vwg";
		 }
		 else {
			 System.out.println("Set correct environment in config properties");
		 }
	 }
	 
	public static void setRegistrationAndOauthURL() throws IOException {
		String env= Environments.getEnvironment();
		if(env.equalsIgnoreCase("DEMO_ECE")) {
			RegisterVehicleURL="https://mod3digestmbbreg-3a.dmo.eu.dp.vwg-connect.com";
			OauthTokenURL="https://digestazsrun-3a.dmo.eu.dp.vwg-connect.com";
			oauthTokenCarportURL ="https://csaazsrundev-3a.dmo.eu.dp.vwg-connect.com/mbbcoauth/vkms/oauth2/v1/requestToken/token";
		}
		else if(env.equalsIgnoreCase("APPROVAL_ECE")) {
			RegisterVehicleURL="https://mod3digestmbbreg-3a.app.eu.dp.vwg-connect.com";
			OauthTokenURL="https://digestazsrun-3a.app.eu.dp.vwg-connect.com";
			oauthTokenCarportURL ="https://csaazsrundev-3a.app.eu.dp.vwg-connect.com/mbbcoauth/vkms/oauth2/v1/requestToken/token";
		 }
		 else {
			 System.out.println("Set correct environment in config properties");
		 }
	}
}
