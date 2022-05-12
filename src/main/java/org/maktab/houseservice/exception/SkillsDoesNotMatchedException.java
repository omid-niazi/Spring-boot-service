package org.maktab.houseservice.exception;

public class SkillsDoesNotMatchedException extends RuntimeException {
    public SkillsDoesNotMatchedException() {
    }

    public SkillsDoesNotMatchedException(String message) {
        super(message);
    }
}
