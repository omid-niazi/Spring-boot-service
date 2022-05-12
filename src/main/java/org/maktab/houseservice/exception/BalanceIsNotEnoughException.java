package org.maktab.houseservice.exception;

public class BalanceIsNotEnoughException extends RuntimeException {
    public BalanceIsNotEnoughException() {
    }

    public BalanceIsNotEnoughException(String message) {
        super(message);
    }
}
