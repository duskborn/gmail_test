package com.gmail.mozhgru;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class LoginPage extends  AbstractPage {

    @FindBy(id = "identifierId")
    private WebElement txtUsername;

    @FindBy(id = "password")
    private WebElement txtPassword;

    @FindBy(id = "")
    private WebElement bttnDone;

    @FindBy(id = "identifierNext")
    private WebElement bttnSubmit;

    @FindBy(id = "passwordNext")
    private WebElement bttnPass;

    @FindBy(css = "#password > div:nth-child(2) > div:nth-child(2)")
    private WebElement loginFailed;

    private MainPage mainPage = null;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
        driver.get(url);}

    public void fillUsername(String login) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainPage = new MainPage(driver);
        mainPage.tryFillField(txtUsername, login);
    }

    public void fillPassword(String pass) {
        mainPage = new MainPage(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        mainPage.tryFillField(txtPassword, pass);
    }

    public void submitUser() {
        bttnSubmit.click();
    }

    public void submitPass() {
        mainPage.tryPressButton(bttnPass);
    }

    public void done() {
        mainPage = new MainPage(driver);
        mainPage.tryPressButton(bttnDone);
    }

    public String getTextOfElement(){
        wait.until(drv -> loginFailed.isDisplayed());
        return loginFailed.getText();
    }
}
