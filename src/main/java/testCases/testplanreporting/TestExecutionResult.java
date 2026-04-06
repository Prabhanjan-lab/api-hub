/**
 *
 */
package testCases.testplanreporting;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.sulzer.REST.ConstantsURLs;

/**
 * @author Sulzer GmbH
 *
 */

public class TestExecutionResult {

    private static final String HOST = ConstantsURLs.HOST_JIRA;
    private static final String URL_TEST_EXECUTION_RESULT = "/jira/secure/XrayExecuteTest!default.jspa";

    private String testCaseId;
    private String testCaseTitle;
    private String testExecutionId;
    private LocalDateTime timestamp;
    private String errorMessageShortened;
    private String linkEvidenceScreenshot;
    private String linkEvidenceLinkZipFileErrorText;
    private List<String> bugs;
    private List<String> bugPriorities;
    private List<String[]> testSteps;
    private String testExecutionTestStepLink;
    private String testCaseStatusResult;

    public TestExecutionResult(String statusResult) {

        this.testCaseStatusResult = statusResult;

        this.bugs = new ArrayList();
        this.bugPriorities = new ArrayList();
        this.testSteps = new ArrayList();

    }

    /**
     * @return the testCaseId
     */
    public String getTestCaseId() {
        return testCaseId;
    }

    /**
     * @param testCaseId the testCaseId to set
     */
    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getTestCaseTitle() {
        return testCaseTitle;
    }

    public void setTestCaseTitle(String testCaseTitle) {
        this.testCaseTitle = testCaseTitle;
    }

    /**
     * @return the testExecutionId
     */
    public String getTestExecutionId() {
        return testExecutionId;
    }

    /**
     * @param testExecutionId the testExecutionId to set
     */
    public void setTestExecutionId(String testExecutionId) {
        this.testExecutionId = testExecutionId;
    }

    /**
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the errorMessageShortened
     */
    public String getErrorMessageShortened() {
        return errorMessageShortened;
    }

    /**
     * @param errorMessageShortened the errorMessageShortened to set
     */
    public void setErrorMessageShortened(String errorMessageShortened) {
        this.errorMessageShortened = errorMessageShortened;
    }

    /**
     * @return the linkEvidenceScreenshot
     */
    public String getLinkEvidenceScreenshot() {
        return linkEvidenceScreenshot;
    }

    /**
     * @param linkEvidenceScreenshot the linkEvidenceScreenshot to set
     */
    public void setLinkEvidenceScreenshot(String linkEvidenceScreenshot) {
        this.linkEvidenceScreenshot = linkEvidenceScreenshot;
    }

    /**
     * @return the linkEvidenceZipFileErrorText
     */
    public String getLinkEvidenceZipFileErrorText() {
        return linkEvidenceLinkZipFileErrorText;
    }

    /**
     * @param linkEvidenceZipFileErrorText the linkEvidenceZipFileErrorText to set
     */
    public void setLinkEvidenceZipFileErrorText(String linkEvidenceZipFileErrorText) {
        this.linkEvidenceLinkZipFileErrorText = linkEvidenceZipFileErrorText;
    }

    /**
     * @return the bugs
     */
    public List<String> getBugs() {
        return bugs;
    }

    /**
     * @param bugs the bugs to set
     */
    public void setBugs(List<String> bugs) {

        if (null == this.bugs) {
            this.bugs = new ArrayList<>();
        }

        this.bugs = bugs;
    }

    /**
     * @return the bugs priorities
     */
    public List<String> getBugPriorities() {

        this.initBugPriorities();

        return this.bugPriorities;
    }

    /**
     * @param bugPriorities the bugs to set
     */
    public void setBugPriorities(List<String> bugPriorities) {

        this.initBugPriorities();

        this.bugPriorities = bugPriorities;
    }



    /**
     *
     */
    private void initBugPriorities() {
        if (null == this.bugPriorities) {
            this.bugPriorities = new ArrayList();
        }
    }

    /**
     * @param
     * @return testSteps
     */
    public List<String[]> getTestSteps() {

        if (null == this.testSteps) {
            this.testSteps = new ArrayList<String[]>();
        }

        return this.testSteps;

    }


    /**
     * @param testStep
     */
    public void addTestStep(String[] testStep) {

        if (null == this.testSteps) {
            this.testSteps = new ArrayList<String[]>();
        }

        this.testSteps.add(testStep);

    }

    /**
     * @return the testExecutionTestStepLink
     */
    public String getTestExecutionTestStepLink() {

        if (null == this.testExecutionTestStepLink ||
                this.testExecutionTestStepLink.equals("")) {
            this.createTestExecutionTestStepLink();
        }

        return testExecutionTestStepLink;
    }

    public void createTestExecutionTestStepLink() {
        this.testExecutionTestStepLink =
                HOST + URL_TEST_EXECUTION_RESULT + "?testExecIssueKey=" + this.testExecutionId + "&testIssueKey=" + this.testCaseId;
    }

    /**
     * @return the testCaseStatusResult
     */
    public String getTestCaseStatusResult() {
        return testCaseStatusResult;
    }

}

