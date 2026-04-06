/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.sulzer.pages.checkpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import de.sulzer.pages.genericelements.Page;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author GP4KM3W
 */
public class CheckdetailsGLCS extends Page {

	private Object DateTimeFormat;

	@FindBy(xpath = "(//*[contains(text(),'Operation Mode')]/..//td)[2]")
	private WebElement operationMode;

	public CheckdetailsGLCS(WebDriver driver) {
		super(driver);
	}

	public String getOperationMode() {
		return operationMode.getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Anwendungsnummer')]//following-sibling::td")
	private WebElement anwendungsnummer;

	public WebElement getAnwendungsnummer() {
		return this.anwendungsnummer;
	}

	public String anwendungsnummer() {
		return this.getAnwendungsnummer().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Anwendungsbezeichnung')]//following-sibling::td")
	private WebElement anwendungsbezeichnung;

	public WebElement getAnwendungsbezeichnung() {
		return this.anwendungsbezeichnung;
	}

	public String anwendungsbezeichnung() {
		return this.getAnwendungsbezeichnung().getText();
	}

	@FindBy(xpath = "(//*[contains(text(),'Version')]//following-sibling::td)[1]")
	private WebElement version;

	public WebElement getVersion() {
		return this.version;
	}

	public String version() {
		return this.getVersion().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'API Level')]//following-sibling::td")
	private WebElement apiLevel;

	public WebElement getAPILevel() {
		return this.apiLevel;
	}

	public String apiLevel() {
		return this.getAPILevel().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Build-Date')]//following-sibling::td")
	private WebElement buildDate;

	public WebElement getBuildDate() {
		return this.buildDate;
	}

	public String buildDate() {
		return this.getBuildDate().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Host')]//following-sibling::td")
	private WebElement host;

	public WebElement getHost() {
		return this.host;
	}

	public String host() {
		return this.getHost().getText();
	}

	@FindBy(xpath = "(//*[contains(text(),'Timestamp')]//following-sibling::td)[1]")
	private WebElement timestamp;

	public WebElement getTimestamp() {
		return this.timestamp;
	}

	public String timestamp() {
		return this.getTimestamp().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Manually Fetch Repair Advice')]")
	private WebElement manuallyFetchRepairAdvice;

	public WebElement getManuallyFetchRepairAdvice() {
		return this.manuallyFetchRepairAdvice;
	}

	public void clickManuallyFetchRepairAdviceTab() {
		this.getManuallyFetchRepairAdvice().click();
	}

	@FindBy(name = "campaignId")
	private WebElement campaignID;

	public WebElement getCampaignID() {
		return this.campaignID;
	}

	public void setCampaignID(String campaignID) {
		this.getCampaignID().sendKeys(campaignID);
	}

	@FindBy(xpath = "(//input[@name='version'])[2]")
	private WebElement campaignVersion;

	public WebElement getCampaignVersion() {
		return this.campaignVersion;
	}

	public void setCampaignVersion(String campaignversion) {
		this.getCampaignVersion().sendKeys(campaignversion);
	}

	@FindBy(name = "vcHash")
	private WebElement vcHash;

	public WebElement getvcHash() {
		return this.vcHash;
	}

	public void setvcHash(String getvcHash) {
		this.getvcHash().sendKeys(getvcHash);
	}

	@FindBy(name = "language")
	private WebElement language;

	public WebElement getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.getLanguage().sendKeys(language);
	}

	@FindBy(xpath = "(//input[@name='timestamp'])[2]")
	private WebElement vehiclesTimestamp;

	public WebElement getVehiclesTimestamp() {
		return this.vehiclesTimestamp;
	}

	public void setvehiclesTimestamp(String timestamp) {
		this.getVehiclesTimestamp().sendKeys(timestamp);
	}

	@FindBy(name = "accept")
	private WebElement accept;

	public WebElement getAccept() {
		return this.accept;
	}

	public void setAccept(String accept) {
		this.getAccept().sendKeys(accept);
	}

	@FindBy(name = "vin")
	private WebElement vin;

	public WebElement getVin() {
		return this.vin;
	}

	public void setVin(String vin) {
		this.getVin().sendKeys(vin);
	}

	@FindBy(xpath = "(//input[@name='brand'])[1]")
	private WebElement brand;

	public WebElement getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.getBrand().sendKeys(brand);
	}

	@FindBy(xpath = "(//input[@name='country'])[1]")
	private WebElement country;

	public WebElement getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.getCountry().sendKeys(country);
	}

	@FindBy(xpath = "//input[@value='Fetch Repair Advice']")
	private WebElement fetchRepairAdviceButton;

	public WebElement getFetchRepairAdviceButton() {
		return this.fetchRepairAdviceButton;
	}

	public void clickFetchRepairAdviceButton() {
		this.getFetchRepairAdviceButton().submit();
	}

	@FindBy(xpath = "(//form[@method='post']/div/div)[1]")
	private WebElement usedInputParametersLabel;

	public WebElement getPleaseProvideLabel() {
		return pleaseProvideLabel;
	}

	public String pleaseProvideText() {
		return pleaseProvideLabel.getText();
	}

	@FindBy(xpath = "(//*[.=\"Please provide the following information - Advice\"])[1]")
	private WebElement pleaseProvideLabel;

	public WebElement getUsedInputParametersLabel() {
		return this.usedInputParametersLabel;
	}

	public String usedInputParametersLabel() {
		return this.getUsedInputParametersLabel().getText();
	}

	@FindBy(xpath = "(//form[@method='post']/div/div)[2]")
	private WebElement backEndResponseLable;

	public List<WebElement> getInputResponse() {
		return inputResponse;
	}

	@FindAll({
			@FindBy(xpath = "(//*[@class='noBorder'])[5]//tbody//tr//td[2]")
	})
	private List<WebElement> inputResponse;

	public WebElement getBackEndResponseLable() {
		return this.backEndResponseLable;
	}

	public String backEndResponseLable() {
		return this.getBackEndResponseLable().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Campaign')]//following::td)[1]")
	private WebElement campaign_Response;

	public WebElement getCampaign_Response() {
		return this.campaign_Response;
	}

	public String campaign_Response() {
		return this.getCampaign_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Version')]//following::td)[1]")
	private WebElement version_Response;

	public WebElement getVersion_Response() {
		return this.version_Response;
	}

	public String version_Response() {
		return this.getVersion_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'VC Hash')]//following::td)[1]")
	private WebElement vcHash_Response;

	public WebElement getVCHash_Response() {
		return this.vcHash_Response;
	}

	public String vcHash_Response() {
		return this.getVCHash_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Language')]//following::td)[1]")
	private WebElement language_Response;

	public WebElement getLanguage_Response() {
		return this.language_Response;
	}

	public String language_Response() {
		return this.getLanguage_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Timestamp')]//following::td)[1]")
	private WebElement timeStamp_Response;

	public WebElement getTimeStamp_Response() {
		return this.timeStamp_Response;
	}

	public String timeStamp_Response() {
		return this.getTimeStamp_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Accept')]//following::td)[1]")
	private WebElement accept_Response;

	public WebElement getAccept_Response() {
		return this.accept_Response;
	}

	public String accept_Response() {
		return this.getAccept_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Vin')]//following::td)[1]")
	private WebElement vin_Response;

	public WebElement getVin_Response() {
		return this.vin_Response;
	}

	public String vin_Response() {
		return this.getVin_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Brand')]//following::td)[1]")
	private WebElement brand_Response;

	public WebElement getBrand_Response() {
		return this.brand_Response;
	}

	public String brand_Response() {
		return this.getBrand_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Country')]//following::td)[1]")
	private WebElement country_Response;

	public WebElement getCountry_Response() {
		return this.country_Response;
	}

	public String country_Response() {
		return this.getCountry_Response().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Http Code')]//following::td)[1]")
	private WebElement httpCode;

	public WebElement getHttpCode() {
		return this.httpCode;
	}

	public String httpCode() {
		return this.getHttpCode().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Content-Type')]//following::td)[1]")
	private WebElement contentType;

	public WebElement getContentType() {
		return this.contentType;
	}

	public String contentType() {
		return this.getContentType().getText();
	}

	@FindBy(xpath = "(//b[contains(text(),'Content-Type')]//following::textarea)[1]")
	private WebElement textBoxForReturnedXML;

	public WebElement getextBoxForReturnedXML() {
		return this.textBoxForReturnedXML;
	}

	public String textBoxForReturnedXML() {
		return this.getextBoxForReturnedXML().getText();
	}

	@FindBy(xpath = "//div[@id='manuallyFetchRepairAdvice']/h4")
	private WebElement informationLabel;

	public WebElement getInformationLabel() {
		return this.informationLabel;
	}

	public String informationLabel() {
		return this.getInformationLabel().getText();
	}

	@FindBy(linkText = "Manually report service42 AM and ORU campaign's temporary data usage")
	private WebElement ManuallyReportService42AMandORUCampaignTemporaryDataUsage;

	public WebElement getManualReportService42AMandORUCampaignTempDataUsage() {
		return this.ManuallyReportService42AMandORUCampaignTemporaryDataUsage;
	}

	public void clickManReportS42andORUTempDataUsage() {
		this.getManualReportService42AMandORUCampaignTempDataUsage().click();
	}

	@FindBy(xpath = "(//*[@value='Calculate size'])[2]")
	private WebElement calculateSize;

	public WebElement getcalculateSize() {
		return this.calculateSize;
	}

	public void clickCalculateSize() {
		this.getcalculateSize().click();
	}

	@FindBy(xpath = "(//*[@value='Calculate size'])[2]//following::h4")
	private WebElement responseHeaderOfS42AndORUTempdata;

	public WebElement getResponseHeaderOfS42AndORUTempdata() {
		return this.responseHeaderOfS42AndORUTempdata;
	}

	public String TextOfResponseHeaderOfS42AndORUTempdata() {
		return this.getResponseHeaderOfS42AndORUTempdata().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Backend response')]//following::h5[1]")
	private WebElement s42ChangeProceduresHeader;

	public WebElement getS42ChangeProceduresHeader() {
		return this.s42ChangeProceduresHeader;
	}

	public String textOfS42ChangeProceduresHeader() {
		return this.getS42ChangeProceduresHeader().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Backend response')]//following::h5[2]")
	private WebElement temporaryDistributionData_size;

	public WebElement getTemporaryDistributionData_Size() {
		return this.temporaryDistributionData_size;
	}

	public String textOfTemporaryDistributionData_Size() {
		return this.getTemporaryDistributionData_Size().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Backend response')]//following::h5[3]")
	private WebElement temporaryDistributionData_Folder;

	public WebElement getTemporaryDistributionData_Folder() {
		return this.temporaryDistributionData_Folder;
	}

	public String textOfTemporaryDistributionData_Folder() {
		return this.getTemporaryDistributionData_Folder().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Used input parameters')]")
	private WebElement used_Input_Parameter_Text;

	public WebElement getUsed_Input_Parameter_Text() {
		return this.used_Input_Parameter_Text;
	}

	public String used_Input_Parameter_Text() {
		return this.getUsed_Input_Parameter_Text().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Manual service42 Change Procedures')]//following::p[1]")
	private WebElement s42ChangeProceduresHintMessage;

	public WebElement getS42ChangeProceduresHintMessage() {
		return this.s42ChangeProceduresHintMessage;
	}

	public String s42ChangeProceduresHintMessage() {
		return this.getS42ChangeProceduresHintMessage().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Temporary Distribution Data (by Size)')]//following::p[1]")
	private WebElement temporaryDistributionDataSize_HintMessage;

	public WebElement getTemporaryDistributionDataSize_HintMessage() {
		return this.temporaryDistributionDataSize_HintMessage;
	}

	public String temporaryDistributionData_BySize_HintMessage() {
		return this.getTemporaryDistributionDataSize_HintMessage().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Temporary Distribution Data (by Folder)')]//following::p[1]")
	private WebElement temporaryDistributionDataFolder_HintMessage;

	public WebElement getTemporaryDistributionDataFolder_HintMessage() {
		return this.temporaryDistributionDataFolder_HintMessage;
	}

	public String temporaryDistributionData_ByFolder_HintMessage() {
		return this.getTemporaryDistributionDataFolder_HintMessage().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Manual service42 Change Procedures')]//following::textarea[1]")
	private WebElement textboxForManualS42ChangeProcdures;

	public WebElement getTextboxForManualS42ChangeProcdures() {
		return this.textboxForManualS42ChangeProcdures;
	}

	public String responseOfManualS42ChangeProcdures() {
		return this.getTextboxForManualS42ChangeProcdures().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Temporary Distribution Data (by Size)')]//following::textarea[1]")
	private WebElement textboxForTempDistributionData_Size;

	public WebElement getTextboxForTempDistributionData_Size() {
		return this.textboxForTempDistributionData_Size;
	}

	public String responseOfTempDistributionData_Size() {
		return this.getTextboxForTempDistributionData_Size().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Temporary Distribution Data (by Folder)')]//following::textarea[1]")
	private WebElement textboxForTempDistributionData_Folder;

	public WebElement getTextboxForTempDistributionData_Folder() {
		return this.textboxForTempDistributionData_Folder;
	}

	public String responseOfTempDistributionData_Folder() {
		return this.getTextboxForTempDistributionData_Folder().getText();
	}

	@FindBy(xpath = "//h2")
	private WebElement checkDetailsPageHeader;

	public WebElement getCheckDetailsPageHeader() {
		return this.checkDetailsPageHeader;
	}

	public String checkDetailsPageHeaderText() {
		return this.getCheckDetailsPageHeader().getText();
	}

	@FindBy(xpath = "//*[contains(text(),'Fetch latest MassNotification LOAD XML')]")
	private WebElement fetchLatestMassNotificationLOADXML;

	public WebElement getFetchLatestMassNotificationLOADXML() {
		return this.fetchLatestMassNotificationLOADXML;
	}

	public void clickfetchLatestMassNotificationLOADXML() {
		this.getFetchLatestMassNotificationLOADXML().click();
	}

	@FindBy(xpath = "//input[@value='Load LOAD XML']")
	private WebElement loadLOADXML;

	public WebElement getLoadLOADXML() {
		return this.loadLOADXML;
	}

	public void clickLoadLOADXML() {
		this.getLoadLOADXML().submit();
	}

	@FindBy(xpath = "//*[contains(text(),'Used input parameters in LOAD XML')]//following::textarea[1]")
	private WebElement loadXMLTextArea;

	public WebElement getLoadXMLTextArea() {
		return this.loadXMLTextArea;
	}

	public String massNotificationLoadXMLText() {
		return this.getLoadXMLTextArea().getText();
	}

	// ************** Following two Start button paths are written based on
	// assumption ***************//
	@FindBy(xpath = "//input[@value='Start']")
	private WebElement startButton_01;

	public WebElement getstartButton_01() {
		return this.startButton_01;
	}

	@FindBy(xpath = "//button[contains(text(), 'Start')]")
	private WebElement startButton_02;

	public WebElement getstartButton_02() {
		return this.startButton_02;
	}




	public boolean isValidTime(String dateToValTime) {
		boolean valid = true;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//To make strict date format validation
		formatter.setLenient(false);
		Date parsedDate = null;

		try {
			parsedDate = formatter.parse(dateToValTime);
			System.out.println("Timestamp : "+formatter.format(parsedDate));

		} catch (ParseException e) {
			valid = false;
		}
		return valid;
	}



	public boolean isValidDate(String dateToValDate) {
		boolean valid = true;

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		//To make strict date format validation
		formatter.setLenient(false);
		Date parsedDate = null;

		try {
			parsedDate = formatter.parse(dateToValDate);
			System.out.println("Build-Date : "+formatter.format(parsedDate));

		} catch (ParseException e) {
			valid = false;
		}
		return valid;
	}

}
