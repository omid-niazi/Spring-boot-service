package org.maktab.houseservice.exception;

public class DuplicateOfferException extends RuntimeException {
    public DuplicateOfferException() {
    }

    public DuplicateOfferException(String message) {
        super(message);
    }
}
