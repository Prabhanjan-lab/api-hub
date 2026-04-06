/**
 *
 */
package testFramework.utilities.testservices;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import de.sulzer.container.TestContainer;
import de.sulzer.model.ItemEvidence;
import de.sulzer.model.util.Base64Converter;
import de.sulzer.utils.screenshots.ScreenshotsShutterbug;
import testFramework.AbstractStandardBehaviour;
import testFramework.utilities.zipfiles.ZipFileOperations;

/**
 * @author Sulzer GmbH
 */
public class HandlerError {

    private static final String NO_ERROR_EXCEPTION_MESSAGE_WRITABLE = "No error/exception message writable.";
    private static final String ZIP_FILE_ENTRY_NAME = "exception-and-error.txt";
    private static final String APPLICATION_ZIP = "application/zip";
    private static final String IMAGE_PNG = "image/png";
    private static final String IMAGE_JPG = "image/jpg";

    private static final String SUFFIX_PNG = ".png";
    private static final String SUFFIX_ZIP = ".zip";
    private static final String SUFFIX_TXT = ".txt";

    // date format for time stamp generation
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private ZipFileOperations zipFileOperations;

    /**
     *
     */
    public HandlerError() {

        this.zipFileOperations = new ZipFileOperations();

    }

    public void processFailure(
            String logPath,
            TestContainer testContainer,
            Throwable throwable,
            ReportSetEndTimeAndResult reportSetEndTimeAndResult,
            WebDriver webDriver, AbstractStandardBehaviour testClass) {

        if (testContainer != null) {

            System.out.println("***processFailure Tcont not null");
            // printing relevant part of stack trace with causing line of code
            this.printRelevantStackTrace(throwable.getStackTrace(), testClass);

            // processing exception and setting/adding steps with failure and ToDo
            testContainer.processFailure(logPath, this.createEvidenceStackTrace(throwable), this.createEvidenceScreenshot(webDriver));
            System.out.println("***processFailure createEvidenceScreenshot");
            // setting end time/date of test and result PASS/FAIL/TODO
            reportSetEndTimeAndResult.setEndTimeAndResult(testContainer);

        }

    }

    public void printRelevantStackTrace(StackTraceElement[] elements, AbstractStandardBehaviour testClass) {
        String className = testClass.getClass().getSimpleName();

        int index = -1;
        int defaultSpan = 3;

        // searching for exception cause
        for (int i = 0; i < elements.length; i++) {
            if (null != elements[i].getFileName()) {
                if (elements[i].getFileName().contains(className)) {
                    index = i;
                    break;
                }
            }
        }

        // checking for root cause; causing method call
        if (index < 0) {

            System.out.println("no root cause for exception found (involved class not found)");

            // printing root cause/causing method call to console (centered around cause)
        } else {

            int span = 0;

            // index not starting below zero (avoiding index out of bounds)
            if (index - defaultSpan < 0) {
                span = 0;
            } else {
                span = index - defaultSpan;
            }

            int lastIndex = 0;

            // finding last index (avoiding index out of bounds)
            int tempLastIndex = (span + 1 + defaultSpan);
            if (tempLastIndex > elements.length) {
                lastIndex = elements.length;
            } else {
                lastIndex = tempLastIndex;
            }

            // printing
            System.out.println("ATTENTION Exception found on position (according stack trace)");
            //
            for (int i = span; i < lastIndex; i++) {
                System.out.println("\t" + elements[i].toString());
            }
        }

    }

