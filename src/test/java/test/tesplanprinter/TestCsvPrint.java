package test.tesplanprinter;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;
import testCases.testplanreporting.BaseReporting;
import testCases.testplanreporting.OUDTA_17_TestplanReportingFails;
import testCases.testplanreporting.TestExecutionResult;
import testCases.testplanreporting.output.PrintTableCsv;

class TestCsvPrint {

	private TestExecutionResult ter;

	private Map<String, TestExecutionResult> mapTestExecutions;
	private PrintTableCsv pc;

	@BeforeEach
	void setUp() throws Exception {

		this.mapTestExecutions = new HashMap();
		this.pc = new PrintTableCsv("out", new HandlerRestVariable(), new XrayProperties(), "Jira-API-Token");

		this.ter = new TestExecutionResult(BaseReporting.STATUS_FAIL);

		this.ter.setTestCaseId("OUQA-0000");
		this.ter.setTestExecutionId("OUQA-00000");
		this.ter.setErrorMessageShortened("short error message");

		List<String> bug = new ArrayList<String>();
		bug.add("Bug me-0000");
		bug.add("Bug me-0001");
		bug.add("Bug me-0002");
		this.ter.setBugs(bug);

		this.mapTestExecutions.put("failure", ter);

	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testPrintCsvFileWritten() throws IOException {

		File directory = new File("data/reporting/");
		File file = new File(directory, "out.csv");

		if (file.exists()) {
			file.delete();
		}

		if (directory.exists()) {
			directory.delete();
		}

		this.pc.print(directory, this.mapTestExecutions);

		assertTrue(file.exists());

	}

}
