package testFramework.constants;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.WebDriver;
import testFramework.utilities.properties.PropertyReader;
import java.io.IOException;

/**
 *
 * @author Sulzer GmbH
 */

public class ValuesGlobal extends Page {

    WebDriver webDriver;

    public ValuesGlobal(WebDriver driver) {
        super(driver);
        webDriver = driver;
    }

    /**
     FLASH MEDIUM
     */
    private static final String basicPath = "/opt/selenium/"; // Linux path

    // Flash medium files for linux
    public static final String MIB2 = basicPath + "FM_Index_2.zip";
    public static final String MIB3 = basicPath + "FM_Index_1_1_1.zip";
    public static final String MIB2_5GB = basicPath + "FM_MIB_5GB-1.0.zip";
    public static final String cGW2 = basicPath + "FM_cGW3_OCU3-1.0.odx";
    public static final String cGW3 = basicPath + "FM_cGW3-1.0.odx";
    public static final String OCU2 = basicPath + "FM_OCU2-1.0.odx";
    public static final String OCU3 = basicPath + "FM_cGW3_OCU3-1.0.odx";
    public static final String PDX = basicPath + "FL_571035284_X005_PART2B152REL_V001_E.pdx";

    // Flash medium files for windows
    public static final String LOCAL_FM_MIB2 = "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Flashmedium\\MIB2+\\FM_Index_2.zip";
    public static final String LOCAL_FM_MIB3 = "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Flashmedium\\MIB2+\\FM_Index_1_1_1.zip";
    public static final String LOCAL_FM_MIB2_5GB = "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Flashmedium\\MIB2+\\FM_MIB_5GB-1.0.zip";
    public static final String LOCAL_FM_CGW3 = "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Flashmedium\\cGW3\\FL_4K1907468_U317___E.odx";
    public static final String LOCAL_FM_CGW2 = "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Flashmedium\\cGW2\\FL_4N2907468B_U009_51OT1_E.odx";
    public static final String LOCAL_FM_OCU2 = "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Flashmedium\\OCU2\\FL_5NA035284_0823_PRODALL_V400_S.odx";
    public static final String LOCAL_FM_OCU3 = "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Flashmedium\\OCU3\\FL_4N2907468B_U009_51OT1_E.odx";
    public static final String LOCAL_FM_PDX = "C:\\Users\\FNX2JOE.VW\\OneDrive - Volkswagen AG\\Dokumente\\Flashmedium\\PDX\\FL_571035284_X005_PART2B152REL_V001_E.pdx";

    public static String LOCAL_FILE_PATH = "data/importRecall/";
    public static final String LOCAL_TEMP_FOLDER = "data/tempFolder/";

    public static String RECEIVE_FOLDER_PATH = "/nfs/mbbb/dv/rsync/receive/";
    public static String IGNORED_FOLDER_PATH = "/nfs/mbbb_onup/as/demo/recall_import/processed/ignored";
    private static final String IGNORED_ZIP = "_ignored.zip";
    public static String FLASH_MEDIUM;

    /**
     MOCK REST SERVER
     */
    public static String HOST_4363;
    public static String USERNAME;
    public static String SERVER_PSW;
    public static int PORT_4363;
    public static String MOCK_SERVER_KEY_PATH;

    static {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            PropertyReader pr = new PropertyReader();

            FLASH_MEDIUM = getFlashMedium();

            if (os.contains("windows")) {
                HOST_4363 = pr.readProperty("local-config.properties", "HOST_4363");
                USERNAME = pr.readProperty("local-config.properties", "USERNAME");
                SERVER_PSW = pr.readProperty("local-config.properties", "SERVER_PSW");
                PORT_4363 = Integer.parseInt(pr.readProperty("local-config.properties", "PORT_4363"));
                MOCK_SERVER_KEY_PATH = pr.readProperty("local-config.properties", "MOCK_SERVER_KEY_PATH");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFlashMedium() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("linux")) {
            return cGW2;
        } else if (os.contains("windows")) {
            return LOCAL_FM_CGW3;
        } else {
            return "";
        }
    }

    public static String getFlashMedium(WebDriver driver) {
        AdminPortletHomepage adminPortletHomepage = new AdminPortletHomepage(driver);
        String os = System.getProperty("os.name").toLowerCase();

        String flashMedium = null;
        if (os.contains("linux")) {

            // Brand Audi
            if (adminPortletHomepage.getLinkBrand().getText().contains("Audi")) {
                return LOCAL_FM_CGW2;
            }

            // Brand VW_PKW
            if (adminPortletHomepage.getLinkBrand().getText().contains("VW PKW")) {
                flashMedium = cGW3;
            }

        } else if (os.contains("windows")) {
            // Brand Audi
            if (adminPortletHomepage.getLinkBrand().getText().contains("Audi")) {
                flashMedium = LOCAL_FM_CGW3;
            }

            // Brand VW_PKW
            if (adminPortletHomepage.getLinkBrand().getText().contains("VW PKW")) {
                flashMedium = LOCAL_FM_CGW3;
            }
        }
        return flashMedium;
    }
}