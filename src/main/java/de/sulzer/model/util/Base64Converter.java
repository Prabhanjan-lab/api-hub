/**
 * 
 */
package de.sulzer.model.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

/**
 * @author Bege
 *
 */
public class Base64Converter {

	/**
	 * 
	 */
	private Base64Converter() {

	}

	public static String getImageAsBase64(File file) throws IOException {

		String encoded = "";

		byte[] byteArray = Files.readAllBytes(file.toPath());

		encoded = Base64.getEncoder().encodeToString(byteArray);

		return encoded;

	}

}
