package org.codecool.backend.controller.exception;

public class NoSuchEntityInDBException extends RuntimeException{
    public NoSuchEntityInDBException(String message){super(message);}
}
