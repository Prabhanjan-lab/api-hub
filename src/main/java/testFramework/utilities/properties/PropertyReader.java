/**
 *
 */
package testFramework.utilities.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Sulzer GmbH
 *
 */
public class PropertyReader {

//    public String readProperty(String propertyFile, String property) throws IOException {
//
//        InputStream input = new FileInputStream(propertyFile);
//
//        Properties prop = new Properties();
//
//        // load a properties file
//        prop.load(input);
//
//        return prop.getProperty(property);
//
//    }

    public String readProperty(String propertyFile, String property) throws IOException {

        Properties prop = new Properties();
        String configFileName = null;
        try {
            configFileName = System.getProperty("local-config.properties", propertyFile);
            InputStream input = new FileInputStream(configFileName);

            // load a properties file
            prop.load(input);

            return prop.getProperty(property);
        } catch (Exception e) {
            propertyFile = "src/main/resources/local_properties/" + propertyFile;
            InputStream input = new FileInputStream(propertyFile);
            // load a properties file
            prop.load(input);
            return prop.getProperty(property);
        }
    }

    public static String loadProperty(String property) {
        String prop = "";

        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("startup.properties")) {
            if (input == null) {
                throw new IOException("startup.properties file not found in resources");
            }

            Properties properties = new Properties();
            properties.load(input);
            prop = properties.getProperty(property);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return prop;
    }

}
