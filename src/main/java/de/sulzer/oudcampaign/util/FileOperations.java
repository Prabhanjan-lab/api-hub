/**
 *
 */
package de.sulzer.oudcampaign.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author Bege
 *
 */
public final class FileOperations {

	/**
	 *
	 */
	private FileOperations() {

	}

	/**
	 * Reads one ID from given file.
	 *
	 * @param fileIds
	 * @return A string representing one ID with four figures (e. g.: 'TAAA')
	 * @throws IOException
	 */
	public static String readId(File fileIds) throws IOException {

		// bytes to read (ID has four figures)
		byte figure_count = 4;

		Path path = fileIds.toPath();
		FileChannel fc = FileChannel.open(path,
		                                  StandardOpenOption.READ,
		                                  StandardOpenOption.WRITE);

		long length = fc.size();

		ByteBuffer bf = ByteBuffer.allocate(figure_count);

		// reading last four byte
		fc.read(bf, length - figure_count);

		// truncating file by 4 bytes (just read four bytes, ascertained ID)
		fc.truncate(length - figure_count);
		// close file
		fc.close();

		return new String(bf.array());
	}

	/**
	 * @param ids
	 * @param fileIds
	 */
	public static void writingFile(List<String> ids, File fileIds) {

		FileWriter fw;

		try {

			fw = new FileWriter(fileIds);
			BufferedWriter bw = new BufferedWriter(fw);

			for (String id : ids) {
				bw.append(id);
			}

			bw.flush();
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void deleteFile(File fileIds) {
		fileIds.delete();
	}

}
