package com.floristicboom.utils.exceptionhandler.exceptions;

public class EmailAlreadyRegisteredException extends RuntimeException {
    public EmailAlreadyRegisteredException(String message) {
    super(message);
    }
}