package com.gmail.mozhgru.page;

import com.gmail.mozhgru.config.Config;
import com.gmail.mozhgru.config.DriverManager;
import com.gmail.mozhgru.config.PageHandler;
import com.gmail.mozhgru.elements.Button;
import com.gmail.mozhgru.elements.Label;
import com.gmail.mozhgru.elements.TextField;
import com.gmail.mozhgru.utils.DriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class LoginPage extends AbstractPage {

    @FindBy(id = "identifierId")
    private TextField txtUsername;

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

    private MainPage mainPage = null;

    public LoginPage(WebDriver driver) {
        super();
    }

    public void open(String url) {
        waitForLoadFinished();
        driver.get(url);
    }

    public void fillUsername(String login) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        txtUsername.setText(login);
    }

    public void fillPassword(String pass) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

    //TODO state
    public LoginPage() {
        super(DriverManager.get().findElement(By.xpath("//ion-view[@state = 'добавить стэйт']")));
    }

    //TODO state
    @Override
    protected void waitForLoadFinished() {
        DriverUtils.waitFor(Config.getPageLoadingTimeout(), PageHandler.waitForLoadFinished(true, "поменять стэйт"));
    }
}
