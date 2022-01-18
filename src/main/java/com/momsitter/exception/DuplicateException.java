package com.momsitter.exception;

public class DuplicateException extends ConflictException {
    public DuplicateException(String message) {
        super(message);
    }
}
