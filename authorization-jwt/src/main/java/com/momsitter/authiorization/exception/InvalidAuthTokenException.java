package com.momsitter.authiorization.exception;

import com.momsitter.common.exception.UnauthorizedException;

public class InvalidAuthTokenException extends UnauthorizedException {
    public InvalidAuthTokenException(String message) {
        super(message);
    }
}
