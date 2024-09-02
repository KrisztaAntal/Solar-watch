package org.codecool.backend.controller.exception;

public class UnauthorizedChangeException extends RuntimeException{
    public UnauthorizedChangeException(){super("User is not authorized to change other users data");}
}
