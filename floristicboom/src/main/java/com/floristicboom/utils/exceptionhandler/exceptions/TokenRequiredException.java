package com.floristicboom.utils.exceptionhandler.exceptions;

public class TokenRequiredException extends RuntimeException {
    public TokenRequiredException(String e) {
        super(e);
    }
}