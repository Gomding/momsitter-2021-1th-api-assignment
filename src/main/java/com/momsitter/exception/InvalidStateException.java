package com.momsitter.exception;

public class InvalidStateException extends BadRequestException {
    public InvalidStateException(String message) {
        super(message);
    }
}
