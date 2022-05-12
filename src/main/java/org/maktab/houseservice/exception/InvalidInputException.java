package org.maktab.houseservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends RuntimeException {
    private Integer statusCode;

    public InvalidInputException() {
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(Integer statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public Integer getStatusCode() {
        return statusCode;
    }
}
