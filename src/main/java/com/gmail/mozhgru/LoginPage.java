package com.gmail.mozhgru;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends  AbstractPage {

    @FindBy(id = "")
    private WebElement txtUsername;

    @FindBy(id = "")
    private WebElement txtPassword;

    @FindBy(id = "")
    private WebElement bttnSubmit;

    @FindBy(id = "")
    private WebElement bttnDone;

    @FindBy(id = "")
    private WebElement bttnAccount;

    @FindBy(id = "")
    private WebElement bttnCompose;

    {
        driver.get("http://at.pflb.ru/matrixboard2/");
        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {driver.get(url);}

    public void fillUsername(String login) {
        txtUsername.sendKeys(login);
    }

    public void fillPassword(String pass) {
        txtPassword.sendKeys(pass);
    }

    public void submit() {
        bttnSubmit.click();
    }

    public void done() {
        bttnDone.click();
    }

    public void checkAccount() {
        bttnAccount.click();
    }

    public void compose() {
        bttnCompose.click();
    }
}
