package org.maktab.houseservice.exception;

public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException() {
    }

    public RequestNotFoundException(String message) {
        super(message);
    }
}
