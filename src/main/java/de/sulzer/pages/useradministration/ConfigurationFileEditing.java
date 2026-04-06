/**
 *
 */
package de.sulzer.pages.useradministration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author GCH9F5D
 *
 */
public class ConfigurationFileEditing extends Page {

//	@FindBy(xpath="//h3[@class='panel-title'][contains(text(), 'Configuration File Editing')]")
	@FindBy(xpath = "//h3[.='Configuration File Editing']")
	private WebElement titlePageConfigurationFileEditing;

	@FindBy(xpath="//h3[contains(text(),'MBBB database import')]")
	private WebElement titleMBBBDatabaseImport;

//	@FindBy(xpath="//div//a[contains(text(), 'Role Mapping')]")
	@FindBy(xpath = "(//div//a[contains(text(), 'Role Mapping')])[1]")
	private WebElement linkRoleMapping;

	@FindBy(xpath="//div//a[contains(text(), 'Spiider Role Mapping')]")
	private WebElement linkSpiiderRoleMapping;

	@FindBy(xpath="//div//a[contains(text(), ' OIDC Role Mapping ')]")
	private WebElement linkOIDCRoleMapping;

	@FindBy(xpath="//div//a[contains(text(), 'Permission Matrix')]")
	private WebElement linkPermissionMatrix;

	@FindBy(xpath="//div//a[contains(text(), 'OU Mapping')]")
	private WebElement linkOUMapping;

	@FindBy(xpath="//div//a[contains(text(), 'Sandbox Configuration')]")
	private WebElement linkSandboxConfiguration;

	@FindBy(xpath="//div[@id='reloadMBBA']//button[@class='btn btn-primary'][contains(text(),'Update')]")
	private WebElement databaseImportMBBAUpdate;

	@FindBy(xpath="//div[@id='msgMBBA']//p[contains(text(), 'Not imported. The data are already up to date.')]")
	private WebElement updateMessageMBBAAlreadyUpToDate;

	@FindBy(xpath="//div[@id='reloadMBBB']//button[@class='btn btn-primary'][contains(text(),'Update')]")
	private WebElement databaseImportMBBBUpdate;

//	@FindBy(xpath="//div[@id='msgMBBB']//p[contains(text(), 'Not imported. The data are already up to date.')]")
	@FindBy(xpath = "//p[.=' Not imported. The data are already up to date. ']")
	private WebElement updateMessageMBBBAlreadyUpToDate;


	public ConfigurationFileEditing(WebDriver driver) {
		super(driver);
	}

	/**
	 * @return the titlePageConfigurationFileEditing
	 */
	public WebElement getTitlePageConfigurationFileEditing() {
		return titlePageConfigurationFileEditing;
	}

	/**
	 * @return the titleMBBBDatabaseImport
	 */
	public WebElement getTitleMBBBDatabaseImport() {
		return titleMBBBDatabaseImport;
	}

	/**
	 * @return the linkRoleMapping
	 */
	public WebElement getLinkRoleMapping() {
		return linkRoleMapping;
	}

	public void clickLinkRoleMapping() {
		this.getLinkRoleMapping().click();
	}

	/**
	 * @return the linkSpiiderRoleMapping
	 */
	public WebElement getLinkSpiiderRoleMapping() {
		return linkSpiiderRoleMapping;
	}

	public void clickLinkSpiiderRoleMapping() {
		this.getLinkSpiiderRoleMapping().click();
	}

	/**
	 * @return the linkOIDCRoleMapping
	 */
	public WebElement getLinkOIDCRoleMapping() {
		return linkOIDCRoleMapping;
	}
	/**
	 * @return the linkPermissionMatrix
	 */
	public WebElement getLinkPermissionMatrix() {
		return this.linkPermissionMatrix;
	}

	public void clickLinkPermissionMatrix() {
		this.getLinkPermissionMatrix().click();
	}

	/**
	 * @return the linkOUMapping
	 */
	public WebElement getLinkOUMapping() {
		return linkOUMapping;
	}

	public void clickLinkOUMapping() {
		this.getLinkOUMapping().click();
	}

	/**
	 * @return the linkSandboxConfiguration
	 */
	public WebElement getLinkSandboxConfiguration() {
		return linkSandboxConfiguration;
	}

	public void clickLinkSandboxConfiguration() {
		this.getLinkSandboxConfiguration().click();
	}

	/**
	 * @return the databaseImportMBBAUpdate
	 */
	public WebElement getDatabaseImportMBBAUpdate() {
		return databaseImportMBBAUpdate;
	}

	public void clickDatabaseImportMBBAUpdate() {
		this.getDatabaseImportMBBAUpdate().click();
	}

	/**
	 * @return the updateMessageMBBAAlreadyUpToDate
	 */
	public WebElement getUpdateMessageMBBAAlreadyUpToDate() {
		return updateMessageMBBAAlreadyUpToDate;
	}

	/**
	 * @return the databaseImportMBBBUpdate
	 */
	public WebElement getDatabaseImportMBBBUpdate() {
		return databaseImportMBBBUpdate;
	}

	public void clickDatabaseImportMBBBUpdate() {
		this.getDatabaseImportMBBBUpdate().click();
	}

	/**
	 * @return the updateMessageMBBBAlreadyUpToDate
	 */
	public WebElement getUpdateMessageMBBBAlreadyUpToDate() {
		return updateMessageMBBBAlreadyUpToDate;
	}

}
