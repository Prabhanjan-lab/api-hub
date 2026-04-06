package testDev.testingtestdata;

import de.sulzer.model.util.ConstantsTestResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.testingmenu.recall.AddRecallSystemModal;
import de.sulzer.pages.testingmenu.recall.ManualRecallSystemPage;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import testFramework.AbstractStandardBehaviour;

public class OUQA_255_CreateRecallCampaignWithOneCriteriaTest extends AbstractStandardBehaviour{
    private static final String VIN = "BAUFP74N816110920"; //VIN für Demo und Approval???
    private static final String RECALL_ID = "TA00";
    private static final String RECALL_TITLE = de.sulzer.utils.CreateRandomChars.generateRandomChars("ABCDEFGHIJKLMNOPQRSUVWXYZ1234567890", 15);
    private static final String RECALL_DISABLED_COUNTRY = "ATA";
    private static final String CRITERION01_ID = "01";
    private static final String CRITERION01_TITLE = "Step 01";
    private static final String COMMENT = "IT-Sulzer: Smoketest";

    private AdminPortletHomepage _adminPortletHomepage;
    private ManualRecallSystemPage _manualRecallSystemPage;
    private AddRecallSystemModal _addRecallSystemModal;

    @Override
    @BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(new Throwable(e));
			throw e;
		} finally {
			// creating test container and configuring test steps (constructor; here 7)
			this.initTestContainer(7);
		}

        ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

    }

    @Override
	protected void testHook() throws Exception {

    	_adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());
    	_adminPortletHomepage.clickTestingMenu();
    	_adminPortletHomepage.clickManualRecallSystem();

    	_manualRecallSystemPage = new ManualRecallSystemPage(this.getWebDriver());
    	assertEquals("Recall - Campaigns", _manualRecallSystemPage.getPanelTitleText());

    	_manualRecallSystemPage.clickAddRecallButton();
    	_addRecallSystemModal = new AddRecallSystemModal(this.getWebDriver());
    	assertEquals("Create New ReCall Campaign", _addRecallSystemModal.getModalTitleText());
    	_addRecallSystemModal.setRecallActionIdInputFieldText(RECALL_ID);
    	this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "");
    	//Step finished
    	_addRecallSystemModal.setRecallActionTitleInputField(RECALL_TITLE);
    	this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "");
    	//Step finished
    	_addRecallSystemModal.setDisabledCountriesInputField(RECALL_DISABLED_COUNTRY);

    	_addRecallSystemModal.setCriterionIdControlInputField(CRITERION01_ID);
    	this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "");
    	//Step finished
    	_addRecallSystemModal.setCriterionTitleInputField(CRITERION01_TITLE);
    	this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "");
    	//Step finished
    	_addRecallSystemModal.setVehiclesInputField(VIN);
    	this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "");
    	//Step finished
    	_addRecallSystemModal.setCommentInputField(COMMENT);
    	this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "");
    	//Step finished
    	_addRecallSystemModal.clickCreateRecallCampaignButton();
    	_manualRecallSystemPage.searchReCallSimple(RECALL_TITLE);
    	this.getTestContainer().addTestStep(ConstantsTestResult.PASS, "");
    	//Step finished

    }

    @Override
    public void tearDownHook() {
    	
        _manualRecallSystemPage.clickDeleteRecallButton();
        _manualRecallSystemPage.clickConfirmDeletion();
        
    }
}
