package com.gmail.mozhgru.elements;

import com.gmail.mozhgru.utils.Attributes;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

import static com.gmail.mozhgru.utils.CommonUtils.isEmptyString;

public class TextField extends AbstractElement {

    private static Logger logger = Logger.getLogger(AbstractElement.class);

    public TextField(WebElement initialElement) {
        super(initialElement);
    }

    public String getText() {
        String text = getAttribute(Attributes.VALUE);
        return (isEmptyString(text)) ? initialElement.getText() : text;
    }

    public void setText(String text) {
        String name = isEmptyString(getAnnotatedName()) ? getObjectName() : getAnnotatedName();
        logger.info(String.format("Ввод текста '%s' в поле '%s'", text, name));
        try {
            if (!isEnabled()) {
                logger.info("Текстовое поле в состоянии \"отключено\", ввод текста не возможен");
            }
            initialElement.clear();
            initialElement.clear();
            initialElement.click();
            Thread.sleep(1000);
            initialElement.sendKeys(text);
        } catch (Exception e) {
            logger.info("Не удалось ввести текст", e);
        }
    }
}
