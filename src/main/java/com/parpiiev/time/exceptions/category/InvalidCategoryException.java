package com.parpiiev.time.exceptions.category;

public class InvalidCategoryException extends RuntimeException{

    public InvalidCategoryException() {
    }

    public InvalidCategoryException(String message) {
        super(message);
    }

    public InvalidCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCategoryException(Throwable cause) {
        super(cause);
    }
}
