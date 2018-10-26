package com.gmail.mozhgru;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MainPage extends  AbstractPage {

    @FindBy(id = "")
    private WebElement loginFailed;

    @FindBy(css = "")
    private WebElement profileContainer;

    @FindBy(id = "")
    private WebElement logOut;

    @FindBy(id = "")
    private WebElement lastDraft;

    public MainPage (WebDriver driver){
        super(driver);
        wait.until(drv -> ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete"));
    }

    public String getTextOfElement(){
        wait.until(drv -> loginFailed.isDisplayed());
        return loginFailed.getText();
    }

    public void tryPressButton(WebElement button){
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", button);
    }

    public void tryLogOut(){
        tryPressButton(logOut);
    }

    public String getCurrentUser(){
        wait.until(drv -> profileContainer.isDisplayed());
        return profileContainer.getText();
    }

    public void chooseLastDraft(){
        tryPressButton(lastDraft);
    }


}
