package com.example.developer.docker_microservices.ui.exception;

public class PermissionException extends RuntimeException {
    public PermissionException(String message) {
        super(message);
    }
}
