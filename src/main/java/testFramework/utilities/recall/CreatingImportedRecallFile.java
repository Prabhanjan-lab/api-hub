package testFramework.utilities.recall;

import testFramework.constants.ValuesGlobal;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import testCases.utils.ReplaceStringInJsonOutTxtFile;
import testCases.util.ConstantsReCallImports;

import static org.junit.jupiter.api.Assertions.assertTrue;



public class CreatingImportedRecallFile {

    private static final AppExtended ae = new AppExtended();
    private static String importedRecallFileNameForZip;

    public static void getContent(String importedRecallFileName, String content) throws Exception {

        // Check if the directory exists, if not, create it
        createFolderWithDirs(ValuesGlobal.LOCAL_TEMP_FOLDER);

        String jsonPath = ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName  + ".json";
        importedRecallFileNameForZip = importedRecallFileName;

        // Path to the json file to be created
        File jsonFile = new File(ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName  + ".json");

        // Write content to file
        FileWriter writer = new FileWriter(jsonFile);
        writer.write(content);
        writer.close();

        // Create json and json.out.txt files according to the recallSpecification_v0_9_0.schema.json by running
        // the main method in class de.audi.oru.recall.AppExtended.
        String[] args ;
        if(importedRecallFileName.contains("EEC.N5MI3J.ORU.DATA.")){
        args= new String[] {
                "-i",
                jsonPath,
                "-s",
                "data/importReCall/recall2_Specification_v1_0.schema.json"};
        }
        else {
        	args= new String[] {
                    "-i",
                    jsonPath,
                    "-s",
                    "data/importReCall/recallSpecification_v0_9_0.schema.json"};
        }

        try {
            ae.process(args);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        // Json and json.out.txt files are created by the process method.
        String outTxtFileName = ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName + ".json.out.txt";
        String fileName = ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName;

        // Change json.out.txt file name
        File outTxtFile = new File(outTxtFileName);
        File newFile = new File(fileName);

        if (outTxtFile.exists()) {
            if (outTxtFile.renameTo(newFile)) {
                System.out.println("The recall file : " + importedRecallFileName + " to be imported has been created successfully.");

                // Print the content of the json.out.txt file
//                try {
//                    String contentTxt = new String(Files.readAllBytes(Paths.get(newFile.getPath())));
//                    System.out.println("Content of the file: \n" + contentTxt);
//                } catch (IOException e) {
//                    System.err.println("Error reading the file: " + e.getMessage());
//                }

            } else {
                System.err.println("Could not change file name.");
            }
        } else {
            System.err.println("File does not exist.");
        }

        // The file was zipped and the .zip extension was deleted.
        zipFile(fileName);
        System.out.println("The recall file : " + importedRecallFileName + " to be imported was created, zipped and the .zip extension was deleted.");

        // Delete json file
        Thread.sleep(2000);
        assertTrue(jsonFile.exists());
        assertTrue(newFile.length()>0);
        CreatingImportedRecallFile.deleteImportedRecallTemplate(ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName + ".json");
    }


    public static void getContent_Out_Txt_Without_Json_File(String importedRecallFileName, String content) throws Exception {

        // Check if the directory exists, if not, create it
        createFolderWithDirs(ValuesGlobal.LOCAL_TEMP_FOLDER);

        importedRecallFileNameForZip = importedRecallFileName;


        // Json and json.out.txt files are created by the process method.
        String fileName = ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName;

        File newFile = new File(fileName);

        try (OutputStream out = Files.newOutputStream(newFile.toPath())) {
            byte[] contentBytes = content.getBytes();
            out.write(contentBytes);
            System.out.println("The content has been written to the file successfully.");
        } catch (IOException e) {
            System.err.println("File could not be written: " + e.getMessage());
        }

        // The file was zipped and the .zip extension was deleted.
        zipFile(fileName);
        System.out.println("The recall file : " + importedRecallFileName + " to be imported was created, zipped and the .zip extension was deleted.");
    }

    public void testCompliantStandardReCallJsonWithoutVehicles() throws Exception {

        String[] args = {
                "-i",
                "data/importReCall/recallSpecification_v0_9_0.example.WithOneVehicles.json",
                "-s",
                "data/importReCall/recallSpecification_v0_9_0.schema.json"};

        try {
            ae.process(args);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        File f = new File("data/importReCall/recallSpecification_v0_9_0.example.WithOneVehicles.json" + ".out.txt");

        Thread.sleep(2000);
        assertTrue(f.exists());

        if (f.exists()) {
            f.delete();
        }

    }


    /**
     * Generates a unique file name based on the current date and time.
     * The format of the file name is "EEC.R11I3J.ORU.DATA.yyyyMMddss".
     *
     * @return A unique file name string.
     */
    public static String generateFileName() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define the format for the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddss");

        // Format the current date and time according to the defined format
        String formattedDateTime = now.format(formatter);

        // Construct and return the unique file name
        return "EEC.R11I3J.ORU.DATA." + formattedDateTime;
    }
    
    /**
     * Generates a unique file name based on the current date and time.
     * The format of the file name is "EEC.N5MI3J.ORU.DATA.yyyyMMddss".
     *
     * @return A unique file name string.
     */
    public static String generateRecall2FileName() {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();

        // Define the format for the date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddss");

        // Format the current date and time according to the defined format
        String formattedDateTime = now.format(formatter);

        // Construct and return the unique file name
        return "EEC.N5MI3J.ORU.DATA." + formattedDateTime;
    }



    /**
     * Zip the specified file and then rename the zip file to remove the .zip extension
     * @param filePath The path of the file to be zipped
     */
    public static void zipFile(String filePath) {
        try {
            // Create a FileOutputStream to write the zip file
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + ".zip");
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            
            // Compress the file
            addToZipFile(filePath, zipOutputStream);
            

            // Close the zip file
            zipOutputStream.close();
            fileOutputStream.close();

            // Delete first created File
            File firstCreatedFile = new File(filePath);
            
            if (firstCreatedFile.exists()&& firstCreatedFile.length()>0) {
                firstCreatedFile.delete();
                System.out.println(filePath + " file deleted successfully");
            }

            // Remove the .zip extension
            File zipFile = new File(filePath + ".zip");
            File newFile = new File(filePath);
            
            if (zipFile.exists() && zipFile.renameTo(newFile)) {
            	long fileSizeInBytes = newFile.length();
                System.out.println("File size: " + fileSizeInBytes + " bytes");
            	if(newFile.length()>0) {
                System.out.println("File successfully zipped and .zip extension removed.");
            	}
            } else {
                System.err.println("Error: There was an error while zipping the file or removing the .zip extension.");

                // Delete created files
                deleteImportedRecallTemplate(ValuesGlobal.LOCAL_TEMP_FOLDER +importedRecallFileNameForZip);
                deleteImportedRecallTemplate(ValuesGlobal.LOCAL_TEMP_FOLDER +importedRecallFileNameForZip + ".zip");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add the file to the specified ZipOutputStream
     * @param filePath The path of the file to be added to the zip file
     * @param zipOutputStream The ZipOutputStream to which the file will be added
     * @throws IOException If an I/O error occurs
     */
    private static void addToZipFile(String filePath, ZipOutputStream zipOutputStream) throws IOException {

        // Create a FileInputStream to read the file
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);

        // Create a ZipEntry for the file
        ZipEntry zipEntry = new ZipEntry(file.getName());

        // Put the next entry in the zip file
        zipOutputStream.putNextEntry(zipEntry);

        // Read the file and write its contents to the zip file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = fileInputStream.read(buffer)) >= 0) {
            zipOutputStream.write(buffer, 0, length);
        }

        // Close the entry in the zip file
        zipOutputStream.closeEntry();

        // Close the FileInputStream
        fileInputStream.close();
    }


    public static void deleteImportedRecallTemplate(String fileName){

        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }

        File file = new File(fileName);

        if (file.exists()) {
            boolean deleted = file.delete();
            if (deleted) {
                System.out.println(fileName + " file deleted successfully");
            } else {
                System.out.println(fileName + " file could not be deleted");
            }
        } else {
            System.out.println(fileName + " file is not exist");
        }
    }




