package org.maktab.houseservice.controller;

import org.maktab.houseservice.exception.*;
import org.maktab.houseservice.model.ApiResponse;
import org.maktab.houseservice.model.ErrorApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class, CategoryNotFoundException.class, CommentNotFoundException.class, OfferNotFoundException.class, RequestNotFoundException.class, ServiceNotFoundException.class})
    public ResponseEntity<ApiResponse> resourceNotFoundException(Exception e) {
        ApiResponse apiResponse = ErrorApiResponse.withError(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler({DuplicateCategoryNameException.class, DuplicateCommentException.class, DuplicateEmailException.class, DuplicateOfferException.class, DuplicateServiceNameException.class})
    public ResponseEntity<ApiResponse> duplicateKeyAttributeException(Exception e) {
        ApiResponse apiResponse = ErrorApiResponse.withError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler({BalanceIsNotEnoughException.class, InvalidInputException.class, InvalidRequestException.class, OfferAmountIsLowException.class, SkillsDoesNotMatchedException.class})
    public ResponseEntity<ApiResponse> invalidRequestException(Exception e) {
        ApiResponse apiResponse = ErrorApiResponse.withError(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse> validationExceptions(ConstraintViolationException constraintViolationException) {
        Set<String> errors = constraintViolationException.getConstraintViolations().stream().map(constraintViolation -> String.format("%s value '%s' %s", constraintViolation.getPropertyPath(), constraintViolation.getInvalidValue(), constraintViolation.getMessage())).collect(Collectors.toSet());
        ApiResponse apiResponse = ErrorApiResponse.withError(errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
