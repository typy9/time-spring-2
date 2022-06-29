package com.parpiiev.time.exceptions.users_activity;

public class UsersActivityAlreadyExistsException extends RuntimeException{

    public UsersActivityAlreadyExistsException() {
    }

    public UsersActivityAlreadyExistsException(String message) {
        super(message);
    }

    public UsersActivityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsersActivityAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
