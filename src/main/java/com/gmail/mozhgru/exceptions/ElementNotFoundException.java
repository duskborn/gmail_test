package com.gmail.mozhgru.exceptions;

import com.gmail.mozhgru.elements.AbstractElement;
import org.apache.log4j.Logger;

/**
 * Исключение элемент не найден.
 */
public class ElementNotFoundException extends RuntimeException {
    private static Logger logger = Logger.getLogger(AbstractElement.class);

    /**
     * Конструктор.
     *
     * @param message сообщение
     */
    public ElementNotFoundException(String message) {
        super(message);
        logger.info(message);
    }

    /**
     * Конструктор.
     *
     * @param message сообщение
     * @param cause другое исключение
     */
    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
        logger.info(message, cause);
    }
}
