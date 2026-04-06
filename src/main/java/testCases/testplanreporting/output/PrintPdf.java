/**
 *
 */
package testCases.testplanreporting.output;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.Property;
import com.itextpdf.layout.properties.UnitValue;

import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;
import testCases.testplanreporting.BaseReporting;
import testCases.testplanreporting.PrintTestExecutionResult;
import testCases.testplanreporting.TestExecutionResult;

/**
 * @author Sulzer GmbH
 *
 */
public class PrintPdf implements PrintTestExecutionResult {

    private static final String PROP_TEST_PLAN_KEY = "testPlanKey";
    private static final String PROP_FIX_VERSION = "fixVersion";
    private static final String PROP_ENVIRONMENT = "environment";

    // logging
    private Logger logReporting = Logger.getLogger("reporting");

    private HandlerRestVariable hrv;

    private XrayProperties properties;

    private ReadZipForErrorMessage readZipForErrorMessage;

    private String devstackApiToken;

    private static final String TEST_PLAN_ID = "Testplan";
    private static final String TEST_CASE_ID = "Test case";
    private static final String TEST_EXECUTION_ID = "Test Execution";
    private static final String TEST_ERROR_MESSAGE = "Error message (partial)";
    private static final String TEST_CASE_BUGS = "Bug(s)";
    private static final String TEST_EXECUTION_TEST_STEPS_LINK = "Test Execution Test Steps Link";

    private static final String JIRA_TESTPLAN_EVALUATION = "Jira Testplan Evaluation";
    private static final String ENVIRONMENT = "Environment: ";
    private static final String FIX_VERSION = "Fix Version: ";
    private static final String TESTPLAN_KEY = "Testplan Key: ";
    private static final String TESTPLAN_KEY_UNKNOWN = "Testplan Key: unknown";
    private static final String EVALUATION_DATE = "Evaluation date: ";

    private static final String JIRA_BROWSE_ISSUE_ID = "https://devstack.vwgroup.com/jira/browse/";

    /*
     * PDF
     */

    private String out_pdf;
    private static final String SUFFIX_PDF = ".pdf";

    private static final float[] TABLE_COLUM_WIDTHS = {30.0f, 70.0f};
    private static final float[] TABLE_COLUM_WIDTHS_FAIL_LISTS = new float[] {20.0f, 20.0f, 20.0f, 20.0f, 20.0f};

    private PdfCustomSplitCharactersForCellContent customSplitCharactersForCellContent;

    /**
     * @param hrv
     * @param properties
     * @param testplanReportName
     * @param devstackApiToken
     */
    public PrintPdf(
            HandlerRestVariable hrv,
            XrayProperties properties,
            String testplanReportName,
            String devstackApiToken) {

        this.hrv = hrv;

        this.properties = properties;

        this.out_pdf = testplanReportName + SUFFIX_PDF;

        this.readZipForErrorMessage = new ReadZipForErrorMessage();

        this.devstackApiToken = devstackApiToken;

        this.customSplitCharactersForCellContent = new PdfCustomSplitCharactersForCellContent();

    }

    @Override
    public void print(File directory, Map<String, TestExecutionResult> map) throws IOException {

        if (! directory.exists()) {
            directory.mkdir();
        }

        File out = new File(directory, this.out_pdf);

        if (out.exists()) {
            out.delete();
        }

        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(out);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        /*
         * Writing covering page.
         */

        this.writeCoveringPage(document);

        /*
         * Adding table with failed test cases on covering page.
         */

        this.writeTableFailedTestCases(document, map);

        //https://stackoverflow.com/a/40544535
        document.add(new AreaBreak()); // change page

        /*
         * Writing single page per test case.
         */

        Iterator<String> iterator = map.keySet().iterator();

        boolean hasElements = iterator.hasNext();

        if (! hasElements) {

            this.closeDocument(writer, pdf, document);

            return;
        }

        String key = iterator.next();

        TestExecutionResult ter = map.get(key);

        this.writePage(directory, document, ter);

        /*
         * Writing remaining pages of every test case.
         */
        while (iterator.hasNext()) {

            //https://stackoverflow.com/a/40544535
            document.add(new AreaBreak()); // change page

            key = iterator.next();

            ter = map.get(key);

            this.writePage(directory, document, ter);

        }

        this.closeDocument(writer, pdf, document);

    }

