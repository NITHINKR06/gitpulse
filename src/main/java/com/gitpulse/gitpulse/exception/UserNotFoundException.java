package com.gitpulse.gitpulse.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String username) {
        super("GitHub user not found: " + username);
    }
}