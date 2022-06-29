package com.parpiiev.time.exceptions.activity;

public class ActivityAlreadyExistsException extends RuntimeException {
    public ActivityAlreadyExistsException() {
    }

    public ActivityAlreadyExistsException(String message) {
        super(message);
    }

    public ActivityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivityAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
