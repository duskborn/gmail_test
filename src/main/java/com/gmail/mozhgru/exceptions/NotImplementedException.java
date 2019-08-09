package com.gmail.mozhgru.exceptions;

public class NotImplementedException extends NullPointerException {
    public NotImplementedException(String s){
        super(s);
    }
    public NotImplementedException(){
        super("Метод не определен");
    }
}
