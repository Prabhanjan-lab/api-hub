package testDev.testdata;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.testingmenu.service42.AddService42SystemModal;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import testFramework.constants.ValuesGlobal;
import testFramework.AbstractStandardBehaviour;

/**
 *
 * @author Schulzp
 */
public class TestDataPreparation_Smoketest extends AbstractStandardBehaviour {

    private static final String PATH_TO_FILE_TO_BE_UPLOADED_MIB2 = ValuesGlobal.MIB2;
    private static final String PATH_TO_FILE_TO_BE_UPLOADED_OCU2 = ValuesGlobal.OCU2;
    private static final String PATH_TO_FILE_TO_BE_UPLOADED_OCU3 = ValuesGlobal.OCU3;
    private static final String PATH_TO_FILE_TO_BE_UPLOADED_CGW2 = ValuesGlobal.cGW2;
    private static final String PATH_TO_FILE_TO_BE_UPLOADED_CGW3 = ValuesGlobal.cGW3;

    private static final String AM_CODE_OUQA_1358 = "TA28";
    private static final String AM_CODE_OUQA_1356 = "TA0F";
    private static final String AM_CODE_OUQA_1060 = "TA0K";
    private static final String AM_CODE_OUQA_1359 = "TA0A";
    private static final String AM_CODE_OUQA_1061 = "TA0H";
    private static final String AM_CODE_OUQA_1355 = "TA0J";
    private static final String AM_CODE_OUQA_1366 = "TA0L";
    private static final String AM_CODE_OUQA_1365 = "TA0D";
    private static final String TNR_NUMBER_OUQA_1358 = "SLZTA1001";
    private static final String TNR_NUMBER_OUQA_1356 = "SLZTA0002";
    private static final String TNR_NUMBER_OUQA_1060 = "SLZTA0003";
    private static final String TNR_NUMBER_OUQA_1359 = "SLZTA0004";
    private static final String TNR_NUMBER_OUQA_1061 = "SLZTA0005";
    private static final String TNR_NUMBER_OUQA_1355 = "SLZTA0006";
    private static final String TNR_NUMBER_OUQA_1366 = "SLZTA1007";
    private static final String TNR_NUMBER_OUQA_1365 = "SLZTA0008";
    private static final String AM_ID_OUQA_1358 = "42343445101";
    private static final String AM_ID_OUQA_1356 = "42343445002";
    private static final String AM_ID_OUQA_1060 = "42343445003";
    private static final String AM_ID_OUQA_1359 = "42343445004";
    private static final String AM_ID_OUQA_1061 = "42343445005";
    private static final String AM_ID_OUQA_1355 = "42343445006";
    private static final String AM_ID_OUQA_1366 = "42343445107";
    private static final String AM_ID_OUQA_1365 = "42343445008";
    private static final String DESCRIPTION = de.sulzer.utils.CreateRandomChars.generateRandomChars("ABCDEFGHIJKLMNOPQRSUVWXYZ1234567890", 15);
    private static final String COMMENT = "Tester: IT Sulzer: Smoketest";

    private AdminPortletHomepage _adminPortletHomepage;
    private ManualService42SystemPage _manualService42SystemPage;
    private AddService42SystemModal _addService42SystemModal;

