/**
 *
 */
package de.sulzer.pages.splunk;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;
import testFramework.utilities.ReusableMethods;


/**
 * @author Sulzer GmbH
 *
 */
public class SplunkHomepage extends Page {

	protected WebDriver _webDriver;

	public SplunkHomepage(WebDriver driver) {
		super(driver);
		_webDriver = driver;
	}

	@FindBy(xpath = "//h1[@data-testid='user-information']")
	private WebElement userNameHeader;

	@FindBy(xpath = "//button[@action='UI-SH-0200-ONLINEUPDATE-2_3-ECE-SUPPORT']")
	private WebElement UI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_TAB;

	@FindBy(xpath = "//div[@data-role='app-name']//span[contains(.,'UI-SH-0200-ONLINEUPDATE-2_3-ECE-SUPPORT')]")
	private WebElement UI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_APP_HEADER;

	@FindBy(xpath = "//textarea[@class='ace_text-input']")
	private WebElement searchInput;

	@FindBy(xpath = "//td[@class='search-button']//i[@class='icon-search-thin']")
	private WebElement searchIcon;

	@FindBy(xpath = "//div[@class='status shared-jobstatus-count']//i/..")
	private WebElement eventsWithDateTime;

	@FindAll({
		@FindBy(xpath = "//tbody/tr[@class='shared-eventsviewer-list-body-row']/td[@class='event']//div[@class='raw-event normal  wrap ']")
	})
	private List<WebElement> eventList;



	//@return the userNameHeader
	public WebElement getUserNameHeader() {
		return this.userNameHeader;
	}

	public String getUserNameHeaderTitle() {
		return this.getUserNameHeader().getText();
	}

	//@return the UI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_TAB
	public WebElement getUI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_TAB() {
		return this.UI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_TAB;
	}

	public void click_UI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_TAB() {
		this.getUI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_TAB().click();
	}

	//@return the UI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_APP_HEADER
	public WebElement getUI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_APP_HEADER() {
		return this.UI_SH_0200_ONLINEUPDATE_2_3_ECE_SUPPORT_APP_HEADER;
	}

	//@return the searchInput
	public WebElement getSearchInput() {
		return this.searchInput;
	}

	public void setSearchbar(String text) {
		this.getSearchInput().sendKeys(text);
	}

	//@click method searchIcon
	public WebElement getSearchIcon() {
		return this.searchIcon;
	}

	public void clickSearchIcon() {
		this.getSearchInput().click();;
	}

	//@click method searchIcon
	public WebElement getEventsWithDateTime() {
		return this.eventsWithDateTime;
	}

	public String getEventsWithDateTimeText() {
		return this.getEventsWithDateTime().getText();
	}

	public List<WebElement> getEventsList() {
		return this.eventList;
	}

	public String getVehicleJobID() throws InterruptedException {
		List<WebElement> eventsList = getEventsList();
		Pattern pattern = Pattern.compile("<ns1:vehicleJobId>(\\d+)</ns1:vehicleJobId>");
		String vehicleJobId = null;
		for (WebElement event : eventsList) {
			Matcher matcher = pattern.matcher(event.getText());
			if (matcher.find()) {
				vehicleJobId = matcher.group(1);
				System.out.println("Vehicle Job ID :" +vehicleJobId);
				break; // stop once the first match is found
			}
		}
		return vehicleJobId;
	}
	
	public void getDetailedLog(String expectedLog) throws InterruptedException {
	    List<WebElement> eventsList = getEventsList();
	    String actualLog = null;
	 
	    for (WebElement event : eventsList) {
	        String logText = event.getText();
	 
	        if (logText.contains(expectedLog)) {
	            // Trim the actual log to only the matching part
	            int startIndex = logText.indexOf(expectedLog);
	            actualLog = logText.substring(startIndex, startIndex + expectedLog.length()).trim();
	 
	            System.out.println("✅ Actual log contains expected text.\nTrimmed Actual Log: " + actualLog);
	            break;
	        }
	    }
	 
	    if (actualLog == null) {
	        System.out.println("❌ Expected log not found in Splunk logs: " + expectedLog);
	        Assert.fail("Expected log not found in Splunk logs: " + expectedLog);
	    } else {
	       
	       Assert.assertTrue(actualLog.contains(expectedLog));
	    }
	}
}

