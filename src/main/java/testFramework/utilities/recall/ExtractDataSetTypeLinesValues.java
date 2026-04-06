package testFramework.utilities.recall;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class ExtractDataSetTypeLinesValues {

    private ExtractDataSetTypeLinesValues() {
    }

    private static synchronized Optional<Path> findLastCreatedFile(String targetDirectory) throws IOException {
        return Files.list(Paths.get(targetDirectory))
                .filter(f -> !Files.isDirectory(f))
                .max(Comparator.comparingLong(f -> f.toFile().lastModified()));
    }

    private static synchronized void deleteTempFile(File tempFile) {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    public static synchronized List<String> getFoundValuesWithoutZipFile(String targetDirectory, String lineType, int startPosition, int endPosition) {
        List<String> values = new ArrayList<>();
        try {
            Optional<Path> lastFilePath = findLastCreatedFile(targetDirectory);
            if (lastFilePath.isPresent()) {
                File tempFile = lastFilePath.get().toFile();
                try (Stream<String> stream = Files.lines(tempFile.toPath())) {
                    stream.filter(s -> s.startsWith(lineType))
                          .map(s -> s.substring(startPosition, endPosition))
                          .forEach(values::add);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                deleteTempFile(tempFile);
            }
        } catch (IOException e) {
            // Handle exception
        }
        return values;
    }
}