    private void writeCoveringPage(Document document) {

        document.add(new Paragraph(JIRA_TESTPLAN_EVALUATION));
        document.add(new Paragraph(ENVIRONMENT + System.getProperty(PROP_ENVIRONMENT)));
        document.add(new Paragraph(FIX_VERSION + System.getProperty(PROP_FIX_VERSION)));

        String testPlanKey = System.getProperty(PROP_TEST_PLAN_KEY);

        if (null != testPlanKey) {

            Paragraph p = new Paragraph();

            p.add(new Text(TESTPLAN_KEY));
            p.add(this.getLinkTestplanID(testPlanKey));

            document.add(p);

        } else {
            document.add(new Paragraph(TESTPLAN_KEY_UNKNOWN));
        }

        document.add(new Paragraph(EVALUATION_DATE + LocalDateTime.now().toString()));

    }

    private void writeTableFailedTestCases(Document document, Map<String, TestExecutionResult> map) {

        // Creating table
        Table table = new Table(UnitValue.createPercentArray(TABLE_COLUM_WIDTHS_FAIL_LISTS)).useAllAvailableWidth();

        int counter = 0;

        for (Entry<String, TestExecutionResult> entry : map.entrySet()) {

            Paragraph p = new Paragraph();

            String id = entry.getValue().getTestCaseId();

            p.add(id);
            p.setAction(PdfAction.createGoTo(id));

            Cell c = new Cell();

            String resultStatus = entry.getValue().getTestCaseStatusResult();

            if (BaseReporting.STATUS_FAIL.equals(resultStatus)) {

                if (entry.getValue().getBugs().size() > 0) {
                    c.setBackgroundColor(ColorConstants.RED);
                } else {
                    c.setBackgroundColor(ColorConstants.ORANGE);
                }

            } else if (BaseReporting.STATUS_PASS.equals(resultStatus)) {

                if (entry.getValue().getBugs().size() > 0) {
                    c.setBackgroundColor(ColorConstants.YELLOW);
                } else {
                    c.setBackgroundColor(ColorConstants.GREEN);
                }

            } else {
                c.setBackgroundColor(ColorConstants.GRAY);
            }

            c.add(p);

            table.addCell(c);

            counter++;

            if (TABLE_COLUM_WIDTHS_FAIL_LISTS.length == counter) {
                table.startNewRow();
                counter = 0;
            }

        }

        document.add(table);

    }

    /**
     * @param directory
     * @param document
     * @param ter
     * @throws IOException
     * @throws MalformedURLException
     */
    private void writePage(File directory, Document document, TestExecutionResult ter)
            throws IOException, MalformedURLException {

        // Add Paragraph to document

        Paragraph p = new Paragraph();

        p.add(TEST_CASE_ID + " ");

        // adding Link destination
        Paragraph parLink = this.getLinkTestCaseID(ter.getTestCaseId());
        parLink.setDestination(ter.getTestCaseId());

        p.add(parLink);

        document.add(p);

        Table table = this.createTestCaseTable(ter, directory);

        // Add table to page
        document.add(table);

        // add line
        document.add(new Paragraph(""));

        this.addErrorScreenshot(directory, document, ter);

    }

    /**
     * @param writer
     * @param pdf
     * @param document
     * @throws IOException
     */
    private void closeDocument(PdfWriter writer, PdfDocument pdf, Document document) throws IOException {

        //Close document
        document.close();

        pdf.close();

        writer.close();

    }

    /**
     * @param linkText
     * @return
     */
    private Link createLinkBrowseIssueID(String linkText) {

        Link chunk = new Link(linkText,
                PdfAction.createURI(JIRA_BROWSE_ISSUE_ID + linkText));

        return chunk;
    }

    private Paragraph getLinksBugs(List<String> bugs) {

        //
        Paragraph p = new Paragraph();

        if (0 == bugs.size()) {
            return p;
        }

        String bug = bugs.get(0);

        Link chunk = new Link(bug,
                PdfAction.createURI(JIRA_BROWSE_ISSUE_ID + bug));

        p.add(chunk);

        if (bugs.size() > 1) {

            for (String bugger : bugs) {

                p.add(", ");

                chunk = new Link(bugger,
                        PdfAction.createURI(JIRA_BROWSE_ISSUE_ID + bugger));

                p.add(chunk);

            }

        }

        return p;

    }

