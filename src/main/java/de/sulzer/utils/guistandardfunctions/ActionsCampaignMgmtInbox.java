/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.sulzer.pages.campaignmanagement.InboxPage;
import de.sulzer.pages.campaignmanagement.inbox.popUpDialogues.DialogueReCallCampaignDetails;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;

/**
 * @author Sulzer GmbH
 *
 */
public class ActionsCampaignMgmtInbox {

	/**
	 *
	 */
	public ActionsCampaignMgmtInbox() {

	}

	/**
	 * @throws NumberFormatException
	 */
	public void checkReCallDetailsHeaderInformation(
			InboxPage inboxPage,
			DialogueReCallCampaignDetails dialogueReCallCampaignDetails,
			String id,
			int vinCount,
			String brandName,
			WebDriverWait webDriverWait
			) throws NumberFormatException {

		inboxPage.clickRecallTableListFirstInfoIcon();

		this.checkingHeader(dialogueReCallCampaignDetails, id, vinCount, brandName, webDriverWait);

		dialogueReCallCampaignDetails.clickButtonClose();
	}

	/**
	 * @throws NumberFormatException
	 */
	public void checkReCallDetailsHeaderAndFirstCriteriaInformation(
			InboxPage inboxPage,
			DialogueReCallCampaignDetails dialogueReCallCampaignDetails,
			String campaignId,
			int vins,
			String brandName,
			String criteriaId,
			String criteriaTitle,
			String criteriaVinCount,
			WebDriverWait webDriverWait
			) throws NumberFormatException {

		inboxPage.clickRecallTableListFirstInfoIcon();

		this.checkingHeader(dialogueReCallCampaignDetails, campaignId, vins, brandName, webDriverWait);

    	List<WebElement> criterias = dialogueReCallCampaignDetails.getTableCriterionInfoCriterias();

    	assertTrue(criterias.size() == 1, "Expected one entry in table, but found '" + criterias.size() + "'.");

    	assertFalse(criterias.get(0).getText().trim().contains(ConstantsGuiTexts.NO_RECORDS_FOUND));

    	WebElement criteria = criterias.get(0);

    	String id = criteria.findElement(By.xpath(".//td[1]")).getText().trim();
    	String title = criteria.findElement(By.xpath(".//td[2]")).getText().trim();
    	String vinCount = criteria.findElement(By.xpath(".//td[5]")).getText().trim();

    	assertTrue(criteriaId.equals(id), "Criteria ID was not '01', but '" + id + "'.");
    	assertTrue(criteriaTitle.equals(title), "Criteria title was not '01', but '" + title + "'.");
    	assertTrue(criteriaVinCount.equals(vinCount), "Expected to have '" + criteriaVinCount + "' VIN in criteria, but found '" + vinCount + "'.");

		dialogueReCallCampaignDetails.clickButtonClose();
	}

	/**
	 * @param dialogueReCallCampaignDetails
	 * @param id
	 * @param vinCount
	 * @param brandName
	 * @param webDriverWait
	 * @throws NumberFormatException
	 */
	private void checkingHeader(
			DialogueReCallCampaignDetails dialogueReCallCampaignDetails,
			String id,
			int vinCount,
			String brandName,
			WebDriverWait webDriverWait) throws NumberFormatException {

		/*
		 * Added waiting time, in order that data is loaded into GUI.
		 * Loading takes about a few seconds (manually tests 2020-10-08).
		 */
		webDriverWait.until(
				ExpectedConditions.attributeToBeNotEmpty(
						dialogueReCallCampaignDetails.getCampaignNumber(),
						"ng-reflect-model"));

		String campaignId = dialogueReCallCampaignDetails.getTextCampaignNumber();
		String campaignTitle = dialogueReCallCampaignDetails.getTextCampaignTitle();
		String amountVins = dialogueReCallCampaignDetails.getTextFieldTotalVins();
		String brand = dialogueReCallCampaignDetails.getTextBrand();

		assertTrue(campaignId.equals(id), "Found campaign ID '" + campaignId + "', but expected '" + id + "'.");
		assertTrue(campaignTitle.contains(id), "Found campaign title '" + campaignTitle + "', but text has to contain '" + id + "'.");
		assertTrue(vinCount == Integer.parseInt(amountVins), "Found '" + amountVins + "', but expected exactly '" + vinCount + "'.");
		assertTrue(brand.contains(brandName), "Expected brand '" + brandName + "', but it wasn't found (was '" + brand + "').");
	}

}
