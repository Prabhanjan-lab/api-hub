/**
 *
 */
package testFramework;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import de.sulzer.utils.screenshots.ScreenshotsSelenium;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.sulzer.container.TestContainer;
import de.sulzer.model.Info;
import de.sulzer.model.ItemTest;

import testFramework.customExceptions.ObjectNotExistingException;
import testFramework.utilities.ReusableMethods;
import testFramework.utilities.properties.PropertyReader;
import testFramework.utilities.testservices.HandlerError;
import testFramework.utilities.testservices.ReportSetEndTimeAndResult;
import testFramework.utilities.testservices.ReportingTestCaseResult;
import de.sulzer.utils.FirefoxTestingBrowser;
import de.sulzer.utils.guistandardfunctions.ActionsOnupUserOverview;

/**
 * @author Sulzer GmbH
 */
public abstract class AbstractStandardBehaviour {

    //
    private TestContainer testContainer;
    private WebDriver driver;
    private ReportSetEndTimeAndResult reportSetEndTimeAndResult;
    private HandlerError handlerError;
    private Throwable throwable;

    private String logPath="";
    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }


    // test step counter
    // test step counter
    private byte step = 0;

    // support classes: classes which are used by a majority of all derived classes
    protected ActionsOnupUserOverview changeUserRolesRights = null;
    protected ReusableMethods reusableMethods;
    //
    private WebDriverWait wait;

    // reporting related
    private String reporting;

    // date format for time stamp generation
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    @org.junit.jupiter.api.BeforeEach
    protected void setUp() throws Throwable {

        StartClass.initialize();
        System.out.println(
                "ENVIRONMENT: " + StartClass.environment + ", " +
                        "TEST PLAN: " + StartClass.testPlanKey + ", " +
                        "EXECUTE: " + this.getClass().getName());


        //this.initDriver();
        //this.wait = new WebDriverWait(this.getWebDriver(), 30);

        this.reportSetEndTimeAndResult = new ReportSetEndTimeAndResult();
        this.handlerError = new HandlerError();

        this.initSupportClasses();
    }


    private void initSupportClasses() {

        this.changeUserRolesRights = new ActionsOnupUserOverview();

    }

    /**
     *
     */
    protected void initDriver() {
        this.driver = new FirefoxTestingBrowser().startONUPInFirefox();
    }
    
    protected void initDriverforSplunk(boolean splunk, String endPoint) throws IOException {
    	this.driver = new FirefoxTestingBrowser().startSplunkInFirefox(splunk, endPoint);
    }


    /**
     * This method is executed after each unit test.
     * In case any exception occurs, stack trace is given intentionally into
     * console (command line or CI tool like Jenkins).
     *
     * @throws java.lang.Exception
     */
    @org.junit.jupiter.api.AfterEach
    protected void tearDown() throws Throwable {

        String os = System.getProperty("os.name");
        os = os.toLowerCase();

        // in case of exeception, error or other throwable
        if (null != this.getThrowable()) {
            System.out.println("***(null != this.getThrowable())");
            this.handlerError.processFailure(this.logPath, this.getTestContainer(), this.getThrowable(), this.reportSetEndTimeAndResult, this.getWebDriver(), this);
        }

        if (os.contains("linux")) {
            this.reporting = System.getProperty("reporting");
//            this.reporting = System.getenv("reporting");
        }
        if (os.contains("windows")) {
            PropertyReader pr = new PropertyReader();
            reporting = pr.readProperty("local-config.properties", "reporting");
        }

        ReportingTestCaseResult rtj = new ReportingTestCaseResult();

        rtj.reportTestResults(this.testContainer, this.reporting, this.getClassName());

        try {
            this.tearDownHook();
//            rtj.writeJsonToTargetFolder(this.testContainer, this.getClassName());//TODO: wieso war das auskommentiert?
        } catch (Throwable t) {
            System.out.println("***catch (Throwable t)");
            this.printMessage("error occured in tearDown() of " + this.getClass().getName());
            this.printMessage("error message is: " + System.lineSeparator() + t.getMessage());
//            this.takeScreenshotTearDownFault(this.getWebDriver());
            this.handlerError.printRelevantStackTrace(t.getStackTrace(), this);
            //rtj.writeJsonToTargetFolder(this.testContainer, this.getClassName());//TODO: wieso war das auskommentiert?
        } finally {
//            this.getWebDriver().quit();
            //System.out.println("***Stacktrace:    " +this.handlerError.toString());

            if (os.contains("linux")) {
                //this.getWebDriver().quit();
                // assuming running locally with Windows
            }
        }

    }

    private void takeScreenshotTearDownFault(WebDriver webDriver) {

        String directory = System.getProperty("user.dir") + "/screenshots/";

        File file = new File(directory);

        if (!file.exists()) {
            file.mkdir();
        }

        //
        String stringDate = this.dateFormat.format(new Date());
        String[] classParts = this.getClass().getSimpleName().split("_");
        String fileName = "";

        this.printMessage("screenshot taker (class name): " + this.getClass().getSimpleName());

        if (classParts.length > 1) {
            fileName = classParts[0] + "-" + classParts[1] + "-" + stringDate;
        } else {
            fileName = this.getClass().getName() + "-" + stringDate;
        }

        this.handlerError.takeScreenshot(fileName, directory, this.getWebDriver());
//        this.handlerError.takeScreenshot(fileName, ".", this.getWebDriver()); // TODO remove in case devstack migration is done

    }

    /**
     * Method stub for any needed test by inheriting class. The real test
     * happens here.
     *
     * @throws Exception, Error, Throwable
     */
    protected abstract void tearDownHook() throws Throwable;

    @org.junit.jupiter.api.Test
    public void test() throws Throwable {


        try {

            if (this.getTestContainer() == null) {
                throw new ObjectNotExistingException(this.getClassName() + ": TestContainer is not available for use!");
            }

            this.testHook();

            // setting end time/date of test and result PASS/FAIL/TODO
            this.reportSetEndTimeAndResult.setEndTimeAndResult(this.getTestContainer());

        } catch (ObjectNotExistingException e) {

            System.out.println("***catch (ObjectNotExistingException e): " +e.getMessage() +"____" +e.getLocalizedMessage());
            // message to caller
            throw e;

        } catch (Throwable e) {

            System.out.println("***catch (Throwable e): " +e.getMessage() +"___" +e.getLocalizedMessage());
            this.handlerError.processFailure(this.logPath, this.getTestContainer(), e, this.reportSetEndTimeAndResult, this.getWebDriver(), this);
            // message to caller
            throw e;

        }

    }

    /**
     * Method stub for any needed test by inheriting class. The real test
     * happens here.
     *
     * @throws Exception
     */
    protected abstract void testHook() throws Throwable;

    /**
     * @return the driver
     */
    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * @return the wait
     */
    public WebDriverWait getWebDriverWait() {
        return this.wait;
    }

    public WebDriverWait getWebDriverWait(int second) {
        wait = new WebDriverWait(this.getWebDriver(), second);
        return wait;
    }

    /**
     * @return the testContainer
     */
    public TestContainer getTestContainer() {
        return testContainer;
    }

    /**
     * @param testContainer the testContainer to set
     */
    public void setTestContainer(TestContainer testContainer) {
        this.testContainer = testContainer;
    }

    public void logStepPassed(String logPath) {
        this.getTestContainer().addTestStepPassed(logPath);
        this.printTimestamp();
    }

    /*public void logStepFailed() {
        this.getTestContainer().addTestStepFailed();
        this.printTimestamp();
    }

    public void logStepToDo() {
        this.getTestContainer().addTestStepToDo();
        this.printTimestamp();
    }*/

    /**
     * Creates a summary containing datetimestamp with test name.
     *
     * @return
     */
    protected String createSummary() {

        String testClassName = this.getClass().getSimpleName().replaceAll("_", "-");

        String localDateTime = this.getLocalDateTime();

        return localDateTime + " " + testClassName;

    }

   /*protected String getLocalDateTime() {

        Instant instant = Instant.now();
        ZonedDateTime timestamp = instant.atZone(ZoneId.systemDefault());
        String time = timestamp.toString();

        // removing written indication of time zone
        int i = time.indexOf("[");
        time = time.substring(0, i);
        // removing 'Z' from timestamp (ohterwise date is refused by JIRA)
        //time = time.replaceAll("Z", "");

        return time.replaceAll("[.][0-9]*[+]", "+");

    }*/

    protected String getLocalDateTime() {
        // Erstellen Sie einen Instant mit der aktuellen Zeit
        Instant instant = Instant.now();

        // Konvertieren Sie den Instant in die Zeitzone des Systems
        ZonedDateTime timestamp = instant.atZone(ZoneId.systemDefault());

        // Verwenden Sie einen DateTimeFormatter, um das korrekte ISO-8601-Format zu erstellen
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss+02:00");
        String time = timestamp.format(formatter);

        return time;
    }

    /**
     * Creating test key from test class name.
     *
     * @return String with extracted class name as test key.
     */
    protected String getTestKey() {

        String[] testname = this.getClassName();

        // regular and wanted to be output
        if (testname.length >= 2) {
            return testname[0] + "-" + testname[1];

            // backup output
        } else {
            return testname[0];
        }

    }

    /**
     * @return String array with parts of this class name.
     */
    protected String[] getClassName() {
        return this.getClass().getSimpleName().split("_");
    }

    protected String getTestRunStatus(TestContainer testContainer) {
        return this.testContainer.checkTestResult();
    }

    protected void setEndTimeAndResult(TestContainer testContainer) {
        this.reportSetEndTimeAndResult.setEndTimeAndResult(testContainer);
    }

    protected void initTestContainer(int testSteps) {

        String os = System.getProperty("os.name").toLowerCase();
        PropertyReader pr = new PropertyReader();

        // init test container with test step count
        this.setTestContainer(new TestContainer(testSteps));

        //
        // creating info block
        Info info = new Info();

        // creating test (test case)
        ItemTest test = new ItemTest();

        /*
         * setting common values for test run
         */
        info.setSummary(this.createSummary());

        if (os.contains("linux")) {
            info.setUser(System.getProperty("jirauser"));
        }
        if (os.contains("windows")) {
            try {
                info.setUser(pr.readProperty("local-config.properties","jirauser"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // setting start time/date of test
        info.setStartDate(this.getLocalDateTime());

        if (os.contains("linux")) {
            info.setTestEnvironments(System.getProperty("environment"));
        }
        if (os.contains("windows")) {
            try {
                info.setTestEnvironments(pr.readProperty("local-config.properties","environment"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (os.contains("linux")) {
            info.setTestPlanKey(System.getProperty("testPlanKey"));
        }
        if (os.contains("windows")) {
            try {
                info.setTestPlanKey(pr.readProperty("local-config.properties","testPlanKey"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        this.getTestContainer().setInfo(info);

        /*
         * setting values for test case for test run
         */
        test.setTestKey(this.getTestKey());
        test.setStart(this.getLocalDateTime());
        test.setComment("automated test run");

        //
        this.getTestContainer().setTest(test);

    }

    /**
     * @return the throwable
     */
    protected Throwable getThrowable() {
        return throwable;
    }

    /**
     * @param throwable the throwable to set
     */
    protected void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void printTimestamp() {
        System.out.println(this.getMessagePreface() + "step " + (++this.step));
    }

    public void printMessage(String message) {

        System.out.println(this.getMessagePreface() + message);

        ItemTest itemTest = this.testContainer.getTest();
//        itemTest.setComment(itemTest.getComment() + "\\n" + message);
        itemTest.setComment(itemTest.getComment() + "\n" + message);
    }


    private String getMessagePreface() {
        return LocalDateTime.now() + " | " + this.getClass().getSimpleName() + " | ";
    }

    public void logDirectlyToJira() {

        ReportingTestCaseResult rtj = new ReportingTestCaseResult();

        rtj.reportTestResults(this.testContainer, ReportingTestCaseResult.REPORTING_JIRA, this.getClassName());

    }

    public void screenShot(WebDriver driver) {
        String name = this.getMessagePreface() + ".png";
        new ScreenshotsSelenium().takeScreenshot(name, ".", driver);
    }

    public void screenShot(int num, WebDriver driver) {
        String name = num + " | " + LocalDateTime.now() + ".png";
        new ScreenshotsSelenium().takeScreenshot(name, ".", driver);
    }

    public void printScreen(){
        this.handlerError.processFailure(this.logPath, this.getTestContainer(), this.getThrowable(), this.reportSetEndTimeAndResult, this.getWebDriver(), this);
    }

    public void checkTime(int num) {
        LocalDateTime time = LocalDateTime.now();
        System.out.println(num + " - " + time);
    }
}