    private Paragraph getLinkExecutionTestStepLink(String testExecutionTestStepLink) {

        Link chunk = new Link(testExecutionTestStepLink,
                PdfAction.createURI(testExecutionTestStepLink));

        // see https://stackoverflow.com/a/61448817
        chunk.setProperty(Property.SPLIT_CHARACTERS, this.customSplitCharactersForCellContent);

        return new Paragraph(chunk);

    }

    /**
     * @param ter
     * @return
     */
    private Table createTestCaseTable(TestExecutionResult ter, File directory) {

        // Creating table
        Table table = new Table(UnitValue.createPercentArray(TABLE_COLUM_WIDTHS)).useAllAvailableWidth();

        table.addCell(TEST_EXECUTION_ID);
        table.addCell(this.getLinkTestExecution(ter.getTestExecutionId()));
        table.startNewRow();
        table.addCell(TEST_ERROR_MESSAGE);
        table.addCell(this.getErrorMessage(ter, directory));
        table.startNewRow();
        table.addCell(TEST_CASE_BUGS);
        table.addCell(this.getLinksBugs(ter.getBugs()));
        table.startNewRow();
        table.addCell(TEST_EXECUTION_TEST_STEPS_LINK);
        table.addCell(this.getLinkExecutionTestStepLink(ter.getTestExecutionTestStepLink()));
        table.startNewRow();

        return table;

    }

    private Paragraph getLinkTestplanID(String issueId) {
        return new Paragraph(this.createLinkBrowseIssueID(issueId));
    }

    private Paragraph getLinkTestCaseID(String testCaseId) {
        return new Paragraph(this.createLinkBrowseIssueID(testCaseId));
    }

    private Paragraph getLinkTestExecution(String testExecutionId) {

    	if (null != testExecutionId) {
        	return new Paragraph(this.createLinkBrowseIssueID(testExecutionId));
        } else {
        	return new Paragraph("no-id-available, maybe no test execution existing");
        }

    }

    /**
     * @param directory
     * @param document
     * @param ter
     * @throws IOException
     * @throws MalformedURLException
     */
    private void addErrorScreenshot(File directory, Document document, TestExecutionResult ter)
            throws IOException, MalformedURLException {

        // Add image to page (if available)
        Image image = loadErrorScreenshot(directory, ter);

        if (null != image &&
                image.getImageHeight() < 10000.0f) {

            image.setAutoScale(true);
            document.add(image);

        }

    }

    /**
     * @param directory
     * @param ter
     * @return
     * @throws IOException
     * @throws MalformedURLException
     */
    private Image loadErrorScreenshot(File directory, TestExecutionResult ter)
            throws IOException, MalformedURLException {

        Image image = null;

        if (null == ter.getLinkEvidenceScreenshot() ||
                ter.getLinkEvidenceScreenshot().length() == 0) {
            return image;
        }

        try {

        		File file = this.loadFileByLink(ter.getLinkEvidenceScreenshot(), directory);

            if (null != file) {
                image = new Image(ImageDataFactory.create(file.getAbsolutePath().toString()));
            }

        } catch (KeyManagementException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException
                | CertificateException | IllegalArgumentException e) {
            image = null;
            logReporting.fine("Loading and processing image caused exception: " + e.getMessage());
        }

        return image;

    }

    /**
     * Current code based on https://stackoverflow.com/a/10960438
     *
     * @param fileUrl
     * @param directory
     * @return
     * @throws IOException
     * @throws KeyManagementException
     * @throws UnrecoverableKeyException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     */
    private File loadFileByLink(
            String fileUrl,
            File directory) throws IOException, KeyManagementException, UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException, CertificateException {

        File file = hrv.getResultByUrlAsFile(
                fileUrl,
                this.properties,
                this.devstackApiToken);

        return file;

    }

    private Paragraph getErrorMessage(TestExecutionResult ter, File directory) {

        //
        Paragraph p = new Paragraph();

        // see https://stackoverflow.com/a/61448817
        p.setProperty(Property.SPLIT_CHARACTERS, this.customSplitCharactersForCellContent);

        String errorMessageAssembled = this.readZipForErrorMessage.loadAndReadZipFile(
                ter,
                directory,
                this.hrv,
                this.properties,
                this.devstackApiToken);

        p.add(errorMessageAssembled);

        return p;
    }

}
