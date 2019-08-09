package com.gmail.mozhgru.page;

import com.gmail.mozhgru.annotations.Element;
import com.gmail.mozhgru.config.PageHandler;
import com.gmail.mozhgru.elements.AbstractElement;
import com.gmail.mozhgru.exceptions.ElementNotFoundException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.gmail.mozhgru.config.Config;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public abstract class AbstractPage {
    WebDriver driver;
    WebDriverWait wait;
    private static final int POLLING = 100;
//    private WebElement context;
    private static Logger logger = Logger.getLogger(AbstractElement.class);

    AbstractPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Config.getGlobalTimeout(), POLLING);
        waitForLoadFinished();
        PageFactory.initElements(driver, this);
    }

//    public AbstractPage(WebElement context) {
//        waitForLoadFinished();
//        this.context = context;
//        PageFactory.initElements(driver, this);
//        PageHandler.putPage(this);
//    }

    public <T> T getElement(String name) {
        try {
            AccessibleObject member = getMemberByName(name);
            if ((member.getClass().isAssignableFrom(Field.class))) {
                Object field = ((Field) member).get(this);
                ((AbstractElement) field).setFieldName(name);
                return (T) field;
            } else {
                return (T) ((Method) member).invoke(this);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.info("Ошибка получения элемента", e);
            throw new RuntimeException(e);
        }
    }

    protected abstract void waitForLoadFinished();

    private AccessibleObject getMemberByName(String name) {
        Set<Field> fieldSet = Arrays.stream(this.getClass().getDeclaredFields()).collect(Collectors.toSet());
        fieldSet.addAll(Arrays.stream(this.getClass().getFields()).collect(Collectors.toSet()));
        Optional<Field> field = fieldSet.stream()
                .filter(f -> f.isAnnotationPresent(Element.class)
                        && f.getDeclaredAnnotation(Element.class).value().equals(name))
                .findFirst();
        if (field.isPresent()) {
            field.get().setAccessible(true);
            return field.get();
        }

        Set<Method> methodsSet = Arrays.stream(this.getClass().getDeclaredMethods()).collect(Collectors.toSet());
        methodsSet.addAll(Arrays.stream(this.getClass().getMethods()).collect(Collectors.toSet()));
        Method method = methodsSet.stream()
                .filter(f -> f.isAnnotationPresent(Element.class)
                        && f.getDeclaredAnnotation(Element.class).value().equals(name))
                .findFirst()
                .orElseThrow(() -> new ElementNotFoundException(
                        String.format(
                                "Элемент с именем \"%s\" не описан на странице \"%s\"", name, this.toString())
                ));
        method.setAccessible(true);
        return method;
    }

    public void afterClose(Object... args) {
    }

}
