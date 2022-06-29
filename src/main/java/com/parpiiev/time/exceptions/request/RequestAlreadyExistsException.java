package com.parpiiev.time.exceptions.request;

public class RequestAlreadyExistsException extends RuntimeException {

    public RequestAlreadyExistsException() {
    }

    public RequestAlreadyExistsException(String message) {
        super(message);
    }

    public RequestAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}
