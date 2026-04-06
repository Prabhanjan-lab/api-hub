package testDev.templates.tbe.prototypes;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.caradministration.VehicleOverviewPage;
import de.sulzer.pages.caradministration.VehicleOverviewPageSpecificVIN;
import de.sulzer.utils.guistandardfunctions.ActionsOnupVehicleOverview;
import de.sulzer.utils.guistandardfunctions.ConvienentActionsInONUP;
import testFramework.AbstractStandardBehaviour;

public class OUQA_9999_VehicleOverview_Init extends AbstractStandardBehaviour {

	private AdminPortletHomepage adminPortletHomepage;

    private VehicleOverviewPage vehicleOverviewPage;
    private VehicleOverviewPageSpecificVIN vehicleOverviewPageSpecificVIN;

	private String VIN = "BAUSLZGEZ18032737";
//	private String VIN = "BAUEE20D518024355";

	private ActionsOnupVehicleOverview actionsOnupVehicleOverview;

    @Override
    @org.junit.jupiter.api.BeforeEach
	protected void setUp() throws Throwable {

		try {
			super.setUp();
		} catch (Throwable e) {
			this.setThrowable(e);
			throw e;
		} finally {
			this.initTestContainer(1); // test has 1 test steps
		}
    }

    @Override
	protected void testHook() throws Throwable {

    	/*
    	 * Test initialisation.
    	 */

		this.initialiseWebPages();

		this.actionsOnupVehicleOverview = new ActionsOnupVehicleOverview(getWebDriver());

    	/*
    	 * Test step 1
    	 */

    	ConvienentActionsInONUP.loginAsUserToPortletHomepage(this.getWebDriver());

    	this.actionsOnupVehicleOverview.initVehicleOverviewQueues(
    			VIN,
    			this.adminPortletHomepage,
    			this.vehicleOverviewPage,
    			this.vehicleOverviewPageSpecificVIN);

    	//
    	//this.logStepPassed(); // 1

    }

    @Override
    public void tearDownHook() {

    }

	/**
	 *
	 */
	private void initialiseWebPages() {

		this.adminPortletHomepage = new AdminPortletHomepage(this.getWebDriver());

		this.vehicleOverviewPage = new VehicleOverviewPage(this.getWebDriver());
		this.vehicleOverviewPageSpecificVIN = new VehicleOverviewPageSpecificVIN(this.getWebDriver());

	}

}