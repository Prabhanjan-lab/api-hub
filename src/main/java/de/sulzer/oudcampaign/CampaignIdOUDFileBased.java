/**
 *
 */
package de.sulzer.oudcampaign;

import de.sulzer.oudcampaign.idgenerator.model.Figure;
import de.sulzer.oudcampaign.idgenerator.values.Values;
import de.sulzer.oudcampaign.idprocessor.ProcessIds;
import de.sulzer.oudcampaign.util.FileOperations;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author GCH9F5D
 */
public class CampaignIdOUDFileBased {

	private static CampaignIdOUDFileBased singleton;
	private static final Lock lockGettingId = new ReentrantLock(true);
	private static final Lock lockIdCreation = new ReentrantLock(true);
	private static ProcessIds processIds;
	private static String fileWithIds;
	private static String lockingFileIDs;

	/*
	 * files
	 */


	public static String getFileWithIds() {

		String os = System.getProperty("os.name").toLowerCase();

		// System.property == WORKSPACE Jenkins
		if (os.contains("linux")) {
			fileWithIds = System.getProperty("user.dir") + "/ids.txt";
		}

		// Local
		if (os.contains("windows")) {
			fileWithIds = "unique_id/ids.txt";
		}
		return fileWithIds;
	}

	public static String getLockingFileIDs() {

		String os = System.getProperty("os.name").toLowerCase();

		// System.property == WORKSPACE Jenkins
		if (os.contains("linux")) {
			lockingFileIDs = System.getProperty("user.dir") + "/lock.file";
		}

		// Local
		if (os.contains("windows")) {
			lockingFileIDs = "unique_id/lock.file";
		}
		return lockingFileIDs;
	}


	private CampaignIdOUDFileBased() throws Exception {
		init();
	}

	public static CampaignIdOUDFileBased getInstance() throws Exception {

		if (null == singleton) {
			singleton = new CampaignIdOUDFileBased();
		}

		return singleton;
	}

	private static void init() throws Exception {
		createIds(); // if not yet existing
	}

	/**
	 * Creating file with IDs, if not yet existing.
	 *
	 */
	private static void createIds() throws Exception {
		lockIdCreation.lock();

		try {
			FileChannel fc = null;
			FileLock fileLock = null;
			File fileIds;

			try {
				// Create lock file if it doesn't exist
				File lockFile = new File(getLockingFileIDs());
				lockFile.createNewFile();

				// Open file channel for lock and read/write operations
				Path path = Paths.get(getLockingFileIDs());
				fc = FileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE);

				// Try to acquire lock on the file
				while (true) {
					try {
						fileLock = fc.tryLock();
						break;
					} catch (Exception e) {
						e.printStackTrace();
						Thread.sleep(1000); // Wait for 1 second and retry if lock is not acquired
					}
				}

				// Check if ids.txt file exists and is empty
				fileIds = new File(getFileWithIds());
				if (!fileIds.exists() || fileIds.length() == 0) {
					createIdFile();
				}

			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				throw new Exception(e.getMessage());
			} finally {
				// Release file lock
				if (fileLock != null && fileLock.isValid()) {
					try {
						fileLock.release();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// Close file channel
				if (fc != null && fc.isOpen()) {
					try {
						fc.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} finally {
			lockIdCreation.unlock();
		}
	}


	private static void createIdFile() throws Exception {


		// loaded IDs from ONUP
		String regexOnup = "TF[A-Z0-9]{2}";

		// id range to generate
		String regexIdGenerator = "TF[A-Z0-9]{2}";

		Figure figure = new Figure("TAAA", Values.lettersAZ09);

		processIds = new ProcessIds(regexOnup, regexIdGenerator, figure);

		// create ID file
		writeIdFile(processIds.getListIds(), getFileWithIds());
	}

	private static void writeIdFile(List<String> listIds, String fileWithIds) {

		// reversing order of elements because file is read from end to start
		Collections.reverse(listIds);

		// writing list to file
		FileOperations.writingFile(listIds, new File(fileWithIds));

	}

	public String ascertainNewId() throws Exception {

		lockGettingId.lock();

		try {

			//
			String id;
			//
			FileChannel fc = null;
			Path path;
			FileLock fileLock = null;
			File fileIds;

			try {

				//
				File lockFile = new File(getLockingFileIDs());
				lockFile.createNewFile();
				//
				path = Paths.get(getLockingFileIDs());
				fc = FileChannel.open(path,
						StandardOpenOption.READ,
						StandardOpenOption.WRITE);

				// try to acquire  lock
				while (true) {

					try {
						fileLock = fc.tryLock();
						break;
					} catch (Exception ignored) {
					}
					// Wait for 1 second and retry if the lock is not acquired
					Thread.sleep(1000);
				}

				// creating file
				fileIds = new File(getFileWithIds());
				// reading one ID from file
				id = FileOperations.readId(fileIds);

			} catch (Exception e) {
				throw new Exception(e.getMessage());

			} finally {

				// releasing lock
				if (null != fileLock && fileLock.isValid()) {
					try {
						fileLock.release();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// closing channel
				if (null != fc && fc.isOpen()) {
					try {
						fc.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			System.out.println("Recall ID : " + id);
			return id;

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
			lockGettingId.unlock();
		}

	}





}

