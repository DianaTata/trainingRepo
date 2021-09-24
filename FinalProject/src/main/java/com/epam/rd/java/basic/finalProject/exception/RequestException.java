package com.epam.rd.java.basic.finalProject.exception;

public class RequestException extends RuntimeException{
    public RequestException() {
    }

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
