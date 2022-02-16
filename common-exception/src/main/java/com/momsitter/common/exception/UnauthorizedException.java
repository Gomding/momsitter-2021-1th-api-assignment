package com.momsitter.common.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends MomSitterException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, new ExceptionResponse(message));
    }
}
