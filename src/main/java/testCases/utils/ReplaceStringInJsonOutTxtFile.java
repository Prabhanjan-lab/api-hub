package testCases.utils;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class ReplaceStringInJsonOutTxtFile {

    public static void modifyOutTextFile(String filePath, String targetLine,String subText, int startColumn, int length, String replacement) throws IOException {
        // Read all lines from the file
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);

        // Get the target line (adjust to 0-based index)
        for(int i=0;i<lines.size();i++)
        {
        	String line= lines.get(i);
        	if(line.startsWith(targetLine)&&line.contains(subText)) {
	
        // Check if the starting column and length are valid for the line's length
        if (startColumn <= 0 || startColumn + length - 1 > line.length()) {
            System.out.println("Error: The specified column range is out of bounds.");
            return;
        }

        // Replace the specified substring with the replacement string
        StringBuilder modifiedLine = new StringBuilder(line);
        modifiedLine.replace(startColumn - 1, startColumn - 1 + length, replacement);  // Adjust for 0-based index

        // Update the line in the list
        lines.set(i , modifiedLine.toString());
        // Write the modified content back to the file
        Files.write(path, lines);
        System.out.println("Substring replacement complete. Check the file for updates.");
        	}
        }
     }
}

