package testCases.reusable_TestSteps;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TestExecutionScheduler {

	public static void waitUntilNextDayAndRunTest() {
		ZonedDateTime nowUtc = ZonedDateTime.now(ZoneOffset.UTC);
		ZonedDateTime nextUtcMidnight = nowUtc.plusDays(1).withHour(1).withMinute(20).withSecond(0).withNano(0);

		System.out.println("Current UTC time: " + nowUtc);
		System.out.println("Next execution at UTC: " + nextUtcMidnight + " (midnight UTC)");

		// Loop and check the time until we reach midnight in UTC
		while (ZonedDateTime.now(ZoneOffset.UTC).isBefore(nextUtcMidnight)) {
			try {
				Thread.sleep(1000); // Sleep for 1 second, then check again
			} catch (InterruptedException e) {
				System.err.println("Sleep interrupted: " + e.getMessage());
				return; // Exit method if interrupted
			}
		}

		// Run the test case once at midnight UTC
		System.out.println("Executing test at UTC: " + ZonedDateTime.now(ZoneOffset.UTC));
		runTestCase();
	}

	/**
	 * Placeholder for test case execution logic.
	 */
	public static void runTestCase() {
		System.out.println("Running test case at UTC: " + ZonedDateTime.now(ZoneOffset.UTC));
		// Add your specific test case logic here
	}

	public static void main(String[] args) {
		waitUntilNextDayAndRunTest();
	}
}
