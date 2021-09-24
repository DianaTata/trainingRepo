package com.epam.rd.java.basic.finalProject.exception;

public class SendEmailException extends RuntimeException {
    public SendEmailException() {
    }

    public SendEmailException(String message) {
        super(message);
    }

    public SendEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
