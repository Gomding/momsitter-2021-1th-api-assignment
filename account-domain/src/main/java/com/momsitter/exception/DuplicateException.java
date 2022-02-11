package com.momsitter.exception;

import com.momsitter.common.exception.ConflictException;

public class DuplicateException extends ConflictException {
    public DuplicateException(String message) {
        super(message);
    }
}
