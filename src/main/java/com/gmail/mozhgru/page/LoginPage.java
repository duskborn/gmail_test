package com.gmail.mozhgru.page;

import com.gmail.mozhgru.config.Config;
import com.gmail.mozhgru.config.PageHandler;
import com.gmail.mozhgru.elements.Button;
import com.gmail.mozhgru.elements.Label;
import com.gmail.mozhgru.elements.TextField;
import com.gmail.mozhgru.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginPage extends AbstractPage {

    @FindBy(xpath = "//input[@id='identifierId']")
    TextField txtUsername;

    @FindBy(id = "password")
    private TextField txtPassword;

    @FindBy(id = "")
    private Button bttnDone;

    @FindBy(id = "identifierNext")
    private Button bttnSubmit;

    @FindBy(id = "passwordNext")
    private Button bttnPass;

    @FindBy(css = "#password > div:nth-child(2) > div:nth-child(2)")
    public Label loginFailed;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        driver.get(url);
    }

    public void fillUsername(String login) {
        txtUsername.setText(login);
    }

    public void fillPassword(String pass) {
        txtPassword.setText(pass);
    }

    public void submitUser() {
        bttnSubmit.click();
    }

    public void submitPass() {
        bttnPass.click();
    }

    public void done() {
        bttnDone.click();
    }

    @Override
    protected void waitForLoadFinished() {
        new WebDriverWait(driver, 10000).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}
