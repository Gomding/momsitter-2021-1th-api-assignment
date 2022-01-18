package com.momsitter.exception;

public class InvalidAuthTokenException extends UnauthorizedException {
    public InvalidAuthTokenException(String message) {
        super(message);
    }
}
