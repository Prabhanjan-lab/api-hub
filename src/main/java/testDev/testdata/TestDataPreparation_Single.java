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
public class TestDataPreparation_Single extends AbstractStandardBehaviour {

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
        if (System.getProperty("brand") == "VW") {
            try {
                _adminPortletHomepage.changeBrand("VW PKW");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Brand changed");
        }
        String device = "";
        if (System.getProperty("device") == "MIB2") {
            device = ValuesGlobal.MIB2;
        }
        if (System.getProperty("device") == "CGW") {
            device = ValuesGlobal.cGW2;
        }
        if (System.getProperty("device") == "CGW3") {
            device = ValuesGlobal.cGW3;
        }
        if (System.getProperty("device") == "OCU2") {
            device = ValuesGlobal.OCU2;
        }
        if (System.getProperty("device") == "OCU3") {
            device = ValuesGlobal.OCU3;
        }

        _adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());
        _adminPortletHomepage.clickTestingMenu();
        _adminPortletHomepage.clickManualService42System();
        _manualService42SystemPage = new ManualService42SystemPage(this.getWebDriver());
        try {
            _manualService42SystemPage.clickCreateService42Button();
            _addService42SystemModal = new AddService42SystemModal(this.getWebDriver());
            _addService42SystemModal.createService42(System.getProperty("amcode"), DESCRIPTION, System.getProperty("amid"), System.getProperty("tnr"), device, COMMENT);
            _manualService42SystemPage.waitForUploadCompleted(System.getProperty("amcode"), "small");
            System.out.println(System.getProperty("amcode") + " created");
        } catch (Exception e) {
            System.out.println(System.getProperty("amcode") + " already existing");
        }
    }

    @Override
    public void tearDownHook() {

    }

}
