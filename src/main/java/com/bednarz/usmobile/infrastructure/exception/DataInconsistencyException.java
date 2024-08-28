package com.bednarz.usmobile.infrastructure.exception;

public class DataInconsistencyException extends RuntimeException {
    public DataInconsistencyException(String message) {
        super(message);
    }
}
