package testCases.reusable_TestSteps;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JsonRolloutDateUpdater {

	public static void updateToTomorrowDate(String filePath) {
		updateRolloutDate(filePath, LocalDate.now().plusDays(1));
	}

	public static void updateToYesterdayDate(String filePath) {
		updateRolloutDate(filePath, LocalDate.now().minusDays(1));
	}

	public static void updateToSpecificDate(String filePath, int year, int month, int day) {
		updateRolloutDate(filePath, LocalDate.of(year, month, day));
	}

	private static void updateRolloutDate(String filePath, LocalDate newDate) {
		try {
			// Read the file content as a String
			String jsonContent = new String(Files.readAllBytes(Paths.get(filePath)));

			// Calculate the new date in yyyyMMdd format
			String formattedDate = newDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

			// Regex to match the rollout field
			String regex = "(\"rollout\"\\s*:\\s*\")[0-9]{8}(\"\\s*)";
			String replacement = "$1" + formattedDate + "$2";

			// Replace the rollout date
			String updatedJsonContent = jsonContent.replaceAll(regex, replacement);

			// Write the updated content back to the file
			Files.write(Paths.get(filePath), updatedJsonContent.getBytes());

			System.out.println("Rollout field updated successfully to " + formattedDate);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("An error occurred while updating the rollout field.");
		}
	}
}
