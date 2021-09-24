package com.epam.rd.java.basic.finalProject.exception;

public class CardException extends RuntimeException {
    public CardException() {
    }

    public CardException(String message) {
        super(message);
    }

    public CardException(String message, Throwable cause) {
        super(message, cause);
    }
}
