package org.maktab.houseservice.exception;

public class SendMailException extends RuntimeException{
    public SendMailException() {
    }

    public SendMailException(String message) {
        super(message);
    }

    public SendMailException(String message, Throwable cause) {
        super(message, cause);
    }
}
