package com.momsitter.exception;

import com.momsitter.common.exception.BadRequestException;

public class InvalidArgumentException extends BadRequestException {
    public InvalidArgumentException(String message) {
        super(message);
    }
}
