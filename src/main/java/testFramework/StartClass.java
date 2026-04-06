package testFramework;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.launcher.TagFilter.excludeTags;
import static org.junit.platform.launcher.TagFilter.includeTags;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;


import de.sulzer.REST.UtilityProperties;
import testFramework.utilities.properties.PropertyReader;

/*
 * author Sulzer GmbH
 */
public class StartClass {

    public static String os = System.getProperty("os.name").toLowerCase();
    public static PropertyReader pr = new PropertyReader();

    public static String[] selectionClass; //List of selected classes for execution
    public static String[] selectionPackage; //List of selected packages for execution
    public static String[] includeTags; //List of Tags for including tests in a package
    public static String[] excludeTags; //List of Tags for excluding tests in a package

    public static String userName; //Username for Admin
    public static String userPassword; //Password for Admin
    public static String userNameLdapOperating; //Username with OPERATING for User
    public static String userPasswordLdapOperating; //Password with OPERATING  for User
    public static String userNameUser; //Username for User
    public static String userPasswordUser; //Password for User
    public static String adminPortletKeystorePath;
    public static String ADMIN_PORT_KS_PASSWORD;

    public static String environment;
    public static String fixVersion;
    public static String testPlanKey;
    public static String numberThreads;

    public static LauncherDiscoveryRequest request;
    public static LauncherDiscoveryRequestBuilder requestBuilder;


    public static void main(String[] args) throws Exception {

        initialize();

        System.out.println("using " + userName + ":***** on " + environment + " with " + numberThreads + " threads");

        System.out.println("Start Test Suite");

        if (null == selectionPackage || selectionPackage[0].length() < 3) { //class is used

            System.out.println("selecting class(es) " + Arrays.toString(selectionClass));

            try {

                requestBuilder = LauncherDiscoveryRequestBuilder.request();
                for (String aClass : selectionClass) {
                    requestBuilder.selectors(selectClass(Class.forName(aClass)));
                }

                request = configureBuilder(requestBuilder).build();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(StartClass.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

//                System.out.println("selecting package(s) " + Arrays.toString(selectionPackage));
            System.out.println("selecting package(s) " + System.getProperty("selectedPackages"));

            requestBuilder = LauncherDiscoveryRequestBuilder.request();
            for (String s : selectionPackage) {
                requestBuilder.selectors(selectPackage(s));
            }
            if (includeTags.length > 0 && !"x".equals(includeTags[0]) && !"".equals(includeTags[0])) {
                System.out.println("including following Tags: " + System.getProperty("includeTags"));
                requestBuilder.filters(includeTags(includeTags));
            }
            if (excludeTags.length > 0 && !"x".equals(excludeTags[0]) && !"".equals(excludeTags[0])) {
                System.out.println("excluding following Tags: " + System.getProperty("excludeTags"));
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

    public static void initialize() {

        if (os.contains("linux")) {
            try {
                userName = UtilityProperties.getProperties().getAdminPortletUser();
                userPassword = UtilityProperties.getProperties().getAdminPortletPassword();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            environment = System.getProperty("environment");
            fixVersion = System.getProperty("fixVersion");
            numberThreads = System.getProperty("threads");
            selectionPackage = System.getProperty("selectedPackages").split(",");
            selectionClass = System.getProperty("selectedClasses").split(",");
            includeTags = System.getProperty("includeTags").split(",");
            excludeTags = System.getProperty("excludeTags").split(",");
            testPlanKey = System.getProperty("testPlanKey");
            adminPortletKeystorePath =  System.getProperty("OUD_Keystore");
            ADMIN_PORT_KS_PASSWORD =System.getProperty("OUD_Password");
        }

        if (os.contains("windows")) {

            try {
                testPlanKey = pr.readProperty("local-config.properties", "testPlanKey");
                userName = pr.readProperty("local-config.properties", "userName");
                userPassword = pr.readProperty("local-config.properties", "userPassword");
                environment = pr.readProperty("local-config.properties", "environment");
                fixVersion = pr.readProperty("local-config.properties", "fixVersion");
                numberThreads = pr.readProperty("local-config.properties", "threads");
                selectionPackage = pr.readProperty("local-config.properties", "selectedPackages").split(",");
                selectionClass = pr.readProperty("local-config.properties", "selectedClasses").split(",");
                includeTags = pr.readProperty("local-config.properties", "includeTags").split(",");
                excludeTags = pr.readProperty("local-config.properties", "excludeTags").split(",");
                adminPortletKeystorePath =  System.getProperty("local-config.properties", "ADMIN_PORTLET_KEYSTORE_PATH");
                ADMIN_PORT_KS_PASSWORD = System.getProperty("local-config.properties", "ADMIN_PORT_KS_PASSWORD");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}