package com.gmail.mozhgru.exceptions;

public class DriverNotInitializedException extends RuntimeException {

    public DriverNotInitializedException() {
        super("Driver has not been initialized yet");
    }
}
