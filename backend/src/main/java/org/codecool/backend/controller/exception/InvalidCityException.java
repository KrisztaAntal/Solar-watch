package org.codecool.backend.controller.exception;

public class InvalidCityException extends RuntimeException {
    public InvalidCityException() {
        super("Invalid city name, or country code");
    }
}