    @Override
    @BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Exception e) {
			this.setThrowable(new Throwable(e.getMessage()));
			throw e;
		} catch (Error e) {
			this.setThrowable(new Throwable(e.getMessage()));
			throw e;
		} catch (Throwable e) {
			this.setThrowable(new Throwable(e.getMessage()));
			throw e;
		} finally {
			this.initTestContainer(1);
		}

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

    }

    @Override
	protected void testHook() throws Exception {
//         start prep for AUDI
        _adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());
        _adminPortletHomepage.clickTestingMenu();
        _adminPortletHomepage.clickManualService42System();
        _manualService42SystemPage = new ManualService42SystemPage(this.getWebDriver());
        try{
        _manualService42SystemPage.clickCreateService42Button();
        _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
        _addService42SystemModal.createService42(AM_CODE_OUQA_1358, DESCRIPTION, AM_ID_OUQA_1358, TNR_NUMBER_OUQA_1358, PATH_TO_FILE_TO_BE_UPLOADED_MIB2, COMMENT);
        _manualService42SystemPage.waitForUploadCompleted(AM_CODE_OUQA_1358, "small");
        System.out.println(AM_CODE_OUQA_1358+" created");
        }
        catch(Exception e){
            System.out.println(AM_CODE_OUQA_1358+" already existing");
            _addService42SystemModal.clickCancelButton();
        }
        try{
        _manualService42SystemPage.clickCreateService42Button();
        _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
        _addService42SystemModal.createService42(AM_CODE_OUQA_1359, DESCRIPTION, AM_ID_OUQA_1359, TNR_NUMBER_OUQA_1359, PATH_TO_FILE_TO_BE_UPLOADED_MIB2, COMMENT);
        _manualService42SystemPage.waitForUploadCompleted(AM_CODE_OUQA_1359, "small");
        System.out.println(AM_CODE_OUQA_1359+" created");
        }
        catch(Exception e){
            System.out.println(AM_CODE_OUQA_1359+" already existing");
            _addService42SystemModal.clickCancelButton();
        }
        try{
        _manualService42SystemPage.clickCreateService42Button();
        _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
        _addService42SystemModal.createService42(AM_CODE_OUQA_1366, DESCRIPTION, AM_ID_OUQA_1366, TNR_NUMBER_OUQA_1366, PATH_TO_FILE_TO_BE_UPLOADED_CGW2, COMMENT);
        _manualService42SystemPage.waitForUploadCompleted(AM_CODE_OUQA_1366, "small");
        System.out.println(AM_CODE_OUQA_1366+" created");
        }
        catch(Exception e){
            System.out.println(AM_CODE_OUQA_1366+" already existing");
            _addService42SystemModal.clickCancelButton();
        }
        try{
        _manualService42SystemPage.clickCreateService42Button();
        _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
        _addService42SystemModal.createService42(AM_CODE_OUQA_1365, DESCRIPTION, AM_ID_OUQA_1365, TNR_NUMBER_OUQA_1365, PATH_TO_FILE_TO_BE_UPLOADED_CGW2, COMMENT);
        _manualService42SystemPage.waitForUploadCompleted(AM_CODE_OUQA_1365, "small");
        System.out.println(AM_CODE_OUQA_1365+" created");
        }
        catch(Exception e){
            System.out.println(AM_CODE_OUQA_1365+" already existing");
            _addService42SystemModal.clickCancelButton();
        }
        try{
        _adminPortletHomepage.changeBrand("VW PKW");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Brand changed");
//        // start prep for VW PKW
        _adminPortletHomepage.clickTestingMenu();
        _adminPortletHomepage.clickManualService42System();
        _manualService42SystemPage = new ManualService42SystemPage(this.getWebDriver());

        try{
        _manualService42SystemPage.clickCreateService42Button();
        _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
        _addService42SystemModal.createService42(AM_CODE_OUQA_1356, DESCRIPTION, AM_ID_OUQA_1356, TNR_NUMBER_OUQA_1356, PATH_TO_FILE_TO_BE_UPLOADED_OCU2, COMMENT);
        _manualService42SystemPage.waitForUploadCompleted(AM_CODE_OUQA_1356, "small");
        System.out.println(AM_CODE_OUQA_1356+" created");
        }
        catch(Exception e){
            System.out.println(AM_CODE_OUQA_1356+" already existing");
            _addService42SystemModal.clickCancelButton();
        }
        try{
        _manualService42SystemPage.clickCreateService42Button();
        _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
        _addService42SystemModal.createService42(AM_CODE_OUQA_1060, DESCRIPTION, AM_ID_OUQA_1060, TNR_NUMBER_OUQA_1060, PATH_TO_FILE_TO_BE_UPLOADED_OCU3, COMMENT);
        _manualService42SystemPage.waitForUploadCompleted(AM_CODE_OUQA_1060, "small");
        System.out.println(AM_CODE_OUQA_1060+" created");
        }
        catch(Exception e){
            System.out.println(AM_CODE_OUQA_1060+" already existing");
            _addService42SystemModal.clickCancelButton();
        }
        try{
        _manualService42SystemPage.clickCreateService42Button();
        _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
        _addService42SystemModal.createService42(AM_CODE_OUQA_1061, DESCRIPTION, AM_ID_OUQA_1061, TNR_NUMBER_OUQA_1061, PATH_TO_FILE_TO_BE_UPLOADED_OCU3, COMMENT);
        _manualService42SystemPage.waitForUploadCompleted(AM_CODE_OUQA_1061, "small");
        System.out.println(AM_CODE_OUQA_1061+" created");
        }
        catch(Exception e){
            System.out.println(AM_CODE_OUQA_1061+" already existing");
            _addService42SystemModal.clickCancelButton();
        }
        try{
        _manualService42SystemPage.clickCreateService42Button();
        _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
        _addService42SystemModal.createService42(AM_CODE_OUQA_1355, DESCRIPTION, AM_ID_OUQA_1355, TNR_NUMBER_OUQA_1355, PATH_TO_FILE_TO_BE_UPLOADED_OCU2, COMMENT);
        _manualService42SystemPage.waitForUploadCompleted(AM_CODE_OUQA_1355, "small");
        System.out.println(AM_CODE_OUQA_1355+" created");
        }
        catch(Exception e){
            System.out.println(AM_CODE_OUQA_1355+" already existing");
            _addService42SystemModal.clickCancelButton();
        }
    }

    @Override
    public void tearDownHook() {

    }

}
