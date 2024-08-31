package org.codecool.backend.controller.exception;

public class EntityAlreadyInDBException extends RuntimeException{
    public EntityAlreadyInDBException(String message) {
        super(message);
    }
}
