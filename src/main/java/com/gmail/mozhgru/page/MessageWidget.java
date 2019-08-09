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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;

public class MessageWidget extends AbstractPage {

    @FindBy(xpath = "//div[contains(text(), 'Получатели')]")
    private TextField to;

    @FindBy(xpath = "//div[@tabindex]/div/span[@email]")
    private TextField draftTo;

    @FindBy(xpath = "//div[@role='main']//tr[1]//div[contains(text(), 'Кому')]/span[@email]")
    private TextField sentTo;

    @FindBy(xpath = "//textarea[@role='combobox'][@name='to']")
    private TextField toCombobox;

    @FindBy(xpath = "//input[@name='subjectbox']")
    private TextField theme;

    @FindBy(xpath = "//div[@role='main']//tr[1]//div[@role='link']/div/div/span/span")
    private TextField draftTheme;

    @FindBy(xpath = "//div[@aria-label='Тело письма']")
    private TextField message;

    @FindBy(xpath = "//div[@role='main']//tr[1]//div[@role='link']/div/span")
    private WebElement sentMessage;

    @FindBy(xpath = "//div[@role='button'][contains(text(), 'Отправить')]")
    private Button bttnSend;

    @FindBy(xpath = "//img[@aria-label='Сохранить и закрыть']")
    private Button bttnSaveAndClose;

    @FindBy(xpath = "//div[contains(text(), 'Новое сообщение')]")
    private Label letterHeader;

    private String textOfElement = "";

    public MessageWidget(WebDriver driver) {
        super(driver);
    }

    public String getTextOfElement(String field){
        Actions actions = new Actions(driver);

        switch (field) {

            case "кому":
                textOfElement = draftTo.getText();
                break;

            case "кому отправленного письма":
                textOfElement = sentTo.getText();
                break;

            case "тема":
                textOfElement = draftTheme.getText();
                break;

            case "тело письма":
                textOfElement = message.getText();
                break;

            case "тело отправленного письма":
                actions.moveToElement(sentMessage);
                textOfElement = sentMessage.getText();
                textOfElement = textOfElement.substring(4, textOfElement.length());
                break;

            case "Новое сообщение":
                textOfElement = letterHeader.getText();
                break;

            default:
                throw new IllegalArgumentException("Invalid field name:" + field);
        }

        return textOfElement;
    }

    public void fillTo(String text) {
        toCombobox.setText(text);
    }

    public void fillTheme(String text) {
        theme.setText(text);
    }

    public void fillMessage(String text) {
        message.click();
        message.setText(text);
    }

    public void saveAndClose() {
        bttnSaveAndClose.click();
    }

    public void send() {
        bttnSend.click();
    }

    @Override
    protected void waitForLoadFinished() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }
}
