package org.codecool.backend.controller.exception;

public class ExistingUsernameException extends RuntimeException{
    public ExistingUsernameException() {super("Username already exists");}
}
