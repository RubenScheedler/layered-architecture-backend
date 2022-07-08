package com.hashnode.rubenscheedler.layeredArchitectureBackend.business.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
