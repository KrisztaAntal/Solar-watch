package org.codecool.backend.controller.exception;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(){
        super("Member not found");
    }
}
