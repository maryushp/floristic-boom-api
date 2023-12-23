package com.floristicboom.utils.exceptionhandler.exceptions;

public class NoSuchItemException extends RuntimeException {
    public NoSuchItemException(String message) {
        super(message);
    }
}