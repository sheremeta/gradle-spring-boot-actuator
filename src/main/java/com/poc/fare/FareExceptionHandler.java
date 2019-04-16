package com.poc.fare;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@ControllerAdvice
public class FareExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public HttpEntity globalException(Throwable t) {
        return new ResponseEntity(SERVICE_UNAVAILABLE);
    }
}
