/**
 *
 */
package testCases.testplanreporting.output;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import de.sulzer.REST.XrayProperties;
import de.sulzer.REST.jira.HandlerRestVariable;
import testCases.testplanreporting.TestExecutionResult;
import testFramework.utilities.zipfiles.ZipFileOperations;

/**
 * @author Sulzer GmbH
 *
 */
public class ReadZipForErrorMessage {

    private static final String READING_ZIP_FILE_WITH_ERROR_MESSAGE_CAUSED_AN_ERROR =
            "Reading zip file with error message caused an error: ";

    private ZipFileOperations zipFileOperations;

    private FileLoader fileLoader;

    /**
     *
     */
    public ReadZipForErrorMessage() {

        this.zipFileOperations = new ZipFileOperations();

        this.fileLoader = new FileLoader();

    }

    /**
     * @param ter
     * @param directory
     * @param errorMessage
     * @return
     */
    public String loadAndReadZipFile(
            TestExecutionResult ter,
            File directory,
            HandlerRestVariable hrv,
            XrayProperties properties,
            String devstackApiToken) {

        if (null == ter.getLinkEvidenceZipFileErrorText() ||
                ter.getLinkEvidenceZipFileErrorText().length() == 0) {
            return "";
        }

        File zipFileWithErrorMessage;

        String errorMessageAssembled = "";

        try {

            zipFileWithErrorMessage = this.fileLoader.loadFileByLink(
                    ter.getLinkEvidenceZipFileErrorText(),
                    directory,
                    hrv,
                    properties,
                    devstackApiToken);

            if (null != zipFileWithErrorMessage) {

                String str = this.readFirstLineErrorZipFile(zipFileWithErrorMessage);

                if (str.contains(READING_ZIP_FILE_WITH_ERROR_MESSAGE_CAUSED_AN_ERROR)) {
                    errorMessageAssembled = errorMessageAssembled + str;
                } else {

                    if (str.length() > 150) {
                        errorMessageAssembled = str.substring(0, 150);
                    } else {
                        errorMessageAssembled = str;
                    }

                }

            } else {
                errorMessageAssembled = "No error message availble.";
            }

        } catch (KeyManagementException | UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException
                | CertificateException | IOException e) {
            errorMessageAssembled = "Couldn't read zip file with error message: " + e.getMessage();
        }

        return errorMessageAssembled;

    }

    private String readFirstLineErrorZipFile(File zipFileWithErrorMessage) {

        File tempFile = null;

        String content = "";

        try {

            if (zipFileWithErrorMessage.exists()) { // your folder may be empty

                tempFile = this.zipFileOperations.readZipFile(zipFileWithErrorMessage.toPath());

                byte[] bytes = Files.readAllBytes(Paths.get(tempFile.toURI()));
                content = new String(bytes, StandardCharsets.UTF_8);

            }

        } catch (IOException e) {
            content = READING_ZIP_FILE_WITH_ERROR_MESSAGE_CAUSED_AN_ERROR + e.getMessage();
        } finally {

            if (null != tempFile) {
                this.deleteTempFile(tempFile);
            }

        }

        return content;

    }

    private void deleteTempFile(File tempFile) {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }
}
