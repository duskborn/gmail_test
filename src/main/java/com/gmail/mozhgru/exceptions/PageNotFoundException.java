package com.gmail.mozhgru.exceptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String name) {
        super();
    }

    public PageNotFoundException(String name, Throwable cause) {
        super(String.format("Страница с именеем \"%s\" не найдена", name), cause);
    }
}
