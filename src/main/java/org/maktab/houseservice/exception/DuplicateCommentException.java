package org.maktab.houseservice.exception;

public class DuplicateCommentException extends RuntimeException {
    public DuplicateCommentException() {
    }

    public DuplicateCommentException(String message) {
        super(message);
    }
}
