package testFramework;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.launcher.TagFilter.excludeTags;
import static org.junit.platform.launcher.TagFilter.includeTags;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import com.google.gson.JsonObject;

import testFramework.utilities.properties.PropertyReader;
/*
 * @auhtor Sulzer GmbH
 */
public class StartClassLocal {

    private static final String LOCAL_CONFIG_PROPERTIES = "local-config.properties";
    private static final String ENVIRONMENT = "environment";
    private static final String SELECTED_PACKAGES = "selectedPackages";
    private static final String SELECTED_CLASSES = "selectedClasses";
    private static final String INCLUDE_TAGS = "includeTags";
    private static final String EXCLUDE_TAGS = "excludeTags";
    private static final String THREADS = "threads";
    private static final String AT_PW = "at_pw";
    private static final String AT_USER = "at_user";

    public static String[] selectionClass; //List of selected classes for execution
    public static String[] selectionPackage; //List of selected packages for execution
    public static String[] includeTags; //List of Tags for including tests in a package
    public static String[] excludeTags; //List of Tags for excluding tests in a package

    public static String userName; //Username for Admin
    public static String userPassword; //Password for Admin

    public static String environment;
    public static String tcNameString;
    public static String numberThreads;

    public static ArrayList<String> tcName = new ArrayList<String>();
    public static ArrayList<String> methodName = new ArrayList<String>();
    public static ArrayList<JsonObject> jsonArray = new ArrayList<JsonObject>();

    public static LauncherDiscoveryRequest request;
    public static LauncherDiscoveryRequestBuilder requestBuilder;

    public static void main(String[] args) throws Exception{

        // different setting independent of below if-else
        StartClass.userNameLdapOperating = "hdanis";
        StartClass.userPasswordLdapOperating = "hdanisLU57";
        StartClass.userNameUser = "skeegan";
        StartClass.userPasswordUser = "skeeganYQ68";


        String os = System.getProperty("os.name");

        os = os.toLowerCase();

        // assuming running on RHEL VM
        if (os.contains("linux")) {

            userName = System.getenv("at_user");
            userPassword = System.getenv("at_pw");

            environment = System.getProperty("environment");
            numberThreads = System.getProperty("threads");
            selectionPackage = System.getProperty("selectedPackages").split(",");
            selectionClass = System.getProperty("selectedClasses").split(",");
            includeTags = System.getProperty("includeTags").split(",");
            excludeTags = System.getProperty("excludeTags").split(",");

        // pretty sure a mistake in assuming it is Windows, could be local Linux too
        } else {

            PropertyReader pr = new PropertyReader();

            userName = "seichler";
            userPassword = "seichlerFV97";
            StartClass.userName = userName;
            StartClass.userPassword = userPassword;

            environment = pr.readProperty(LOCAL_CONFIG_PROPERTIES, ENVIRONMENT);

            StartClass.environment = environment;

            numberThreads = pr.readProperty(LOCAL_CONFIG_PROPERTIES, THREADS);
            selectionPackage = pr.readProperty(LOCAL_CONFIG_PROPERTIES, SELECTED_PACKAGES).split(",");
            selectionClass = pr.readProperty(LOCAL_CONFIG_PROPERTIES, SELECTED_CLASSES).split(",");
            includeTags = pr.readProperty(LOCAL_CONFIG_PROPERTIES, INCLUDE_TAGS).split(",");
            excludeTags = pr.readProperty(LOCAL_CONFIG_PROPERTIES, EXCLUDE_TAGS).split(",");

        }

        System.out.println("using "+userName+":***** on " + environment+" with "+numberThreads+" threads");

        System.out.println("Start Test Suite");

        if(null == selectionPackage || selectionPackage[0].length()<3) { //class is used

            System.out.println("selecting class(es) " + Arrays.toString(selectionClass));

            try {

                requestBuilder = LauncherDiscoveryRequestBuilder.request();
                for(int i=0;i<selectionClass.length;i++) {
                    requestBuilder.selectors(selectClass(Class.forName(selectionClass[i])));
                }

                request = configureBuilder(requestBuilder).build();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StartClassLocal.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            System.out.println("selecting package(s) " + Arrays.toString(selectionPackage));

            requestBuilder = LauncherDiscoveryRequestBuilder.request();
            for(int i=0;i<selectionPackage.length;i++) {
                requestBuilder.selectors(selectPackage(selectionPackage[i]));
            }
            if(includeTags.length>0 && !"x".equals(includeTags[0]) && !"".equals(includeTags[0])) {
                System.out.println("including following Tags: "+System.getProperty(INCLUDE_TAGS));
                    requestBuilder.filters(includeTags(includeTags));
            }
            if(excludeTags.length>0 && !"x".equals(excludeTags[0]) && !"".equals(excludeTags[0])) {
                System.out.println("excluding following Tags: "+System.getProperty(EXCLUDE_TAGS));
                    requestBuilder.filters(excludeTags(excludeTags));
            }

            request = configureBuilder(requestBuilder).build();
        }
        Launcher launcher = LauncherFactory.create();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        TestExecutionSummary summary = listener.getSummary();
        StringWriter stringWriter = new StringWriter();
        summary.printTo(new PrintWriter(stringWriter));
        summary.printFailuresTo(new PrintWriter(stringWriter));

        System.out.println(stringWriter);

    }

    private static LauncherDiscoveryRequestBuilder configureBuilder(LauncherDiscoveryRequestBuilder builder) {

        builder.configurationParameter("junit.jupiter.execution.parallel.enabled", "true");
        builder.configurationParameter("junit.jupiter.execution.parallel.config.strategy", "fixed");
        builder.configurationParameter("junit.jupiter.execution.parallel.config.fixed.parallelism", numberThreads);

        return requestBuilder;
    }
}
