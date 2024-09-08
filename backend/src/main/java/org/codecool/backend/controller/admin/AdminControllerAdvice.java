package org.codecool.backend.controller.admin;

import org.codecool.backend.controller.exception.EntityAlreadyInDBException;
import org.codecool.backend.controller.exception.NoSuchEntityInDBException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdminControllerAdvice {

    @ResponseBody
    @ExceptionHandler(EntityAlreadyInDBException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEntityAlreadyInDBException(EntityAlreadyInDBException e) {return e.getMessage();}

    @ResponseBody
    @ExceptionHandler(NoSuchEntityInDBException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchEntityInDBException(NoSuchEntityInDBException e) {return e.getMessage();}

}
