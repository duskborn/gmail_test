package com.gmail.mozhgru.stepdefinition;

import com.gmail.mozhgru.LoginPage;
import com.gmail.mozhgru.MainPage;
import com.gmail.mozhgru.MessageWidget;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.И;
import cucumber.api.java.ru.Пусть;
import cucumber.api.java.ru.Также;
import cucumber.api.java.ru.Тогда;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;

public class MailPageSteps {
    private WebDriver driver = null;
    private LoginPage loginPage = null;
    private MainPage mainPage = null;
    private MessageWidget messageWidget = null;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mozhg\\jars\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Пусть("^открыта страница входа в приложение '(.+)'$")
    public void openLoginPage(String url) {
        loginPage = new LoginPage(driver);
        loginPage.open(url);
    }

    @И("^пользователь вводит в поле \"([^\"]*)\" значение \"([^\"]*)\"$")
    public void setTextToInput(String fieldName, String value) {
        switch (fieldName) {
            case "имя пользователя":
                loginPage.fillUsername(value);
                break;

            case "пароль":
                loginPage.fillPassword(value);
                break;

            default:
                throw new IllegalArgumentException("Invalid field name:" + fieldName);
        }

    }

    @И("^нажимает кнопку \"([^\"]*)\"$")
    public void clickButton(String btnName) {
//        mainPage = new MainPage(driver);
//        loginPage = new LoginPage(driver);
//        messageWidget = new MessageWidget(driver);

        switch (btnName) {

            case "далее":
                loginPage.submit();
                break;

            case "готово":
                loginPage.done();
                break;

            case "аккаунт google":
                mainPage.checkAccount();
                break;

            case "написать":
                mainPage.compose();
                break;

            case "сохранить и закрыть":
                messageWidget.saveAndClose();
                break;

            case "черновики":
                mainPage.checkDrafts();
                break;

            case "отправить":
                messageWidget.send();
                break;

            case "отправленные":
                mainPage.checkSentMessages();
                break;


            default:
                throw new IllegalArgumentException("Invalid button name:" + btnName);
        }
    }


    @Тогда("^появилось сообщение о неуспешном входе \"(.+)\"$")
    public void getErrorMessage(String error) {
        mainPage = new MainPage(driver);
        Assert.assertEquals(mainPage.getTextOfElement(), error);
    }


    @Тогда("^открылась главная страница$")
    public void mainPageOpened() {
        mainPage = new MainPage(driver);
    }

    @Тогда("^в появившемся виджете видно почту пользователя \"([^\"]*)\"$")
    public void checkUserName(String text) {
        Assert.assertEquals(text, mainPage.getCurrentUser());
    }


    @Пусть("^пользователь выходит из учетной записи$")
    public void pressLogout() throws Throwable {
        mainPage = new MainPage(driver);
        mainPage.tryLogOut();
    }

    @Тогда("^пользователь выбирает \"([^\"]*)\"$")
    public void chooseElement(String stringElement) {
        mainPage = new MainPage(driver);
        switch (stringElement) {

            case "последний черновик":
                mainPage.chooseLastDraft();
                break;

            case "последнее отправленное письмо":
                mainPage.chooseLastLetter();
                break;

            default:
                throw new IllegalArgumentException("Invalid element name:" + stringElement);
        }
    }

    @И("^видит в поле \"([^\"]*)\" значение \"([^\"]*)\"$")
    public void checkTextInField(String field, String textInField) {
        messageWidget = new MessageWidget(driver);
        Assert.assertEquals(messageWidget.getTextOfElement(field), textInField);
    }

}
