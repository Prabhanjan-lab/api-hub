package de.sulzer.pages.vehicleadministration.template;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import de.sulzer.pages.genericelements.Page;
import de.sulzer.pages.testingmenu.service42.ManualService42SystemPage;
import de.sulzer.pages.util.constants.ConstantsGuiTexts;
import testFramework.utilities.ReusableMethods;

public class TemplateOverviewPage extends Page {

    WebDriver webDriver;

    public TemplateOverviewPage(WebDriver driver){
        super(driver);
        webDriver = driver;
    }


    // panel title text
//    @FindBy(xpath="//h3[@class='panel-title']")
    @FindBy(xpath = "//h3[.=' Template-Management']")
    private WebElement panelTitle;

    // Add template plus icon
    @FindBy(id="add-record-dialog")
    private WebElement addTemplateButtonIcon;

    // template search
    @FindBy(xpath = "//input[@placeholder='search']")
    private WebElement searchTemplateInputfield;

    @FindBy(xpath = "//button[@class='btn btn-default btn-xs']")
    private WebElement editTemplate;

    // Template delete
    @FindBy(xpath="//button[@title='Delete Template']")
    private WebElement deleteTemplate;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement confirmDeletion;

    @FindBy(xpath = "//div/number-of-entries")
    private WebElement numberOfEntriesParagraph;

//    public TemplateOverviewPage(WebDriver driver) {
//        super(driver);
//    }

    public void saveClick(WebElement element){
        element.isDisplayed();
        element.isEnabled();
        try{
            Thread.sleep(2000);
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        element.click();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TemplateOverviewPage.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    public WebElement getAddTemplateButton() {
        return addTemplateButtonIcon;
    }

    public boolean verifyTemplateAddButtonEnabled(){
        return addTemplateButtonIcon.isEnabled();
    }

    public String getNumberOfEntriesParagraphText() {
        return numberOfEntriesParagraph.getText();
    }
    public void clickAddTemplateButton(){
        this.getAddTemplateButton().click();
    }

    public WebElement getPanelTitle() {
        return panelTitle;
    }

    public String getPanelTitleText() {
        return this.getPanelTitle().getText().trim();
    }

    public WebElement getSearchTemplateInputfield() {
        return searchTemplateInputfield;
    }

    public WebElement getNumberOfEntriesParagraph() {
        return numberOfEntriesParagraph;
    }


    public WebElement getDeleteTemplate() {
        return deleteTemplate;
    }

    public boolean verifyTemplateDeleteButtonEnabled() {
        return deleteTemplate.isEnabled();
    }
    public void clickDeleteTemplate(){
        this.getDeleteTemplate().click();
    }

    public WebElement getEditTemplate() {
        return editTemplate;
    }

    public void clickEditTemplate(){
        this.getEditTemplate().click();
    }

    public WebElement getConfirmDeletion() {
        return confirmDeletion;
    }

    public void clickConfirmDeletion(){
        this.saveClick(this.getConfirmDeletion());
    }


    public void searchTemplate(String input) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
        ReusableMethods.waitForClickablility(getSearchTemplateInputfield(), 5, webDriver );

        this.getSearchTemplateInputfield().clear();
        this.getSearchTemplateInputfield().sendKeys(Keys.ENTER);

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }
        ReusableMethods.waitForClickablility(getSearchTemplateInputfield(), 3, webDriver);
        this.getSearchTemplateInputfield().sendKeys(input);
        this.getSearchTemplateInputfield().sendKeys(Keys.ENTER);

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ManualService42SystemPage.class.getName()).log(Level.SEVERE, null, ex);
//        }
        ReusableMethods.waitForVisibility(getNumberOfEntriesParagraph(), 5, webDriver);

        int i3 = 0;
        int f = 15;
        while (i3 == 0 && f>0) {
            String numberOfEntriesText = this.getNumberOfEntriesParagraph().getText();
            System.out.println(numberOfEntriesText);
            if (numberOfEntriesText.contains(ConstantsGuiTexts.ONE_OF_ONE_FROM_RECORDS)) {
                i3 = 1;
            } else {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            f--;
                        if(f==1){
                            throw new java.lang.Error("Timeout after 1 minute!");
                        }
        }
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
