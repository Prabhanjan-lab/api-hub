/**
 *
 */
package de.sulzer.pages.caradministration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Bege
 *
 */
public class VehicleOverviewSpecificVinAttributes extends Page {

    private static final String PREFIX_XPATH = "//vehicle-attributes//p-table//tbody/tr[";
    private static final String SUFFIX_XPATH = "]/td[2]";

    @FindBy(xpath = PREFIX_XPATH + "1" + SUFFIX_XPATH)
    private WebElement vin;

    @FindBy(xpath = PREFIX_XPATH + "2" + SUFFIX_XPATH)
    private WebElement carPortTimestamp;

    @FindBy(xpath = PREFIX_XPATH + "3" + SUFFIX_XPATH)
    private WebElement brand;

    @FindBy(xpath = PREFIX_XPATH + "4" + SUFFIX_XPATH)
    protected WebElement mbv;

    @FindBy(xpath = PREFIX_XPATH + "5" + SUFFIX_XPATH)
    private WebElement mbt;

    @FindBy(xpath = PREFIX_XPATH + "6" + SUFFIX_XPATH)
    private WebElement modelYear;

    @FindBy(xpath = PREFIX_XPATH + "7" + SUFFIX_XPATH)
    private WebElement modelName;

    @FindBy(xpath = PREFIX_XPATH + "8" + SUFFIX_XPATH)
    private WebElement pkn;

    @FindBy(xpath = PREFIX_XPATH + "10" + SUFFIX_XPATH)
    private WebElement attendingDealershipCountryCode;

    @FindBy(xpath = PREFIX_XPATH + "11" + SUFFIX_XPATH)
    private WebElement attendingDealershipPartnernumber;

    @FindBy(xpath = PREFIX_XPATH + "12" + SUFFIX_XPATH)
    private WebElement attendingDealershipWholesaler;

    @FindBy(xpath = PREFIX_XPATH + "14" + SUFFIX_XPATH)
    private WebElement deliveringDealershipCountryCode;

    @FindBy(xpath = PREFIX_XPATH + "15" + SUFFIX_XPATH)
    private WebElement deliveringDealershipPartnernumber;

    @FindBy(xpath = PREFIX_XPATH + "16" + SUFFIX_XPATH)
    private WebElement deliveringDealershipWholesaler;

    @FindBy(xpath = PREFIX_XPATH + "18" + SUFFIX_XPATH)
    private WebElement orderingDealershipCountryCode;

    @FindBy(xpath = PREFIX_XPATH + "19" + SUFFIX_XPATH)
    private WebElement orderingDealershipPartnernumber;

    @FindBy(xpath = PREFIX_XPATH + "20" + SUFFIX_XPATH)
    private WebElement orderingDealershipWholesaler;

    @FindBy(xpath = PREFIX_XPATH + "22" + SUFFIX_XPATH)
    private WebElement effectiveDealershipCountryCode;

    @FindBy(xpath = PREFIX_XPATH + "23" + SUFFIX_XPATH)
    private WebElement effectiveDealershipPartnernumber;

    @FindBy(xpath = PREFIX_XPATH + "24" + SUFFIX_XPATH)
    private WebElement effectiveDealershipWholesaler;

    @FindBy(xpath = PREFIX_XPATH + "25" + SUFFIX_XPATH)
    private WebElement prNumber;

    @FindBy(xpath = PREFIX_XPATH + "26" + SUFFIX_XPATH)
    private WebElement vehicleDatasource;

    /**
     * @param driver
     */
    public VehicleOverviewSpecificVinAttributes(WebDriver driver) {
        super(driver);
    }

    public WebElement getVin() {
        return vin;
    }

    public String getVinText() {
        return vin.getText().trim();
    }

    public String getCarPortTimestampText() {
        return carPortTimestamp.getText().trim();
    }

    public String getBrandText() {
        return brand.getText().trim();
    }

    public WebElement getMbv() {
        return mbv;
    }

    public String getMbvText() {
        return mbv.getText().trim();
    }

    public String getMbtText() {
        return mbt.getText().trim();
    }

    public String getModelYearText() {
        return modelYear.getText().trim();
    }

    public String getModelNameText() {
        return modelName.getText().trim();
    }

    public String getPknText() {
        return pkn.getText().trim();
    }

    public String getAttendingDealershipCountryCodeText() {
        return attendingDealershipCountryCode.getText().trim();
    }

    public String getAttendingDealershipPartnernumberText() {
        return attendingDealershipPartnernumber.getText().trim();
    }

    public String getAttendingDealershipWholesalerText() {
        return attendingDealershipWholesaler.getText().trim();
    }

    public String getDeliveringDealershipCountryCodeText() {
        return deliveringDealershipCountryCode.getText().trim();
    }

    public String getDeliveringDealershipPartnernumberText() {
        return deliveringDealershipPartnernumber.getText().trim();
    }

    public String getDeliveringDealershipWholesalerText() {
        return deliveringDealershipWholesaler.getText().trim();
    }

    public String getOrderingDealershipCountryCodeText() {
        return orderingDealershipCountryCode.getText().trim();
    }

    public String getOrderingDealershipPartnernumberText() {
        return orderingDealershipPartnernumber.getText().trim();
    }

    public String getOrderingDealershipWholesalerText() {
        return orderingDealershipWholesaler.getText().trim();
    }

    public String getEffectiveDealershipCountryCodeText() {
        return effectiveDealershipCountryCode.getText().trim();
    }

    public String getEffectiveDealershipPartnernumberText() {
        return effectiveDealershipPartnernumber.getText().trim();
    }

    public String getEffectiveDealershipWholesalerText() {
        return effectiveDealershipWholesaler.getText().trim();
    }

    public String getPrNumberText() {
        return prNumber.getText().trim();
    }

    public String getVehicleDatasourceText() {
        return vehicleDatasource.getText().trim();
    }

    public WebElement getVehicleDatasource() {
        return vehicleDatasource;
    }

}
