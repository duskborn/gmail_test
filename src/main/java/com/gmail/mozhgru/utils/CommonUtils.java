package com.gmail.mozhgru.utils;

import com.gmail.mozhgru.config.Config;
import com.gmail.mozhgru.config.DriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonUtils {
    final WebDriver driver = DriverManager.get();
    private final WebDriverWait wait = new WebDriverWait(driver, Config.getGlobalTimeout());

    public void tryPressButton(WebElement button) {
        wait.until(drv -> button.isDisplayed());
        Actions actions = new Actions(driver);
        actions.moveToElement(button);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", button);
    }

    public static boolean isEmptyString(String s) {
        return s == null || "".equals(s);
    }
}
