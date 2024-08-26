package com.bednarz.usmobile.infrastructure.exception;

import com.bednarz.usmobile.application.ApiResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manve = (MethodArgumentNotValidException) ex;
            manve.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        } else if (ex instanceof HandlerMethodValidationException) {
            HandlerMethodValidationException hmve = (HandlerMethodValidationException) ex;
            hmve.getAllValidationResults().forEach(violation -> {
                String fieldName = violation.getMethodParameter().toString();
                String errorMessage = violation.getResolvableErrors().get(0).getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        }

        return new ResponseEntity<>(ApiResponse.error(errors.toString()), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
