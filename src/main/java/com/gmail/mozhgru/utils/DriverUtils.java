package com.gmail.mozhgru.utils;

import com.gmail.mozhgru.config.Config;
import com.gmail.mozhgru.elements.AbstractElement;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;


public class DriverUtils {

    private static Logger logger = Logger.getLogger(AbstractElement.class);

    private DriverUtils() {
    }

    private static void disableImplicitWait() {
        DriverManager.get().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }

    private static void enableImplicitWait() {
        DriverManager.get().manage().timeouts()
                .implicitlyWait(Config.getGlobalTimeout(), TimeUnit.SECONDS);
    }

    public static <V> V waitFor(int seconds, Function<? super WebDriver, V> isTrue) {
        try {
            disableImplicitWait();
            return new WebDriverWait(DriverManager.get(), seconds).until(isTrue);
        } finally {
            enableImplicitWait();
        }
    }

    public static <V> V waitFor(Function<? super WebDriver, V> isTrue) {
        return new WebDriverWait(DriverManager.get(), Config.getGlobalTimeout()).until(isTrue);
    }

    public static <V> V waitForOrFail(int seconds, Function<? super WebDriver, V> isTrue) {
        try {
            return waitFor(seconds, isTrue);
        } catch (TimeoutException e) {
            logger.info("Не удалось дождаться события", e);
        }
        return null;
    }
}
