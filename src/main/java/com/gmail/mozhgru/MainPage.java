package com.gmail.mozhgru;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class MainPage extends  AbstractPage {

    @FindBy(xpath = "//*[@id='gb']/div[2]/div[6]/div[1]/div/div[2]")
    private WebElement profileContainer;

    @FindBy(xpath = "//a[contains(text(), 'Выйти')]")
    private WebElement logOut;

    @FindBy(xpath = "//tr[1]//td//div[2]//span[contains(text(), 'Черновик')]")
    private WebElement lastDraft;

    @FindBy(xpath = "//a[@href='https://accounts.google.com/SignOutOptions?hl=ru&continue=https://mail.google.com/mail&service=mail']")
    private WebElement bttnAccount;

    @FindBy(xpath = "//div[contains(text(), 'Написать')]")
    private WebElement bttnCompose;

    @FindBy(xpath = "//div[contains(@data-tooltip, 'Черновики')]")
    private WebElement bttnDrafts;

    @FindBy(xpath = "//div[contains(@data-tooltip, 'Отправленные')]")
    private WebElement bttnSentMessages;

    @FindBy(xpath = "//tr[1]//td//div[contains(text(), 'Кому')]")
    private WebElement lastLetter;

    public MainPage (WebDriver driver){
        super(driver);
        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }

    public void tryPressButton(WebElement button){
        wait.until(drv -> button.isDisplayed());
        Actions actions = new Actions(driver);
        actions.moveToElement(button);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", button);
    }

    public void tryFillField(WebElement button, String text){
        wait.until(drv -> button.isDisplayed());
        Actions actions = new Actions(driver);
        actions.moveToElement(button);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", button);
        wait.until(drv -> button.isEnabled());
        actions.sendKeys(text);
        actions.build().perform();
    }

    public void tryLogOut(){
        tryPressButton(bttnAccount);
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
        tryPressButton(bttnAccount);
    }

    public void compose() {
        bttnCompose.click();
    }

    public void checkDrafts() {
        tryPressButton(bttnDrafts);
    }

    public void checkSentMessages() {
        tryPressButton(bttnSentMessages);
    }

}
