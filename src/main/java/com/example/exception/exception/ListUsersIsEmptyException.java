package com.example.exception.exception;

public class ListUsersIsEmptyException extends RuntimeException {
    public ListUsersIsEmptyException() {
        super("List of users is empty!");
    }
}
