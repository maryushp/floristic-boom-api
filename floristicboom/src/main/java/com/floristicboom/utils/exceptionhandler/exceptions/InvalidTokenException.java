package com.floristicboom.utils.exceptionhandler.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String e) {
        super(e);
    }
}