package com.parpiiev.time.exceptions.category;

public class CategoryAlreadyExistsException extends RuntimeException{

    public CategoryAlreadyExistsException() {
    }

    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    public CategoryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
