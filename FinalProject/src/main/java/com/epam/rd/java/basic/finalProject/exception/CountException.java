package com.epam.rd.java.basic.finalProject.exception;

public class CountException extends RuntimeException{
    public CountException() {
    }

    public CountException(String message) {
        super(message);
    }

    public CountException(String message, Throwable cause) {
        super(message, cause);
    }
}
