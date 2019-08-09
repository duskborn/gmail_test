package com.gmail.mozhgru.page;

import com.gmail.mozhgru.config.Config;
import com.gmail.mozhgru.config.DriverManager;
import com.gmail.mozhgru.config.PageHandler;
import com.gmail.mozhgru.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class MainPage extends AbstractPage {

    @FindBy(xpath = "//div[@aria-label=\"Информация об аккаунте\"]/div[1]/div/div[2]")
    private WebElement profileContainer;

    @FindBy(xpath = "//a[contains(text(), 'Выйти')]")
    private WebElement logOut;

    @FindBy(xpath = "//tr[1]//div[2]//span[contains(text(), 'Черновик')]")
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

    public void tryLogOut(){
        bttnAccount.click();
        logOut.click();
    }

    public String getCurrentUser(){
        return profileContainer.getText();
    }

    public void chooseLastDraft(){
        lastDraft.click();
    }

    public void chooseLastLetter(){
        lastLetter.click();
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
        try {
            bttnSentMessages.click();
        } catch (org.openqa.selenium.StaleElementReferenceException ex) {
            bttnSentMessages.click();
        }
    }

    //TODO state
    public MainPage() {
        super(DriverManager.get().findElement(By.xpath("//ion-view[@state = 'добавить стэйт']")));
    }

    //TODO state
    @Override
    protected void waitForLoadFinished() {
        DriverUtils.waitFor(Config.getPageLoadingTimeout(), PageHandler.waitForLoadFinished(true, "поменять стэйт"));
//        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
//        wait.until(drv -> profileContainer.isDisplayed());
    }
}
