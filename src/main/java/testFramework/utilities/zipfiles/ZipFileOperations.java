/**
 *
 */
package testFramework.utilities.zipfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * Reading latest file in directory and getting content of file by lines.
 * Temporary extracted file is deleted afterwards.
 *
 * @author Sulzer GmbH
 *
 */
public class ZipFileOperations{

	/**
	 *
	 */
	public ZipFileOperations() {

	}

	/**
	 * @param zipFile
	 * @return List of lines (as String) contained in given Zip-file.
	 * @throws Exception
	 */
	public List<String> getFoundValues(File zipFile) throws Exception {

		List<String> values = new ArrayList();

		/*
		 * Following code is according:
		 * https://stackoverflow.com/a/30892976
		 */

		if ( zipFile.exists() ) { // your folder may be empty

			File tempFile = this.readZipFile(zipFile.toPath());

			// getting lines and values of line type
			try (Stream<String> stream = Files.lines(tempFile.toPath())) {

				Stream<String> lines = stream.sequential();

				lines.forEach(o -> values.add(o));

			} catch (IOException e) {
				throw new Exception(e);
			}

			deleteTempFile(tempFile);

		}

		return values;
	}

	/**
	 * @param lastFilePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public File readZipFile(Path lastFilePath) throws FileNotFoundException, IOException {

		/*
		 * Code for unzipping is from:
		 * https://www.baeldung.com/java-compress-and-uncompress
		 */
		ZipInputStream zis = new ZipInputStream(new FileInputStream(lastFilePath.toFile()));
		zis.getNextEntry(); // setting to first entry NECESSARY for reading!

		File tempFile = File.createTempFile("unzipping", ".txt");

		FileOutputStream fos = new FileOutputStream(tempFile);
		byte[] buffer = new byte[1024];
		int len;

		while ((len = zis.read(buffer)) > 0) {
		    fos.write(buffer, 0, len);
		}

		fos.close();

		zis.closeEntry();
		zis.close();

		return tempFile;

	}

	/**
	 * @param sb
	 * @param target
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeZipFile(String content, File target, String zipEntryName) throws FileNotFoundException, IOException {

		ZipEntry catalogEntry = new ZipEntry(zipEntryName);
		FileOutputStream out = new FileOutputStream(target);
		ZipOutputStream zip = new ZipOutputStream(out);
		zip.putNextEntry(catalogEntry);

		OutputStreamWriter writer = new OutputStreamWriter(zip);

		writer.write(content);
		writer.flush();

		zip.close();

	}

	/**
	 * @param tempFile
	 */
	private void deleteTempFile(File tempFile) {
		if (tempFile.exists()) {
			tempFile.delete();
		}
	}

}
