package com.example.exception.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(UUID id) {
        super("User with id: " + id.toString() + " not found!");
    }
}
