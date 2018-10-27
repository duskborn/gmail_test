package com.gmail.mozhgru;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends  AbstractPage {

    @FindBy(id = "")
    private WebElement loginFailed;

    @FindBy(css = "")
    private WebElement profileContainer;

    @FindBy(id = "")
    private WebElement logOut;

    @FindBy(id = "")
    private WebElement lastDraft;

    @FindBy(id = "")
    private WebElement bttnAccount;

    @FindBy(id = "")
    private WebElement bttnCompose;

    @FindBy(id = "")
    private WebElement bttnDrafts;

    @FindBy(id = "")
    private WebElement bttnSentMessages;

    @FindBy(id = "")
    private WebElement lastLetter;

    public MainPage (WebDriver driver){
        super(driver);
        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }

    public String getTextOfElement(){
        wait.until(drv -> loginFailed.isDisplayed());
        return loginFailed.getText();
    }

    public void tryPressButton(WebElement button){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", button);
    }

    public void tryLogOut(){
        tryPressButton(logOut);
    }

    public String getCurrentUser(){
        wait.until(drv -> profileContainer.isDisplayed());
        return profileContainer.getText();
    }

    public void chooseLastDraft(){
        tryPressButton(lastDraft);
    }

    public void chooseLastLetter(){
        tryPressButton(lastLetter);
    }

    public void checkAccount() {
        bttnAccount.click();
    }

    public void compose() {
        bttnCompose.click();
    }

    public void checkDrafts() {
        bttnDrafts.click();
    }

    public void checkSentMessages() {
        bttnSentMessages.click();
    }

}
