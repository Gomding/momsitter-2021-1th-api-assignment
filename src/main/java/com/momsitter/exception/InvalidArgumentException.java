package com.momsitter.exception;

public class InvalidArgumentException extends BadRequestException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
