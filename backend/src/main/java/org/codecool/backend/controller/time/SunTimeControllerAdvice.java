package org.codecool.backend.controller.time;

import org.codecool.backend.controller.exception.InvalidCityException;
import org.codecool.backend.controller.exception.NoSuchEntityInDBException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SunTimeControllerAdvice {
    @ResponseBody
    @ExceptionHandler(InvalidCityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidCityExceptionHandler(InvalidCityException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NoSuchEntityInDBException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchEntityInDBException(NoSuchEntityInDBException e) {return e.getMessage();}
}
