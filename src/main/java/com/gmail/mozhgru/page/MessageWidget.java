package com.gmail.mozhgru;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class MessageWidget extends AbstractPage {

    @FindBy(xpath = "//div[contains(text(), 'Получатели')]")
    private WebElement to;

    @FindBy(xpath = "//div[@tabindex]/div/span[@email]")
    private WebElement draftTo;

    @FindBy(xpath = "//div[@role='main']//tr[1]//div[contains(text(), 'Кому')]/span[@email]")
    private WebElement sentTo;

    @FindBy(xpath = "//textarea[@role='combobox'][@name='to']")
    private WebElement toCombobox;

    @FindBy(xpath = "//input[@name='subjectbox']")
    private WebElement theme;

    @FindBy(xpath = "//div[@role='main']//tr[1]//div[@role='link']/div/div/span/span")
    private WebElement draftTheme;

    @FindBy(xpath = "//div[@aria-label='Тело письма']")
    private WebElement message;

    @FindBy(xpath = "//div[@role='main']//tr[1]//div[@role='link']/div/span")
    private WebElement sentMessage;

    @FindBy(xpath = "//div[@role='button'][contains(text(), 'Отправить')]")
    private WebElement bttnSend;

    @FindBy(xpath = "//img[@aria-label='Сохранить и закрыть']")
    private WebElement bttnSaveAndClose;

    @FindBy(xpath = "//div[contains(text(), 'Новое сообщение')]")
    private WebElement letterHeader;

    private MainPage mainPage = null;

    private String textOfElement = "";

    public MessageWidget(WebDriver driver) {
        super(driver);
        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
        mainPage = new MainPage(driver);
    }

    public String getTextOfElement(String field){
        Actions actions = new Actions(driver);

        switch (field) {

            case "кому":
                wait.until(drv -> draftTo.isDisplayed());
                actions.moveToElement(draftTo);
                textOfElement = draftTo.getText();
                break;

            case "кому отправленного письма":
                wait.until(drv -> sentTo.isDisplayed());
                actions.moveToElement(sentTo);
                textOfElement = sentTo.getText();
                break;

            case "тема":
                wait.until(drv -> draftTheme.isDisplayed());
                actions.moveToElement(draftTheme);
                textOfElement = draftTheme.getText();
                break;

            case "тело письма":
                wait.until(drv -> message.isDisplayed());
                actions.moveToElement(message);
                textOfElement = message.getText();
                break;

            case "тело отправленного письма":
                wait.until(drv -> sentMessage.isDisplayed());
                actions.moveToElement(sentMessage);
                textOfElement = sentMessage.getText();
                textOfElement = textOfElement.substring(4, textOfElement.length());
                break;

            case "Новое сообщение":
                wait.until(drv -> letterHeader.isDisplayed());
                actions.moveToElement(letterHeader);
                textOfElement = letterHeader.getText();
                break;

            default:
                throw new IllegalArgumentException("Invalid field name:" + field);
        }

        return textOfElement;
    }

    public void fillTo(String text) {
        mainPage.tryFillField(toCombobox, text);
    }

    public void fillTheme(String text) {
        theme.sendKeys(text);
    }

    public void fillMessage(String text) {
        message.click();
        mainPage.tryFillField(message, text);
    }

    public void saveAndClose() {
        bttnSaveAndClose.click();
    }

    public void send() {
        mainPage.tryPressButton(bttnSend);
    }
}
