package com.momsitter.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends MomSitterException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, new ExceptionResponse(message));
    }
}
