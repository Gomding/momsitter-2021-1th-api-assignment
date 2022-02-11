package com.momsitter.common.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends MomSitterException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, new ExceptionResponse(message));
    }
}
