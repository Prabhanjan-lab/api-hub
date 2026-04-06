/**
 *
 */
package de.sulzer.utils.guistandardfunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import testFramework.AbstractStandardBehaviour;

/**
 * @author Sulzer GmbH
 *
 */
public class ChecksReCallCreation {

    private final WebDriver webDriver;
    private final WebDriverWait webDriverWait;


    public ChecksReCallCreation(WebDriver webDriver, WebDriverWait webDriverWait) {

        this.webDriver = webDriver;
        this.webDriverWait = webDriverWait;
    }

    public void checkVisibilityOfNewInputFields(String x) {

        this.waitingForElements(Integer.parseInt(x));

    }

    public void checkVisibilityOfNewInputFields(int panelIndex) {

        this.waitingForElements(panelIndex);

    }

    private void waitingForElements(int panelIndex) {

        this.webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p-tabpanel[" + panelIndex + "]//input[@id='inpt-add-crit-id' or @id = 'inpt-edit-crit-id']")));
        this.webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p-tabpanel[" + panelIndex + "]//input[@id='inpt-add-crit-title' or @id = 'inpt-edit-crit-title']")));
        this.webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p-tabpanel[" + panelIndex + "]//textarea[@id='inpt-add-crit-vehs' or @id = 'inpt-edit-crit-vehs']")));

    }

    public void setCriterionData_OUQA262(String noCriteria, String id, String title, String VIN, AbstractStandardBehaviour abstractStandardBehaviour) {

        WebElement weCriterionControlId = this.webDriver.findElement(By.xpath("//p-tabpanel[" + noCriteria + "]//input[@id='inpt-add-crit-id' or @id = 'inpt-edit-crit-id']"));

        weCriterionControlId.clear();
        weCriterionControlId.sendKeys(id);

        abstractStandardBehaviour.logStepPassed(""); //
        //Step finished

        WebElement weCriterionControlTitle = this.webDriver.findElement(By.xpath("//p-tabpanel[" + noCriteria + "]//input[@id='inpt-add-crit-title' or @id = 'inpt-edit-crit-title']"));

        weCriterionControlTitle.clear();
        weCriterionControlTitle.sendKeys(title);

        abstractStandardBehaviour.logStepPassed(""); //
        //Step finished

        WebElement weCriterionControlVins = this.webDriver.findElement(By.xpath("//p-tabpanel[" + noCriteria + "]//textarea[@id='inpt-add-crit-vehs' or @id = 'inpt-edit-crit-vehs']"));

        weCriterionControlVins.clear();
        weCriterionControlVins.sendKeys(VIN);
    }

    public void setCriterionData_OUQA261(String noCriteria, String id, String title, String VIN, AbstractStandardBehaviour abstractStandardBehaviour) {

        this.setCriterionData_OUQA262(noCriteria, id, title, VIN, abstractStandardBehaviour);

        abstractStandardBehaviour.logStepPassed("");

    }

}
