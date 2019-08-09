package com.gmail.mozhgru.stepdefinition;

import com.gmail.mozhgru.config.Config;
import com.gmail.mozhgru.config.PageHandler;
import com.gmail.mozhgru.interfaces.Clickable;
import com.gmail.mozhgru.page.AbstractPage;
import com.gmail.mozhgru.page.LoginPage;
import com.gmail.mozhgru.page.MainPage;
import com.gmail.mozhgru.page.MessageWidget;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.ru.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;

import org.apache.log4j.Logger;

public class MailPageSteps {
    private WebDriver driver = null;
    private LoginPage loginPage = null;
    private MainPage mainPage = null;
    private MessageWidget messageWidget = null;
    private static Logger logger = Logger.getLogger(MailPageSteps.class);

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", Config.getDriverLocation());
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        logger.info("before");
    }

    @After
    public void tearDown() {
        driver.quit();
        logger.info("after");
    }

    @Пусть("^открыта страница входа в приложение")
    public void openLoginPage() {
        String url = Config.getAppUrl();
        loginPage = new LoginPage(driver);
        loginPage.open(url);
        logger.info("Пусть открыта страница входа в приложение");
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

            case "кому":
                messageWidget.fillTo(value);
                break;

            case "тема":
                messageWidget.fillTheme(value);
                break;

            case "тело письма":
                messageWidget.fillMessage(value);
                break;

            default:
                throw new IllegalArgumentException("Invalid field name:" + fieldName);
        }
        logger.info("И пользователь вводит в поле " + fieldName + " значение " + value);
    }

    @И("нажимает кнопку {string}")
    public void clickOnButton(String elementName) {
        AbstractPage activePage = PageHandler.getActivePage();
        Clickable element = activePage.getElement(elementName);
        element.click();
    }

//
//    @И("^нажимает кнопку \"([^\"]*)\"$")
//    public void clickButton(String btnName) {
//
//        switch (btnName) {
//
//            case "далее (пользователь)":
//                loginPage.submitUser();
//                break;
//
//            case "далее (пароль)":
//                loginPage.submitPass();
//                break;
//
//            case "готово":
//                loginPage.done();
//                break;
//
//            case "аккаунт google":
//                mainPage.checkAccount();
//                break;
//
//            case "Написать":
//                mainPage.compose();
//                break;
//
//            case "сохранить и закрыть":
//                messageWidget.saveAndClose();
//                break;
//
//            case "черновики":
//                mainPage.checkDrafts();
//                break;
//
//            case "отправить":
//                messageWidget.send();
//                break;
//
//            case "отправленные":
//                mainPage.checkSentMessages();
//                break;
//
//
//            default:
//                throw new IllegalArgumentException("Invalid button name:" + btnName);
//        }
//        logger.info("И нажимает кнопку " + btnName);
//    }


    @Тогда("^появилось сообщение о неуспешном входе \"(.+)\"$")
    public void getErrorMessage(String error) {
        Assert.assertEquals(loginPage.loginFailed.getText(), error);
        logger.info("Тогда появилось сообщение о неуспешном входе: " + error);
    }


    @Тогда("^открылась главная страница$")
    public void mainPageOpened() {
        mainPage = new MainPage(driver);
        logger.info("Тогда открылась главная страница.");
    }

    @Тогда("^в появившемся виджете видно почту пользователя \"([^\"]*)\"$")
    public void checkUserName(String text) {
        Assert.assertEquals(mainPage.getCurrentUser(), text);
        logger.info("Тогда в появившемся виджете видно почту пользователя: " + text);
    }


    @Пусть("^пользователь выходит из учетной записи$")
    public void pressLogout() {
        mainPage.tryLogOut();
        logger.info("Пусть пользователь выходит из учетной записи.");
    }

    @Тогда("^пользователь выбирает \"([^\"]*)\"$")
    public void chooseElement(String stringElement) {
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
        logger.info("Тогда пользователь выбирает " + stringElement);
    }

    @И("^видит в поле \"([^\"]*)\" значение \"([^\"]*)\"$")
    public void checkTextInField(String field, String textInField) {
        Assert.assertEquals(messageWidget.getTextOfElement(field), textInField);
        logger.info("И видит в поле " + field + "значение" + textInField);
    }

    @И("^видит, что появился виджет \"([^\"]*)\"$")
    public void checkMessageWidget(String letterHeaderText) {
        messageWidget = new MessageWidget(driver);
        Assert.assertEquals(messageWidget.getTextOfElement(letterHeaderText), letterHeaderText);
        logger.info("И видит, что появился виджет " + letterHeaderText);
    }
}
