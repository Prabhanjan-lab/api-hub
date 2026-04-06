/**
 *
 */
package testCases.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.sulzer.pages.AdminPortletHomepage;
import de.sulzer.pages.sectorlog.LoggingDetail;
import de.sulzer.pages.sectorlog.LoggingOverview;
import de.sulzer.utils.screenshots.ScreenshotsShutterbug;

/**
 * @author Sulzer GmbH
 *
 */
public class SectorLogChecking {

	private WebDriver webDriver;

	/**
	 *
	 */
	public SectorLogChecking(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	/**
	 * @param elements
	 * @param found
	 * @param entries
	 * @param searchItems
	 * @return
	 * @throws InterruptedException
	 */
	public boolean searchInLoggingOverviewByCriterias(
			List<WebElement> elements,
			String sourceItComponent,
			String xpathTableColumn,
			List<String> searchItems,
			AdminPortletHomepage adminPortletHomepage,
			LoggingOverview loggingOverview,
			LoggingDetail loggingDetail) throws InterruptedException {

		Map<String, Integer> searchResults = new HashMap();

		int entries = elements.size();
		boolean found = false;

		// leave system time to load data into table (usual timing problem)
		Thread.sleep(8000);

		if (this.hasNoData(elements)) {
			return found;
		}

		for (int i = 0; i < entries; i++) {

			WebElement we = elements.get(i).findElement(By.xpath(xpathTableColumn));

			String value = we.getText().trim();

			int count = 0;

			try {
				count = Integer.parseInt(value);
			} catch (NumberFormatException e) {
				count = 0;
			}

			if (count != 0) {

				we.click();

				Thread.sleep(3000);

				searchResults.clear();

				List<WebElement> listElements = loggingDetail.getListLogDetailEntries();

				this.searchForElements(searchItems, searchResults, listElements);

				if (this.areSpecimensFound(searchResults)) {
					found = true;
					break;
				}

				this.webDriver.navigate().back();

				Thread.sleep(3000);
		        loggingOverview.searchLogEntries(sourceItComponent);
				Thread.sleep(3000);

			}

			if (found) {
				break;
			}

		}

		adminPortletHomepage.clickLoggingOverview();

		return found;
	}

	private boolean hasNoData(List<WebElement> elements) {

		if (1 == elements.size()) {

			WebElement element = elements.get(0);

			String text = element.getText().trim();

			if (text.contains(ConstantsSectorLog.NO_RECORDS_FOUND)) {
				return true;
			}

		}

		return false;

	}

	/**
	 * @param e
	 * @return
	 */
	public boolean checkContainsSearchItems(WebElement e, List<String> searchItems) {

		String specimen = e.getText().trim();

		int count = 0;

		for (String searchItem : searchItems) {
			if (specimen.contains(searchItem)) {
				count++;
			}
		}

		return count == searchItems.size();
	}

	/**
	 * @param searchItems
	 * @param searchResults
	 * @param listElements
	 */
	private void searchForElements(
			List<String> searchItems,
			Map<String, Integer> searchResults,
			List<WebElement> listElements) {

		for (WebElement e : listElements) {

			String specimen = e.getText().trim();

			for (String searchItem : searchItems) {

				if (specimen.contains(searchItem)) {

					if (searchResults.containsKey(searchItem)) {
						searchResults.put(searchItem, searchResults.get(searchItem) + 1);
					} else {
						searchResults.put(searchItem, 1);
					}

				}

			}

		}

	}

	private boolean areSpecimensFound(Map<String, Integer> searchResults) {

		for (Integer integer : searchResults.values()) {

			if (integer <= 0) {
				return false;
			}

		}

		return true;
	}

}
