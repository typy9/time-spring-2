package com.parpiiev.time.exceptions.users_activity;

public class InvalidUsersActivityException extends RuntimeException{

    public InvalidUsersActivityException() {
    }

    public InvalidUsersActivityException(String message) {
        super(message);
    }

    public InvalidUsersActivityException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidUsersActivityException(Throwable cause) {
        super(cause);
    }
}
