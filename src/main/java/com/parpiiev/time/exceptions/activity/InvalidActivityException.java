package com.parpiiev.time.exceptions.activity;

public class InvalidActivityException extends RuntimeException{

    public InvalidActivityException() {
    }

    public InvalidActivityException(String message) {
        super(message);
    }

    public InvalidActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidActivityException(Throwable cause) {
        super(cause);
    }
}
