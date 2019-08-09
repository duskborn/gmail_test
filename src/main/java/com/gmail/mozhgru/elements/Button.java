package com.gmail.mozhgru.elements;

import com.gmail.mozhgru.interfaces.Clickable;
import com.gmail.mozhgru.interfaces.HasText;
import com.gmail.mozhgru.utils.DriverUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.gmail.mozhgru.utils.CommonUtils.isEmptyString;

public class Button extends AbstractElement implements HasText, Clickable {

    public Button(WebElement initialElement) {
        super(initialElement);
    }

    private static Logger logger = Logger.getLogger(AbstractElement.class);

    @Override
    public void click() {
        try {
            String name = isEmptyString(getAnnotatedName()) ? getObjectName() : getAnnotatedName();
            logger.info(String.format("Клик по кнопке '%s'", name));
            DriverUtils
                    .waitFor(10, ExpectedConditions.elementToBeClickable(initialElement))
                    .click();
            //initialElement.click();
        } catch (Exception e) {
            logger.error("Не удалось выполнить клик по кнопке", e);
        }
    }

    @Override
    public String getText() {
        return initialElement.getText();
    }
}
