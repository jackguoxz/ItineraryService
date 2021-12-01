package com.example.developer.docker_microservices.ui.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.InvalidParameterException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice @Slf4j
public class GlobalExceptionHandler
{
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public String methodArgumentNotValidException(Exception ex) {
        log.error("An unexpected error has happened", ex);
        return "An internal error has happened, please report the incident";
    }

    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(InvalidParameterException.class)
    public String invalidParameterException(InvalidParameterException ex){
        log.error("This is a BAD REQUEST", ex);
        return "This is a BAD REQUEST";
    }


    @ResponseBody
    @ExceptionHandler(PermissionException.class)
    public String invalidParameterException(PermissionException ex) {
        return "This is a BAD Permission";
    }
}