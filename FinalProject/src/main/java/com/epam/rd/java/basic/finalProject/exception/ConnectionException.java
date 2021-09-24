package com.epam.rd.java.basic.finalProject.exception;

public class ConnectionException extends RuntimeException{

    public ConnectionException() {
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
