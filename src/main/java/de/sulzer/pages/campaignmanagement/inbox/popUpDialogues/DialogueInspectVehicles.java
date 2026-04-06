/**
 *
 */
package de.sulzer.pages.campaignmanagement.inbox.popUpDialogues;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;

/**
 * @author Sulzer GmbH
 *
 */
public class DialogueInspectVehicles extends Page {

    @FindBy(xpath="//div[@class='modal-dialog']//h4[@class='modal-title']")
    private WebElement headerDialogueInspectVehicles;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']//p-table//tbody//tr")
    )
    private List<WebElement> listRowElementsVINs;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']//p-table//tbody//tr//td[1]")
    )
    private List<WebElement> listColumnElementsVINs;

    @FindAll(
        @FindBy(xpath="//div[@class='modal-body']//p-table//tbody//tr//td[2]")
    )
    private List<WebElement> listColumnElementsVINsState;

    @FindBy(xpath="//button[@class='btn btn-primary']")
    private WebElement buttonClose;

    public DialogueInspectVehicles(WebDriver driver) {
        super(driver);
    }

    /**
     * @return the headerDialogueInspectVehicles
     */
    public WebElement getHeaderDialogueInspectVehicles() {
        return headerDialogueInspectVehicles;
    }

    /**
     * @return the listColumnElementsVINs
     */
    public List<WebElement> getListColumnElementsVINs() {
        return listColumnElementsVINs;
    }

    /**
     * @return the listRowElementsVINs
     */
    public List<WebElement> getListRowElementsVINs() {
        return listRowElementsVINs;
    }

    public List<WebElement> getListColumnElementsVINsState() {
        return listColumnElementsVINsState;
    }

    /**
     * @return the buttonClose
     */
    public WebElement getButtonClose() {
        return buttonClose;
    }

    /**
     *
     */
    public void clickButtonClose() {
        this.getButtonClose().click();
    }

    public boolean isVinAvailable(String vin) {

        boolean found = false;

        List<WebElement> elements = this.getListColumnElementsVINs();

        for (WebElement element : elements) {

            String text = element.getText().trim();

            if (text.contains(vin)) {
                found = true;
                break;
            }

        }

        return found;

    }

}
