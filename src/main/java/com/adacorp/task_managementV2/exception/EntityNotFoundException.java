package com.adacorp.task_managementV2.exception;

/* Personnaliser une Exception */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
