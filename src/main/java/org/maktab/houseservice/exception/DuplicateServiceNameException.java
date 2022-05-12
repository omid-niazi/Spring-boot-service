package org.maktab.houseservice.exception;

public class DuplicateServiceNameException extends RuntimeException {

    public DuplicateServiceNameException() {
    }

    public DuplicateServiceNameException(String message) {
        super(message);
    }
}
