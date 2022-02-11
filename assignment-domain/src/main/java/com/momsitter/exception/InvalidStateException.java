package com.momsitter.exception;

import com.momsitter.common.exception.BadRequestException;

public class InvalidStateException extends BadRequestException {
    public InvalidStateException(String message) {
        super(message);
    }
}
