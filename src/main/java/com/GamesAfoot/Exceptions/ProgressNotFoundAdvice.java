package com.GamesAfoot.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProgressNotFoundAdvice {

    @ExceptionHandler(ProgressNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String progressNotFoundHandler(ProgressNotFoundException ex) {
        return ex.getMessage();
    }
}
