package de.sulzer.pages.testingmenu.service42;

import de.sulzer.pages.genericelements.Page;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import testFramework.utilities.ReusableMethods;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AddService42SystemModal extends Page {

    protected WebDriver _driver;

    public AddService42SystemModal(WebDriver driver) {
        super(driver);
        _driver = driver;
    }

    @FindBy(id = "inf-s42-mod-usable-space")
    private WebElement diskSpace;

    @FindBy(id = "inf-s42-add-s42-ic")
    private WebElement createService42Button;

    @FindBy(id = "inpt-s42-mod-am-code")
    private WebElement amCode;

    @FindBy(id = "inpt-s42-mod-am-descr")
    private WebElement description;

    @FindBy(id = "inpt-s42-mod-am-id")
    private WebElement amId;

    @FindBy(id = "inpt-s42-mod-tnr")
    private WebElement tnr;

    @FindBy(id = "inpt-file-upl-file")
    private WebElement inputButton;

    @FindBy(id = "btn-s42-mod-save")
    private WebElement submitSave;

    @FindBy(id="cancel-save")
    private WebElement submitCancel;

    @FindBy(id="file-upload-progress-container-in-angular4")
    private WebElement uploadProgressbar;

    @FindBy(id = "inpt-s42-mod-comment")
    private WebElement commentField;

    @FindBy(id = "btn-s42-mod-cancel")
    private WebElement closeButton;

    @FindBy(xpath = "//div[.='Service42 with this AM-CODE already exists.']")
    private WebElement S42alreadyExistMessage;

    public void createService42(String amCode, String description, String amId, String tnr, String filepath, String comment) {
        this.setAmCodeField(amCode);
        this.setDescriptionField(description);
        this.setAmId(amId);
        this.setTnrField(tnr);
        this.uploadFile(filepath);
        this.setCommentField(comment);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.clickSaveButton();
    }

    public void createService42FileUploadAbort(String amCode,
            String description,
            String amId,
            String tnrNumber,
            String pathToFileToBeUploaded,
            String comment) {

        this.setAmCodeField(amCode);
        this.setDescriptionField(description);
        this.setAmId(amId);
        this.setTnrField(tnrNumber);
        this.uploadFile(pathToFileToBeUploaded);
        this.setCommentField(comment);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(AddService42SystemModal.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.getSaveButton().click();

    }

    public WebElement getCreateService42Button() {
        return createService42Button;
    }

    public WebElement getCloseButton(){
        return closeButton;
    }

    public void clickCloseModal() {
        this.getCloseButton().click();
    }

    public WebElement getCommentField(){
        return commentField;
    }

    public void setCommentField(String input){
        this.getCommentField().clear();
        this.getCommentField().sendKeys(input);
    }

    public WebElement getUploadButton(){
        return inputButton;
    }

    public void uploadFile(String filepath){
        System.out.println(filepath);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.getUploadButton().sendKeys(filepath);
    }

    public WebElement getdiskSpace() {
        return diskSpace;
    }

    public String getDiskSpaceText() {
        return this.getdiskSpace().getText();

    }

    public WebElement getAmCodeField() {
        return amCode;
    }

    public void setAmCodeField(String input) {
        this.getAmCodeField().clear();
        this.getAmCodeField().sendKeys(input);
    }

    public WebElement getSaveButton() {
        return submitSave;
    }
    public WebElement getCancelButton() {
        return submitCancel;
    }

    public WebElement getUploadProgressbar(){
        return uploadProgressbar;
    }

    public WebElement getS42alreadyExistMessage() {
        return S42alreadyExistMessage;
    }

    public void clickSaveButton() {

        this.getSaveButton().click();
        ManualService42SystemPage manualService42SystemPage = new ManualService42SystemPage(_driver);
        ReusableMethods.waitForClickablility(manualService42SystemPage.getSearchField(), 20, _driver);
    }

    public void clickCancelButton() {
        try{
//            this.getCancelButton().click();
//
//            Thread.sleep(10000);
            ReusableMethods.clickElement(getCancelButton(), 3,_driver);
        }
        catch(Exception e){
            System.out.println("cancel was not possible");
            System.out.println(e);
        }
    }

    public WebElement getDescriptionField() {
        return description;
    }

    public void setDescriptionField(String input) {
        this.getDescriptionField().clear();
        this.getDescriptionField().sendKeys(input);
    }
    public WebElement getAmIdField() {
        return amId;
    }

    public void setAmId(String input) {
//        this.getAmIdField().clear();
        ReusableMethods.clearField(getAmIdField());
        this.getAmIdField().sendKeys(input);
    }
    public WebElement getTnrField() {
        return tnr;
    }

    public void setTnrField(String input) {
        this.getTnrField().clear();
        this.getTnrField().sendKeys(input);
    }
}