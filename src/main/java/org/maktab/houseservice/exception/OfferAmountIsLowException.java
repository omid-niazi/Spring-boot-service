package org.maktab.houseservice.exception;

public class OfferAmountIsLowException extends RuntimeException {
    public OfferAmountIsLowException() {
    }

    public OfferAmountIsLowException(String message) {
        super(message);
    }
}