    /**
     * creating an evidence
     *
     * @param webDriver
     * @return ItemEvidence
     */
    private ItemEvidence createEvidenceScreenshot(WebDriver webDriver) {
        System.out.println("***createEvidenceScreenshot");
        ItemEvidence itemEvidence = new ItemEvidence();

        try {

            //
            String path;

            String stringDate = this.dateFormat.format(new Date());
//	        zoomInZoomOut("0.5", webDriver);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //this.takeScreenshot(stringDate, System.getProperty("user.dir"), webDriver);

//	        zoomInZoomOut("1.0", webDriver);

            path = System.getProperty("user.dir") + "/" + stringDate + SUFFIX_PNG;
            File target = new File(path);

            itemEvidence.setFilename(stringDate + SUFFIX_PNG);
            itemEvidence.setContentType(IMAGE_PNG);
            itemEvidence.setData(Base64Converter.getImageAsBase64(target));

            deleteFile(target);
            System.out.println("***createEvidenceScreenshot ENDE");
            return itemEvidence;

        } catch (WebDriverException | IOException e) {
            System.out.println("***catch (WebDriverException | IOException e) {");
            System.out.println(e.getMessage());
            System.out.println(new File(
                    System.getProperty("user.dir") + "/selenium.gui.testing/data/evidence/H96566k.jpg").getAbsolutePath());

            itemEvidence.setFilename("nofileavailable.png");
            itemEvidence.setContentType(IMAGE_JPG);
            try {
                System.out.println("***TRY selenium.gui.testing/data/evidence/H96566k.jpg");

                itemEvidence.setData(Base64Converter.getImageAsBase64(new File(
                        System.getProperty("user.dir") + "/selenium.gui.testing/data/evidence/H96566k.jpg")));
            } catch (IOException e1) {
                System.out.println("***catch selenium.gui.testing/data/evidence/H96566k.jpg");
                System.out.println(e1.getMessage());
                System.out.println("***createEvidenceScreenshot ENDE3");
            }
            System.out.println("***createEvidenceScreenshot ENDE2");
            return itemEvidence;

        }

    }

    private ItemEvidence createEvidenceStackTrace(Throwable throwable) {

        //System.out.println("***createEvidenceStackTrace");
        String stringDate = this.dateFormat.format(new Date());

        //
        String path = System.getProperty("user.dir") + "/" + stringDate + SUFFIX_ZIP;
        File target = new File(path);

        ItemEvidence itemEvidence = new ItemEvidence();

        try {

            StringBuilder sb = new StringBuilder();

            // write exception etc. message as first line of error message
            sb.append(throwable.getMessage() + "\r\n"); // escaping (linux) line change for JSON/Jira

            // see:
            // https://stackoverflow.com/a/22271986
            // or
            // https://docs.oracle.com/javase/tutorial/essential/exceptions/chained.html
            //
            StackTraceElement elements[] = throwable.getStackTrace();

            for (int i = 0, n = elements.length; i < n; i++) {
                sb.append(elements[i].toString());
                sb.append("\r\n");
            }

            this.zipFileOperations.writeZipFile(sb.toString(), target, ZIP_FILE_ENTRY_NAME);

            this.prepareItemEvidence(stringDate, target, itemEvidence);

//			this.deleteFile(target);
//
//			return itemEvidence;

        } catch (WebDriverException | IOException e) {

            try {

                this.zipFileOperations.writeZipFile(NO_ERROR_EXCEPTION_MESSAGE_WRITABLE + e.getMessage(), target, ZIP_FILE_ENTRY_NAME);

                this.prepareItemEvidence(stringDate, target, itemEvidence);

            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }

        this.deleteFile(target);
        System.out.println("***createEvidenceStackTrace ENDE");
        return itemEvidence;

    }

    /**
     * @param target
     */
    private void deleteFile(File target) {
        // deleting temp file
        if (target.exists()) {
            target.delete();
        }
    }

    /**
     * @param stringDate
     * @param target
     * @param itemEvidence
     * @throws IOException
     */
    private void prepareItemEvidence(String stringDate, File target, ItemEvidence itemEvidence) throws IOException {

        itemEvidence.setFilename(stringDate + SUFFIX_ZIP);
        itemEvidence.setContentType(APPLICATION_ZIP);
        itemEvidence.setData(Base64Converter.getImageAsBase64(target));

    }

    //	public void takeScreenshot(String filename, String path, WebDriver webDriver) {
    public static void takeScreenshot(String filename, String path, WebDriver webDriver) {
        new ScreenshotsShutterbug().takeScreenshot(filename, path, webDriver);
    }

    public void zoomInZoomOut(String value, WebDriver webDriver) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("document.body.style.zoom='" + value + "'");
    }

}
