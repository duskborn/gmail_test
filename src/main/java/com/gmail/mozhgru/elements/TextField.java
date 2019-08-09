package com.gmail.mozhgru.elements;

import static com.gmail.mozhgru.utils.CommonUtils.isEmptyString;

import com.gmail.mozhgru.interfaces.Clickable;
import com.gmail.mozhgru.interfaces.HasText;
import com.gmail.mozhgru.interfaces.TextEditable;
import com.gmail.mozhgru.utils.Attributes;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

public class TextField extends AbstractElement implements HasText, TextEditable, Clickable {

    private static Logger logger = Logger.getLogger(AbstractElement.class);

    public TextField(WebElement initialElement) {
        super(initialElement);
    }

    @Override
    public String getText() {
        String text = getAttribute(Attributes.VALUE);
        return (isEmptyString(text)) ? initialElement.getText() : text;
    }

    @Override
    public void appendText(String text) {
        initialElement.sendKeys(text);
    }

    @Override
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
