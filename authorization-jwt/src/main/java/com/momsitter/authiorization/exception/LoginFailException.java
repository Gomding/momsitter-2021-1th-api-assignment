package com.momsitter.authiorization.exception;

import com.momsitter.common.exception.UnauthorizedException;

public class LoginFailException extends UnauthorizedException {
    public LoginFailException(String message) {
        super(message);
    }
}