    public static void callRecallGeneratorMain(String inputFileName) {
        try {

            // Specify the package name of the Generator class
            String recallGeneratorClassName = "de.audi.oru.recall.AppExtended";

            // Load the RecallGenerator class
            Class<?> recallGeneratorClass = Class.forName(recallGeneratorClassName);

            // Get the main method from the RecallGenerator class
            Method mainMethod = recallGeneratorClass.getMethod("main", String[].class);

            // Define the arguments required to call the main method in the RecallGenerator class
            String[] generatorArgs = {"-i", inputFileName, "-s", "recallSpecification_v0_9_0.schema.json"};

            // Call the main method
            mainMethod.invoke(null, (Object) generatorArgs);


        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred while calling Generator.main: " + e.getMessage());
        }
    }

    private static void createFolderWithDirs(String folderPath) throws IOException {
        File folder = new File(folderPath);

        // Check if the directory exists, if not, create it
        if (!folder.exists()) {
            boolean dirsCreated = folder.mkdirs();
            if (dirsCreated) {
                System.out.println("Directories created: " + folder.getPath());
            } else {
                throw new IOException("Failed to create directories: " + folder.getPath());
            }
        } else {
            System.out.println("Directory already exists: " + folder.getPath());
        }
    }
    public static void getContentWithFaultyValues(String importedRecallFileName, String content,String targetLine,String subText,String faultyCode,int faultyCodeIndex,int faultyCodeLength) throws Exception {

        // Check if the directory exists, if not, create it
        createFolderWithDirs(ValuesGlobal.LOCAL_TEMP_FOLDER);

        String jsonPath = ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName  + ".json";
        importedRecallFileNameForZip = importedRecallFileName;

        // Path to the json file to be created
        File jsonFile = new File(ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName  + ".json");

        // Write content to file
        FileWriter writer = new FileWriter(jsonFile);
        writer.write(content);
        writer.close();

        // Create json and json.out.txt files according to the recallSpecification_v0_9_0.schema.json by running
        // the main method in class de.audi.oru.recall.AppExtended.
        String[] args = {
                "-i",
                jsonPath,
                "-s",
                "data/importReCall/recallSpecification_v0_9_0.schema.json"};

        try {
            ae.process(args);
        } catch (Throwable e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        // Json and json.out.txt files are created by the process method.
        String outTxtFileName = ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName + ".json.out.txt";
        String fileName = ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName;
        ReplaceStringInJsonOutTxtFile.modifyOutTextFile(outTxtFileName, targetLine,subText, faultyCodeIndex, faultyCodeLength, faultyCode);
//         Change json.out.txt file name
        File outTxtFile = new File(outTxtFileName);
        File newFile = new File(fileName);

        if (outTxtFile.exists()) {
            if (outTxtFile.renameTo(newFile)) {
                System.out.println("The recall file : " + importedRecallFileName + " to be imported has been created successfully.");

//                 Print the content of the json.out.txt file and modify json.out.txt file
//                try {
//                    String contentTxt = new String(Files.readAllBytes(Paths.get(newFile.getPath())));
//                    System.out.println("Content of the file: \n" + contentTxt);
//                } catch (IOException e) {
//                    System.err.println("Error reading the file: " + e.getMessage());
//                }

            } else {
                System.err.println("Could not change file name.");
            }
        } else {
            System.err.println("File does not exist.");
        }

        // The file was zipped and the .zip extension was deleted.
        zipFile(fileName);
        System.out.println("The recall file : " + importedRecallFileName + " to be imported was created, zipped and the .zip extension was deleted.");

        // Delete json file
        Thread.sleep(2000);
        assertTrue(jsonFile.exists());

        CreatingImportedRecallFile.deleteImportedRecallTemplate(ValuesGlobal.LOCAL_TEMP_FOLDER + importedRecallFileName + ".json");
  }
}
