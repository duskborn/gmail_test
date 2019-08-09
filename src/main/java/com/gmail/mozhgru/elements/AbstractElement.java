package com.gmail.mozhgru.elements;

import com.gmail.mozhgru.config.DriverManager;
import com.gmail.mozhgru.interfaces.IEnabled;
import com.gmail.mozhgru.utils.*;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.apache.log4j.Logger;

/**
 * Абстрактный класс для кастомных элементов.
 */
public abstract class AbstractElement implements IEnabled {

    private static Logger logger = Logger.getLogger(AbstractElement.class);

    protected final WebElement initialElement;

    private String fieldName;

    public AbstractElement(WebElement initialElement) {
        this.initialElement = initialElement;
    }

    public AbstractElement(By locator) {
        this.initialElement = DriverManager.get().findElement(locator);
    }

    public WebElement getInitialElement() {
        return initialElement;
    }

    public boolean isDisplayed() {
        if (initialElement == null) {
            logger.info("Элемент не был проинициализирован и имеет значение null");
        }
        try {
            return DriverUtils.waitFor(0, ExpectedConditions.visibilityOf(initialElement)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isEnabled() {
        return ifPresent().isEnabled();
    }

    public boolean isFocused() {
        if (initialElement == null) {
            logger.info("Элемент не был проинициализирован и имеет значение null");
        }

        try {
            return Objects.equals(initialElement.getAttribute("Focused").toLowerCase(), "true");
        } catch (Exception e) {
            //todo
        }
        return false;
    }

    String getObjectName() {
        try {
            if (fieldName != null) {
                return fieldName;
            }
            return ifPresent().getText().equals("") ? initialElement.findElement(By.xpath(".."))
                    .getText() : initialElement.getText();
        } catch (Exception ignored) {
            return "";
        }
    }

    private WebElement ifPresent() {
        if (initialElement == null) {
            logger.info(String.format("Не найден элемент '%s'", getAnnotatedName()));
        }
        return initialElement;
    }

    public void click() {
        Objects.requireNonNull(DriverUtils.waitForOrFail(3, ExpectedConditions.elementToBeClickable(ifPresent()))).click();
    }

    public boolean exists(String elementName) {
        try {
            if (initialElement != null && initialElement.isDisplayed()) {
                logger.info(String.format("Элемент '%s' отображается", elementName));
                return true;
            }
        } catch (Exception ignored) {

        }
        logger.info(String.format("Элемент '%s' не отображается", elementName));
        return false;
    }

    public void notExists(String elementName) {
        try {
            if (initialElement != null && initialElement.isDisplayed()) {
                logger.info(String.format("Элемент '%s' отображается", elementName));
            }
        } catch (Exception ignored) {
            logger.info(String.format("Элемент '%s' не отображается", elementName));
        }
    }

    public Map<String, Object> getAttributes() {
        Map<String, Object> map = Debug.getStaticFields(Attributes.class);
        Map result = new TreeMap();
        map.forEach((k, v) -> {
            try {
                Object value = getAttribute((String) v);
                if (value != null) {
                    result.put(k, value);
                }
            } catch (Exception ignored) {

            }
        });
        return result;
    }

    public String getAttribute(String attribute) {
        String result = ifPresent().getAttribute(attribute);
        if (result != null) {
            return result;
        }
        if ("text".equals(attribute)) {
            return initialElement.getText();
        }
        if ("index".equals(attribute)) {
            return String.valueOf(initialElement.findElements(By.xpath("./../*")).indexOf(initialElement));
        }
        return null;
    }

    public void setFieldName(String name) {
        fieldName = name;
    }

    protected String getAnnotatedName() {
        return this.fieldName;
    }

}
