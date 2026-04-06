/**
 *
 */
package testCases.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import testFramework.constants.ConstantsCommunicationRestServer;

/**
 * @author Bege
 *
 */
public class UtilFileHandling {

	/**
	 *
	 */
	public UtilFileHandling() {

	}

	/**
	 * @param stringContent
	 * @param file
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void writeStringContentToFile(String stringContent, File file)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {

		// making sure, content is written as UTF-8, from https://stackoverflow.com/a/1001568
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), ConstantsCommunicationRestServer.UTF_8));
		bw.write(stringContent);
		bw.close();
	}

}
