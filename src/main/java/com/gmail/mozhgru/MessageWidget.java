package com.gmail.mozhgru;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MessageWidget extends AbstractPage {

    @FindBy(id = "")
    private WebElement to;

    @FindBy(id = "")
    private WebElement theme;

    @FindBy(id = "")
    private WebElement message;

    @FindBy(id = "")
    private WebElement bttnSend;

    @FindBy(id = "")
    private WebElement bttnSaveAndClose;

    private String textOfElement = "";

    public MessageWidget(WebDriver driver) {
        super(driver);
        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }

    public String getTextOfElement(String field){

        switch (field) {

            case "кому":
                textOfElement = to.getText();
                break;

            case "тема":
                textOfElement = theme.getText();
                break;

            case "тело письма":
                textOfElement = message.getText();
                break;

            default:
                throw new IllegalArgumentException("Invalid field name:" + field);
        }

        return textOfElement;
    }

    public void saveAndClose() {
        bttnSaveAndClose.click();
    }

    public void send() {
        bttnSend.click();
    }
}
