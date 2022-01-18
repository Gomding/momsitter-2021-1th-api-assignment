package com.momsitter.exception;

public class LoginFailException extends UnauthorizedException {
    public LoginFailException(String message) {
        super(message);
    }
}
