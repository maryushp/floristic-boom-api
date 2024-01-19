package com.floristicboom.utils.exceptionhandler.exceptions;

import static com.floristicboom.utils.Constants.INCORRECT_TOKEN_TYPE_EXCEPTION;

public class IncorrectTokenTypeException extends RuntimeException {
    public IncorrectTokenTypeException() {
        super(INCORRECT_TOKEN_TYPE_EXCEPTION);
    }
}