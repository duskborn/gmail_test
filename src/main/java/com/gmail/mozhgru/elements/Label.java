package com.gmail.mozhgru.elements;

import com.gmail.mozhgru.interfaces.Clickable;
import com.gmail.mozhgru.interfaces.HasText;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;

public class Label extends AbstractElement implements HasText, Clickable {

    private static Logger logger = Logger.getLogger(AbstractElement.class);

    public Label(WebElement initialElement) {
        super(initialElement);
    }

    @Override
    public String getText() {
        return isDisplayed() ? initialElement.getText() : "";
    }

    public String getValue() {
        return initialElement.getAttribute("value");
    }
}
