package testCases.utils;

import io.restassured.RestAssured;
import io.restassured.config.LogConfig;


import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogReports extends Environments {

	static Date now = new Date();
	static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYYMMddHHmmss");
	static String time = dateFormat.format(now);

	static String separator = System.getProperty("file.separator");

	static String REPORTING_FOLDER = "reporting" + separator;

	public static void logs(String testname) throws IOException {

		File folderRep = new File(LogReports.REPORTING_FOLDER);
		folderRep.mkdir();

		File folder = new File(LogReports.REPORTING_FOLDER + time+"_"+ Environments.getEnvironment() +"_"+testname);
		folder.mkdir();

	}
	/*public static void logGeneration(String domain,String testname,String StepName) throws FileNotFoundException {

		PrintStream log = new PrintStream(new FileOutputStream(
				LogReports.REPORTING_FOLDER + time+"_"+ Enviroment+ "_" +testname+ "\\" + StepName +".txt"));
		RestAssured.config = RestAssured.config().logConfig(new LogConfig().defaultStream(log));
	}*/

	public static String logGeneration(String testname,String StepName) throws IOException {

		PrintStream log = new PrintStream(new FileOutputStream(LogReports.REPORTING_FOLDER + time+"_"+ Environments.getEnvironment() + "_" +testname+ separator + StepName +".txt"));
		RestAssured.config = RestAssured.config().logConfig(new LogConfig().defaultStream(log));

		return LogReports.REPORTING_FOLDER + time+"_"+ Environments.getEnvironment() + "_" +testname+ separator + StepName +".txt";
	}
}
