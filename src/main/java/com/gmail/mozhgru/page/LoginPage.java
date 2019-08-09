package com.gmail.mozhgru.page;

import com.gmail.mozhgru.annotations.Element;
import com.gmail.mozhgru.config.Config;
import com.gmail.mozhgru.config.PageHandler;
import com.gmail.mozhgru.elements.AbstractElement;
import com.gmail.mozhgru.elements.Button;
import com.gmail.mozhgru.elements.Label;
import com.gmail.mozhgru.elements.TextField;
import com.gmail.mozhgru.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LoginPage extends AbstractPage {

    @Element("имя пользователя")
    @FindBy(xpath = "//input[@id='identifierId']")
    private WebElement username;
    private TextField txtUsername = new TextField(username);

    @FindBy(id = "password")
    WebElement password;
    private TextField txtPassword = new TextField(password);;

    @FindBy(id = "")
    private Button bttnDone;

//    @FindBy(xpath = "//*[@id='identifierNext']")

    @Element("далее (пользователь)")
    @FindBy(id = "identifierNext")
    private WebElement submit;
    private Button bttnSubmit = new Button(submit);

    @FindBy(id = "passwordNext")
    private WebElement passwordNext;
    private Button bttnPass = new Button(passwordNext);

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
