package org.codecool.backend.controller;

import jakarta.validation.constraints.NotNull;
import org.codecool.backend.controller.exception.ExistingUsernameException;
import org.codecool.backend.controller.exception.InvalidCityException;
import org.codecool.backend.controller.exception.MemberNotFoundException;
import org.codecool.backend.controller.exception.UnauthorizedChangeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MemberControllerAdvice {
    @ResponseBody
    @ExceptionHandler(UnauthorizedChangeException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handleUnauthorizedChangeException(UnauthorizedChangeException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMemberNotFoundException(MemberNotFoundException e) {return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(ExistingUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleExistingUsernameException(ExistingUsernameException e) {return e.getMessage();}
}
